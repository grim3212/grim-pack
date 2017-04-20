package com.grim3212.mc.pack.world.util;

import com.grim3212.mc.pack.core.util.Utils;

import net.minecraft.util.SoundEvent;

public class WorldSounds {

	public static SoundEvent bobomb_living;

	public static SoundEvent parabuzzy_dead;
	public static SoundEvent parabuzzy_living;
	public static SoundEvent parabuzzy_hurt;

	public static void init() {
		bobomb_living = Utils.registerSound("bobomb_living");
		parabuzzy_dead = Utils.registerSound("parabuzzy_dead");
		parabuzzy_living = Utils.registerSound("parabuzzy_living");
		parabuzzy_hurt = Utils.registerSound("parabuzzy_hurt");
	}
}
