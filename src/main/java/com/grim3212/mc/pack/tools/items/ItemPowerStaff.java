package com.grim3212.mc.pack.tools.items;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.entity.EntityBlockPushPull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPowerStaff extends Item {

	public static ArrayList<Block> allowedBlocks = new ArrayList<Block>();

	protected ItemPowerStaff() {
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return stack.getItemDamage() == 0 ? super.getUnlocalizedName() + "_push" : super.getUnlocalizedName() + "_pull";
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		int xMov = 0;
		int zMov = 0;
		switch (facing) {
		case EAST:
			xMov = 1;
			break;
		case WEST:
			xMov = -1;
			break;
		case SOUTH:
			zMov = 1;
			break;
		case NORTH:
			zMov = -1;
			break;
		default:
			return EnumActionResult.FAIL;
		}

		if (stack.getItemDamage() == 0) {
			xMov *= -1;
			zMov *= -1;
		}

		IBlockState state = worldIn.getBlockState(pos);
		// Not-allowed blocks no matter what
		if (state.getBlock() == null || state.getMaterial().isLiquid() || state.getBlock() == Blocks.FIRE || state.getBlock() == Blocks.SNOW_LAYER || state.getBlock() instanceof BlockDoublePlant || state.getBlock() instanceof BlockContainer)
			return EnumActionResult.FAIL;

		if (ToolsConfig.restrictPowerStaffBlocks && allowedBlocks.contains(state.getBlock())) {
			onPower(state, worldIn, pos, xMov, zMov);
		} else if (!ToolsConfig.restrictPowerStaffBlocks) {
			// Default not-allowed blocks
			if (state.getBlock() != Blocks.BEDROCK || state.getBlock() != Blocks.OBSIDIAN)
				onPower(state, worldIn, pos, xMov, zMov);
		}

		return EnumActionResult.SUCCESS;
	}

	private void onPower(IBlockState state, World worldIn, BlockPos pos, int xMov, int zMov) {
		if (state.getBlock() instanceof BlockFalling) {
			worldIn.setBlockToAir(pos);
			worldIn.setBlockState(pos.east(xMov).south(zMov), state);
		} else {
			EntityBlockPushPull blockpushpull = new EntityBlockPushPull(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
			blockpushpull.motionX = 0.29999999999999999D * (double) xMov;
			blockpushpull.motionZ = 0.29999999999999999D * (double) zMov;
			worldIn.spawnEntityInWorld(blockpushpull);
		}
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		subItems.add(new ItemStack(itemIn, 1, 0));
		subItems.add(new ItemStack(itemIn, 1, 1));
	}
}
