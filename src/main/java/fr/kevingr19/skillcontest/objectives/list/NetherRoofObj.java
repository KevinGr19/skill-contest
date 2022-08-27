package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerEnterNetherRoofEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class NetherRoofObj extends Objective {

    public NetherRoofObj(GameTeam gameTeam) {
        super(ObjectiveType.NETHER_ROOF, Rarity.MODERATE, "Roubaix",
                "Aller sur le toit du Nether.",
                "Pensez à prendre de quoi sortir\navant d'y aller.",
                new ItemStack(Material.BEDROCK), false, gameTeam);
    }

    @EventHandler
    public void onPlayerRoof(final PlayerEnterNetherRoofEvent e){
        if(isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}
