package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class StarFireworkObj extends Objective {

    public StarFireworkObj(GameTeam gameTeam) {
        super(ObjectiveType.STAR_FIREWORK, Rarity.EASY, "Des paillettes dans ma vie",
                "Tirer un feu d'artifice étoilé\navec une arbalète.", null,
                new ItemStack(Material.FIREWORK_STAR), false, gameTeam);
    }

    @EventHandler
    public void onProjectile(ProjectileLaunchEvent e){
        if(!(e.getEntity().getShooter() instanceof Player player) || !isInTeam(player)) return;
        if(!(e.getEntity() instanceof Firework firework)) return;

        ItemStack tool = player.getInventory().getItem(EquipmentSlot.HAND);
        if(ItemUtil.isNull(tool) || tool.getType() != Material.CROSSBOW) return;

        for(FireworkEffect effect : firework.getFireworkMeta().getEffects()){
            if(effect.getType() == FireworkEffect.Type.STAR) {
                complete(player);
                return;
            }
        }
    }
}
