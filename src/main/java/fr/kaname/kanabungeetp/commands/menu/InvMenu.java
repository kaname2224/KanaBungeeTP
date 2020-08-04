package fr.kaname.kanabungeetp.commands.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.kaname.kanabungeetp.managers.DatabaseManager;
import fr.kaname.kanabungeetp.objects.Servers;

public class InvMenu {

	private KanaBungeeTP main;
	private DatabaseManager db;
	
	public InvMenu(KanaBungeeTP main) {
		this.main = main;
		this.db = main.getDatabaseManager();
	}
	
	public Inventory menuInventory() {
		
		String thisServer = main.getConfig().getString("currentServerBungeeName");

		List<Integer> serversSlotAvailable = new ArrayList<Integer>();
		serversSlotAvailable.add(10);
		serversSlotAvailable.add(11);
		serversSlotAvailable.add(12);
		serversSlotAvailable.add(13);
		serversSlotAvailable.add(14);
		serversSlotAvailable.add(15);
		serversSlotAvailable.add(16);
		serversSlotAvailable.add(19);
		serversSlotAvailable.add(20);
		serversSlotAvailable.add(21);
		serversSlotAvailable.add(22);
		serversSlotAvailable.add(23);
		serversSlotAvailable.add(24);
		serversSlotAvailable.add(25);

		Inventory inv = Bukkit.createInventory(null, 36, "Menu bwarp");
		
		ItemStack livre = new ItemStack(Material.MAP, 1);
		ItemMeta livreMeta = livre.getItemMeta();
		livreMeta.setDisplayName(ChatColor.GREEN + "Serveurs Disponibles");
		livre.setItemMeta(livreMeta);
		inv.setItem(5, livre);
		
		ItemStack noName = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1);
		ItemMeta noNameMeta = livre.getItemMeta();
		noNameMeta.setDisplayName(" ");
		noName.setItemMeta(noNameMeta);
		
		inv.setItem(0, noName);
		inv.setItem(1, noName);
		inv.setItem(2, noName);
		inv.setItem(3, noName);
		inv.setItem(4, noName);
		inv.setItem(6, noName);
		inv.setItem(7, noName);
		inv.setItem(8, noName);
		inv.setItem(9, noName);
		inv.setItem(17, noName);
		inv.setItem(18, noName);
		inv.setItem(26, noName);
		for(int i = 27; i < 36; i++) {
			inv.setItem(i, noName);
		}
		
		for (Servers server : db.getServersList()) {
			
			if(server.getOpenStatus().equals("open") && server.getSlot() >= 0) {
				Material item_type = server.getMaterial();
				List<String> lore = Arrays.asList(ChatColor.DARK_PURPLE + server.getDescription());
				ItemStack item = new ItemStack(item_type, 1);
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setLore(lore);
				itemMeta.setDisplayName(ChatColor.RESET+ server.getServerName());
				item.setItemMeta(itemMeta);
				int slot = serversSlotAvailable.get(server.getSlot());
				inv.setItem(slot, item);
			} else if (server.getOpenStatus().equals("close") && server.getSlot() >= 0){
				Material item_type = Material.BARRIER;
				ItemStack item = new ItemStack(item_type, 1);
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName(ChatColor.RED + "Closed server");
				item.setItemMeta(itemMeta);
				int slot = serversSlotAvailable.get(server.getSlot());
				inv.setItem(slot, item);
			}
		}
		
		return inv;
		
	}
}