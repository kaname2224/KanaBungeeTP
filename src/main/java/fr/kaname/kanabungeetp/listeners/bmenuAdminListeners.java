package fr.kaname.kanabungeetp.listeners;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.commands.menu.bmenuAdd;
import fr.kaname.kanabungeetp.managers.DatabaseManager;
import fr.kaname.kanabungeetp.objects.Servers;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class bmenuAdminListeners implements Listener {

    //KanaBungeeTP Admin bmenu
    private KanaBungeeTP plugin;
    private DatabaseManager db;

    public bmenuAdminListeners(KanaBungeeTP plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
    }

    @EventHandler
    public void setServerSlot(InventoryClickEvent event) {

        if (event.getCurrentItem() == null) {
            return;
        }

        Player player = (Player) event.getView().getPlayer();
        if (event.getClickedInventory().getType() == InventoryType.CHEST && event.getView().getTitle().equals("KanaBungeeTP Admin bmenu")) {
            event.setCancelled(true);

            String serverName = event.getClickedInventory().getItem(5).getItemMeta().getDisplayName().replace("§aInformation de §2","");
            Servers server = db.getServer(serverName);
            int Slot = event.getSlot();
            ItemStack clickedItem = event.getCurrentItem();
            Material mat = clickedItem.getType();

            List<Integer> SlotList = new ArrayList<>();
            SlotList.add(12);
            SlotList.add(13);
            SlotList.add(14);
            SlotList.add(21);
            SlotList.add(22);
            SlotList.add(23);


            int index = SlotList.indexOf(Slot) + 1;

            if (SlotList.contains(Slot)) {
                if (mat == Material.GLASS) {
                    db.setDisplay(true, serverName, index);
                    player.sendMessage("§2Le slot du serveur a bien été changé");
                } else if (mat == server.getMaterial()) {
                    player.sendMessage("§cLe serveur est déjà dans ce Slot");
                    return;
                } else {
                    String toReplaceServerName = event.getCurrentItem().getItemMeta().getDisplayName().replace("§d", "");
                    db.setDisplay(false, toReplaceServerName, -1);
                    db.setDisplay(true, serverName, index);
                    player.sendMessage("§2Les serveurs ont bien été échangés");
                }

                Inventory inv = new bmenuAdd(plugin).getInventory(serverName);
                player.openInventory(inv);
            }


        }

    }
}
