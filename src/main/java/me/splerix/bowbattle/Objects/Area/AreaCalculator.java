package me.splerix.bowbattle.Objects.Area;

import org.bukkit.Location;

public class AreaCalculator {
    public int parseInt(String  num) {
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 0;
        }
    }
    public double parseDouble(String doub) {
        try {
            return Double.parseDouble(doub);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean inArea(Location loc,double highX,double highY,double highZ,double lowX,double lowY,double lowZ) {

        double y = loc.getY();
        double x = loc.getX();
        double z = loc.getZ();

        if (x >= lowX && x <= highX)
            if (y >= lowY && y <= highY)
                return z >= lowZ && z <= highZ;

        return false;
    }
}
