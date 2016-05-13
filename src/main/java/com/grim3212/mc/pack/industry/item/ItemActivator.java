package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.industry.block.BlockGate;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemActivator extends Item {

	private static final int maxRange = 32;
	private static final int vertRange = 3;

	public ItemActivator() {
		this.maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		int x = MathHelper.floor_double(player.posX);
		int y = MathHelper.floor_double(player.posY);
		int z = MathHelper.floor_double(player.posZ);

		Block activationBlock = null;
		if (stack.getItem() == IndustryItems.garage_remote) {
			activationBlock = IndustryBlocks.garage;
		} else if (stack.getItem() == IndustryItems.gate_trumpet) {
			activationBlock = IndustryBlocks.castle_gate;
		}

		switch (player.getHorizontalFacing()) {
		case NORTH:
			for (int i = z; i > z - maxRange; --i) {
				for (int var9 = y + vertRange; var9 > y - vertRange; --var9) {
					BlockPos pos = new BlockPos(x, var9, i);

					if (world.getBlockState(pos).getBlock() instanceof BlockGate && world.getBlockState(pos).getBlock() == activationBlock) {
						EnumFacing facing = (EnumFacing) world.getBlockState(pos).getValue(BlockGate.FACING);
						BlockGate gate = (BlockGate) world.getBlockState(pos).getBlock();
						gate.onBlockActivated(world, pos, world.getBlockState(pos), player, facing, 1, 1, 1);
						return stack;
					}
				}
			}

			return stack;
		case SOUTH:
			for (int i = z; i < z + maxRange; ++i) {
				for (int var9 = y + vertRange; var9 > y - vertRange; --var9) {
					BlockPos pos = new BlockPos(x, var9, i);

					if (world.getBlockState(pos).getBlock() instanceof BlockGate && world.getBlockState(pos).getBlock() == activationBlock) {
						EnumFacing facing = (EnumFacing) world.getBlockState(pos).getValue(BlockGate.FACING);
						BlockGate gate = (BlockGate) world.getBlockState(pos).getBlock();
						gate.onBlockActivated(world, pos, world.getBlockState(pos), player, facing, 1, 1, 1);
						return stack;
					}
				}
			}

			return stack;
		case EAST:
			for (int i = x; i < x + maxRange; ++i) {
				for (int var9 = y + vertRange; var9 > y - vertRange; --var9) {
					BlockPos pos = new BlockPos(i, var9, z);

					if (world.getBlockState(pos).getBlock() instanceof BlockGate && world.getBlockState(pos).getBlock() == activationBlock) {
						EnumFacing facing = (EnumFacing) world.getBlockState(pos).getValue(BlockGate.FACING);
						BlockGate gate = (BlockGate) world.getBlockState(pos).getBlock();
						gate.onBlockActivated(world, pos, world.getBlockState(pos), player, facing, 1, 1, 1);
						return stack;
					}
				}
			}

			return stack;
		case WEST:
			for (int i = x; i > x - maxRange; --i) {
				for (int var9 = y + vertRange; var9 > y - vertRange; --var9) {
					BlockPos pos = new BlockPos(i, var9, z);

					if (world.getBlockState(pos).getBlock() instanceof BlockGate && world.getBlockState(pos).getBlock() == activationBlock) {
						EnumFacing facing = (EnumFacing) world.getBlockState(pos).getValue(BlockGate.FACING);
						BlockGate gate = (BlockGate) world.getBlockState(pos).getBlock();
						gate.onBlockActivated(world, pos, world.getBlockState(pos), player, facing, 1, 1, 1);
						return stack;
					}
				}
			}
		default:
			return stack;
		}
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
}
