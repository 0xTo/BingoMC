package fr.theome.bingo.objects;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import fr.theome.bingo.Main;
import fr.theome.bingo.manager.WinManager;
import fr.theome.bingo.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    public ArrayList<BPlayer> bplayers=new ArrayList<>();
    public ArrayList<BPlayer> decoPlayers = new ArrayList<>();


    public State state;
    public int pvpTime;
    public int teamSize = 1;
    public BPlayerBoard board;
    public int tick;
    public boolean isPvPEnabled;
    public Main plugin;
    public int nb_row_or_item = 1;
    public WinCondition winCondition;

    public void create(Main plugin){
        try {
            WorldUtils.PasteBarrier(Bukkit.getWorld("lobby"),new Location(Bukkit.getWorld("lobby"),0,151,0));
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.state=State.Open;
        pvpTime=600;
        isPvPEnabled=false;
        DLocation.create();
        WinManager.init();
        this.plugin=plugin;
        winCondition = WinCondition.ROWS;
    }

    public void launch(){

        /*for(BPlayer bPlayer : bplayers){
            if(bPlayer.getTeam() == null){
                broadcast("§cAu moins un joueur n'est pas dans une Team, il est donc impossible de lancer la partie !");
                return;
            }
        }*/

        if(plugin.teamManager.getNotEmptyTeams().size()==1){
            broadcast("§cTous les joueurs sont dans la même équipe !");
            return;
        }

        tick = 0;

       for (BPlayer bplayer : bplayers){
           bplayer.getPlayer().getLocation().getWorld().setDifficulty(Difficulty.PEACEFUL);
       }

       if(teamSize == 1){
           for(BPlayer bplayer : bplayers){
               bplayer.setSpawn(DLocation.world);
           }
       } else {

           for (Team team : plugin.teamManager.getTeams()){
               team.setSpawn(DLocation.world);
               for(BPlayer bplayer : team.getBPlayers()){
                   bplayer.setSpawn(DLocation.world);
               }
           }

       }

       for (BPlayer bplayer : bplayers){
           if(bplayer.getTeam()!=null){
               bplayer.getPlayer().setDisplayName(bplayer.getTeam().getPrefix() + bplayer.getPlayer().getName());
           }
           bplayer.getPlayer().teleport(bplayer.getSpawn());
           broadcast("Téléportation de §7" + bplayer.getPlayer().getName());
           bplayer.getPlayer().getInventory().clear();
           bplayer.getPlayer().getInventory().setArmorContents(null);
           bplayer.getPlayer().setGameMode(GameMode.SURVIVAL);
           bplayer.getPlayer().setHealth(20);
           bplayer.getPlayer().setFoodLevel(40);
           bplayer.getPlayer().setExp(0);
           bplayer.getPlayer().setLevel(0);
           bplayer.getPlayer().getLocation().getWorld().setTime(0);
           bplayer.getPlayer().getLocation().getWorld().setStorm(false);
           bplayer.getPlayer().getLocation().getWorld().setThundering(false);
           bplayer.getPlayer().getLocation().getWorld().setDifficulty(Difficulty.PEACEFUL);
           bplayer.getPlayer().getLocation().getWorld().setDifficulty(Difficulty.NORMAL);
           bplayer.getPlayer().setPlayerListName(bplayer.getPlayer().getDisplayName());


           board = Netherboard.instance().createBoard(bplayer.getPlayer(), "§6§lBingo");
           board.setAll(
                   "§7§m+-------------------+",
                   "§c>> Équipe: " + bplayer.getTeam().getName(),
                   "§c>> Joueurs: §f" + Bukkit.getOnlinePlayers().size(),
                   "§c>> Win condition: §f" + winCondition.toString() + " : " + nb_row_or_item,
                   "§c>> PVP: ",
                   "§c>> Temps: ",
                   "§f§7§m+-------------------+"
           );
       }
        this.state=State.Launched;
    }

    public void win(Team team){

        this.state=State.End;
        for (BPlayer bplayer : bplayers){
            TitleUtils.sendTitle(bplayer.getPlayer(),"§bL'équipe " + team.getName()+ " §bremporte la partie en " + getTime((int)Math.floor(tick/20)) + " !");
            bplayer.getPlayer().sendMessage("§bL'équipe " + team.getName()+ " §bremporte la partie en " + getTime((int)Math.floor(tick/20)) + " !");
            bplayer.getPlayer().setGameMode(GameMode.SPECTATOR);
            bplayer.getPlayer().getLocation().getWorld().setDifficulty(Difficulty.PEACEFUL);
            board = Netherboard.instance().getBoard(bplayer.getPlayer());
            board.set("§c>> Temps : §f" + getTime((int)Math.floor(tick/20)), 2);
        }
    }

    public void makePlayerJoin(Player player){

        player.teleport(DLocation.lobby);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.setHealth(20);
        player.setFoodLevel(40);
        broadcast(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + player.getDisplayName());
        ItemStack ItemTeams = new ItemCreator(Material.BANNER, 0).setName("§cSélection des Teams").getItem();
        player.getInventory().setItem(0, ItemTeams);
        player.updateInventory();

        if (player.hasPermission("bingo.config")){
            ItemStack ItemConfig = new ItemCreator(Material.COMPASS, 0).setName("§5Config").getItem();
            player.getInventory().setItem(4, ItemConfig);
            player.updateInventory();
        }
    }

    public void makePlayerLeave(Player player){

        broadcast(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + player.getDisplayName());
        if (getBPlayer(player).getTeam()==null) return;
        getBPlayer(player).getTeam().removeBPlayers(getBPlayer(player));
        getBPlayer(player).setTeam(null,true);
        bplayers.remove(getBPlayer(player));
    }

    public void setTeamSize(int nbr){
        if(nbr>=1){
            teamSize=nbr;
        } else {
            teamSize=1;
        }
    }

    public void setNb(int nb_row_or_item) {
        if (nb_row_or_item>0){
            this.nb_row_or_item = nb_row_or_item;
        }
    }

    public void switchWinCondition(){
        if (this.winCondition==WinCondition.ROWS){
            this.winCondition=WinCondition.ITEMS;
        } else {
            this.winCondition=WinCondition.ROWS;
        }
    }

    public BPlayer getBPlayer(Player player){

        for(BPlayer bPlayer : bplayers){
            if(bPlayer.getPlayer()==player){
                return bPlayer;
            }
        }
        return null;
    }

    public void tick(){
        if (tick==pvpTime*20 && state.equals(State.Launched)){
            for(Player player:Bukkit.getOnlinePlayers()){
                TitleUtils.sendTitle(player, "§cLe pvp est activé !");
            }
            isPvPEnabled=true;
        }
        if (tick==600 && state.equals(State.Launched)){

            for(Player player:Bukkit.getOnlinePlayers()){
                TitleUtils.sendTitle(player, ChatColor.GREEN + "Les dégats sont désormais actifs !");
            }

        }
        if (tick % 20 == 0 && state.equals(State.Launched)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                board = Netherboard.instance().getBoard(player);
                board.set("§c>> Joueurs: §f" + bplayers.size(), 1);
                board.set("§c>> Temps : §f" + getTime(tick/20), 2);
                if (pvpTime-(tick/20) > 0) {
                    board.set("§c>> PVP: §f" + getTime(pvpTime-(tick/20)), 3);
                } else {
                    board.set("§c>> PVP: §aActivé", 3);
                }
            }
        }


        tick++;




    }

    public void setSpectatorMode(Player player, String msg){

        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.GREEN + msg);

    }

    public void broadcast(String message){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(message);
        }
    }

    public void setPvpTime(int nbr){
        if (nbr>=0){
            pvpTime=nbr;
        }
        else{
            pvpTime=0;
        }
    }

    public String getTime(int s) {
        int m = 0;
        int h = 0;

        while (s >= 60) {
            m++;
            s -= 60;
        }
        while (m >= 60) {
            h++;
            m -= 60;
        }

        String msg = "";
        if (h > 0) {
            if (h < 10) {
                msg += "0" + h + ":";
            } else {
                msg += h + ";";
            }
        }
        if (m < 10) {
            msg += "0" + m + ":";
        } else {
            msg += m + ":";
        }
        if (s < 10) {
            msg += "0" + s;
        } else {
            msg += s;
        }

        return msg;
    }

}