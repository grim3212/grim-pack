package com.grim3212.mc.pack.world.init;

import com.grim3212.mc.pack.core.util.Utils;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class WorldSounds {

	public static SoundEvent bobomb_living = Utils.createSound("bobomb_living");

	public static SoundEvent parabuzzy_dead = Utils.createSound("parabuzzy_dead");
	public static SoundEvent parabuzzy_living = Utils.createSound("parabuzzy_living");
	public static SoundEvent parabuzzy_hurt = Utils.createSound("parabuzzy_hurt");

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		IForgeRegistry<SoundEvent> reg = evt.getRegistry();
		reg.register(bobomb_living);
		reg.register(parabuzzy_dead);
		reg.register(parabuzzy_living);
		reg.register(parabuzzy_hurt);
	}

}