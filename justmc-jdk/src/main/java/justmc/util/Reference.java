package justmc.util;

import justmc.Memory;
import justmc.Primitive;
import justmc.Unsafe;
import justmc.Variable;
import justmc.annotation.Inline;

@Inline
public final class Reference<T extends Primitive> {
    public Reference(T value) {
        setValue(value);
    }

    public Variable getVariable() {
        return Memory.getPrimitiveFieldsVariable(Unsafe.asAddress(this));
    }

    public void setValue(T value) {
        getVariable().setValue(value);
    }

    public T getValue() {
        return Unsafe.cast(getVariable());
    }
}
