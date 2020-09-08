package fr.kaname.kanabungeetp.commands;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.objects.Servers;
import org.bukkit.Bukkit;
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

		Player player = null;

		if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (cmd.getName().equalsIgnoreCase("bmenu") && player != null) {
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

		if(cmd.getName().equalsIgnoreCase("badmin") && player != null) {
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

		if (cmd.getName().equalsIgnoreCase("bwarp") && args.length >= 1) {

			String bungeeName = null;
			boolean isOpen = true;

			for (Servers server : plugin.getDatabaseManager().getServersList()) {
				if (server.getServerName().equalsIgnoreCase(args[0])) {
					bungeeName = server.getBungeeName();
					if (server.getOpenStatus().equals("close")) {
						isOpen = false;
					}
					break;
				}
			}

			if (bungeeName == null) {
				return false;
			}

			if (sender.hasPermission("kanabungeetp.bwarp." + bungeeName)) {
				if (args.length >= 2 && sender.hasPermission("kanabungeetp.bwarp.other")) {

					Player target = Bukkit.getPlayer(args[1]);
					if (target != null) {

						if (!bungeeName.equals(plugin.getServerName())) {
							sender.sendMessage("§3[KanaBungeeTP] §e" + target.getName() + " will be teleport to server " + bungeeName + " !");
							plugin.send_server(target, bungeeName);
						} else {
							sender.sendMessage("§3[KanaBungeeTP] §eThis player is already connected to this server !");
						}

					} else {
						sender.sendMessage("§3[KanaBungeeTP] §cPlayer not found");
					}

				} else {
					if(!bungeeName.equals(plugin.getServerName()) && player != null) {
						if (isOpen) {
							plugin.send_server(player, bungeeName);
						} else {
							player.sendMessage("§3[KanaBungeeTP] §cServer closed");
						}
					} else {
						if (player != null) {
							player.sendMessage("§3[KanaBungeeTP] §eYou are already connected to this server !");
						} else {
							sender.sendMessage("§3[KanaBungeeTP] §cConsole cannot use this command");
						}
					}
				}
			}
		} else {
			player.sendMessage("§3[KanaBungeeTP] §cUsage /bwarp {server} [player]");
		}

		return false;
	}
}
