package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.block.BlockGate;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemActivator extends ItemManual {

	private static final int maxRange = 32;
	private static final int vertRange = 3;

	public ItemActivator(String name) {
		super(name);
		this.maxStackSize = 1;
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getItem() == IndustryItems.gate_trumpet)
			return ManualIndustry.gateTrumpet_page;

		return ManualIndustry.garageRemote_page;
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		int x = MathHelper.floor(playerIn.posX);
		int y = MathHelper.floor(playerIn.posY);
		int z = MathHelper.floor(playerIn.posZ);

		ItemStack itemStackIn = playerIn.getHeldItem(hand);
		Block activationBlock = null;
		if (itemStackIn.getItem() == IndustryItems.garage_remote) {
			activationBlock = IndustryBlocks.garage;
		} else if (itemStackIn.getItem() == IndustryItems.gate_trumpet) {
			activationBlock = IndustryBlocks.castle_gate;
		}

		switch (playerIn.getHorizontalFacing()) {
		case NORTH:
			for (int i = z; i > z - maxRange; --i) {
				for (int var9 = y + vertRange; var9 > y - vertRange; --var9) {
					BlockPos pos = new BlockPos(x, var9, i);

					if (worldIn.getBlockState(pos).getBlock() instanceof BlockGate && worldIn.getBlockState(pos).getBlock() == activationBlock) {
						EnumFacing facing = (EnumFacing) worldIn.getBlockState(pos).getValue(BlockGate.FACING);
						BlockGate gate = (BlockGate) worldIn.getBlockState(pos).getBlock();
						gate.onBlockActivated(worldIn, pos, worldIn.getBlockState(pos), playerIn, hand, facing, 1, 1, 1);
						return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
					}
				}
			}

			break;
		case SOUTH:
			for (int i = z; i < z + maxRange; ++i) {
				for (int var9 = y + vertRange; var9 > y - vertRange; --var9) {
					BlockPos pos = new BlockPos(x, var9, i);

					if (worldIn.getBlockState(pos).getBlock() instanceof BlockGate && worldIn.getBlockState(pos).getBlock() == activationBlock) {
						EnumFacing facing = (EnumFacing) worldIn.getBlockState(pos).getValue(BlockGate.FACING);
						BlockGate gate = (BlockGate) worldIn.getBlockState(pos).getBlock();
						gate.onBlockActivated(worldIn, pos, worldIn.getBlockState(pos), playerIn, hand, facing, 1, 1, 1);
						return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
					}
				}
			}

			break;
		case EAST:
			for (int i = x; i < x + maxRange; ++i) {
				for (int var9 = y + vertRange; var9 > y - vertRange; --var9) {
					BlockPos pos = new BlockPos(i, var9, z);

					if (worldIn.getBlockState(pos).getBlock() instanceof BlockGate && worldIn.getBlockState(pos).getBlock() == activationBlock) {
						EnumFacing facing = (EnumFacing) worldIn.getBlockState(pos).getValue(BlockGate.FACING);
						BlockGate gate = (BlockGate) worldIn.getBlockState(pos).getBlock();
						gate.onBlockActivated(worldIn, pos, worldIn.getBlockState(pos), playerIn, hand, facing, 1, 1, 1);
						return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
					}
				}
			}

			break;
		case WEST:
			for (int i = x; i > x - maxRange; --i) {
				for (int var9 = y + vertRange; var9 > y - vertRange; --var9) {
					BlockPos pos = new BlockPos(i, var9, z);

					if (worldIn.getBlockState(pos).getBlock() instanceof BlockGate && worldIn.getBlockState(pos).getBlock() == activationBlock) {
						EnumFacing facing = (EnumFacing) worldIn.getBlockState(pos).getValue(BlockGate.FACING);
						BlockGate gate = (BlockGate) worldIn.getBlockState(pos).getBlock();
						gate.onBlockActivated(worldIn, pos, worldIn.getBlockState(pos), playerIn, hand, facing, 1, 1, 1);
						return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
					}
				}
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
}
