package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityMountEvent;

public class AllRideableObj extends TeamObjective<String> {

    public AllRideableObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_RIDEABLE, Rarity.MODERATE, "Everything Rider",
                "Monter dans chaque véhicule ou monture\nde la liste de l'objectif.",
                "Pour certains mobs, vous serez obligés\nd'utiliser une selle.",
                new ItemStack(Material.SADDLE), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, true, "Bateau", "Bateau-coffre", "Wagon", "Cheval",
                "Âne", "Mule", "Lama", "Cochon", "Arpenteur");
    }

    @EventHandler
    public void onRide(final EntityMountEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player)) return;

        String type = null;
        switch(e.getMount().getType())
        {
            case HORSE -> type = "Cheval";
            case DONKEY -> type = "Âne";
            case MULE -> type = "Mule";
            case PIG -> type = "Cochon";
            case STRIDER -> type = "Arpenteur";
            case LLAMA -> type = "Lama";
            case BOAT -> type = "Bateau";
            case CHEST_BOAT -> type = "Bateau-coffre";
            case MINECART -> type = "Wagon";
        }

        if(type != null) objectiveData.addProgress(player, type);
    }
}
