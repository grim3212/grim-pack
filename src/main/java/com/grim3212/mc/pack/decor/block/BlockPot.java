package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockPot extends BlockManual {

	protected static final AxisAlignedBB STOOL_POT_AABB = new AxisAlignedBB(0.18F, 0.0F, 0.18F, 0.82F, 1F, 0.82F);
	public static final PropertyInteger TOP = PropertyInteger.create("top", 0, 6);
	public static final PropertyBool DOWN = PropertyBool.create("down");

	protected BlockPot() {
		super(Material.CLAY, SoundType.GROUND);
		this.setTickRandomly(true);
		this.setDefaultState(blockState.getBaseState().withProperty(TOP, 0));
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		IBlockState plant = plantable.getPlant(world, pos.offset(direction));
		EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));

		if (plantType == EnumPlantType.Cave) {
			return true;
		}

		if (world.getBlockState(pos).getBlock() == DecorBlocks.pot) {
			int top = world.getBlockState(pos).getValue(TOP);
			switch (top) {
			case 0:
				if ((plantType == EnumPlantType.Plains && !this.isStool(world, pos)) || (plantType == EnumPlantType.Plains && plant.getBlock() instanceof BlockFlower)) {
					return true;
				}

				if (plantType == EnumPlantType.Beach && !this.isStool(world, pos)) {
					boolean hasWater = (world.getBlockState(pos.east()).getMaterial() == Material.WATER || world.getBlockState(pos.west()).getMaterial() == Material.WATER || world.getBlockState(pos.north()).getMaterial() == Material.WATER || world.getBlockState(pos.south()).getMaterial() == Material.WATER);
					return hasWater;
				}
				break;
			case 1:
				if ((plantType == EnumPlantType.Desert && !this.isStool(world, pos)) || (plantType == EnumPlantType.Desert && plant.getBlock() == Blocks.DEADBUSH)) {
					return true;
				}
				if (plantType == EnumPlantType.Beach && !this.isStool(world, pos)) {
					boolean hasWater = (world.getBlockState(pos.east()).getMaterial() == Material.WATER || world.getBlockState(pos.west()).getMaterial() == Material.WATER || world.getBlockState(pos.north()).getMaterial() == Material.WATER || world.getBlockState(pos.south()).getMaterial() == Material.WATER);
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
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (isStool(source, pos))
			return STOOL_POT_AABB;
		else
			return FULL_BLOCK_AABB;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;

		if (heldItem == null || heldItem.stackSize == 0) {
			int top = worldIn.getBlockState(pos).getValue(TOP);
			if (top == 6) {
				top = 0;
			} else {
				top++;
			}
			worldIn.setBlockState(pos, state.withProperty(TOP, top), 2);
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { DOWN, TOP });
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
		return worldIn.getBlockState(pos.down()).getBlock() == DecorBlocks.stool;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.firing_page;
	}
}