package justmc;

public final class EntityId extends Primitive {
    public final Text nameOrUUID;

    public EntityId(Text nameOrUUID) {
        this.nameOrUUID = nameOrUUID;
    }
}
