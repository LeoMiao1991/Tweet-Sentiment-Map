package backend;

import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class DBController
{
	// set mySQL DB configurations
	static final String USER = "";
	static final String PASS = "";
	static final String url = "gotogether.c1x3yqbfdy6z.us-east-1.rds.amazonaws.com:3306/GoTogether";
	static final String DB_URL = "jdbc:mysql://" + url;

    // set the format of the table
	static final String condition = "WHERE createdTime > ? AND keyword = ?";
	static final String selectSQL = "SELECT latitude, longitude, sentiment, statusId from tweet " + condition;
	static final String deleteSQL = "DELETE FROM tweet ORDER BY statusId LIMIT 100";

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


    public void updateTable()
    {
    	try
    	{
    		PreparedStatement updateStmt = conn.prepareStatement("SELECT COUNT(1) FROM tweet");
		ResultSet resultSet = updateStmt.executeQuery();
		resultSet.next();
		int counter = resultSet.getInt(1);
		if(counter > 2000)
			deleteEntry();
		resultSet.close();
	} catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteEntry()
    {
    	try
    	{
		PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
		deleteStmt.executeUpdate();
	} catch (SQLException e) { e.printStackTrace(); }
    }


    public List<GeoInfor> getGeoInfor(String message)
    {
    	String[] para = message.split("\\s");
    	List<GeoInfor> ans = new ArrayList<GeoInfor>();
    	if(para.length < 2)
    		return ans;
    	String timeslot = para[0], type = para[1];
    	int range = Integer.parseInt(timeslot) * 60;
    	try
    	{
    		Calendar curTime = Calendar.getInstance(TimeZone.getTimeZone("EST"));
  	  	curTime.add(Calendar.SECOND, 0 - range);
 	  	java.sql.Timestamp startTime = uDateToSDate(curTime.getTime());
 	  	PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
  	  	List<String> types = new ArrayList<>();
  	  	if(type.equals("all"))
  	  	{
  	  		types.add("tech");
  	  		types.add("music");
  	  		types.add("food");
  	  		types.add("sports");
  	  	}
  	  	else
  	  		types.add(type);
  	  	for(String eleType : types)
  	  	{
  	  		selectStmt.setTimestamp(1, startTime);
  	  		selectStmt.setString(2, eleType);
  	  		ResultSet rs = selectStmt.executeQuery();
  	  		while(rs.next())
  	  		{
  	  			double latitude = rs.getDouble("latitude");
  	  			double longitude = rs.getDouble("longitude");
  	  			String statusID = rs.getString("statusId");
  	  			String sentiment = rs.getString("sentiment");
  	  			GeoInfor tmp = new GeoInfor(latitude, longitude, sentiment, statusID);
  	  			ans.add(tmp);
  	  		}
  	  		rs.close();
  	  	}
    	}catch(SQLException e){e.printStackTrace();}
    	return ans;
    }

    public static java.sql.Timestamp uDateToSDate(java.util.Date uDate)
    {
    	java.sql.Timestamp sDate = new java.sql.Timestamp(uDate.getTime());
    	return sDate;
    }
}
