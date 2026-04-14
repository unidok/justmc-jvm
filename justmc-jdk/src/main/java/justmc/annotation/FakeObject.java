package justmc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
public @interface FakeObject {
}
