public class LeaveRecord implements Comparable<LeaveRecord>
{
	Day startDate;
	Day endDate;
	int length;
	
	public LeaveRecord(String sStartDate, String sEndDate) throws ExDateStartLaterThanEnd
	{
		startDate = new Day(sStartDate);
		endDate = new Day(sEndDate);
		if (startDate.compareTo(endDate) > 0)
		{
			throw new ExDateStartLaterThanEnd();
		}
		int lenCount = 1;
		Day temp = startDate.clone();
		while (temp.compareTo(endDate) < 0)
		{
			temp = temp.next();
			lenCount++;
		}
		length = lenCount;
	}
	
	int getLength()
	{
		return length;
	}
	
	Day getStartDate()
	{
		return startDate;
	}
	
	Day getEndDate()
	{
		return endDate;
	}
	
	@Override
	public int compareTo(LeaveRecord another)
	{
		return this.startDate.compareTo(another.startDate);
	}
	
	@Override
	public String toString()
	{
		return startDate + " to " + endDate;
	}
	
	@Override
	public boolean equals(Object another)
	{
		if (this == another)
			return true;
		if (another == null)
			return false;
		if (getClass() != another.getClass())
			return false;
		if (this.toString().equals(another.toString()))
			return true;
		else
			return false;
	}
}
