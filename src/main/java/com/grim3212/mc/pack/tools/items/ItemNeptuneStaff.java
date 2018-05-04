package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.frozen.FrozenCapability;
import com.grim3212.mc.pack.util.frozen.MessageFrozen;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemNeptuneStaff extends ItemStaff {

	private static final int PLACE_WATER = 0;
	private static final int FREEZE_MOBS = 1;
	private static final int FREEZE_WATER = 10;

	public ItemNeptuneStaff() {
		super("neptune_staff");
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.neptuneStaff_page;
	}

	@Override
	protected void hitEntity(EntityLivingBase target) {
		FrozenCapability.freezeEntity(target, 0);
		if (!target.world.isRemote) {
			// Send to Client
			PacketDispatcher.sendToDimension(new MessageFrozen(target.getEntityId(), true, 0), target.dimension);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		int keys = NBTHelper.getInt(stack, "Keys");

		if (keys == FREEZE_MOBS) {
			playerIn.swingArm(handIn);
			int dmg = freezeStuff(playerIn, stack, worldIn, playerIn.getPosition(), false);
			stack.damageItem(dmg, playerIn);
		} else if (keys == FREEZE_WATER) {
			playerIn.swingArm(handIn);
			int dmg = freezeStuff(playerIn, stack, worldIn, playerIn.getPosition(), true);
			stack.damageItem(dmg, playerIn);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		int keys = NBTHelper.getInt(stack, "Keys");

		if (keys == FREEZE_MOBS) {
			stack.damageItem(1, player);
			freezeStuff(player, stack, worldIn, player.getPosition(), false);
		} else if (keys == PLACE_WATER) {
			BlockPos waterPos = pos.offset(facing);

			if (worldIn.isAirBlock(waterPos) || worldIn.getBlockState(waterPos).getBlock().isReplaceable(worldIn, waterPos)) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, 20D, 20D, 20D, 0.0D, 0.0D, 0.0D);
				worldIn.setBlockState(waterPos, Blocks.FLOWING_WATER.getDefaultState());
				stack.damageItem(10, player);
			}
		} else if (keys == FREEZE_WATER) {
			BlockPos waterPos = pos.offset(facing);

			if (worldIn.getBlockState(waterPos).getBlock() == Blocks.WATER && worldIn.getBlockState(waterPos).getValue(BlockLiquid.LEVEL) == 0) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, 20D, 20D, 20D, 0.0D, 0.0D, 0.0D);
				worldIn.setBlockState(waterPos, Blocks.ICE.getDefaultState());
				stack.damageItem(1, player);
			}
		}

		return EnumActionResult.SUCCESS;
	}

	private int freezeStuff(EntityPlayer player, ItemStack itemstack, World world, BlockPos pos, boolean water) {
		int range = 8;
		int dmg = 0;

		for (int l = pos.getX() - range; l <= pos.getX() + range; l++) {
			if (water) {
				for (int i1 = pos.getY() - range; i1 <= pos.getY() + range; i1++) {
					for (int j1 = pos.getZ() - range; j1 <= pos.getZ() + range; j1++) {
						if (Utils.getDistance(l, j1, pos.getX(), pos.getZ()) > range || Utils.getDistance(l, i1, pos.getX(), pos.getY()) > range) {
							continue;
						}

						BlockPos newPos = new BlockPos(l, i1, j1);
						IBlockState state = world.getBlockState(newPos);

						if (state.getBlock() == Blocks.WATER && state.getValue(BlockLiquid.LEVEL) == 0 || state.getBlock() == Blocks.FLOWING_WATER && state.getValue(BlockLiquid.LEVEL) == 0) {
							world.setBlockState(newPos, Blocks.ICE.getDefaultState());
							particles(world, newPos, itemRand);
							dmg++;
						}
					}
				}
			} else {
				if (UtilConfig.subpartFrozen) {
					particles(world, pos, itemRand);
					List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos.getX() - range, pos.getY() - range, pos.getZ() - range, pos.getX() + range, pos.getY() + range, pos.getZ() + range));
					for (int k1 = 0; k1 < list.size(); k1++) {
						Entity entity = list.get(k1);

						if (entity instanceof EntityLivingBase) {
							if (!entity.isDead && Utils.getDistance(MathHelper.floor(entity.posX), MathHelper.floor((float) entity.posZ), pos.getX(), pos.getZ()) <= range && Utils.getDistance(MathHelper.floor(entity.posX), MathHelper.floor(entity.posY), pos.getX(), pos.getY()) <= range) {
								particles(world, entity.getPosition(), itemRand);
								FrozenCapability.freezeEntity((EntityLivingBase) entity, 0);
								dmg++;
							}
						}
					}
				}
			}
		}

		return dmg;
	}
}
