package fr.theome.bingo.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.theome.bingo.Main;
import fr.theome.bingo.utils.ItemCreator;
import fr.theome.bingo.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class ConfigMenu implements InventoryProvider {

    public Main plugin;

    public ConfigMenu() {

        this.plugin = (Main) Bukkit.getServer().getPluginManager().getPlugin("Bingo");

    }

    public static final SmartInventory CONFIG = SmartInventory.builder()
            .id("configGui")
            .provider(new ConfigMenu())
            .size(5, 9)
            .title(ChatColor.DARK_PURPLE + "Config")
            .manager(JavaPlugin.getPlugin(Main.class).getInvManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {

        ItemStack purpleglass = new ItemCreator(Material.STAINED_GLASS_PANE, (byte) 10).setName(" ").getItem();

        ItemStack grillebingo = new ItemCreator(Material.PAINTING, 1).setName(ChatColor.AQUA + "Afficher / Modifier le Bingo").setLores(Collections.singletonList(ChatColor.GRAY + "Affiche la grille de Bingo et permet de la modifier")).getItem();

        ItemStack wlmanager = new ItemCreator(Material.PAPER, 1).setName(ChatColor.AQUA + "Modifier la WhiteList").setLores(Collections.singletonList(ChatColor.GRAY + "Permet de gerer la WhiteList")).getItem();

        ItemStack doorleave = new ItemCreator(Material.BARRIER, 1).setName(ChatColor.RED + "Fermer").getItem();

        ItemStack startSlime = new ItemCreator(Material.SLIME_BLOCK, 1).setName(ChatColor.GREEN + "Démarrer la partie").getItem();

        ItemStack stopBlock = new ItemCreator(Material.REDSTONE_BLOCK, 1).setName(ChatColor.RED + "Annuler").getItem();

        ItemStack bannerteam = new ItemCreator(Material.BANNER, (byte) 7).setName(ChatColor.AQUA + "Gestionnaire de Team").setLores(Collections.singletonList(ChatColor.GRAY + "Permet de gerer les différents parametres de Team")).getItem();

        ItemStack commandsutiles = new ItemCreator(Material.BOOK_AND_QUILL,0).setName(ChatColor.AQUA + "Commandes utiles").setLores(Collections.singletonList(ChatColor.GRAY + "Affiche les commandes principales")).getItem();

        ItemStack winGui = new ItemCreator(Material.WATCH, 0).setName("§4Conditions de victoires").getItem();

        ItemStack pvpGui = new ItemCreator(Material.IRON_SWORD, 0).setName("§4Config du pvp").getItem();

        contents.fill(ClickableItem.empty(new ItemStack(purpleglass)));

        contents.set(2, 2, ClickableItem.of(new ItemStack(grillebingo),
                e -> BingoEditorMenu.BINGOEDITOR.open(player)));
        contents.set(2, 6, ClickableItem.of(new ItemStack(wlmanager),
                e -> WhitelistEditorMenu.WHITELISTCONFIG.open(player)));
        contents.set(2, 4, ClickableItem.of(new ItemStack(bannerteam),
                e -> TeamManagerMenu.TEAM.open(player)));
        contents.set(3, 4, ClickableItem.of(new ItemStack(commandsutiles),
                e -> CommandsMenu.COMMANDS.open(player)));
        contents.set(0, 8, ClickableItem.of(new ItemStack(doorleave),
                e -> player.closeInventory()));
        contents.set(3, 2, ClickableItem.of(winGui, e -> WinEditorMenu.WINEDITOR.open(player)));
        contents.set(3,3,ClickableItem.of(pvpGui,e-> PvpConfigMenu.PvpGUI.open(player)));


        contents.set(1, 4, ClickableItem.of(new ItemStack(startSlime),
                e -> {
                    plugin.game.launch();
                    TitleUtils.sendTitle((List<Player>) Bukkit.getOnlinePlayers(), 0, 1, 0, "§2Lancement de la partie!", "§aChargement du terrain ...");
                }));


    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        int state = inventoryContents.property("state", 0);
        inventoryContents.setProperty("state", state + 1);

        if (state % 5 != 0)
            return;
    }
}
