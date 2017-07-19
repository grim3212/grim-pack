package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUraniumOre extends BlockManual {

	public BlockUraniumOre() {
		super("uranium_ore", Material.IRON, SoundType.STONE);
		setHardness(1.0F);
		setLightLevel(0.55F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {

		float damage = IndustryConfig.uraniumDamage;
		float damageFraction = damage / 4;
		if (entityIn instanceof EntityPlayer) {
			if (!((EntityPlayer) entityIn).inventory.armorInventory.get(0).isEmpty() && ((EntityPlayer) entityIn).inventory.armorInventory.get(0).getItem() == IndustryItems.anti_radiation_boots) {
				damage = damage - damageFraction;
				if (damage < 0)
					damage = 0;
			}
			if (!((EntityPlayer) entityIn).inventory.armorInventory.get(1).isEmpty() && ((EntityPlayer) entityIn).inventory.armorInventory.get(1).getItem() == IndustryItems.anti_radiation_legs) {
				// damage--;
				damage = damage - damageFraction;
				if (damage < 0)
					damage = 0;
			}
			if (!((EntityPlayer) entityIn).inventory.armorInventory.get(2).isEmpty() && ((EntityPlayer) entityIn).inventory.armorInventory.get(2).getItem() == IndustryItems.anti_radiation_chest) {
				// damage--;
				damage = damage - damageFraction;
				if (damage < 0)
					damage = 0;
			}
			if (!((EntityPlayer) entityIn).inventory.armorInventory.get(3).isEmpty() && ((EntityPlayer) entityIn).inventory.armorInventory.get(3).getItem() == IndustryItems.anti_radiation_helmet) {
				// damage--;
				damage = damage - damageFraction;
				if (damage < 0)
					damage = 0;
			}
			if (damage != 0) {
				entityIn.attackEntityFrom(DamageSource.MAGIC, damage);
			}
		} else {
			if (damage != 0) {
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 1.75D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);

				entityIn.attackEntityFrom(DamageSource.MAGIC, damage);
			}
		}
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		float damage = IndustryConfig.uraniumDamage;
		float damageFraction = damage / 4;
		if (!playerIn.inventory.armorInventory.get(3).isEmpty() && playerIn.inventory.armorInventory.get(3).getItem() == IndustryItems.anti_radiation_helmet) {
			// damage--;
			damage = damage - damageFraction;
			if (damage < 0)
				damage = 0;
		}
		if (!playerIn.inventory.armorInventory.get(2).isEmpty() && playerIn.inventory.armorInventory.get(2).getItem() == IndustryItems.anti_radiation_chest) {
			// damage--;
			damage = damage - damageFraction;
			if (damage < 0)
				damage = 0;
		}
		if (!playerIn.inventory.armorInventory.get(1).isEmpty() && playerIn.inventory.armorInventory.get(1).getItem() == IndustryItems.anti_radiation_legs) {
			// damage--;
			damage = damage - damageFraction;
			if (damage < 0)
				damage = 0;
		}
		if (!playerIn.inventory.armorInventory.get(0).isEmpty() && playerIn.inventory.armorInventory.get(0).getItem() == IndustryItems.anti_radiation_boots) {
			// damage--;
			damage = damage - damageFraction;
			if (damage < 0)
				damage = 0;
		}
		if (damage != 0) {
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 1.75D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D, new int[0]);

			playerIn.attackEntityFrom(DamageSource.MAGIC, damage);
		}
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.uranium_page;
	}
}
