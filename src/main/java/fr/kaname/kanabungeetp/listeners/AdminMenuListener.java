package fr.kaname.kanabungeetp.listeners;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.commands.menu.AdminMenu;
import fr.kaname.kanabungeetp.commands.menu.AdminServerMenu;
import fr.kaname.kanabungeetp.commands.menu.bmenuAdd;
import fr.kaname.kanabungeetp.managers.DatabaseManager;
import fr.kaname.kanabungeetp.objects.Servers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdminMenuListener implements Listener {

    private KanaBungeeTP plugin;
    private DatabaseManager db;

    public AdminMenuListener(KanaBungeeTP plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
    }

    @EventHandler
    public void onAdminInteract(InventoryClickEvent event) {

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getClickedInventory().getType() == InventoryType.CHEST && event.getView().getTitle().equalsIgnoreCase("KanaBungeeTP Admin")) {
            Player player = (Player)event.getView().getPlayer();
            event.setCancelled(true);
            Material itemMaterial = event.getCurrentItem().getType();
            String itemName = event.getCurrentItem().getItemMeta().getDisplayName();

            if (itemMaterial.equals(Material.MAP) && itemName.equalsIgnoreCase("§2Serveurs Disponibles")) {
                return;
            } else if (itemMaterial.equals(Material.RED_STAINED_GLASS_PANE) && itemName.equalsIgnoreCase("")) {
                return;
            }


            Inventory serverMenu = new AdminServerMenu(plugin).getServersParametersMenu(event.getCurrentItem().getItemMeta().getDisplayName());
            player.openInventory(serverMenu);
        }

        if (event.getClickedInventory().getType() == InventoryType.CHEST && event.getView().getTitle().equalsIgnoreCase("KanaBungeeTP Server Infos")) {
            Player player = (Player)event.getView().getPlayer();
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Change Material")) {

                if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    player.sendMessage("§cVous devez tenir un item en main");
                    return;
                } else {
                    Material newMaterial = player.getInventory().getItemInMainHand().getType();
                    String serverName = event.getInventory().getItem(4).getItemMeta().getDisplayName().replace("§aInformation de §2", "");
                    db.changeMaterial(serverName, newMaterial);
                    Inventory inv = new AdminMenu(plugin).menuInventory();
                    player.openInventory(inv);
                    player.sendMessage("§aL'item de référence à bien été changé");
                }
            } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Change Display Name")){
                String serverName = event.getInventory().getItem(4).getItemMeta().getDisplayName().replace("§aInformation de §2", "");
                plugin.getMapChangeName().put(player.getUniqueId(), serverName);
                player.sendMessage(ChatColor.AQUA + "Taper le nouveau nom du serveur dans le chat\n§cAttention ne mettez pas de code couleur !");
                player.closeInventory();
            } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Change Open Status")) {
                String serverName = event.getInventory().getItem(4).getItemMeta().getDisplayName().replace("§aInformation de §2", "");
                Servers server = db.getServer(serverName);
                if (server.getOpenStatus().equals("close")) {
                    plugin.openServer(serverName);
                    player.sendMessage(ChatColor.GOLD + serverName + "§e a bien été ouvert");
                } else if (server.getOpenStatus().equals("open")) {
                    plugin.closeServer(serverName);
                    player.sendMessage(ChatColor.GOLD + serverName + "§e a bien été fermé");
                } else {
                    player.sendMessage("§cLe serveur n'a pas été trouvé");
                }
                Inventory inv = new AdminServerMenu(plugin).getServersParametersMenu(serverName);
                player.openInventory(inv);

            } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Change Display Status")) {

                String serverName = event.getInventory().getItem(4).getItemMeta().getDisplayName().replace("§aInformation de §2", "");
                Inventory inv = new bmenuAdd(plugin).getInventory(serverName);
                player.openInventory(inv);

            } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Teleport to Server")) {

                String serverName = event.getInventory().getItem(4).getItemMeta().getDisplayName().replace("§aInformation de §2", "");
                Servers server = db.getServer(serverName);

                plugin.send_server(player, server.getBungeeName());
            } else if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Hide Server")) {
                String serverName = event.getInventory().getItem(4).getItemMeta().getDisplayName().replace("§aInformation de §2", "");
                db.setDisplay(false, serverName, -1);
            }
        }
    }
}
