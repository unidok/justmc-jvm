package justmc.event;

public interface CancellableEvent {
    default void cancel() {
        //Util.operation("game_cancel_event");
    }

    default void uncancel() {
        //Util.operation("game_uncancel_event");
    }
}
