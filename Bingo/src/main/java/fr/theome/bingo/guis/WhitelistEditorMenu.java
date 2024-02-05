package fr.theome.bingo.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.theome.bingo.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class WhitelistEditorMenu implements InventoryProvider {

    public static final SmartInventory WHITELISTCONFIG = SmartInventory.builder()
            .id("wlGui")
            .provider(new WhitelistEditorMenu())
            .size(3, 9)
            .title(ChatColor.DARK_PURPLE + "Whitelist")
            .manager(JavaPlugin.getPlugin(Main.class).getInvManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents){

        ItemStack blackglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
        ItemMeta blackglassM = blackglass.getItemMeta();
        blackglassM.setDisplayName(" ");
        blackglass.setItemMeta(blackglassM);

        ItemStack back = new ItemStack(Material.RED_ROSE);
        ItemMeta backM = back.getItemMeta();
        backM.setDisplayName(ChatColor.RED + "Retour");
        back.setItemMeta(backM);

        ItemStack redglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        ItemMeta redglassM = redglass.getItemMeta();
        redglassM.setDisplayName(" ");
        redglass.setItemMeta(redglassM);

        contents.set(0, 8, ClickableItem.of(new ItemStack(back),
                e -> ConfigMenu.CONFIG.open(player)));

        contents.set(1, 0, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 2, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 3, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 5, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 6, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 8, ClickableItem.empty(new ItemStack(redglass)));

        contents.fillRect(0, 0, 0, 7, ClickableItem.empty(new ItemStack(blackglass)));
        contents.fillRect(2, 0, 2, 8, ClickableItem.empty(new ItemStack(blackglass)));

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {}
}
