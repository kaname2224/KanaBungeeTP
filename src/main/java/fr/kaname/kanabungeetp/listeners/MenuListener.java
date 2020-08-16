package fr.kaname.kanabungeetp.listeners;

import java.util.ArrayList;
import java.util.List;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import fr.kaname.kanabungeetp.managers.DatabaseManager;
import fr.kaname.kanabungeetp.objects.Servers;

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

		List<Integer> slotServerAvailable = new ArrayList<Integer>();
		slotServerAvailable.add(10);
		slotServerAvailable.add(11);
		slotServerAvailable.add(12);
		slotServerAvailable.add(13);
		slotServerAvailable.add(14);
		slotServerAvailable.add(15);
		slotServerAvailable.add(16);
		slotServerAvailable.add(19);
		slotServerAvailable.add(20);
		slotServerAvailable.add(21);
		slotServerAvailable.add(22);
		slotServerAvailable.add(23);
		slotServerAvailable.add(24);
		slotServerAvailable.add(25);
		
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
				
			} else {
				event.setCancelled(true);
			}
		}
			
			
			
	}
	
}
