package overwitch.are_you_serious.enchanted.Util.EventHate;

import net.minecraftforge.fml.common.eventhandler.EventPriority;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target(value = METHOD)
public @interface OverrideEvent
{
    EventPriority priority()default EventPriority.HIGHEST;
    boolean receiveCanceled()default false;
}
