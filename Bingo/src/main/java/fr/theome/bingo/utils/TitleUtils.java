package fr.theome.bingo.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Blendman974
 */
public class TitleUtils {


    /**
     * Send a title to a player
     * @param player   The player
     * @param title    The title
     */
    public static void sendTitle(Player player, @Nullable String title) {
        if (title != null) {
            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}"));
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
        }
    }

    /**
     * Send a title to multiple players
     *
     * @param players  The players
     * @param fadeIn   the fade in time in ticks
     * @param display  the display time in ticks
     * @param fadeOut  the fade out time int ticks
     * @param title    The title
     * @param subtitle The subtitle
     */
    public static void sendTitle(List<Player> players, int fadeIn, int display, Integer fadeOut, @Nullable String title, @Nullable String subtitle) {
        if (title != null) {
            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}"), fadeIn, display, fadeOut);
            players.forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket));
        }
        if (subtitle != null) {
            PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subtitle + "\"}"), fadeIn, display, fadeOut);
            players.forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitlePacket));
        }
    }

}