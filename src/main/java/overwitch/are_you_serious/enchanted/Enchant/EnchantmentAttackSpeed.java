package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class EnchantmentAttackSpeed extends Enchantment {

    // 为头盔使用固定的UUID
    private static final UUID HELMET_UUID = UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A5B5B8");

    public static final EnchantmentAttackSpeed INSTANCE = new EnchantmentAttackSpeed();

    public EnchantmentAttackSpeed() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.HEAD  // 只允许头盔槽位
        });
        this.setRegistryName("enchanted", "AttackSpeed");
        this.setName("AttackSpeed");
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

    @Override
    public int getMinEnchantability(int level) {
        return 10 + 5 * (level - 1);
    }

    @Override
    public int getMaxEnchantability(int level) {
        return super.getMinEnchantability(level) + 20;
    }

    public static void applyAttackSpeedNBT(EntityPlayer player, int totalLevel) {
        System.out.println("=== 开始应用攻击速度NBT，总等级: " + totalLevel + " ===");

        // 只处理头盔槽位
        EntityEquipmentSlot slot = EntityEquipmentSlot.HEAD;
        ItemStack stack = player.getItemStackFromSlot(slot);

        if (stack.isEmpty()) {
            System.out.println("头盔槽位为空");
            EnchantmentAttackSpeed.clearAttackSpeedNBT(player);
            return;
        }

        System.out.println("处理头盔: " + stack.getDisplayName());

        // 确保物品有NBT
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
            System.out.println("  创建了新NBT");
        }

        NBTTagCompound tag = stack.getTagCompound();

        // 获取或创建AttributeModifiers列表
        NBTTagList attrList;
        if (tag.hasKey("AttributeModifiers", Constants.NBT.TAG_LIST)) {
            attrList = tag.getTagList("AttributeModifiers", Constants.NBT.TAG_COMPOUND);
            System.out.println("  已有AttributeModifiers，数量: " + attrList.tagCount());
        } else {
            attrList = new NBTTagList();
            System.out.println("  创建新的AttributeModifiers列表");
        }

        // 移除HELMET_UUID的攻击速度修饰符
        int removed = removeOurAttackSpeedModifiers(attrList);
        System.out.println("  移除了 " + removed + " 个HELMET_UUID的攻击速度修饰符");

        // 检查头盔是否有攻击速度附魔
        int itemLevel = getAttackSpeedEnchantmentLevel(stack);
        if (itemLevel > 0) {
            // 为头盔添加攻击速度修饰符
            NBTTagCompound modifier = createAttackSpeedModifier(itemLevel, slot, HELMET_UUID);
            attrList.appendTag(modifier);
            System.out.println("  添加了攻击速度修饰符，Amount: " + itemLevel);
        }

        // 设置AttributeModifiers标签
        tag.setTag("AttributeModifiers", attrList);
        System.out.println("  最终AttributeModifiers数量: " + attrList.tagCount());

        // 重新设置NBT到物品
        stack.setTagCompound(tag);

        // 同步玩家装备
        player.inventory.markDirty();
        System.out.println("=== 攻击速度NBT应用完成 ===");
    }

    public static void clearAttackSpeedNBT(EntityPlayer player) {
        System.out.println("=== 清除攻击速度NBT ===");

        // 只处理头盔槽位
        EntityEquipmentSlot slot = EntityEquipmentSlot.HEAD;
        ItemStack stack = player.getItemStackFromSlot(slot);

        if (stack.isEmpty() || !stack.hasTagCompound()) {
            return;
        }

        NBTTagCompound tag = stack.getTagCompound();
        if (tag.hasKey("AttributeModifiers", Constants.NBT.TAG_LIST)) {
            NBTTagList attrList = tag.getTagList("AttributeModifiers", Constants.NBT.TAG_COMPOUND);
            boolean modified = removeOurAttackSpeedModifiers(attrList) > 0;

            if (modified) {
                if (attrList.tagCount() > 0) {
                    tag.setTag("AttributeModifiers", attrList);
                } else {
                    tag.removeTag("AttributeModifiers");
                }
                stack.setTagCompound(tag);
                System.out.println("清除了头盔的攻击速度修饰符");
            }
        }
        player.inventory.markDirty();
    }

    private static int removeOurAttackSpeedModifiers(NBTTagList attrList) {
        int removed = 0;
        for (int i = attrList.tagCount() - 1; i >= 0; i--) {
            NBTTagCompound mod = attrList.getCompoundTagAt(i);
            if ("generic.attackSpeed".equals(mod.getString("AttributeName"))) {
                // 只移除我们特定UUID的修饰符
                UUID modUUID = new UUID(mod.getLong("UUIDMost"), mod.getLong("UUIDLeast"));
                if (modUUID.equals(HELMET_UUID)) {
                    attrList.removeTag(i);
                    removed++;
                    System.out.println("  移除我们的攻击速度修饰符: " + modUUID);
                }
            }
        }
        return removed;
    }

    private static int getAttackSpeedEnchantmentLevel(ItemStack stack) {
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

    private static NBTTagCompound createAttackSpeedModifier(int level, EntityEquipmentSlot slot, UUID uuid) {
        NBTTagCompound modifier = new NBTTagCompound();

        modifier.setString("AttributeName", "generic.attackSpeed");
        modifier.setString("Name", "AttackSpeedEnchantment");
        modifier.setDouble("Amount", level * 1.0); // 每级+1.0攻击速度
        modifier.setInteger("Operation", 0);
        modifier.setLong("UUIDLeast", uuid.getLeastSignificantBits());
        modifier.setLong("UUIDMost", uuid.getMostSignificantBits());
        modifier.setString("Slot", "head"); // 固定为头盔槽位

        return modifier;
    }
}