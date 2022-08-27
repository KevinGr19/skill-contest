package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class AllMinecartsObj extends TeamObjective<String> {

    public AllMinecartsObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_MINECARTS, Rarity.MODERATE, "RATP",
                "Fabriquer tout les types de wagon.", null,
                new ItemStack(Material.FURNACE_MINECART), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Normal", "Coffre", "Four", "Entonnoir", "TNT");
    }

    @EventHandler
    public void onItemCraft(final ResultItemTakenEvent e){
        if(!isInTeam(e.getPlayer())) return;

        String type = null;
        switch(e.getResult().getType())
        {
            case MINECART -> type = "Normal";
            case FURNACE_MINECART -> type = "Four";
            case CHEST_MINECART -> type = "Coffre";
            case HOPPER_MINECART -> type = "Entonnoir";
            case TNT_MINECART -> type = "TNT";
        }

        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}
