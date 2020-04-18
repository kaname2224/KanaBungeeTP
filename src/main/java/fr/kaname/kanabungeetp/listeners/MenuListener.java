package fr.kaname.kanabungeetp.listeners;

import java.util.ArrayList;
import java.util.List;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.managers.DatabaseManager;
import fr.kaname.kanabungeetp.objects.Servers;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.ServerHandshakeStateEvent;

public class MenuListener implements Listener {
	
	private KanaBungeeTP main;
	private DatabaseManager db;
	
	public MenuListener(KanaBungeeTP main) {
		this.main = main;
		this.db = main.getDatabaseManager();
	}

	
	@EventHandler
	public void onMenuInteract(InventoryClickEvent event) {
		
		if(event.getCurrentItem() == null) {
			return;
		}
		
		List<Integer> slotMenuAvailable = new ArrayList<Integer>();
		slotMenuAvailable.add(14);
		slotMenuAvailable.add(15);
		slotMenuAvailable.add(16);
		slotMenuAvailable.add(23);
		slotMenuAvailable.add(24);
		slotMenuAvailable.add(25);
		
		List<Integer> slotServerAvailable = new ArrayList<Integer>();
		slotServerAvailable.add(10);
		slotServerAvailable.add(11);
		slotServerAvailable.add(12);
		slotServerAvailable.add(19);
		slotServerAvailable.add(20);
		slotServerAvailable.add(21);
		
		InventoryType invType = event.getClickedInventory().getType();
		String invName = event.getView().getTitle();
		Player player = (Player)event.getView().getPlayer();
		String serverName = main.getServerName();
		
		if(invType == InventoryType.CHEST && invName.equalsIgnoreCase("Menu bwarp")) {
			ItemStack current = event.getCurrentItem();
			String ItemName = current.getItemMeta().getDisplayName();
				
			if(slotServerAvailable.contains(event.getSlot())) {
				
				event.setCancelled(true);
				if(!serverName.equals("§cClosed server")) {
					

					Servers server = db.getServer(ItemName.replace("§r", ""));
					String sendServerName = server.getBungeeName();
					
					if(!sendServerName.equals(serverName)) {
						main.send_server(player, sendServerName);
					} else {
						player.sendMessage("§3[KanaBungeeTP] §eYou are already connected to this server !");
					}
					player.closeInventory();
				}
				
			} else if (slotMenuAvailable.contains(event.getSlot())) {
					
				event.setCancelled(true);
				player.closeInventory();
				String itemDisplayName = current.getItemMeta().getDisplayName().replace("�r", "");
				String worldName = main.getConfig().getString("servers." + serverName + ".worlds." + itemDisplayName + ".world_name");
				
				World world = Bukkit.getWorld(worldName);
				main.send_world(player, world, itemDisplayName);
				
			} else {
				
				event.setCancelled(true);
			}
		}
			
			
			
	}
	
}
