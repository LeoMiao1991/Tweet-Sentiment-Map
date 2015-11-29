package collector;

public class GeoInfo
{
	double latitude;
	double longitude;
	String id;
	
	public GeoInfo(double la, double lo, String id)
	{
		latitude = la;
		longitude = lo;
		this.id = id;
	}
}
