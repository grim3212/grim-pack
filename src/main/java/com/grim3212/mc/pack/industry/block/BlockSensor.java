package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntitySensor;

import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSensor extends BlockManual implements ITileEntityProvider {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyEnum<Direction> FACING = PropertyEnum.create("facing", Direction.class);
	private int triggerType;

	public BlockSensor(String name, int triggerType) {
		super(name, Material.ROCK, null);
		this.triggerType = triggerType;

		if (triggerType == 0)
			this.setSoundType(SoundType.WOOD);
		else if (triggerType == 1)
			this.setSoundType(SoundType.STONE);
		else if (triggerType == 2)
			this.setSoundType(SoundType.METAL);
		else if (triggerType == 3)
			this.setSoundType(SoundType.STONE);

		setHardness(0.5F);
		setResistance(10F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockState getState() {
		return this.blockState.getBaseState().withProperty(ACTIVE, false).withProperty(FACING, Direction.NORTH);
	}

	public static Direction getFacing(int meta) {
		return Direction.getFront(meta & 7);
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(ACTIVE, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(FACING).getIndex();

		if (state.getValue(ACTIVE)) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, ACTIVE });
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, Direction.getDirectionFromEntityLiving(pos, placer)), 2);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, BlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.setDefaultDirection(worldIn, pos, state);
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

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(ACTIVE, false), 2);
		}
	}

	@Override
	public int getWeakPower(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		if (blockState.getValue(ACTIVE))
			return 15;

		return 0;
	}

	@Override
	public int getStrongPower(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		return 0;
	}

	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}

	@Override
	public PushReaction getMobilityFlag(BlockState state) {
		return PushReaction.BLOCK;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySensor(triggerType);
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
	public Page getPage(BlockState state) {
		return ManualIndustry.sensor_page;
	}
}