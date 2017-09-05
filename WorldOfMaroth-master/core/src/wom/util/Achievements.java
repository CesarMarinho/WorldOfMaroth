package wom.util;

public class Achievements {
	
	public static Achievements instance = new Achievements();
	
	private boolean[] achievements;

	
	private Achievements()
	{		
	}
	
	public void init()
	{
		setAchievements(new boolean[4]);
		
		for(int i = 0; i < 4; ++i)
		{
			achievements[i] = false;
		}	
	}

	public boolean[] getAchievements() 
	{
		return achievements;
	}

	public void setAchievements(boolean[] achievements) 
	{
		this.achievements = achievements;
	}
	
}
