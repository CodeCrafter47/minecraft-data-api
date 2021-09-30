package de.codecrafter47.data.velocity;

import com.velocitypowered.api.proxy.Player;

import java.util.function.Function;

public class VelocityClientVersionProvider implements Function<Player, String> {
    @Override
    public String apply(Player player) {
        return player.getProtocolVersion().getVersionIntroducedIn();
    }
}
