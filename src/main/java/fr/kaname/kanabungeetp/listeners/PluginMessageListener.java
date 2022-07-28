package fr.kaname.kanabungeetp.listeners;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.managers.DatabaseManager;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

			if (subChannel.equals("Teleport")) {

				boolean complete = false;

				double locX = 0;
				double locY = 0;
				double locZ = 0;
				UUID uuid = null;
				String worldName = null;

				short len = in.readShort();
				byte[] msgbytes = new byte[len];

				in.readFully(msgbytes);

				DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
				try {
					uuid = UUID.fromString(msgin.readUTF());
					worldName = msgin.readUTF();
					locX = msgin.readDouble();
					locY = msgin.readDouble();
					locZ = msgin.readDouble();

					complete = true;
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (complete) {
					Location location = new Location(Bukkit.getServer().getWorld(worldName), locX, locY, locZ);
					main.getTeleportMap().put(uuid, location);
				}

			}
		}
		

	}

}
