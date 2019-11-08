package de.codecrafter47.data.bungee.proxysuite;

import de.sabbertran.proxysuite.ProxySuiteAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxySuiteIsVanishedProvider implements Function<ProxiedPlayer, Boolean> {
    private final Logger logger;

    public ProxySuiteIsVanishedProvider(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Boolean apply(ProxiedPlayer player) {
        if (ProxyServer.getInstance().getPluginManager().getPlugin("ProxySuite") != null) {
            try {
                return ProxySuiteAPI.isVanished(player);
            } catch (Throwable th) {
                logger.log(Level.WARNING, "An error occurred while looking up a players vanish status from ProxyCore.", th);
            }
        }
        return null;
    }
}
