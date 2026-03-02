package justmc.event.world;

import justmc.CopyableList;
import justmc.CopyableMap;
import justmc.GameValue;
import justmc.Util;
import justmc.event.MessageEvent;
import justmc.event.WebEvent;

public final class WebResponseEvent implements WebEvent {
    private WebResponseEvent() {}

    public native String getResponse();
    public native int getResponseCode();
    public native CopyableMap<String, CopyableList<String>> getHeaders();
}
