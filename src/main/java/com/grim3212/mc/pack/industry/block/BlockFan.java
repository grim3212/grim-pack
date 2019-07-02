package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFan.FanMode;

import net.minecraft.block.*;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFan extends BlockManual implements ITileEntityProvider {

	public static final PropertyDirection FACING = DirectionalBlock.FACING;
	public static final PropertyEnum<FanMode> MODE = PropertyEnum.create("mode", FanMode.class);

	public BlockFan() {
		super("fan", Material.ROCK, SoundType.STONE);
		setHardness(1.5F);
		setResistance(10F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockState getState() {
		return this.blockState.getBaseState().withProperty(FACING, Direction.NORTH);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, BlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.setDefaultDirection(worldIn, pos, state);
	}

	@Override
	public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, Hand hand) {
		return this.getDefaultState().withProperty(FACING, Direction.getDirectionFromEntityLiving(pos, placer));
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, Direction.getDirectionFromEntityLiving(pos, placer)), 2);
	}

	private void setDefaultDirection(World worldIn, BlockPos pos, BlockState state) {
		if (!worldIn.isRemote) {
			Direction enumfacing = (Direction) state.getValue(FACING);
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

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
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
	public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);

		if (te instanceof TileEntityFan)
			return state.withProperty(MODE, ((TileEntityFan) te).getMode());

		return state;
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, Direction.getFront(meta));
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public BlockState withRotation(BlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState withMirror(BlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, MODE });
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
		playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.FAN_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFan();
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.fan_page;
	}

}
