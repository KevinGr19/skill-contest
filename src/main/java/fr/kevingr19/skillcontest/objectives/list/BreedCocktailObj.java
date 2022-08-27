package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;

public class BreedCocktailObj extends TeamObjective<String> {

    public BreedCocktailObj(GameTeam gameTeam) {
        super(ObjectiveType.BREED_COCKTAIL, Rarity.IMPOSSIBLE, "Marchand de sable",
                "Reproduire tout les mobs du jeu.",
                "Cet objectif prend en compte le\ntype de mob de l'enfant.\nNe compte pas les animaux à oeuf.",
                new ItemStack(Material.GOLDEN_CARROT), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Cheval", "Âne", "Mule",
                "Vache", "Chèvre", "Mouton", "Champimeuh", "Cochon", "Poulet", "Loup", "Chat/Ocelot", "Axolot",
                "Lama", "Lapin", "Panda", "Renard", "Abeille", "Arpenteur", "Hoglin");
    }

    @EventHandler
    public void onBreed(final EntityBreedEvent e){
        Main.print("BreedEvent");
        if(!(e.getBreeder() instanceof Player player) || !isInTeam(player)) return;

        String type = null;
        switch(e.getEntity().getType())
        {
            case HORSE -> type = "Cheval";
            case DONKEY -> type = "Âne";
            case MULE -> type = "Mule";
            case COW -> type = "Vache";
            case GOAT -> type = "Chèvre";
            case SHEEP -> type = "Mouton";
            case MUSHROOM_COW -> type = "Champimeuh";
            case PIG -> type = "Cochon";
            case CHICKEN -> type = "Poulet";
            case WOLF -> type = "Loup";
            case CAT, OCELOT -> type = "Chat/Ocelot";
            case AXOLOTL -> type = "Axolot";
            case LLAMA, TRADER_LLAMA -> type = "Lama";
            case RABBIT -> type = "Lapin";
            case PANDA -> type = "Panda";
            case FOX -> type = "Renard";
            case BEE -> type = "Abeille";
            case STRIDER -> type = "Arpenteur";
            case HOGLIN -> type = "Hoglin";
        }

        if(type != null) objectiveData.addProgress(player, type);
    }
}
