public class CmdListLeaves implements Command
{
	public void execute(String[] cmdParts)
	{
		try
		{
			Company company = Company.getInstance();
			if (cmdParts.length == 1)
			{
				company.listAllLeaves();
			}
			else
			{
				company.findEmployee(cmdParts[1]).listLeaves();
			}
		}
		catch (ExEmployeeNotFound e)
		{
			System.out.println(e.getMessage());
		}
	}
}
