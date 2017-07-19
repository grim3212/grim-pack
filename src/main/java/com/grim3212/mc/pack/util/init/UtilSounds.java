package com.grim3212.mc.pack.util.init;

import com.grim3212.mc.pack.core.util.Utils;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class UtilSounds {

	public static final SoundEvent fusrodahSound = Utils.createSound("fusrodah");
	public static final SoundEvent fusrodahOldSound = Utils.createSound("fusrodah-old");

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		IForgeRegistry<SoundEvent> reg = evt.getRegistry();
		reg.register(fusrodahSound);
		reg.register(fusrodahOldSound);
	}

}
