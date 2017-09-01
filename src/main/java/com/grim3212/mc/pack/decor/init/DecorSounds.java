package com.grim3212.mc.pack.decor.init;

import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class DecorSounds {

	public static final SoundEvent carlock = Utils.createSound("carlock");
	public static final SoundEvent alarm_a = Utils.createSound("alarm_a");
	public static final SoundEvent alarm_b = Utils.createSound("alarm_b");
	public static final SoundEvent alarm_c = Utils.createSound("alarm_c");
	public static final SoundEvent alarm_d = Utils.createSound("alarm_d");
	public static final SoundEvent alarm_e = Utils.createSound("alarm_e");
	public static final SoundEvent alarm_f = Utils.createSound("alarm_f");
	public static final SoundEvent alarm_g = Utils.createSound("alarm_g");
	public static final SoundEvent alarm_h = Utils.createSound("alarm_h");
	public static final SoundEvent alarm_i = Utils.createSound("alarm_i");
	public static final SoundEvent alarm_j = Utils.createSound("alarm_j");
	public static final SoundEvent alarm_k = Utils.createSound("alarm_k");
	public static final SoundEvent alarm_l = Utils.createSound("alarm_l");
	public static final SoundEvent alarm_m = Utils.createSound("alarm_m");

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		IForgeRegistry<SoundEvent> reg = evt.getRegistry();

		if (DecorConfig.subpartAlarm) {
			reg.register(carlock);
			reg.register(alarm_a);
			reg.register(alarm_b);
			reg.register(alarm_c);
			reg.register(alarm_d);
			reg.register(alarm_e);
			reg.register(alarm_f);
			reg.register(alarm_g);
			reg.register(alarm_h);
			reg.register(alarm_i);
			reg.register(alarm_j);
			reg.register(alarm_k);
			reg.register(alarm_l);
			reg.register(alarm_m);
		}
	}

}
