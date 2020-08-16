package fr.kaname.kanabungeetp.listeners;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.managers.CommandeCompletor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class EventListener implements Listener {

	private KanaBungeeTP main;

	public EventListener(KanaBungeeTP main) {
		this.main = main;
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		String[] servers = main.getServers();
		Player player = event.getPlayer();
		if (servers == null) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				@Override
				public void run() {
					main.getPluginMessageManager().getServers(player);
					main.getPluginMessageManager().getServerName(player);
					main.getLogger().info("Récupération des serveurs disponibles...");
				}
			}, 10);
		}

		if (main.getTeleportMap().containsKey(player.getUniqueId())) {
			main.teleportPlayer(player, main.getTeleportMap().get(player.getUniqueId()));
		}

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				if (main.getTeleportMap().containsKey(player.getUniqueId())) {
					main.teleportPlayer(player, main.getTeleportMap().get(player.getUniqueId()));
				}
			}
		}, 20);

	}
}
