package fr.kaname.kanabungeetp.listeners;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.managers.DatabaseManager;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

	private KanaBungeeTP main;
	private DatabaseManager db;
	
	public PluginMessageListener(KanaBungeeTP main) {
		this.main = main;
		this.db = main.getDatabaseManager();
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
		if(channel.equals("BungeeCord")) {
			ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
			String subChannel = in.readUTF();
			
			if(subChannel.equals("GetServers")) {
				String[] servers = in.readUTF().split(", ");
				main.setServers(servers);
				db.checkServers(servers);
			}
		}
		

	}

}
