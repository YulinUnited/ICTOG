package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class UltimateExecution extends Enchantment {
    public UltimateExecution()
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("enchanted","UltimateExecution");
        this.setName("UltimateExecution");
    }
    @Override
    public int getMaxLevel()
    {
        return 1;
    }
    @Override
    public boolean isCurse()
    {
        return false;
    }
    @Override
    public boolean isTreasureEnchantment()
    {
        return false;
    }
    @Override
    public String getName()
    {
        return "enchantment.UltimateExecution";
    }

    @Override
    public void onEntityDamaged(EntityLivingBase entityLivingBase, Entity target, int level)
    {
        if(target instanceof EntityLivingBase)
        {
            EntityLivingBase livingEntity = (EntityLivingBase) target;
            if(livingEntity.getHealth()<livingEntity.getMaxHealth()*0.6)
            {
                if(Float.isNaN(livingEntity.getHealth()))
                {
                    livingEntity.setDead();
                    livingEntity.onDeath(new DamageSource("UltimateExecution"));
                }
                livingEntity.setHealth(0.0F);
                livingEntity.onDeath(new DamageSource("UltimateExecution"));
            }
            if(livingEntity.getMaxHealth()>220.0F)
            {
                livingEntity.onDeath(new DamageSource("UltimateExecution"));
            }
        }
    }
}
