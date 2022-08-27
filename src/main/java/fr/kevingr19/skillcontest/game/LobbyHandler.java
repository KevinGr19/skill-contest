package fr.kevingr19.skillcontest.game;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.gui.hotbar.ItemInteractHandler;
import fr.kevingr19.skillcontest.gui.hotbar.PlayerInteractAction;
import fr.kevingr19.skillcontest.gui.inventory.SettingsGUI;
import fr.kevingr19.skillcontest.gui.inventory.TeamSelectGUI;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Optional;

/**
 * Class responsible for handling players while the game is waiting to start.
 */

public class LobbyHandler implements Listener {

    private static LobbyHandler inst;
    static LobbyHandler inst(){
        return inst;
    }

    public static boolean isActive(){
        return inst != null;
    }

    public static void activate(World world){
        if(inst != null) return;

        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setTime(0);
        
        inst = new LobbyHandler(world);
        inst.platform.generatePlatform();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(!player.isDead()) inst.setupPlayer(player, true);
        });

        Bukkit.getPluginManager().registerEvents(inst, Main.inst());
        Main.print("LobbyHandler activated.");
    }

    public static void deactivate(boolean safeTp){
        if(inst == null) return;

        inst.world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        inst.world.setGameRule(GameRule.DO_MOB_SPAWNING, true);

        inst.platform.removePlatform();
        HandlerList.unregisterAll(inst);
        if(safeTp) for(Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(Optional.ofNullable(player.getBedSpawnLocation()).orElse(inst.world.getSpawnLocation()));
        }

        inst = null;
        Main.print("LobbyHandler deactivated.");
    }

    private final World world;
    private final Platform platform;

    private final ItemInteractHandler itemHandler;
    private final ItemStack[] hostBar;
    private final ItemStack[] playerBar;

    private LobbyHandler(World world){
        this.world = world;
        this.platform = new Platform(world);

        itemHandler = new ItemInteractHandler("lobbyAction");
        hostBar = new ItemStack[9];
        playerBar = new ItemStack[9];
        prepareItemHandler();
    }

    private void prepareItemHandler(){
        itemHandler.setAction("settings", new PlayerInteractAction((player, e) -> {
            if(Game.inst().isHost(player)) SettingsGUI.openForPlayer(player);
        }));

        itemHandler.setAction("changeTeam", new PlayerInteractAction((player, e) -> {
            TeamSelectGUI.openForPlayer(player);
        }));

        hostBar[2] = itemHandler.setActionTag(ItemUtil.create(Material.REDSTONE_LAMP, 1, "§eParamètres"), "settings");

        ItemStack teamChange = itemHandler.setActionTag(ItemUtil.create(Material.WHITE_BANNER, 1, "§dChanger d'équipe"), "changeTeam");
        hostBar[6] = teamChange;
        playerBar[4] = teamChange;
    }

    public void setupPlayer(Player player){
        setupPlayer(player, true);
    }

    public void setupPlayer(Player player, boolean tp){
        if(tp) player.teleport(inst.platform.spawnLoc);
        player.setGameMode(GameMode.ADVENTURE);

        player.setHealth(19.9);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        player.getInventory().clear();
        setPlayerHotbar(player);
    }

    public void setPlayerHotbar(Player player){
        ItemStack[] hotbar = Game.inst().isHost(player) ? hostBar : playerBar;

        for(int i = 0; i < Math.min(hotbar.length, 9); i++)
            player.getInventory().setItem(i, hotbar[i]);
    }

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent e){
        e.setSpawnLocation(platform.spawnLoc);
        setupPlayer(e.getPlayer(), false);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        e.setRespawnLocation(platform.spawnLoc);
        setupPlayer(e.getPlayer(), false);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        for(int i = 0; i < e.getDrops().size(); i++)
            if(itemHandler.hasAction(e.getDrops().get(i))) e.getDrops().set(i, null);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onFoodChange(FoodLevelChangeEvent e){
        e.setFoodLevel(20);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player && e.getCause() != EntityDamageEvent.DamageCause.VOID) e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent e){
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent e){

        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = e.getPlayer();
        PlayerInteractAction action = itemHandler.getActionIfActive(e.getItem());

        if(action != null) {
            action.getAction().invoke(player, e);
            e.setCancelled(true);
        }
        else e.setCancelled(player.getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onDrop(PlayerDropItemEvent e){
        if(itemHandler.hasAction(e.getItemDrop().getItemStack())) e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onSwap(PlayerSwapHandItemsEvent e){
        if(itemHandler.hasAction(e.getMainHandItem()) || itemHandler.hasAction(e.getOffHandItem())) e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onClick(InventoryClickEvent e){

        if(itemHandler.hasAction(e.getCurrentItem())) {
            e.setCancelled(true);
            return;
        }

        int slot = e.getHotbarButton();
        if(slot >= 0 && itemHandler.hasAction(e.getWhoClicked().getInventory().getItem(slot))){
            e.setCancelled(true);
            return;
        }

        // Security precaution : Some visual glitches happen in creative mode
        if(itemHandler.hasAction(e.getCursor()))
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCreativeClick(InventoryCreativeEvent e){
        onClick(e);
    }

    private static class Platform{
        private static final short H_SIDE = 15;
        private static final short HEIGHT = 201;
        private static final Material WALL = Material.BLUE_STAINED_GLASS;
        private static final Material FLOOR = Material.BARRIER;

        private final World world;
        private Location spawnLoc;

        Platform(World world){
            this.world = world;
        }

        void generatePlatform(){

            // Floor
            fillMaterial(FLOOR, -H_SIDE, H_SIDE, HEIGHT -1, HEIGHT -1, -H_SIDE, H_SIDE);

            // Sides
            fillMaterial(WALL, -H_SIDE, H_SIDE, HEIGHT, HEIGHT +3, H_SIDE, H_SIDE);
            fillMaterial(WALL, -H_SIDE, H_SIDE, HEIGHT, HEIGHT +3, -H_SIDE, -H_SIDE);
            fillMaterial(WALL, H_SIDE, H_SIDE, HEIGHT, HEIGHT +3, -H_SIDE, H_SIDE);
            fillMaterial(WALL, -H_SIDE, -H_SIDE, HEIGHT, HEIGHT +3, -H_SIDE, H_SIDE);

            // Inside
            fillMaterial(Material.AIR, -H_SIDE +1, H_SIDE -1, HEIGHT, HEIGHT +3, -H_SIDE +1, H_SIDE -1);

            spawnLoc = new Location(world, 0, HEIGHT, 0);
        }

        void removePlatform(){
            fillMaterial(Material.AIR, -H_SIDE, H_SIDE, HEIGHT -1, HEIGHT +3, -H_SIDE, H_SIDE);
            spawnLoc = null;
        }

        void fillMaterial(Material material, int x1, int x2, int y1, int y2, int z1, int z2) {
            for(int x = x1; x <= x2; x++) for (int z = z1; z <= z2; z++) for (int y = y1; y <= y2; y++) {
                Block block = world.getBlockAt(x,y,z);
                if(block.getType() != material) block.setType(material);
            }
        }
    }

}
