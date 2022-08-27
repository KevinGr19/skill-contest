package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class DonkeyChestObj extends Objective {

    public DonkeyChestObj(GameTeam gameTeam) {
        super(ObjectiveType.DONKEY_CHEST, Rarity.EASY, "SUV",
                "Mettre un coffre sur un âne.", null,
                new ItemStack(Material.MULE_SPAWN_EGG), false, gameTeam);
    }

    @EventHandler
    public void onDonkeyInteract(final PlayerInteractEntityEvent e){
        if(e.getRightClicked().getType() != EntityType.DONKEY || !isInTeam(e.getPlayer())) return;
        final ItemStack hand = e.getPlayer().getInventory().getItem(EquipmentSlot.HAND);
        if(ItemUtil.isNull(hand) || hand.getType() != Material.CHEST) return;

        Donkey donkey = (Donkey) e.getRightClicked();
        if(donkey.isTamed() && !donkey.isCarryingChest() && !e.getPlayer().isSneaking()) complete(e.getPlayer());
    }
}
