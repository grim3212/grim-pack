package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUraniumOre extends Block {

	public BlockUraniumOre() {
		super(Material.IRON);
		setSoundType(SoundType.STONE);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		int damage = 4;
		if (entityIn instanceof EntityPlayer) {
			if (((EntityPlayer) entityIn).inventory.armorInventory[0] != null && ((EntityPlayer) entityIn).inventory.armorInventory[0].getItem() == IndustryItems.anti_radiation_boots) {
				damage--;
			}
			if (((EntityPlayer) entityIn).inventory.armorInventory[1] != null && ((EntityPlayer) entityIn).inventory.armorInventory[1].getItem() == IndustryItems.anti_radiation_legs) {
				damage--;
			}
			if (((EntityPlayer) entityIn).inventory.armorInventory[2] != null && ((EntityPlayer) entityIn).inventory.armorInventory[2].getItem() == IndustryItems.anti_radiation_chest) {
				damage--;
			}
			if (((EntityPlayer) entityIn).inventory.armorInventory[3] != null && ((EntityPlayer) entityIn).inventory.armorInventory[3].getItem() == IndustryItems.anti_radiation_helmet) {
				damage--;
			}
			if (damage != 0) {
				entityIn.attackEntityFrom(DamageSource.magic, damage);
			}
		} else {
			if (damage != 0) {
				entityIn.attackEntityFrom(DamageSource.magic, damage);
			}
		}
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		int damage = 4;
		if (playerIn.inventory.armorInventory[3] != null && playerIn.inventory.armorInventory[3].getItem() == IndustryItems.anti_radiation_helmet) {
			damage--;
		}
		if (playerIn.inventory.armorInventory[2] != null && playerIn.inventory.armorInventory[2].getItem() == IndustryItems.anti_radiation_chest) {
			damage--;
		}
		if (playerIn.inventory.armorInventory[1] != null && playerIn.inventory.armorInventory[1].getItem() == IndustryItems.anti_radiation_legs) {
			damage--;
		}
		if (playerIn.inventory.armorInventory[0] != null && playerIn.inventory.armorInventory[0].getItem() == IndustryItems.anti_radiation_boots) {
			damage--;
		}
		if (damage != 0) {
			playerIn.attackEntityFrom(DamageSource.magic, damage);
		}
	}
}
