package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.common.CommonItems;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.ItemManualArmor;
import com.grim3212.mc.pack.core.item.ItemManualAxe;
import com.grim3212.mc.pack.core.item.ItemManualHoe;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.item.ItemManualPickaxe;
import com.grim3212.mc.pack.core.item.ItemManualSpade;
import com.grim3212.mc.pack.core.item.ItemManualSword;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.block.BlockSiding.EnumSidingColor;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class IndustryItems {

	public static final ToolMaterial steel = Utils.addToolMaterial(IndustryConfig.steelToolMaterial);
	public static final ArmorMaterial antiRadiation = Utils.addArmorMaterial(GrimPack.modID + ":radiation", SoundEvents.BLOCK_CLOTH_PLACE, IndustryConfig.antiRadiationArmorMaterial);
	public static final ArmorMaterial gravboots = Utils.addArmorMaterial(GrimPack.modID + ":gravity", SoundEvents.ITEM_ARMOR_EQUIP_IRON, IndustryConfig.gravityArmorMaterial);

	@ObjectHolder(IndustryNames.GRAVITY_BOOTS)
	public static Item gravity_boots;
	@ObjectHolder(IndustryNames.URANIUM_INGOT)
	public static Item uranium_ingot;
	@ObjectHolder(IndustryNames.REFINED_URANIUM)
	public static Item refined_uranium;
	@ObjectHolder(IndustryNames.PLUTONIUM_INGOT)
	public static Item plutonium_ingot;
	@ObjectHolder(IndustryNames.REFINED_PLUTONIUM)
	public static Item refined_plutonium;
	@ObjectHolder(IndustryNames.REACTOR_CORE)
	public static Item reactor_core;
	@ObjectHolder(IndustryNames.REACTOR_CORE_CASE)
	public static Item reactor_core_case;
	@ObjectHolder(IndustryNames.IRON_PARTS)
	public static Item iron_parts;
	@ObjectHolder(IndustryNames.LOW_GRAVITY_CONTROLLER)
	public static Item low_gravity_controller;
	@ObjectHolder(IndustryNames.GRAVITY_CONTROLLER)
	public static Item gravity_controller;
	@ObjectHolder(IndustryNames.MOB_REPULSOR)
	public static Item mob_repulsor;
	@ObjectHolder(IndustryNames.ANTI_RADIATION_HELMET)
	public static Item anti_radiation_helmet;
	@ObjectHolder(IndustryNames.ANTI_RADIATION_CHEST)
	public static Item anti_radiation_chest;
	@ObjectHolder(IndustryNames.ANTI_RADIATION_LEGS)
	public static Item anti_radiation_legs;
	@ObjectHolder(IndustryNames.ANTI_RADIATION_BOOTS)
	public static Item anti_radiation_boots;
	@ObjectHolder(IndustryNames.GATE_GRATING)
	public static Item gate_grating;
	@ObjectHolder(IndustryNames.GATE_TRUMPET)
	public static Item gate_trumpet;
	@ObjectHolder(IndustryNames.GARAGE_PANEL)
	public static Item garage_panel;
	@ObjectHolder(IndustryNames.GARAGE_REMOTE)
	public static Item garage_remote;
	@ObjectHolder(IndustryNames.PAINT_ROLLER)
	public static Item paint_roller;
	@ObjectHolder(IndustryNames.TARBALL)
	public static Item tarball;
	@ObjectHolder(IndustryNames.ASPHALT)
	public static Item asphalt;
	@ObjectHolder(IndustryNames.PAINT_ROLLER_WHITE)
	public static Item paint_roller_white;
	@ObjectHolder(IndustryNames.PAINT_ROLLER_RED)
	public static Item paint_roller_red;
	@ObjectHolder(IndustryNames.PAINT_ROLLER_GREEN)
	public static Item paint_roller_green;
	@ObjectHolder(IndustryNames.PAINT_ROLLER_BLUE)
	public static Item paint_roller_blue;
	@ObjectHolder(IndustryNames.COAL_IRON_INGOT)
	public static Item coal_iron_ingot;
	@ObjectHolder(IndustryNames.CRUDE_OIL)
	public static Item crude_oil;
	@ObjectHolder(IndustryNames.FUEL)
	public static Item fuel;
	@ObjectHolder(IndustryNames.OILY_CHUNK)
	public static Item oily_chunk;
	@ObjectHolder(IndustryNames.STEEL_AXE)
	public static Item steel_axe;
	@ObjectHolder(IndustryNames.STEEL_HOE)
	public static Item steel_hoe;
	@ObjectHolder(IndustryNames.STEEL_PICKAXE)
	public static Item steel_pickaxe;
	@ObjectHolder(IndustryNames.STEEL_SHOVEL)
	public static Item steel_shovel;
	@ObjectHolder(IndustryNames.STEEL_SWORD)
	public static Item steel_sword;
	@ObjectHolder(IndustryNames.SUPER_CRUDE_OIL)
	public static Item super_crude_oil;
	@ObjectHolder(IndustryNames.DRILL_HEAD_ITEM)
	public static Item drill_head_item;
	@ObjectHolder(IndustryNames.EXTRUDER)
	public static Item extruder;
	@ObjectHolder(IndustryNames.PORTABLE_DIAMOND_WORKBENCH)
	public static Item portable_diamond_workbench;
	@ObjectHolder(IndustryNames.PORTABLE_IRON_WORKBENCH)
	public static Item portable_iron_workbench;
	@ObjectHolder(IndustryNames.POSITION_FINDER)
	public static Item position_finder;
	@ObjectHolder(IndustryNames.LOCKSMITH_LOCK)
	public static Item locksmith_lock;
	@ObjectHolder(IndustryNames.LOCKSMITH_KEY)
	public static Item locksmith_key;

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (IndustryConfig.subpartStorage.get()) {
			r.register(new ItemCombination(IndustryNames.LOCKSMITH_LOCK));
			r.register(new ItemCombination(IndustryNames.LOCKSMITH_KEY));
		}

		if (IndustryConfig.subpartMachines.get()) {
			r.register((new ItemManualPage(IndustryNames.CRUDE_OIL, "industry:machines.refinery_recipes")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.SUPER_CRUDE_OIL, "industry:machines.refinery_recipes")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.FUEL, "industry:machines.refinery_recipes")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.OILY_CHUNK, "industry:metalworks.fuel")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.DRILL_HEAD_ITEM, "industry:machines.drill")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
		}

		if (IndustryConfig.subpartGates.get()) {
			r.register((new ItemManualPage(IndustryNames.GATE_GRATING, "industry:gates.gate")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register(new ItemActivator(IndustryNames.GATE_TRUMPET));
			r.register((new ItemManualPage(IndustryNames.GARAGE_PANEL, "industry:gates.garage")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register(new ItemActivator(IndustryNames.GARAGE_REMOTE));
		}

		if (IndustryConfig.subpartRWays.get()) {
			r.register((new ItemManualPage(IndustryNames.TARBALL, "industry:tarball.paint")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register(new ItemAsphalt());
		}

		if (IndustryConfig.subpartDecoration.get()) {
			r.register((new ItemManualPage(IndustryNames.PAINT_ROLLER, "industry:rways.paint")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY).setMaxStackSize(1).setFull3D());
			r.register(new ItemPaintRollerColor(EnumSidingColor.white));
			r.register(new ItemPaintRollerColor(EnumSidingColor.red));
			r.register(new ItemPaintRollerColor(EnumSidingColor.green));
			r.register(new ItemPaintRollerColor(EnumSidingColor.blue));
		}

		if (IndustryConfig.subpartExtruder.get())
			r.register(new ItemExtruder());

		if (IndustryConfig.subpartWorkbenchUpgrades.get()) {
			r.register(new ItemUpgradedPortableWorkbench(PackGuiHandler.PORTABLE_DIAMOND_MAIN_GUI_ID, PackGuiHandler.PORTABLE_DIAMOND_OFF_GUI_ID));
			r.register(new ItemUpgradedPortableWorkbench(PackGuiHandler.PORTABLE_IRON_MAIN_GUI_ID, PackGuiHandler.PORTABLE_IRON_OFF_GUI_ID));
		}

		if (IndustryConfig.subpartSensors.get())
			r.register(new ItemPositionFinder());

		if (IndustryConfig.subpartSteel.get()) {
			r.register((new ItemManualAxe(IndustryNames.STEEL_AXE, steel, 8.0f, -3.1f, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualHoe(IndustryNames.STEEL_HOE, steel, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPickaxe(IndustryNames.STEEL_PICKAXE, steel, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualSpade(IndustryNames.STEEL_SHOVEL, steel, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualSword(IndustryNames.STEEL_SWORD, steel, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.COAL_IRON_INGOT, "industry:metalworks.coaliron")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));

			if (CoreConfig.subpartSteel.get())
				steel.setRepairItem(new ItemStack(CommonItems.steel_ingot));
		}

		if (IndustryConfig.subpartNuclear.get()) {
			r.register((new ItemManualArmor(IndustryNames.ANTI_RADIATION_BOOTS, antiRadiation, 3, EquipmentSlotType.FEET, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualArmor(IndustryNames.ANTI_RADIATION_CHEST, antiRadiation, 3, EquipmentSlotType.CHEST, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualArmor(IndustryNames.ANTI_RADIATION_HELMET, antiRadiation, 3, EquipmentSlotType.HEAD, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualArmor(IndustryNames.ANTI_RADIATION_LEGS, antiRadiation, 3, EquipmentSlotType.LEGS, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.IRON_PARTS, "industry:reactor.iron_parts")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.REACTOR_CORE_CASE, "industry:reactor.reactor_case")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.REACTOR_CORE, "industry:reactor.reactor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.REFINED_PLUTONIUM, "industry:refining.refined_plutonium")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.PLUTONIUM_INGOT, "industry:refining.plutonium")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.REFINED_URANIUM, "industry:refining.refined_uranium")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register((new ItemManualPage(IndustryNames.URANIUM_INGOT, "industry:refining.uranium_smelt")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));

			antiRadiation.setRepairItem(new ItemStack(Blocks.WOOL));
		}

		if (IndustryConfig.subpartGravity.get()) {
			r.register((new ItemManualArmor(IndustryNames.GRAVITY_BOOTS, gravboots, 4, EquipmentSlotType.FEET, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register(new ItemGravityController());
			r.register(new ItemLowGravityController());
			r.register(new ItemMobRepulsor());

			gravboots.setRepairItem(new ItemStack(Items.IRON_INGOT));
		}
	}
}