package fr.theome.bingo.listeners;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import fr.theome.bingo.Main;
import fr.theome.bingo.guis.ConfigMenu;
import fr.theome.bingo.guis.TeamPicker;
import fr.theome.bingo.manager.WinManager;
import fr.theome.bingo.objects.State;
import fr.theome.bingo.utils.BPlayer;
import fr.theome.bingo.utils.WinCondition;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MainListeners implements Listener{

    public Main plugin;
    public MainListeners(Main plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        event.setJoinMessage(null);

        if(plugin.game.state.equals(State.Open)){
            plugin.game.makePlayerJoin(player);
            plugin.game.bplayers.add(new BPlayer(player, plugin));
        } else {

            boolean remove = false;
            for(BPlayer bPlayer : plugin.game.decoPlayers){

                if (bPlayer.getPlayer().getName().equals(player.getName())){
                    bPlayer.setPlayer(player);
                    plugin.game.decoPlayers.remove(bPlayer);
                    bPlayer.getPlayer().setDisplayName(bPlayer.getTeam().getPrefix() + bPlayer.getPlayer().getName());
                    bPlayer.getPlayer().setPlayerListName(player.getDisplayName());
                    remove = true;
                    break;
                }
            }

            if(remove){ plugin.game.broadcast(player.getName() + "vient de se reconnecter."); }
            else{ plugin.game.setSpectatorMode(player, "La partie a déjà commencé. Vous êtes spectateur"); }

        }

        if (plugin.game.state.equals(State.Launched)){
            BPlayerBoard board = Netherboard.instance().createBoard(player.getPlayer(), "§6§lBingo");
            String teamName;
            if (plugin.game.getBPlayer(player)==null){
                teamName="§7Spectateur";
            }
            else{
                teamName=plugin.game.getBPlayer(player).getTeam().getName();
            }
            board.setAll(
                    "§7§m+-------------------+",
                    "§c>> Équipe: " + teamName,
                    "§c>> Joueurs: §f" + plugin.game.bplayers.size(),
                    "§c>> Win condition: §f" + plugin.game.winCondition.toString() + " : " + plugin.game.nb_row_or_item,
                    "§c>> PVP: ",
                    "§c>> Temps: ",
                    "§f§7§m+-------------------+"
            );
        }


        player.updateInventory();

    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event){

        event.setQuitMessage(null);
        Player player = event.getPlayer();

        if(plugin.game.getBPlayer(player)==null) return;

        if(plugin.game.state.equals(State.Open)){ plugin.game.makePlayerLeave(player); }
        if(plugin.game.state.equals(State.Launched)){
            plugin.game.broadcast(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + player.getDisplayName() + ChatColor.YELLOW + "a quitté la partie.");
            plugin.game.decoPlayers.add(plugin.game.getBPlayer(player));
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack it = event.getItem();

        if(it == null) return;

        if(player.hasPermission("bingo.config") && it.getType() == Material.COMPASS && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§5Config")){
            ConfigMenu.CONFIG.open(player);
        }

        if(it.getType() == Material.BANNER && !plugin.game.state.equals(State.Launched)){
            TeamPicker.TEAMPICKER.open(player);
        }

    }

    @EventHandler
    public void onRecupItem(PlayerPickupItemEvent event){
        Main main = JavaPlugin.getPlugin(Main.class);
        ItemStack item = event.getItem().getItemStack();

        if(plugin.game.state.equals(State.Launched)){

            if(main.items_list.contains(item.getType())){

                Player player = event.getPlayer();
                if (plugin.game.getBPlayer(player)!=null){
                    plugin.game.getBPlayer(player).getTeam().items_get.set(main.items_list.indexOf(item.getType()),true);
                    if (!plugin.game.getBPlayer(player).getTeam().items_get.get(main.items_list.indexOf(item.getType()))){
                        player.getInventory().remove(item.getType());
                    }
                    if (plugin.game.winCondition.equals(WinCondition.ROWS)){
                        if (WinManager.checkWinRows(plugin.game.getBPlayer(player).getTeam(),plugin.game.nb_row_or_item)){
                            plugin.game.win(plugin.game.getBPlayer(player).getTeam());
                        }
                    } else {
                        if (WinManager.checkWinItems(plugin.game.getBPlayer(player).getTeam(),plugin.game.nb_row_or_item)){
                            plugin.game.win(plugin.game.getBPlayer(player).getTeam());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCraft(InventoryClickEvent event){
        Main main = JavaPlugin.getPlugin(Main.class);
        if(plugin.game.state.equals(State.Launched)){
            if(event.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack item = event.getCurrentItem();

                if(main.items_list.contains(item.getType())){
                    Player player = (Player) event.getWhoClicked();
                    if (plugin.game.getBPlayer(player) != null){
                        plugin.game.getBPlayer(player).getTeam().items_get.set(main.items_list.indexOf(item.getType()), true);
                        if (!plugin.game.getBPlayer(player).getTeam().items_get.get(main.items_list.indexOf(item.getType()))){
                            player.getInventory().remove(item.getType());
                        }
                        if (plugin.game.winCondition.equals(WinCondition.ROWS)){
                            if (WinManager.checkWinRows(plugin.game.getBPlayer(player).getTeam(),plugin.game.nb_row_or_item)){
                                plugin.game.win(plugin.game.getBPlayer(player).getTeam());
                            }
                        } else {
                            if (WinManager.checkWinItems(plugin.game.getBPlayer(player).getTeam(),plugin.game.nb_row_or_item)){
                                plugin.game.win(plugin.game.getBPlayer(player).getTeam());
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(!plugin.game.state.equals(State.Launched)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!plugin.game.state.equals(State.Launched)){
            event.setCancelled(true);
        } else if (plugin.game.tick<600){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamagebyEntity(EntityDamageByEntityEvent event){
        if(!plugin.game.state.equals(State.Launched)){
            event.setCancelled(true);
        } else if (plugin.game.tick<plugin.game.pvpTime*20){
            if (event.getEntity() instanceof Player && event.getDamager() instanceof Player){
                event.setCancelled(true);
            } else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow){
                if (((Arrow)event.getDamager()).getShooter() instanceof Player){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        if(!plugin.game.state.equals(State.Launched)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event){
        if(!plugin.game.state.equals(State.Launched)){
            event.setCancelled(true);
        }
    }

    //Cancel la possibilité de poser un bloc quand la partie n'est pas start
    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event){

        if(!plugin.game.state.equals(State.Launched)){
            event.setCancelled(true);
        }
    }

    //Cancel la possibilité de récup un item quand la partie n'est pas start
    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent event){

        if(!plugin.game.state.equals(State.Launched)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){

        if(!plugin.game.state.equals(State.Launched)){
            event.setCancelled(true);
        }

    }



}