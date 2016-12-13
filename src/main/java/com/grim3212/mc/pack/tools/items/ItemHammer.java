package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.client.ManualTools;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHammer extends ItemManual {

	public ItemHammer(ToolMaterial toolMaterial) {
		setMaxStackSize(1);
		setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		this.setMaxDamage(toolMaterial.getMaxUses());
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.hammer_page;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer entityplayer) {
		World world = entityplayer.world;

		if (!world.isRemote && !entityplayer.capabilities.isCreativeMode) {
			world.getBlockState(pos).getBlock();
			entityplayer.addStat(StatList.getBlockStats(world.getBlockState(pos).getBlock()), 1);
			entityplayer.addExhaustion(0.025F);
			world.playEvent(2001, pos, Block.getStateId(world.getBlockState(pos)));
			if (itemstack.getItemDamage() <= itemstack.getMaxDamage()) {
				world.setBlockToAir(pos);
			}
			itemstack.damageItem(1, entityplayer);
			return true;
		} else if (world.isRemote) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return true;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		return 80f;
	}
}
