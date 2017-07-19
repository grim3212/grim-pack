package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.ItemManualArmor;
import com.grim3212.mc.pack.core.item.ItemManualAxe;
import com.grim3212.mc.pack.core.item.ItemManualHoe;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.item.ItemManualPickaxe;
import com.grim3212.mc.pack.core.item.ItemManualSpade;
import com.grim3212.mc.pack.core.item.ItemManualSword;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.CreateRecipes;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.tools.blocks.ToolsBlocks;
import com.grim3212.mc.pack.tools.items.ItemBetterBucket.BucketType;
import com.grim3212.mc.pack.tools.util.ChiselRegistry;
import com.grim3212.mc.pack.tools.util.DispenseBehaviors;
import com.grim3212.mc.pack.tools.util.EnumSpearType;

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
public class ToolsItems {

	public static final ToolMaterial blackdiamond = EnumHelper.addToolMaterial("black_diamond", 4, 5122, 15F, 5F, 20);
	public static final ToolMaterial obsidianToolMaterial = EnumHelper.addToolMaterial("obsidian", 3, 3333, 9.5F, 7f, 14);
	public static final ArmorMaterial masks = EnumHelper.addArmorMaterial("mask", GrimPack.modID + ":masks", 5, new int[] { 1, 2, 3, 1 }, 15, SoundEvents.BLOCK_CLOTH_PLACE, 0.0F);
	public static final ArmorMaterial blackarmor = EnumHelper.addArmorMaterial("blackarmor", GrimPack.modID + ":blackarmor", 35, new int[] { 4, 8, 10, 4 }, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5F);

	public static final Item backpack = new ItemBackpack();
	public static final Item pellet_bag = new ItemPelletBag();
	public static final Item portable_workbench = new ItemPortableWorkbench("portable_workbench");
	public static final Item tomahawk = new ItemTomahawk();
	public static final Item grip = (new ItemManualPage("grip", "tools:ballistic.grip")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item throwing_knife = new ItemKnife();
	public static final Item loaded_knife = new ItemBallisticKnife(true, false);
	public static final Item unloaded_knife = new ItemBallisticKnife(false, false);
	public static final Item ammo_part = new ItemBallisticKnife(false, true);
	public static final Item button_part = (new ItemManualPage("button_part", "tools:ballistic.part3")).setMaxStackSize(1).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item spring_part = (new ItemManualPage("spring_part", "tools:ballistic.part2")).setMaxStackSize(1).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond = (new ItemManualPage("black_diamond", "tools:black.cookOre")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_pickaxe = (new ItemManualPickaxe("black_diamond_pickaxe", blackdiamond, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_shovel = (new ItemManualSpade("black_diamond_shovel", blackdiamond, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_axe = (new ItemManualAxe("black_diamond_axe", blackdiamond, 8.0f, -2.8f, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_hoe = (new ItemManualHoe("black_diamond_hoe", blackdiamond, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_sword = (new ItemManualSword("black_diamond_sword", blackdiamond, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_helmet = (new ItemManualArmor("black_diamond_helmet", blackarmor, 4, EntityEquipmentSlot.HEAD, "tools:tools.armor")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_chestplate = (new ItemManualArmor("black_diamond_chestplate", blackarmor, 4, EntityEquipmentSlot.CHEST, "tools:tools.armor")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_leggings = (new ItemManualArmor("black_diamond_leggings", blackarmor, 4, EntityEquipmentSlot.LEGS, "tools:tools.armor")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_boots = (new ItemManualArmor("black_diamond_boots", blackarmor, 4, EntityEquipmentSlot.FEET, "tools:tools.armor")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item wooden_bucket = new ItemBetterBucket(1, 0, 1000f, new ItemStack(Items.STICK, 3), BucketType.wood);
	public static final Item stone_bucket = new ItemBetterBucket(1, 0, new ItemStack(Blocks.COBBLESTONE, 3), BucketType.stone);
	public static final Item golden_bucket = new ItemBetterBucket(4, 0, BucketType.gold);
	public static final Item diamond_bucket = new ItemBetterBucket(16, 1, BucketType.diamond);
	public static final Item obsidian_bucket = new ItemBetterBucket(32, 2, true, BucketType.obsidian);
	public static final Item wooden_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) wooden_bucket);
	public static final Item stone_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) stone_bucket);
	public static final Item golden_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) golden_bucket);
	public static final Item diamond_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) diamond_bucket);
	public static final Item obsidian_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) obsidian_bucket);
	public static final Item iron_chisel = new ItemChisel(1);
	public static final Item iron_ore_item = (new ItemManualPage("iron_ore_item", "tools:chisels.ore")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item gold_ore_item = (new ItemManualPage("gold_ore_item", "tools:chisels.ore")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item diamond_chisel = new ItemChisel(4);
	public static final Item extinguisher = new ItemExtinguisher();
	public static final Item wood_hammer = new ItemHammer(ToolMaterial.WOOD);
	public static final Item stone_hammer = new ItemHammer(ToolMaterial.STONE);
	public static final Item gold_hammer = new ItemHammer(ToolMaterial.GOLD);
	public static final Item iron_hammer = new ItemHammer(ToolMaterial.IRON);
	public static final Item diamond_hammer = new ItemHammer(ToolMaterial.DIAMOND);
	public static final Item machete_gold = new ItemMachete("machete_gold", ToolMaterial.GOLD);
	public static final Item machete_iron = new ItemMachete("machete_iron", ToolMaterial.IRON);
	public static final Item machete_diamond = new ItemMachete("machete_diamond", ToolMaterial.DIAMOND);
	public static final Item machete_stone = new ItemMachete("machete_stone", ToolMaterial.STONE);
	public static final Item machete_wood = new ItemMachete("machete_wood", ToolMaterial.WOOD);
	public static final Item machete_slime = new ItemMachete("machete_slime", ToolMaterial.IRON);
	public static final Item building_wand = new ItemBuildingWand(false);
	public static final Item breaking_wand = new ItemBreakingWand(false);
	public static final Item mining_wand = new ItemMiningWand(false);
	public static final Item reinforced_building_wand = new ItemBuildingWand(true);
	public static final Item reinforced_breaking_wand = new ItemBreakingWand(true);
	public static final Item reinforced_mining_wand = new ItemMiningWand(true);
	public static final Item diamond_multi_tool = new ItemMultiTool("diamond_multi_tool", ToolMaterial.DIAMOND);
	public static final Item wooden_multi_tool = new ItemMultiTool("wooden_multi_tool", ToolMaterial.WOOD);
	public static final Item stone_multi_tool = new ItemMultiTool("stone_multi_tool", ToolMaterial.STONE);
	public static final Item iron_multi_tool = new ItemMultiTool("iron_multi_tool", ToolMaterial.IRON);
	public static final Item golden_multi_tool = new ItemMultiTool("golden_multi_tool", ToolMaterial.GOLD);
	public static final Item obsidian_multi_tool = new ItemMultiTool("obsidian_multi_tool", obsidianToolMaterial);
	public static final Item black_diamond_multi_tool = new ItemMultiTool("black_diamond_multi_tool", blackdiamond);
	public static final Item steel_multi_tool = new ItemMultiTool("steel_multi_tool", IndustryItems.steel);
	public static final Item pokeball = new ItemPokeball();
	public static final Item powerstaff = new ItemPowerStaff();
	public static final Item energy_canister = (new ItemManualPage("energy_canister", "tools:raygun.canisters")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item element_115 = (new ItemManualPage("element_115", "tools:raygun.element")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item empty_energy_canister = (new ItemManualPage("empty_energy_canister", "tools:raygun.canisters")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item ray_gun = new ItemRayg();
	public static final Item advanced_empty_energy_canister = (new ItemManualPage("advanced_empty_energy_canister", "tools:raygun.advCanisters")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item advanced_energy_canister = (new ItemManualPage("advanced_energy_canister", "tools:raygun.advCanisters")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item advanced_ray_gun = new ItemAdvRayg();
	public static final Item dark_iron_ingot = (new ItemManualPage("dark_iron_ingot", "tools:raygun.darkIron")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item sling_shot = new ItemSlingshot();
	public static final Item sling_pellet = new ItemSlingPellet();
	public static final Item spear = new ItemSpear(EnumSpearType.STONE);
	public static final Item iron_spear = new ItemSpear(EnumSpearType.IRON);
	public static final Item diamond_spear = new ItemSpear(EnumSpearType.DIAMOND);
	public static final Item explosive_spear = new ItemSpear(EnumSpearType.EXPLOSIVE);
	public static final Item fire_spear = new ItemSpear(EnumSpearType.FIRE);
	public static final Item slime_spear = new ItemSpear(EnumSpearType.SLIME);
	public static final Item light_spear = new ItemSpear(EnumSpearType.LIGHT);
	public static final Item lightning_spear = new ItemSpear(EnumSpearType.LIGHTNING);
	public static final Item ultimate_fist = new ItemUltimateFist();
	public static final Item mask = new ItemMaskArmor(masks, 3);
	public static final Item boomerang = new ItemBoomerang("boomerang");
	public static final Item diamond_boomerang = new ItemDiamondBoomerang();

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();
		// TODO: Possibly look into dynamic bucket creation and use that
		// for bucketWater and bucketLava, and so on for recipes
		r.register(boomerang);
		r.register(diamond_boomerang);
		r.register(backpack);
		r.register(pellet_bag);
		r.register(portable_workbench);
		r.register(loaded_knife);
		r.register(unloaded_knife);
		r.register(ammo_part);
		r.register(button_part);
		r.register(spring_part);
		r.register(black_diamond);
		r.register(black_diamond_boots);
		r.register(black_diamond_leggings);
		r.register(black_diamond_chestplate);
		r.register(black_diamond_helmet);
		r.register(black_diamond_sword);
		r.register(black_diamond_hoe);
		r.register(black_diamond_axe);
		r.register(black_diamond_shovel);
		r.register(black_diamond_pickaxe);
		r.register(wooden_bucket);
		r.register(wooden_milk_bucket);
		r.register(stone_bucket);
		r.register(stone_milk_bucket);
		r.register(golden_bucket);
		r.register(golden_milk_bucket);
		r.register(diamond_bucket);
		r.register(diamond_milk_bucket);
		r.register(obsidian_bucket);
		r.register(obsidian_milk_bucket);
		r.register(throwing_knife);
		r.register(tomahawk);
		r.register(grip);
		r.register(diamond_chisel);
		r.register(gold_ore_item);
		r.register(iron_ore_item);
		r.register(iron_chisel);
		r.register(extinguisher);
		r.register(wood_hammer);
		r.register(stone_hammer);
		r.register(gold_hammer);
		r.register(iron_hammer);
		r.register(diamond_hammer);
		r.register(machete_gold);
		r.register(machete_iron);
		r.register(machete_diamond);
		r.register(machete_stone);
		r.register(machete_wood);
		r.register(machete_slime);
		r.register(building_wand);
		r.register(breaking_wand);
		r.register(mining_wand);
		r.register(reinforced_building_wand);
		r.register(reinforced_breaking_wand);
		r.register(reinforced_mining_wand);
		r.register(diamond_multi_tool);
		r.register(wooden_multi_tool);
		r.register(stone_multi_tool);
		r.register(iron_multi_tool);
		r.register(golden_multi_tool);
		r.register(obsidian_multi_tool);
		r.register(black_diamond_multi_tool);
		r.register(pokeball);
		r.register(powerstaff);
		r.register(energy_canister);
		r.register(element_115);
		r.register(empty_energy_canister);
		r.register(ray_gun);
		r.register(advanced_empty_energy_canister);
		r.register(advanced_energy_canister);
		r.register(advanced_ray_gun);
		r.register(dark_iron_ingot);
		r.register(sling_shot);
		r.register(sling_pellet);
		r.register(spear);
		r.register(iron_spear);
		r.register(diamond_spear);
		r.register(explosive_spear);
		r.register(fire_spear);
		r.register(slime_spear);
		r.register(light_spear);
		r.register(lightning_spear);
		r.register(ultimate_fist);
		r.register(mask);

		if (CoreConfig.useIndustry || OreDictionary.doesOreNameExist("ingotSteel"))
			r.register(steel_multi_tool);

		// Set repair items
		obsidianToolMaterial.setRepairItem(new ItemStack(Blocks.OBSIDIAN));
		blackdiamond.setRepairItem(new ItemStack(black_diamond));
		blackarmor.setRepairItem(new ItemStack(black_diamond));
		masks.setRepairItem(new ItemStack(Items.PAPER));

		// Register all of the dispenser behaviors
		DispenseBehaviors.register();

		initOreDict();
	}

	private static void initOreDict() {
		OreDictionary.registerOre("ingotDarkIron", dark_iron_ingot);
		OreDictionary.registerOre("bucketMilk", Items.MILK_BUCKET);
		OreDictionary.registerOre("bucketMilk", wooden_milk_bucket);
		OreDictionary.registerOre("bucketMilk", stone_milk_bucket);
		OreDictionary.registerOre("bucketMilk", golden_milk_bucket);
		OreDictionary.registerOre("bucketMilk", diamond_milk_bucket);
		OreDictionary.registerOre("bucketMilk", obsidian_milk_bucket);
		OreDictionary.registerOre("gemBlackDiamond", black_diamond);
		OreDictionary.registerOre("element115", element_115);
		OreDictionary.registerOre("oreIron", iron_ore_item);
		OreDictionary.registerOre("oreGold", gold_ore_item);
		OreDictionary.registerOre("canEnergy", energy_canister);
		OreDictionary.registerOre("canAdvancedEnergy", advanced_energy_canister);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		// Register chiseleable blocks
		ChiselRegistry.registerBlock(Blocks.DIAMOND_ORE, Blocks.STONE, Items.DIAMOND, 1, 0);
		ChiselRegistry.registerBlock(Blocks.EMERALD_ORE, Blocks.STONE, Items.EMERALD, 1, 0);
		ChiselRegistry.registerBlock(Blocks.QUARTZ_ORE, Blocks.NETHERRACK, Items.QUARTZ, 1, 0);
		ChiselRegistry.registerBlock(Blocks.GOLD_ORE, Blocks.STONE, Items.GOLD_INGOT, 1, 0);
		ChiselRegistry.registerBlock(Blocks.IRON_ORE, Blocks.STONE, Items.IRON_INGOT, 1, 0);
		ChiselRegistry.registerBlock(Blocks.REDSTONE_ORE, Blocks.STONE, Items.REDSTONE, 4, 0);
		ChiselRegistry.registerBlock(Blocks.LIT_REDSTONE_ORE, Blocks.STONE, Items.REDSTONE, 4, 0);
		ChiselRegistry.registerBlock(Blocks.COAL_ORE, Blocks.STONE, Items.COAL, 1, 0);
		ChiselRegistry.registerBlock(Blocks.LAPIS_ORE, Blocks.STONE, Items.DYE, 3, 4);
		ChiselRegistry.registerBlock(ToolsBlocks.black_diamond_ore, Blocks.STONE, black_diamond, 1, 0);

		GameRegistry.addSmelting(iron_ore_item, new ItemStack(Items.IRON_INGOT), 0.4F);
		GameRegistry.addSmelting(gold_ore_item, new ItemStack(Items.GOLD_INGOT), 1F);
		GameRegistry.addSmelting(ToolsBlocks.black_diamond_ore, new ItemStack(black_diamond, 1), 1.0F);
	}

	public static List<IRecipe> blackTools;
	public static List<IRecipe> blackArmor;
	public static List<IRecipe> chisels;
	public static IRecipe extinguisherRecipe;
	public static IRecipe extinguisherRefillRecipe;
	public static List<IRecipe> hammers;
	public static List<IRecipe> machetes;
	public static List<IRecipe> regular;
	public static List<IRecipe> reinforced;
	public static List<IRecipe> tools;
	public static List<IRecipe> advCanisters;
	public static List<IRecipe> basicCanisters;
	public static List<IRecipe> pellets;
	public static List<IRecipe> basics;
	public static List<IRecipe> specials;
	public static IRecipe emptyRecipe;
	public static List<IRecipe> mobs;

	public static void addRecipes() {

		CreateRecipes.addShapedRecipe(new ItemStack(boomerang, 1), new Object[] { "XX ", "X  ", "XX ", 'X', "plankWood" });
		CreateRecipes.addShapedRecipe(new ItemStack(diamond_boomerang, 1), new Object[] { "XX ", "XY ", "XX ", 'X', "gemDiamond", 'Y', boomerang });

		CreateRecipes.addShapedRecipe(new ItemStack(backpack, 1), new Object[] { "LLS", "LIS", "LLL", 'L', "leather", 'S', "string", 'I', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(portable_workbench, 1), new Object[] { "III", "IWI", "III", 'W', "workbench", 'I', "ingotIron" });

		// Backpack Recipes
		// GameRegistry.addRecipe(new BackpackRecipe());
		// RecipeSorter.register("Backpack_Recipes", BackpackRecipe.class,
		// Category.SHAPELESS, "after:forge:shapelessore");

		// Pellet Bag Recipes
		// GameRegistry.addRecipe(new PelletBagRecipe());
		// RecipeSorter.register("Pellet_Bag_Recipes", PelletBagRecipe.class,
		// Category.SHAPELESS, "after:forge:shapelessore");

		CreateRecipes.addShapedRecipe(new ItemStack(iron_chisel, 1), new Object[] { " B", "I ", 'B', "ingotIron", 'I', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(diamond_chisel, 1), new Object[] { " B", "I ", 'B', "gemDiamond", 'I', "stickIron" });
		// chisels = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(tomahawk, 1), new Object[] { "XX ", "XY ", " Y ", 'X', "ingotIron", 'Y', grip });
		CreateRecipes.addShapedRecipe(new ItemStack(throwing_knife, 4), new Object[] { "Y", "X", 'Y', "ingotIron", 'X', grip });
		CreateRecipes.addShapedRecipe(new ItemStack(grip, 2), new Object[] { "Y", "Y", 'Y', "leather" });

		CreateRecipes.addShapedRecipe(new ItemStack(ammo_part, 1), new Object[] { "#  ", " # ", "  !", '#', "ingotIron", '!', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(spring_part, 1), new Object[] { "#  ", " ! ", "  #", '#', "ingotIron", '!', "string" });
		CreateRecipes.addShapedRecipe(new ItemStack(button_part, 1), new Object[] { " # ", "#!#", " # ", '#', "dustRedstone", '!', Blocks.STONE_BUTTON });
		CreateRecipes.addShapedRecipe(new ItemStack(unloaded_knife, 1), new Object[] { " #", "! ", " @", '#', button_part, '!', spring_part, '@', grip });
		CreateRecipes.addShapedRecipe(new ItemStack(black_diamond_pickaxe, 1), new Object[] { "###", " X ", " X ", 'X', "stickWood", '#', black_diamond });
		CreateRecipes.addShapedRecipe(new ItemStack(black_diamond_sword, 1), new Object[] { "#", "#", "X", 'X', "stickWood", '#', black_diamond });
		CreateRecipes.addShapedRecipe(new ItemStack(black_diamond_axe, 1), new Object[] { "## ", "#X ", " X ", 'X', "stickWood", '#', black_diamond });
		CreateRecipes.addShapedRecipe(new ItemStack(black_diamond_hoe, 1), new Object[] { "## ", " X ", " X ", 'X', "stickWood", '#', black_diamond });
		CreateRecipes.addShapedRecipe(new ItemStack(black_diamond_shovel, 1), new Object[] { "#", "X", "X", 'X', "stickWood", '#', black_diamond });
		// blackTools = RecipeHelper.getLatestIRecipes(5);

		CreateRecipes.addShapedRecipe(new ItemStack(black_diamond_helmet, 1), new Object[] { "###", "# #", '#', black_diamond });
		CreateRecipes.addShapedRecipe(new ItemStack(black_diamond_chestplate, 1), new Object[] { "# #", "###", "###", '#', black_diamond });
		CreateRecipes.addShapedRecipe(new ItemStack(black_diamond_leggings, 1), new Object[] { "###", "# #", "# #", '#', black_diamond });
		CreateRecipes.addShapedRecipe(new ItemStack(black_diamond_boots, 1), new Object[] { "# #", "# #", '#', black_diamond });
		// blackArmor = RecipeHelper.getLatestIRecipes(4);

		CreateRecipes.addShapedRecipe(new ItemStack(wooden_bucket, 1), new Object[] { "# #", " # ", '#', "slabWood" });
		CreateRecipes.addShapedRecipe(new ItemStack(stone_bucket, 1), new Object[] { "# #", " # ", '#', "cobblestone" });
		CreateRecipes.addShapedRecipe(new ItemStack(golden_bucket, 1), new Object[] { "# #", " # ", '#', "ingotGold" });
		CreateRecipes.addShapedRecipe(new ItemStack(diamond_bucket, 1), new Object[] { "# #", " # ", '#', "gemDiamond" });
		CreateRecipes.addShapedRecipe(new ItemStack(obsidian_bucket, 1), new Object[] { "# #", " # ", '#', "obsidian" });

		CreateRecipes.addShapedRecipe(new ItemStack(extinguisher, 1), new Object[] { "FX ", " X ", " T ", 'X', Items.WATER_BUCKET, 'F', "dustRedstone", 'T', "ingotIron" });
		// extinguisherRecipe = RecipeHelper.getLatestIRecipe();
		CreateRecipes.addShapelessRecipe(new ItemStack(extinguisher, 1), new Object[] { Items.WATER_BUCKET, Items.WATER_BUCKET, new ItemStack(extinguisher, 1, 1) });
		// extinguisherRefillRecipe = RecipeHelper.getLatestIRecipe();

		CreateRecipes.addShapedRecipe(new ItemStack(wood_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "plankWood", 'G', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(stone_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "cobblestone", 'G', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(gold_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "ingotGold", 'G', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(iron_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "ingotIron", 'G', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(diamond_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "gemDiamond", 'G', "stickIron" });
		// hammers = RecipeHelper.getLatestIRecipes(5);

		CreateRecipes.addShapedRecipe(new ItemStack(machete_wood, 1), new Object[] { " XX", "XX ", "#  ", 'X', "plankWood", '#', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(machete_stone, 1), new Object[] { " XX", "XX ", "#  ", 'X', "cobblestone", '#', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(machete_gold, 1), new Object[] { " XX", "XX ", "#  ", 'X', "ingotGold", '#', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(machete_iron, 1), new Object[] { " XX", "XX ", "#  ", 'X', "ingotIron", '#', "stickIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(machete_diamond, 1), new Object[] { " XX", "XX ", "#  ", 'X', "gemDiamond", '#', "stickIron" });
		// machetes = RecipeHelper.getLatestIRecipes(5);

		CreateRecipes.addShapedRecipe(new ItemStack(machete_slime, 1), new Object[] { " XX", "XX ", "#  ", 'X', "slimeball", '#', "stickWood" });

		CreateRecipes.addShapedRecipe(new ItemStack(building_wand), new Object[] { "PXP", "PXP", "PXP", 'P', "plankWood", 'X', "ingotGold" });
		CreateRecipes.addShapedRecipe(new ItemStack(breaking_wand), new Object[] { "PXP", "PXP", "PXP", 'P', "plankWood", 'X', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(mining_wand), new Object[] { "PXP", "PXP", "PXP", 'P', "plankWood", 'X', "gemDiamond" });
		// regular = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapedRecipe(new ItemStack(reinforced_building_wand), new Object[] { "OXO", "OXO", "OXO", 'O', Blocks.OBSIDIAN, 'X', "blockGold" });
		CreateRecipes.addShapedRecipe(new ItemStack(reinforced_breaking_wand), new Object[] { "OXO", "OXO", "OXO", 'O', Blocks.OBSIDIAN, 'X', "blockIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(reinforced_mining_wand), new Object[] { "OXO", "OXO", "OXO", 'O', Blocks.OBSIDIAN, 'X', "blockDiamond" });
		// reinforced = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapelessRecipe(new ItemStack(wooden_multi_tool, 1), new Object[] { Items.WOODEN_SWORD, Items.WOODEN_PICKAXE, Items.WOODEN_SHOVEL, Items.WOODEN_HOE, Items.WOODEN_AXE, "logWood", "logWood", "logWood", "logWood" });
		CreateRecipes.addShapelessRecipe(new ItemStack(stone_multi_tool, 1), new Object[] { Items.STONE_SWORD, Items.STONE_PICKAXE, Items.STONE_SHOVEL, Items.STONE_HOE, Items.STONE_AXE, "cobblestone", "cobblestone", "cobblestone", "cobblestone" });
		CreateRecipes.addShapelessRecipe(new ItemStack(iron_multi_tool, 1), new Object[] { Items.IRON_SWORD, Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_HOE, Items.IRON_AXE, "ingotIron", "ingotIron", "ingotIron", "ingotIron" });
		CreateRecipes.addShapelessRecipe(new ItemStack(golden_multi_tool, 1), new Object[] { Items.GOLDEN_SWORD, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_HOE, Items.GOLDEN_AXE, "ingotGold", "ingotGold", "ingotGold", "ingotGold" });
		CreateRecipes.addShapelessRecipe(new ItemStack(diamond_multi_tool, 1), new Object[] { Items.DIAMOND_SWORD, Items.DIAMOND_PICKAXE, Items.DIAMOND_SHOVEL, Items.DIAMOND_HOE, Items.DIAMOND_AXE, "gemDiamond", "gemDiamond", "gemDiamond", "gemDiamond" });
		CreateRecipes.addShapelessRecipe(new ItemStack(obsidian_multi_tool, 1), new Object[] { diamond_multi_tool, golden_multi_tool, wooden_multi_tool, stone_multi_tool, iron_multi_tool, "obsidian", "obsidian", "obsidian", "obsidian" });
		CreateRecipes.addShapelessRecipe(new ItemStack(black_diamond_multi_tool, 1), new Object[] { black_diamond_sword, black_diamond_pickaxe, black_diamond_shovel, black_diamond_hoe, black_diamond_axe, black_diamond, black_diamond, black_diamond, black_diamond });

		// TODO: Create custom condition for oredict check
		if (CoreConfig.useIndustry || OreDictionary.doesOreNameExist("ingotSteel")) {
			CreateRecipes.addShapelessRecipe(new ItemStack(steel_multi_tool, 1), new Object[] { IndustryItems.steel_sword, IndustryItems.steel_pickaxe, IndustryItems.steel_shovel, IndustryItems.steel_hoe, IndustryItems.steel_axe, "ingotSteel", "ingotSteel", "ingotSteel", "ingotSteel" });
			// tools = RecipeHelper.getLatestIRecipes(8);
		} else {
			// tools = RecipeHelper.getLatestIRecipes(7);
		}

		CreateRecipes.addShapedRecipe(new ItemStack(pokeball), new Object[] { "RRR", "CBC", "III", 'R', "dustRedstone", 'C', Items.COAL, 'B', Blocks.STONE_BUTTON, 'I', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(powerstaff, 1), new Object[] { "IDI", "IRI", " I ", 'D', "gemDiamond", 'R', "dustRedstone", 'I', "ingotIron" });

		CreateRecipes.addShapedRecipe(new ItemStack(empty_energy_canister, 2), new Object[] { "YXY", "Y Y", "YXY", 'X', "ingotIron", 'Y', "blockGlass" });
		CreateRecipes.addShapedRecipe(new ItemStack(energy_canister, 1), new Object[] { "Y", "X", 'X', empty_energy_canister, 'Y', element_115 });
		// basicCanisters = RecipeHelper.getLatestIRecipes(2);
		CreateRecipes.addShapedRecipe(new ItemStack(ray_gun, 1), new Object[] { "XZ ", " XY", " XX", 'X', "ingotIron", 'Y', element_115, 'Z', "dustRedstone" });

		CreateRecipes.addShapedRecipe(new ItemStack(dark_iron_ingot, 1), new Object[] { "XYX", 'X', element_115, 'Y', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(advanced_empty_energy_canister, 2), new Object[] { "YXY", "Y Y", "YXY", 'X', "ingotDarkIron", 'Y', "blockGlass" });
		CreateRecipes.addShapedRecipe(new ItemStack(advanced_energy_canister, 1), new Object[] { "XY", "YY", 'X', advanced_empty_energy_canister, 'Y', element_115 });
		// advCanisters = RecipeHelper.getLatestIRecipes(2);
		CreateRecipes.addShapedRecipe(new ItemStack(advanced_ray_gun, 1), new Object[] { "XZ ", " XY", " XX", 'X', "ingotDarkIron", 'Y', ray_gun, 'Z', "dustRedstone" });

		CreateRecipes.addShapedRecipe(new ItemStack(sling_shot, 1), new Object[] { "#@#", " # ", " # ", '#', "stickWood", '@', Items.STRING });
		CreateRecipes.addShapedRecipe(new ItemStack(pellet_bag, 1), new Object[] { "SIL", "LLL", 'L', "leather", 'S', "string", 'I', "ingotIron" });

		CreateRecipes.addShapedRecipe(new ItemStack(sling_pellet, 2, 0), new Object[] { "#", '#', "cobblestone" });
		CreateRecipes.addShapelessRecipe(new ItemStack(sling_pellet, 2, 1), new Object[] { new ItemStack(sling_pellet, 1, 0), "ingotIron" });
		CreateRecipes.addShapelessRecipe(new ItemStack(sling_pellet, 2, 2), new Object[] { new ItemStack(sling_pellet, 1, 0), "netherrack" });
		CreateRecipes.addShapelessRecipe(new ItemStack(sling_pellet, 1, 3), new Object[] { new ItemStack(sling_pellet, 1, 0), Blocks.TORCH });
		CreateRecipes.addShapelessRecipe(new ItemStack(sling_pellet, 1, 4), new Object[] { new ItemStack(sling_pellet, 1, 0), Items.COAL });
		CreateRecipes.addShapelessRecipe(new ItemStack(sling_pellet, 1, 5), new Object[] { new ItemStack(sling_pellet, 1, 2), "gunpowder" });
		// pellets = RecipeHelper.getLatestIRecipes(6);

		CreateRecipes.addShapedRecipe(new ItemStack(spear, 1), new Object[] { "#  ", " X ", "  X", '#', Items.FLINT, 'X', "stickWood" });
		CreateRecipes.addShapedRecipe(new ItemStack(spear, 1), new Object[] { "XX#", '#', Items.FLINT, 'X', "stickWood" });
		CreateRecipes.addShapedRecipe(new ItemStack(iron_spear, 1), new Object[] { "#", "X", '#', "ingotIron", 'X', spear });
		CreateRecipes.addShapedRecipe(new ItemStack(diamond_spear, 1), new Object[] { "#", "X", '#', "gemDiamond", 'X', spear });
		// basics = RecipeHelper.getLatestIRecipes(4);

		CreateRecipes.addShapedRecipe(new ItemStack(explosive_spear, 1), new Object[] { " # ", "# #", " X ", '#', "gunpowder", 'X', spear });
		CreateRecipes.addShapedRecipe(new ItemStack(fire_spear, 1), new Object[] { "#", "X", '#', Items.COAL, 'X', spear });
		CreateRecipes.addShapedRecipe(new ItemStack(slime_spear, 1), new Object[] { "#", "X", '#', "slimeball", 'X', spear });
		CreateRecipes.addShapedRecipe(new ItemStack(light_spear, 1), new Object[] { "#", "X", '#', "torch", 'X', spear });
		CreateRecipes.addShapedRecipe(new ItemStack(lightning_spear, 1), new Object[] { "U", "#", "X", 'U', Items.WATER_BUCKET, '#', "dustRedstone", 'X', iron_spear });
		// specials = RecipeHelper.getLatestIRecipes(5);

		CreateRecipes.addShapedRecipe(new ItemStack(ultimate_fist, 1), new Object[] { "XXX", "X##", "XXX", '#', "leather", 'X', "blockDiamond" });

		ItemStack emptyStack = new ItemStack(mask, 1, 0);
		CreateRecipes.addShapedRecipe(emptyStack, new Object[] { "YXY", 'Y', "string", 'X', "paper" });
		// emptyRecipe = RecipeHelper.getLatestIRecipe();

		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 1), new Object[] { emptyStack, "dyeBrown", "dyeYellow", "dyeOrange" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 2), new Object[] { emptyStack, "dyeBlue", "dyeRed", "dyeCyan" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 3), new Object[] { emptyStack, "dyeWhite", "dyeRed", "dyeOrange" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 4), new Object[] { emptyStack, "dyeWhite", "dyeBrown", "dyeBlack" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 5), new Object[] { emptyStack, "dyeGreen", "dyeBlack", "dyeBlack" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 6), new Object[] { emptyStack, "dyeBlack", "dyeMagenta", "dyePurple" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 7), new Object[] { emptyStack, "dyeWhite", "dyeGray", "dyeLightGray" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 8), new Object[] { emptyStack, "dyeBlack", "dyeRed", "dyeYellow" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 9), new Object[] { emptyStack, "dyePink", "dyeBlack" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 10), new Object[] { emptyStack, "dyePink", "dyeWhite", "dyeBrown" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 11), new Object[] { emptyStack, "dyeRed", "dyeBlack", "dyeWhite" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 12), new Object[] { emptyStack, "dyeWhite", "dyeGray" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 13), new Object[] { emptyStack, "dyeGreen", "dyeLime" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 14), new Object[] { emptyStack, "dyeRed", "dyeBlack", "dyeGray" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 15), new Object[] { emptyStack, "dyeBlue", "dyeCyan", "dyeLightBlue" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 16), new Object[] { emptyStack, "dyeLime", "dyeBrown", "dyeOrange" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 17), new Object[] { emptyStack, "dyeBlack", "dyeBrown", "dyeLightGray" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 18), new Object[] { emptyStack, "dyeGreen", "dyeBlack" });
		CreateRecipes.addShapelessRecipe(new ItemStack(mask, 1, 19), new Object[] { emptyStack, "dyeLime", "dyePink", "dyeGray" });
		// mobs = RecipeHelper.getLatestIRecipes(19);
	}
}