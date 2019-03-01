package com.grim3212.mc.pack.core.block;

import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockManualPage extends BlockManual {

	private String page;

	public BlockManualPage(String name, Block.Properties props, String page) {
		super(name, props);
		this.page = page;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualRegistry.getPageFromString(this.page);
	}

}
