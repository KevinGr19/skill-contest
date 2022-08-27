package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInsideStructureEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class MansionObj extends Objective {

    public MansionObj(GameTeam gameTeam) {
        super(ObjectiveType.MANSION, Rarity.IMPOSSIBLE, "Résidence Poutinienne",
                "Entrer dans un Manoir.", null,
                new ItemStack(Material.DARK_OAK_PLANKS), false, gameTeam);
    }

    @EventHandler
    public void onPlayerInMansion(final PlayerInsideStructureEvent e){
        if(isInTeam(e.getPlayer()) && e.getStructure() instanceof WoodlandMansionStructure) complete(e.getPlayer());
    }
}
