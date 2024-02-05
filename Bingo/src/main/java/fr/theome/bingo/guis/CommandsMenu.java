package fr.theome.bingo.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.theome.bingo.Main;
import fr.theome.bingo.guis.sencondariesGuis.bingoHelperMenu;
import fr.theome.bingo.guis.sencondariesGuis.configHelperMenu;
import fr.theome.bingo.guis.sencondariesGuis.teaminfosHelperMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommandsMenu implements InventoryProvider {

    private final Random random = new Random();

    public static final SmartInventory COMMANDS = SmartInventory.builder()
            .id("commandsGui")
            .provider(new CommandsMenu())
            .size(3, 9)
            .title(ChatColor.DARK_PURPLE + "Helper")
            .manager(JavaPlugin.getPlugin(Main.class).getInvManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {

        List<String> loresList1 = new ArrayList<String>();
        List<String> loresList2 = new ArrayList<String>();
        List<String> loresList3 = new ArrayList<String>();

        loresList1.add(ChatColor.GRAY + "Clique gauche pour plus d'information");
        loresList1.add(ChatColor.GRAY + "Clique droit pour executer la commande");

        loresList2.add(ChatColor.GRAY + "Clique gauche pour plus d'information");
        loresList2.add(ChatColor.GRAY + "Clique droit pour executer la commande");

        loresList3.add(ChatColor.GRAY + "Clique gauche pour plus d'information");

        ItemStack bingohitem = new ItemStack(Material.EMPTY_MAP, 0);
        ItemMeta bingM = bingohitem.getItemMeta();
        bingM.setDisplayName(ChatColor.GOLD + "/bingo");
        bingM.setLore(loresList1);
        bingohitem.setItemMeta(bingM);

        ItemStack back = new ItemStack(Material.RED_ROSE);
        ItemMeta backM = back.getItemMeta();
        backM.setDisplayName(ChatColor.RED + "Retour");
        back.setItemMeta(backM);

        ItemStack confighelper = new ItemStack(Material.EMPTY_MAP, 0);
        ItemMeta confM = confighelper.getItemMeta();
        confM.setDisplayName(ChatColor.GOLD + "/config");
        confM.setLore(loresList2);
        confighelper.setItemMeta(confM);

        ItemStack blackglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
        ItemMeta blackglassM = blackglass.getItemMeta();
        blackglassM.setDisplayName(" ");
        blackglass.setItemMeta(blackglassM);

        ItemStack teaminfohelper = new ItemStack(Material.EMPTY_MAP, 0);
        ItemMeta teamM = teaminfohelper.getItemMeta();
        teamM.setDisplayName(ChatColor.GOLD + "/teaminfo");
        teamM.setLore(loresList3);
        teaminfohelper.setItemMeta(teamM);

        contents.set(1, 1, ClickableItem.of(new ItemStack(bingohitem), e -> {
            if(e.isLeftClick()){
                bingoHelperMenu.BINGOHELPER.open(player);
            }else if(e.isRightClick()){
                BingoMenu.BINGO.open(player);
            }
        }));
        contents.set(1, 4, ClickableItem.of(new ItemStack(confighelper), e -> {
            if(e.isLeftClick()){
                configHelperMenu.CONFIGHELPER.open(player);
            }else if(e.isRightClick()){
                ConfigMenu.CONFIG.open(player);
            }
        }));
        contents.set(1, 7, ClickableItem.of(new ItemStack(teaminfohelper), e -> {
            if(e.isLeftClick()){
                teaminfosHelperMenu.TEAMINFOHELPER.open(player);
            }
        }));
        contents.fillRect(0, 0, 0, 7, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
        contents.fillRect(2, 0, 2, 8, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
        contents.set(0, 8, ClickableItem.of(new ItemStack(back),
                e -> ConfigMenu.CONFIG.open(player)));
        contents.set(1, 0, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
        contents.set(1, 8, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
        //contents.fillRow(7, ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        int state = contents.property("state", 0);
        contents.setProperty("state", state + 1);

        if(state % 5 != 0)
            return;

        short rdmer = (short) random.nextInt(15);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, rdmer);
        //contents.fillRow(2, ClickableItem.empty(glass));
        contents.fillRect(0, 0, 0, 7, ClickableItem.empty(new ItemStack(glass)));
        contents.fillRect(2, 0, 2, 8, ClickableItem.empty(new ItemStack(glass)));
        contents.set(1, 0, ClickableItem.empty(new ItemStack(glass)));
        contents.set(1, 8, ClickableItem.empty(new ItemStack(glass)));
    }
}
