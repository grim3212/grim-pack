package com.grim3212.mc.pack.tools.items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemExtinguisher extends Item {

	public ItemExtinguisher() {
		setMaxDamage(1);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (itemstack.getItemDamage() == 0) {
			double d = entityplayer.posX;
			double d1 = entityplayer.posY;
			double d2 = entityplayer.posZ;
			Random random = new Random();
			for (int i = -10; i < 10; i++) {
				for (int j = -10; j < 10; j++) {
					for (int k = -10; k < 10; k++) {
						BlockPos pos = new BlockPos((int) d + i + 1, (int) d1 + j, (int) d2 + k + 1);
						Block l = world.getBlockState(pos).getBlock();
						if (l != Blocks.fire && l != Blocks.lava && l.getMaterial() != Material.lava) {
							continue;
						}

						world.setBlockToAir(pos);
						for (int i1 = 0; i1 < 4; i1++) {
							float f = (float) d + (float) i + 1.0F + random.nextFloat();
							float f1 = (float) d1 + (float) j + random.nextFloat() * 0.5F + 0.1F;
							float f2 = (float) d2 + (float) k + 1.0F + random.nextFloat();
							world.spawnParticle(EnumParticleTypes.WATER_SPLASH, f, f1, f2, 0.0D, 0.0D, 0.0D);
						}
					}
				}
			}
			itemstack.damageItem(1, entityplayer);
			world.playSoundEffect(d, d1, d2, "random.fizz", 2.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
			entityplayer.extinguish();
		}
		return itemstack;
	}
}
