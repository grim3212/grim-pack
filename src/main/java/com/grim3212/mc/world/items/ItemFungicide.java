package com.grim3212.mc.world.items;

import java.util.Random;

import com.grim3212.mc.world.GrimWorld;
import com.grim3212.mc.world.blocks.BlockFungusBase;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemFungicide extends Item {

	public ItemFungicide() {
		super();
		setMaxDamage(0);
		setCreativeTab(GrimWorld.INSTANCE.getCreativeTab());
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer pl) {
		pl.swingItem();
		world.playSoundAtEntity(pl, "random.fizz", 0.2F, 0.3F / (itemRand.nextFloat() * 0.4F + 0.8F));

		killf(world, new BlockPos(pl.posX, pl.posY, pl.posZ));
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		killf(worldIn, pos);
		worldIn.playSoundAtEntity(playerIn, "random.fizz", 0.2F, 0.3F / (itemRand.nextFloat() * 0.4F + 0.8F));
		return true;
	}

	private void killf(World world, BlockPos pos) {
		if (world.isRemote)
			spawnParticles(world, pos, itemRand);

		for (int i = pos.getX() - 20; i < pos.getX() + 20; i++) {
			for (int j = pos.getY() - 20; j < pos.getY() + 20; j++) {
				for (int k = pos.getZ() - 20; k < pos.getZ() + 20; k++) {

					if (getDistance(i, k, pos.getX(), pos.getZ()) > 20 || getDistance(i, j, pos.getX(), pos.getY()) > 20) {
						continue;
					}

					BlockPos newPos = new BlockPos(i, j, k);
					Block id = world.getBlockState(newPos).getBlock();

					if (id instanceof BlockFungusBase) {
						world.setBlockToAir(newPos);
					}
				}
			}
		}
	}

	public final float getDistance(int x1, int y1, int x2, int y2) {
		float x = MathHelper.abs(x1 - x2);
		float y = MathHelper.abs(y1 - y2);

		return MathHelper.sqrt_float(x * x + y * y);
	}

	public void spawnParticles(World world, BlockPos pos, Random rand) {
		for (int ci = 0; ci < 500; ci++) {
			world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + (rand.nextDouble() * 16D) - (rand.nextDouble() * 16D), pos.getY() + (rand.nextDouble() * 16D) - (rand.nextDouble() * 16D), pos.getZ() + (rand.nextDouble() * 16D) - (rand.nextDouble() * 16D), 0.1D, 0.6D, 0.2D);
		}
	}
}
