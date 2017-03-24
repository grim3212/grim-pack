package com.grim3212.mc.pack.industry;

import com.google.common.collect.ImmutableMap;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;

public class IndustryCommonProxy extends CommonProxy {

	public void airParticles(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, TileEntityFan fan) {

	}

	@Override
	public void preInit() {
	}

	/**
	 * loads the animation state machine definition file. Only needs to be
	 * loaded client side. Server side we just return null.
	 * 
	 * @param location
	 *            The {@link ResourceLocation} of the definition file. Usually
	 *            located in "modid:asms/block/*.json"
	 * @param parameters
	 *            A mapping between animated variables in java and their names
	 *            in the definition file.
	 * @return The loaded state machine.
	 */
	public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
		return null;
	}
}
