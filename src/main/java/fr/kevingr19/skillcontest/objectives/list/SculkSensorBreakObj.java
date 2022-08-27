package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SculkSensorBreakObj extends TeamObjective<Integer> {

    public SculkSensorBreakObj(GameTeam gameTeam) {
        super(ObjectiveType.SCULK_SENSOR_BREAK, Rarity.MODERATE, "Oreilles coupées",
                "Casser 18 détecteurs de rôdeur.",
                "L'outil utilisé ne doit pas avoir\nl'enchantement \"Toucher de Soie\".",
                new ItemStack(Material.SCULK_SENSOR), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 18);
    }

    @EventHandler (ignoreCancelled = true)
    public void onBreak(final BlockBreakEvent e){
        if(e.getBlock().getType() != Material.SCULK_SENSOR || !isInTeam(e.getPlayer())) return;
        final ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();

        if(ItemUtil.isNull(hand) || hand.getItemMeta().getEnchantLevel(Enchantment.SILK_TOUCH) < 1)
            objectiveData.addProgress(e.getPlayer(), 1);
    }

}
