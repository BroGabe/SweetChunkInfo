package com.thedev.sweetchunkinfo.utils;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.bukkit.Location;


public class FactionsUtil {

    public static boolean isFactionLand(Location location) {
        FLocation fLocation = new FLocation(location);

        Faction claimedFaction = Board.getInstance().getFactionAt(fLocation);

        return (!(claimedFaction.isSafeZone() || claimedFaction.isWarZone() || claimedFaction.isWilderness()));
    }

    public static String getFactionAtLand(Location location) {
        FLocation fLocation = new FLocation(location);

        return Board.getInstance().getFactionAt(fLocation).getTag();
    }
}
