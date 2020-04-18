package fr.kaname.kanabungeetp.commands;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.kaname.kanabungeetp.commands.menu.AdminMenu;
import fr.kaname.kanabungeetp.commands.menu.InvMenu;

public class Commandes implements CommandExecutor {

	private KanaBungeeTP plugin;
	
	public Commandes(KanaBungeeTP main) {
		this.plugin = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		Player player = (Player)sender;
		
		if (cmd.getName().equalsIgnoreCase("bmenu")) {
			InvMenu invMenu = new InvMenu(plugin);
			Inventory inv = invMenu.menuInventory();
			if(inv == null) {
				plugin.getLogger().warning("Cannot open inventory check your config file");
				player.sendMessage("§3[KanaBungeeTP] §cAn error occured when performing this command please check your log file or contact a server administrator");
				return false;
			}
			
			player.openInventory(inv);
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("badmin")) {
			AdminMenu invMenu = new AdminMenu(plugin);
			Inventory inv = invMenu.menuInventory();
			String[] servers = plugin.getServers();
			if(inv == null || servers == null) {
				plugin.getLogger().warning("Cannot open inventory check your config file");
				player.sendMessage("§3[KanaBungeeTP] §cAn error occured when performing this command please check your log file or contact a server administrator");
				return false;
			}
			player.openInventory(inv);
			return true;
		}
		
		
		return false;
	}
}
