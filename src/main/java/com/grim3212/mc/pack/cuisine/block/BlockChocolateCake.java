package com.grim3212.mc.pack.cuisine.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockChocolateCake extends BlockCake implements IManualBlock {

	protected BlockChocolateCake() {
		super();
		setSoundType(SoundType.CLOTH);
		setHardness(0.5F);
		setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
		setUnlocalizedName("chocolate_cake");
		setRegistryName(new ResourceLocation(GrimPack.modID, "chocolate_cake"));
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(CuisineBlocks.chocolate_cake);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualCuisine.chocolateCake_page;
	}
}
