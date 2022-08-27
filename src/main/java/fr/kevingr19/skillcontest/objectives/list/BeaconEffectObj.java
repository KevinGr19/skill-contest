package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;

public class BeaconEffectObj extends Objective {

    public BeaconEffectObj(GameTeam gameTeam) {
        super(ObjectiveType.BEACON_EFFECT, Rarity.DIFFICULT, "Pouvoir Divin",
                "Recevoir un effet d'une balise.",
                "Ne laissez pas traîner votre balise\nune fois l'objectif réalisé.",
                new ItemStack(Material.BEACON), true, gameTeam);
    }

    @EventHandler
    public void onEffect(final EntityPotionEffectEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player) || e.getNewEffect() == null) return;
        if(e.getCause() == EntityPotionEffectEvent.Cause.BEACON)
            complete(player);
    }
}
