package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemModernDoor extends ItemManualBlock {

	public ItemModernDoor(Block block) {
		super(block);
	}

	public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		if (facing != Direction.UP) {
			return ActionResultType.FAIL;
		} else {
			BlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (!block.isReplaceable(worldIn, pos)) {
				pos = pos.offset(facing);
			}

			ItemStack itemstack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(worldIn, pos)) {
				Direction enumfacing = Direction.fromAngle((double) player.rotationYaw);
				int i = enumfacing.getFrontOffsetX();
				int j = enumfacing.getFrontOffsetZ();
				boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
				placeDoor(worldIn, pos, enumfacing, this.block, flag);
				SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
				worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				itemstack.shrink(1);
				return ActionResultType.SUCCESS;
			} else {
				return ActionResultType.FAIL;
			}
		}
	}

	public static void placeDoor(World worldIn, BlockPos pos, Direction facing, Block door, boolean isRightHinge) {
		BlockPos blockpos = pos.offset(facing.rotateY());
		BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
		int i = (worldIn.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
		int j = (worldIn.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
		boolean flag = worldIn.getBlockState(blockpos1).getBlock() == door || worldIn.getBlockState(blockpos1.up()).getBlock() == door;
		boolean flag1 = worldIn.getBlockState(blockpos).getBlock() == door || worldIn.getBlockState(blockpos.up()).getBlock() == door;

		if ((!flag || flag1) && j <= i) {
			if (flag1 && !flag || j < i) {
				isRightHinge = false;
			}
		} else {
			isRightHinge = true;
		}

		BlockPos blockpos2 = pos.up();
		boolean flag2 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos2);
		BlockState iblockstate = door.getDefaultState().withProperty(DoorBlock.FACING, facing).withProperty(DoorBlock.HINGE, isRightHinge ? DoorBlock.EnumHingePosition.RIGHT : DoorBlock.EnumHingePosition.LEFT).withProperty(DoorBlock.POWERED, Boolean.valueOf(flag2)).withProperty(DoorBlock.OPEN, Boolean.valueOf(flag2));
		worldIn.setBlockState(pos, iblockstate.withProperty(DoorBlock.HALF, DoorBlock.EnumDoorHalf.LOWER), 2);
		worldIn.setBlockState(blockpos2, iblockstate.withProperty(DoorBlock.HALF, DoorBlock.EnumDoorHalf.UPPER), 2);
		worldIn.notifyNeighborsOfStateChange(pos, door, false);
		worldIn.notifyNeighborsOfStateChange(blockpos2, door, false);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.doors_page;
	}

}
