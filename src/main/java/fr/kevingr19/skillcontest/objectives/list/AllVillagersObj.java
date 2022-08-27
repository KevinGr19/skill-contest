package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.VillagerTradeEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class AllVillagersObj extends TeamObjective<String> {

    public AllVillagersObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_VILLAGERS, Rarity.DIFFICULT, "Empire Économique",
                "Échanger avec un villageois de\nchaque profession", null,
                new ItemStack(Material.FLETCHING_TABLE), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Armurier", "Berger", "Bibliothécaire",
                "Boucher", "Cartographe", "Fermier", "Fléchier", "Forgeron d'armes", "Forgeron d'outils", "Maçon",
                "Pêcheur", "Prêtre", "Tanneur");
    }

    @EventHandler
    public void onTrade(final VillagerTradeEvent e){
        if(!isInTeam(e.getPlayer())) return;

        String type = null;
        switch(e.getVillager().getProfession())
        {
            case ARMORER -> type = "Armurier";
            case MASON -> type = "Maçon";
            case CLERIC -> type = "Prêtre";
            case FARMER -> type = "Fermier";
            case BUTCHER -> type = "Boucher";
            case FLETCHER -> type = "Fléchier";
            case SHEPHERD -> type = "Berger";
            case FISHERMAN -> type = "Pêcheur";
            case LIBRARIAN -> type = "Bibliothécaire";
            case TOOLSMITH -> type = "Forgeron d'outils";
            case WEAPONSMITH -> type = "Forgeron d'armes";
            case CARTOGRAPHER -> type = "Cartographe";
            case LEATHERWORKER -> type = "Tanneur";
        }

        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}
