package de.codecrafter47.data.bungee.premiumvanish;

import de.myzelyam.api.vanish.BungeeVanishAPI;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.function.Function;

public class PremiumVanishIsVanishedProvider implements Function<ProxiedPlayer, Boolean> {
    @Override
    public Boolean apply(ProxiedPlayer player) {
        return BungeeVanishAPI.isInvisible(player);
    }
}
