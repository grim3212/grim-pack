package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.init.IndustryNames;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUraniumOre extends BlockManual {

	public BlockUraniumOre() {
		super(IndustryNames.URANIUM_ORE, Block.Properties.create(Material.IRON).sound(SoundType.STONE).hardnessAndResistance(1.0f).lightValue(4));
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		float damage = IndustryConfig.uraniumDamage.get();
		float damageFraction = damage / 4;
		if (entityIn instanceof PlayerEntity) {
			if (!((PlayerEntity) entityIn).inventory.armorInventory.get(0).isEmpty() && ((PlayerEntity) entityIn).inventory.armorInventory.get(0).getItem() == IndustryItems.anti_radiation_boots) {
				damage -= damageFraction;
				if (damage < 0)
					damage = 0;
			}
			if (!((PlayerEntity) entityIn).inventory.armorInventory.get(1).isEmpty() && ((PlayerEntity) entityIn).inventory.armorInventory.get(1).getItem() == IndustryItems.anti_radiation_legs) {
				damage -= damageFraction;
				if (damage < 0)
					damage = 0;
			}
			if (!((PlayerEntity) entityIn).inventory.armorInventory.get(2).isEmpty() && ((PlayerEntity) entityIn).inventory.armorInventory.get(2).getItem() == IndustryItems.anti_radiation_chest) {
				damage -= damageFraction;
				if (damage < 0)
					damage = 0;
			}
			if (!((PlayerEntity) entityIn).inventory.armorInventory.get(3).isEmpty() && ((PlayerEntity) entityIn).inventory.armorInventory.get(3).getItem() == IndustryItems.anti_radiation_helmet) {
				damage -= damageFraction;
				if (damage < 0)
					damage = 0;
			}
			if (damage != 0) {
				entityIn.attackEntityFrom(DamageSource.MAGIC, damage);
			}
		} else {
			if (damage != 0) {
				worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);
				worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);
				worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);
				worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 1.75D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);
				worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);

				entityIn.attackEntityFrom(DamageSource.MAGIC, damage);
			}
		}
	}

	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn) {
		float damage = IndustryConfig.uraniumDamage.get();
		float damageFraction = damage / 4;
		if (!playerIn.inventory.armorInventory.get(3).isEmpty() && playerIn.inventory.armorInventory.get(3).getItem() == IndustryItems.anti_radiation_helmet) {
			damage -= damageFraction;
			if (damage < 0)
				damage = 0;
		}
		if (!playerIn.inventory.armorInventory.get(2).isEmpty() && playerIn.inventory.armorInventory.get(2).getItem() == IndustryItems.anti_radiation_chest) {
			damage -= damageFraction;
			if (damage < 0)
				damage = 0;
		}
		if (!playerIn.inventory.armorInventory.get(1).isEmpty() && playerIn.inventory.armorInventory.get(1).getItem() == IndustryItems.anti_radiation_legs) {
			damage -= damageFraction;
			if (damage < 0)
				damage = 0;
		}
		if (!playerIn.inventory.armorInventory.get(0).isEmpty() && playerIn.inventory.armorInventory.get(0).getItem() == IndustryItems.anti_radiation_boots) {
			damage -= damageFraction;
			if (damage < 0)
				damage = 0;
		}
		if (damage != 0) {
			worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);
			worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 1.25D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);
			worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);
			worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 1.75D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);
			worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, 0.0D, 4.0D, 0.0D);

			playerIn.attackEntityFrom(DamageSource.MAGIC, damage);
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.uranium_page;
	}
}
