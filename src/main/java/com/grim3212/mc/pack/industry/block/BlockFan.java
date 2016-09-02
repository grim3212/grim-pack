package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFan.FanMode;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFan extends BlockManual implements ITileEntityProvider {

	public static final PropertyDirection FACING = BlockDirectional.FACING;
	public static final PropertyEnum<FanMode> MODE = PropertyEnum.create("mode", FanMode.class);

	public BlockFan() {
		super(Material.ROCK, SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.setDefaultDirection(worldIn, pos, state);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer));
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer)), 2);
	}

	private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
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

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		boolean powered = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
		TileEntity te = worldIn.getTileEntity(pos);

		if (te != null && te instanceof TileEntityFan) {
			TileEntityFan fan = (TileEntityFan) te;

			if (powered) {
				if (fan.getMode() != FanMode.OFF) {
					fan.setOldMode(fan.getMode());
					fan.setMode(FanMode.OFF);
				}
			} else {
				fan.setMode(fan.getOldMode());
			}

			worldIn.markBlockRangeForRenderUpdate(pos, pos);
			worldIn.notifyBlockUpdate(pos, state, state.withProperty(MODE, fan.getMode()), 2);
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);

		if (te instanceof TileEntityFan)
			return state.withProperty(MODE, ((TileEntityFan) te).getMode());

		return state;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, MODE });
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.FAN_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFan();
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.fan_page;
	}

}
