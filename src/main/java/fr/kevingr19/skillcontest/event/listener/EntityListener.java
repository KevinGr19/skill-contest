package fr.kevingr19.skillcontest.event.listener;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.event.*;
import fr.kevingr19.skillcontest.game.Game;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.*;
import org.bukkit.block.Jukebox;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EntityListener implements Listener {

    // Piglin gold
    @EventHandler
    public void onPiglinGetIngot(final EntityPickupItemEvent e){
        if(e.getEntity().getType() != EntityType.PIGLIN || e.getItem().getItemStack().getType() != Material.GOLD_INGOT) return;

        if(e.getItem().getThrower() != null){
            PiglinGetGoldIngotEvent event = new PiglinGetGoldIngotEvent(e.getItem().getThrower(), e.getItem().getItemStack());
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    // TNT Kill
    @EventHandler (ignoreCancelled = true)
    public void onTntKill(final EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof LivingEntity livingEntity)) return;
        if(livingEntity.getHealth() - e.getFinalDamage() > 0) return;

        if(e.getDamager() instanceof TNTPrimed tnt && tnt.getSource() instanceof Player player){
            PlayerTNTKillMobEvent event = new PlayerTNTKillMobEvent(player, livingEntity);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    // Arrow Kill
    @EventHandler (ignoreCancelled = true)
    public void onArrowKill(final EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof LivingEntity livingEntity)) return;
        if(livingEntity.getHealth() - e.getFinalDamage() > 0) return;

        if(e.getDamager() instanceof Arrow arrow && arrow.getShooter() instanceof Player player){
            PlayerArrowKillMobEvent event = new PlayerArrowKillMobEvent(player, livingEntity);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    // Track Fall Distance of players
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e){
        trackFall(e.getPlayer());
    }

    public void trackAllPlayerFall(){
        Bukkit.getOnlinePlayers().forEach(this::trackFall);
    }

    private void trackFall(Player player){
        if(Game.inst().isPlaying(player, true)) new PlayerFallRunnable(player).runTaskTimer(Main.inst(), 1, 1);
    }

    // Wither skeleton kill
    @EventHandler (ignoreCancelled = true)
    public void onWitherSkeletonPotionKill(EntityDamageByEntityEvent e){
        if(e.getEntity().getType() != EntityType.WITHER_SKELETON) return;
        if(e.getDamager().getType() != EntityType.SPLASH_POTION) return;
        LivingEntity skeleton = (LivingEntity) e.getEntity();
        ThrownPotion potion = (ThrownPotion) e.getDamager();

        if(skeleton.getHealth() - e.getFinalDamage() > 0) return;
        if(!(potion.getShooter() instanceof Player player)) return;

        for(PotionEffect effect : potion.getEffects()){
            if(effect.getType().equals(PotionEffectType.HEAL)){
                WitherSkeletonPotionKillEvent event = new WitherSkeletonPotionKillEvent(player);
                Bukkit.getPluginManager().callEvent(event);
            }
        }
    }

    // Biome track
    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerChangeBiome(final PlayerMoveEvent e){
        checkPlayerBiome(e.getPlayer(), e.getFrom(), e.getTo());
    }

    @EventHandler (ignoreCancelled = true)
    public void onPlayerTeleportBiome(final PlayerTeleportEvent e){
        checkPlayerBiome(e.getPlayer(), e.getFrom(), e.getTo());
    }

    public void getPlayersBiome(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            PlayerChangeBiomeEvent event = new PlayerChangeBiomeEvent(player, player.getLocation().getBlock().getBiome());
            Bukkit.getPluginManager().callEvent(event);
        });
    }

    private void checkPlayerBiome(Player player, Location from, Location to){
        if(to != null && from.getBlock().getBiome() != to.getBlock().getBiome()){
            PlayerChangeBiomeEvent event = new PlayerChangeBiomeEvent(player, to.getBlock().getBiome());
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    // Parrot dance
    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDiscNearParrot(final PlayerInteractEvent e){
        if(e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.JUKEBOX) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(ItemUtil.isNull(e.getItem()) || !e.getItem().getType().name().startsWith("MUSIC_DISC")) return;

        Jukebox jukebox = (Jukebox) e.getClickedBlock().getState();
        if(jukebox.isPlaying()) return;

        Location center = jukebox.getLocation().add(0.5,0.5,0.5);
        Collection<Entity> parrots = jukebox.getWorld().getNearbyEntities(center, 4, 4, 4,
                entity -> entity instanceof Parrot parrot && parrot.getLocation().distance(center) < 3.46D && !parrot.isDancing());

        if(parrots.size() > 0){
            PlayerDanceParrotEvent event = new PlayerDanceParrotEvent(e.getPlayer(), jukebox, parrots.size());
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    // Damage blocked by shield
    @EventHandler
    public void onDamageShielded(final EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player player)) return;

        if(e.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) < 0 && e.getFinalDamage() == 0){
            PlayerBlockDamageEvent event = new PlayerBlockDamageEvent(player, e.getDamager());
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    // On nether roof
    @EventHandler
    public void onPlayerMoveToRoof(final PlayerMoveEvent e){
        checkPlayerIsOnRoof(e.getPlayer(), e.getFrom(), e.getTo());
    }

    @EventHandler
    public void onPlayerTeleportToRoof(final PlayerTeleportEvent e){
        checkPlayerIsOnRoof(e.getPlayer(), e.getFrom(), e.getTo());
    }

    private void checkPlayerIsOnRoof(Player player, Location from, @Nullable Location to){
        if(isOnNetherRoof(to) && !isOnNetherRoof(from)){
            PlayerEnterNetherRoofEvent event = new PlayerEnterNetherRoofEvent(player);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    private boolean isOnNetherRoof(Location loc){
        return loc != null && loc.getWorld().getEnvironment() == World.Environment.NETHER && loc.getY() >= 128D;
    }

    // Wither Spawn
    @EventHandler
    public void onWitherSpawn(final CreatureSpawnEvent e){
        if(e.getEntity().getType() == EntityType.WITHER && e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BUILD_WITHER){
            Wither wither = (Wither) e.getEntity();
            List<Player> closePlayers = wither.getNearbyEntities(7, 7, 7).stream().filter(entity -> entity.getType() == EntityType.PLAYER).map(entity -> (Player) entity).collect(Collectors.toList());

            closePlayers.forEach(player -> {
                PlayerCloseToSpawnWitherEvent event = new PlayerCloseToSpawnWitherEvent(player, wither);
                Bukkit.getPluginManager().callEvent(event);
            });
        }
    }

    // Enderman target
    @EventHandler
    public void onTarget(final EntityTargetEvent e){
        if(!(e.getTarget() instanceof Player player) || e.getEntity().getType() != EntityType.ENDERMAN) return;
        long endermanCount = player.getNearbyEntities(64, 64, 64).stream().filter(entity -> entity instanceof Enderman enderman && enderman.getTarget() == player).count() + 1;

        EndermanTargetPlayerEvent event = new EndermanTargetPlayerEvent(player, endermanCount);
        Bukkit.getPluginManager().callEvent(event);
    }

    // Enderdragon
    private OfflinePlayer lastCrystalPlacer;

    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCrystalPlace(final PlayerInteractEvent e){

        // Player placed an end crystal on a bedrock block
        if(ItemUtil.isNull(e.getItem()) || e.getItem().getType() != Material.END_CRYSTAL) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.BEDROCK) return;

        // This happens in The End.
        World world = e.getClickedBlock().getWorld();
        if(world.getEnvironment() != World.Environment.THE_END) return;

        // There was no crystal at this position
        Location center = e.getClickedBlock().getLocation().add(0.5, 1.5, 0.5);
        if(world.getNearbyEntities(center, 0.5, 0.5, 0.5, entity -> entity.getType() == EntityType.ENDER_CRYSTAL).size() > 0) return;

        // No dragon is respawning
        DragonBattle battle = world.getEnderDragonBattle();
        if(battle.getEnderDragon() != null || battle.getRespawnPhase() != DragonBattle.RespawnPhase.NONE) return;

        lastCrystalPlacer = e.getPlayer();
    }

    @EventHandler
    public void onDragonRespawn(final CreatureSpawnEvent e){
        if(e.getEntity().getType() != EntityType.ENDER_DRAGON || e.getEntity().getWorld().getEnvironment() != World.Environment.THE_END) return;
        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.COMMAND) return;

        DragonBattle battle = e.getEntity().getWorld().getEnderDragonBattle();
        if(battle.hasBeenPreviouslyKilled() && lastCrystalPlacer != null){
            PlayerRespawnDragonEvent event = new PlayerRespawnDragonEvent(lastCrystalPlacer);
            Bukkit.getPluginManager().callEvent(event);

            lastCrystalPlacer = null;
        }
    }

}
