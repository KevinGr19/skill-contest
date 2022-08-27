package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class AllFoodsObj extends TeamObjective<String> {

    public AllFoodsObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_FOODS, Rarity.IMPOSSIBLE, "Gérard Depardieu",
                "Manger toutes les nourritures du jeu.", null,
                new ItemStack(Material.SMOKER), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Pomme", "Carotte", "Pomme de terre",
                "Pomme de terre cuite", "Pomme de terre empoisonée", "Tranche de pastèque", "Betterave", "Algue séchée", "Baies sucrées",
                "Baies lumineuses", "Boeuf cru", "Côtelette de porc cru", "Mouton cru", "Lapin cru", "Poulet cru",
                "Steak", "Côtelette de porc cuite", "Mouton cuit", "Poulet rôti", "Lapin cuit", "Soupe de champignons",
                "Soupe de betterave", "Ragoût de lapin", "Saumon cru", "Morue crue", "Poisson tropical", "Poisson-globe",
                "Morue cuite", "Saumon cuit", "Tarte à la citrouille", "Fiole de miel", "Cookie",
                "Fruit du chorus", "Oeil d'araignée", "Chair putréfiée", "Carotte dorée", "Pomme en or", "Pomme en or enchantée");
    }

    @EventHandler
    public void onItemEat(final PlayerItemConsumeEvent e){
        if(!isInTeam(e.getPlayer())) return;

        String type = null;
        switch(e.getItem().getType())
        {
            case APPLE -> type = "Pomme";
            case CARROT -> type = "Carotte";
            case POTATO -> type = "Pomme de terre";
            case POISONOUS_POTATO -> type = "Pomme de terre empoisonée";
            case BAKED_POTATO -> type = "Pomme de terre cuite";
            case MELON_SLICE -> type = "Tranche de pastèque";
            case BEETROOT -> type = "Betterave";
            case DRIED_KELP -> type = "Algue séchée";
            case SWEET_BERRIES -> type = "Baies sucrées";
            case GLOW_BERRIES -> type = "Baies lumineuses";
            case BEEF -> type = "Boeuf cru";
            case PORKCHOP -> type = "Côtelette de porc cru";
            case MUTTON -> type = "Mouton cru";
            case RABBIT -> type = "Lapin cru";
            case CHICKEN -> type = "Poulet cru";
            case COOKED_BEEF -> type = "Steak";
            case COOKED_PORKCHOP -> type = "Côtelette de porc cuite";
            case COOKED_MUTTON -> type = "Mouton cuit";
            case COOKED_RABBIT -> type = "Lapin cuit";
            case COOKED_CHICKEN -> type = "Poulet rôti";
            case MUSHROOM_STEW -> type = "Soupe de champignons";
            case BEETROOT_SOUP -> type = "Soupe de betterave";
            case RABBIT_STEW -> type = "Ragoût de lapin";
            case SALMON -> type = "Saumon cru";
            case COD -> type = "Morue crue";
            case TROPICAL_FISH -> type = "Poisson tropical";
            case PUFFERFISH -> type = "Poisson-globe";
            case COOKED_SALMON -> type = "Saumon cuit";
            case COOKED_COD -> type = "Morue cuite";
            case PUMPKIN_PIE -> type = "Tarte à la citrouille";
            case HONEY_BOTTLE -> type = "Fiole de miel";
            case COOKIE -> type = "Cookie";
            case CHORUS_FRUIT -> type = "Fruit du chorus";
            case SPIDER_EYE -> type = "Oeil d'araignée";
            case ROTTEN_FLESH -> type = "Chair putréfiée";
            case GOLDEN_CARROT -> type = "Carotte dorée";
            case GOLDEN_APPLE -> type = "Pomme en or";
            case ENCHANTED_GOLDEN_APPLE -> type = "Pomme en or enchantée";
        }
        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}
