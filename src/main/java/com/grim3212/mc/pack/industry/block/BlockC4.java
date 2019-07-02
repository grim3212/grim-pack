package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class BlockC4 extends BlockManual {

	public BlockC4() {
		super(IndustryNames.C4, Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(1.0f));
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
		if (worldIn.isBlockPowered(pos)) {
			worldIn.destroyBlock(pos, false);
			worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 7F, true, Mode.DESTROY);
		}
	}

	@Override
	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isRemote) {
			worldIn.destroyBlock(pos, false);
			worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 7F, true, Mode.DESTROY);
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.c4_page;
	}
}
