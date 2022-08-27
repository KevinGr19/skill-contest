package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerCloseToSpawnWitherEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class WitherSpawnObj extends Objective {

    public WitherSpawnObj(GameTeam gameTeam) {
        super(ObjectiveType.WITHER_SPAWN, Rarity.DIFFICULT, "Triple Problème",
                "Faire apparaître le Wither.",
                "L'objectif se valide pour tout les joueurs\ndans un certain rayon autour du Wither.\n" +
                        "Faites alors attention aux intrus.",
                new ItemStack(Material.WITHER_SKELETON_SKULL), true, gameTeam);
    }

    @EventHandler
    public void onPlayerCloseToWither(final PlayerCloseToSpawnWitherEvent e){
        if(isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
