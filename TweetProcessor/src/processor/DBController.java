package processor;

import java.io.UnsupportedEncodingException;
import java.sql.*;


public class DBController
{
	// set mySQL DB configurations
	static final String USER = "";
	static final String PASS = "";
	static final String url = "gotogether.c1x3yqbfdy6z.us-east-1.rds.amazonaws.com:3306/GoTogether";
	static final String DB_URL = "jdbc:mysql://" + url;

    // set the format of the table
	static final String condition = "WHERE statusId = ?";
	static final String selectSQL = "SELECT content from tweet " + condition;
	static final String updateSQL = "UPDATE tweet SET sentiment = ?" + condition;

	// JDBC connection and statement
    Connection conn;
    PreparedStatement stmt;

    public void setConnnection()
    {
    	try
        {
        	System.out.println("configure JDBC driver");
        	Class.forName("com.mysql.jdbc.Driver");
        	conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        catch(Exception e){e.printStackTrace();}
    }

    public void closeConnection()
    {
    	try
    	{
    		stmt.close();
    		conn.close();
    	}catch(SQLException se){se.printStackTrace();}
    }

    public void updataById(String statusId)
    {
    	try
    	{
    		PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
    		selectStmt.setString(1, statusId);
    		ResultSet rs = selectStmt.executeQuery();
    		String content = "";
		  	while(rs.next())
		  		content = rs.getString("content");
		  	String type = SentimentRequest.getSentiment(content);
		  	rs.close();
		  	PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
		  	updateStmt.setString(1, type);
		  	updateStmt.setString(2, statusId);
		  	updateStmt.executeUpdate();
    	}
    	catch(SQLException e){e.printStackTrace();}
    	catch(UnsupportedEncodingException e){e.printStackTrace();}
    }

}
