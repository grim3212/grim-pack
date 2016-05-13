package com.grim3212.mc.pack.decor.item;

import java.util.List;

import com.grim3212.mc.pack.decor.entity.EntityFrame;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFrame extends Item {

	public ItemFrame() {
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return stack.getItemDamage() == 0 ? super.getUnlocalizedName() + "_wood" : super.getUnlocalizedName() + "_iron";
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!playerIn.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		}

		if (facing == EnumFacing.UP || facing == EnumFacing.DOWN)
			return EnumActionResult.FAIL;

		if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
			facing = facing.getOpposite();

		EntityFrame frame = new EntityFrame(worldIn, pos, facing.getHorizontalIndex(), stack.getItemDamage());

		if (frame.onValidSurface()) {
			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(frame);
				frame.playFrameSound();
			}

			stack.stackSize -= 1;
		}

		return EnumActionResult.SUCCESS;
	}
}