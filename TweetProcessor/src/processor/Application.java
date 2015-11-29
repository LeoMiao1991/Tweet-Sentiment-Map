package processor;


public class Application 
{
	public static final int numberOfWorkers = 2;
	//private static final String SNSArn = "arn:aws:sns:us-east-1:811553136737:Tweet";
	
	public static void main(String[] args)
	{
		/*
		AWSCredentials credentials = null;
		try { credentials = new PropertiesCredentials(TweetReceiver.class.getResourceAsStream("AwsCredentials.properties")); } 
		catch (Exception e) { e.printStackTrace(); }
		
		
		Region myRegion = Region.getRegion(Regions.US_EAST_1);
		AmazonSNSClient sns = new AmazonSNSClient(credentials);
        sns.setRegion(myRegion);
		
		SubscribeRequest subRequest = new SubscribeRequest(SNSArn, "HTTP", "http://129.236.234.110:8080/Cloud-HW2/BackendServlet");
		sns.subscribe(subRequest);
		System.out.println("SubscribeRequest - " + sns.getCachedResponseMetadata(subRequest));
		System.out.println("Check your email and confirm subscription.");*/
		
		
		TweetReceiver[] tasks = new TweetReceiver[numberOfWorkers];
		for(int i = 0; i < numberOfWorkers; i++)
		{
			tasks[i] = new TweetReceiver();
			tasks[i].start();
			try 
			{
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		
	}
}
