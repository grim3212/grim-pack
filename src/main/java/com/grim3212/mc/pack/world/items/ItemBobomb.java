package com.grim3212.mc.pack.world.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.entity.EntityBobomb;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBobomb extends ItemManual {

	public ItemBobomb() {
		super("bobomb");
		setCreativeTab(GrimCreativeTabs.GRIM_WORLD);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			EntityBobomb bobomb = new EntityBobomb(worldIn);
			BlockPos offset = pos.offset(facing);
			bobomb.setLocationAndAngles(offset.getX() + 0.5D, offset.getY() + 0.5D, offset.getZ() + 0.5D, player.rotationYaw, 0.3F);
			worldIn.spawnEntity(bobomb);
		}

		player.getHeldItem(hand).shrink(1);

		return EnumActionResult.SUCCESS;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualWorld.bobomb_page;
	}

}
