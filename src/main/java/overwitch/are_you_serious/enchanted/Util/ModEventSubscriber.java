package overwitch.are_you_serious.enchanted.Util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import overwitch.are_you_serious.enchanted.Enchant.*;

@Mod.EventBusSubscriber(modid = "enchanted")
public class ModEventSubscriber {
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event)
    {
        if(event.getEntityLiving()instanceof EntityPlayer)
        {
            EnchantmentExperienceRedemption.onPlayerDeath(event);
        }
    }
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event)
    {
        if(event.getEntityLiving()instanceof EntityPlayer)
        {
            UltimateRedemption.onPlayerDeath(event);
        }
    }
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        if(event.getEntityLiving()instanceof EntityLivingBase)
        {
            Enchantment_Strike_At_The_Critical_Moment.onPlayerHurt(event);
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        UltimateRepair.onPlayerTick(event);
    }
    @SubscribeEvent
    public static void onDrop(LivingExperienceDropEvent event)
    {
        ExperienceAccumulation.onExperienceDrop(event);
    }
}
