package fr.kaname.kanabungeetp.listeners;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.objects.Servers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class autocompleteListener implements Listener {

    private final KanaBungeeTP plugin;
    private final List<String> complete = new ArrayList<>();
    private final List<String> bwarpComplete = new ArrayList<>();

    public autocompleteListener(KanaBungeeTP kanaBungeeTP) {
        this.plugin = kanaBungeeTP;

    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {

        complete.clear();
        bwarpComplete.clear();

        for (Servers server : plugin.getDatabaseManager().getServersList()) {
            bwarpComplete.add(server.getServerName());
        }

        String command = event.getBuffer().replace("/", "");
        if (command.startsWith("bwarp")) {

            for (String warp : bwarpComplete) {
                String testCommand = "bwarp " + warp;
                if (testCommand.startsWith(command)) {
                    complete.add(warp);
                }

                if (command.startsWith(testCommand)) {
                    complete.clear();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String playerTestCommand = "bwarp " + warp + " " + player.getName();
                        if (playerTestCommand.startsWith(command)) {
                            complete.add(player.getName());
                        }
                    }
                }

            }

            event.setCompletions(complete);
        }
    }
}
