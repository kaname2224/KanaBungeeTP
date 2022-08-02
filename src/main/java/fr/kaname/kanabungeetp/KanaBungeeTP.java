package fr.kaname.kanabungeetp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import fr.felix911.apibukkit.ApiBukkit;
import fr.kaname.kanabungeetp.commands.Commandes;
import fr.kaname.kanabungeetp.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.kaname.kanabungeetp.managers.DatabaseManager;
import fr.kaname.kanabungeetp.managers.PluginMessageManagers;

public class KanaBungeeTP extends JavaPlugin {

	private String serverName = "";
	private final DatabaseManager db;
	private PluginMessageManagers pluginMessageManager;
	private String[] servers;
	private final Map<UUID, String> mapChangeName = new HashMap<>();
	private final Map<UUID, Location> teleportMap = new HashMap<>();
	
	public KanaBungeeTP() throws IOException, SQLException {
		db = new DatabaseManager(this);
	}
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		Objects.requireNonNull(this.getCommand("badmin")).setExecutor(new Commandes(this));
		Objects.requireNonNull(this.getCommand("bmenu")).setExecutor(new Commandes(this));
		Objects.requireNonNull(this.getCommand("bwarp")).setExecutor(new Commandes(this));
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageListener(this));
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getServer().getPluginManager().registerEvents(new MenuListener(this), this);
		getServer().getPluginManager().registerEvents(new AdminMenuListener(this), this);
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getServer().getPluginManager().registerEvents(new bmenuAdminListeners(this), this);
		getServer().getPluginManager().registerEvents(new autocompleteListener(this), this);
		pluginMessageManager = new PluginMessageManagers(this);
	}
	
	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public DatabaseManager getDatabaseManager() {
		return this.db;
	}
	
	public PluginMessageManagers getPluginMessageManager() {
		return pluginMessageManager;
	}

	public void setServers(String[] servers) {
		this.servers = servers;
	}
	
	public String[] getServers() {
		return this.servers;
	}

	public void teleportPlayer(Player player, Location location) {
		if (player != null && teleportMap.containsKey(player.getUniqueId())) {
			player.teleport(location);
			teleportMap.remove(player.getUniqueId());
		}
	}

	public void teleportPlayer(Player player, Location location, String serverName, String worldName) {

		if (!serverName.equals(this.getServerName())) {
			ApiBukkit.teleportPlayerToServer(player, serverName);
			this.getPluginMessageManager().sendTeleportRequest(player, location, serverName, worldName);
		} else {
			location.setWorld(Bukkit.getWorld(worldName));
			player.teleport(location);
		}
	}

	public Map<UUID, Location> getTeleportMap() {
		return teleportMap;
	}

	public Map<UUID, String> getMapChangeName(){
		return this.mapChangeName;
	}

	public void openServer(String serverName) {
		getDatabaseManager().setStatus("open", serverName);
	}

	public void closeServer(String serverName) {
		getDatabaseManager().setStatus("close", serverName);
	}
}
