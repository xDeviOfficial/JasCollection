package pl.jasmc.jashub.util;

import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.util.*;

public class UtilVelocity
{
    public static Vector getBumpVector(final Entity entity, final Location from, final double power) {
        final Vector bump = entity.getLocation().toVector().subtract(from.toVector()).normalize();
        bump.multiply(power);
        return bump;
    }

    public static Vector getpushVector(final Entity entity, final Location to, final double power) {
        final Vector push = to.toVector().subtract(entity.getLocation().toVector()).normalize();
        push.multiply(power);
        return push;
    }

    public static void bumpEntity(final Entity entity, final Location from, final double power) {
        entity.setVelocity(getBumpVector(entity, from, power));
    }

    public static void bumpEntity(final Entity entity, final Location from, final double power, final double fixedY) {
        final Vector vector = getBumpVector(entity, from, power);
        vector.setY(fixedY);
        entity.setVelocity(vector);
    }

    public static void pullEntity(final Entity entity) {
        entity.setVelocity(entity.getLocation().getDirection().multiply(-1.0).setY(0.8));
    }

    public static void pullEntity(final Entity entity, final Integer multiple) {
        entity.setVelocity(entity.getLocation().getDirection().multiply(-multiple).setY(0.8));
    }

    public static void pushEntity(final Entity entity, final Integer multiple) {
        entity.setVelocity(entity.getLocation().getDirection().multiply((int)multiple));
    }

    public static void pushEntity(final Entity entity, final Integer multiple, final Integer y) {
        entity.setVelocity(entity.getLocation().getDirection().multiply((int)multiple).setY((int)y));
    }

    public static void pushEntity(final Location loc, final Entity entity, final Integer multiple) {
        entity.setVelocity(loc.getDirection().multiply((int)multiple));
    }

    public static void pushEntityXZ(final Entity entity, final double fixedZ, final Location to, final double power, final double fixedX, final double fixedY) {
        final Vector vector = getpushVector(entity, to, power);
        vector.setY(fixedY);
        vector.setX(fixedX);
        vector.setZ(fixedZ);
        entity.setVelocity(vector);
    }

    public static void pushEntityZ(final Entity entity, final Location to, final double power, final double fixedZ, final double fixedY) {
        final Vector vector = getpushVector(entity, to, power);
        vector.setY(fixedY);
        vector.setZ(fixedZ);
        entity.setVelocity(vector);
    }

    public static void pushEntity(final Entity entity, final Location from, final double power, final double fixedY) {
        final Vector vector = getpushVector(entity, from, power);
        vector.setY(fixedY);
        entity.setVelocity(vector);
    }

    public static void velocity(final Entity ent, final double str, final double yAdd, final double yMax) {
        velocity(ent, ent.getLocation().getDirection(), str, false, 0.0, yAdd, yMax);
    }

    public static void velocity(final Entity ent, final Vector vec, final double str, final boolean ySet, final double yBase, final double yAdd, final double yMax) {
        if (Double.isNaN(vec.getX()) || Double.isNaN(vec.getY()) || Double.isNaN(vec.getZ()) || vec.length() == 0.0) {
            return;
        }
        if (ySet) {
            vec.setY(yBase);
        }
        vec.normalize();
        vec.multiply(str);
        vec.setY(vec.getY() + yAdd);
        if (vec.getY() > yMax) {
            vec.setY(yMax);
        }
        ent.setFallDistance(0.0f);
        ent.setVelocity(vec);
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

    public static final double angleToXAxis(final Vector vector) {
        return Math.atan2(vector.getX(), vector.getY());
    }
}
