package processor;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class TweetReceiver extends Thread
{
	private static final String SNSArn = "arn:aws:sns:us-east-1:811553136737:Tweet";
	
	private static String queueURL;
	private DBController controller;
	private AmazonSQS sqs;
	private AmazonSNSClient sns;
	
	public TweetReceiver()
	{
		AWSCredentials credentials = null;
		try { credentials = new PropertiesCredentials(TweetReceiver.class.getResourceAsStream("AwsCredentials.properties")); } 
		catch (Exception e) { e.printStackTrace(); }
		
		sqs = new AmazonSQSClient(credentials);
		Region myRegion = Region.getRegion(Regions.US_EAST_1);
        sqs.setRegion(myRegion);
        
        sns = new AmazonSNSClient(credentials);
        sns.setRegion(myRegion);

        for (String queueUrl : sqs.listQueues().getQueueUrls())
        	queueURL = queueUrl;
        System.out.println("Queue URL is: " + queueURL);
        
        controller = new DBController();
        controller.setConnnection();
	}
	
	public void run()
	{
        if(queueURL == null)
        	return;
        
        while(true)
        {
        	ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueURL);
            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
            for(Message message : messages) 
            {
            	String messageRecieptHandle = message.getReceiptHandle();
            	String statusId = message.getBody();
            	
            	controller.updataById(statusId);
            	
            	PublishRequest publishRequest = new PublishRequest(SNSArn, statusId);
        		sns.publish(publishRequest);
        		
        		//print MessageId of message published to SNS topic
                
                sqs.deleteMessage(new DeleteMessageRequest(queueURL, messageRecieptHandle));
            }
            try 
			{
				Thread.sleep(200);
			} 
			catch (InterruptedException e) { e.printStackTrace(); }
        }
	}
}
