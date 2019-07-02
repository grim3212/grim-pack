package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityDGravity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDGravity extends BlockManual implements ITileEntityProvider {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	private int type;

	protected BlockDGravity(String name, int type) {
		super(name, Material.IRON, SoundType.METAL);
		this.type = type;
		setHardness(0.3F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockState getState() {
		return this.blockState.getBaseState().withProperty(FACING, Direction.EAST).withProperty(POWERED, false);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, BlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		setDefaultDirection(worldIn, pos, state);
	}

	private void setDefaultDirection(World worldIn, BlockPos pos, BlockState state) {
		if (!worldIn.isRemote) {
			Direction enumfacing = state.getValue(FACING);
			boolean flag = worldIn.getBlockState(pos.north()).isFullBlock();
			boolean flag1 = worldIn.getBlockState(pos.south()).isFullBlock();

			if (enumfacing == Direction.NORTH && flag && !flag1) {
				enumfacing = Direction.SOUTH;
			} else if (enumfacing == Direction.SOUTH && flag1 && !flag) {
				enumfacing = Direction.NORTH;
			} else {
				boolean flag2 = worldIn.getBlockState(pos.west()).isFullBlock();
				boolean flag3 = worldIn.getBlockState(pos.east()).isFullBlock();

				if (enumfacing == Direction.WEST && flag2 && !flag3) {
					enumfacing = Direction.EAST;
				} else if (enumfacing == Direction.EAST && flag3 && !flag2) {
					enumfacing = Direction.WEST;
				}
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(POWERED, false), 2);
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		updateGravitor(worldIn, pos);
	}

	@Override
	public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, Hand hand) {
		return this.getDefaultState().withProperty(FACING, Direction.getDirectionFromEntityLiving(pos, placer)).withProperty(POWERED, false);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, Direction.getDirectionFromEntityLiving(pos, placer)), 2);
		updateGravitor(worldIn, pos);
	}

	public static Direction getFacing(int meta) {
		return Direction.getFront(meta & 7);
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(POWERED, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(FACING).getIndex();

		if (state.getValue(POWERED)) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED });
	}

	public void updateGravitor(World worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos);
		TileEntity te = worldIn.getTileEntity(pos);

		int powerLevel = worldIn.isBlockIndirectlyGettingPowered(pos);

		if (powerLevel > 0 && te instanceof TileEntityDGravity) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, true), 2);
		} else {
			worldIn.setBlockState(pos, state.withProperty(POWERED, false), 2);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDGravity(type, getFacing(meta).getIndex());
	}

	@Override
	public Page getPage(BlockState state) {
		if (state.getBlock() == IndustryBlocks.direction_gravitor) {
			return ManualIndustry.gravitor_page;
		} else if (state.getBlock() == IndustryBlocks.direction_attractor) {
			return ManualIndustry.attract_page;
		}

		return ManualIndustry.repulse_page;
	}
}
