package com.grim3212.mc.pack.tools.items;

import java.util.Random;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemExtinguisher extends ItemManual {

	public ItemExtinguisher() {
		setMaxDamage(1);
		setMaxStackSize(1);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.extinguisher_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.getHeldItem(hand).getItemDamage() == 0) {
			double d = playerIn.posX;
			double d1 = playerIn.posY;
			double d2 = playerIn.posZ;
			Random random = new Random();
			for (int i = -10; i < 10; i++) {
				for (int j = -10; j < 10; j++) {
					for (int k = -10; k < 10; k++) {
						BlockPos pos = new BlockPos((int) d + i + 1, (int) d1 + j, (int) d2 + k + 1);
						IBlockState l = worldIn.getBlockState(pos);
						if (l.getBlock() != Blocks.FIRE && l.getBlock() != Blocks.LAVA && l.getMaterial() != Material.LAVA) {
							continue;
						}

						worldIn.setBlockToAir(pos);
						for (int i1 = 0; i1 < 4; i1++) {
							float f = (float) d + (float) i + 1.0F + random.nextFloat();
							float f1 = (float) d1 + (float) j + random.nextFloat() * 0.5F + 0.1F;
							float f2 = (float) d2 + (float) k + 1.0F + random.nextFloat();
							worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, f, f1, f2, 0.0D, 0.0D, 0.0D);
						}
					}
				}
			}
			playerIn.getHeldItem(hand).damageItem(1, playerIn);
			worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 2.0F, (1.0F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F) * 0.7F);
			playerIn.extinguish();
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}
}
