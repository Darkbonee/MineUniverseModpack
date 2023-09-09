package net.skteam.mumodpack.util;

import net.minecraft.entity.damage.DamageSource;

public interface AnimatedDeathInterface {
    
    public void onDeath(DamageSource damageSource);
    public void updatePostDeath();
    public int getTicksUntilDeath();
    public int getDeathTicks();
    public void setDeath();
}
