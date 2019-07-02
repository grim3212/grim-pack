package com.grim3212.mc.pack.decor.init;

import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class DecorSounds {

	@ObjectHolder(DecorNames.SOUND_CARLOCK)
	public static SoundEvent carlock;
	@ObjectHolder(DecorNames.SOUND_ALARM_A)
	public static SoundEvent alarm_a;
	@ObjectHolder(DecorNames.SOUND_ALARM_B)
	public static SoundEvent alarm_b;
	@ObjectHolder(DecorNames.SOUND_ALARM_C)
	public static SoundEvent alarm_c;
	@ObjectHolder(DecorNames.SOUND_ALARM_D)
	public static SoundEvent alarm_d;
	@ObjectHolder(DecorNames.SOUND_ALARM_E)
	public static SoundEvent alarm_e;
	@ObjectHolder(DecorNames.SOUND_ALARM_F)
	public static SoundEvent alarm_f;
	@ObjectHolder(DecorNames.SOUND_ALARM_G)
	public static SoundEvent alarm_g;
	@ObjectHolder(DecorNames.SOUND_ALARM_H)
	public static SoundEvent alarm_h;
	@ObjectHolder(DecorNames.SOUND_ALARM_I)
	public static SoundEvent alarm_i;
	@ObjectHolder(DecorNames.SOUND_ALARM_J)
	public static SoundEvent alarm_j;
	@ObjectHolder(DecorNames.SOUND_ALARM_K)
	public static SoundEvent alarm_k;
	@ObjectHolder(DecorNames.SOUND_ALARM_L)
	public static SoundEvent alarm_l;
	@ObjectHolder(DecorNames.SOUND_ALARM_M)
	public static SoundEvent alarm_m;

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		IForgeRegistry<SoundEvent> reg = evt.getRegistry();

		if (DecorConfig.subpartAlarm.get()) {
			reg.register(Utils.createSound(DecorNames.SOUND_CARLOCK));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_A));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_B));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_C));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_D));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_E));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_F));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_G));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_H));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_I));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_J));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_K));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_L));
			reg.register(Utils.createSound(DecorNames.SOUND_ALARM_M));
		}
	}

}
