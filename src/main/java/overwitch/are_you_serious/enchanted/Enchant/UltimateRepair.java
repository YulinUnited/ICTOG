package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import overwitch.are_you_serious.enchanted.Util.Mathf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UltimateRepair extends Enchantment {
    public static final int REPAIR_INTERVAL_TICKS = 4; // 每0.2秒
    public static final int REPAIR_AMOUNT = 10;
    private static final Map<UUID, Long> lastCombatTime = new HashMap<>();
    private static final int COMBAT_COOLDOWN_TICKS = 100; // 5秒

    public UltimateRepair()
    {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET,EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.LEGS,EntityEquipmentSlot.HEAD,EntityEquipmentSlot.CHEST});
        this.setName("UltimateRepair");
        this.setRegistryName("enchanted","UltimateRepair");
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }
    @Override
    public boolean isCurse()
    {
        return super.isCurse();
    }
    @Override
    public boolean isTreasureEnchantment()
    {
        return false;//不是宝藏附魔
    }
    @Override
    public boolean canApply(ItemStack stack)
    {
        return stack.isItemStackDamageable();
    }

    @Override
    public String getName()
    {
        return "enchantment.UltimateRepair";
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.phase!=TickEvent.Phase.END||event.player.world.isRemote)return;
        EntityPlayer player = event.player;
        long currentTime = player.world.getTotalWorldTime();
        long lastCombat = lastCombatTime.getOrDefault(player.getUniqueID(),0L);
        if(player.world.getTotalWorldTime()-lastCombat<COMBAT_COOLDOWN_TICKS)
        {
            return;//仍在战斗冷却钟
        }
        for(ItemStack stack:player.getEquipmentAndArmor())
        {
            if(stack.isEmpty()||!stack.isItemStackDamageable())continue;
            if(EnchantmentHelper.getEnchantmentLevel(ModEnchantments.ULTIMATEREPAIR,stack)>0)
            {
                int newDamage = (int) Mathf.Mathf_Integer_Max(0,stack.getItemDamage()-REPAIR_AMOUNT);
                stack.setItemDamage(newDamage);
            }
        }
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // 玩家受伤时记录战斗时间
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            lastCombatTime.put(player.getUniqueID(), player.world.getTotalWorldTime());
        }
        // 玩家攻击时也记录
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            lastCombatTime.put(player.getUniqueID(), player.world.getTotalWorldTime());
        }
    }
}
