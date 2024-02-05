package fr.theome.bingo;

import fr.minuskube.inv.InventoryManager;
import fr.theome.bingo.commands.CommandBingo;
import fr.theome.bingo.commands.CommandConfig;
import fr.theome.bingo.listeners.MainListeners;
import fr.theome.bingo.objects.Game;
import fr.theome.bingo.utils.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main extends JavaPlugin {

    private final InventoryManager invManager = new InventoryManager(this);
    public final Game game = new Game();
    public PluginManager manager;
    public TeamManager teamManager;
    private static final Random r = new Random();


    public final List<Material> items_list = new ArrayList<>();
    private final List<Material> banLists = Arrays.asList(Material.AIR,Material.BREWING_STAND,Material.IRON_DOOR_BLOCK,Material.NAME_TAG,Material.WATER,Material.STATIONARY_WATER,Material.LAVA,Material.STATIONARY_LAVA,Material.HUGE_MUSHROOM_1,Material.HUGE_MUSHROOM_2,Material.GREEN_RECORD,Material.GOLD_RECORD,Material.RECORD_3,Material.RECORD_4,Material.RECORD_5,Material.RECORD_6,Material.RECORD_7,Material.RECORD_8,Material.RECORD_9,Material.RECORD_10,Material.RECORD_11,Material.RECORD_12,Material.CHAINMAIL_BOOTS,Material.CHAINMAIL_CHESTPLATE,Material.CHAINMAIL_HELMET,Material.CHAINMAIL_LEGGINGS,Material.DIAMOND_BARDING,Material.GOLD_BARDING,Material.IRON_BARDING,Material.BARRIER, Material.BEDROCK,Material.MONSTER_EGG,Material.MONSTER_EGGS,null,Material.COMMAND,Material.COMMAND_MINECART,Material.REDSTONE_COMPARATOR_OFF,Material.REDSTONE_TORCH_OFF,Material.MOB_SPAWNER,Material.BEACON,Material.PISTON_EXTENSION,Material.TRIPWIRE,Material.PISTON_MOVING_PIECE,Material.FIRE,Material.DOUBLE_STEP,Material.DRAGON_EGG,Material.ENDER_STONE,Material.ENDER_PORTAL,Material.ENDER_PORTAL_FRAME,Material.DIODE_BLOCK_OFF,Material.MELON_STEM,Material.SPRUCE_DOOR,Material.ACACIA_DOOR,Material.BIRCH_DOOR,Material.DARK_OAK_DOOR,Material.JUNGLE_DOOR,Material.SIGN_POST,Material.CAKE_BLOCK,Material.QUARTZ_ORE,Material.CAULDRON,Material.WALL_SIGN,Material.FLOWER_POT,Material.BED_BLOCK,Material.DIAMOND_ORE,Material.BURNING_FURNACE,Material.ICE,Material.WEB,Material.WALL_BANNER,Material.WOODEN_DOOR,Material.GRASS,Material.SOIL,Material.COAL_ORE,Material.SUGAR_CANE_BLOCK,Material.CROPS,Material.DOUBLE_STONE_SLAB2,Material.DIODE_BLOCK_ON,Material.REDSTONE_LAMP_ON,Material.LAPIS_ORE,Material.POTATO,Material.NETHER_WARTS,Material.EXP_BOTTLE,Material.MYCEL,Material.SADDLE,Material.LONG_GRASS,Material.CARROT,Material.EMERALD_ORE,Material.COCOA,Material.PUMPKIN_STEM,Material.PORTAL,Material.STANDING_BANNER,Material.GLOWING_REDSTONE_ORE,Material.REDSTONE_WIRE,Material.SKULL,Material.REDSTONE_COMPARATOR_ON,Material.REDSTONE_ORE,Material.DAYLIGHT_DETECTOR_INVERTED,Material.WOOD_DOUBLE_STEP,Material.PACKED_ICE);

    @Override
    public void onEnable() {

        System.out.println("[Bingo] > Le plugin est en train de s'activer...");

        teamManager = new TeamManager(this);
        manager = getServer().getPluginManager();
        manager.registerEvents(new MainListeners(this), this);
        generateItems();

        //Création du jeu
        WorldCreator wc = new WorldCreator("world_the_end");
        wc.environment(World.Environment.THE_END);
        wc.createWorld();
        wc = new WorldCreator("world_nether");
        wc.environment(World.Environment.NETHER);
        wc.createWorld();
        wc = new WorldCreator("world");
        wc.environment(World.Environment.NORMAL);
        wc.createWorld();
        wc = new WorldCreator("lobby");
        wc.environment(World.Environment.NORMAL);
        wc.createWorld();


        World lobby = Bukkit.getWorld("lobby");
        lobby.setGameRuleValue("doMobSpawning", "false");
        lobby.setGameRuleValue("doDaylightCycle", "false");
        World world = Bukkit.getWorld("world");
        world.setGameRuleValue("spectatorsGenerateChunks", "false");
        world.setGameRuleValue("naturalRegeneration", "true");

        invManager.init();
        game.create(this);

        System.out.println("[Bingo] > Le plugin est en maintenant actif !");
        getCommand("bingo").setExecutor(new CommandBingo(this));
        getCommand("config").setExecutor(new CommandConfig());

        Bukkit.getScheduler().runTaskTimer(this, () -> game.tick(), 1, 1);

    }

    public InventoryManager getInvManager() {
        return invManager;
    }

    public List<Material> getItems_list() {
        return items_list;
    }

    public ArrayList<Integer> slot(int nbr) {
        int l, c;
        c = nbr % 9;
        l = (nbr - c) / 9;
        ArrayList<Integer> cl = new ArrayList<>();
        cl.add(l);
        cl.add(c);
        return cl;
    }

    public void generateItems() {
        items_list.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.closeInventory();
        }
        for (int i = 0; i < 25; i++) {
            items_list.add(randomizer());
        }
        System.out.println(items_list);
    }

    public Material randomizer() {

        Material mat = Material.getMaterial(r.nextInt(Material.values().length));

        while (items_list.contains(mat) || banLists.contains(mat)) {
            mat = Material.getMaterial(r.nextInt(Material.values().length));
        }
        return mat;
    }

}