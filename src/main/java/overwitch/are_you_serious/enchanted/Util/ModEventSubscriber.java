package overwitch.are_you_serious.enchanted.Util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import overwitch.are_you_serious.enchanted.Enchant.EnchantmentExperienceRedemption;

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
}
