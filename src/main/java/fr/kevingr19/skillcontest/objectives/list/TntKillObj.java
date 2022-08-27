package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerTNTKillMobEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class TntKillObj extends TeamObjective<Integer> {

    public TntKillObj(GameTeam gameTeam) {
        super(ObjectiveType.TNT_KILL, Rarity.DIFFICULT, "Moyenne Orientation",
                "Tuer 20 mobs avec des TNT.",
                "Vous devez utiliser des TNT\net non des wagons-TNT.\nLes joueurs ne sont pas inclus.",
                new ItemStack(Material.TNT), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, false, 20);
    }

    @EventHandler
    public void onTntKill(final PlayerTNTKillMobEvent e){
        if(isInTeam(e.getPlayer()) && e.getEntity().getType() != EntityType.PLAYER)
            objectiveData.addProgress(e.getPlayer(), 1);
    }
}
