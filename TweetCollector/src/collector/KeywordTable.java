package collector;

public class KeywordTable 
{
	static final String[] kMusic = {"music", "concert", "jazz", "guitar", "piano", 
								"rhythm", "rock"};
	
	static final String[] kSports = {"sports", "basketball", "football", 
								"soccer", "sports", "baseball", "running", "swimming"};
	
	static final String[] kTech = {"tech", "android", "java", "geek",
								"software", "google", "microsoft", "apple"};
	
	static final String[] kFood = {"food", "pizza", "fruit", "noodle", "burger"};
	
	public static String[] getKeywordSet(int typeIndex)
	{
		switch(typeIndex)
		{
			case 0: return kMusic;
			case 1: return kSports;
			case 2: return kTech;
			case 3: return kFood;
		}
		return kMusic;
	}
}
