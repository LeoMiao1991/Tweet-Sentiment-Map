package collector;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetGetTask extends Thread
{
	DBController controller;
	String keyword;
	String[] keywordSet;
	int type;

	private AmazonSQS sqs;
	private String queueURL;

	public TweetGetTask(int typeIndex)
	{
		controller = new DBController();
		controller.setConnnection();
		keywordSet = KeywordTable.getKeywordSet(typeIndex);
		keyword = keywordSet[0];
		type = typeIndex;

		AWSCredentials credentials = null;
		try { credentials = new PropertiesCredentials(TweetGetTask.class.getResourceAsStream("AwsCredentials.properties")); }
		catch (Exception e) { e.printStackTrace(); }

		sqs = new AmazonSQSClient(credentials);
		Region myRegion = Region.getRegion(Regions.US_EAST_1);
        sqs.setRegion(myRegion);

        for (String queueUrl : sqs.listQueues().getQueueUrls())
        	queueURL = queueUrl;
        System.out.println("queue URL is: " + queueURL);

		System.out.println("task for " + keyword + " initialized");
	}

	public void run()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		if(type % 2 == 0)
		{
			cb.setDebugEnabled(true)
	           .setOAuthConsumerKey("")
	           .setOAuthConsumerSecret("")
	           .setOAuthAccessToken("")
	           .setOAuthAccessTokenSecret("");
		}
		else
		{
			cb.setDebugEnabled(true)
	           .setOAuthConsumerKey("")
	           .setOAuthConsumerSecret("")
	           .setOAuthAccessToken("")
	           .setOAuthAccessTokenSecret("");
		}

        if(queueURL == null)
        	return;

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

		StatusListener listener = new StatusListener()
		{
			@Override
			public void onStatus(Status status)
			{
				GeoLocation location = status.getGeoLocation();
				if (location == null)
					return;

				System.out.println("Received a new tweet on topic: " + keyword);
				controller.writeToDB(status, keyword);
				sqs.sendMessage(new SendMessageRequest(queueURL, "" + status.getId()));
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {}

			@Override
			public void onStallWarning(StallWarning warning) {}

			@Override
			public void onException(Exception ex)
			{
				ex.printStackTrace();
			}
		};

		FilterQuery tweetFilter = new FilterQuery();
        tweetFilter.track(keywordSet);
        twitterStream.addListener(listener);
        twitterStream.filter(tweetFilter);
	}
}
