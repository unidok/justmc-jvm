package justmc.event;

import justmc.GameValue;
import justmc.enums.EquipmentSlot;

public interface EquipmentSlotEvent {
    default EquipmentSlot getEquipmentSlot() {
        return (EquipmentSlot) GameValue.get("event_equipment_slot");
    }
}
