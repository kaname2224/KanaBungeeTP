package fr.kaname.kanabungeetp.managers.completors;

import fr.kaname.kanabungeetp.KanaBungeeTP;
import fr.kaname.kanabungeetp.objects.Servers;

import java.util.ArrayList;
import java.util.List;

public class BadminCompletor {


    private KanaBungeeTP plugin;

    public BadminCompletor(KanaBungeeTP plugin) {
        this.plugin = plugin;
    }

    public List<String> setCompletions(List<String> commande) {
        List<String> finalCompletion = new ArrayList<>();
        return finalCompletion;
    }
}
