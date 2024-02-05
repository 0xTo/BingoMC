package fr.theome.bingo.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class DLocation {

    public static Location lobby;
    public static Location world;
    public static Location nether;
    public static Location end;

    public static void create(){

        lobby = new Location(Bukkit.getWorld("lobby"), 0, 151, 0);
        world = new Location(Bukkit.getWorld("world"), 0, 100, 0 );
        nether = new Location(Bukkit.getWorld("world_nether"), 0, 100, 0 );
        end = new Location(Bukkit.getWorld("wordl_the_end"), 0, 100, 0 );

    }

}
