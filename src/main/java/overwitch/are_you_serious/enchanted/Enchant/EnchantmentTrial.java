package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

public class EnchantmentTrial extends Enchantment {
    public EnchantmentTrial()
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("enchanted","Trial");
        this.setName("Trial");
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
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem()instanceof ItemSword;
    }
    @Override
    public boolean isTreasureEnchantment()
    {
        return true;
    }
    @Override
    public void onEntityDamaged(EntityLivingBase attacker, Entity target, int level) {
        if (target instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) target;
            float targetHealth = entityLivingBase.getHealth();
            float damage = targetHealth * 0.90f;  // 90%的当前生命值伤害

            // 进行伤害计算
            if (!attacker.world.isRemote) {
                entityLivingBase.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) attacker), damage);
            }
        }
    }
    @Override
    public String getName()
    {
        return "enchantment.Trial";
    }
}
