package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;

public class EnterTheEndObj extends Objective {

    public EnterTheEndObj(GameTeam gameTeam) {
        super(ObjectiveType.ENTER_THE_END, Rarity.EASY, "Endgame",
                "Prendre un portail de l'End.", null,
                new ItemStack(Material.END_PORTAL_FRAME), false, gameTeam);
    }

    @EventHandler
    public void onPortalTravel(final PlayerPortalEvent e){
        if(!isInTeam(e.getPlayer()) || e.getTo() == null) return;
        if(e.getTo().getWorld().getEnvironment() == World.Environment.THE_END) complete(e.getPlayer());
    }
}
