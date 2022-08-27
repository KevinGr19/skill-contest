package fr.kevingr19.skillcontest.objectives.list;

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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class InvisibilityObj extends Objective {

    private static final ItemStack potion;
    static{
        potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        potion.setItemMeta(meta);
    }

    public InvisibilityObj(GameTeam gameTeam) {
        super(ObjectiveType.INVISIBILITY, Rarity.EASY, "I am the Spy",
                "Devenir invisible.", null,
                potion, false, gameTeam);
    }

    @EventHandler
    public void onEffect(final EntityPotionEffectEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player) || e.getNewEffect() == null) return;
        if(e.getNewEffect().getType().equals(PotionEffectType.INVISIBILITY))
            complete(player);
    }
}
