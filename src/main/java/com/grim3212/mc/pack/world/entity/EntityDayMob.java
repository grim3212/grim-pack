package com.grim3212.mc.pack.world.entity;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public abstract class EntityDayMob extends EntityMob implements IManualEntity {

	public EntityDayMob(World worldIn) {
		super(worldIn);
	}

	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
}
