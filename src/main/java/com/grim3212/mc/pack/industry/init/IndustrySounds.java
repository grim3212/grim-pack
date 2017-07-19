package com.grim3212.mc.pack.industry.init;

import com.grim3212.mc.pack.core.util.Utils;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class IndustrySounds {

	public static final SoundEvent spikeDeploySound = Utils.createSound("spikeDeploy");
	public static final SoundEvent spikeCloseSound = Utils.createSound("spikeClose");

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		IForgeRegistry<SoundEvent> reg = evt.getRegistry();
		reg.register(spikeDeploySound);
		reg.register(spikeCloseSound);
	}

}
