package com.impetus.eth.jdbc;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.Transaction;

public class EthStatement implements Statement {
  
	protected EthConnection connection;
	protected int rSetType;
	protected int rSetConcurrency;
	
	
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
	
	public EthStatement(EthConnection connection, int rSetType,
			int rSetConcurrency) {
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void cancel() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearBatch() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int[] executeBatch() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		
		ResultSet queryResultSet=null;
		try {
		System.out.println("In execute Query Block ");
		System.out.println("Preparing to execute Query : "+sql);
		
		connection.getWeb3jClient().web3ClientVersion().observable().subscribe(x -> {
		    String clientVersion = x.getWeb3ClientVersion();
		    System.out.println("web3j Client Version: " + clientVersion);
		});
		
		
	    List<TransactionResult> trans = getTransactions("1876545");
	   	TransactionResultDataHandler dataHandler = new TransactionResultDataHandler();
		queryResultSet = new EthResultSet(dataHandler.convertToObjArray(trans), dataHandler.getColumnNamesMap(),rSetType,rSetConcurrency,dataHandler.getTableName());
		
		
		/*
		Block blk= getBlock("1876545");
		List<Block> blkl= new ArrayList<Block>();
		blkl.add(blk);
		BlockResultDataHandler blockDataHandler= new BlockResultDataHandler();
		queryResultSet= new EthResultSet(blockDataHandler.convertToObjArray(blkl), BlockResultDataHandler.getColumnNamesMap(),rSetType,rSetConcurrency,blockDataHandler.getTableName());
 		*/
		
		/*
		Block blkByHash= getBlockByHash("0xb76e315ce8a2531e910b2c55d975579d1b05b451eed46947d98278f18f22d25d");
		List<Block> lblkByHash= new ArrayList<Block>();
		blkl.add(blkByHash);
		BlockResultDataHandler blkByHashDataHandler= new BlockResultDataHandler();
		queryResultSet= new EthResultSet(blkByHashDataHandler.convertToObjArray(blkl), BlockResultDataHandler.getColumnNamesMap(),rSetType,rSetConcurrency,blkByHashDataHandler.getTableName());
			
		 
		 Transaction transByHash= getTransactionByHash("0xe74e6119caa2f92ca50fee714c8ded39385c1ede944aad60dbf3a2cfc69d5b23");
		 List<Transaction> lTransByHash= new ArrayList<Transaction>();
		 lTransByHash.add(transByHash);
		 TransactionResultDataHandler transByHashdataHandler = new TransactionResultDataHandler();
	     queryResultSet = new EthResultSet(transByHashdataHandler.convertToObjArray(lTransByHash), transByHashdataHandler.getColumnNamesMap(),rSetType,rSetConcurrency,transByHashdataHandler.getTableName());
		 */
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return queryResultSet;
	}
	
	@Override
	public int executeUpdate(String sql) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
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
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new UnsupportedOperationException();}

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
		EthBlock block = connection.getWeb3jClient().ethGetBlockByNumber(
				DefaultBlockParameter.valueOf(new BigInteger(blockNumber)),
				true).send();
		
		return block.getBlock().getTransactions();
	}

	private Block getBlock(String blockNumber) throws IOException {
		EthBlock block = connection.getWeb3jClient().ethGetBlockByNumber(
				DefaultBlockParameter.valueOf(new BigInteger(blockNumber)),
				true).send();
		return block.getBlock();
	}
  
	private Block getBlockByHash(String blockHash) throws IOException {
		EthBlock block = connection.getWeb3jClient().ethGetBlockByHash(blockHash, true).send();
		return block.getBlock();
	}
	
	private Transaction getTransactionByHash(String transactionHash) throws IOException {
		Transaction transaction = connection.getWeb3jClient().ethGetTransactionByHash(transactionHash).send().getResult();
		return transaction;
	}

	private Transaction getTransactionByBlockHashAndIndex(String blockHash,BigInteger transactionIndex) throws IOException {
		Transaction transaction = connection.getWeb3jClient().ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send().getResult();
		return transaction;
	}


}
