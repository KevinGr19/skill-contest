package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class GhastReturnSenderObj extends TeamObjective<Integer> {

    public GhastReturnSenderObj(GameTeam gameTeam) {
        super(ObjectiveType.GHAST_RETURN_SENDER, Rarity.DIFFICULT, "Roland-Garros",
                "Tuer 4 Ghasts en renvoyant leur\nboule de feu.", null,
                new ItemStack(Material.GHAST_TEAR), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 4);
    }

    @EventHandler (ignoreCancelled = true)
    public void onGhastFatalHit(final EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Ghast ghast) || ghast.getHealth() - e.getFinalDamage() > 0) return;
        if(!(e.getDamager() instanceof Fireball fireball)) return;

        if(fireball.getShooter() instanceof Player player && isInTeam(player)) objectiveData.addProgress(player, 1);
    }
}
