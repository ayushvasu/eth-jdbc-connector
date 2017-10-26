package com.impetus.eth.general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;
import org.web3j.utils.Numeric;

public class EthDriverTest {
public static void main(String[] args) throws ClassNotFoundException {

	 String url="jdbc:blkchn:ethereum://172.25.41.52:8545";
	 String driverClass="com.impetus.eth.jdbc.EthDriver";
	 try {
		Class.forName(driverClass);
		
		System.out.println(DriverManager.getDriver(url).getMajorVersion());
		
		Connection conn= DriverManager.getConnection(url, null);
		System.out.println("Connected to Ethereum\n*********\n");
		
		Statement stmt=conn.createStatement();
		System.out.println("Statement Created\n*********\n");
		
		ResultSet rs=stmt.executeQuery("SAMPLE_QUERY");
		System.out.println(rs.getMetaData().getColumnCount());
		while(rs.next())
		{
			//For Transactions
			
			  System.out.println(""+rs.getString(3));
			  System.out.println(""+rs.getString("from"));
			  System.out.println();
		}
		System.out.println("Result set MetaData");
		
		ResultSetMetaData rsMetaData= rs.getMetaData();
		System.out.println("Total Columns : "+rsMetaData.getColumnCount());
		System.out.println("column label name : "+rsMetaData.getColumnLabel(3));
		System.out.println("column Name : "+rsMetaData.getColumnName(3));
		System.out.println("tableName : "+rsMetaData.getTableName(3));
	} catch (SQLException e1) {
		e1.printStackTrace();
	}
	
	}
}
