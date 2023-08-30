package net.alfheim.internal.kotlinui.event;

import net.alfheim.internal.kotlinui.dsl.label.Label;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

/**
 * This class is used to listen to different **events**. This class acts exactly
 * the same as {@link Label#getListeners()}. For such purpose such as optimization,
 * it is now useless using it.
 *
 * @see EventTypes
 * @see Label#getListeners()
 */
@Deprecated(forRemoval = true)
public interface EventLabelCallback {
    Event<EventLabelCallback> EVENT = EventFactory.createArrayBacked(EventLabelCallback.class,
            (listeners) -> (event, label) -> {
                for (EventLabelCallback listener : listeners) {
                    ActionResult result = listener.interact(event, label);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(EventTypes eventType, Label label);
}