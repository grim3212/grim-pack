package com.grim3212.mc.pack.industry.init;

import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.config.IndustryConfig;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class IndustrySounds {

	@ObjectHolder(IndustryNames.SOUND_SPIKE_DEPLOY)
	public static SoundEvent spikeDeploySound;
	@ObjectHolder(IndustryNames.SOUND_SPIKE_CLOSE)
	public static SoundEvent spikeCloseSound;

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		IForgeRegistry<SoundEvent> reg = evt.getRegistry();

		if (IndustryConfig.subpartSpikes.get()) {
			reg.register(Utils.createSound(IndustryNames.SOUND_SPIKE_DEPLOY));
			reg.register(Utils.createSound(IndustryNames.SOUND_SPIKE_CLOSE));
		}
	}

}
