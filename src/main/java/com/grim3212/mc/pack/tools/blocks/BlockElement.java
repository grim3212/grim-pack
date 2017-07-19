package com.grim3212.mc.pack.tools.blocks;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockElement extends BlockManual {

	protected BlockElement() {
		super("element_115_ore", Material.ROCK, SoundType.STONE);
		setHardness(2.5F);
		setResistance(30.0F);
		setLightLevel(0.4F);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ToolsItems.element_115;
	}

	@Override
	public int quantityDropped(Random var1) {
		return 3 + var1.nextInt(2);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualTools.element115_page;
	}
}
