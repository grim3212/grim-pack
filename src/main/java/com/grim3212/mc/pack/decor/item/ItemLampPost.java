package com.grim3212.mc.pack.decor.item;

import java.util.List;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemLampPost extends Item {

	public ItemLampPost() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (block == Blocks.SNOW_LAYER && ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1) {
			facing = EnumFacing.UP;
		} else if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(facing);
		}

		if (stack.stackSize == 0) {
			return EnumActionResult.FAIL;
		} else if (!playerIn.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		} else if (pos.getY() == 255 && DecorBlocks.lamp_post_bottom.getDefaultState().getMaterial().isSolid()) {
			return EnumActionResult.FAIL;
		} else if (worldIn.canBlockBePlaced(DecorBlocks.lamp_post_bottom, pos, false, facing, (Entity) null, stack) && worldIn.canBlockBePlaced(DecorBlocks.lamp_post_middle, pos.up(), false, facing, (Entity) null, stack) && worldIn.canBlockBePlaced(DecorBlocks.lamp_post_top, pos.up(2), false, facing, (Entity) null, stack)) {

			worldIn.setBlockState(pos, DecorBlocks.lamp_post_bottom.getDefaultState());
			worldIn.setBlockState(pos.up(), DecorBlocks.lamp_post_middle.getDefaultState());
			worldIn.setBlockState(pos.up(2), DecorBlocks.lamp_post_top.getDefaultState());

			--stack.stackSize;

			worldIn.playSound(playerIn, pos, DecorBlocks.lamp_post_bottom.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (DecorBlocks.lamp_post_bottom.getSoundType().getVolume() + 1.0F) / 2.0F, DecorBlocks.lamp_post_bottom.getSoundType().getPitch() * 0.8F);

			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		ItemStack itemstack = new ItemStack(this);
		NBTHelper.setString(itemstack, "registryName", Block.REGISTRY.getNameForObject(Blocks.AIR).toString());
		NBTHelper.setInteger(itemstack, "meta", 0);
		subItems.add(itemstack);
	}
}
