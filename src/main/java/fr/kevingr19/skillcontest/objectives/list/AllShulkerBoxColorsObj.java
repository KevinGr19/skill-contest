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

public class AllShulkerBoxColorsObj extends TeamObjective<String> {

    public AllShulkerBoxColorsObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_SHULKER_BOX_COLORS, Rarity.DIFFICULT, "Trier ses déchets",
                "Obtenir chaque couleur de Boîte de Shulker.", null,
                new ItemStack(Material.SHULKER_BOX), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Rouge", "Marron", "Orange", "Jaune", "Vert clair", "Vert", "Turquoise",
                "Bleu clair", "Bleu", "Violet", "Magenta", "Rose", "Blanc", "Gris clair", "Gris", "Noir");
    }

    @EventHandler
    public void onObtainItem(final PlayerInventoryNewItemEvent e){
        if(!isInTeam(e.getPlayer())) return;

        String type = null;
        switch(e.getMaterial())
        {
            case RED_SHULKER_BOX -> type = "Rouge";
            case BLACK_SHULKER_BOX -> type = "Noir";
            case BLUE_SHULKER_BOX -> type = "Bleu";
            case LIGHT_BLUE_SHULKER_BOX -> type = "Bleu clair";
            case LIME_SHULKER_BOX -> type = "Vert clair";
            case GREEN_SHULKER_BOX -> type = "Vert";
            case PINK_SHULKER_BOX -> type = "Rose";
            case PURPLE_SHULKER_BOX -> type = "Violet";
            case YELLOW_SHULKER_BOX -> type = "Jaune";
            case ORANGE_SHULKER_BOX -> type = "Orange";
            case BROWN_SHULKER_BOX -> type = "Marron";
            case CYAN_SHULKER_BOX -> type = "Turquoise";
            case WHITE_SHULKER_BOX -> type = "Blanc";
            case MAGENTA_SHULKER_BOX -> type = "Magenta";
            case GRAY_SHULKER_BOX -> type = "Gris";
            case LIGHT_GRAY_SHULKER_BOX -> type = "Gris clair";

        }

        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}
