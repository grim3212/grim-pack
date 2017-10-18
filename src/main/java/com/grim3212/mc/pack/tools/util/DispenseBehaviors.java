package com.grim3212.mc.pack.tools.util;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.entity.EntityBallisticKnife;
import com.grim3212.mc.pack.tools.entity.EntityDetonator;
import com.grim3212.mc.pack.tools.entity.EntityKnife;
import com.grim3212.mc.pack.tools.entity.EntitySlimeSpear;
import com.grim3212.mc.pack.tools.entity.EntitySpear;
import com.grim3212.mc.pack.tools.entity.EntityTomahawk;
import com.grim3212.mc.pack.tools.items.ItemDetonator;
import com.grim3212.mc.pack.tools.items.ItemSpear;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DispenseBehaviors {

	public static void register() {

		if (ToolsConfig.subpartSpears) {
			GrimLog.info(GrimTools.partName, "Registering spears dispenser behaviors");

			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.spear, new SpearDispenseBehavior());
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.iron_spear, new SpearDispenseBehavior());
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.diamond_spear, new SpearDispenseBehavior());
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.explosive_spear, new SpearDispenseBehavior());
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.fire_spear, new SpearDispenseBehavior());
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.light_spear, new SpearDispenseBehavior());
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.lightning_spear, new SpearDispenseBehavior());
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.slime_spear, new BehaviorProjectileDispense() {
				@Override
				protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
					EntitySlimeSpear spear = new EntitySlimeSpear(worldIn, position.getX(), position.getY(), position.getZ());
					spear.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
					return spear;
				}
			});
		}

		if (ToolsConfig.subpartSpears) {
			GrimLog.info(GrimTools.partName, "Registering detonators dispenser behaviors");

			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.thermal_detonator, new DetonatorDispenseBehavior());
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.solar_detonator, new DetonatorDispenseBehavior());
			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.nukeulator, new DetonatorDispenseBehavior());
		}

		if (ToolsConfig.subpartKnives) {
			GrimLog.info(GrimTools.partName, "Registering knives dispenser behaviors");

			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.throwing_knife, new BehaviorProjectileDispense() {
				@Override
				protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
					EntityKnife knife = new EntityKnife(worldIn, position.getX(), position.getY(), position.getZ());
					knife.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
					return knife;
				}
			});

			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.tomahawk, new BehaviorProjectileDispense() {
				@Override
				protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
					EntityTomahawk tomahawk = new EntityTomahawk(worldIn, position.getX(), position.getY(), position.getZ());
					tomahawk.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
					return tomahawk;
				}
			});

			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ToolsItems.ammo_part, new BehaviorProjectileDispense() {
				@Override
				protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
					EntityBallisticKnife knife = new EntityBallisticKnife(worldIn, position.getX(), position.getY(), position.getZ());
					knife.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
					return knife;
				}
			});
		}
	}

	private static class SpearDispenseBehavior extends BehaviorProjectileDispense {
		@Override
		protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
			EnumSpearType type = EnumSpearType.STONE;

			if (stackIn.getItem() instanceof ItemSpear)
				type = ((ItemSpear) stackIn.getItem()).getType();

			EntitySpear spear = new EntitySpear(worldIn, position.getX(), position.getY(), position.getZ(), type);
			spear.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
			return spear;
		}
	}

	private static class DetonatorDispenseBehavior extends BehaviorProjectileDispense {
		@Override
		protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
			EnumDetonatorType type = EnumDetonatorType.SOLAR;

			if (stackIn.getItem() instanceof ItemDetonator)
				type = ((ItemDetonator) stackIn.getItem()).getType();

			EntityDetonator detonator = new EntityDetonator(worldIn, position.getX(), position.getY(), position.getZ(), type);
			return detonator;
		}
	}
}
