package justmc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Использование классов и методов с пометкой {@link UnsafeMark}
 * может привести к неопределённому поведению или ошибкам в вашем коде.
 * Используйте, только если знаете, что делаете.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface UnsafeMark {
}
