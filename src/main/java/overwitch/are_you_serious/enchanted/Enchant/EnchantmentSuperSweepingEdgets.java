package overwitch.are_you_serious.enchanted.Enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentSweepingEdge;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentSuperSweepingEdgets extends EnchantmentSweepingEdge {
    public EnchantmentSuperSweepingEdgets(Rarity r1, EntityEquipmentSlot... r2) {
        super(r1, r2);
        setRegistryName("enchanted","SuperSweepingEdgets");
    }
    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 2 + (enchantmentLevel - 1) * 9;
    }
    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }
    @Override
    public int getMaxLevel()
    {
        return 6;
    }

    public static float getSweepingDamageRatio(int level)
    {
        return 1.5F +level *0.4F;
    }
    @Override
    public boolean isTreasureEnchantment()
    {
        return true;
    }
    @Override
    public String getName()
    {
        return "enchantment.SuperSweeping";
    }
    @Override
    public boolean canApplyTogether(Enchantment enchantment)
    {
        return !(enchantment instanceof EnchantmentSweepingEdge)&&super.canApplyTogether(enchantment);
    }
}
