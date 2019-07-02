package com.grim3212.mc.pack.decor.item;

import java.util.Set;

import com.google.common.collect.Sets;
import com.grim3212.mc.pack.core.item.ItemManualTool;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerHedge;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPruners extends ItemManualTool {

	private static Set<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] { Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES });

	public ItemPruners() {
		super(DecorNames.PRUNERS, 0.0f, 0.0f, ItemTier.IRON, blocksEffectiveAgainst, new Item.Properties().maxDamage(256).group(GrimItemGroups.GRIM_DECOR));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.pruners_page;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World worldIn = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = worldIn.getBlockState(pos);
		PlayerEntity playerIn = context.getPlayer();

		ItemStack stack = playerIn.getHeldItem(context.getHand());
		if (state.getBlock() instanceof LeavesBlock) {
			stack.damageItem(1, playerIn, (ent) -> {
				ent.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});

			if (playerIn.isSneaking()) {
				worldIn.setBlockState(pos, DecorBlocks.sloped_post.getDefaultState().with(BlockColorizerHedge.FACING, Direction.DOWN), 3);
			} else {
				worldIn.setBlockState(pos, DecorBlocks.sloped_post.getDefaultState().with(BlockColorizerHedge.FACING, Direction.UP), 3);
			}

			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityColorizer) {
				((TileEntityColorizer) tileentity).setStoredBlockState(state);
				worldIn.playSound(playerIn, pos, state.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (state.getSoundType().getVolume() + 1.0F) / 2.0F, state.getSoundType().getPitch() * 0.8F);
			}
			return ActionResultType.SUCCESS;
		} else if (state.getBlock() instanceof BlockColorizerHedge) {
			if (state.getBlock() == DecorBlocks.sloped_post) {
				TileEntity tileentity = worldIn.getTileEntity(pos);

				if (tileentity instanceof TileEntityColorizer) {
					BlockState stored = ((TileEntityColorizer) tileentity).getStoredBlockState();

					if (stored.getBlock() instanceof LeavesBlock) {
						stack.damageItem(1, playerIn, (ent) -> {
							ent.sendBreakAnimation(EquipmentSlotType.MAINHAND);
						});
						if (playerIn.isSneaking()) {
							worldIn.setBlockState(pos, DecorBlocks.full_pyramid.getDefaultState().with(BlockColorizerHedge.FACING, Direction.DOWN), 3);
						} else {
							worldIn.setBlockState(pos, DecorBlocks.full_pyramid.getDefaultState().with(BlockColorizerHedge.FACING, Direction.UP), 3);
						}

						worldIn.playSound(playerIn, pos, stored.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (stored.getSoundType().getVolume() + 1.0F) / 2.0F, stored.getSoundType().getPitch() * 0.8F);
					}

					return ActionResultType.SUCCESS;
				}
			} else if (state.getBlock() == DecorBlocks.full_pyramid) {
				TileEntity tileentity = worldIn.getTileEntity(pos);

				if (tileentity instanceof TileEntityColorizer) {
					BlockState stored = ((TileEntityColorizer) tileentity).getStoredBlockState();

					if (stored.getBlock() instanceof LeavesBlock) {
						stack.damageItem(1, playerIn, (ent) -> {
							ent.sendBreakAnimation(EquipmentSlotType.MAINHAND);
						});

						if (playerIn.isSneaking()) {
							worldIn.setBlockState(pos, DecorBlocks.pyramid.getDefaultState().with(BlockColorizerHedge.FACING, Direction.DOWN), 3);
						} else {
							worldIn.setBlockState(pos, DecorBlocks.pyramid.getDefaultState().with(BlockColorizerHedge.FACING, Direction.UP), 3);
						}

						worldIn.playSound(playerIn, pos, stored.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (stored.getSoundType().getVolume() + 1.0F) / 2.0F, stored.getSoundType().getPitch() * 0.8F);
					}
					return ActionResultType.SUCCESS;
				}
			}
		}

		return super.onItemUse(context);
	}
}
