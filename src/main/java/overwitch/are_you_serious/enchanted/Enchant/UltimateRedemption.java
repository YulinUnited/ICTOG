package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UltimateRedemption extends Enchantment {
    public UltimateRedemption()
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR,new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD});
        this.setName("UltimateRedemption");
        this.setRegistryName("enchanted","UltimateRedemption");
    }
    @Override
    public int getMaxLevel()
    {
        return 1;
    }
    @Override
    public int getMinLevel()
    {
        return 1;
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
        return false;
    }
    @Override
    public String getName()
    {
        return "enchantment.UltimateRedemption";
    }
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event)
    {
        EntityLivingBase LivingEntity = event.getEntityLiving();
        Entity entity = event.getEntity();
        if(LivingEntity!=null)
        {
            if(!(event.getEntityLiving() instanceof EntityPlayer))
            {
                return;
            }
            EntityPlayer player = (EntityPlayer)entity;
            ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.ULTIMATEREDEMPTION,head)>0)
            {
                int requiredXP=20;
                if(player.experienceLevel>=requiredXP)
                {
                    player.addExperienceLevel(-requiredXP);
                    player.setHealth(player.getMaxHealth());
                    if(player.getMaxHealth()<=0.0F)player.setHealth(20.0F);
                    event.setCanceled(true);
                    player.sendMessage(new TextComponentString("已启动终极复活效果，扣减经验值助您复活!"));
                    return;
                }
                else if(player.getFoodStats().getFoodLevel() >= 5.0F) {
                    int ConsumeFood = 5;
                    player.getFoodStats().setFoodLevel(ConsumeFood);
                    player.setHealth(20.0F);
                    event.setCanceled(true);
                    player.sendMessage(new TextComponentString("您的经验值不足，已使用饱和度助您复活！"));
                    return;
                }
                else {
                    if(!player.getHeldItemOffhand().isEmpty())
                    {
                        ItemStack itemStack = player.getHeldItemOffhand();
                        itemStack.shrink(5);
                        player.setHealth(20.0F);
                        event.setCanceled(true);
                        player.sendMessage(new TextComponentString("您的饱和度、经验值不足，以消耗您副手的物品助您复活！"));
                        return;
                    }
                }
            }
        }
    }
}
