package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PillagerChiefKillObj extends Objective {

    public PillagerChiefKillObj(GameTeam gameTeam) {
        super(ObjectiveType.PILLAGER_CHIEF_KILL, Rarity.MODERATE, "Salamancas",
                "Tuer un Chef Pillageois.", null,
                new ItemStack(Material.IRON_AXE), false, gameTeam);
    }

    @EventHandler
    public void onEffect(final EntityPotionEffectEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player) || e.getNewEffect() == null) return;
        if(e.getNewEffect().getType().equals(PotionEffectType.BAD_OMEN))
            complete(player);
    }
}
