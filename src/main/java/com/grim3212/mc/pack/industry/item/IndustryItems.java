package com.grim3212.mc.pack.industry.item;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.item.ItemManualAxe;
import com.grim3212.mc.pack.core.item.ItemManualHoe;
import com.grim3212.mc.pack.core.item.ItemManualArmor;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.item.ItemManualPickaxe;
import com.grim3212.mc.pack.core.item.ItemManualSpade;
import com.grim3212.mc.pack.core.item.ItemManualSword;
import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.block.BlockSiding.EnumSidingColor;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class IndustryItems implements IPartItems {

	public static Item aluminum_ingot;
	public static Item gravity_boots;
	public static Item uranium_ingot;
	public static Item refined_uranium;
	public static Item plutonium_ingot;
	public static Item refined_plutonium;
	public static Item graphite;
	public static Item graphite_rod;
	public static Item reactor_core;
	public static Item reactor_core_case;
	public static Item iron_parts;
	public static Item low_gravity_controller;
	public static Item gravity_controller;
	public static Item anti_radiation_helmet;
	public static Item anti_radiation_chest;
	public static Item anti_radiation_legs;
	public static Item anti_radiation_boots;
	public static Item gate_grating;
	public static Item gate_trumpet;
	public static Item garage_panel;
	public static Item garage_remote;
	public static Item paint_roller;
	public static Item tarball;
	public static Item asphalt;
	public static ItemModernDoor door_chain_item;
	public static ItemModernDoor door_glass_item;
	public static ItemModernDoor door_steel_item;
	public static ItemPaintRollerColor paint_roller_white;
	public static ItemPaintRollerColor paint_roller_red;
	public static ItemPaintRollerColor paint_roller_green;
	public static ItemPaintRollerColor paint_roller_blue;
	public static Item aluminum_can;
	public static Item aluminum_shaft;
	public static Item steel_shaft;
	public static Item coal_dust;
	public static Item coal_iron_ingot;
	public static Item crude_oil;
	public static Item fuel;
	public static Item oily_chunk;
	public static Item steel_axe;
	public static Item steel_hoe;
	public static Item steel_ingot;
	public static Item steel_pickaxe;
	public static Item steel_shovel;
	public static Item steel_sword;
	public static Item super_crude_oil;
	public static Item rubber;
	public static Item iron_stick;

	public static ToolMaterial steel = EnumHelper.addToolMaterial("steel", 3, 1200, 7.5F, 2.5F, 12);
	public static ArmorMaterial antiRadiation = EnumHelper.addArmorMaterial("antiRadiation", GrimPack.modID + ":radiation", 5, new int[] { 2, 5, 3, 1 }, 15, SoundEvents.BLOCK_CLOTH_PLACE, 0.0F);
	public static ArmorMaterial gravboots = EnumHelper.addArmorMaterial("gravityboots", GrimPack.modID + ":gravity", 15, new int[] { 2, 6, 5, 2 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

	@Override
	public void initItems() {
		iron_stick = (new ItemManualPage("industry:gates.iron_stick")).setUnlocalizedName("iron_stick").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		rubber = (new ItemManualPage("industry:machines.refinery_recipes")).setUnlocalizedName("rubber").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		aluminum_can = (new ItemManualPage("industry:explosives.aluminium")).setUnlocalizedName("aluminum_can").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		aluminum_shaft = (new ItemManualPage("industry:explosives.aluminium")).setUnlocalizedName("aluminum_shaft").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		coal_dust = (new ItemManualPage("industry:metalworks.coaliron")).setUnlocalizedName("coal_dust").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		coal_iron_ingot = (new ItemManualPage("industry:metalworks.coaliron")).setUnlocalizedName("coal_iron_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		crude_oil = (new ItemManualPage("industry:machines.refinery_recipes")).setUnlocalizedName("crude_oil").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		fuel = (new ItemManualPage("industry:machines.refinery_recipes")).setUnlocalizedName("fuel").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		oily_chunk = (new ItemManualPage("industry:metalworks.fuel")).setUnlocalizedName("oily_chunk").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		super_crude_oil = (new ItemManualPage("industry:machines.refinery_recipes")).setUnlocalizedName("super_crude_oil").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_ingot = (new ItemManualPage("industry:metalworks.steelingot")).setUnlocalizedName("steel_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_shaft = (new ItemManualPage("industry:metalworks.steelstuff")).setUnlocalizedName("steel_shaft").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_pickaxe = (new ItemManualPickaxe(steel, "industry:metalworks.steeltools")).setUnlocalizedName("steel_pickaxe").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_axe = (new ItemManualAxe(steel, 8.0f, -3.1f, "industry:metalworks.steeltools")).setUnlocalizedName("steel_axe").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_hoe = (new ItemManualHoe(steel, "industry:metalworks.steeltools")).setUnlocalizedName("steel_hoe").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_shovel = (new ItemManualSpade(steel, "industry:metalworks.steeltools")).setUnlocalizedName("steel_shovel").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_sword = (new ItemManualSword(steel, "industry:metalworks.steeltools")).setUnlocalizedName("steel_sword").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		door_chain_item = (ItemModernDoor) (new ItemModernDoor(IndustryBlocks.door_chain)).setUnlocalizedName("door_chain_item");
		door_glass_item = (ItemModernDoor) (new ItemModernDoor(IndustryBlocks.door_glass)).setUnlocalizedName("door_glass_item");
		door_steel_item = (ItemModernDoor) (new ItemModernDoor(IndustryBlocks.door_steel)).setUnlocalizedName("door_steel_item");
		gravity_boots = (new ItemManualArmor(gravboots, 4, EntityEquipmentSlot.FEET, "industry:refining.armor")).setUnlocalizedName("gravity_boots").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		anti_radiation_boots = (new ItemManualArmor(antiRadiation, 3, EntityEquipmentSlot.FEET, "industry:refining.armor")).setUnlocalizedName("anti_radiation_boots").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		anti_radiation_legs = (new ItemManualArmor(antiRadiation, 3, EntityEquipmentSlot.LEGS, "industry:refining.armor")).setUnlocalizedName("anti_radiation_legs").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		anti_radiation_chest = (new ItemManualArmor(antiRadiation, 3, EntityEquipmentSlot.CHEST, "industry:refining.armor")).setUnlocalizedName("anti_radiation_chest").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		anti_radiation_helmet = (new ItemManualArmor(antiRadiation, 3, EntityEquipmentSlot.HEAD, "industry:refining.armor")).setUnlocalizedName("anti_radiation_helmet").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		iron_parts = (new ItemManualPage("industry:reactor.iron_parts")).setUnlocalizedName("iron_parts").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		reactor_core_case = (new ItemManualPage("industry:reactor.reactor_case")).setUnlocalizedName("reactor_core_case").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		reactor_core = (new ItemManualPage("industry:reactor.reactor")).setUnlocalizedName("reactor_core").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		graphite_rod = (new ItemManualPage("industry:reactor.graphite_rod")).setUnlocalizedName("graphite_rod").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		graphite = (new ItemManualPage("industry:reactor.graphite")).setUnlocalizedName("graphite").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		refined_plutonium = (new ItemManualPage("industry:refining.refined_plutonium")).setUnlocalizedName("refined_plutonium").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		plutonium_ingot = (new ItemManualPage("industry:refining.plutonium")).setUnlocalizedName("plutonium_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		refined_uranium = (new ItemManualPage("industry:refining.refined_uranium")).setUnlocalizedName("refined_uranium").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		uranium_ingot = (new ItemManualPage("industry:refining.uranium_smelt")).setUnlocalizedName("uranium_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		aluminum_ingot = (new ItemManualPage("industry:explosives.alumingot")).setUnlocalizedName("aluminum_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		gravity_controller = new ItemGravityController().setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("gravity_controller").setMaxStackSize(1);
		low_gravity_controller = new ItemLowGravityController().setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("low_gravity_controller").setMaxStackSize(1);
		gate_grating = new ItemManualPage("industry:gates.gate").setUnlocalizedName("gate_grating").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		gate_trumpet = new ItemActivator().setUnlocalizedName("gate_trumpet").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		garage_panel = new ItemManualPage("industry:gates.garage").setUnlocalizedName("garage_panel").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		garage_remote = new ItemActivator().setUnlocalizedName("garage_remote").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		paint_roller = new ItemManualPage("industry:rways.paint").setUnlocalizedName("paint_roller").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setMaxStackSize(1).setFull3D();
		tarball = (new ItemManualPage("industry:tarball.paint")).setUnlocalizedName("tarball").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		asphalt = (new ItemAsphalt()).setUnlocalizedName("asphalt").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		paint_roller_white = (ItemPaintRollerColor) (new ItemPaintRollerColor(EnumSidingColor.white)).setUnlocalizedName("paint_roller_white");
		paint_roller_red = (ItemPaintRollerColor) (new ItemPaintRollerColor(EnumSidingColor.red)).setUnlocalizedName("paint_roller_red");
		paint_roller_green = (ItemPaintRollerColor) (new ItemPaintRollerColor(EnumSidingColor.green)).setUnlocalizedName("paint_roller_green");
		paint_roller_blue = (ItemPaintRollerColor) (new ItemPaintRollerColor(EnumSidingColor.blue)).setUnlocalizedName("paint_roller_blue");

		Utils.registerItem(iron_stick, "iron_stick");
		Utils.registerItem(rubber, "rubber");
		Utils.registerItem(aluminum_can, "aluminum_can");
		Utils.registerItem(aluminum_shaft, "aluminum_shaft");
		Utils.registerItem(coal_dust, "coal_dust");
		Utils.registerItem(coal_iron_ingot, "coal_iron_ingot");
		Utils.registerItem(crude_oil, "crude_oil");
		Utils.registerItem(fuel, "fuel");
		Utils.registerItem(oily_chunk, "oily_chunk");
		Utils.registerItem(steel_ingot, "steel_ingot");
		Utils.registerItem(steel_shaft, "steel_shaft");
		Utils.registerItem(super_crude_oil, "super_crude_oil");
		Utils.registerItem(steel_pickaxe, "steel_pickaxe");
		Utils.registerItem(steel_axe, "steel_axe");
		Utils.registerItem(steel_hoe, "steel_hoe");
		Utils.registerItem(steel_shovel, "steel_shovel");
		Utils.registerItem(steel_sword, "steel_sword");
		Utils.registerItem(paint_roller_white, "paint_roller_white");
		Utils.registerItem(paint_roller_red, "paint_roller_red");
		Utils.registerItem(paint_roller_green, "paint_roller_green");
		Utils.registerItem(paint_roller_blue, "paint_roller_blue");
		Utils.registerItem(paint_roller, "paint_roller");
		Utils.registerItem(door_chain_item, "door_chain_item");
		Utils.registerItem(door_glass_item, "door_glass_item");
		Utils.registerItem(door_steel_item, "door_steel_item");
		Utils.registerItem(tarball, "tarball");
		Utils.registerItem(asphalt, "asphalt");
		Utils.registerItem(gate_grating, "gate_grating");
		Utils.registerItem(gate_trumpet, "gate_trumpet");
		Utils.registerItem(garage_panel, "garage_panel");
		Utils.registerItem(garage_remote, "garage_remote");
		Utils.registerItem(gravity_controller, "gravity_controller");
		Utils.registerItem(low_gravity_controller, "low_gravity_controller");
		Utils.registerItem(gravity_boots, "gravity_boots");
		Utils.registerItem(anti_radiation_boots, "anti_radiation_boots");
		Utils.registerItem(anti_radiation_legs, "anti_radiation_legs");
		Utils.registerItem(anti_radiation_chest, "anti_radiation_chest");
		Utils.registerItem(anti_radiation_helmet, "anti_radiation_helmet");
		Utils.registerItem(iron_parts, "iron_parts");
		Utils.registerItem(reactor_core_case, "reactor_core_case");
		Utils.registerItem(reactor_core, "reactor_core");
		Utils.registerItem(graphite_rod, "graphite_rod");
		Utils.registerItem(graphite, "graphite");
		Utils.registerItem(refined_plutonium, "refined_plutonium");
		Utils.registerItem(plutonium_ingot, "plutonium_ingot");
		Utils.registerItem(aluminum_ingot, "aluminum_ingot");
		Utils.registerItem(refined_uranium, "refined_uranium");
		Utils.registerItem(uranium_ingot, "uranium_ingot");
	}

	public static List<IRecipe> armor;
	public static List<IRecipe> control;
	public static List<IRecipe> gates = new ArrayList<IRecipe>();
	public static List<IRecipe> garages = new ArrayList<IRecipe>();
	public static List<IRecipe> paint;
	public static List<IRecipe> doors;
	public static List<IRecipe> coaliron;
	public static List<IRecipe> steeltools;
	public static List<IRecipe> alumstuff;
	public static List<IRecipe> fuelstuff = new ArrayList<IRecipe>();

	@Override
	public void addRecipes() {
		OreDictionary.registerOre("rubber", rubber);
		OreDictionary.registerOre("dustCoal", coal_dust);
		OreDictionary.registerOre("ingotSteel", steel_ingot);
		OreDictionary.registerOre("ingotUranium", uranium_ingot);
		OreDictionary.registerOre("ingotRefinedUranium", refined_uranium);
		OreDictionary.registerOre("ingotPlutonium", plutonium_ingot);
		OreDictionary.registerOre("ingotRefinedPlutonium", refined_plutonium);
		OreDictionary.registerOre("ingotAluminum", aluminum_ingot);
		OreDictionary.registerOre("stickIron", iron_stick);
		OreDictionary.registerOre("rodIron", iron_stick);
		OreDictionary.registerOre("rod", iron_stick);

		GameRegistry.addSmelting(tarball, new ItemStack(asphalt, 1), 0.35F);
		GameRegistry.addSmelting(Items.FLINT, new ItemStack(graphite), 0.35F);
		GameRegistry.addSmelting(coal_iron_ingot, new ItemStack(steel_ingot, 1), 0.5F);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aluminum_can, 16), new Object[] { "a a", "a a", "a a", 'a', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aluminum_shaft, 4), new Object[] { "a", "a", 'a', "ingotAluminum" }));
		alumstuff = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(super_crude_oil, 1), new Object[] { "o", "a", 'o', oily_chunk, 'a', aluminum_can }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fuel, 9), new Object[] { "F", 'F', IndustryBlocks.fuel_tank }));
		fuelstuff.addAll(RecipeHelper.getLatestIRecipes(2));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(IndustryItems.steel_shaft, 4), new Object[] { "i", "i", 'i', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(IndustryItems.steel_ingot, 9), new Object[] { "B", 'B', "blockSteel" }));
		IndustryBlocks.steelstuff.addAll(RecipeHelper.getLatestIRecipes(2));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steel_sword, 1), new Object[] { "i", "i", "s", 'i', "ingotSteel", 's', steel_shaft }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steel_pickaxe, 1), new Object[] { "iii", " s ", " s ", 'i', "ingotSteel", 's', steel_shaft }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steel_shovel, 1), new Object[] { "i", "s", "s", 'i', "ingotSteel", 's', steel_shaft }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steel_axe, 1), new Object[] { "ii", "is", " s", 'i', "ingotSteel", 's', steel_shaft }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steel_hoe, 1), new Object[] { "ii", " s", " s", 'i', "ingotSteel", 's', steel_shaft }));
		steeltools = RecipeHelper.getLatestIRecipes(5);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(coal_dust, 2), new Object[] { "c", 'c', Items.COAL }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(coal_iron_ingot, 1), new Object[] { "dustCoal", "ingotIron" }));
		coaliron = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(door_chain_item, 1), new Object[] { "ss", "ss", "ss", 's', aluminum_shaft }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(door_glass_item, 1), new Object[] { "ss", "ss", "ss", 's', "blockGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(door_steel_item, 1), new Object[] { "ss", "ss", "ss", 's', "ingotSteel" }));
		doors = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_white, Items.WATER_BUCKET }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_red, Items.WATER_BUCKET }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_green, Items.WATER_BUCKET }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_blue, Items.WATER_BUCKET }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller_white, 1), new Object[] { paint_roller, "dyeWhite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller_red, 1), new Object[] { paint_roller, "dyeRed" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller_green, 1), new Object[] { paint_roller, "dyeGreen" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller_blue, 1), new Object[] { paint_roller, "dyeBlue" }));
		paint = RecipeHelper.getLatestIRecipes(4);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tarball, 16), new Object[] { "C", "G", "W", 'C', Items.COAL, 'G', Blocks.GRAVEL, 'W', Items.WATER_BUCKET }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paint_roller, 1), new Object[] { "WWW", " S ", " S ", 'W', Blocks.WOOL, 'S', "stickWood" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_stick, 4), new Object[] { " i ", "iSi", " i ", 'i', "ingotIron", 'S', "stickWood" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gate_grating, 4), new Object[] { " i ", "iii", " i ", 'i', "stickIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gate_grating, 4), new Object[] { " i ", "iii", " i ", 'i', "rodIron" }));
		gates.addAll(RecipeHelper.getLatestIRecipes(2));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gate_trumpet, 1), new Object[] { "g  ", "wg ", " wi", 'w', Blocks.WOOL, 'g', "ingotGold", 'i', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(garage_panel, 4), new Object[] { "i", "G", "i", 'i', "ingotIron", 'G', "blockGlass" }));
		gates.add(RecipeHelper.getLatestIRecipe());
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(garage_remote, 1), new Object[] { "b", "r", "i", 'b', Blocks.STONE_BUTTON, 'r', "dustRedstone", 'i', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gravity_boots, 1), new Object[] { "S S", "M M", 'M', IndustryBlocks.attractor, 'S', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(anti_radiation_helmet, 1), new Object[] { "WWW", "WGW", "I I", 'W', Blocks.WOOL, 'G', "blockGlassColorless", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(anti_radiation_chest, 1), new Object[] { "W W", "WWW", "III", 'W', Blocks.WOOL, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(anti_radiation_legs, 1), new Object[] { "WWW", "W W", "I I", 'W', Blocks.WOOL, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(anti_radiation_boots, 1), new Object[] { "W W", "I I", 'W', Blocks.WOOL, 'I', "ingotIron" }));
		armor = RecipeHelper.getLatestIRecipes(4);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refined_uranium, 1), new Object[] { " U ", "U U", 'U', "ingotUranium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plutonium_ingot, 1), new Object[] { "U  ", " G ", "  U", 'U', "ingotRefinedUranium", 'G', "glowstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refined_plutonium, 1), new Object[] { "P P", " P ", "P P", 'P', "ingotPlutonium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(graphite_rod, 1), new Object[] { "GGG", "GGG", "GGG", 'G', graphite }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(reactor_core, 1), new Object[] { "UUU", "UPU", "UUU", 'U', "ingotRefinedUranium", 'P', "ingotRefinedPlutonium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(reactor_core_case, 1), new Object[] { " G ", " G ", " G ", 'G', graphite_rod }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_parts, 1), new Object[] { "III", " I ", 'I', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gravity_controller, 1), new Object[] { "EGE", "GDG", "EGE", 'G', "ingotGold", 'E', Items.ENDER_EYE, 'D', "gemDiamond" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(low_gravity_controller, 1), new Object[] { "EGE", "GDG", "EGE", 'G', "ingotGold", 'E', "enderpearl", 'D', "gemDiamond" }));
		control = RecipeHelper.getLatestIRecipes(2);
	}

}
