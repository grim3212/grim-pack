package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.state.IBlockState;

public class BlockSlopedPost extends BlockHedge {

	public BlockSlopedPost() {
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.slopedPost_page;
	}
}
