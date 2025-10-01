package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

public class EnchantmentLifeDrain extends Enchantment {
    public EnchantmentLifeDrain()
    {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON,new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("enchanted","LifeDrain");
        this.setName("LifeDrain");
    }
    @Override
    public int getMaxLevel()
    {
        return 3;
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
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem()instanceof ItemSword;
    }
    @Override
    public void onEntityDamaged(EntityLivingBase livingBase, Entity target,int level)
    {
        if(target instanceof EntityLivingBase)
        {
            EntityLivingBase entityLivingBase=(EntityLivingBase) target;
            float damage=entityLivingBase.getHealth()*(0.15F*level);
            float healthAmount=damage*0.25F;
            entityLivingBase.attackEntityFrom(DamageSource.GENERIC.setDamageBypassesArmor(),damage);
            livingBase.heal(healthAmount);
        }
    }
}
