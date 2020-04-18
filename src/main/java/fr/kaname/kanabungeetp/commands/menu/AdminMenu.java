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

public class AdminMenu {

	private KanaBungeeTP main;
	private DatabaseManager db;
	
	public AdminMenu(KanaBungeeTP main) {
		this.main = main;
		this.db = main.getDatabaseManager();
	}
	
	public Inventory menuInventory() {
		
		String thisServer = main.getServerName();

		List<Integer> ServerSlot = new ArrayList<Integer>();
		ServerSlot.add(10);
		ServerSlot.add(11);
		ServerSlot.add(12);
		ServerSlot.add(13);
		ServerSlot.add(14);
		ServerSlot.add(15);
		ServerSlot.add(16);
		ServerSlot.add(17);
		ServerSlot.add(18);
		ServerSlot.add(19);
		ServerSlot.add(20);
		ServerSlot.add(21);
		ServerSlot.add(22);
		ServerSlot.add(23);

		Inventory inv = Bukkit.createInventory(null, 36, "KanaBungeeTP Admin");
		
		ItemStack livre = new ItemStack(Material.MAP, 1);
		ItemMeta livreMeta = livre.getItemMeta();
		livreMeta.setDisplayName("§2Serveurs Disponibles");
		List<String> lore1 = new ArrayList<>();
		lore1.add("Survolez un serveur pour voir ses paramètres");
		lore1.add("Cliquez pour modifier ses paramètres");
		livreMeta.setLore(lore1);
		livre.setItemMeta(livreMeta);
		inv.setItem(4, livre);

		ItemStack noName = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
		ItemMeta noNameMeta = noName.getItemMeta();
		noNameMeta.setDisplayName(" ");
		noName.setItemMeta(noNameMeta);

		inv.setItem(0, noName);
		inv.setItem(1, noName);
		inv.setItem(2, noName);
		inv.setItem(3, noName);
		inv.setItem(5, noName);
		inv.setItem(6, noName);
		inv.setItem(7, noName);
		inv.setItem(8, noName);
		inv.setItem(9, noName);
		inv.setItem(17, noName);
		inv.setItem(18, noName);
		inv.setItem(26, noName);
		for(int i = 27; i <= 35; i++) {
			inv.setItem(i, noName);
		}
		int i = 0;
		List<Servers> ServerList = db.getServersList();
		for (Servers srv : ServerList) {
			if (i <= 13) {
				ItemStack srvItem = new ItemStack(srv.getMaterial());

				ItemMeta srvItemMeta = srvItem.getItemMeta();
				srvItemMeta.setDisplayName(ChatColor.RESET + srv.getServerName());
				List<String> serverLore = new ArrayList<>();
				serverLore.add(ChatColor.LIGHT_PURPLE + "Server : " + ChatColor.GOLD + srv.getBungeeName());
				serverLore.add(ChatColor.LIGHT_PURPLE + "Slot : " + ChatColor.GOLD + srv.getSlot());
				serverLore.add(ChatColor.LIGHT_PURPLE + "Status : " + ChatColor.GOLD + srv.getOpenStatus());
				serverLore.add(ChatColor.LIGHT_PURPLE + "Display : " + ChatColor.GOLD + srv.isDisplay());


				srvItemMeta.setLore(serverLore);

				srvItem.setItemMeta(srvItemMeta);
				inv.setItem(ServerSlot.get(i), srvItem);
				i++;
			}
		}


		return inv;
		
	}
}