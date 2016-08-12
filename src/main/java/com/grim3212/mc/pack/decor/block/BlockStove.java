package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.state.IBlockState;

public class BlockStove extends BlockFireplaceBase implements IManualBlock {

	public BlockStove() {
	}
	
	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.stove_page;
	}
}
