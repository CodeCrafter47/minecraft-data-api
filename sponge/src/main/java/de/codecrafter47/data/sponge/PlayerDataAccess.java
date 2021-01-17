/*
 *     Copyright (C) 2020 Florian Stober
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.codecrafter47.data.sponge;

import de.codecrafter47.data.minecraft.api.MinecraftData;
import de.codecrafter47.data.sponge.api.SpongeData;
import de.codecrafter47.data.sponge.nucleus.Nucleus;
import de.codecrafter47.data.sponge.sponge5.Sponge5;
import de.codecrafter47.data.sponge.sponge7.Sponge7;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDataAccess extends AbstractSpongeDataAccess<Player> {

    private static Pattern PATTERN_API_VERSION = Pattern.compile("(?<major>\\d+)\\.(?<minor>\\d+)(?:\\.(?<patch>\\d+))?(?:-.*)?");

    public PlayerDataAccess(Logger logger) {
        super(logger);

        addProvider(MinecraftData.Health, player -> player.getHealthData().health().get());
        addProvider(MinecraftData.MaxHealth, player -> player.getHealthData().maxHealth().get());
        addProvider(MinecraftData.Level, player -> player.get(Keys.EXPERIENCE_LEVEL).orElse(null));
        addProvider(MinecraftData.XP, player -> player.get(Keys.EXPERIENCE_SINCE_LEVEL).flatMap(xp -> player.get(Keys.EXPERIENCE_FROM_START_OF_LEVEL).map(xpCap -> (float) xp / (float) xpCap)).orElse(null));
        addProvider(MinecraftData.TotalXP, player -> player.get(Keys.TOTAL_EXPERIENCE).orElse(null));
        addProvider(MinecraftData.PosX, player -> player.getLocation().getX());
        addProvider(MinecraftData.PosY, player -> player.getLocation().getY());
        addProvider(MinecraftData.PosZ, player -> player.getLocation().getZ());
        addProvider(MinecraftData.Team, player -> player.getScoreboard().getMemberTeam(player.getTeamRepresentation()).map(Team::getName).orElse(null));
        addProvider(MinecraftData.TeamColor, player -> player.getScoreboard().getMemberTeam(player.getTeamRepresentation())
                .map(Team::getColor).map(color -> TextSerializers.FORMATTING_CODE.serialize(Text.builder().color(color).toText())).orElse(null));
        addProvider(MinecraftData.TeamDisplayName, player -> player.getScoreboard().getMemberTeam(player.getTeamRepresentation()).map(Team::getDisplayName).map(TextSerializers.FORMATTING_CODE::serialize).orElse(null));
        addProvider(MinecraftData.TeamPrefix, player -> player.getScoreboard().getMemberTeam(player.getTeamRepresentation()).map(Team::getPrefix).map(TextSerializers.FORMATTING_CODE::serialize).orElse(null));
        addProvider(MinecraftData.TeamSuffix, player -> player.getScoreboard().getMemberTeam(player.getTeamRepresentation()).map(Team::getSuffix).map(TextSerializers.FORMATTING_CODE::serialize).orElse(null));
        addProvider(MinecraftData.Permission, (player, key) -> player.hasPermission(key.getParameter()));

        addProvider(MinecraftData.DisplayName, player -> TextSerializers.FORMATTING_CODE.serialize(player.getDisplayNameData().displayName().get()));
        addProvider(MinecraftData.World, player -> player.getWorld().getName());

        addProvider(MinecraftData.Economy_Balance, player -> Sponge.getGame().getServiceManager().provide(EconomyService.class).flatMap(e -> e.getOrCreateAccount(player.getUniqueId()).map(a -> a.getBalance(e.getDefaultCurrency(), player.getActiveContexts()).doubleValue())).orElse(null));

        if (getAPIMajorVersion(logger) < 7) {
            addProvider(MinecraftData.Permissions_PermissionGroup, Sponge5::getPrimaryGroup);
        } else {
            addProvider(MinecraftData.Permissions_PermissionGroup, Sponge7::getPrimaryGroup);
        }
        addProvider(MinecraftData.Permissions_PermissionGroupRank, player -> player.getOption("rank").map(Integer::parseInt).orElse(null));
        addProvider(MinecraftData.Permissions_PermissionGroupWeight, player -> player.getOption("weight").map(Integer::parseInt).orElse(null));
        addProvider(MinecraftData.Permissions_Prefix, player -> player.getOption("prefix").orElse(null));
        addProvider(MinecraftData.Permissions_Suffix, player -> player.getOption("suffix").orElse(null));
        addProvider(SpongeData.Sponge_IsVanished, player -> player.get(Keys.VANISH).orElse(false));

        if (classExists("io.github.nucleuspowered.nucleus.api.NucleusAPI")) {
            addProvider(SpongeData.Nucleus_IsAFK, Nucleus::isAfk);
            addProvider(SpongeData.Nucleus_Nick, Nucleus::getNick);
        }
    }

    private static int getAPIMajorVersion(Logger logger) {
        Optional<String> apiVersion = Sponge.getPlatform().getApi().getVersion();
        if (apiVersion.isPresent()) {
            Matcher matcher = PATTERN_API_VERSION.matcher(apiVersion.get());
            if (matcher.matches()) {
                String major = matcher.group("major");
                if (major == null) {
                    // this is not supposed to happen
                    throw new AssertionError();
                }
                try {
                    return Integer.parseInt(major);
                } catch (NumberFormatException e) {
                    logger.warn("Failed to parse Sponge API major version", e);
                    return 0;
                }
            } else {
                logger.warn("Sponge API version does not match expected pattern.");
                return 0;
            }
        } else {
            logger.warn("Sponge API version not available.");
            return 0;
        }
    }

    private static boolean classExists(String name) {
        try {
            Class.forName(name);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}