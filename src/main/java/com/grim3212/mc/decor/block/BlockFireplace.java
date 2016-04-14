package com.grim3212.mc.decor.block;

import java.util.Random;

import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.config.DecorConfig;
import com.grim3212.mc.decor.tile.TileEntityFireplace;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockFireplace extends BlockFireplaceBase {

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	public BlockFireplace() {
		this.setDefaultState(blockState.getBaseState().withProperty(EAST, false).withProperty(WEST, false).withProperty(SOUTH, false).withProperty(NORTH, false));
	}

	@Override
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		TileEntityFireplace tef = (TileEntityFireplace) worldIn.getTileEntity(pos);
		if (DecorConfig.isFireParticles) {
			if (tef.isActive()) {
				for (int i = 0; i < 5; i++) {
					double xVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;
					double yVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;
					double zVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;

					double height = rand.nextDouble();
					if (height > 0.7D || height < 0.2D)
						height = 0.3D;
					worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D + xVar, pos.getY() + height + yVar, pos.getZ() + 0.5D + zVar, 0.0D, 0.0D, 0.0D);
				}
			}
		}

		if (tef.isActive() && worldIn.getBlockState(pos.up()).getBlock() == DecorBlocks.chimney) {
			int smokeheight = 1;
			while (worldIn.getBlockState(pos.up(smokeheight)).getBlock() == DecorBlocks.chimney) {
				smokeheight++;
			}

			GrimDecor.proxy.produceSmoke(worldIn, pos.up(smokeheight), 0.5D, 0.0D, 0.5D, 1, true);
		}
	}

	@Override
	protected BlockState createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { NORTH, SOUTH, WEST, EAST }, new IUnlistedProperty[] { BLOCKID, BLOCKMETA, ACTIVE });
	}

	@Override
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(NORTH, this.canConnectTo(worldIn, pos.north())).withProperty(EAST, this.canConnectTo(worldIn, pos.east())).withProperty(SOUTH, this.canConnectTo(worldIn, pos.south())).withProperty(WEST, this.canConnectTo(worldIn, pos.west()));
	}

	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		IBlockState blockState = worldIn.getBlockState(pos).getBlock().getDefaultState();
		return blockState == this.getDefaultState();
	}
}