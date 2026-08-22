package justmc.annotation;

/**
 * Аннотация для static методов, которые будут заменены на событие.
 * @see <a href="https://justwiki.gitbook.io/wiki/creative/editor/blocks/player_event">События игрока</a>
 * @see <a href="https://justwiki.gitbook.io/wiki/creative/editor/blocks/entity_event">События сущности</a>
 * @see <a href="https://justwiki.gitbook.io/wiki/creative/editor/blocks/world_event">События мира</a>
 */
public @interface EventHandler {
    String id() default "";
}
