public class SystemDate extends Day
{
	private static SystemDate instance;
	
	//private constructor for singleton
	private SystemDate(String sDay)
	{
		super(sDay);
	}
	
	public static SystemDate getInstance()
	{
		return instance;
	}
	
	public static void createTheInstance(String sDay)
	{
		if (instance == null)	//making sure only one instance can be created
		{
			instance = new SystemDate(sDay);
		}
		else
			System.out.println("Cannot create one more system data instance.");
	}
}
