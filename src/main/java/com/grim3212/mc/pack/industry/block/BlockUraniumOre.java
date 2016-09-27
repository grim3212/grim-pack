package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUraniumOre extends BlockManual {

	public BlockUraniumOre() {
		super(Material.IRON, SoundType.STONE);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		float damage = IndustryConfig.uraniumDamage;
		if (entityIn instanceof EntityPlayer) {
			if (((EntityPlayer) entityIn).inventory.armorInventory[0] != null && ((EntityPlayer) entityIn).inventory.armorInventory[0].getItem() == IndustryItems.anti_radiation_boots) {
				damage = damage - damage/4;
				if (damage < 0) damage = 0;
			}
			if (((EntityPlayer) entityIn).inventory.armorInventory[1] != null && ((EntityPlayer) entityIn).inventory.armorInventory[1].getItem() == IndustryItems.anti_radiation_legs) {
				//damage--;
				damage = damage - damage/4;
				if (damage < 0) damage = 0;
			}
			if (((EntityPlayer) entityIn).inventory.armorInventory[2] != null && ((EntityPlayer) entityIn).inventory.armorInventory[2].getItem() == IndustryItems.anti_radiation_chest) {
				//damage--;
				damage = damage - damage/4;
				if (damage < 0) damage = 0;
			}
			if (((EntityPlayer) entityIn).inventory.armorInventory[3] != null && ((EntityPlayer) entityIn).inventory.armorInventory[3].getItem() == IndustryItems.anti_radiation_helmet) {
				//damage--;
				damage = damage - damage/4;
				if (damage < 0) damage = 0;
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
		float damage = IndustryConfig.uraniumDamage;
		float damageFraction = damage/4;
		if (playerIn.inventory.armorInventory[3] != null && playerIn.inventory.armorInventory[3].getItem() == IndustryItems.anti_radiation_helmet) {
			//damage--;
			damage = damage - damageFraction;
			if (damage < 0) damage = 0;
		}
		if (playerIn.inventory.armorInventory[2] != null && playerIn.inventory.armorInventory[2].getItem() == IndustryItems.anti_radiation_chest) {
			//damage--;
			damage = damage - damageFraction;
			if (damage < 0) damage = 0;
		}
		if (playerIn.inventory.armorInventory[1] != null && playerIn.inventory.armorInventory[1].getItem() == IndustryItems.anti_radiation_legs) {
			//damage--;
			damage = damage - damageFraction;
			if (damage < 0) damage = 0;
		}
		if (playerIn.inventory.armorInventory[0] != null && playerIn.inventory.armorInventory[0].getItem() == IndustryItems.anti_radiation_boots) {
			//damage--;
			damage = damage - damageFraction;
			if (damage < 0) damage = 0;
		}
		if (damage != 0) {
			playerIn.attackEntityFrom(DamageSource.magic, damage);
		}
	}
	
	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.uranium_page;
	}
}
