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

public class WinEditorMenu implements InventoryProvider {

    private final Main plugin;
    public WinEditorMenu() { this.plugin = (Main) Bukkit.getServer().getPluginManager().getPlugin("Bingo"); }

    public static final SmartInventory WINEDITOR = SmartInventory.builder()
            .id("winGui")
            .provider(new WinEditorMenu())
            .size(3, 9)
            .title(ChatColor.DARK_PURPLE + "Win EDITOR")
            .manager(JavaPlugin.getPlugin(Main.class).getInvManager())
            .build();


    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack black_glasses = new ItemCreator(Material.STAINED_GLASS_PANE,15).setName(" ").getItem();
        contents.fill(ClickableItem.empty(black_glasses));

        ItemStack item_remove = new ItemCreator(Material.BANNER, 0).setName("§c-1 item/ligne").setBasecolor(DyeColor.WHITE).addBannerPreset(9, DyeColor.BLACK).getItem();

        ItemStack item_add = new ItemCreator(Material.BANNER, 0).setName("§a+1 item/ligne").setBasecolor(DyeColor.WHITE).addBannerPreset(10, DyeColor.BLACK).getItem();

        ItemStack Back = new ItemCreator(Material.BARRIER, 0).setName("§cRetour").getItem();

        contents.set(1, 2, ClickableItem.of(new ItemStack(item_remove),
                e -> plugin.game.setNb(plugin.game.nb_row_or_item-1)));

        contents.set(1, 6, ClickableItem.of(new ItemStack(item_add),
                e -> plugin.game.setNb(plugin.game.nb_row_or_item+1)));

        contents.set(0, 8, ClickableItem.of(new ItemStack(Back),
                e -> ConfigMenu.CONFIG.open(player)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        ItemStack nb_item_rows = new ItemCreator(Material.SKULL_ITEM, 0).setName("§bNombre de ligne ou d'item : "+plugin.game.nb_row_or_item).getItem();
        contents.set(1, 4, ClickableItem.empty(new ItemStack(nb_item_rows)));

        ItemStack item =  new ItemCreator(Material.WATCH,0).setName("Mode de victoire : "+plugin.game.winCondition.toString()).getItem();
        contents.set(0,4,ClickableItem.of(item,e->{
            plugin.game.switchWinCondition();
        }));
    }
}
