package com.grim3212.mc.pack.decor.tile;

import com.grim3212.mc.pack.core.tile.TileEntityGrim;
import com.grim3212.mc.pack.decor.init.DecorSounds;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;

public class TileEntityAlarm extends TileEntityGrim {

	public int alarmType = 13;

	public TileEntityAlarm() {
		super(DecorTileEntities.ALARM);
	}

	public TileEntityAlarm(int alarmType) {
		super(DecorTileEntities.ALARM);

		this.alarmType = alarmType;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		alarmType = compound.getInt("AlarmType");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);

		compound.putInt("AlarmType", alarmType);

		return compound;
	}

	public SoundEvent getSound() {
		return getSound(alarmType);
	}

	public static SoundEvent getSound(int type) {
		switch (type) {
		case 0:
			return DecorSounds.alarm_a;
		case 1:
			return DecorSounds.alarm_b;
		case 2:
			return DecorSounds.alarm_c;
		case 3:
			return DecorSounds.alarm_d;
		case 4:
			return DecorSounds.alarm_e;
		case 5:
			return DecorSounds.alarm_f;
		case 6:
			return DecorSounds.alarm_g;
		case 7:
			return DecorSounds.alarm_h;
		case 8:
			return DecorSounds.alarm_i;
		case 9:
			return DecorSounds.alarm_j;
		case 10:
			return DecorSounds.alarm_k;
		case 11:
			return DecorSounds.alarm_l;
		case 12:
			return DecorSounds.alarm_m;
		default:
			return DecorSounds.carlock;
		}
	}
}
