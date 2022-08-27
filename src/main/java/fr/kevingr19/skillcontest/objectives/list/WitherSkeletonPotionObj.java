package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.WitherSkeletonPotionKillEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class WitherSkeletonPotionObj extends Objective {

    private static final ItemStack potion;
    static{
        potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        potion.setItemMeta(meta);
    }

    public WitherSkeletonPotionObj(GameTeam gameTeam) {
        super(ObjectiveType.WITHER_SKELETON_POTION, Rarity.MODERATE, "Medecine médiévale",
                "Tuer un Wither Squelette avec\nune potion de soin instantané.", null,
                potion, false, gameTeam);
    }

    @EventHandler
    public void onWitherSkeletonKill(final WitherSkeletonPotionKillEvent e){
        if(isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}