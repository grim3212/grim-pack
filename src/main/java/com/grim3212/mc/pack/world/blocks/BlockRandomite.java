package com.grim3212.mc.pack.world.blocks;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.world.client.ManualWorld;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockRandomite extends BlockManual {

	protected BlockRandomite() {
		super("randomite", Material.ROCK, SoundType.STONE);
		this.setHardness(1.6F);
		this.setResistance(1.0F);
		this.setCreativeTab(GrimCreativeTabs.GRIM_WORLD);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		int i = rand.nextInt(63);
		return (Item) (i > 0 && i < 18 ? Items.COAL : (i > 18 && i < 31 ? Item.getItemFromBlock(Blocks.IRON_ORE) : (i > 31 && i < 35 ? Item.getItemFromBlock(Blocks.GOLD_ORE) : (i > 35 && i < 38 ? Items.DIAMOND : (i > 38 && i < 48 ? Items.REDSTONE : (i > 48 && i < 51 ? Items.EMERALD : (i > 51 && i < 58 ? Items.QUARTZ : Items.EGG)))))));
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualWorld.randomite_page;
	}
}
