public class RActingHeadInTeam extends RNormalInTeam
{
	LeaveRecord LR;		// leave record of the head
	Employee e;
	
	RActingHeadInTeam(Team bt, LeaveRecord leave, Employee emp)
	{
		super(bt);
		LR = leave;
		e = emp;
	}
	
	@Override
	public String getDescriptionForTeam(Employee e)
	{
		return LR + ": " + e.getName();
	}
	
	public String getActingDescription(Employee e)
	{
		return LR + ": " + e.getName();
	}
	
	public LeaveRecord getLeaveRecord()
	{
		return LR;
	}
	
	public Employee getPerson()
	{
		return e;
	}
}
