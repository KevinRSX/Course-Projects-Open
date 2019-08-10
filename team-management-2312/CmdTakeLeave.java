import java.util.ArrayList;
import java.util.Arrays;

public class CmdTakeLeave extends RecordedCommand
{
	private LeaveRecord LR;
	private Employee e;
	private ArrayList<RActingHeadInTeam> actingRoles;
	
	public void execute(String[] cmdParts)
	{
		try
		{
			if (cmdParts.length < 4)
			{
				throw new ExInsufficientArgument();
			}
			Company company = Company.getInstance();
			e = company.findEmployee(cmdParts[1]);	//may throw ExEmployeeNotFound
			LR = new LeaveRecord(cmdParts[2], cmdParts[3]);
			
			if (cmdParts.length == 4)
			{
				actingRoles = e.addLeaveRecord(cmdParts[2], cmdParts[3], null);	//may throw the last four exceptions
			}
			else
			{
				actingRoles = e.addLeaveRecord(cmdParts[2], cmdParts[3], Arrays.copyOfRange(cmdParts, 4, cmdParts.length));
			}
			
			for (RActingHeadInTeam r : actingRoles)
			{
				r.getBelongTeam().addActingHead(r);
			}
			
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");			
		}
		catch (ExInsufficientArgument e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExEmployeeNotFound e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExDateStartLaterThanEnd e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExOverlappedLeaves e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExInsufficientBalance e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExDateHasAlreadyPassed e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExTeamNotFound e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExActingHeadNotDesignated e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExNotLeadingRequiredTeam e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void undoMe()
	{
		e.removeLeaveRecord(LR);
		for (RActingHeadInTeam r : actingRoles)
		{
			r.getPerson().removeRole(r);
			r.getBelongTeam().removeActingHead(r);
		}
		addRedoCommand(this);
	}
	
	public void redoMe()
	{
		e.addLeaveRecord(LR);
		for (RActingHeadInTeam r : actingRoles)
		{
			r.getPerson().addRole(r);
			r.getBelongTeam().addActingHead(r);
		}
		addUndoCommand(this);
	}
}
