package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class GoldToolBreakObj extends TeamObjective<String> {

    public GoldToolBreakObj(GameTeam gameTeam) {
        super(ObjectiveType.GOLD_TOOL_BREAK, Rarity.MODERATE, "Made in China",
                "Casser chaque type d'outil en or.", null,
                new ItemStack(Material.GOLDEN_SHOVEL), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Épée", "Hache", "Pelle", "Pioche", "Houe");
    }

    @EventHandler
    public void onItemBreak(final PlayerItemBreakEvent e){
        if(!isInTeam(e.getPlayer())) return;

        String type = null;
        switch(e.getBrokenItem().getType())
        {
            case GOLDEN_SWORD -> type = "Épée";
            case GOLDEN_AXE -> type = "Hache";
            case GOLDEN_SHOVEL -> type = "Pelle";
            case GOLDEN_PICKAXE -> type = "Pioche";
            case GOLDEN_HOE -> type = "Houe";
        }

        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}
