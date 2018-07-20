package com.impetus.blkchn.eth;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.WindowsIpcService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.logging.LogManager;


public class BenchMarkEthDriver {

    static final int min = 1000;
    static final int max = 3000000;
    static final int[] blockSize = {1, 100, 200, 500, 1000, 3000, 20000};
    static final int numIter = 3;
    static Connection conn = null;
    static Random randomNum = null;
    static String ipPort = "127.0.0.1:8545";
    static String httpUrl = "http://"+ipPort;
    static String url = "jdbc:blkchn:ethereum://"+ipPort;
    static Web3j web3jClient = null;


    public static void main(String args[]) throws Exception {
        web3jClient = Web3j.build(new HttpService(httpUrl));
        String driverClass = "com.impetus.eth.jdbc.EthDriver";

        PrintStream out = new PrintStream(new FileOutputStream("Benchmark.txt"));
        System.setOut(out);

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, null);
        randomNum = new Random();
        String format = "|%1$-30s|%2$-30s|%3$-30s|\n";

        for (int ittr = 0; ittr < numIter; ittr++) {
            List<HashMap> queryList = getQueryList();
            for (Object qmap : queryList) {
                System.out.println("\n\n");
                for (Object qtype : ((Map) qmap).keySet()) {
                    System.out.println("\n-"+qtype);
                    System.out.println("    -Iteration " + (ittr + 1));
                    int i = 0;
                    System.out.format(format, "Block Range", "Time By Driver(ms)", "Time By WEB3j(ms)");
                    for (Object query : (List) ((HashMap) qmap).get((String) qtype)) {
                        String driverTime = String.valueOf(runQuery((String) query));
                        String web3jTime = String.valueOf(gtTimeWithWeb3J(blockSize[i]));
                        System.out.format(format,blockSize[i],driverTime,web3jTime);
                        i++;
                    }
                    System.out.println("==============================================================================================\n\n");
                }
            }
        }
    }

    public static List<HashMap> getQueryList() {
        List queryList = new ArrayList<HashMap>();
        Map queryMap = new HashMap<String, ArrayList>();
        String q1 = "select * from block where blocknumber = " + getRandomNumber();//1
        queryMap.put("Simple Query", new ArrayList<String>());
        ((List) queryMap.get("Simple Query")).add(q1);
        for (int i : blockSize) {
            if (i == 1) continue;
            blockRange blnum = getRandomRange(i);
            ((List) queryMap.get("Simple Query")).add("select * from block where blocknumber > " + blnum.rmin + " and blocknumber < " + blnum.rmax);
        }
        String qO1 = "select * from block where blocknumber = " + getRandomNumber() + "  order by hash";//1
        queryMap.put("Order By Query", new ArrayList<String>());
        ((List) queryMap.get("Order By Query")).add(qO1);
        for (int i : blockSize) {
            if (i == 1) continue;
            blockRange blnum = getRandomRange(i);
            ((List) queryMap.get("Order By Query")).add("select * from block where blocknumber > " + blnum.rmin + " and blocknumber < " + blnum.rmax + " order by hash");
        }

        String qG1 = "select count(*) as cnt, blocknumber  from transaction where blocknumber = " + getRandomNumber() + "  group by blocknumber";//1
        queryMap.put("Group By Query", new ArrayList<String>());
        ((List) queryMap.get("Group By Query")).add(qG1);
        for (int i : blockSize) {
            if (i == 1) continue;
            blockRange blnum = getRandomRange(i);
            ((List) queryMap.get("Group By Query")).add("select count(*) as cnt, blocknumber from transaction where " +
                    "blocknumber > " + blnum.rmin + " and blocknumber < " + blnum.rmax + " group by blocknumber");
        }
        queryList.add(queryMap);
        return queryList;
    }

    public static blockRange getRandomRange(int n) {
        int rmin = getRandomNumber();
        return (new blockRange(rmin, rmin + n));
    }

    public static int getRandomNumber() {
        return min + randomNum.nextInt(max);
    }

    public static long runQuery(String q) {
        try {
            Statement stmt = conn.createStatement();
            Long start = System.currentTimeMillis();
            ResultSet rs1 = stmt.executeQuery(q);
            Long end = System.currentTimeMillis();
            while (rs1.next()) {
                break;
            }
            Long qt = (end - start);
            return qt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static long gtTimeWithWeb3J(int range){
        blockRange blnum = getRandomRange(range);
        int minBlock = blnum.rmin;
        int maxBlock = blnum.rmax;
        Long start = System.currentTimeMillis();
        try {
            BigInteger blockHeight = web3jClient.ethBlockNumber().send().getBlockNumber();
            for (int i = minBlock; i <= maxBlock; i++) {
                EthBlock block = web3jClient.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger(i+"")), true).send();
                EthBlock.Block blockInfo = block.getBlock();
                BigInteger blocknumber = blockInfo.getNumber();
                String hash = blockInfo.getHash();
                String parenthash = blockInfo.getParentHash();
                BigInteger nonce = blockInfo.getNonce();
                String sha3uncles = blockInfo.getSha3Uncles();
                String logsbloom = blockInfo.getLogsBloom();
                String transactionsroot = blockInfo.getTransactionsRoot();
                String stateroot = blockInfo.getStateRoot();
                String receiptsroot = blockInfo.getReceiptsRoot();
                String author = blockInfo.getAuthor();
                String miner = blockInfo.getMiner();
                String mixhash = blockInfo.getMixHash();
                BigInteger totaldifficulty = blockInfo.getTotalDifficulty();
                String extradata = blockInfo.getExtraData();
                BigInteger size = blockInfo.getSize();
                BigInteger gaslimit = blockInfo.getGasLimit();
                BigInteger gasused = blockInfo.getGasUsed();
                BigInteger timestamp = blockInfo.getTimestamp();
                List<EthBlock.TransactionResult> transactions = blockInfo.getTransactions();
                List<String> uncles = blockInfo.getUncles();
                List<String> sealfields = blockInfo.getSealFields();
            }
            Long end = System.currentTimeMillis();
            return (end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

class blockRange {
    public int rmin;
    public int rmax;
    public blockRange(int rmin, int rmax) {
        this.rmin = rmin;
        this.rmax = rmax;
    }
}
