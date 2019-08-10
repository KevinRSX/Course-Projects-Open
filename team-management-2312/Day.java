public class Day implements Cloneable, Comparable<Day> {
	
	private int year;
	private int month;
	private int day;
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
	
	//Constructors
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}
	
	public Day(String sDay)
	{
		set(sDay);
	}
	
	public void set(String sDay) //Set year,month,day based on a string like 01-Jan-2018
	{		
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]); //Apply Integer.parseInt for sDayParts[0];
		this.year = Integer.parseInt(sDayParts[2]); 
		this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
	}
	
	public Day next()
	{
		int y = 0, m = 0, d = 0;
		if (month  == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10)
		{
			y = year;
			if (day == 31)
			{
				m = month + 1;
				d = 1;
			}
			else
			{
				m = month;
				d = day + 1;
			}
		}
		else if (month == 4 || month == 6 || month == 9 || month == 11)
		{
			y = year;
			if (day == 31)
			{
				m = month + 1;
				d = 1;
			}
			else
			{
				m = month;
				d = day + 1;
			}
		}
		else if (month == 2)
		{
			y = year;
			if (Day.isLeapYear(year))
			{
				if (day == 29)
				{
					m = month + 1;
					d = 1;
				}
				else
				{
					m = month;
					d = day + 1;
				}
			}
			else
			{
				if (day == 28)
				{
					m = month + 1;
					d = 1;
				}
				else
				{
					m = month;
					d = day + 1;
				}
			}
		}
		else	//month == 12
		{
			if (day == 31)
			{
				y = year + 1;
				m = 1;
				d = 1;
			}
			else
			{
				y = year;
				m = month;
				d = day + 1;
			}
		}
		return new Day(y, m, d);
	}
	
	// check if a given year is a leap year
	static public boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	// Return a string for the day like dd MMM yyyy
	@Override
	public String toString() {
		
		return day+"-"+ MonthNames.substring((month - 1) * 3, month * 3) + "-"+ year; // (month-1)*3,(month)*3
	}
	
	public Integer toIntFormat()
	{
		return year * 10000 + month * 100 + day;
	}
	
	@Override
	public Day clone() 
	{
		Day copy = null;
		try 
		{
			copy = (Day)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			System.out.println("Clone Not Supported!");
		}
		return copy;
	}
	
	@Override
	public int compareTo(Day another)
	{
		return this.toIntFormat().compareTo(another.toIntFormat());
	}
}