package overwitch.are_you_serious.enchanted.Util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.UUID;

public class ArmorAttributeHelper {

    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    /**
     * 给玩家所有盔甲写入攻击速度
     * @param player 玩家
     * @param level 攻击速度等级，每级可对应 +0.5
     */
    public static void applyAttackSpeedToArmor(EntityPlayer player, int level) {
        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            if (slot.getSlotType() != EntityEquipmentSlot.Type.ARMOR) continue;

            ItemStack stack = player.getItemStackFromSlot(slot);
            if (stack.isEmpty()) continue;

            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                stack.setTagCompound(tag);
            }

            NBTTagList attrList;
            if (tag.hasKey("AttributeModifiers", 9)) { // 9 = TAG_LIST
                attrList = tag.getTagList("AttributeModifiers", 10); // 10 = TAG_COMPOUND
            } else {
                attrList = new NBTTagList();
            }

            // 移除已有的攻击速度修饰符，防止重复
            for (int i = 0; i < attrList.tagCount(); i++) {
                NBTTagCompound mod = attrList.getCompoundTagAt(i);
                if ("generic.attackSpeed".equals(mod.getString("AttributeName"))) {
                    attrList.removeTag(i);
                    i--;
                }
            }

            // 添加新的攻击速度修饰符
            NBTTagCompound modifier = new NBTTagCompound();
            modifier.setString("AttributeName", "generic.attackSpeed");
            modifier.setString("Name", "Armor Attack Speed Bonus");
            modifier.setDouble("Amount", 0.5 * level);
            modifier.setInteger("Operation", 0); // 0 = 加到 BaseValue
            modifier.setString("Slot", slot.getName());
            modifier.setLong("UUIDMost", ATTACK_SPEED_UUID.getMostSignificantBits());
            modifier.setLong("UUIDLeast", ATTACK_SPEED_UUID.getLeastSignificantBits());

            attrList.appendTag(modifier);
            tag.setTag("AttributeModifiers", attrList);
        }
    }
}
