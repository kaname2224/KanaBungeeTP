package fr.kaname.kanabungeetp.commands.menu;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.managers.DatabaseManager;
import fr.kaname.kanabungeetp.objects.Servers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AdminServerMenu {

    private String ServerName;
    private KanaBungeeTP plugin;
    private DatabaseManager db;

    public AdminServerMenu(KanaBungeeTP plugin) {
        this.plugin = plugin;
        this.db = plugin.getDatabaseManager();
    }

    public Inventory getServersParametersMenu(String serverName) {

        Servers server = db.getServer(serverName.replace("§r", ""));

        Inventory inv = Bukkit.createInventory(null, 36, "KanaBungeeTP Server Infos");

        ItemStack livre = new ItemStack(Material.MAP, 1);
        ItemMeta livreMeta = livre.getItemMeta();
        livreMeta.setDisplayName("§aInformation de §2" + server.getServerName());
        List<String> lore1 = new ArrayList<>();
        lore1.add("Cliquez sur les items pour");
        lore1.add("modifier les paramètres");
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

        ItemStack changeMaterial = new ItemStack(server.getMaterial(), 1);
        ItemMeta changeMaterialMeta = changeMaterial.getItemMeta();

        changeMaterialMeta.setDisplayName(ChatColor.DARK_PURPLE + "Change Material");
        List<String> changeMaterialLore = new ArrayList<>();
        changeMaterialLore.add(ChatColor.LIGHT_PURPLE + "Current : §6" + server.getMaterial().name());
        changeMaterialLore.add(ChatColor.YELLOW + "Cliquez ici pour définir l'item que vous");
        changeMaterialLore.add(ChatColor.YELLOW + "tenez comme block de référence");
        changeMaterialMeta.setLore(changeMaterialLore);

        changeMaterial.setItemMeta(changeMaterialMeta);
        inv.setItem(10, changeMaterial);

        ItemStack changeName = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta changeNameMeta = changeName.getItemMeta();

        changeNameMeta.setDisplayName(ChatColor.DARK_PURPLE + "Change Display Name");
        List<String> changeNameLore = new ArrayList<>();
        changeNameLore.add(ChatColor.LIGHT_PURPLE + "Current : §6" + server.getServerName());
        changeNameLore.add(ChatColor.YELLOW + "Cliquez ici pour changer le nom");
        changeNameLore.add(ChatColor.YELLOW + "affiché sur le bloc du serveur");
        changeNameMeta.setLore(changeNameLore);

        changeName.setItemMeta(changeNameMeta);
        inv.setItem(11, changeName);

        ItemStack changeStatus = new ItemStack(Material.OAK_DOOR, 1);
        ItemMeta changeStatusMeta = changeStatus.getItemMeta();

        changeStatusMeta.setDisplayName(ChatColor.DARK_PURPLE + "Change Open Status");
        List<String> changeStatusLore = new ArrayList<>();
        changeStatusLore.add(ChatColor.LIGHT_PURPLE + "Current : §6" + server.getOpenStatus());
        changeStatusLore.add(ChatColor.YELLOW + "Cliquez ici ouvrir ou fermer");
        changeStatusLore.add(ChatColor.YELLOW + "le serveur");
        changeStatusMeta.setLore(changeStatusLore);

        changeStatus.setItemMeta(changeStatusMeta);
        inv.setItem(12, changeStatus);


        ItemStack changeDisplay = new ItemStack(Material.ENDER_EYE, 1);
        ItemMeta changeDisplayMeta = changeDisplay.getItemMeta();

        changeDisplayMeta.setDisplayName(ChatColor.DARK_PURPLE + "Change Display Status");
        List<String> changeDisplayLore = new ArrayList<>();
        changeDisplayLore.add(ChatColor.LIGHT_PURPLE + "Current : §6" + server.isDisplay());
        changeDisplayLore.add(ChatColor.YELLOW + "Cliquez ici pour définir la position");
        changeDisplayLore.add(ChatColor.YELLOW + "du serveur dans le §6/bmenu");
        changeDisplayMeta.setLore(changeDisplayLore);
        changeDisplay.setItemMeta(changeDisplayMeta);
        inv.setItem(13, changeDisplay);

        ItemStack HideServer = new ItemStack(Material.ENDER_CHEST, 1);
        ItemMeta HideServerMeta = HideServer.getItemMeta();

        HideServerMeta.setDisplayName(ChatColor.DARK_PURPLE + "Hide Server");
        List<String> HideServerLore = new ArrayList<>();
        HideServerLore.add(ChatColor.LIGHT_PURPLE + "Current : §6" + server.isDisplay());
        HideServerLore.add(ChatColor.YELLOW + "Cliquez ici pour supprimer le serveur");
        HideServerLore.add(ChatColor.YELLOW + "du §6/bmenu");
        HideServerMeta.setLore(HideServerLore);
        HideServer.setItemMeta(HideServerMeta);
        inv.setItem(14, HideServer);

        ItemStack teleportTo = new ItemStack(Material.SPECTRAL_ARROW, 1);
        ItemMeta teleportToMeta = teleportTo.getItemMeta();

        teleportToMeta.setDisplayName(ChatColor.DARK_PURPLE + "Teleport to Server");
        List<String> teleportToLore = new ArrayList<>();
        teleportToLore.add(ChatColor.YELLOW + "Cliquez ici pour rejoindre ce serveur");
        teleportToMeta.setLore(teleportToLore);
        teleportTo.setItemMeta(teleportToMeta);
        inv.setItem(15, teleportTo);

        return inv;
    }
}
