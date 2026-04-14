package justmc;

import justmc.annotation.Inline;
import justmc.enums.BlockValueType;
import justmc.enums.FluidCollisionMode;
import justmc.enums.RayCollisionMode;

@Inline
public final class World {
    private World() {}

    /**
     * Получает игровое значение.
     * @param id Идентификатор значения
     * @return Игровое значение
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/values.json">Список значений</a>
     */
    public static native Primitive getValue(String id);

    public static int cpu() {
        return Unsafe.asInt(getValue("cpu_usage"));
    }

    public static <T> T getBlock(Location location, BlockValueType<T> valueType) {
        var result = Variable.result();
        Unsafe.operation("set_variable_get_block_material", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("location", location),
                Pair.of("value_type", valueType)
        ));
        return Unsafe.cast(result);
    }

    public static void setBlock(Location location, Block block) {
        Unsafe.operation("game_set_block", MapPrimitive.of(
                Pair.of("locations", location),
                Pair.of("block", block)
        ));
    }

    public static void setBlock(ListPrimitive<Location> locations, Block block) {
        Unsafe.operation("game_set_block", MapPrimitive.of(
                Pair.of("locations", locations),
                Pair.of("block", block)
        ));
    }

    public static void setBlock(ListPrimitive<Location> locations, Block block, boolean updateBlocks) {
        Unsafe.operation("game_set_block", MapPrimitive.of(
                Pair.of("locations", locations),
                Pair.of("block", block),
                Pair.of("update_blocks", EnumPrimitive.of(updateBlocks))
        ));
    }

    public static RayTraceResult rayTrace(
            Location start,
            double raySize,
            double maxDistance,
            RayCollisionMode rayCollisionMode,
            boolean ignorePassableBlocks,
            FluidCollisionMode fluidCollisionMode,
            ListPrimitive<Text> entities
    ) {
        var hitLocation = Variable.temp();
        var hitBlockLocation = Variable.temp();
        var hitBlockFace = Variable.temp();
        var hitEntityUUID = Variable.temp();
        Unsafe.operation("set_variable_ray_trace_result", MapPrimitive.of(
                Pair.of("start", start),
                Pair.of("ray_size", NumberPrimitive.of(raySize)),
                Pair.of("max_distance", NumberPrimitive.of(maxDistance)),
                Pair.of("ray_collision_mode", rayCollisionMode),
                Pair.of("ignore_passable_blocks", EnumPrimitive.of(ignorePassableBlocks)),
                Pair.of("fluid_collision_mode", Unsafe.cast(fluidCollisionMode)),
                Pair.of("variable_for_hit_location", hitLocation),
                Pair.of("variable_for_hit_block_location", hitBlockLocation),
                Pair.of("variable_for_hit_block_face", hitBlockFace),
                Pair.of("variable_for_hit_entity_uuid", hitEntityUUID),
                Pair.of("entities", entities)
        ));
        return RayTraceResult.of(
                Unsafe.cast(hitLocation),
                Unsafe.cast(hitBlockLocation),
                Unsafe.cast(hitBlockFace),
                Unsafe.cast(hitEntityUUID)
        );
    }
}
