package jp.dip.kazu0617.griefexhp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author kazu0617
 * GriefEXHPのメインソースです。
 */
public class GriefEXHP extends JavaPlugin implements Listener
{
	String Pluginprefix = "[" + ChatColor.GREEN + getDescription().getName() + ChatColor.RESET +"] ";
	String Pluginname = "[" + getDescription().getName() +"] ";
	public ConsoleLog cLog = new ConsoleLog(this);
	boolean DebugMode;
	double SetHP = 0;
	float AddXP = 0;
        double AddHeart = 0;
	public void ConfigReload()
	{
            DebugMode = getConfig().getBoolean("DebugMode");
            SetHP = getConfig().getDouble("SetHP");
            AddHeart = getConfig().getDouble("AddHeart");
            AddXP = (float)getConfig().getDouble("AddXP");
            return;
	}
	public void ConfigSet()
	{
            getConfig().addDefault("DebugMode", false);
            getConfig().addDefault("SetHP", 20.0);
            getConfig().addDefault("AddHeart", 2.0);
            getConfig().addDefault("AddXP", 0.0);
            getConfig().options().copyDefaults(true);
            this.saveConfig();
            this.reloadConfig();
            this.ConfigReload();
            return;
	}
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		this.reloadConfig();
		this.ConfigReload();
		if(SetHP==0)
		{
			this.ConfigSet();
			this.reloadConfig();
			this.ConfigReload();
		}
		this.cLog.info(DebugMode+" "+SetHP+" "+AddXP+" "+AddHeart);
	}
	@Override
	public void onDisable()
	{
	    saveConfig();
	}
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent e)
	{
		Player Killer = (Player)e.getEntity().getKiller();
		Entity Death = e.getEntity();
		if (!(Killer instanceof Player))
                    return;
		if(DebugMode)
                    Killer.sendMessage(Pluginprefix+Killer+" "+Death);
		if( (Death instanceof Player) && (Killer instanceof Player) && Killer != Death)
		{
                    Player DeathMan = (Player)Death;
                    double MHP = Killer.getMaxHealth();
                    Killer.sendMessage(Pluginprefix+Killer+" Killed " + Death);
                    Killer.sendMessage(Pluginprefix+"MHP="+MHP);
                    if(!DebugMode)
                        Killer.setHealth(MHP);
                    else if(DebugMode)
                    {
                        double TempA = Killer.getMaxHealth();
                        if(SetHP==-2.0)
                        {
                            double Temp = Killer.getMaxHealth();
                            Killer.setHealth(Temp);
                        }
                        else Killer.setHealth(SetHP);
                        Killer.setExp(Killer.getExp()+AddXP);
                        if(AddHeart==0)
                            return;
                        if(DeathMan.getMaxHealth()>=20.0)
                        {
                            double TempB = DeathMan.getMaxHealth()-20.0;
                            Killer.setMaxHealth(TempB+TempA);
                        }
                        else if(DeathMan.getMaxHealth()==20.0)
                            Killer.setMaxHealth(AddHeart+TempA);
                        DeathMan.setMaxHealth(20.0);
                    }
                    return;
		}
		else return;
	}
        @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(args.length>=1)
		{
			if(args.length>=1&&("reload".equalsIgnoreCase(args[0])))
			{
                            reloadConfig();
                            this.ConfigReload();
                            this.cLog.info(DebugMode+" "+SetHP+" "+AddXP);
			}
                        else if(args.length>=1&&("setconfig".equalsIgnoreCase(args[0])))
			{
                            this.ConfigSet();
                            reloadConfig();
                            this.ConfigReload();
                            this.cLog.info("config reloaded.");
			}
			else
			{
                            sender.sendMessage(Pluginprefix+ChatColor.DARK_RED+"有効なコマンドを入力して下さい");
			}
		}
                else
                {
                    sender.sendMessage("/gexhp setconfig");
                    sender.sendMessage("/gexhp reload");
                    return true;
                }
                return false;
	}
}