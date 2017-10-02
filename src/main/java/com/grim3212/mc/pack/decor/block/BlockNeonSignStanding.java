package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.GrimPack;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNeonSignStanding extends BlockNeonSign {

	public BlockNeonSignStanding() {
		setSoundType(SoundType.WOOD);
		setHardness(1.0F);
		disableStats();
		setUnlocalizedName("neon_sign_standing");
		setDefaultState(this.blockState.getBaseState().withProperty(BlockStandingSign.ROTATION, 0));
		setRegistryName(new ResourceLocation(GrimPack.modID, "neon_sign_standing"));

	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.getBlockState(pos.down()).getMaterial().isSolid()) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}

		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BlockStandingSign.ROTATION, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockStandingSign.ROTATION);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(BlockStandingSign.ROTATION, rot.rotate(state.getValue(BlockStandingSign.ROTATION), 16));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withProperty(BlockStandingSign.ROTATION, mirrorIn.mirrorRotation(state.getValue(BlockStandingSign.ROTATION), 16));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { BlockStandingSign.ROTATION });
	}
}
