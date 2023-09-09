package net.skteam.mumodpack.Item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Rarity;

public class bossAxe extends AxeItem {

    public bossAxe(Settings settings){
        super(ToolMaterials.IRON, 7, -2f, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int hpHeal = 4;
        attacker.setHealth(attacker.getHealth() + hpHeal);
        return super.postHit(stack, target, attacker);
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }
}
