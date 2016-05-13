package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.tools.GrimTools;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemHammer extends Item {

	public ItemHammer(ToolMaterial toolMaterial) {
		setMaxStackSize(1);
		setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		this.setMaxDamage(toolMaterial.getMaxUses());
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer entityplayer) {
		World world = entityplayer.worldObj;

		if (!world.isRemote && !entityplayer.capabilities.isCreativeMode) {
			world.getBlockState(pos).getBlock();
			entityplayer.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(world.getBlockState(pos).getBlock())], 1);
			entityplayer.addExhaustion(0.025F);
			world.playAuxSFX(2001, pos, Block.getStateId(world.getBlockState(pos)));
			if (itemstack.getItemDamage() <= itemstack.getMaxDamage()) {
				world.setBlockToAir(pos);
			}
			itemstack.damageItem(1, entityplayer);
			if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
				entityplayer.destroyCurrentEquippedItem();
				world.playSoundAtEntity(entityplayer, "random.break", 1.0F, 1.0F);
				return false;
			}
			return true;
		} else if (world.isRemote) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
		return true;
	}

	public boolean isFull3D() {
		return true;
	}

	@Override
	public float getDigSpeed(ItemStack itemstack, IBlockState state) {
		return 80F;
	}
}
