package fr.kaname.kanabungeetp.listeners;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.commands.menu.AdminMenu;
import fr.kaname.kanabungeetp.managers.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

public class ChatListener implements Listener {

    private KanaBungeeTP plugin;
    private DatabaseManager db;

    public ChatListener(KanaBungeeTP kanaBungeeTP) {
        this.plugin = kanaBungeeTP;
        this.db = kanaBungeeTP.getDatabaseManager();
    }

    @EventHandler
    public void onChatEntry(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Map<UUID, String> mapChangeName = plugin.getMapChangeName();

        if (mapChangeName.containsKey(player.getUniqueId())) {
            String message = event.getMessage();
            String serverName = mapChangeName.get(player.getUniqueId());
            db.getServer(serverName);
            db.changeServerName(serverName, message);

            mapChangeName.remove(player.getUniqueId());
            event.setCancelled(true);
            player.sendMessage("§aLe nom du serveur §2" + serverName + " §aa bien été modifié en §2" + message);
        }
    }
}
