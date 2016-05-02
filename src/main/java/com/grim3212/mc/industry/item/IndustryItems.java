package com.grim3212.mc.industry.item;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.core.item.AxeItem;
import com.grim3212.mc.core.item.PickaxeItem;
import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.industry.GrimIndustry;
import com.grim3212.mc.industry.block.BlockSiding.EnumSidingColor;
import com.grim3212.mc.industry.block.IndustryBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
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

	public static ToolMaterial steel = EnumHelper.addToolMaterial("steel", 3, 1200, 7.5F, 2.5F, 12);
	public static ArmorMaterial antiRadiation = EnumHelper.addArmorMaterial("antiRadiation", GrimIndustry.modID + ":radiation", 5, new int[] { 2, 5, 3, 1 }, 15);
	public static ArmorMaterial gravboots = EnumHelper.addArmorMaterial("gravityboots", GrimIndustry.modID + ":gravity", 15, new int[] { 2, 6, 5, 2 }, 9);

	@Override
	public void initItems() {
		rubber = (new Item()).setUnlocalizedName("rubber").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		aluminum_can = (new Item()).setUnlocalizedName("aluminum_can").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		aluminum_shaft = (new Item()).setUnlocalizedName("aluminum_shaft").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		coal_dust = (new Item()).setUnlocalizedName("coal_dust").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		coal_iron_ingot = (new Item()).setUnlocalizedName("coal_iron_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		crude_oil = (new Item()).setUnlocalizedName("crude_oil").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		fuel = (new Item()).setUnlocalizedName("fuel").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		oily_chunk = (new Item()).setUnlocalizedName("oily_chunk").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		super_crude_oil = (new Item()).setUnlocalizedName("super_crude_oil").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_ingot = (new Item()).setUnlocalizedName("steel_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_shaft = (new Item()).setUnlocalizedName("steel_shaft").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_pickaxe = (new PickaxeItem(steel)).setUnlocalizedName("steel_pickaxe").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_axe = (new AxeItem(steel)).setUnlocalizedName("steel_axe").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_hoe = (new ItemHoe(steel)).setUnlocalizedName("steel_hoe").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_shovel = (new ItemSpade(steel)).setUnlocalizedName("steel_shovel").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_sword = (new ItemSword(steel)).setUnlocalizedName("steel_sword").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		door_chain_item = (ItemModernDoor) (new ItemModernDoor(IndustryBlocks.door_chain)).setUnlocalizedName("door_chain_item");
		door_glass_item = (ItemModernDoor) (new ItemModernDoor(IndustryBlocks.door_glass)).setUnlocalizedName("door_glass_item");
		door_steel_item = (ItemModernDoor) (new ItemModernDoor(IndustryBlocks.door_steel)).setUnlocalizedName("door_steel_item");
		gravity_boots = (new ItemArmor(gravboots, 4, 3)).setUnlocalizedName("gravity_boots").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		anti_radiation_boots = (new ItemArmor(antiRadiation, 3, 3)).setUnlocalizedName("anti_radiation_boots").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		anti_radiation_legs = (new ItemArmor(antiRadiation, 3, 2)).setUnlocalizedName("anti_radiation_legs").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		anti_radiation_chest = (new ItemArmor(antiRadiation, 3, 1)).setUnlocalizedName("anti_radiation_chest").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		anti_radiation_helmet = (new ItemArmor(antiRadiation, 3, 0)).setUnlocalizedName("anti_radiation_helmet").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		iron_parts = (new Item()).setUnlocalizedName("iron_parts").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		reactor_core_case = (new Item()).setUnlocalizedName("reactor_core_case").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		reactor_core = (new Item()).setUnlocalizedName("reactor_core").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		graphite_rod = (new Item()).setUnlocalizedName("graphite_rod").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		graphite = (new Item()).setUnlocalizedName("graphite").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		refined_plutonium = (new Item()).setUnlocalizedName("refined_plutonium").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		plutonium_ingot = (new Item()).setUnlocalizedName("plutonium_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		refined_uranium = (new Item()).setUnlocalizedName("refined_uranium").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		uranium_ingot = (new Item()).setUnlocalizedName("uranium_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		aluminum_ingot = (new Item()).setUnlocalizedName("aluminum_ingot").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		gravity_controller = new ItemGravityController().setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("gravity_controller").setMaxStackSize(1);
		low_gravity_controller = new ItemLowGravityController().setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("low_gravity_controller").setMaxStackSize(1);
		gate_grating = new Item().setUnlocalizedName("gate_grating").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		gate_trumpet = new ItemActivator().setUnlocalizedName("gate_trumpet").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		garage_panel = new Item().setUnlocalizedName("garage_panel").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		garage_remote = new ItemActivator().setUnlocalizedName("garage_remote").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		paint_roller = new Item().setUnlocalizedName("paint_roller").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setMaxStackSize(1).setFull3D();
		tarball = (new Item()).setUnlocalizedName("tarball").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		asphalt = (new ItemAsphalt()).setUnlocalizedName("asphalt").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		paint_roller_white = (ItemPaintRollerColor) (new ItemPaintRollerColor(EnumSidingColor.white)).setUnlocalizedName("paint_roller_white");
		paint_roller_red = (ItemPaintRollerColor) (new ItemPaintRollerColor(EnumSidingColor.red)).setUnlocalizedName("paint_roller_red");
		paint_roller_green = (ItemPaintRollerColor) (new ItemPaintRollerColor(EnumSidingColor.green)).setUnlocalizedName("paint_roller_green");
		paint_roller_blue = (ItemPaintRollerColor) (new ItemPaintRollerColor(EnumSidingColor.blue)).setUnlocalizedName("paint_roller_blue");

		GameRegistry.registerItem(rubber, "rubber");
		GameRegistry.registerItem(aluminum_can, "aluminum_can");
		GameRegistry.registerItem(aluminum_shaft, "aluminum_shaft");
		GameRegistry.registerItem(coal_dust, "coal_dust");
		GameRegistry.registerItem(coal_iron_ingot, "coal_iron_ingot");
		GameRegistry.registerItem(crude_oil, "crude_oil");
		GameRegistry.registerItem(fuel, "fuel");
		GameRegistry.registerItem(oily_chunk, "oily_chunk");
		GameRegistry.registerItem(steel_ingot, "steel_ingot");
		GameRegistry.registerItem(steel_shaft, "steel_shaft");
		GameRegistry.registerItem(super_crude_oil, "super_crude_oil");
		GameRegistry.registerItem(steel_pickaxe, "steel_pickaxe");
		GameRegistry.registerItem(steel_axe, "steel_axe");
		GameRegistry.registerItem(steel_hoe, "steel_hoe");
		GameRegistry.registerItem(steel_shovel, "steel_shovel");
		GameRegistry.registerItem(steel_sword, "steel_sword");
		GameRegistry.registerItem(paint_roller_white, "paint_roller_white");
		GameRegistry.registerItem(paint_roller_red, "paint_roller_red");
		GameRegistry.registerItem(paint_roller_green, "paint_roller_green");
		GameRegistry.registerItem(paint_roller_blue, "paint_roller_blue");
		GameRegistry.registerItem(paint_roller, "paint_roller");
		GameRegistry.registerItem(door_chain_item, "door_chain_item");
		GameRegistry.registerItem(door_glass_item, "door_glass_item");
		GameRegistry.registerItem(door_steel_item, "door_steel_item");
		GameRegistry.registerItem(tarball, "tarball");
		GameRegistry.registerItem(asphalt, "asphalt");
		GameRegistry.registerItem(gate_grating, "gate_grating");
		GameRegistry.registerItem(gate_trumpet, "gate_trumpet");
		GameRegistry.registerItem(garage_panel, "garage_panel");
		GameRegistry.registerItem(garage_remote, "garage_remote");
		GameRegistry.registerItem(gravity_controller, "gravity_controller");
		GameRegistry.registerItem(low_gravity_controller, "low_gravity_controller");
		GameRegistry.registerItem(gravity_boots, "gravity_boots");
		GameRegistry.registerItem(anti_radiation_boots, "anti_radiation_boots");
		GameRegistry.registerItem(anti_radiation_legs, "anti_radiation_legs");
		GameRegistry.registerItem(anti_radiation_chest, "anti_radiation_chest");
		GameRegistry.registerItem(anti_radiation_helmet, "anti_radiation_helmet");
		GameRegistry.registerItem(iron_parts, "iron_parts");
		GameRegistry.registerItem(reactor_core_case, "reactor_core_case");
		GameRegistry.registerItem(reactor_core, "reactor_core");
		GameRegistry.registerItem(graphite_rod, "graphite_rod");
		GameRegistry.registerItem(graphite, "graphite");
		GameRegistry.registerItem(refined_plutonium, "refined_plutonium");
		GameRegistry.registerItem(plutonium_ingot, "plutonium_ingot");
		GameRegistry.registerItem(aluminum_ingot, "aluminum_ingot");
		GameRegistry.registerItem(refined_uranium, "refined_uranium");
		GameRegistry.registerItem(uranium_ingot, "uranium_ingot");
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

		GameRegistry.addSmelting(tarball, new ItemStack(asphalt, 1), 0.35F);
		GameRegistry.addSmelting(Items.flint, new ItemStack(graphite), 0.35F);
		GameRegistry.addSmelting(Items.iron_ingot, new ItemStack(aluminum_ingot), 0.35F);
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

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(coal_dust, 2), new Object[] { "c", 'c', Items.coal }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(coal_iron_ingot, 1), new Object[] { "dustCoal", "ingotIron" }));
		coaliron = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(door_chain_item, 1), new Object[] { "ss", "ss", "ss", 's', aluminum_shaft }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(door_glass_item, 1), new Object[] { "ss", "ss", "ss", 's', "blockGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(door_steel_item, 1), new Object[] { "ss", "ss", "ss", 's', "ingotSteel" }));
		doors = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_white, Items.water_bucket }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_red, Items.water_bucket }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_green, Items.water_bucket }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_blue, Items.water_bucket }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller_white, 1), new Object[] { paint_roller, "dyeWhite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller_red, 1), new Object[] { paint_roller, "dyeRed" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller_green, 1), new Object[] { paint_roller, "dyeGreen" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(paint_roller_blue, 1), new Object[] { paint_roller, "dyeBlue" }));
		paint = RecipeHelper.getLatestIRecipes(4);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tarball, 16), new Object[] { "C", "G", "W", 'C', Items.coal, 'G', Blocks.gravel, 'W', Items.water_bucket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paint_roller, 1), new Object[] { "WWW", " S ", " S ", 'W', Blocks.wool, 'S', "stickWood" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gate_grating, 4), new Object[] { " i ", "iii", " i ", 'i', "ingotIron" }));
		gates.add(RecipeHelper.getLatestIRecipe());
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gate_trumpet, 1), new Object[] { "g  ", "wg ", " wi", 'w', Blocks.wool, 'g', "ingotGold", 'i', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(garage_panel, 4), new Object[] { "i", "G", "i", 'i', "ingotIron", 'G', "blockGlass" }));
		gates.add(RecipeHelper.getLatestIRecipe());
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(garage_remote, 1), new Object[] { "b", "r", "i", 'b', Blocks.stone_button, 'r', "dustRedstone", 'i', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gravity_boots, 1), new Object[] { "S S", "M M", 'M', IndustryBlocks.attractor, 'S', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(anti_radiation_helmet, 1), new Object[] { "WWW", "WGW", "I I", 'W', Blocks.wool, 'G', "blockGlassColorless", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(anti_radiation_chest, 1), new Object[] { "W W", "WWW", "III", 'W', Blocks.wool, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(anti_radiation_legs, 1), new Object[] { "WWW", "W W", "I I", 'W', Blocks.wool, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(anti_radiation_boots, 1), new Object[] { "W W", "I I", 'W', Blocks.wool, 'I', "ingotIron" }));
		armor = RecipeHelper.getLatestIRecipes(4);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refined_uranium, 1), new Object[] { " U ", "U U", 'U', "ingotUranium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plutonium_ingot, 1), new Object[] { "U  ", " G ", "  U", 'U', "ingotRefinedUranium", 'G', "glowstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refined_plutonium, 1), new Object[] { "P P", " P ", "P P", 'P', "ingotPlutonium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(graphite_rod, 1), new Object[] { "GGG", "GGG", "GGG", 'G', graphite }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(reactor_core, 1), new Object[] { "UUU", "UPU", "UUU", 'U', "ingotRefinedUranium", 'P', "ingotRefinedPlutonium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(reactor_core_case, 1), new Object[] { " G ", " G ", " G ", 'G', graphite_rod }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_parts, 1), new Object[] { "III", " I ", 'I', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gravity_controller, 1), new Object[] { "EGE", "GDG", "EGE", 'G', "ingotGold", 'E', Items.ender_eye, 'D', "gemDiamond" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(low_gravity_controller, 1), new Object[] { "EGE", "GDG", "EGE", 'G', "ingotGold", 'E', "enderpearl", 'D', "gemDiamond" }));
		control = RecipeHelper.getLatestIRecipes(2);
	}

}
