package fr.theome.bingo.utils;

import fr.theome.bingo.Main;
import fr.theome.bingo.objects.State;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BPlayer {

    private Main plugin;
    public Player player;
    public Team team;
    public Location spawn;


    public BPlayer(Player player, Main plugin){
        this.plugin = plugin;
        this.player=player;
        this.team=null;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player) { this.player = player; }

    public Team getTeam() { return team; }

    public void setTeam(Team team, boolean silent){

        ItemStack banner = new ItemStack(Material.BANNER, 1, (short)10);
        ItemMeta bannerM = banner.getItemMeta();
        bannerM.setDisplayName("§dSelection des Teams");
        banner.setItemMeta(bannerM);

        if (plugin.game.state.equals(State.Launched) && getTeam() != null) { plugin.teamManager.updateTeams(getTeam()); }

        if (team == null) {
            if (this.team != null) this.team.removeBPlayers(this);

            this.team = null;
            if(plugin.game.state.equals(State.Open)){

                getPlayer().getInventory().setItem(0, new ItemCreator(Material.BANNER, 0).setName("§cSélection des Teams").getItem());
                player.getInventory().setHelmet(null);

            }
            if (!silent) player.sendMessage(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + ChatColor.GRAY + "] " + ChatColor.RED + "Vous avez quitté votre équipe");
        } else {
            Team actualTeam = getTeam();
            if (actualTeam != null) { actualTeam.removeBPlayers(this); }

            if (plugin.game.state.equals(State.Open)) {

                getPlayer().getInventory().setItem(0, banner);
                player.getInventory().setHelmet(team.getItemCreator().getItem());

            }

            this.team = team;
            this.team.addBPlayer(this);

            if(!silent) player.sendMessage(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + ChatColor.GRAY + "] " + ChatColor.GREEN + "Vous avez rejoint l'equipe " + team.getName());

        }

        if (plugin.game.state.equals(State.Launched) && getTeam() != null)
            plugin.teamManager.updateTeams(getTeam());

    }

    public void setSpawn(Location spawn) { this.spawn = spawn; }
    public Location getSpawn() { return spawn; }

}
