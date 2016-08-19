package com.grim3212.mc.pack.decor.block;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

public abstract class BlockHedge extends BlockSloped {

	public BlockHedge() {
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		TileEntity te = world.getTileEntity(pos);

		List<ItemStack> ret = new ArrayList<ItemStack>();

		if (!(((IExtendedBlockState) this.getExtendedState(state, world, pos)).getValue(BLOCK_STATE).getBlock() instanceof BlockLeaves)) {
			if (te instanceof TileEntityColorizer) {
				ItemStack item = new ItemStack(this);
				NBTHelper.setString(item, "registryName", Block.REGISTRY.getNameForObject(Blocks.AIR).toString());
				NBTHelper.setInteger(item, "meta", 0);
				ret.add(item);
			} else {
				ret.add(new ItemStack(this));
			}
		}
		return ret;
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (!(((IExtendedBlockState) this.getExtendedState(state, worldIn, pos)).getValue(BLOCK_STATE).getBlock() instanceof BlockLeaves)) {
			if (te instanceof TileEntityColorizer) {
				player.addStat(StatList.getBlockStats(this));
				player.addExhaustion(0.025F);

				harvesters.set(player);
				ItemStack itemstack = new ItemStack(this);
				NBTHelper.setString(itemstack, "registryName", Block.REGISTRY.getNameForObject(Blocks.AIR).toString());
				NBTHelper.setInteger(itemstack, "meta", 0);
				spawnAsEntity(worldIn, pos, itemstack);
				harvesters.set(null);
			} else {
				super.harvestBlock(worldIn, player, pos, state, (TileEntity) null, stack);
			}
		} else {
			player.addStat(StatList.getBlockStats(this));
			player.addExhaustion(0.025F);

			harvesters.set(player);
			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			this.dropBlockAsItem(worldIn, pos, state, i);
			harvesters.set(null);
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			if (!(((IExtendedBlockState) this.getExtendedState(state, worldIn, pos)).getValue(BLOCK_STATE) == Blocks.AIR.getDefaultState()) && !(((IExtendedBlockState) this.getExtendedState(state, worldIn, pos)).getValue(BLOCK_STATE).getBlock() instanceof BlockLeaves)) {
				IBlockState blockState = ((IExtendedBlockState) this.getExtendedState(state, worldIn, pos)).getValue(BLOCK_STATE);
				spawnAsEntity(worldIn, pos, new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState)));
			}
		}
	}

}
