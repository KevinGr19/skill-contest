package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;

public class EnterNetherObj extends Objective {

    public EnterNetherObj(GameTeam gameTeam) {
        super(ObjectiveType.ENTER_NETHER, Rarity.EASY, "Welcome to Brazil !",
                "Prendre un portail du Nether.", null,
                new ItemStack(Material.OBSIDIAN), false, gameTeam);
    }

    @EventHandler
    public void onPortalTravel(final PlayerPortalEvent e){
        if(!isInTeam(e.getPlayer()) || e.getTo() == null) return;
        if(e.getTo().getWorld().getEnvironment() == World.Environment.NETHER) complete(e.getPlayer());
    }
}
