package com.grim3212.mc.pack.world.items;

import java.util.Random;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.world.blocks.BlockFungusBase;
import com.grim3212.mc.pack.world.client.ManualWorld;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemFungicide extends ItemManual {

	public ItemFungicide() {
		super("fungicide");
		setMaxDamage(0);
		setCreativeTab(GrimCreativeTabs.GRIM_WORLD);
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.swingArm(handIn);

		killf(worldIn, playerIn.getPosition());
		worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.2F, 0.3F / (itemRand.nextFloat() * 0.4F + 0.8F));

		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		killf(worldIn, pos);
		worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.2F, 0.3F / (itemRand.nextFloat() * 0.4F + 0.8F));

		return EnumActionResult.SUCCESS;
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

		return MathHelper.sqrt(x * x + y * y);
	}

	public void spawnParticles(World world, BlockPos pos, Random rand) {
		for (int ci = 0; ci < 500; ci++) {
			world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + (rand.nextDouble() * 16D) - (rand.nextDouble() * 16D), pos.getY() + (rand.nextDouble() * 16D) - (rand.nextDouble() * 16D), pos.getZ() + (rand.nextDouble() * 16D) - (rand.nextDouble() * 16D), 0.1D, 0.6D, 0.2D);
		}
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualWorld.fungicide_page;
	}
}
