package com.grim3212.mc.pack.world.blocks;

import java.util.Iterator;
import java.util.Random;

import com.grim3212.mc.pack.world.items.WorldItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockGunpowderReed extends Block implements IPlantable {

	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

	protected BlockGunpowderReed() {
		super(Material.plants);
		float f = 0.375F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer) state.getValue(AGE);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { AGE });
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (worldIn.getBlockState(pos.down()).getBlock() == WorldBlocks.gunpowder_reed_block || this.checkForDrop(worldIn, pos, state)) {
			if (worldIn.isAirBlock(pos.up())) {
				int i;

				for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) {
					;
				}

				if (i < 3) {
					int j = ((Integer) state.getValue(AGE)).intValue();

					if (j == 15) {
						worldIn.setBlockState(pos.up(), this.getDefaultState());
						worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(0)), 4);
					} else {
						worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(j + 1)), 4);
					}
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		if (block.canSustainPlant(worldIn, pos, EnumFacing.UP, this))
			return true;

		if (block == this) {
			return true;
		} else if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand) {
			return false;
		} else {
			Iterator<EnumFacing> iterator = EnumFacing.Plane.HORIZONTAL.iterator();
			EnumFacing enumfacing;

			do {
				if (!iterator.hasNext()) {
					return false;
				}

				enumfacing = (EnumFacing) iterator.next();
			} while (worldIn.getBlockState(pos.offset(enumfacing).down()).getBlock().getMaterial() != Material.water);

			return true;
		}
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		this.checkForDrop(worldIn, pos, state);
	}

	protected final boolean checkForDrop(World worldIn, BlockPos p_176353_2_, IBlockState state) {
		if (this.canPlaceBlockAt(worldIn, p_176353_2_)) {
			return true;
		} else {
			this.dropBlockAsItem(worldIn, p_176353_2_, state, 0);
			worldIn.setBlockToAir(p_176353_2_);
			return false;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return WorldItems.gunpowder_reed_item;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public Item getItem(World worldIn, BlockPos pos) {
		return WorldItems.gunpowder_reed_item;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Beach;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		return this.getDefaultState();
	}
}