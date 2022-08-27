package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerBlockDamageEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class ShieldBlockObj extends TeamObjective<String> {

    public ShieldBlockObj(GameTeam gameTeam) {
        super(ObjectiveType.SHIELD_BLOCK, Rarity.EASY, "À rude épreuve",
                "Bloquer les dégâts d'un zombie, d'un\nsquelette, d'un creeper et d'araignée\navec un bouclier.",
                null, new ItemStack(Material.SHIELD), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Zombie", "Squelette", "Creeper", "Araignée");
    }

    @EventHandler
    public void onBlock(final PlayerBlockDamageEvent e){
        if(!isInTeam(e.getPlayer())) return;

        switch(e.getDamager().getType())
        {
            case ZOMBIE -> objectiveData.addProgress(e.getPlayer(), "Zombie");
            case CREEPER -> objectiveData.addProgress(e.getPlayer(), "Creeper");
            case SPIDER -> objectiveData.addProgress(e.getPlayer(), "Araignée");
            case ARROW -> {
                final ProjectileSource source = ((Arrow) e.getDamager()).getShooter();
                if(source instanceof Skeleton) objectiveData.addProgress(e.getPlayer(), "Squelette");
            }
        }
    }
}
