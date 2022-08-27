package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

public class SuperheroObj extends Objective {

    private static final ItemStack potion;
    static{
        potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.STRENGTH));
        potion.setItemMeta(meta);
    }

    public SuperheroObj(GameTeam gameTeam) {
        super(ObjectiveType.SUPER_HERO, Rarity.IMPOSSIBLE, "Super-Héros",
                "Avoir les effets Vitesse II, Saut amélioré II,\n" +
                        "Force II, Résistance au feu, Chute lente, Vision Nocturne\n" +
                        "et Respiration Aquatique en même temps.", null,
                potion, false, gameTeam);
    }

    @EventHandler
    public void onPotionEffect(final EntityPotionEffectEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player)) return;

        new BukkitRunnable(){
            @Override public void run(){
                if(e.isCancelled()) return;

                if(!hasEffect(player, PotionEffectType.SLOW_FALLING, 1)) return;
                if(!hasEffect(player, PotionEffectType.WATER_BREATHING, 1)) return;
                if(!hasEffect(player, PotionEffectType.FIRE_RESISTANCE, 1)) return;
                if(!hasEffect(player, PotionEffectType.INCREASE_DAMAGE, 2)) return;
                if(!hasEffect(player, PotionEffectType.SPEED, 2)) return;
                if(!hasEffect(player, PotionEffectType.JUMP, 2)) return;
                if(hasEffect(player, PotionEffectType.NIGHT_VISION, 1)) complete(player);
            }
        }.runTaskLater(Main.inst(), 0);
    }

    private static boolean hasEffect(Player player, PotionEffectType type, int level){
        PotionEffect effect = player.getPotionEffect(type);
        return effect != null && effect.getAmplifier() + 1 >= level;
    }
}
