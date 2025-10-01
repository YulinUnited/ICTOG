package overwitch.are_you_serious.enchanted.Util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class EntityPlayerEvent extends LivingAttackEvent {
    private float currentAmount;
    public EntityPlayerEvent(EntityLivingBase entity, DamageSource source, float amount, float currentAmount) {
        super(entity, source, amount);
        this.currentAmount = currentAmount;
    }
    @Override
    public float getAmount()
    {
        return currentAmount;
    }
    public final float setAmount(float value)
    {
        this.currentAmount=value;
        return currentAmount;
    }

}
