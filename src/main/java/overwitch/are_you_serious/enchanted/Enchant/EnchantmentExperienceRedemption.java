package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentExperienceRedemption extends Enchantment {
    public EnchantmentExperienceRedemption()
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR_HEAD,new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD});
        this.setName("ExperienceRedemption");
        this.setRegistryName("enchanted","DeathPrevention");
    }
    @Override
    public int getMaxLevel()
    {
        return super.getMaxLevel();
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
        return "enchantment.ExperienceRedemption";
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer)) {
            return; // 仅对玩家生效
        }

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

        // 检查玩家的头盔是否有 "DeathPrevention" 附魔
        if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.DEATH_PREVENTION, helmet) > 0) {
            int requiredXP = 50; // 设定消耗的经验值
            if (player.experienceLevel >= requiredXP) {
                // 玩家有足够的经验值，取消死亡事件并消耗经验
                player.addExperienceLevel(-requiredXP); // 消耗经验
                player.setHealth(6.0F);
                event.setCanceled(true); // 取消死亡事件
                player.sendMessage(new TextComponentString("警告：侦测到死亡，现消耗经验值助您复活！"));
            }
        }
    }
}
