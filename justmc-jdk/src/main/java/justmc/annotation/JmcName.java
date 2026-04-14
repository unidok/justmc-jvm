package justmc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface JmcName {
    String name();
}
