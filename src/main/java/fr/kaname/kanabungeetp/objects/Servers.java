package fr.kaname.kanabungeetp.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Material;

public class Servers {
	
	private String serverName;
	private String bungeeName;
	private Material material;
	private int slot;
	private String open;
	private String desc;
	private boolean display;
	
	public Servers(ResultSet server) throws SQLException {
		serverName = server.getString("Name");
		bungeeName = server.getString("BungeeName");
		material = Material.valueOf(server.getString("Material"));
		slot = server.getInt("Slot");
		open = server.getString("Open");
		desc = server.getString("Desc");
		display = server.getBoolean("Display");
	}
	
	public String getServerName() {
		return serverName;
	}
	
	public String getBungeeName() {
		return bungeeName;
	}
	
	public String getOpenStatus() {
		return open;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public Material getMaterial() {
		return material;
	}

	public boolean isDisplay() { return display; }
	

}
