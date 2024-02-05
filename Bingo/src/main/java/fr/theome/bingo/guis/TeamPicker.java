package fr.theome.bingo.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.theome.bingo.Main;
import fr.theome.bingo.utils.ItemCreator;
import fr.theome.bingo.utils.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class TeamPicker implements InventoryProvider {

    private Main plugin;
    public TeamPicker() { this.plugin = (Main) Bukkit.getServer().getPluginManager().getPlugin("Bingo"); }

    public static final SmartInventory TEAMPICKER = SmartInventory.builder()
            .id("teamPickerGUI")
            .provider(new TeamPicker())
            .size(6, 9)
            .title("» Ekip «")
            .manager(JavaPlugin.getPlugin(Main.class).getInvManager())
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        ItemStack blackGlass = new ItemCreator(Material.STAINED_GLASS_PANE, 15).setName(" ").getItem();
        inventoryContents.fill(ClickableItem.empty(new ItemStack(blackGlass)));

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

        int index=0;
        for (Team team : plugin.teamManager.getTeams()){
            ItemStack item = new ItemStack(team.getItemCreator()
                    .setName(team.getName())
                    .setLores(Arrays.asList("§7", "§7Joueurs: " + team.playerToString() + "§7 (" + team.getBPlayers().size() + "/" + plugin.game.teamSize + ")")).getItem());

            ClickableItem clickableItem = ClickableItem.of(item, e -> {
                if (team.getBPlayers().size() < plugin.game.teamSize) {
                    plugin.game.getBPlayer((Player) e.getWhoClicked()).setTeam(team, false);
                }});

            inventoryContents.set((int) plugin.slot(index + 10).get(0), (int) plugin.slot(index + 10).get(1), clickableItem);
            if (index == 6 || index == 15 || index == 24) {
                index += 3;
            } else index++;
        }

    }
}
