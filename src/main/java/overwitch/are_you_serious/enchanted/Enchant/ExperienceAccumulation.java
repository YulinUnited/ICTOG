package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExperienceAccumulation extends Enchantment {
    public ExperienceAccumulation()
    {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON,new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("enchanted","ExperienceAccumulation");
        this.setName("ExperienceAccumulation");
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
        return false;
    }
    @Override
    public String getName()
    {
        return "enchantment.ExperienceAccumulation";
    }

    @Override
    public int getMinEnchantability(int level)
    {
        return 15+(level-1)*10;
    }
    @Override
    public int getMaxEnchantability(int level)
    {
        return getMinEnchantability(level)+50;
    }

    @SubscribeEvent
    public static void onExperienceDrop(LivingExperienceDropEvent event)
    {
        EntityPlayer player=event.getAttackingPlayer();
        if(player==null)return;
        if(!player.world.isRemote)
        {
            ItemStack weapon = player.getHeldItemMainhand();
            EntityLivingBase livingBase=event.getEntityLiving();
            int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.EXPERIENCEACCUMULATION,weapon);
            if(level<=0)return;
            float multiplier;
            switch (level) {
                case 1: multiplier = 3.0f; break;
                case 2: multiplier = 6.0f; break;
                case 3: multiplier = 9.0f; break;
                default: multiplier = 1.0f;
            }
            int originalExp=event.getDroppedExperience();
            int newExp=(int)(originalExp*multiplier);
            event.setDroppedExperience(newExp);
        }
    }
}
