/*
 * Copyright (C) 2016 Florian Stober
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.codecrafter47.data.sponge;

import de.codecrafter47.data.minecraft.api.MinecraftData;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.service.context.Contextual;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.permission.PermissionService;

public class PlayerDataAccess extends AbstractSpongeDataAccess<Player> {

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
        addProvider(MinecraftData.Permission, (player, key) -> player.hasPermission(key.getParameter()));

        addProvider(MinecraftData.DisplayName, player -> player.getDisplayNameData().displayName().get().toPlain());
        addProvider(MinecraftData.World, player -> player.getWorld().getName());

        addProvider(MinecraftData.Economy_Balance, player -> Sponge.getGame().getServiceManager().provide(EconomyService.class).flatMap(e -> e.getOrCreateAccount(player.getUniqueId()).map(a -> a.getBalance(e.getDefaultCurrency(), player.getActiveContexts()).doubleValue())).orElse(null));

        addProvider(MinecraftData.Permissions_PermissionGroup, player -> player.getParents().stream().filter(subject -> subject.getContainingCollection().getIdentifier().equals(PermissionService.SUBJECTS_GROUP)).findFirst().map(Contextual::getIdentifier).orElse(null));
        addProvider(MinecraftData.Permissions_PermissionGroupRank, player -> player.getOption("rank").map(Integer::parseInt).orElse(null));
        addProvider(MinecraftData.Permissions_PermissionGroupWeight, player -> player.getOption("weight").map(Integer::parseInt).orElse(null));
        addProvider(MinecraftData.Permissions_Prefix, player -> player.getOption("prefix").orElse(null));
        addProvider(MinecraftData.Permissions_Suffix, player -> player.getOption("suffix").orElse(null));
    }
}