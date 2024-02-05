package fr.theome.bingo.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.theome.bingo.Main;
import fr.theome.bingo.commands.CommandTeamInfo;
import fr.theome.bingo.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class TeamManagerMenu  extends CommandTeamInfo implements InventoryProvider {

    private final Main plugin;
    public TeamManagerMenu() { this.plugin = (Main) Bukkit.getServer().getPluginManager().getPlugin("Bingo"); }

    public static final SmartInventory TEAM = SmartInventory.builder()
            .id("teamManagerGui")
            .provider(new TeamManagerMenu())
            .size(3, 9)
            .title(ChatColor.DARK_PURPLE + "TeamManager")
            .manager(JavaPlugin.getPlugin(Main.class).getInvManager())
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        ItemStack blackGlass = new ItemCreator(Material.STAINED_GLASS_PANE, 15).setName("").getItem();

        ItemStack teamRemove1 = new ItemCreator(Material.BANNER, 0).setName("§c-1 joueur").setBasecolor(DyeColor.WHITE).addBannerPreset(9, DyeColor.BLACK).getItem();

        ItemStack teamAdd1 = new ItemCreator(Material.BANNER, 0).setName("§a+1 joueur").setBasecolor(DyeColor.WHITE).addBannerPreset(10, DyeColor.BLACK).getItem();

        ItemStack Back = new ItemCreator(Material.BARRIER, 0).setName("§cRetour").getItem();


        inventoryContents.fill(ClickableItem.empty(new ItemStack(blackGlass)));

        inventoryContents.set(1, 2, ClickableItem.of(new ItemStack(teamRemove1),
                e -> plugin.game.setTeamSize(plugin.game.teamSize-1)));

        inventoryContents.set(1, 6, ClickableItem.of(new ItemStack(teamAdd1),
                e -> plugin.game.setTeamSize(plugin.game.teamSize+1)));

        inventoryContents.set(0, 8, ClickableItem.of(new ItemStack(Back),
                e -> ConfigMenu.CONFIG.open(player)));

        /*
        ItemStack blackglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta blackglassM = blackglass.getItemMeta();
        blackglassM.setDisplayName(" ");
        blackglass.setItemMeta(blackglassM);

        ItemStack back = new ItemStack(Material.RED_ROSE);
        ItemMeta backM = back.getItemMeta();
        backM.setDisplayName(ChatColor.RED + "Retour");
        back.setItemMeta(backM);

        ItemStack redglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta redglassM = redglass.getItemMeta();
        redglassM.setDisplayName(" ");
        redglass.setItemMeta(redglassM);

        ItemStack item = new ItemStack(Material.SKULL_ITEM, team_size, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(ChatColor.GOLD + "Team de " + team_size);
        skull.setOwner(player.getName());
        item.setItemMeta(skull);

        ItemStack solo = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skulll = (SkullMeta) solo.getItemMeta();
        skulll.setDisplayName(ChatColor.GOLD + "FFA");
        skulll.setOwner(player.getName());
        solo.setItemMeta(skulll);

        contents.set(0, 8, ClickableItem.of(new ItemStack(back),
                e -> ConfigMenu.CONFIG.open(player)));

        contents.set(1, 0, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 2, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 3, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 5, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 6, ClickableItem.empty(new ItemStack(redglass)));
        contents.set(1, 8, ClickableItem.empty(new ItemStack(redglass)));

        ItemStack itemm = new ItemStack(Material.SKULL_ITEM, team_size + 1, (short) 3);
        SkullMeta skullm = (SkullMeta) itemm.getItemMeta();
        skullm.setDisplayName(ChatColor.GOLD + "Team de " + team_size);
        skullm.setOwner(player.getName());
        itemm.setItemMeta(skullm);

        if (team_size == 1) {
            contents.set(1, 4, ClickableItem.empty(new ItemStack(solo)));
        } else if (team_size > 1) {
            contents.set(1, 4, ClickableItem.empty(new ItemStack(item)));
        }

        contents.fillRect(0, 0, 0, 7, ClickableItem.empty(new ItemStack(blackglass)));
        contents.fillRect(2, 0, 2, 8, ClickableItem.empty(new ItemStack(blackglass)));*/

    }

    @Override
    public void update(Player player, InventoryContents contents) {

        ItemStack teamSizeShow = new ItemCreator(Material.SKULL_ITEM, 0).setName("§bNombre de joueurs par équipe : "+plugin.game.teamSize).setBasecolor(DyeColor.WHITE).addBannerPreset(7, DyeColor.BLUE).getItem();
        contents.set(1, 4, ClickableItem.empty(new ItemStack(teamSizeShow)));

        /*ItemStack item = new ItemStack(Material.SKULL_ITEM, team_size, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(ChatColor.GOLD + "Team de " + team_size);
        skull.setOwner(player.getName());
        item.setItemMeta(skull);

        ItemStack solo = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skulll = (SkullMeta) solo.getItemMeta();
        skulll.setDisplayName(ChatColor.GOLD + "FFA");
        skulll.setOwner(player.getName());
        solo.setItemMeta(skulll);

        contents.set(1, 4, ClickableItem.of(new ItemStack(item), e -> {
            if (e.isLeftClick()) {
                team_size++;
            } else if (e.isRightClick() && team_size != 1) {
                team_size--;
            }
        }));*/
    }
}
