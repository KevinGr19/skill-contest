package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.EndermanTargetPlayerEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class EndermanTargetObj extends Objective {

    public EndermanTargetObj(GameTeam gameTeam) {
        super(ObjectiveType.ENDERMAN_TARGET, Rarity.MODERATE, "Regardé de travers",
                "Être la cible de 10 Endermans\nen même temps", null,
                new ItemStack(Material.ENDERMAN_SPAWN_EGG), false, gameTeam);
    }

    @EventHandler
    public void onEndermanTarget(final EndermanTargetPlayerEvent e){
        if(isInTeam(e.getPlayer()) && e.getCount() >= 10) complete(e.getPlayer());
    }
}
