package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class EnchantmentExperienceDamage extends Enchantment
{
    public EnchantmentExperienceDamage()
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("enchanted","ExperienceDamage");
        this.setName("ExperienceDamage");
    }
    @Override
    public int getMaxLevel()
    {
        return super.getMaxLevel();
    }
    @Override
    public void onEntityDamaged(EntityLivingBase attacker, Entity target, int level) {
        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;
            int experience = player.experienceLevel;  // 获取玩家的经验等级
            float damage = experience * 2.0f;  // 经验转化为伤害

            // 对目标类型进行判断，减少伤害
            if (target instanceof EntityWitch || target instanceof EntityEvoker) {
                damage *= 0.5f;  // 女巫和唤魔者伤害削减50%
            }

            // 进行魔法伤害计算，伤害无视护甲
            if (!attacker.world.isRemote) {
                target.attackEntityFrom(DamageSource.MAGIC, damage);
            }
        }
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
    public String getName()
    {
        return "enchantment.ExperienceDamage";
    }
}
