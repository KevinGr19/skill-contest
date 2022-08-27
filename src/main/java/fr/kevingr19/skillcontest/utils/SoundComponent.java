package fr.kevingr19.skillcontest.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public record SoundComponent (Sound sound, float volume, float pitch){

    public void playSound(Player player){
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

}
