package com.grim3212.mc.tools.items;

import java.util.List;

import com.grim3212.mc.tools.entity.EntityBlockPushPull;

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

	protected ItemPowerStaff() {
		maxStackSize = 1;
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
		double xMov = 0;
		double zMov = 0;
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
		if (block instanceof BlockFalling) {
			IBlockState state = worldIn.getBlockState(pos);
			worldIn.setBlockToAir(pos);
			worldIn.setBlockState(pos.east((int) xMov).south((int) zMov), state);
		} else if (block == null || block.getMaterial().isLiquid() || block == Blocks.fire || block == Blocks.snow_layer || block instanceof BlockDoublePlant || block instanceof BlockContainer || block == Blocks.bedrock || block == Blocks.obsidian) {
			return false;
		} else {
			EntityBlockPushPull blockpushpull = new EntityBlockPushPull(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
			blockpushpull.motionX = 0.29999999999999999D * (double) xMov;
			blockpushpull.motionZ = 0.29999999999999999D * (double) zMov;
			worldIn.spawnEntityInWorld(blockpushpull);
		}

		return true;
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		subItems.add(new ItemStack(itemIn, 1, 0));
		subItems.add(new ItemStack(itemIn, 1, 1));
	}
}
