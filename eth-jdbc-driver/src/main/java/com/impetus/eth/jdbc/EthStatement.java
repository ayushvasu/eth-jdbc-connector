/******************************************************************************* 
 * * Copyright 2018 Impetus Infotech.
 * *
 * * Licensed under the Apache License, Version 2.0 (the "License");
 * * you may not use this file except in compliance with the License.
 * * You may obtain a copy of the License at
 * *
 * * http://www.apache.org/licenses/LICENSE-2.0
 * *
 * * Unless required by applicable law or agreed to in writing, software
 * * distributed under the License is distributed on an "AS IS" BASIS,
 * * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * * See the License for the specific language governing permissions and
 * * limitations under the License.
 ******************************************************************************/
package com.impetus.eth.jdbc;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import com.impetus.blkch.BlkchnException;
import com.impetus.blkch.sql.query.RangeNode;
import com.impetus.eth.parser.EthPhysicalPlan;
import com.impetus.eth.query.EthColumns;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.Transaction;
import com.impetus.blkch.BlkchnErrorListener;
import com.impetus.blkch.jdbc.BlkchnStatement;
import com.impetus.blkch.sql.DataFrame;
import com.impetus.blkch.sql.generated.BlkchnSqlLexer;
import com.impetus.blkch.sql.generated.BlkchnSqlParser;
import com.impetus.blkch.sql.parser.AbstractSyntaxTreeVisitor;
import com.impetus.blkch.sql.parser.BlockchainVisitor;
import com.impetus.blkch.sql.parser.CaseInsensitiveCharStream;
import com.impetus.blkch.sql.parser.LogicalPlan;
import com.impetus.blkch.sql.query.FromItem;
import com.impetus.blkch.sql.query.IdentifierNode;
import com.impetus.blkch.sql.query.Table;
import com.impetus.eth.parser.EthQueryExecutor;

/**
 * The Class EthStatement.
 * 
 * @author ashishk.shukla
 * 
 */
public class EthStatement implements BlkchnStatement {

    private static final Logger LOGGER = LoggerFactory.getLogger(EthStatement.class);

    protected EthConnection connection;

    /** Holds batched commands */
    protected List<Object> batchedArgs;

    protected int rSetType;

    protected int rSetConcurrency;

    private RangeNode<?> pageRange;

    private ResultSet queryResultSet = null;

    /** Has this statement been closed? */
    protected boolean isClosed = false;

    public int getrSetType() {
        return rSetType;
    }

    public void setrSetType(int rSetType) {
        this.rSetType = rSetType;
    }

    public int getrSetConcurrency() {
        return rSetConcurrency;
    }

    public void setrSetConcurrency(int rSetConcurrency) {
        this.rSetConcurrency = rSetConcurrency;
    }

    public void setConnection(EthConnection connection) {
        this.connection = connection;
    }

    public EthStatement(EthConnection connection, int rSetType, int rSetConcurrency) {
        super();
        this.connection = connection;
        this.rSetType = rSetType;
        this.rSetConcurrency = rSetConcurrency;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        LogicalPlan logicalPlan = getLogicalPlan(sql);
        if(logicalPlan.getType() == LogicalPlan.SQLType.QUERY){
            throw new BlkchnException("BlkchnStatement Batch can only contains insert or call statements !!");
        }
        if (this.batchedArgs == null) {
            this.batchedArgs = new ArrayList<Object>();
        }
        if (sql != null) {
            this.batchedArgs.add(sql);
        }
    }

    @Override
    public void cancel() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearBatch() throws SQLException {
        if (this.batchedArgs != null) {
            this.batchedArgs.clear();
        }
    }

    public List<Object> getBatchedArgs() {
        return this.batchedArgs == null ? null : Collections.unmodifiableList(this.batchedArgs);
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws SQLException {
        realClose();
    }

    private void realClose() throws SQLException {
        if (isClosed)
            return;
        try {
            this.connection = null;
            this.isClosed = true;
            if (queryResultSet != null)
                queryResultSet.close();
            this.queryResultSet = null;
            this.rSetType = 0;
            this.rSetConcurrency = 0;
            clearBatch();
        } catch (Exception e) {
            throw new BlkchnException("Error while closing statement", e);
        }
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        LOGGER.info("Entering into execute Block");
        ResultSet resultSet = executeQuery(sql);
        boolean result = false;
        if (resultSet != null)
            result = true;
        LOGGER.info("Exiting from execute Block with result: " + result);
        return result;
    }

    public ResultSet executeAndReturn(String sql) throws SQLException {
        LOGGER.info("Entering into execute Block");
        ResultSet result = executeQuery(sql);
        LOGGER.info("Exiting from execute Block with result: " + result);
        return result;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return executeBatchInternal();
    }


    protected int[] executeBatchInternal() throws SQLException {
        if (isClosed)
            throw new BlkchnException("No operations allowed after statement closed.");
        connection.verifyConnection();
        if (this.batchedArgs == null || this.batchedArgs.size() == 0) {
            return new int[0];
        }
        try {
            int[] updateCounts = null;

            if (this.batchedArgs != null) {
                int nbrCommands = this.batchedArgs.size();
                updateCounts = new int[nbrCommands];
                for (int i = 0; i < nbrCommands; i++) {
                    updateCounts[i] = -3;
                }
                for (int commandIndex = 0; commandIndex < nbrCommands; commandIndex++)
                    try {
                        String sql = (String) this.batchedArgs.get(commandIndex);
                        updateCounts[commandIndex] = execute(sql) ? 1 : 0;
                    } catch (SQLException | BlkchnException ex) {
                        updateCounts[commandIndex] = EXECUTE_FAILED;
                        LOGGER.warn("Statement : " + batchedArgs.get(commandIndex) + " : "+ex.getMessage());
                    }
            }
            return (updateCounts != null) ? updateCounts : new int[0];
        } finally {
            clearBatch();
        }
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        if (isClosed)
            throw new BlkchnException("No operations allowed after statement closed.");
        LOGGER.info("Entering into executeQuery Block");
        LogicalPlan logicalPlan = getLogicalPlan(sql);
        Object result = null;
        switch (logicalPlan.getType()) {
            case INSERT:
                result = new EthQueryExecutor(logicalPlan, connection.getWeb3jClient(), connection.getInfo())
                    .executeAndReturn();
                queryResultSet = new EthResultSet(result, rSetType, rSetConcurrency);
                LOGGER.info("Exiting from executeQuery Block");
                return queryResultSet;
            case DEPLOY_SMARTCONTRACT:
                result = new EthQueryExecutor(logicalPlan, connection.getWeb3jClient(), connection.getInfo())
                    .executeDeploy();
                queryResultSet = new EthResultSet(result, rSetType, rSetConcurrency);
                LOGGER.info("Exiting from executeQuery Block");
                return queryResultSet;
            case CALL_FUNCTION:
                result = new EthQueryExecutor(logicalPlan, connection.getWeb3jClient(), connection.getInfo())
                    .executeFunction();
                queryResultSet = new EthResultSet(result, rSetType, rSetConcurrency);
                LOGGER.info("Exiting from executeQuery Block");
                return queryResultSet;
            default:
                Table table = logicalPlan.getQuery().getChildType(FromItem.class, 0).getChildType(Table.class, 0);
                String tableName = table.getChildType(IdentifierNode.class, 0).getValue();
                EthQueryExecutor executor = new EthQueryExecutor(logicalPlan, connection.getWeb3jClient(), connection.getInfo());
                if(this.pageRange != null) {
                    executor.paginate(pageRange);
                }
                DataFrame dataframe = executor.executeQuery();
                Map<String, Integer> dataTypeColumnMap = executor.computeDataTypeColumnMap();
                queryResultSet = new EthResultSet(dataframe, rSetType, rSetConcurrency, tableName, dataTypeColumnMap);
                LOGGER.info("Exiting from executeQuery Block");
                return queryResultSet;
        }
    }

    @Override
    public ResultSetMetaData getSchema(String sql) {
        LOGGER.info("Entering into getSchema Block");
        LogicalPlan logicalPlan = getLogicalPlan(sql);
        Table table = logicalPlan.getQuery().getChildType(FromItem.class, 0).getChildType(Table.class, 0);
        String tableName = table.getChildType(IdentifierNode.class, 0).getValue();
        EthPhysicalPlan physicalPlan = new EthPhysicalPlan(logicalPlan);

        Map<String, String> aliasMapping = physicalPlan.getColumnAliasMapping();
        List<List<Object>> data = new ArrayList<List<Object>>();
        Map<String, Integer> dataTypeColumnMap = physicalPlan.getColumnTypeMap(tableName);
        List<String> returnCols = physicalPlan.getColumns(tableName);
        DataFrame dataframe = new DataFrame(data, returnCols, aliasMapping);
        ResultSet queryResultSet = new EthResultSet(dataframe, rSetType, rSetConcurrency, tableName, dataTypeColumnMap);
        try {
            return queryResultSet.getMetaData();
        } catch (SQLException e) {
            throw new BlkchnException("Exception while getSchema "+e.getMessage());
        }
    }

    public Number getBlockHeight() {
        if(connection.isClosed)
            throw new BlkchnException("Eth Connection is closed");
        else{
            BigInteger blockHeight  = null;
            try{
                blockHeight = connection.getWeb3jClient().ethBlockNumber().send().getBlockNumber();
            }catch(IOException e){
                throw new BlkchnException(e);
            }
            return blockHeight;
        }
    }

    @Override
    public RangeNode getProbableRange(String sql){
        if (isClosed)
            throw new BlkchnException("No operations allowed after statement closed.");
        LOGGER.info("Entering into executeQuery Block");
        LogicalPlan logicalPlan = getLogicalPlan(sql);
        return new EthQueryExecutor(logicalPlan, connection.getWeb3jClient(), connection.getInfo()).getProbableRange();
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxRows() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return queryResultSet;
        // throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.isClosed;
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    private List<TransactionResult> getTransactions(String blockNumber) throws IOException {
        LOGGER.info("Getting details of transactions stored in block - " + blockNumber);
        EthBlock block = connection.getWeb3jClient()
            .ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger(blockNumber)), true).send();

        return block.getBlock().getTransactions();
    }

    private Block getBlock(String blockNumber) throws IOException {
        LOGGER.info("Getting block - " + blockNumber + " Information ");
        EthBlock block = connection.getWeb3jClient()
            .ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger(blockNumber)), true).send();
        return block.getBlock();
    }

    private Block getBlockByHash(String blockHash) throws IOException {
        LOGGER.info("Getting  information of block with hash - " + blockHash);
        EthBlock block = connection.getWeb3jClient().ethGetBlockByHash(blockHash, true).send();
        return block.getBlock();
    }

    private Transaction getTransactionByHash(String transactionHash) throws IOException {
        LOGGER.info("Getting information of Transaction by hash - " + transactionHash);

        Transaction transaction =
            connection.getWeb3jClient().ethGetTransactionByHash(transactionHash).send().getResult();
        return transaction;
    }

    private Transaction getTransactionByBlockHashAndIndex(String blockHash, BigInteger transactionIndex)
        throws IOException {
        LOGGER.info("Getting information of Transaction by blockhash - " + blockHash + " and transactionIndex"
            + transactionIndex);

        Transaction transaction = connection.getWeb3jClient()
            .ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send().getResult();
        return transaction;
    }

    public LogicalPlan getLogicalPlan(String sqlText) {
        LogicalPlan logicalPlan = null;
        BlkchnSqlParser parser = getParser(sqlText);
        parser.removeErrorListeners();
        parser.addErrorListener(BlkchnErrorListener.INSTANCE);
        AbstractSyntaxTreeVisitor astBuilder = new BlockchainVisitor();
        logicalPlan = (LogicalPlan) astBuilder.visitSingleStatement(parser.singleStatement());
        return logicalPlan;
    }

    public BlkchnSqlParser getParser(String sqlText) {
        BlkchnSqlLexer lexer = new BlkchnSqlLexer(new CaseInsensitiveCharStream(sqlText));
        lexer.removeErrorListeners();
        lexer.addErrorListener(BlkchnErrorListener.INSTANCE);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BlkchnSqlParser parser = new BlkchnSqlParser(tokens);
        return parser;
    }

    @Override
    public void setPageRange(RangeNode<?> pageRange) {
       this.pageRange = pageRange;
    }
}
