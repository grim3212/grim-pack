package com.grim3212.mc.pack.tools.items;

import java.util.Random;

import com.google.common.collect.Multimap;
import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.util.config.UtilConfig;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class ItemStaff extends ItemManual {

	public ItemStaff(String s) {
		super(s);
		this.setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
		this.setMaxStackSize(1);
		this.setMaxDamage(500);
		this.setFull3D();
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		return state.getBlock() == Blocks.WEB ? 15f : 1.5f;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(1, attacker);

		if (UtilConfig.subpartFrozen) {
			hitEntity(target);
		}

		return true;
	}

	protected abstract void hitEntity(EntityLivingBase target);

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		stack.damageItem(1, entityLiving);
		return true;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return blockIn.getBlock() == Blocks.WEB;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6.0D, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.8000000953674316D, 0));
		}

		return multimap;
	}

	protected void particles(World world, BlockPos pos, Random rand) {
		world.spawnParticle(EnumParticleTypes.REDSTONE, (pos.getX() + rand.nextDouble() * 16D) - rand.nextDouble() * 16D, (pos.getY() + rand.nextDouble() * 16D) - rand.nextDouble() * 16D, (pos.getZ() + rand.nextDouble() * 16D) - rand.nextDouble() * 16D, 0.29999999999999999D, 0.40000000000000002D, 1.0D);
	}
	
	protected float getDistance(int i, int j, int k, int l) {
		float f = MathHelper.abs(i - k);
		float f1 = MathHelper.abs(j - l);
		return MathHelper.sqrt(f * f + f1 * f1);
	}
}