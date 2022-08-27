package fr.kevingr19.skillcontest.utils;

import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.objectives.Objective;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Broadcast {

    public static void broadcastSound(Location loc, Sound sound, float volume, float pitch){
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(loc == null ? player.getLocation() : loc, sound, volume, pitch));
    }

    public static void broadcastSound(Sound sound, float volume, float pitch){
        broadcastSound(null, sound, volume, pitch);
    }

    public static void broadcastSound(Location loc, SoundComponent soundComponent){
        broadcastSound(loc, soundComponent.sound(), soundComponent.volume(), soundComponent.pitch());
    }

    public static void broadcastSound(SoundComponent soundComponent){
        broadcastSound(soundComponent.sound(), soundComponent.volume(), soundComponent.pitch());
    }

    public static void broadcastTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut){
        Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle(title, subtitle, fadeIn, stay, fadeOut));
    }

    public static void broadcastObjective(Objective obj){
        broadcastObjective(obj, null);
    }

    public static void broadcastObjective(Objective obj, int points){
        broadcastObjective(obj, " §7(+" + points + ")");
    }

    private static void broadcastObjective(Objective obj, String end){
        String formatMsg = obj.rarity.formatMsg;
        TextComponent message = new TextComponent(String.format(formatMsg, obj.getSolverDisplayName(), obj.common ? Texts.TEAM_DONE : Texts.PLAYER_DONE));

        TextComponent hover = new TextComponent(obj.buttonName);
        hover.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(obj.rarity.color + "§n" + obj.name + "§r\n" +
                obj.rarity.color + obj.description)));

        message.addExtra(hover);
        if(end != null && !end.isEmpty()) message.addExtra(end);

        SoundComponent soundComponent = obj.rarity.soundComponent;

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.spigot().sendMessage(message);
            soundComponent.playSound(player);
        });
    }

    public static void sendActionBar(Player player, String text){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
    }

}
