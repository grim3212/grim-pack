package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.client.gui.GuiAlarm;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.tile.TileEntityAlarm;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockAlarm extends BlockManual {

	public static final BooleanProperty POWERED = BooleanProperty.create("powered");
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());

	private static final VoxelShape UP_BOUNDS = Block.makeCuboidShape(3f, 0f, 3f, 13f, 4f, 13f);
	private static final VoxelShape DOWN_BOUNDS = Block.makeCuboidShape(3F, 12f, 3F, 13f, 16f, 13f);
	private static final VoxelShape NORTH_BOUNDS = Block.makeCuboidShape(3F, 3F, 12f, 13f, 13f, 16f);
	private static final VoxelShape SOUTH_BOUNDS = Block.makeCuboidShape(3F, 3F, 0f, 13f, 13f, 4f);
	private static final VoxelShape WEST_BOUNDS = Block.makeCuboidShape(12f, 3f, 3f, 16f, 13f, 13f);
	private static final VoxelShape EAST_BOUNDS = Block.makeCuboidShape(0f, 3f, 3f, 4f, 13f, 13f);

	public BlockAlarm() {
		super(DecorNames.ALARM, Block.Properties.create(Material.IRON).sound(SoundType.METAL).lightValue(3).hardnessAndResistance(2f, 1f));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityAlarm();
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(POWERED, false).with(FACING, Direction.NORTH);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(FACING)) {
		case DOWN:
			return DOWN_BOUNDS;
		case EAST:
			return EAST_BOUNDS;
		case NORTH:
			return NORTH_BOUNDS;
		case SOUTH:
			return SOUTH_BOUNDS;
		case UP:
			return UP_BOUNDS;
		case WEST:
			return WEST_BOUNDS;
		}

		return VoxelShapes.fullCube();
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.alarm_page;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.west()).isOpaqueCube(worldIn, pos) ? true : (worldIn.getBlockState(pos.east()).isOpaqueCube(worldIn, pos) ? true : (worldIn.getBlockState(pos.north()).isOpaqueCube(worldIn, pos) ? true : (worldIn.getBlockState(pos.south()).isOpaqueCube(worldIn, pos) ? true : (worldIn.getBlockState(pos.up()).isOpaqueCube(worldIn, pos) ? true : worldIn.getBlockState(pos.down()).isOpaqueCube(worldIn, pos)))));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		Direction facing = context.getFace();
		BlockState state = world.getBlockState(pos.offset(facing.getOpposite()));

		return Block.hasSolidSide(state, world, pos.offset(facing.getOpposite()), facing) ? this.getDefaultState().with(FACING, facing) : this.getDefaultState().with(FACING, Direction.DOWN);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		this.tryDropAlarm(worldIn, pos);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean flag) {
		// Drop block if it has no base
		if (tryDropAlarm(worldIn, pos)) {
			return;
		}

		TileEntity te = worldIn.getTileEntity(pos);

		if (te instanceof TileEntityAlarm) {
			if (worldIn.isBlockPowered(pos) && !state.get(POWERED)) {
				worldIn.setBlockState(pos, state.with(POWERED, true));

				if (((TileEntityAlarm) te).alarmType == 26) {
					// Explode
					worldIn.destroyBlock(pos, false);
					worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 10F, Explosion.Mode.BREAK);
				} else {

					// Play the current sound set when activated
					worldIn.playSound((PlayerEntity) null, pos, ((TileEntityAlarm) te).getSound(), SoundCategory.BLOCKS, 1f, 1f);
				}
			} else if (!worldIn.isBlockPowered(pos) && state.get(POWERED)) {
				worldIn.setBlockState(pos, state.with(POWERED, false));
			}
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TileEntityAlarm) {
			if (worldIn.isRemote) {
				Minecraft.getInstance().displayGuiScreen(new GuiAlarm((TileEntityAlarm) tile, player));
			}
			
			// Play the current sound set
			worldIn.playSound((PlayerEntity) null, pos, ((TileEntityAlarm) tile).getSound(), SoundCategory.BLOCKS, 1f, 1f);
		}

		return true;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING).add(POWERED);
	}

	private boolean tryDropAlarm(World worldIn, BlockPos pos) {
		if (!isValidPosition(worldIn.getBlockState(pos), worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
			return true;
		}

		return false;
	}
}
