package com.grim3212.mc.pack.tools.init;

import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.config.ToolsConfig;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ToolsSounds {

	public static final SoundEvent raygunSound = Utils.createSound("raysh");

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		IForgeRegistry<SoundEvent> reg = evt.getRegistry();

		if (ToolsConfig.subpartRayGuns)
			reg.register(raygunSound);
	}

}
