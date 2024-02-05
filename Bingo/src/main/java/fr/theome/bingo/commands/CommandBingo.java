package fr.theome.bingo.commands;

import fr.theome.bingo.Main;
import fr.theome.bingo.guis.BingoMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBingo implements CommandExecutor {

    public Main plugin;

    public CommandBingo(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.game.getBPlayer(player) != null) {
                if (plugin.game.getBPlayer(player).getTeam() != null) {
                    BingoMenu.BINGO.open(player);
                } else {
                    player.sendMessage("Merci de rejoindre une équipe pour voir le bingo");
                }
            }
        }
        return true;
    }
}