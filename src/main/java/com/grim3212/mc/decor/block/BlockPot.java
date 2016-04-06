package com.grim3212.mc.decor.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockPot extends Block {

	public static final PropertyInteger TOP = PropertyInteger.create("top", 0, 6);
	public static final PropertyBool DOWN = PropertyBool.create("down");

	protected BlockPot() {
		super(Material.clay);
		this.setTickRandomly(true);
		this.setDefaultState(blockState.getBaseState().withProperty(TOP, 0));
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		IBlockState plant = plantable.getPlant(world, pos.offset(direction));
		EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));

		if (plantType == EnumPlantType.Cave) {
			return true;
		}

		// This is to make Reeds work...
		BlockPos newPos = pos.down();
		if (world.getBlockState(newPos).getBlock() == DecorBlocks.pot) {
			int top = world.getBlockState(newPos).getValue(TOP);
			if ((top == 0 || top == 1) && plantType == EnumPlantType.Beach && plant == Blocks.reeds.getDefaultState() && !this.isStool(world, newPos)) {
				boolean hasWater = (world.getBlockState(newPos.east()).getBlock().getMaterial() == Material.water || world.getBlockState(newPos.west()).getBlock().getMaterial() == Material.water || world.getBlockState(newPos.north()).getBlock().getMaterial() == Material.water || world.getBlockState(newPos.south()).getBlock().getMaterial() == Material.water);
				return hasWater;
			}
		}
		
		//TODO: FIX REEDS

		if (world.getBlockState(pos).getBlock() == DecorBlocks.pot) {
			int top = world.getBlockState(pos).getValue(TOP);
			switch (top) {
			case 0:
				if ((plantType == EnumPlantType.Plains && !this.isStool(world, pos)) || (plantType == EnumPlantType.Plains && plant.getBlock() instanceof BlockFlower)) {
					return true;
				}

				if (plantType == EnumPlantType.Beach && plant != Blocks.reeds.getDefaultState() && !this.isStool(world, pos)) {
					boolean hasWater = (world.getBlockState(newPos.east()).getBlock().getMaterial() == Material.water || world.getBlockState(newPos.west()).getBlock().getMaterial() == Material.water || world.getBlockState(newPos.north()).getBlock().getMaterial() == Material.water || world.getBlockState(newPos.south()).getBlock().getMaterial() == Material.water);
					return hasWater;
				}
				break;
			case 1:
				if ((plantType == EnumPlantType.Desert && !this.isStool(world, pos)) || (plantType == EnumPlantType.Desert && plant.getBlock() == Blocks.deadbush)) {
					return true;
				}
				if (plantType == EnumPlantType.Beach && plant != Blocks.reeds.getDefaultState() && !this.isStool(world, pos)) {
					boolean hasWater = (world.getBlockState(newPos.east()).getBlock().getMaterial() == Material.water || world.getBlockState(newPos.west()).getBlock().getMaterial() == Material.water || world.getBlockState(newPos.north()).getBlock().getMaterial() == Material.water || world.getBlockState(newPos.south()).getBlock().getMaterial() == Material.water);
					return hasWater;
				}
				break;
			case 2:
				return false;
			case 3:
				return false;
			case 4:
				if (plantType == EnumPlantType.Crop && !this.isStool(world, pos))
					return true;
				break;
			case 5:
				return false;
			case 6:
				if (plantType == EnumPlantType.Nether && !this.isStool(world, pos))
					return true;
				break;
			}
		}
		return false;
	}

	@Override
	public int tickRate(World worldIn) {
		return 10;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		worldIn.notifyNeighborsOfStateChange(pos, this);
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		if (isStool(worldIn, pos))
			this.setBlockBounds(0.18F, 0.0F, 0.18F, 0.82F, 1F, 0.82F);
		else
			this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);

	}

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
		if (isStool(worldIn, pos)) {
			this.setBlockBounds(0.18F, 0.0F, 0.18F, 0.82F, 1F, 0.82F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		} else {
			this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = playerIn.getCurrentEquippedItem();
		if (itemstack == null || itemstack.stackSize == 0) {
			int top = worldIn.getBlockState(pos).getValue(TOP);
			if (top == 6) {
				top = 0;
			} else {
				top++;
			}
			worldIn.setBlockState(pos, state.withProperty(TOP, top), 2);
			worldIn.markBlockForUpdate(pos);
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
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
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { DOWN, TOP });
	}

	@Override
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(DOWN, false);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TOP);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TOP, meta);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(DOWN, this.isStool(worldIn, pos));
	}

	private boolean isStool(IBlockAccess worldIn, BlockPos pos) {
		// Block block = worldIn.getBlockState(pos.down()).getBlock();
		// return block == FancyPack.stool;
		return false;
	}
}