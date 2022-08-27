package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInventoryNewItemEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class AllLogsObj extends TeamObjective<String> {

    public AllLogsObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_LOGS, Rarity.MODERATE, "Toucher du bois",
                "Récupérer tout les types de bûches.", null,
                new ItemStack(Material.ACACIA_LOG), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Chêne", "Sapin", "Bouleau", "Acajou", "Acacia",
                "Chêne noir", "Palétuvier", "Tige carmin", "Tige biscornue");
    }

    @EventHandler
    public void onObtainItem(final PlayerInventoryNewItemEvent e){
        if(!isInTeam(e.getPlayer())) return;

        String logType = null;
        switch(e.getMaterial())
        {
            case OAK_LOG, STRIPPED_OAK_LOG -> logType = "Chêne";
            case DARK_OAK_LOG, STRIPPED_DARK_OAK_LOG -> logType = "Chêne noir";
            case SPRUCE_LOG, STRIPPED_SPRUCE_LOG -> logType = "Sapin";
            case JUNGLE_LOG, STRIPPED_JUNGLE_LOG -> logType = "Acajou";
            case ACACIA_LOG, STRIPPED_ACACIA_LOG -> logType = "Acacia";
            case BIRCH_LOG, STRIPPED_BIRCH_LOG -> logType = "Bouleau";
            case MANGROVE_LOG, STRIPPED_MANGROVE_LOG -> logType = "Palétuvier";
            case WARPED_STEM, STRIPPED_WARPED_STEM -> logType = "Tige biscornue";
            case CRIMSON_STEM, STRIPPED_CRIMSON_STEM -> logType = "Tige carmin";
        }

        if(logType != null) objectiveData.addProgress(e.getPlayer(), logType);
    }
}
