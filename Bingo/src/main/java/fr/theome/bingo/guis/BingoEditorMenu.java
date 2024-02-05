package fr.theome.bingo.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.theome.bingo.Main;
import fr.theome.bingo.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

//Ouvrir avec un click sur "PAINTING" ("grillebingo") dans ConfigMenu
//Permet de configurer le Bingo


public class BingoEditorMenu implements InventoryProvider {
    public static final SmartInventory BINGOEDITOR = SmartInventory.builder()
            .id("bingoEditoGui")
            .provider(new BingoEditorMenu())
            .size(5, 9)
            .title(ChatColor.DARK_PURPLE + "Bingo" + ChatColor.RED + "Editor")
            .manager(JavaPlugin.getPlugin(Main.class).getInvManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {

        Main main = JavaPlugin.getPlugin(Main.class);
        int[] x = {2, 3, 4, 5, 6};
        int[] y = {0, 1, 2, 3, 4};
        int xi = 0;
        int yi = 0;

        ItemStack blackglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta blackglassM = blackglass.getItemMeta();
        blackglassM.setDisplayName(" ");
        blackglass.setItemMeta(blackglassM);

        ItemStack redglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta redglassM = redglass.getItemMeta();
        redglassM.setDisplayName(ChatColor.BLACK + "Bingo" + ChatColor.DARK_PURPLE + "Editor");
        redglass.setItemMeta(redglassM);

        ItemStack lightblackglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
        ItemMeta lbM = lightblackglass.getItemMeta();
        lbM.setDisplayName(" ");
        lightblackglass.setItemMeta(lbM);

        ItemStack clock = new ItemCreator(Material.WATCH,0).setName("Retirage de la grille").getItem();

        ItemStack back = new ItemCreator(Material.RED_ROSE,0).setName(ChatColor.RED + "Retour").getItem();

        ItemStack orangeglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        ItemMeta orangeglassM = orangeglass.getItemMeta();
        orangeglassM.setDisplayName(" ");
        orangeglass.setItemMeta(orangeglassM);

        contents.fillColumn(1, ClickableItem.empty(lightblackglass));
        contents.fillColumn(7, ClickableItem.empty(lightblackglass));

        contents.set(0, 0, ClickableItem.empty(redglass));
        contents.set(2, 0, ClickableItem.empty(redglass));
        contents.set(4, 0, ClickableItem.empty(redglass));

        contents.set(2, 8, ClickableItem.empty(redglass));
        contents.set(4, 8, ClickableItem.empty(redglass));

        contents.set(1, 0, ClickableItem.empty(blackglass));
        contents.set(3, 0, ClickableItem.empty(blackglass));

        contents.set(1, 8, ClickableItem.empty(blackglass));
        contents.set(3, 8, ClickableItem.empty(blackglass));

        contents.set(0, 8, ClickableItem.of(back,
                e -> ConfigMenu.CONFIG.open(player)));



        for (Material mat : main.getItems_list()) {
            contents.set(y[yi], x[xi], ClickableItem.empty(new ItemStack(mat)));
            xi = (xi + 1) % 5;
            if (xi == 0) {
                yi = (yi + 1) % 5;
            }
        }

        contents.set(4,8,ClickableItem.of(clock,e->{
            for (Player p : Bukkit.getOnlinePlayers()){
                p.closeInventory();
            }
            main.generateItems();
            BINGOEDITOR.open(player);
        }));


    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }
}