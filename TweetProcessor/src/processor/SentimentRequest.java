package processor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class SentimentRequest
{
	public static String getSentiment(String str) throws UnsupportedEncodingException
	{
		String key = "";
		String encod = URLEncoder.encode(str, "UTF-8");
		String sentiment = HttpRequest.get("http://access.alchemyapi.com/calls/text/TextGetTextSentiment?apikey="
		+ key +"&text=" + encod + "&outputMode=json").body();

		if(sentiment.indexOf("ERROR") == -1)
		{
			int position = sentiment.indexOf("type");
			String type = sentiment.substring(position + 8, position + 16);
			if(type.charAt(7) == '"')
				type = type.substring(0, 7);
			return type;
		}
		else
			return "error";
	}
}
