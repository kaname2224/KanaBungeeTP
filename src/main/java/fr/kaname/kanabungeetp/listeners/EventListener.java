package fr.kaname.kanabungeetp.listeners;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.managers.CommandeCompletor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

	private KanaBungeeTP main;
	private CommandeCompletor commandeCompletor;

	public EventListener(KanaBungeeTP main) {
		this.main = main;
		this.commandeCompletor = new CommandeCompletor(main);
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		String[] servers = main.getServers();
		Player player = event.getPlayer();
		if (servers == null) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				@Override
				public void run() {
					main.getPluginMessageManager().getServers(player, main);
					main.getLogger().info("Récupération des serveurs disponibles...");
				}
			}, 10);
		}

	}
}
