package overwitch.are_you_serious.enchanted.Util.EventHate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import overwitch.are_you_serious.enchanted.Enchant.EnchantmentAttackSpeed;

public class PlayerEventHandler {

    private int lastUpdateTick = 0;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote) {
            // 每20tick检查一次，减少性能开销
            if (event.player.world.getTotalWorldTime() - lastUpdateTick >= 20) {
                updatePlayerAttackSpeed(event.player);
                lastUpdateTick = (int) event.player.world.getTotalWorldTime();
            }
        }
    }

    private void updatePlayerAttackSpeed(EntityPlayer player) {
        // 只检查头盔槽位
        EntityEquipmentSlot slot = EntityEquipmentSlot.HEAD;
        ItemStack stack = player.getItemStackFromSlot(slot);
        int level = 0;
        boolean hasEnchantment = false;

        if (!stack.isEmpty()) {
            level = getAttackSpeedEnchantmentLevel(stack);
            if (level > 0) {
                hasEnchantment = true;
                System.out.println("在头盔上找到攻击速度附魔，等级: " + level);
            }
        }

        System.out.println("攻击速度附魔等级: " + level + ", 是否有附魔: " + hasEnchantment);

        // 应用或清除NBT修改
        if (hasEnchantment) {
            EnchantmentAttackSpeed.applyAttackSpeedNBT(player, level);
        } else {
            EnchantmentAttackSpeed.clearAttackSpeedNBT(player);
        }
    }

    private int getAttackSpeedEnchantmentLevel(ItemStack stack) {
        if (!stack.isItemEnchanted() || !stack.hasTagCompound()) {
            return 0;
        }

        NBTTagList enchantments = stack.getTagCompound().getTagList("ench", 10);
        for (int i = 0; i < enchantments.tagCount(); i++) {
            NBTTagCompound enchantment = enchantments.getCompoundTagAt(i);
            int id = enchantment.getShort("id");
            int level = enchantment.getShort("lvl");

            // 直接使用ID 14作为攻击速度附魔
            if (id == 14) {
                return level;
            }
        }

        return 0;
    }
}