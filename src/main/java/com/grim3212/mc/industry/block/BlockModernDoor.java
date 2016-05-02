package com.grim3212.mc.industry.block;

import java.util.Random;

import com.grim3212.mc.industry.item.IndustryItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockModernDoor extends BlockDoor {

	protected BlockModernDoor(Material material) {
		super(material);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
		Block block = world.getBlockState(pos).getBlock();
		if (block == IndustryBlocks.door_chain) {
			return new ItemStack(IndustryItems.door_chain_item);
		} else if (block == IndustryBlocks.door_glass) {
			return new ItemStack(IndustryItems.door_glass_item);
		} else if (block == IndustryBlocks.door_steel) {
			return new ItemStack(IndustryItems.door_steel_item);
		}

		return super.getPickBlock(target, world, pos, player);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		Block block = state.getBlock();
		if (block == IndustryBlocks.door_chain) {
			return IndustryItems.door_chain_item;
		} else if (block == IndustryBlocks.door_glass) {
			return IndustryItems.door_glass_item;
		} else if (block == IndustryBlocks.door_steel) {
			return IndustryItems.door_steel_item;
		}

		return super.getItemDropped(state, rand, fortune);
	}
}
