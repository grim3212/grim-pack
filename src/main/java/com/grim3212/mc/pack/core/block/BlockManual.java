package com.grim3212.mc.pack.core.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public abstract class BlockManual extends Block implements IManualBlock {

	public BlockManual(String name, Block.Properties properties) {
		super(properties);
		setDefaultState(getState());
		setRegistryName(new ResourceLocation(GrimPack.modID, name));
	}

	/*
	 * Return the default state for the block
	 */
	protected IBlockState getState() {
		return stateContainer.getBaseState();
	}
}
