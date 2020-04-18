package fr.kaname.kanabungeetp.managers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fr.kaname.kanabungeetp.KanaBungeeTP;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.objects.Servers;

public class DatabaseManager {

    private KanaBungeeTP main;
    private Statement statement;
    private String serverTable;
    private String worldTable;

    public DatabaseManager(KanaBungeeTP main) throws IOException, SQLException {
    	this.main = main;
    	Configuration dbinfos = main.getConfig();
    	connectDatabase(dbinfos);
    }

    private void connectDatabase(Configuration dbinfos) throws SQLException {

        String bdName = dbinfos.getString("database.name");
        String user = dbinfos.getString("database.username");
        String password = dbinfos.getString("database.password");
        String host = dbinfos.getString("database.host");
        String port = dbinfos.getString("database.port");

        Connection connect = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + bdName, user, password);
        Statement statement = connect.createStatement();

        
        serverTable = dbinfos.getString("database.tables.servers");
        worldTable = dbinfos.getString("database.tables.worlds");
        this.statement = statement;
        createTable();
    }
	
    private void createTable() throws SQLException {
    	this.getSqlStatement().execute("CREATE TABLE IF NOT EXISTS `" + this.serverTable + "` (" +
    			  "`id` INT NOT NULL AUTO_INCREMENT," +
    			  "`Name` VARCHAR(45) NULL," +
    			  "`BungeeName` VARCHAR(45) NULL," +
    			  "`Material` VARCHAR(45) NULL," +
    			  "`Slot` INT NULL," +
    			  "`Open` VARCHAR(45) NULL," +
    			  "`Desc` TEXT NULL," +
    			  "`Display` BIT NULL," +
    			  "PRIMARY KEY (`id`))");
    	
		this.getSqlStatement().execute("CREATE TABLE IF NOT EXISTS `" + this.worldTable + "` (" +
				  "`id` INT NOT NULL AUTO_INCREMENT," +
				  "`Name` VARCHAR(45) NULL," +
				  "`WorldName` VARCHAR(45) NULL," +
				  "`Material` VARCHAR(45) NULL," +
				  "`Desc` TEXT NULL," +
				  "PRIMARY KEY (`id`))");
    }
    
    private Statement getSqlStatement(){
    	return statement;
    }
    
    public List<Servers> getServersList() {
    	
    	List<Servers> serversList = new ArrayList<>();
    	ResultSet rsl;
		try {
			rsl = getSqlStatement().executeQuery("SELECT * FROM " + serverTable);
	    	while(rsl.next()) {
	        	Servers server = new Servers(rsl);
	        	serversList.add(server);
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return serversList;
    	
    }
    
    public Servers getServer(String serverName) {
    	ResultSet rsl;
    	Servers server = null;
		try {
			rsl = getSqlStatement().executeQuery("SELECT * FROM `" + serverTable + "` WHERE `Name` = '" + serverName + "'");
			rsl.next();
	        server = new Servers(rsl);
	        System.out.println(server.getServerName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return server;
    }

	public void checkServers(String[] servers) {
		List<String> serverLoaded = new ArrayList<>();
		List<Servers> serversList = this.getServersList();
		for(String serverName : servers) {
			for(Servers serverDBName : serversList) {
				if(serverName.equals(serverDBName.getBungeeName())) {
					serverLoaded.add(serverDBName.getBungeeName());
				}
			}
		}

		for(String server : servers) {
			if(!serverLoaded.contains(server)) {
				System.out.println("A new server is detected : " + server);
				String sql = "INSERT INTO " + serverTable + "(`Name`, `BungeeName`, `Material`, `Slot`, `Open`, `Desc`, `Display`)" +
				"VALUES ('" + server + "', '" + server + "', 'BEDROCK', '-1', 'close', '', 0)";
				try {
					getSqlStatement().execute(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

    public void changeMaterial(String serverName, Material material) {
		try {
			getSqlStatement().execute("UPDATE " + this.serverTable + " SET `Material` = '" + material.name() + "' WHERE `Name` = '" + serverName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void changeServerName(String serverName, String message) {
		try {
			getSqlStatement().execute("UPDATE " + this.serverTable + " SET `Name` = '" + message + "' WHERE `Name` = '" + serverName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setStatus(String open, String serverName) {
    	main.getLogger().info(open + " " + serverName);
		try {
			getSqlStatement().execute("UPDATE " + this.serverTable + " SET `Open` = '" + open + "' WHERE `Name` = '" + serverName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setDisplay(Boolean display, String serverName, int index) {
		main.getLogger().info(display + " " + serverName);
		try {
			getSqlStatement().execute("UPDATE " + this.serverTable + " SET `Display` = " + display + ", `Slot` = '" + index + "' WHERE `Name` = '" + serverName + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
