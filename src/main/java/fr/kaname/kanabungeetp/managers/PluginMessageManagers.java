package fr.kaname.kanabungeetp.managers;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.kaname.kanabungeetp.KanaBungeeTP;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PluginMessageManagers {
	
	private KanaBungeeTP plugin;


	public PluginMessageManagers(KanaBungeeTP main) {
		this.plugin = main;
	}

	public void getServers(Player player) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("GetServers");
		if (player != null) {
			player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		}

	}

	public void getServerName(Player player) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("GetServer");
		if (player != null) {
			player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		}

	}

	public void sendTeleportRequest(Player player, Location location, String server) {

		if (player == null) {
			return;
		}

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward");
		out.writeUTF(server);
		out.writeUTF("Teleport");

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);

		try {
			msgout.writeUTF(player.getUniqueId().toString());
			msgout.writeDouble(location.getX());
			msgout.writeDouble(location.getY());
			msgout.writeDouble(location.getZ());
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());

		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}
}
