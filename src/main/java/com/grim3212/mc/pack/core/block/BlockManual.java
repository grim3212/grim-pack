package com.grim3212.mc.pack.core.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public abstract class BlockManual extends Block implements IManualBlock {

	public BlockManual(String name, Material material, SoundType type) {
		super(material);
		setSoundType(type);
		setUnlocalizedName(name);
		setDefaultState(getState());
		setRegistryName(new ResourceLocation(GrimPack.modID, name));
	}

	/*
	 * Return the default state for the block
	 */
	protected IBlockState getState() {
		return blockState.getBaseState();
	}

	@Override
	public abstract Page getPage(IBlockState state);
}
