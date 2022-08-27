package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ConduitPowerObj extends Objective {

    public ConduitPowerObj(GameTeam gameTeam) {
        super(ObjectiveType.CONDUIT_POWER, Rarity.LEGENDARY, "Réveil de Sauron",
                "Recevoir l'effet Pouvoir du Conduit.",
                "Une fois l'objectif fait, ne laissez pas\ntrainer votre conduit à l'air libre, ou d'autres\néquipes pourraient en profiter.",
                new ItemStack(Material.CONDUIT), true, gameTeam);
    }

    @EventHandler
    public void onEffect(final EntityPotionEffectEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player) || e.getNewEffect() == null) return;
        if(e.getNewEffect().getType().equals(PotionEffectType.CONDUIT_POWER))
            complete(player);
    }
}
