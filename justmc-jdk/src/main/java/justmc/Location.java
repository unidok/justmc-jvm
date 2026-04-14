package justmc;

import justmc.annotation.Inline;
import justmc.enums.LocationAlignMode;
import justmc.enums.LocationCoordinate;
import justmc.enums.LocationCoordinates;
import org.jetbrains.annotations.NotNull;

@Inline
public final class Location extends Primitive {
    private Location() {}

    @NotNull
    public static Location of(double x, double y, double z, double yaw, double pitch) {
        var result = Variable.result();
        Unsafe.operation("set_variable_set_all_coordinates", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("x", NumberPrimitive.of(x)),
                Pair.of("y", NumberPrimitive.of(y)),
                Pair.of("z", NumberPrimitive.of(z)),
                Pair.of("yaw", NumberPrimitive.of(yaw)),
                Pair.of("pitch", NumberPrimitive.of(pitch))
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public static Location of(double x, double y, double z) {
        var result = Variable.result();
        Unsafe.operation("set_variable_set_all_coordinates", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("x", NumberPrimitive.of(x)),
                Pair.of("y", NumberPrimitive.of(y)),
                Pair.of("z", NumberPrimitive.of(z))
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Location set(LocationCoordinate coordinate, double value) {
        var result = Variable.result();
        Unsafe.operation("set_variable_set_coordinate", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("coordinate", NumberPrimitive.of(value)),
                Pair.of("type", coordinate)
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Location setX(double x) {
        return set(LocationCoordinate.X, x);
    }

    @NotNull
    public Location setY(double y) {
        return set(LocationCoordinate.Y, y);
    }

    @NotNull
    public Location setZ(double z) {
        return set(LocationCoordinate.Z, z);
    }

    @NotNull
    public Location setYaw(double yaw) {
        return set(LocationCoordinate.YAW, yaw);
    }

    @NotNull
    public Location setPitch(double pitch) {
        return set(LocationCoordinate.PITCH, pitch);
    }

    public void getAllCoordinates(Variable x, Variable y, Variable z, Variable yaw, Variable pitch) {
        Unsafe.operation("set_variable_get_all_coordinates", MapPrimitive.of(
                Pair.of("location", this),
                Pair.of("x", x),
                Pair.of("y", y),
                Pair.of("z", z),
                Pair.of("yaw", yaw),
                Pair.of("pitch", pitch)
        ));
    }

    public void getAllCoordinates(Variable x, Variable y, Variable z) {
        Unsafe.operation("set_variable_get_all_coordinates", MapPrimitive.of(
                Pair.of("location", this),
                Pair.of("x", x),
                Pair.of("y", y),
                Pair.of("z", z)
        ));
    }

    public double get(LocationCoordinate coordinate) {
        var result = Variable.result();
        Unsafe.operation("set_variable_get_coordinate", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("type", coordinate)
        ));
        return Unsafe.cast(result);
    }

    public double getX() {
        return get(LocationCoordinate.X);
    }

    public double getY() {
        return get(LocationCoordinate.Y);
    }

    public double getZ() {
        return get(LocationCoordinate.Z);
    }

    public double getYaw() {
        return get(LocationCoordinate.YAW);
    }

    public double getPitch() {
        return get(LocationCoordinate.PITCH);
    }

    @NotNull
    public Location add(double x, double y, double z, double yaw, double pitch) {
        var result = Variable.result();
        Unsafe.operation("set_variable_shift_all_coordinates", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("x", NumberPrimitive.of(x)),
                Pair.of("y", NumberPrimitive.of(y)),
                Pair.of("z", NumberPrimitive.of(z)),
                Pair.of("yaw", NumberPrimitive.of(yaw)),
                Pair.of("pitch", NumberPrimitive.of(pitch))
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Location add(double x, double y, double z) {
        var result = Variable.result();
        Unsafe.operation("set_variable_shift_all_coordinates", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("x", NumberPrimitive.of(x)),
                Pair.of("y", NumberPrimitive.of(y)),
                Pair.of("z", NumberPrimitive.of(z))
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Location add(LocationCoordinate coordinate, double add) {
        var result = Variable.result();
        Unsafe.operation("set_variable_shift_coordinate", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("distance", NumberPrimitive.of(add)),
                Pair.of("type", coordinate)
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Location addX(double add) {
        return add(LocationCoordinate.X, add);
    }

    @NotNull
    public Location addY(double add) {
        return add(LocationCoordinate.Y, add);
    }

    @NotNull
    public Location addZ(double add) {
        return add(LocationCoordinate.Z, add);
    }

    @NotNull
    public Location addYaw(double add) {
        return add(LocationCoordinate.YAW, add);
    }

    @NotNull
    public Location addPitch(double add) {
        return add(LocationCoordinate.PITCH, add);
    }

    @NotNull
    public Location add(Vector add) {
        var result = Variable.result();
        Unsafe.operation("set_variable_shift_location_on_vector", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("vector", add)
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Location add(Vector add, double length) {
        var result = Variable.result();
        Unsafe.operation("set_variable_shift_location_on_vector", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("vector", add),
                Pair.of("length", NumberPrimitive.of(length))
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Vector getDirection() {
        var result = Variable.result();
        Unsafe.operation("set_variable_get_location_direction", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this)
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Location setDirection(Vector direction) {
        var result = Variable.result();
        Unsafe.operation("set_variable_set_location_direction", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("vector", direction)
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Location clamp(Location pos1, Location pos2, LocationCoordinates.Clamp coordinates) {
        var result = Variable.result();
        Unsafe.operation("set_variable_clamp_location", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("corner_1", pos1),
                Pair.of("corner_2", pos2),
                Pair.of("coordinates_mode", coordinates)
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public Location align(boolean keepRotation, LocationCoordinates.Align coordinates, LocationAlignMode alignMode) {
        var result = Variable.result();
        Unsafe.operation("set_variable_align_location", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", this),
                Pair.of("rotation_mode", EnumPrimitive.of(keepRotation)),
                Pair.of("coordinates_mode", coordinates),
                Pair.of("align_mode", alignMode)
        ));
        return Unsafe.cast(result);
    }

    public double distance(Location to, LocationCoordinates.Distance coordinates) {
        var result = Variable.result();
        Unsafe.operation("set_variable_locations_distance", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location_1", this),
                Pair.of("location_2", to),
                Pair.of("type", coordinates)
        ));
        return Unsafe.cast(result);
    }
}
