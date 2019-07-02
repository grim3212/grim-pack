package com.grim3212.mc.pack.core.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public abstract class BlockManual extends Block implements IManualBlock {

	public BlockManual(String name, Block.Properties properties) {
		super(properties);
		setDefaultState(getState());
		setRegistryName(name);
	}

	/*
	 * Return the default state for the block
	 */
	protected BlockState getState() {
		return stateContainer.getBaseState();
	}
}
