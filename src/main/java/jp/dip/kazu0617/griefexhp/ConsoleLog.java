package jp.dip.kazu0617.griefexhp;

import java.util.logging.Logger;

public class ConsoleLog
{
	@SuppressWarnings("unused")
	private GriefEXHP plugin;
	public Logger log = Logger.getLogger("Minecraft");
	public String cPrefix = "[GriefEXHP] ";
	public ConsoleLog(GriefEXHP plugin)
	{
		this.plugin = plugin;
	}
	public void info(String Mess)
	{
		log.info(this.cPrefix + Mess);
	}
	public void warn(String Mess)
	{
		log.warning(this.cPrefix + Mess);
	}
}
