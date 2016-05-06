package com.grim3212.mc.cuisine.item;

import com.grim3212.mc.cuisine.block.CuisineBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemCocoaFruit extends Item {

	public ItemCocoaFruit() {
		maxStackSize = 16;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(side);
		}

		if (stack.stackSize == 0) {
			return false;
		} else if (!playerIn.canPlayerEdit(pos, side, stack)) {
			return false;
		} else if (worldIn.canBlockBePlaced(CuisineBlocks.cocoa_tree_sapling, pos, false, side, (Entity) null, stack)) {
			int i = this.getMetadata(stack.getMetadata());
			IBlockState iblockstate1 = CuisineBlocks.cocoa_tree_sapling.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, i, playerIn);

			if (placeBlockAt(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ, iblockstate1)) {
				worldIn.playSoundEffect((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), CuisineBlocks.cocoa_tree_sapling.stepSound.getPlaceSound(), (CuisineBlocks.cocoa_tree_sapling.stepSound.getVolume() + 1.0F) / 2.0F, CuisineBlocks.cocoa_tree_sapling.stepSound.getFrequency() * 0.8F);
				--stack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called to actually place the block, after the location is determined and
	 * all permission checks have been made.
	 *
	 * @param stack
	 *            The item stack that was used to place the block. This can be
	 *            changed inside the method.
	 * @param player
	 *            The player who is placing the block. Can be null if the block
	 *            is not being placed by a player.
	 * @param side
	 *            The side the player (or machine) right-clicked on.
	 */
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!world.setBlockState(pos, newState, 3))
			return false;

		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == CuisineBlocks.cocoa_tree_sapling) {
			CuisineBlocks.cocoa_tree_sapling.onBlockPlacedBy(world, pos, state, player, stack);
		}

		return true;
	}
}
