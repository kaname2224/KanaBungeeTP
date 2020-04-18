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
		serversSlotAvailable.add(19);
		serversSlotAvailable.add(20);
		serversSlotAvailable.add(21);

		List<Integer> slotAvailable = new ArrayList<Integer>();
		slotAvailable.add(14);
		slotAvailable.add(15);
		slotAvailable.add(16);
		slotAvailable.add(23);
		slotAvailable.add(24);
		slotAvailable.add(25);
		
		Inventory inv = Bukkit.createInventory(null, 36, "Menu bwarp");
		
		ItemStack livre = new ItemStack(Material.MAP, 1);
		ItemMeta livreMeta = livre.getItemMeta();
		livreMeta.setDisplayName(ChatColor.GREEN + "Serveurs Disponibles");
		livre.setItemMeta(livreMeta);
		inv.setItem(2, livre);
		
		ItemStack livre2 = new ItemStack(Material.MAP, 1);
		ItemMeta livre2Meta = livre.getItemMeta();
		livre2Meta.setDisplayName("Mondes Disponibles");
		livre2.setItemMeta(livre2Meta);
		inv.setItem(6, livre2);
		
		ItemStack noName = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1);
		ItemMeta noNameMeta = livre.getItemMeta();
		noNameMeta.setDisplayName(" ");
		noName.setItemMeta(noNameMeta);
		
		inv.setItem(0, noName);
		inv.setItem(1, noName);
		inv.setItem(3, noName);
		inv.setItem(4, noName);
		inv.setItem(5, noName);
		inv.setItem(7, noName);
		inv.setItem(8, noName);
		inv.setItem(9, noName);
		inv.setItem(13, noName);
		inv.setItem(17, noName);
		inv.setItem(18, noName);
		inv.setItem(22, noName);
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
				int slot = serversSlotAvailable.get(server.getSlot() - 1);
				inv.setItem(slot, item);
			} else if (server.getOpenStatus().equals("close") && server.getSlot() >= 0){
				Material item_type = Material.BARRIER;
				ItemStack item = new ItemStack(item_type, 1);
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName(ChatColor.RED + "Closed server");
				item.setItemMeta(itemMeta);
				int slot = serversSlotAvailable.get(server.getSlot() - 1);
				inv.setItem(slot, item);
			}
		}
		
		Integer i = 0;
		ItemStack itemWorld = new ItemStack(Material.BARRIER);
		ConfigurationSection section = main.getConfig().getConfigurationSection("servers." + thisServer + ".worlds");
		if(section != null) {
			Set<String> worldList = section.getKeys(false);
			for (String worldDisplayName : worldList) {
				if(i < 6) {
					String worldName = main.getConfig().getString("servers." + thisServer + ".worlds." + worldDisplayName + ".world_name");
					World w = Bukkit.getWorld(worldName);
					
					if(w == null) {
						main.getLogger().warning("there is no world called " + worldName);
						return null;
					}
					
					String matName = main.getConfig().getString("servers." + thisServer + ".worlds." + worldDisplayName + ".material");
					Material mat = Material.getMaterial(matName);
					
					if(mat == null) {
						main.getLogger().warning(matName + "isn't a valid material name !");
						return null;
					}
					
					itemWorld = new ItemStack(mat);
					ItemMeta itemMeta = itemWorld.getItemMeta();
					itemMeta.setDisplayName(ChatColor.RESET + worldDisplayName);
					itemWorld.setItemMeta(itemMeta);
					inv.setItem(slotAvailable.get(i), itemWorld);
					i++;
					
					
				}
			}
		}
		
		return inv;
		
	}
}