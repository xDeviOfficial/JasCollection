package pl.jasmc.jashub.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.Location;

import java.text.DecimalFormat;
import java.util.Random;

public class UtilMath
{
    public static final Random random;
    public static final float nanoToSec = 1.0E-9f;
    public static final float FLOAT_ROUNDING_ERROR = 1.0E-6f;
    public static final float PI = 3.1415927f;
    public static final float PI2 = 6.2831855f;
    public static final float SQRT_3 = 1.73205f;
    public static final float E = 2.7182817f;
    public static final float radiansToDegrees = 57.295776f;
    public static final float radDeg = 57.295776f;
    public static final float degreesToRadians = 0.017453292f;
    public static final float degRad = 0.017453292f;
    static final int ATAN2_DIM;
    private static final float INV_ATAN2_DIM_MINUS_1;

    static {
        random = new Random(System.nanoTime());
        ATAN2_DIM = (int)Math.sqrt(16384.0);
        INV_ATAN2_DIM_MINUS_1 = 1.0f / (UtilMath.ATAN2_DIM - 1);
    }

    public static int getRandomNumberBetween(final int min, final int max) {
        final Random foo = new Random();
        final int randomNumber = foo.nextInt(max - min) + min;
        if (randomNumber == min) {
            return min + 1;
        }
        return randomNumber;
    }

    public static boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static int randRange(final int min, final int max) {
        final Random r = new Random();
        return min + r.nextInt() * (max - min);
    }

    public static double randomRange(final double min, final double max) {
        return (Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min);
    }

    public static float randomRangeFloat(final float min, final float max) {
        return (float)((Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
    }

    public static int randomRangeInt(final int min, final int max) {
        return (int)((Math.random() < 0.5) ? ((1.0 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
    }

    public static Vector getRandomVector() {
        final double x = UtilMath.random.nextDouble() * 2.0 - 1.0;
        final double y = UtilMath.random.nextDouble() * 2.0 - 1.0;
        final double z = UtilMath.random.nextDouble() * 2.0 - 1.0;
        return new Vector(x, y, z).normalize();
    }

    public static Vector getRandomVectorline() {
        final int minz = 1;
        final int maxz = 3;
        final int rz = (int)(Math.random() * 2.0 + 1.0);
        final double miny = -1.0;
        final double maxy = 1.0;
        final double ry = Math.random() * 2.0 - 1.0;
        final double x = -5.0;
        final double y = ry;
        final double z = rz;
        return new Vector(-5.0, y, z).normalize();
    }

    public static final Vector rotateAroundAxisX(final Vector v, final double angle) {
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double y = v.getY() * cos - v.getZ() * sin;
        final double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static final Vector rotateAroundAxisY(final Vector v, final double angle) {
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double x = v.getX() * cos + v.getZ() * sin;
        final double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static final Vector rotateAroundAxisZ(final Vector v, final double angle) {
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double x = v.getX() * cos - v.getY() * sin;
        final double y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }

    public static final Vector rotateVector(final Vector v, final double angleX, final double angleY, final double angleZ) {
        rotateAroundAxisX(v, angleX);
        rotateAroundAxisY(v, angleY);
        rotateAroundAxisZ(v, angleZ);
        return v;
    }

    public static Vector calculateVelocity(final Player p, final Entity e) {
        final Location ploc = p.getLocation();
        final Location eloc = e.getLocation();
        final double px = ploc.getX();
        final double py = ploc.getY();
        final double pz = ploc.getZ();
        final double ex = eloc.getX();
        final double ey = eloc.getY();
        final double ez = eloc.getZ();
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        if (px < ex) {
            x = 2.0;
        }
        else if (px > ex) {
            x = -2.0;
        }
        if (py < ey) {
            y = 1.0;
        }
        else if (py > ey) {
            y = 1.0;
        }
        if (pz < ez) {
            z = 2.0;
        }
        else if (pz > ez) {
            z = -2.0;
        }
        return new Vector(x, y, z);
    }

    public static Vector calculateVelocity(final Location l, final Entity e) {
        final Location eloc = e.getLocation();
        final double px = l.getX();
        final double py = l.getY();
        final double pz = l.getZ();
        final double ex = eloc.getX();
        final double ey = eloc.getY();
        final double ez = eloc.getZ();
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        if (px < ex) {
            x = 2.0;
        }
        else if (px > ex) {
            x = -2.0;
        }
        if (py < ey) {
            y = 1.0;
        }
        else if (py > ey) {
            y = 1.0;
        }
        if (pz < ez) {
            z = 2.0;
        }
        else if (pz > ez) {
            z = -2.0;
        }
        return new Vector(x, y, z);
    }

    public static double trim(final int degree, final double d) {
        String format = "#.#";
        for (int i = 1; i < degree; ++i) {
            format = String.valueOf(String.valueOf(format)) + "#";
        }
        final DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.valueOf(twoDForm.format(d));
    }

    public static int r(final int i) {
        return UtilMath.random.nextInt(i);
    }

    public static double offset2d(final Entity a, final Entity b) {
        return offset2d(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset2d(final Location a, final Location b) {
        return offset2d(a.toVector(), b.toVector());
    }

    public static double offset2d(final Vector a, final Vector b) {
        a.setY(0);
        b.setY(0);
        return a.subtract(b).length();
    }

    public static double offset(final Entity a, final Entity b) {
        return offset(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset(final Location a, final Location b) {
        return offset(a.toVector(), b.toVector());
    }

    public static double offset(final Vector a, final Vector b) {
        return a.subtract(b).length();
    }

    public static final float sin(final float radians) {
        return Sin.table[(int)(radians * 2607.5945f) & 0x3FFF];
    }

    public static final float cos(final float radians) {
        return Sin.table[(int)((radians + 1.5707964f) * 2607.5945f) & 0x3FFF];
    }

    public static final float sinDeg(final float degrees) {
        return Sin.table[(int)(degrees * 45.511112f) & 0x3FFF];
    }

    public static final float cosDeg(final float degrees) {
        return Sin.table[(int)((degrees + 90.0f) * 45.511112f) & 0x3FFF];
    }

    public static final float atan2(float y, float x) {
        float mul;
        float add;
        if (x < 0.0f) {
            if (y < 0.0f) {
                y = -y;
                mul = 1.0f;
            }
            else {
                mul = -1.0f;
            }
            x = -x;
            add = -3.1415927f;
        }
        else {
            if (y < 0.0f) {
                y = -y;
                mul = -1.0f;
            }
            else {
                mul = 1.0f;
            }
            add = 0.0f;
        }
        final float invDiv = 1.0f / (((x < y) ? y : x) * UtilMath.INV_ATAN2_DIM_MINUS_1);
        if (invDiv == Float.POSITIVE_INFINITY) {
            return ((float)Math.atan2(y, x) + add) * mul;
        }
        final int xi = (int)(x * invDiv);
        final int yi = (int)(y * invDiv);
        return (Atan2.table[yi * UtilMath.ATAN2_DIM + xi] + add) * mul;
    }

    public static final int random(final int range) {
        return UtilMath.random.nextInt(range + 1);
    }

    public static final int random(final int start, final int end) {
        return start + UtilMath.random.nextInt(end - start + 1);
    }

    public static final boolean randomBoolean() {
        return UtilMath.random.nextBoolean();
    }

    public static final boolean randomBoolean(final float chance) {
        return random() < chance;
    }

    public static final float random() {
        return UtilMath.random.nextFloat();
    }

    public static final float random(final float range) {
        return UtilMath.random.nextFloat() * range;
    }

    public static final float random(final float start, final float end) {
        return start + UtilMath.random.nextFloat() * (end - start);
    }

    public static int nextPowerOfTwo(int value) {
        if (value == 0) {
            return 1;
        }
        value = (--value | value >> 1);
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        value |= value >> 16;
        return value + 1;
    }

    public static boolean isPowerOfTwo(final int value) {
        return value != 0 && (value & value - 1) == 0x0;
    }

    public static int clamp(final int value, final int min, final int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static short clamp(final short value, final short min, final short max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static float clamp(final float value, final float min, final float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static int floor(final float x) {
        return (int)(x + 16384.0) - 16384;
    }

    public static int floorPositive(final float x) {
        return (int)x;
    }

    public static int ceil(final float x) {
        return (int)(x + 16384.999999999996) - 16384;
    }

    public static int ceilPositive(final float x) {
        return (int)(x + 0.9999999);
    }

    public static int round(final float x) {
        return (int)(x + 16384.5) - 16384;
    }

    public static int roundPositive(final float x) {
        return (int)(x + 0.5f);
    }

    public static boolean isZero(final float value) {
        return Math.abs(value) <= 1.0E-6f;
    }

    public static boolean isZero(final float value, final float tolerance) {
        return Math.abs(value) <= tolerance;
    }

    public static boolean isEqual(final float a, final float b) {
        return Math.abs(a - b) <= 1.0E-6f;
    }

    public static boolean isEqual(final float a, final float b, final float tolerance) {
        return Math.abs(a - b) <= tolerance;
    }

    private static class Sin
    {
        static final float[] table;

        static {
            table = new float[16384];
            for (int i = 0; i < 16384; ++i) {
                Sin.table[i] = (float)Math.sin((i + 0.5f) / 16384.0f * 6.2831855f);
            }
            for (int i = 0; i < 360; i += 90) {
                Sin.table[(int)(i * 45.511112f) & 0x3FFF] = (float)Math.sin(i * 0.017453292f);
            }
        }
    }

    private static class Atan2
    {
        static final float[] table;

        static {
            table = new float[16384];
            for (int i = 0; i < UtilMath.ATAN2_DIM; ++i) {
                for (int j = 0; j < UtilMath.ATAN2_DIM; ++j) {
                    final float x0 = i / UtilMath.ATAN2_DIM;
                    final float y0 = j / UtilMath.ATAN2_DIM;
                    Atan2.table[j * UtilMath.ATAN2_DIM + i] = (float)Math.atan2(y0, x0);
                }
            }
        }
    }
}
