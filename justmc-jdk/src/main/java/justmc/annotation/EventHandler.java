package justmc.annotation;

/**
 * Аннотация для static методов, которые будут заменены на событие.
 */
public @interface EventHandler {
    String id() default "";
}
