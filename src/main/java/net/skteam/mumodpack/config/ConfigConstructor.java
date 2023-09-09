package net.skteam.mumodpack.config;

public class ConfigConstructor extends MidnightConfig {
    @Entry public static double champb_health = 500D;
    @Entry(min=0) public static int champb_attack_cooldown_ticks = 20;
    @Entry(min=0) public static int champb_special_cooldown_ticks = 60;
    @Entry(min=0) public static float champb_damage_modifier = 1f;



}
