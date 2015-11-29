package collector;

public class Application 
{
	public static void main(String[] args)
	{
		TweetGetTask[] tasks = new TweetGetTask[4];
		for(int i = 0; i < 4; i++)
		{
			tasks[i] = new TweetGetTask(i);
			tasks[i].start();
			try 
			{
				Thread.sleep(7000);
			} 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
}
