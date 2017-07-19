package com.grim3212.mc.pack.cuisine.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCocoaFruit extends ItemManual {

	public ItemCocoaFruit() {
		super("cocoa_fruit");
		maxStackSize = 16;
		setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(side);
		}

		ItemStack stack = playerIn.getHeldItem(hand);

		if (stack.getCount() == 0) {
			return EnumActionResult.FAIL;
		} else if (!playerIn.canPlayerEdit(pos, side, stack)) {
			return EnumActionResult.FAIL;
		} else if (worldIn.mayPlace(CuisineBlocks.cocoa_tree_sapling, pos, false, side, (Entity) null)) {

			int i = this.getMetadata(stack.getMetadata());
			IBlockState iblockstate1 = CuisineBlocks.cocoa_tree_sapling.getStateForPlacement(worldIn, pos, side, hitX, hitY, hitZ, i, playerIn, hand);

			if (placeBlockAt(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ, iblockstate1)) {
				worldIn.playSound(playerIn, (double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), CuisineBlocks.cocoa_tree_sapling.getSoundType(iblockstate1, worldIn, pos, playerIn).getPlaceSound(), SoundCategory.BLOCKS, (CuisineBlocks.cocoa_tree_sapling.getSoundType(iblockstate1, worldIn, pos, playerIn).getVolume() + 1.0F) / 2.0F, CuisineBlocks.cocoa_tree_sapling.getSoundType(iblockstate1, worldIn, pos, playerIn).getPitch() * 0.8F);
				stack.shrink(1);
			}

			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
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

	@Override
	public Page getPage(ItemStack stack) {
		return ManualCuisine.cocoaFruit_page;
	}
}
