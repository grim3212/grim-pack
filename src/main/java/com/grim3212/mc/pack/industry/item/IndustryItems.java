package com.grim3212.mc.pack.industry.item;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.item.ItemManualArmor;
import com.grim3212.mc.pack.core.item.ItemManualAxe;
import com.grim3212.mc.pack.core.item.ItemManualHoe;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.item.ItemManualPickaxe;
import com.grim3212.mc.pack.core.item.ItemManualSpade;
import com.grim3212.mc.pack.core.item.ItemManualSword;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.CreateRecipes;
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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class IndustryItems {

	public static final ToolMaterial steel = EnumHelper.addToolMaterial("steel", 3, 1200, 7.5F, 2.5F, 12);
	public static final ArmorMaterial antiRadiation = EnumHelper.addArmorMaterial("antiRadiation", GrimPack.modID + ":radiation", 5, new int[] { 2, 5, 3, 1 }, 15, SoundEvents.BLOCK_CLOTH_PLACE, 0.0F);
	public static final ArmorMaterial gravboots = EnumHelper.addArmorMaterial("gravityboots", GrimPack.modID + ":gravity", 15, new int[] { 2, 6, 5, 2 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

	public static final Item aluminum_ingot = (new ItemManualPage("aluminum_ingot", "industry:explosives.alumingot")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item gravity_boots = (new ItemManualArmor("gravity_boots", gravboots, 4, EntityEquipmentSlot.FEET, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item uranium_ingot = (new ItemManualPage("uranium_ingot", "industry:refining.uranium_smelt")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item refined_uranium = (new ItemManualPage("refined_uranium", "industry:refining.refined_uranium")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item plutonium_ingot = (new ItemManualPage("plutonium_ingot", "industry:refining.plutonium")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item refined_plutonium = (new ItemManualPage("refined_plutonium", "industry:refining.refined_plutonium")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item graphite = (new ItemManualPage("graphite", "industry:reactor.graphite")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item graphite_rod = (new ItemManualPage("graphite_rod", "industry:reactor.graphite_rod")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item reactor_core = (new ItemManualPage("reactor_core", "industry:reactor.reactor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item reactor_core_case = (new ItemManualPage("reactor_core_case", "industry:reactor.reactor_case")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item iron_parts = (new ItemManualPage("iron_parts", "industry:reactor.iron_parts")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item low_gravity_controller = new ItemLowGravityController();
	public static final Item gravity_controller = new ItemGravityController();
	public static final Item mob_repulsor = new ItemMobRepulsor();
	public static final Item anti_radiation_helmet = (new ItemManualArmor("anti_radiation_helmet", antiRadiation, 3, EntityEquipmentSlot.HEAD, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item anti_radiation_chest = (new ItemManualArmor("anti_radiation_chest", antiRadiation, 3, EntityEquipmentSlot.CHEST, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item anti_radiation_legs = (new ItemManualArmor("anti_radiation_legs", antiRadiation, 3, EntityEquipmentSlot.LEGS, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item anti_radiation_boots = (new ItemManualArmor("anti_radiation_boots", antiRadiation, 3, EntityEquipmentSlot.FEET, "industry:refining.armor")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item gate_grating = (new ItemManualPage("gate_grating", "industry:gates.gate")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item gate_trumpet = new ItemActivator("gate_trumpet");
	public static final Item garage_panel = (new ItemManualPage("garage_panel", "industry:gates.garage")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item garage_remote = new ItemActivator("garage_remote");
	public static final Item paint_roller = (new ItemManualPage("paint_roller", "industry:rways.paint")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY).setMaxStackSize(1).setFull3D();
	public static final Item tarball = (new ItemManualPage("tarball", "industry:tarball.paint")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item asphalt = new ItemAsphalt();
	public static final Item door_chain_item = new ItemModernDoor("door_chain_item", IndustryBlocks.door_chain);
	public static final Item door_glass_item = new ItemModernDoor("door_glass_item", IndustryBlocks.door_glass);
	public static final Item door_steel_item = new ItemModernDoor("door_steel_item", IndustryBlocks.door_steel);
	public static final Item paint_roller_white = new ItemPaintRollerColor(EnumSidingColor.white);
	public static final Item paint_roller_red = new ItemPaintRollerColor(EnumSidingColor.red);
	public static final Item paint_roller_green = new ItemPaintRollerColor(EnumSidingColor.green);
	public static final Item paint_roller_blue = new ItemPaintRollerColor(EnumSidingColor.blue);
	public static final Item aluminum_can = (new ItemManualPage("aluminum_can", "industry:explosives.aluminium")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item aluminum_shaft = (new ItemManualPage("aluminum_shaft", "industry:explosives.aluminium")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item steel_shaft = (new ItemManualPage("steel_shaft", "industry:metalworks.steelstuff")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item coal_dust = (new ItemManualPage("coal_dust", "industry:metalworks.coaliron")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item coal_iron_ingot = (new ItemManualPage("coal_iron_ingot", "industry:metalworks.coaliron")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item crude_oil = (new ItemManualPage("crude_oil", "industry:machines.refinery_recipes")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item fuel = (new ItemManualPage("fuel", "industry:machines.refinery_recipes")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item oily_chunk = (new ItemManualPage("oily_chunk", "industry:metalworks.fuel")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item steel_axe = (new ItemManualAxe("steel_axe", steel, 8.0f, -3.1f, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item steel_hoe = (new ItemManualHoe("steel_hoe", steel, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item steel_ingot = (new ItemManualPage("steel_ingot", "industry:metalworks.steelingot")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item steel_pickaxe = (new ItemManualPickaxe("steel_pickaxe", steel, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item steel_shovel = (new ItemManualSpade("steel_shovel", steel, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item steel_sword = (new ItemManualSword("steel_sword", steel, "industry:metalworks.steeltools")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item super_crude_oil = (new ItemManualPage("super_crude_oil", "industry:machines.refinery_recipes")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item rubber = (new ItemManualPage("rubber", "industry:machines.refinery_recipes")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item iron_stick = (new ItemManualPage("iron_stick", "industry:gates.iron_stick")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item drill_head_item = (new ItemManualPage("drill_head_item", "industry:machines.drill")).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Item extruder = new ItemExtruder();
	public static final Item portable_diamond_workbench = new ItemUpgradedPortableWorkbench(PackGuiHandler.PORTABLE_DIAMOND_MAIN_GUI_ID, PackGuiHandler.PORTABLE_DIAMOND_OFF_GUI_ID);
	public static final Item portable_iron_workbench = new ItemUpgradedPortableWorkbench(PackGuiHandler.PORTABLE_IRON_MAIN_GUI_ID, PackGuiHandler.PORTABLE_IRON_OFF_GUI_ID);
	public static final Item position_finder = new ItemPositionFinder();
	public static final Item locksmith_lock = new ItemCombination("locksmith_lock");
	public static final Item locksmith_key = new ItemCombination("locksmith_key");

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		r.register(locksmith_lock);
		r.register(locksmith_key);
		r.register(aluminum_can);
		r.register(aluminum_shaft);
		r.register(steel_shaft);
		r.register(coal_dust);
		r.register(coal_iron_ingot);
		r.register(crude_oil);
		r.register(super_crude_oil);
		r.register(fuel);
		r.register(oily_chunk);
		r.register(steel_axe);
		r.register(steel_hoe);
		r.register(steel_ingot);
		r.register(steel_pickaxe);
		r.register(steel_shovel);
		r.register(steel_sword);
		r.register(rubber);
		r.register(iron_stick);
		r.register(door_chain_item);
		r.register(door_glass_item);
		r.register(door_steel_item);
		r.register(gravity_boots);
		r.register(anti_radiation_boots);
		r.register(anti_radiation_chest);
		r.register(anti_radiation_helmet);
		r.register(anti_radiation_legs);
		r.register(iron_parts);
		r.register(reactor_core_case);
		r.register(reactor_core);
		r.register(graphite_rod);
		r.register(graphite);
		r.register(refined_plutonium);
		r.register(plutonium_ingot);
		r.register(refined_uranium);
		r.register(uranium_ingot);
		r.register(aluminum_ingot);
		r.register(gravity_controller);
		r.register(low_gravity_controller);
		r.register(mob_repulsor);
		r.register(gate_grating);
		r.register(gate_trumpet);
		r.register(garage_panel);
		r.register(garage_remote);
		r.register(paint_roller);
		r.register(tarball);
		r.register(asphalt);
		r.register(paint_roller_white);
		r.register(paint_roller_blue);
		r.register(paint_roller_green);
		r.register(paint_roller_red);
		r.register(extruder);
		r.register(portable_diamond_workbench);
		r.register(portable_iron_workbench);
		r.register(position_finder);
		r.register(drill_head_item);

		steel.setRepairItem(new ItemStack(steel_ingot));
		antiRadiation.setRepairItem(new ItemStack(Blocks.WOOL));
		gravboots.setRepairItem(new ItemStack(Items.IRON_INGOT));

		initOreDict();
	}

	private static void initOreDict() {
		OreDictionary.registerOre("rubber", rubber);
		OreDictionary.registerOre("dustCoal", coal_dust);
		OreDictionary.registerOre("ingotSteel", steel_ingot);
		OreDictionary.registerOre("ingotUranium", uranium_ingot);
		OreDictionary.registerOre("ingotRefinedUranium", refined_uranium);
		OreDictionary.registerOre("ingotPlutonium", plutonium_ingot);
		OreDictionary.registerOre("ingotRefinedPlutonium", refined_plutonium);
		OreDictionary.registerOre("ingotAluminum", aluminum_ingot);
		OreDictionary.registerOre("stickIron", iron_stick);
		OreDictionary.registerOre("can", aluminum_can);
		OreDictionary.registerOre("canOil", crude_oil);
		OreDictionary.registerOre("canCrudeOil", super_crude_oil);
		OreDictionary.registerOre("canFuel", fuel);
		OreDictionary.registerOre("tar", tarball);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		GameRegistry.addSmelting(tarball, new ItemStack(asphalt, 1), 0.35F);
		GameRegistry.addSmelting(Items.FLINT, new ItemStack(graphite), 0.35F);
		GameRegistry.addSmelting(coal_iron_ingot, new ItemStack(steel_ingot, 1), 0.5F);
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
	public static List<IRecipe> portableUpgrades;
	public static List<IRecipe> fuelstuff = new ArrayList<IRecipe>();

	public static void addRecipes() {

		CreateRecipes.addShapedRecipe(new ItemStack(position_finder, 1), new Object[] { "IWI", " M ", 'W', Items.COMPASS, 'I', "ingotIron", 'M', Items.MAP });
		CreateRecipes.addShapedRecipe(new ItemStack(position_finder, 1), new Object[] { "IWI", " M ", 'W', Items.COMPASS, 'I', "ingotIron", 'M', Items.FILLED_MAP });

		CreateRecipes.addShapedRecipe(new ItemStack(portable_diamond_workbench, 1), new Object[] { "IDI", "DWD", "IDI", 'W', IndustryBlocks.diamond_workbench, 'I', "ingotIron", 'D', "gemDiamond" });
		CreateRecipes.addShapedRecipe(new ItemStack(portable_iron_workbench, 1), new Object[] { "III", "IWI", "III", 'W', IndustryBlocks.iron_workbench, 'I', "ingotIron" });
		// portableUpgrades = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(extruder, 1), new Object[] { "PD", 'D', Blocks.DISPENSER, 'P', Blocks.PISTON });

		CreateRecipes.addShapedRecipe(new ItemStack(aluminum_can, 16), new Object[] { "a a", "a a", "a a", 'a', "ingotAluminum" });
		CreateRecipes.addShapedRecipe(new ItemStack(aluminum_shaft, 4), new Object[] { "a", "a", 'a', "ingotAluminum" });
		// alumstuff = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(super_crude_oil, 1), new Object[] { "o", "a", 'o', oily_chunk, 'a', aluminum_can });
		CreateRecipes.addShapedRecipe(new ItemStack(fuel, 9), new Object[] { "F", 'F', IndustryBlocks.fuel_tank });
		// fuelstuff.addAll(RecipeHelper.getLatestIRecipes(2));

		CreateRecipes.addShapedRecipe(new ItemStack(IndustryItems.steel_shaft, 4), new Object[] { "i", "i", 'i', "ingotSteel" });
		CreateRecipes.addShapedRecipe(new ItemStack(IndustryItems.steel_ingot, 9), new Object[] { "B", 'B', "blockSteel" });
		// IndustryBlocks.steelstuff.addAll(RecipeHelper.getLatestIRecipes(2));

		CreateRecipes.addShapedRecipe(new ItemStack(steel_sword, 1), new Object[] { "i", "i", "s", 'i', "ingotSteel", 's', steel_shaft });
		CreateRecipes.addShapedRecipe(new ItemStack(steel_pickaxe, 1), new Object[] { "iii", " s ", " s ", 'i', "ingotSteel", 's', steel_shaft });
		CreateRecipes.addShapedRecipe(new ItemStack(steel_shovel, 1), new Object[] { "i", "s", "s", 'i', "ingotSteel", 's', steel_shaft });
		CreateRecipes.addShapedRecipe(new ItemStack(steel_axe, 1), new Object[] { "ii", "is", " s", 'i', "ingotSteel", 's', steel_shaft });
		CreateRecipes.addShapedRecipe(new ItemStack(steel_hoe, 1), new Object[] { "ii", " s", " s", 'i', "ingotSteel", 's', steel_shaft });
		// steeltools = RecipeHelper.getLatestIRecipes(5);

		CreateRecipes.addShapedRecipe(new ItemStack(coal_dust, 2), new Object[] { "c", 'c', Items.COAL });
		CreateRecipes.addShapelessRecipe(new ItemStack(coal_iron_ingot, 1), new Object[] { "dustCoal", "ingotIron" });
		// coaliron = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(door_chain_item, 1), new Object[] { "ss", "ss", "ss", 's', aluminum_shaft });
		CreateRecipes.addShapedRecipe(new ItemStack(door_glass_item, 1), new Object[] { "ss", "ss", "ss", 's', "blockGlass" });
		CreateRecipes.addShapedRecipe(new ItemStack(door_steel_item, 1), new Object[] { "ss", "ss", "ss", 's', "ingotSteel" });
		// doors = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapelessRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_white, Items.WATER_BUCKET });
		CreateRecipes.addShapelessRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_red, Items.WATER_BUCKET });
		CreateRecipes.addShapelessRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_green, Items.WATER_BUCKET });
		CreateRecipes.addShapelessRecipe(new ItemStack(paint_roller, 1), new Object[] { paint_roller_blue, Items.WATER_BUCKET });

		CreateRecipes.addShapelessRecipe(new ItemStack(paint_roller_white, 1), new Object[] { paint_roller, "dyeWhite" });
		CreateRecipes.addShapelessRecipe(new ItemStack(paint_roller_red, 1), new Object[] { paint_roller, "dyeRed" });
		CreateRecipes.addShapelessRecipe(new ItemStack(paint_roller_green, 1), new Object[] { paint_roller, "dyeGreen" });
		CreateRecipes.addShapelessRecipe(new ItemStack(paint_roller_blue, 1), new Object[] { paint_roller, "dyeBlue" });
		// paint = RecipeHelper.getLatestIRecipes(4);

		CreateRecipes.addShapedRecipe(new ItemStack(tarball, 16), new Object[] { "C", "G", "W", 'C', Items.COAL, 'G', Blocks.GRAVEL, 'W', Items.WATER_BUCKET });
		CreateRecipes.addShapedRecipe(new ItemStack(paint_roller, 1), new Object[] { "WWW", " S ", " S ", 'W', Blocks.WOOL, 'S', "stickWood" });

		CreateRecipes.addShapedRecipe(new ItemStack(iron_stick, 4), new Object[] { " i ", "iSi", " i ", 'i', "ingotIron", 'S', "stickWood" });

		CreateRecipes.addShapedRecipe(new ItemStack(gate_grating, 4), new Object[] { " i ", "iii", " i ", 'i', "stickIron" });
		// gates.add(RecipeHelper.getLatestIRecipe());
		CreateRecipes.addShapedRecipe(new ItemStack(gate_trumpet, 1), new Object[] { "g  ", "wg ", " wi", 'w', Blocks.WOOL, 'g', "ingotGold", 'i', "ingotIron" });

		CreateRecipes.addShapedRecipe(new ItemStack(garage_panel, 4), new Object[] { "i", "G", "i", 'i', "ingotIron", 'G', "blockGlass" });
		// gates.add(RecipeHelper.getLatestIRecipe());
		CreateRecipes.addShapedRecipe(new ItemStack(garage_remote, 1), new Object[] { "b", "r", "i", 'b', Blocks.STONE_BUTTON, 'r', "dustRedstone", 'i', "ingotIron" });

		CreateRecipes.addShapedRecipe(new ItemStack(gravity_boots, 1), new Object[] { "S S", "M M", 'M', IndustryBlocks.attractor, 'S', "ingotIron" });

		CreateRecipes.addShapedRecipe(new ItemStack(anti_radiation_helmet, 1), new Object[] { "WWW", "WGW", "I I", 'W', Blocks.WOOL, 'G', "blockGlassColorless", 'I', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(anti_radiation_chest, 1), new Object[] { "W W", "WWW", "III", 'W', Blocks.WOOL, 'I', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(anti_radiation_legs, 1), new Object[] { "WWW", "W W", "I I", 'W', Blocks.WOOL, 'I', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(anti_radiation_boots, 1), new Object[] { "W W", "I I", 'W', Blocks.WOOL, 'I', "ingotIron" });
		// armor = RecipeHelper.getLatestIRecipes(4);

		CreateRecipes.addShapedRecipe(new ItemStack(refined_uranium, 1), new Object[] { " U ", "U U", 'U', "ingotUranium" });
		CreateRecipes.addShapedRecipe(new ItemStack(plutonium_ingot, 1), new Object[] { "U  ", " G ", "  U", 'U', "ingotRefinedUranium", 'G', "glowstone" });
		CreateRecipes.addShapedRecipe(new ItemStack(refined_plutonium, 1), new Object[] { "P P", " P ", "P P", 'P', "ingotPlutonium" });
		CreateRecipes.addShapedRecipe(new ItemStack(graphite_rod, 1), new Object[] { "GGG", "GGG", "GGG", 'G', graphite });
		CreateRecipes.addShapedRecipe(new ItemStack(reactor_core, 1), new Object[] { "UUU", "UPU", "UUU", 'U', "ingotRefinedUranium", 'P', "ingotRefinedPlutonium" });
		CreateRecipes.addShapedRecipe(new ItemStack(reactor_core_case, 1), new Object[] { " G ", " G ", " G ", 'G', graphite_rod });
		CreateRecipes.addShapedRecipe(new ItemStack(iron_parts, 1), new Object[] { "III", " I ", 'I', "ingotIron" });

		CreateRecipes.addShapedRecipe(new ItemStack(gravity_controller, 1), new Object[] { "EGE", "GDG", "EGE", 'G', "ingotGold", 'E', Items.ENDER_EYE, 'D', "gemDiamond" });
		CreateRecipes.addShapedRecipe(new ItemStack(low_gravity_controller, 1), new Object[] { "EGE", "GDG", "EGE", 'G', "ingotGold", 'E', "enderpearl", 'D', "gemDiamond" });
		CreateRecipes.addShapedRecipe(new ItemStack(mob_repulsor, 1), new Object[] { "EGE", "GDG", "EGE", 'G', IndustryItems.gravity_controller, 'E', IndustryItems.gravity_boots, 'D', IndustryItems.low_gravity_controller });
		// control = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(drill_head_item, 1), new Object[] { " X ", "###", " # ", 'X', "stickIron", '#', "ingotIron" });

		CreateRecipes.addShapedRecipe(new ItemStack(locksmith_lock, 3), new Object[] { " I ", "I I", "III", 'I', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(locksmith_key, 3), new Object[] { "II", "II", "I ", 'I', "ingotGold" });

	}

}
