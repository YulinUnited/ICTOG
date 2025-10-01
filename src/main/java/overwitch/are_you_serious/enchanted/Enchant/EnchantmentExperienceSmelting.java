package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class EnchantmentExperienceSmelting extends Enchantment {
    public EnchantmentExperienceSmelting() {
        super(Rarity.RARE, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
        this.setRegistryName("enchanted", "ExperienceSmelting");
        this.setName("ExperienceSmelting");
    }

    @Override
    public int getMaxLevel() {
        return super.getMaxLevel();
    }

    @Override
    public boolean isCurse() {
        return super.isCurse();
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.CHEST;
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        ItemStack chestArmor = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST); // 获取胸甲

        // 确保是护甲并且附魔有效
        if (chestArmor.getItem() instanceof ItemArmor && chestArmor.isItemEnchanted()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(chestArmor);
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment enchantment = entry.getKey();
                if (enchantment == ModEnchantments.EXPERIENCE_SMELTING) {
                    int level = entry.getValue();
                    if (level > 0) {
                        int experienceCost = 5 * level;
                        if (player.experienceLevel >= experienceCost) {
                            player.addExperienceLevel(-experienceCost); // 扣除经验

                            // 获取烧炼结果
                            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(chestArmor.getItem()));
                            if (result != null) {
                                // 查找背包中第一个空槽位并添加烧炼后的物品
                                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                                    if (player.inventory.getStackInSlot(i).isEmpty()) {
                                        player.inventory.setInventorySlotContents(i, result.copy());
                                        break;
                                    }
                                }
                                // 如果没有空槽，则掉落到世界中
                                if (!player.inventory.addItemStackToInventory(result.copy())) {
                                    event.getWorld().spawnEntity(new EntityItem(event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), result));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
