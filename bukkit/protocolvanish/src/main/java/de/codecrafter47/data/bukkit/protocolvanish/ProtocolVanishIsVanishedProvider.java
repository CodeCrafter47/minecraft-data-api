package de.codecrafter47.data.bukkit.protocolvanish;

import com.azortis.protocolvanish.ProtocolVanish;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class ProtocolVanishIsVanishedProvider implements Function<Player, Boolean> {
    @Override
    public Boolean apply(Player player) {
        return JavaPlugin.getPlugin(ProtocolVanish.class).getVisibilityManager().isVanished(player.getUniqueId());
    }
}
