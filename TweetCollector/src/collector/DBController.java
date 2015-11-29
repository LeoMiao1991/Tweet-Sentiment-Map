package collector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import twitter4j.GeoLocation;
import twitter4j.Status;

public class DBController
{

	// set mySQL DB configurations
	static final String USER = "";
	static final String PASS = "";
	static final String url = "gotogether.c1x3yqbfdy6z.us-east-1.rds.amazonaws.com:3306/GoTogether";
	static final String DB_URL = "jdbc:mysql://" + url;

	// set the format of the table
	static final String tableField = "(statusId, userName, latitude, longitude, createdTime, content, keyword, sentiment)";
	static final String valueField = "(?, ?, ?, ?, ?, ?, ?, ?)";
	static final String insertSQL = "INSERT INTO tweet " + tableField + " VALUES " + valueField;

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
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void closeConnection()
	{
		try
		{
			stmt.close();
			conn.close();
		} catch (SQLException se) { se.printStackTrace(); }
	}

	public void writeToDB(Status status, String keyword)
	{
		GeoLocation location = status.getGeoLocation();
		String statusID = "" + status.getId();
		String name = status.getUser().getScreenName();
		String text = status.getText();
		if (text.length() > 255)
			text = text.substring(0, 254);
		Timestamp timestamp = uDateToSDate(status.getCreatedAt());
		try
		{
			stmt = conn.prepareStatement(insertSQL);
			stmt.setString(1, statusID);
			stmt.setString(2, name);
			stmt.setDouble(3, location.getLatitude());
			stmt.setDouble(4, location.getLongitude());
			stmt.setTimestamp(5, timestamp);
			stmt.setString(6, text);
			stmt.setString(7, keyword);
			stmt.setString(8, "empty");
			stmt.executeUpdate();
		}
		catch (SQLException se) { se.printStackTrace(); }
	}

	public static java.sql.Timestamp uDateToSDate(java.util.Date uDate)
	{
		java.sql.Timestamp sDate = new java.sql.Timestamp(uDate.getTime());
		return sDate;
	}
}
