package fr.kaname.kanabungeetp.commands.menu;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.managers.DatabaseManager;
import fr.kaname.kanabungeetp.objects.Servers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class bmenuAdd {

    private DatabaseManager db;
    private KanaBungeeTP plugin;

    public bmenuAdd(KanaBungeeTP main) {
        this.plugin = main;
        this.db = main.getDatabaseManager();
    }

    public Inventory getInventory(String serverName) {

        Servers server = db.getServer(serverName.replace("§r", ""));
        List<Servers> serversList = db.getServersList();
        Inventory inv = Bukkit.createInventory(null, 36, "KanaBungeeTP Admin bmenu");

        ItemStack livre = new ItemStack(Material.MAP, 1);
        ItemMeta livreMeta = livre.getItemMeta();
        livreMeta.setDisplayName("§aAjout de §2" + server.getServerName() + " §aau §2/bmenu");
        List<String> lore1 = new ArrayList<>();
        lore1.add("Cliquez sur les blocs pour définir le slot qui ");
        lore1.add("sera occupé par ce serveur");
        livreMeta.setLore(lore1);
        livre.setItemMeta(livreMeta);
        inv.setItem(3, livre);

        ItemStack serverItem = new ItemStack(server.getMaterial(), 1);
        ItemMeta serverMeta = livre.getItemMeta();
        serverMeta.setDisplayName("§aInformation de §2" + server.getServerName());
        List<String> serverLore = new ArrayList<>();
        serverLore.add(ChatColor.LIGHT_PURPLE + "Display : §6" + server.isDisplay());
        serverLore.add(ChatColor.LIGHT_PURPLE + "Slot : §6" + server.getSlot());
        serverMeta.setLore(serverLore);
        serverItem.setItemMeta(serverMeta);
        inv.setItem(5, serverItem);

        ItemStack noName = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta noNameMeta = noName.getItemMeta();
        noNameMeta.setDisplayName(" ");
        noName.setItemMeta(noNameMeta);

        inv.setItem(0, noName);
        inv.setItem(1, noName);
        inv.setItem(2, noName);
        inv.setItem(4, noName);
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

        List<Integer> slotAvailable = new ArrayList<>();
        slotAvailable.add(12);
        slotAvailable.add(13);
        slotAvailable.add(14);
        slotAvailable.add(21);
        slotAvailable.add(22);
        slotAvailable.add(23);

        List<Material> materialList = new ArrayList<>(6);
        for (int i = 0; i <= 5; i++) {
            materialList.add(null);
        }

        for (int i = 0; i <= 5; i++) {
            materialList.set(i, Material.GLASS);
            for (Servers srv : serversList) {
                if (srv.getSlot() == (i + 1)) {
                    materialList.set(i, srv.getMaterial());
                }
            }
        }

        int i = 0;

        ItemStack emptySlot = new ItemStack(Material.GLASS);
        ItemMeta emptySlotMeta = emptySlot.getItemMeta();
        emptySlotMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Slot Vide");

        for (Material mat : materialList) {
            if (mat.equals(Material.GLASS)) {

                List<String> emptySlotLore = new ArrayList<>();
                emptySlotLore.add("Cliquez ici pour ajouter ce serveur dans le slot " + (i + 1));
                emptySlotMeta.setLore(emptySlotLore);
                emptySlot.setItemMeta(emptySlotMeta);
                inv.setItem(slotAvailable.get(i), emptySlot);
            } else {
                for (Servers srv : serversList) {
                    if (srv.getSlot() == (i + 1)) {
                        if (srv.getSlot() == server.getSlot()) {

                            ItemStack serverSlot = new ItemStack(server.getMaterial());
                            ItemMeta serverSlotMeta = serverSlot.getItemMeta();
                            serverSlotMeta.setDisplayName(ChatColor.GREEN + server.getServerName());
                            List<String> serverSlotLore = new ArrayList<>();
                            serverSlotLore.add("§aCe serveur est déjà dans ce slot");
                            serverSlotMeta.setLore(serverSlotLore);
                            serverSlot.setItemMeta(serverSlotMeta);

                            inv.setItem(slotAvailable.get(i), serverSlot);

                        } else {

                            ItemStack serverSlot = new ItemStack(srv.getMaterial());
                            ItemMeta serverSlotMeta = serverSlot.getItemMeta();
                            serverSlotMeta.setDisplayName(ChatColor.LIGHT_PURPLE + srv.getServerName());
                            List<String> serverSlotLore = new ArrayList<>();
                            serverSlotLore.add("Cliquez ici pour remplacer ce serveur dans le slot " + (i + 1));
                            serverSlotMeta.setLore(serverSlotLore);
                            serverSlot.setItemMeta(serverSlotMeta);

                            inv.setItem(slotAvailable.get(i), serverSlot);

                        }
                    }
                }
            }

            i++;
        }

        return inv;
    }
}
