package com.grim3212.mc.pack.industry.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockNuclearBomb extends Block {

	public BlockNuclearBomb() {
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		if (worldIn.isBlockPowered(pos)) {
			worldIn.setBlockToAir(pos);
			worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 50, true);
		}
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isRemote) {
			worldIn.setBlockToAir(pos);
			worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 50, true);
		}
	}
}
