package fr.theome.bingo.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.theome.bingo.Main;
import fr.theome.bingo.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class PvpConfigMenu implements InventoryProvider {

    public Main plugin;

    public PvpConfigMenu() {

        this.plugin = (Main) Bukkit.getServer().getPluginManager().getPlugin("Bingo");

    }

    public static final SmartInventory PvpGUI = SmartInventory.builder()
            .id("pvpGui")
            .provider(new PvpConfigMenu())
            .size(3, 9)
            .title(ChatColor.DARK_PURPLE + "Config PvP")
            .manager(JavaPlugin.getPlugin(Main.class).getInvManager())
            .build();


    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack purpleglass = new ItemCreator(Material.STAINED_GLASS_PANE, (byte) 10).setName(" ").getItem();
        contents.fill(ClickableItem.empty(purpleglass));


        contents.set(0, 8, ClickableItem.of(new ItemCreator(Material.ARROW, 0).setName("retour").getItem(), e -> {
            ConfigMenu.CONFIG.open(player);
        }));

        ClickableItem timerPvpLess10;
        ClickableItem timerPvpPlus10;
        ClickableItem timerPvpLess60;
        ClickableItem timerPvpPlus60;
        ClickableItem timerPvpLess600;
        ClickableItem timerPvpPlus600;

        timerPvpPlus10 = ClickableItem.of(new ItemCreator(Material.BANNER, 0).setName("§a+10 secondes").setBasecolor(DyeColor.WHITE).addBannerPreset(10, DyeColor.BLACK).getItem(), e -> {
            plugin.game.pvpTime += 10;
        });


        timerPvpLess10 = ClickableItem.of(new ItemCreator(Material.BANNER, 0).setName("§c-10 secondes").setBasecolor(DyeColor.WHITE).addBannerPreset(9, DyeColor.BLACK).getItem(), e -> {
            if (plugin.game.pvpTime >= 10) {
                plugin.game.pvpTime -= 10;
            }
        });

        timerPvpPlus60 = ClickableItem.of(new ItemCreator(Material.BANNER, 0).setName("§a+1 minute").setBasecolor(DyeColor.WHITE).addBannerPreset(10, DyeColor.BLACK).getItem(), e -> {
            plugin.game.pvpTime += 60;
        });


        timerPvpLess60 = ClickableItem.of(new ItemCreator(Material.BANNER, 0).setName("§c-1 minute").setBasecolor(DyeColor.WHITE).addBannerPreset(9, DyeColor.BLACK).getItem(), e -> {
            if (plugin.game.pvpTime >= 60) {
                plugin.game.pvpTime -= 60;
            }
        });

        timerPvpPlus600 = ClickableItem.of(new ItemCreator(Material.BANNER, 0).setName("§a+10 minutes").setBasecolor(DyeColor.WHITE).addBannerPreset(10, DyeColor.BLACK).getItem(), e -> {
            plugin.game.pvpTime += 600;
        });


        timerPvpLess600 = ClickableItem.of(new ItemCreator(Material.BANNER, 0).setName("§c-10 minutes").setBasecolor(DyeColor.WHITE).addBannerPreset(9, DyeColor.BLACK).getItem(), e -> {
            if (plugin.game.pvpTime >= 600) {
                plugin.game.pvpTime -= 600;
            }
        });

        contents.set(1, 1, timerPvpLess600);
        contents.set(1, 2, timerPvpLess60);
        contents.set(1, 3, timerPvpLess10);
        contents.set(1, 5, timerPvpPlus10);
        contents.set(1, 6, timerPvpPlus60);
        contents.set(1, 7, timerPvpPlus600);

    }

    @Override
    public void update(Player player, InventoryContents contents) {
        ClickableItem timer_pvp = ClickableItem.empty(new ItemCreator(Material.WATCH, 0).setLores(Collections.singletonList("§7Temps PvP: §f" + plugin.game.getTime(plugin.game.pvpTime))).setName("§6PvP").getItem());
        contents.set(1, 4, timer_pvp);
    }
}
