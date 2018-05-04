package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.frozen.FrozenCapability;
import com.grim3212.mc.pack.util.frozen.MessageFrozen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemPhoenixStaff extends ItemStaff {

	private static final int PLACE_LAVA = 0;
	private static final int THAW_FIRE = 1;
	private static final int THAW = 10;

	public ItemPhoenixStaff() {
		super("phoenix_staff");
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.phoenixStaff_page;
	}

	@Override
	protected void hitEntity(EntityLivingBase target) {
		FrozenCapability.unFreezeEntity(target);
		if (!target.world.isRemote) {
			// Send to Client
			PacketDispatcher.sendToDimension(new MessageFrozen(target.getEntityId(), false, 0), target.dimension);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		int keys = NBTHelper.getInt(stack, "Keys");

		if (keys == THAW_FIRE) {
			// Thaw entities
			playerIn.swingArm(handIn);
			int dmg = thawStuff(playerIn, stack, worldIn, playerIn.getPosition(), false);
			stack.damageItem(dmg, playerIn);
		} else if (keys == THAW) {
			// Thaw water
			playerIn.swingArm(handIn);
			int dmg = thawStuff(playerIn, stack, worldIn, playerIn.getPosition(), true);
			stack.damageItem(dmg, playerIn);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		int keys = NBTHelper.getInt(stack, "Keys");

		if (keys == PLACE_LAVA) {
			BlockPos lavaPos = pos.offset(facing);

			if (worldIn.isAirBlock(lavaPos) || worldIn.getBlockState(lavaPos).getBlock().isReplaceable(worldIn, lavaPos)) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, 20D, 20D, 20D, 0.0D, 0.0D, 0.0D);
				worldIn.setBlockState(lavaPos, Blocks.FLOWING_LAVA.getDefaultState());
				stack.damageItem(10, player);
			}
		} else if (keys == THAW_FIRE) {
			// Place fire
			BlockPos firePos = pos.offset(facing);

			if (worldIn.isAirBlock(firePos) || worldIn.getBlockState(firePos).getBlock().isReplaceable(worldIn, firePos)) {
				worldIn.playSound((EntityPlayer) null, firePos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				worldIn.setBlockState(firePos, Blocks.FIRE.getDefaultState());
				stack.damageItem(1, player);
			}
		} else if (keys == THAW) {
			// Thaw water
			if (worldIn.getBlockState(pos).getBlock() == Blocks.ICE || worldIn.getBlockState(pos).getBlock() == Blocks.PACKED_ICE) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, 20D, 20D, 20D, 0.0D, 0.0D, 0.0D);
				worldIn.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState());
				stack.damageItem(1, player);
			}
		}

		return EnumActionResult.SUCCESS;
	}

	private int thawStuff(EntityPlayer player, ItemStack itemstack, World world, BlockPos pos, boolean water) {
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

						if (state.getBlock() == Blocks.ICE || state.getBlock() == Blocks.PACKED_ICE) {
							world.setBlockState(newPos, Blocks.FLOWING_WATER.getDefaultState());
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
								FrozenCapability.unFreezeEntity((EntityLivingBase) entity);
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
