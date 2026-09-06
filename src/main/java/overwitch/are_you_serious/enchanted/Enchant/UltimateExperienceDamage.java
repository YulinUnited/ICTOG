package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import overwitch.are_you_serious.enchanted.Util.Mathf;

public class UltimateExperienceDamage extends Enchantment {
    public UltimateExperienceDamage()
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("enchanted","UltimateExperienceDamage");
        this.setName("UltimateExperienceDamage");
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
        return false;
    }
    @Override
    public String getName()
    {
        return "enchantment.UltimateExperienceDamage";
    }

    @Override
    public void onEntityDamaged(EntityLivingBase livingBase, Entity target,int level)
    {
        if(livingBase instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) livingBase;
            float experienceDamage = player.experienceLevel;
            float damage = experienceDamage*6.0f;//将经验值转换为伤害，基数为6倍
            if(target.isDead)
            {
                return;
            }
            if(!livingBase.world.isRemote)
            {
                if(target instanceof EntityLivingBase)
                {
                    EntityLivingBase EntityLiving=(EntityLivingBase) target;
                    if(Float.isNaN(EntityLiving.getHealth())&&Float.isNaN(EntityLiving.getMaxHealth()))
                    {
                        EntityLiving.onDeath(new EntityDamageSource("UltimateExperienceDamage",player));
                        return;
                    }
                    EntityLiving.attackEntityFrom(new EntityDamageSource("UltimateExperienceDamage",player).setDamageIsAbsolute(), damage);
                }
                EntityLivingBase EntityLiving = null;
                if (target instanceof EntityLivingBase)
                {
                    EntityLiving = (EntityLivingBase) target;

                    if (EntityLiving.getMaxHealth() > 50.0F) {
                        float target_Damage = EntityLiving.getHealth();
                        float True_Damage = Mathf.Max(0.0F, target_Damage - damage);
                        EntityLiving.setHealth(True_Damage);
                        if (EntityLiving.getHealth() <= 0) {
                            EntityLiving.onDeath(new DamageSource("UltimateExperienceDamage"));
                        }
                    }
                }
            }
        }
    }
}
