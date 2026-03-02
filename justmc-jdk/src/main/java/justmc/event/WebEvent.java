package justmc.event;

import justmc.GameValue;
import justmc.Util;

public interface WebEvent {
    default String getUrl() {
        return Util.asString(GameValue.get("url"));
    }
}
