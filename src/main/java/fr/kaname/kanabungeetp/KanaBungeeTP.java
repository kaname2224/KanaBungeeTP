package fr.kaname.kanabungeetp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.kaname.kanabungeetp.commands.Commandes;
import fr.kaname.kanabungeetp.listeners.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.kaname.kanabungeetp.managers.DatabaseManager;
import fr.kaname.kanabungeetp.managers.PluginMessageManagers;

public class KanaBungeeTP extends JavaPlugin {
	
	private Connection conn;
	private String serverName;
	private DatabaseManager db;
	private PluginMessageManagers pluginMessageManager;
	private String[] servers;
	private Map<UUID, String> mapChangeName = new HashMap<>();
	
	public KanaBungeeTP() throws IOException, SQLException {
		db = new DatabaseManager(this);
	}
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		this.getCommand("badmin").setExecutor(new Commandes(this));
		this.getCommand("bmenu").setExecutor(new Commandes(this));
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageListener(this));
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getServer().getPluginManager().registerEvents(new MenuListener(this), this);
		getServer().getPluginManager().registerEvents(new AdminMenuListener(this), this);
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getServer().getPluginManager().registerEvents(new bmenuAdminListeners(this), this);
		serverName = getConfig().getString("currentServerBungeeName");
		pluginMessageManager = new PluginMessageManagers(this);
	}
	
	public String getServerName() {
		return this.serverName;
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

	public void send_server(Player player, String serverName) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		
		try {
			
			out.writeUTF("Connect");
			out.writeUTF(serverName);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		player.sendMessage("§3[KanaBungeeTP] §eyou will be teleport to server §6" + serverName);
		player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
	}
	
	public void send_world(Player player, World world, String worldName) {
		Location loc = new Location(world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY(), world.getSpawnLocation().getZ());
		player.sendMessage("§3[KanaBungeeTP] §eyou will be teleport to world §6" + worldName);
		player.teleport(loc);
	}

	public void setChangeNameListener(Map<UUID, String> mapChangeName) {
		this.mapChangeName = mapChangeName;
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
