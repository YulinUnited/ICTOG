package overwitch.are_you_serious.enchanted.Util.EventHate;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "enchanted")
public class LastStrikeEvent {
    private static final Map<UUID, Long> lastStrikeActivationTime = new HashMap<>();
    private static final int LAST_STRIKE_DURATION = 2000; // 5秒
    private static final int ATTACK_SPEED_TICK=1200;
    private static final Map<UUID, Long> lastMessageTime = new HashMap<>(); // 限制消息发送频率

    @SubscribeEvent// 伤害计算完成后再判断
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
        System.out.println("以监听");
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        long currentTime = System.currentTimeMillis(); // 只调用一次 `System.currentTimeMillis()`

        if (player.getHealth()-event.getAmount() <= 6.0F) {
            lastStrikeActivationTime.put(player.getUniqueID(), currentTime); // 存入触发时间
            System.out.println("血量低于6");
            // 限制 `sendMessage()` 频率（1秒发送一次）
            if (!lastMessageTime.containsKey(player.getUniqueID()) || currentTime - lastMessageTime.get(player.getUniqueID()) > 1500) {
                System.out.println("⚡ 发送消息，间隔: " + (currentTime - lastMessageTime.getOrDefault(player.getUniqueID(), 0L)));
                player.sendMessage(new TextComponentString("§6你进入了临危一击状态！"));
                lastMessageTime.put(player.getUniqueID(), currentTime);
            }
        }
    }

    @SubscribeEvent // 默认优先级，避免影响其他插件
    public static void onPlayerAttack(LivingDamageEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        System.out.println("以触发");
        System.out.println("⚡ LivingHurtEvent 触发: " + player.getName() + " 受到伤害 " + event.getAmount() + " 当前血量: " + player.getHealth());
        long currentTime = System.currentTimeMillis(); // 只调用一次 `System.currentTimeMillis()`
        if(lastStrikeActivationTime.containsKey(player.getUniqueID())&&(currentTime- lastStrikeActivationTime.get(player.getUniqueID()))<ATTACK_SPEED_TICK)
        {
            return;
        }
        if (isInLastStrikeMode(player, currentTime)) {
            float damageDealt = event.getAmount();
            float healAmount = damageDealt * 0.5F;
            player.heal(healAmount);

            // 只遍历附近的活着的实体
            List<EntityLivingBase> nearbyEntities = player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(2.5D),
                    entity -> entity != player && entity.isEntityAlive() && player.getDistance(entity) <= 3.5D
            &&!entity.isOnSameTeam(player));

            for (EntityLivingBase target : nearbyEntities) {
                float sweepDamage = 6.0F; // 固定伤害
                target.attackEntityFrom(DamageSource.causePlayerDamage(player), sweepDamage);
            }

            // 限制 `sendMessage()` 频率（1秒发送一次）
            if (!lastMessageTime.containsKey(player.getUniqueID()) || currentTime - lastMessageTime.get(player.getUniqueID()) > 1500) {
                player.sendMessage(new TextComponentString("§a你通过临危一击恢复了 §c" + healAmount + " §a点生命值！"));
                lastMessageTime.put(player.getUniqueID(), currentTime);
            }
        }
    }

    private static boolean isInLastStrikeMode(EntityPlayer player, long currentTime) {
        if (!lastStrikeActivationTime.containsKey(player.getUniqueID())) return false;
        boolean isActive=(currentTime- lastStrikeActivationTime.get(player.getUniqueID()))<=LAST_STRIKE_DURATION;
        if(!isActive)
        {
            lastStrikeActivationTime.remove(player.getUniqueID());
        }
        //return (currentTime - lastStrikeActivationTime.get(player.getUniqueID())) <= LAST_STRIKE_DURATION;
        return isActive;
    }
}
