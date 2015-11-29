package backend;

public class GeoInfor
{
	double latitude;
	double longitude;
	String sentiment;
	String statusId;
	
	public GeoInfor(double la, double lo, String sentiment, String statusId)
	{
		latitude = la;
		longitude = lo;
		this.sentiment = sentiment;
		this.statusId = statusId;
	}
}
