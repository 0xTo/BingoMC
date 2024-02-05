package fr.theome.bingo.utils;

import org.bukkit.Location;

import java.util.ArrayList;

public class Team {

    private final String prefix;               /*Prefix de la team dans le tab*/
    private final String name;                 /*Nom de la team dans le jeu*/
    private final ArrayList<BPlayer> bPlayers; /*Joueurs présents dans la team*/
    private Location spawn;                    /*Spawn de la team*/
    private final ItemCreator item;
    public final ArrayList<Boolean> items_get = new ArrayList<>();

    public Team(String prefix, String name, ItemCreator item) {
        /*Init*/
        this.prefix = prefix;
        this.name = name;
        this.item = item;
        bPlayers = new ArrayList<>();
        for (int i=0; i<25; i++){
            items_get.add(false);
        }
    }

    /*  */
    public String getName() { return name; }

    public ArrayList<BPlayer> getBPlayers() { return bPlayers; }

    public void addBPlayer(BPlayer bPlayer) { if (!bPlayers.contains(bPlayer)) bPlayers.add(bPlayer); }

    public void removeBPlayers(BPlayer bPlayer) { bPlayers.remove(bPlayer); }

    public ItemCreator getItemCreator(){ return item; }

    public String playerToString() {

        if (bPlayers.size() > 0){
            StringBuilder txt = new StringBuilder("§d");

            for (BPlayer bPlayer : bPlayers){
                txt.append(bPlayer.getPlayer().getDisplayName()).append(", ");
            }

            return txt.substring(0, txt.length() - 2);
        } else {
            return "§7Aucun...";
        }
    }

    public Location getSpawn() { return spawn; }
    public void setSpawn(Location spawn) { this.spawn = spawn; }
    public String getPrefix() { return prefix; }

}