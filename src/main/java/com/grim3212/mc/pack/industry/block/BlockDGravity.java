package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityDGravity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDGravity extends BlockManual implements ITileEntityProvider {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	private int type;

	protected BlockDGravity(int type) {
		super(Material.IRON, SoundType.METAL);
		this.type = type;
		setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST).withProperty(POWERED, false));
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		setDefaultDirection(worldIn, pos, state);
	}

	private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			EnumFacing enumfacing = state.getValue(FACING);
			boolean flag = worldIn.getBlockState(pos.north()).isFullBlock();
			boolean flag1 = worldIn.getBlockState(pos.south()).isFullBlock();

			if (enumfacing == EnumFacing.NORTH && flag && !flag1) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag) {
				enumfacing = EnumFacing.NORTH;
			} else {
				boolean flag2 = worldIn.getBlockState(pos.west()).isFullBlock();
				boolean flag3 = worldIn.getBlockState(pos.east()).isFullBlock();

				if (enumfacing == EnumFacing.WEST && flag2 && !flag3) {
					enumfacing = EnumFacing.EAST;
				} else if (enumfacing == EnumFacing.EAST && flag3 && !flag2) {
					enumfacing = EnumFacing.WEST;
				}
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(POWERED, false), 2);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		updateGravitor(worldIn, pos);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer)).withProperty(POWERED, false);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer)), 2);
		updateGravitor(worldIn, pos);
	}

	public static EnumFacing getFacing(int meta) {
		return EnumFacing.getFront(meta & 7);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(POWERED, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
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
		IBlockState state = worldIn.getBlockState(pos);
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
	public Page getPage(IBlockState state) {
		if (state.getBlock() == IndustryBlocks.direction_gravitor) {
			return ManualIndustry.gravitor_page;
		} else if (state.getBlock() == IndustryBlocks.direction_attractor) {
			return ManualIndustry.attract_page;
		}

		return ManualIndustry.repulse_page;
	}
}
