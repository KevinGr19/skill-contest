package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class IronStandObj extends Objective {

    public IronStandObj(GameTeam gameTeam) {
        super(ObjectiveType.IRON_STAND, Rarity.EASY, "Iron Man",
                "Poser une armure en fer complète sur un\nporte-armure.", null,
                new ItemStack(Material.ARMOR_STAND), false, gameTeam);
    }

    @EventHandler
    public void onStandEquip(final PlayerArmorStandManipulateEvent e){
        if(ItemUtil.isNull(e.getPlayerItem()) || !isInTeam(e.getPlayer())) return;
        Material type = e.getPlayerItem().getType();

        if(!ItemUtil.isArmorPiece(e.getPlayerItem(), ItemUtil.ArmorType.IRON)) return;
        if(ItemUtil.hasArmor(e.getRightClicked().getEquipment(), ItemUtil.ArmorType.IRON)) return;

        new BukkitRunnable(){
            @Override
            public void run() {
                if(e.getRightClicked().isDead()) return;
                if(ItemUtil.hasArmor(e.getRightClicked().getEquipment(), ItemUtil.ArmorType.IRON)) complete(e.getPlayer());
            }
        }.runTaskLater(Main.inst(), 0);
    }
}
