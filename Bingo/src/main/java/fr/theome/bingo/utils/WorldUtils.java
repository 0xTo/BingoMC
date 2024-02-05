package fr.theome.bingo.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.IOException;

public class WorldUtils {

    public static void PasteBarrier(World world, Location location) throws IOException {
        int x = location.getBlockX();
        int z = location.getBlockZ();
        world.setSpawnLocation(x, 151, z);

        for (int i = -16; i <= 16; i++) {

            for (int j = -16; j <= 16; j++) {

                new Location(world, i + x, 150, j + z).getBlock().setType(Material.BARRIER);
                new Location(world, i + x, 154, j + z).getBlock().setType(Material.BARRIER);
            }
            for (int j = 151; j < 154; j++) {
                new Location(world, i + x, j, z - 16).getBlock().setType(Material.BARRIER);
                new Location(world, i + x, j, z + 16).getBlock().setType(Material.BARRIER);
                new Location(world, x - 16, j, i + z).getBlock().setType(Material.BARRIER);
                new Location(world, x + 16, j, i + z).getBlock().setType(Material.BARRIER);
            }
        }
    }

}
