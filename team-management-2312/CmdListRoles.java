public class CmdListRoles implements Command
{
	@Override
	public void execute(String[] cmdParts)
	{
		try
		{
			if (cmdParts.length < 2)
				throw new ExInsufficientArgument();
			Company company = Company.getInstance();
			Employee e = company.findEmployee(cmdParts[1]);
			e.listRoles();
		}
		catch (ExEmployeeNotFound e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExInsufficientArgument e)
		{
			System.out.println(e.getMessage());
		}
	}
	
}
