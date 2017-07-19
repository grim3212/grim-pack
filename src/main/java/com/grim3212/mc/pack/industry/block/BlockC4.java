package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockC4 extends BlockManual {

	public BlockC4() {
		super("c4", Material.IRON, SoundType.METAL);
		setHardness(1.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn.isBlockPowered(pos)) {
			worldIn.setBlockToAir(pos);
			worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 7F, true);
		}
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isRemote) {
			worldIn.setBlockToAir(pos);
			worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 7F, true);
		}
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.c4_page;
	}
}
