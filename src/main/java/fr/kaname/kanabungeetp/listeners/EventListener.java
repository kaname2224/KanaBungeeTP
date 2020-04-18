package fr.kaname.kanabungeetp.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.managers.CommandeCompletor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.TabCompleteEvent;

import fr.kaname.kanabungeetp.KanaBungeeTP;

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
		if(servers == null) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				@Override
				public void run() {
					main.getPluginMessageManager().getServers(player, main);
					main.getLogger().info("Récupération des serveurs disponibles...");
				}
				}, 10);
		}

	}

	@EventHandler
	public void onTabComplete(TabCompleteEvent event) {
		String cmd = event.getBuffer();
		List<String> ServerComplete = new ArrayList<>();
		List<String> badminComplete = new ArrayList<>();
		List<String> serverModifyComplete = new ArrayList<String>();
		List<String> serverModify = new ArrayList<String>();
		List<String> commande = new ArrayList<String>();
		List<String> remove = new ArrayList<String>();
		
		badminComplete.add("server");
		
		serverModify.add("material");
		serverModify.add("name");
		serverModify.add("open");

		commande.addAll(Arrays.asList(cmd.split(" ")));
		for (String str : commande) {
			if (str.equals("")) {
				remove.add(str);
			}
		}

		commande.removeAll(remove);
		final List<String> finalCompletion = commandeCompletor.setCompletion(commande);
		event.setCompletions(finalCompletion);


	}
}
