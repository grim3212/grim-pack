package com.grim3212.mc.pack.core.block;

import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockManualPage extends BlockManual {

	private String page;

	public BlockManualPage(Material material, SoundType type, String page) {
		super(material, type);
		this.page = page;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualRegistry.getPageFromString(this.page);
	}

}
