package fr.theome.bingo.utils;

import fr.theome.bingo.Main;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {

    private Main plugin;
    private ArrayList<Team> teams;

    public TeamManager(Main plugin){

        this.plugin = plugin;
        teams = new ArrayList<>();

        for (int index = 0; index < 28; index++) createTeam();
    }

    public Team createTeam() { return createTeam(teams.size()); }

    public Team createTeam(int modulo){

        String prefix = "";
        String name = "";
        ItemCreator item;
        DyeColor baseColor = null;
        int bannerPreset = 0;

        int rest = modulo%7;
        switch(rest){

            case 0:
                prefix = "§4";
                name = "§4Rouge";
                baseColor = DyeColor.RED;
                break;
            case 1:
                prefix = "§b";
                name = "§bBleu";
                baseColor = DyeColor.BLUE;
                break;
            case 2:
                prefix = "§a";
                name = "§aVerts";
                baseColor = DyeColor.GREEN;
                break;
            case 3:
                prefix = "§e";
                name = "§eJaune";
                baseColor = DyeColor.YELLOW;
                break;
            case 4:
                prefix = "§6";
                name = "§6Orange";
                baseColor = DyeColor.ORANGE;
                break;
            case 5:
                prefix = "§5";
                name = "§5Violette";
                baseColor = DyeColor.PURPLE;
                break;
            case 6:
                prefix = "§d";
                name = "§dRose";
                baseColor = DyeColor.PINK;
                break;

        }

        //Rajouter logo en fonction du nombre
        int dividend = modulo/7;
        switch (dividend){
            case 1:
                prefix += " ♡ ";
                name += " ♡ ";
                bannerPreset = 4;
                break;
            case 2:
                prefix += " ✘ ";
                name += " ✘ ";
                bannerPreset = 6;
                break;
            case 3:
                prefix += " ☯ ";
                name += " ☯ ";
                bannerPreset = 7;
                break;
        }

        if (bannerPreset == 0){
            item = new ItemCreator(Material.BANNER, 0).setBasecolor(baseColor);
        } else {
            item = new ItemCreator(Material.BANNER, 0).setBasecolor(baseColor).addBannerPreset(bannerPreset, DyeColor.WHITE);
        }

        return createTeam(prefix, name, item);

    }

    public Team createTeam(String prefix, String name){
        return createTeam(prefix, name, new ItemCreator(Material.BANNER, 0).setBasecolor(DyeColor.BLACK));
    }

    public Team createTeam(String prefix, String name, ItemCreator item) {

        Team team = new Team(prefix, name, item);
        teams.add(team);
        return team;

    }

    public List<Team> getAllTeams(){

        ArrayList<Team> allTeams = new ArrayList<>(teams);
        return allTeams;

    }

    public List<Team> getTeams() {
        return teams;
    }

    public Team getTeam(String name){
        for (Team team : teams){
            if (team.getName().equals(name)) return team;
        }
        return null;
    }

    public List<Team> getNotFullTeams(){

        /*Envoyer les team les plus vides en premiers*/
        int lowest = 100;
        for (Team team : getTeams()){
            if (team.getBPlayers().size() < lowest) lowest = team.getBPlayers().size();
        }

        ArrayList<Team> teams = new ArrayList<>();
        for (Team team : getTeams()){
            if (team.getBPlayers().size() == lowest) teams.add(team);
        }
        return teams;
    }

    public List<Team> getNotEmptyTeams(){
        ArrayList<Team> teams = new ArrayList<>();
        for (Team team : getTeams()){
            if (team.getBPlayers().size() > 0) teams.add(team);
        }
        return teams;
    }

    public void checkEveryTeams(){
        List<Team> activatedTeams = getTeams();
        for (Team team : getAllTeams()){
            /*Check team vides - inactives -*/
            if(!activatedTeams.contains(team)){
                ArrayList<BPlayer> bPlayers = new ArrayList<>(team.getBPlayers());
                for(BPlayer bPlayer : bPlayers){
                    bPlayer.setTeam(null, true);
                }
            }

            /*Check la taille de la team*/
            if (team.getBPlayers().size() > plugin.game.teamSize){
                team.getBPlayers().get(plugin.game.teamSize - 1).setTeam(null, true);
            }
        }
    }

    public void killTeam(Team team) { teams.remove(team); }
    public void resTeam(Team team) { teams.add(team); }

    public void updateTeams(Team team){

        if (team.getBPlayers().size() == 0) {
            if (teams.contains(team)) { teams.remove(team); }
        }
    }

    public void deleteTeam(Team team){
        teams.remove(team);
        for (BPlayer bPlayer : new ArrayList<>(team.getBPlayers())) {
            bPlayer.setTeam(null, true);
        }
    }

    public void deleteLastTeam() {
        Team team = teams.get(teams.size() - 1);
        deleteTeam(team);
    }

}
