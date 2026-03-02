package justmc.event;

import justmc.GameValue;
import justmc.Util;

public interface MessageEvent {
    default String getMessage() {
        return Util.asString(GameValue.get("event_message"));
    }
}
