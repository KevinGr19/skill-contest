package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class EndGatewayTpObj extends TeamObjective<Integer> {

    public EndGatewayTpObj(GameTeam gameTeam) {
        super(ObjectiveType.END_GATEWAY_TP, Rarity.MODERATE, "Trou de Ver",
                "Utiliser 12 fois une passerelle de l'End.", null,
                new ItemStack(Material.ENDER_PEARL), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 12);
    }

    @EventHandler
    public void onTeleport(final PlayerTeleportEvent e){
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.END_GATEWAY && isInTeam(e.getPlayer()))
            objectiveData.addProgress(e.getPlayer(), 1);
    }
}
