package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class BlockPot extends BlockManual {

	protected static final VoxelShape STOOL_POT_AABB = Block.makeCuboidShape(2.88f, 0F, 2.88f, 13.12f, 16F, 13.12f);
	public static final IntegerProperty TOP = IntegerProperty.create("top", 0, 6);
	public static final BooleanProperty DOWN = BooleanProperty.create("down");

	protected BlockPot() {
		super(DecorNames.POT, Block.Properties.create(Material.CLAY).sound(SoundType.GROUND).tickRandomly().hardnessAndResistance(0.5f, 10f));
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(TOP, 0).with(DOWN, false);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(TOP, DOWN);
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		BlockState plant = plantable.getPlant(world, pos.offset(facing));
		PlantType plantType = plantable.getPlantType(world, pos.offset(facing));

		if (plantType == PlantType.Cave) {
			return true;
		}

		if (world.getBlockState(pos).getBlock() == DecorBlocks.pot) {
			int top = world.getBlockState(pos).get(TOP);
			switch (top) {
			case 0:
				if ((plantType == PlantType.Plains && !this.isStool(world, pos)) || (plantType == PlantType.Plains && plant.getBlock() instanceof FlowerBlock)) {
					return true;
				}

				if (plantType == PlantType.Beach && !this.isStool(world, pos)) {
					boolean hasWater = (world.getBlockState(pos.east()).getMaterial() == Material.WATER || world.getBlockState(pos.west()).getMaterial() == Material.WATER || world.getBlockState(pos.north()).getMaterial() == Material.WATER || world.getBlockState(pos.south()).getMaterial() == Material.WATER);
					return hasWater;
				}
				break;
			case 1:
				if ((plantType == PlantType.Desert && !this.isStool(world, pos)) || (plantType == PlantType.Desert && plant.getBlock() == Blocks.DEAD_BUSH)) {
					return true;
				}
				if (plantType == PlantType.Beach && !this.isStool(world, pos)) {
					boolean hasWater = (world.getBlockState(pos.east()).getMaterial() == Material.WATER || world.getBlockState(pos.west()).getMaterial() == Material.WATER || world.getBlockState(pos.north()).getMaterial() == Material.WATER || world.getBlockState(pos.south()).getMaterial() == Material.WATER);
					return hasWater;
				}
				break;
			case 2:
				return false;
			case 3:
				return false;
			case 4:
				if (plantType == PlantType.Crop && !this.isStool(world, pos))
					return true;
				break;
			case 5:
				return false;
			case 6:
				if (plantType == PlantType.Nether && !this.isStool(world, pos))
					return true;
				break;
			}
		}
		return false;
	}

	@Override
	public int tickRate(IWorldReader worldIn) {
		return 10;
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		worldIn.notifyNeighborsOfStateChange(pos, this);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean flag) {
		worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext ctx) {
		if (isStool(worldIn, pos))
			return STOOL_POT_AABB;
		return VoxelShapes.fullCube();
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (worldIn.isRemote)
			return true;

		if (player.getHeldItem(hand).isEmpty() || player.getHeldItem(hand).getCount() == 0) {
			int top = worldIn.getBlockState(pos).get(TOP);
			if (top == 6) {
				top = 0;
			} else {
				top++;
			}
			worldIn.setBlockState(pos, state.with(TOP, top), 2);
			return true;
		} else {
			return false;
		}
	}

	private boolean isStool(IBlockReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getBlock() == DecorBlocks.stool;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return stateIn.with(DOWN, this.isStool(worldIn, currentPos));
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.firing_page;
	}
}