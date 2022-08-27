package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerLandEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class GreatFallObj extends Objective {

    public GreatFallObj(GameTeam gameTeam) {
        super(ObjectiveType.GREAT_FALL, Rarity.DIFFICULT, "Saut de la Hype",
                "Sauter depuis la limite de construction\njusqu'à la couche minimale sans mourir.",
                "Cela équivaut à faire une chute\nd'au moins 375 blocs de haut.",
                new ItemStack(Material.WATER_BUCKET), false, gameTeam);
    }

    @EventHandler
    public void onFall(final PlayerLandEvent e){
        if(isInTeam(e.getPlayer()) && e.getFallDistance() >= 375f) complete(e.getPlayer());
    }
}
