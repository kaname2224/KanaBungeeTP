package fr.kaname.kanabungeetp.managers;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.managers.completors.BadminCompletor;

import java.util.ArrayList;
import java.util.List;

public class CommandeCompletor {

    private KanaBungeeTP plugin;
    private BadminCompletor badminCompletor;

    public CommandeCompletor(KanaBungeeTP plugin) {
        this.plugin = plugin;
        badminCompletor = new BadminCompletor(plugin);
    }

    public List<String> setCompletion(List<String> commande) {
        List<String> completors = new ArrayList<>();
        if (commande.get(0).equals("/badmin")) {
            completors = badminCompletor.setCompletions(commande);
        }
        return completors;
    }
}
