package fr.theome.bingo.objects;

import fr.theome.bingo.Main;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Loader {

    //VARIABLE PERSONNALISABLE
    private final static int chunckPerLoad = 5;
    private final static int tickBetweenLoad = 1;
    private final static int tickBetweenLog = 40;
    private final static boolean skipIfLoad = true;
    private final static int maxSkip = 30;
    private final static String prefix = "[Loader] ";

    //VARIABLE SYSTEME
    private static ArrayList<ToLoad> toLoads;
    private static ToLoad actualToLoad;
    private static boolean isLoading;
    private static int actualChunck;
    private static int actualSkip;
    private static int totalChunck;
    private static int x;
    private static int y;
    private static Main main;

    public static void init(Main main) {



        toLoads = new ArrayList<>();
        isLoading = false;
        actualChunck = 0;
        totalChunck = 0;
        x = 0;
        y = 0;
        actualSkip = 0;

        actualToLoad = null;

        Bukkit.getScheduler().runTaskTimer(main, () -> {
            if (isLoading) {
                Location loc = actualToLoad.location;
                World world = loc.getWorld();
                int baseX = (int) loc.getX();
                int baseZ = (int) loc.getZ();

                for (int index = 0; index < chunckPerLoad; index++) {
                    actualChunck++;
                    Chunk chunck = world.getChunkAt(baseX + x*16, baseZ + y*16);

                    if (!chunck.isLoaded()) {
                        chunck.load();
                        actualSkip = 0;
                    } else {
                        if (skipIfLoad && actualSkip <= maxSkip -chunckPerLoad) {
                            index--;
                            actualSkip++;
                        }
                    }

                    x++;
                    if (x > actualToLoad.size) {
                        x = -actualToLoad.size;
                        y++;
                        if (y > actualToLoad.size) {
                            Bukkit.getConsoleSender().sendMessage(prefix + "Loading of area " + actualToLoad.name + " complete ! (" + actualChunck + " chuncks)");
                            toLoads.remove(actualToLoad);
                            setNewToLoad();
                            if (toLoads.size() == 0) {
                                isLoading = false;
                                Bukkit.getConsoleSender().sendMessage(prefix + "All areas loaded !");
                            }
                            break;
                        }
                    }
                }
            } else {
                setNewToLoad();
            }
        }, 5, tickBetweenLoad);

        Bukkit.getScheduler().runTaskTimer((Plugin) main, () -> {
            if (isLoading) {
                Bukkit.getConsoleSender().sendMessage(prefix + "Loading " + actualToLoad.name + " " + (int) ((double)actualChunck/(double)totalChunck * 100) + "% ..... (" + actualChunck + "/" + totalChunck + ")");
            }
        }, 5, tickBetweenLog);
    }

    private static void setNewToLoad() {
        if (toLoads.size() > 0) {
            actualToLoad = toLoads.get(0);
            Location loc = actualToLoad.location;
            Bukkit.getConsoleSender().sendMessage(prefix + "Charging new area: " + actualToLoad.name + " , [world:" + loc.getWorld() +"] [X:" + loc.getX() + "] [Z:" + loc.getZ() + "] [radius:" + actualToLoad.size + "] [canJoin:" + actualToLoad.canJoin + "]");

            isLoading = true;
            actualChunck = 0;
            actualSkip = 0;
            totalChunck = (actualToLoad.size *2 +1) * (actualToLoad.size *2 +1);
            x = -actualToLoad.size;
            y = -actualToLoad.size;
        }
    }

    public static void addNewLoad(String name, Location loc, int size, boolean canJoin) {
        toLoads.add(new ToLoad(name, loc, size, canJoin));
        Bukkit.getConsoleSender().sendMessage(prefix + "New area in waiting list: " + name + " ,  [world:" + loc.getWorld() +"] [X:" + loc.getX() + "] [Z:" + loc.getZ() + "] [radius:" + size + "] [canJoin:" + canJoin + "]");
    }

    public static boolean canJoin() {
        for (ToLoad toLoad : toLoads) {
            if (!toLoad.canJoin) return false;
        }
        return true;
    }

    public static void kickPlayer(Player player) {
        player.kickPlayer(prefix + "Chargement de la zone " + actualToLoad.name + " ... (" + (int) ((double)actualChunck/(double)totalChunck * 100) + "%)");
    }

    public static boolean isLoading() {
        return isLoading;
    }

    public static String getName() {
        return actualToLoad.name;
    }

    public static int getPourcentage() {
        return (int) ((double)actualChunck/(double)totalChunck * 100);
    }

    public static int getHowManyAreas() {
        return toLoads.size();
    }

    private static class ToLoad {
        public final String name;
        public final Location location;
        public final int size;
        public final boolean canJoin;

        public ToLoad(String name, Location loc, int size, boolean canJoin) {
            this.name = name;
            this.location = loc;
            this.size = size;
            this.canJoin = canJoin;
        }
    }
}