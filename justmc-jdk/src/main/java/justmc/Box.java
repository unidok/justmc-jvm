package justmc;

import justmc.annotation.Inline;

@Inline
public final class Box<T extends Primitive> {
    public Box(T value) {
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
