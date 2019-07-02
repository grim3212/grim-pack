package com.grim3212.mc.pack.cuisine.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlockCBarMould extends BlockManual {

	protected static final VoxelShape MOULD_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
	public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 15);

	protected BlockCBarMould() {
		super(CuisineNames.CHOCOLATE_BAR_MOULD, Block.Properties.create(Material.WOOD).sound(SoundType.STONE).tickRandomly().hardnessAndResistance(1.0f));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(STAGE);
	}

	@Override
	protected BlockState getState() {
		return this.stateContainer.getBaseState().with(STAGE, 0);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return super.isValidPosition(state, worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean flag) {
		if (!this.canBlockStay(worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
		}
	}

	private boolean canBlockStay(IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		int current_stage = 2;
		if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.ICE || worldIn.getBlockState(pos.down()).getBlock() == Blocks.SNOW || worldIn.getBlockState(pos.down()).getBlock() == Blocks.PACKED_ICE) {
			current_stage = 4;
		}

		int meta = worldIn.getBlockState(pos).get(STAGE);
		if (meta >= 12) {
			current_stage = (current_stage + (15 - meta)) - current_stage;
		}
		if (meta == 0) {
			worldIn.setBlockState(pos, state.with(STAGE, 0), 2);
		} else if (meta == 15) {
			worldIn.setBlockState(pos, state.with(STAGE, 15), 2);
		} else {
			worldIn.setBlockState(pos, state.with(STAGE, meta + current_stage), 2);
		}
	}

	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn) {
		if (state.get(STAGE) == 15) {
			if (!worldIn.isRemote) {
				worldIn.setBlockState(pos, CuisineBlocks.chocolate_bar_mould.getDefaultState(), 3);
				float f = 0.7F;
				double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
				double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				ItemEntity entityitem = new ItemEntity(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(CuisineItems.chocolate_bar, 2));
				entityitem.setPickupDelay(10);
				worldIn.addEntity(entityitem);
			}
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
		ItemStack heldItem = player.getHeldItem(hand);

		if (!heldItem.isEmpty() && heldItem.getItem() == CuisineItems.chocolate_bowl_hot) {
			if (worldIn.getBlockState(pos).get(STAGE) == 0) {
				worldIn.setBlockState(pos, state.with(STAGE, 1), 2);

				heldItem.shrink(1);
				if (heldItem.getCount() <= 0) {
					player.inventory.addItemStackToInventory(new ItemStack(Items.BOWL));
				}
			}
		}

		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return MOULD_SHAPE;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualCuisine.chocolateMould_page;
	}
}
