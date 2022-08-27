package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.CheckForArmorEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class AllArmorsObj extends TeamObjective<String> {

    public AllArmorsObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_ARMORS, Rarity.LEGENDARY, "Chic, branché, moderne.",
                "Porter un jeu d'armure de chaque type.", null,
                new ItemStack(Material.CHAINMAIL_CHESTPLATE), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Cuir", "Or", "Mailles", "Fer", "Diamant",
                "Netherite");
    }

    @EventHandler
    public void onArmorCheck(final CheckForArmorEvent e){
        if(!isInTeam(e.getPlayer())) return;

        ItemUtil.ArmorType armorType = ItemUtil.getArmorType(e.getPlayer().getEquipment());
        if(armorType == null) return;

        String type = null;
        switch(armorType)
        {
            case LEATHER -> type = "Cuir";
            case GOLD -> type = "Or";
            case CHAINMAIL -> type = "Mailles";
            case IRON -> type = "Fer";
            case DIAMOND -> type = "Diamant";
            case NETHERITE -> type = "Netherite";
        }

        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}