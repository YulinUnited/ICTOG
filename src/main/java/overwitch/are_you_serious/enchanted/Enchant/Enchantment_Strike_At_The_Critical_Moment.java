package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static overwitch.are_you_serious.enchanted.Enchant.ModEnchantments.STRIKEATTHECRITICALMOMENT;

public class Enchantment_Strike_At_The_Critical_Moment extends Enchantment {
    public Enchantment_Strike_At_The_Critical_Moment() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.HEAD,EntityEquipmentSlot.FEET,EntityEquipmentSlot.LEGS,EntityEquipmentSlot.CHEST  // 附魔兼容全套装备
        });
        this.setName("Strike_At_The_Critical_Moment");
        this.setRegistryName("enchanted","StrikeAtTheCriticalMoment");
    }
    @Override
    public int getMinLevel()
    {
        return super.getMinLevel();
    }
    @Override
    public boolean isAllowedOnBooks()
    {
        return super.isAllowedOnBooks();
    }
    @Override
    public boolean isTreasureEnchantment()
    {
        return super.isTreasureEnchantment();
    }
    @Override
    public boolean isCurse()
    {
        return super.isCurse();
    }
    @Override
    public String getName()
    {
        return "enchantment.StrikeAtTheCriticalMoment";
    }
    @Override
    public int getMaxLevel()
    {
        return super.getMaxLevel();
    }
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event)
    {
        if(event.getEntityLiving().world.isRemote||!(event.getEntityLiving()instanceof EntityLivingBase))
        {
            return;
        }
        EntityLivingBase victim = event.getEntityLiving();
        ItemStack head = victim.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack feet = victim.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack legs = victim.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        ItemStack chest = victim.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if(victim.isDead||!(victim.getHealth()<=0)||victim.isEntityAlive())
        {
            if(EnchantmentHelper.getEnchantmentLevel(STRIKEATTHECRITICALMOMENT,head)>0 && EnchantmentHelper.getEnchantmentLevel(STRIKEATTHECRITICALMOMENT,chest)>0&& EnchantmentHelper.getEnchantmentLevel(STRIKEATTHECRITICALMOMENT,legs)>0 && EnchantmentHelper.getEnchantmentLevel(STRIKEATTHECRITICALMOMENT,feet)>0)
            {
                float currentHealth = victim.getHealth();
                float finalHealthAfterDamage = currentHealth - event.getAmount();
                if(finalHealthAfterDamage < 6.0F)
                {
                    long currentTime = victim.world.getTotalWorldTime();
                    if(victim.getEntityData().hasKey("StrikeAtTheCriticalMoment"))
                    {
                        long lastTriggerTime = victim.getEntityData().getLong("StrikeAtTheCriticalMoment");
                        if(currentTime - lastTriggerTime<1200L)
                        {
                            return;
                        }
                    }

                    event.setAmount(0.0F);
                    victim.setHealth(victim.getMaxHealth());

                    DamageSource customSource;
                    if(victim instanceof EntityPlayer)
                    {
                        customSource = DamageSource.causePlayerDamage((EntityPlayer) victim).setDamageIsAbsolute();
                    }
                    else customSource = DamageSource.causeMobDamage(victim).setDamageBypassesArmor();
                    float baseDamage;
                    if(victim instanceof EntityPlayer)
                    {
                        baseDamage = ((EntityPlayer)victim).experienceLevel * 6.0F;
                    }
                    else
                    {
                        baseDamage = 12.0F;
                    }
                    AxisAlignedBB area = victim.getEntityBoundingBox().grow(5.0D,2.5D,5.0D);
                    List<EntityLivingBase>targets = victim.world.getEntitiesWithinAABB(EntityLivingBase.class,area);
                    for(EntityLivingBase target:targets)
                    {
                        if(target!=victim&&target.isDead)
                        {
                            float True_Damage = baseDamage;
                            if(target instanceof EntityPlayer)
                            {
                                True_Damage = 6.0F;
                            }
                            target.attackEntityFrom(customSource.setDamageAllowedInCreativeMode(),True_Damage);
                        }
                    }
                    victim.getEntityData().setLong("StrikeAtTheCriticalMoment",currentTime);
                }
            }
        }
    }
}
