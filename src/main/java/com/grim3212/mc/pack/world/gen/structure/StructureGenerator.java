package com.grim3212.mc.pack.world.gen.structure;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class StructureGenerator {

	/**
	 * The name of the Structure for this Generator
	 */
	protected final String structName;

	public StructureGenerator(String structName) {
		this.structName = structName;
	}

	public abstract boolean generate(World worldIn, Random random, BlockPos pos);
}
