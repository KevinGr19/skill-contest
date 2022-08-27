package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffectType;

public class SusSoupsObj extends TeamObjective<String> {

    public SusSoupsObj(GameTeam gameTeam) {
        super(ObjectiveType.SUS_SOUPS, Rarity.MODERATE, "Ant-impasta",
                "Avoir reçu tout les effets de\nla liste en buvant une soupe suspicieuse.", null,
                new ItemStack(Material.SUSPICIOUS_STEW), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Résistance au feu", "Cécité", "Saturation", "Saut amélioré",
                "Poison", "Régénération", "Vision nocturne", "Faiblesse");
    }

    @EventHandler
    public void onSoupDrink(final PlayerItemConsumeEvent e){
        if(e.getItem().getType() != Material.SUSPICIOUS_STEW || !isInTeam(e.getPlayer())) return;

        SuspiciousStewMeta meta = (SuspiciousStewMeta) e.getItem().getItemMeta();
        meta.getCustomEffects().forEach(potionEffect -> checkEffectType(e.getPlayer(), potionEffect.getType()));
    }

    private void checkEffectType(Player player, PotionEffectType effectType){

        String type = null;
        switch(effectType.getKey().getKey())
        {
            case "fire_resistance" -> type = "Résistance au feu";
            case "blindness" -> type = "Cécité";
            case "saturation" -> type = "Saturation";
            case "jump_boost" -> type = "Saut amélioré";
            case "poison" -> type = "Poison";
            case "regeneration" -> type = "Régénération";
            case "night_vision" -> type = "Vision nocturne";
            case "weakness" -> type = "Faiblesse";
        }

        if(type != null) objectiveData.addProgress(player, type);
    }
}
