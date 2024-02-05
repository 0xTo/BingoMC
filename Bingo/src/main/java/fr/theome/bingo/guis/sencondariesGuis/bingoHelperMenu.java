package fr.theome.bingo.guis.sencondariesGuis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.theome.bingo.Main;
import fr.theome.bingo.guis.CommandsMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class bingoHelperMenu implements InventoryProvider {

    public static final SmartInventory BINGOHELPER = SmartInventory.builder()
            .id("bingoHelper")
            .provider(new bingoHelperMenu())
            .size(3, 9)
            .title("§5BingoCommand")
            .manager(JavaPlugin.getPlugin(Main.class).getInvManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {

        ItemStack blackglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
        ItemMeta blackglassM = blackglass.getItemMeta();
        blackglassM.setDisplayName(" ");
        blackglass.setItemMeta(blackglassM);

        ItemStack redglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        ItemMeta redglassM = redglass.getItemMeta();
        redglassM.setDisplayName(" ");
        redglass.setItemMeta(redglassM);

        ItemStack back = new ItemStack(Material.RED_ROSE);
        ItemMeta backM = back.getItemMeta();
        backM.setDisplayName(ChatColor.RED + "Retour");
        back.setItemMeta(backM);

        ItemStack bingocommand = new ItemStack(Material.EMPTY_MAP);
        ItemMeta bingM = bingocommand.getItemMeta();
        bingM.setDisplayName(ChatColor.GOLD + "Commande");
        bingM.setLore(Collections.singletonList(ChatColor.RED + "/bingo"));
        bingocommand.setItemMeta(bingM);

        ItemStack bingodesc = new ItemStack(Material.EMPTY_MAP);
        ItemMeta bingdeM = bingodesc.getItemMeta();
        bingdeM.setDisplayName(ChatColor.GOLD + "Description");
        bingdeM.setLore(Collections.singletonList(ChatColor.RED + "Permet d'afficher la grille du bingo"));
        bingodesc.setItemMeta(bingdeM);

        ItemStack bingoperm = new ItemStack(Material.EMPTY_MAP);
        ItemMeta bingopM = bingoperm.getItemMeta();
        bingopM.setDisplayName(ChatColor.GOLD + "Permissions");
        bingopM.setLore(Collections.singletonList(ChatColor.RED + "Joueurs"));
        bingoperm.setItemMeta(bingopM);

        contents.set(0, 8, ClickableItem.of(new ItemStack(back),
                e -> CommandsMenu.COMMANDS.open(player)));

        contents.set(1, 0, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 2, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 3, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 5, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 6, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 8, ClickableItem.empty(new ItemStack(redglass)));

        contents.set(1, 1, ClickableItem.empty(new ItemStack(bingocommand)));
        contents.set(1, 4, ClickableItem.empty(new ItemStack(bingodesc)));
        contents.set(1, 7, ClickableItem.empty(new ItemStack(bingoperm)));
        contents.fillRect(0, 0, 0, 7, ClickableItem.empty(new ItemStack(blackglass)));
        contents.fillRect(2, 0, 2, 8, ClickableItem.empty(new ItemStack(blackglass)));

    }

    @Override
    public void update(Player player, InventoryContents contents) {}
}
