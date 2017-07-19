package com.grim3212.mc.pack.decor.item;

import java.util.Set;

import com.google.common.collect.Sets;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerHedge;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate.EnumHalf;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ItemPruners extends ItemTool {

	private static Set<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] { Blocks.LEAVES, Blocks.LEAVES2 });

	public ItemPruners() {
		super(ToolMaterial.IRON, blocksEffectiveAgainst);
		this.setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
		this.setUnlocalizedName("pruners");
		this.setRegistryName(new ResourceLocation(GrimPack.modID, "pruners"));
	}

	@Override
	@SuppressWarnings("deprecation")
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = worldIn.getBlockState(pos);

		ItemStack stack = playerIn.getHeldItem(hand);
		if (state.getBlock() instanceof BlockLeaves) {
			stack.damageItem(1, playerIn);

			if (playerIn.isSneaking()) {
				worldIn.setBlockState(pos, DecorBlocks.sloped_post.getDefaultState().withProperty(BlockColorizerSlopedRotate.HALF, EnumHalf.TOP), 3);
			} else {
				worldIn.setBlockState(pos, DecorBlocks.sloped_post.getDefaultState().withProperty(BlockColorizerSlopedRotate.HALF, EnumHalf.BOTTOM), 3);
			}

			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityColorizer) {
				((TileEntityColorizer) tileentity).setBlockState(state);
				worldIn.playSound(playerIn, pos, state.getBlock().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (state.getBlock().getSoundType().getVolume() + 1.0F) / 2.0F, state.getBlock().getSoundType().getPitch() * 0.8F);
			}
			return EnumActionResult.SUCCESS;
		} else if (state.getBlock() instanceof BlockColorizerHedge) {
			if (state.getBlock() == DecorBlocks.sloped_post) {
				IBlockState exState = ((IExtendedBlockState) state.getBlock().getExtendedState(state, worldIn, pos)).getValue(BlockColorizerHedge.BLOCK_STATE);

				if (exState.getBlock() instanceof BlockLeaves) {
					stack.damageItem(1, playerIn);
					if (playerIn.isSneaking()) {
						worldIn.setBlockState(pos, DecorBlocks.full_pyramid.getDefaultState().withProperty(BlockColorizerSlopedRotate.HALF, EnumHalf.TOP), 3);
					} else {
						worldIn.setBlockState(pos, DecorBlocks.full_pyramid.getDefaultState().withProperty(BlockColorizerSlopedRotate.HALF, EnumHalf.BOTTOM), 3);
					}

					TileEntity tileentity = worldIn.getTileEntity(pos);

					if (tileentity instanceof TileEntityColorizer) {
						((TileEntityColorizer) tileentity).setBlockState(exState);
						worldIn.playSound(playerIn, pos, exState.getBlock().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (exState.getBlock().getSoundType().getVolume() + 1.0F) / 2.0F, exState.getBlock().getSoundType().getPitch() * 0.8F);
					}

					return EnumActionResult.SUCCESS;
				}
			} else if (state.getBlock() == DecorBlocks.full_pyramid) {
				IBlockState exState = ((IExtendedBlockState) state.getBlock().getExtendedState(state, worldIn, pos)).getValue(BlockColorizerHedge.BLOCK_STATE);

				if (exState.getBlock() instanceof BlockLeaves) {
					stack.damageItem(1, playerIn);

					if (playerIn.isSneaking()) {
						worldIn.setBlockState(pos, DecorBlocks.pyramid.getDefaultState().withProperty(BlockColorizerSlopedRotate.HALF, EnumHalf.TOP), 3);
					} else {
						worldIn.setBlockState(pos, DecorBlocks.pyramid.getDefaultState().withProperty(BlockColorizerSlopedRotate.HALF, EnumHalf.BOTTOM), 3);
					}

					TileEntity tileentity = worldIn.getTileEntity(pos);

					if (tileentity instanceof TileEntityColorizer) {
						((TileEntityColorizer) tileentity).setBlockState(exState);
						worldIn.playSound(playerIn, pos, exState.getBlock().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (exState.getBlock().getSoundType().getVolume() + 1.0F) / 2.0F, exState.getBlock().getSoundType().getPitch() * 0.8F);
					}
					return EnumActionResult.SUCCESS;
				}
			}
		}

		return super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
