package fr.kaname.kanabungeetp.managers;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.kaname.kanabungeetp.KanaBungeeTP;

public class PluginMessageManagers {
	
	private KanaBungeeTP plugin;
	
	

	
	public PluginMessageManagers(KanaBungeeTP main) {
		this.plugin = main;
	}


	public void getServers(Player player, KanaBungeeTP main) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("GetServers");
		player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		
	}
	
}
