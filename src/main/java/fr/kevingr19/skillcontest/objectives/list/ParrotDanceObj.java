package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerDanceParrotEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class ParrotDanceObj extends Objective {

    public ParrotDanceObj(GameTeam gameTeam) {
        super(ObjectiveType.PARROT_DANCE, Rarity.DIFFICULT, "Tequila.",
                "Faire danser un perroquet.",
                "Il faut que le perroquet ne\nsoit pas sur l'épaule d'un joueur.",
                new ItemStack(Material.JUKEBOX), false, gameTeam);
    }

    @EventHandler
    public void onParrotDance(final PlayerDanceParrotEvent e){
        if(isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
