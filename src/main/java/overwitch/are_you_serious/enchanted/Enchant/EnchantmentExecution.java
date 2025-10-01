package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class EnchantmentExecution extends Enchantment {
    public EnchantmentExecution()
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("enchanted","Execution");
        this.setName("Execution");
    }
    @Override
    public int getMaxLevel()
    {
        return super.getMaxLevel();
    }
    @Override
    public boolean isCurse()
    {
        return super.isCurse();
    }
    @Override
    public boolean isTreasureEnchantment()
    {
        return true;
    }
    @Override
    public void onEntityDamaged(EntityLivingBase attacker, Entity target, int level) {
        if(target instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase=(EntityLivingBase) target;
            if(livingBase.getHealth()<livingBase.getMaxHealth()*0.3)
            {
                livingBase.attackEntityFrom(DamageSource.GENERIC,Float.MAX_VALUE);
            }
        }
    }
    @Override
    public String getName()
    {
        return "enchantment.Execution";
    }
}
