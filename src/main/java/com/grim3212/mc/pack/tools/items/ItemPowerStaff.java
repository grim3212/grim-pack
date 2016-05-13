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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		int xMov = 0;
		int zMov = 0;
		switch (side) {
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
			return false;
		}

		if (stack.getItemDamage() == 0) {
			xMov *= -1;
			zMov *= -1;
		}

		Block block = worldIn.getBlockState(pos).getBlock();
		// Not-allowed blocks no matter what
		if (block == null || block.getMaterial().isLiquid() || block == Blocks.fire || block == Blocks.snow_layer || block instanceof BlockDoublePlant || block instanceof BlockContainer)
			return false;

		if (ToolsConfig.restrictPowerStaffBlocks && allowedBlocks.contains(block)) {
			onPower(block, worldIn, pos, xMov, zMov);
		} else if (!ToolsConfig.restrictPowerStaffBlocks) {
			// Default not-allowed blocks
			if (block != Blocks.bedrock || block != Blocks.obsidian)
				onPower(block, worldIn, pos, xMov, zMov);
		}

		return true;
	}

	private void onPower(Block block, World worldIn, BlockPos pos, int xMov, int zMov) {
		if (block instanceof BlockFalling) {
			IBlockState state = worldIn.getBlockState(pos);
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
