package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class Mineshaftobj extends Objective {

    public Mineshaftobj(GameTeam gameTeam) {
        super(ObjectiveType.MINESHAFT, Rarity.EASY, "Mineurs majeurs",
                "Entrer dans un Mineshaft.", null,
                new ItemStack(Material.RAIL), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInMineshaft(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && e.getStructure() instanceof MineshaftStructure) complete(e.getPlayer());
    }
}
