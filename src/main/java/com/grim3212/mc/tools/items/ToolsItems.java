package com.grim3212.mc.tools.items;

import java.util.List;

import com.grim3212.mc.core.item.AxeItem;
import com.grim3212.mc.core.item.PickaxeItem;
import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.OreDictionaryHelper;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.items.ItemBetterBucket.BucketType;
import com.grim3212.mc.tools.util.BackpackRecipeHandler;
import com.grim3212.mc.tools.util.ChiselRegistry;
import com.grim3212.mc.tools.util.EnumSpearType;

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
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ToolsItems implements IPartItems {

	public static Item backpack;
	public static Item portable_workbench;
	public static Item tomahawk;
	public static Item grip;
	public static Item throwing_knife;
	public static Item loaded_knife;
	public static Item unloaded_knife;
	public static Item ammo_part;
	public static Item button_part;
	public static Item spring_part;
	public static Item casing_part;
	public static Item rod_part;
	public static Item black_diamond;
	public static Item black_diamond_pickaxe;
	public static Item black_diamond_shovel;
	public static Item black_diamond_axe;
	public static Item black_diamond_hoe;
	public static Item black_diamond_sword;
	public static Item black_diamond_helmet;
	public static Item black_diamond_chestplate;
	public static Item black_diamond_leggings;
	public static Item black_diamond_boots;
	public static Item wooden_bucket;
	public static Item stone_bucket;
	public static Item golden_bucket;
	public static Item diamond_bucket;
	public static Item obsidian_bucket;
	public static Item wooden_milk_bucket;
	public static Item stone_milk_bucket;
	public static Item golden_milk_bucket;
	public static Item diamond_milk_bucket;
	public static Item obsidian_milk_bucket;
	public static Item iron_chisel;
	public static Item iron_ore_item;
	public static Item gold_ore_item;
	public static Item diamond_chisel;
	public static Item extinguisher;
	public static Item wood_hammer;
	public static Item stone_hammer;
	public static Item gold_hammer;
	public static Item iron_hammer;
	public static Item diamond_hammer;
	public static Item machete_gold;
	public static Item machete_iron;
	public static Item machete_diamond;
	public static Item machete_stone;
	public static Item machete_wood;
	public static Item machete_slime;
	public static Item building_wand;
	public static Item breaking_wand;
	public static Item mining_wand;
	public static Item reinforced_building_wand;
	public static Item reinforced_breaking_wand;
	public static Item reinforced_mining_wand;
	public static Item diamond_multi_tool;
	public static Item wooden_multi_tool;
	public static Item stone_multi_tool;
	public static Item iron_multi_tool;
	public static Item golden_multi_tool;
	public static Item obsidian_multi_tool;
	public static Item pokeball;
	public static Item powerstaff;
	public static Item energy_canister;
	public static Item element_115;
	public static Item empty_energy_canister;
	public static Item ray_gun;
	public static Item advanced_empty_energy_canister;
	public static Item advanced_energy_canister;
	public static Item advanced_ray_gun;
	public static Item dark_iron_ingot;
	public static Item sling_shot;
	public static Item sling_pellet;
	public static Item spear;
	public static Item iron_spear;
	public static Item diamond_spear;
	public static Item explosive_spear;
	public static Item fire_spear;
	public static Item slime_spear;
	public static Item light_spear;
	public static Item lightning_spear;
	public static Item ultimate_fist;
	public static Item mask;

	public static ToolMaterial blackdiamond = EnumHelper.addToolMaterial("black_diamond", 4, 5122, 15F, 5F, 20);
	public static ToolMaterial obsidianToolMaterial = EnumHelper.addToolMaterial("obsidian", 3, 10000, 9.5F, 7f, 14);
	public static ArmorMaterial masks = EnumHelper.addArmorMaterial("mask", GrimTools.modID + ":masks", 5, new int[] { 1, 3, 2, 1 }, 15);
	public static ArmorMaterial blackarmor = EnumHelper.addArmorMaterial("blackarmor", GrimTools.modID + ":blackarmor", 35, new int[] { 3, 8, 6, 3 }, 20);

	@Override
	public void initItems() {
		backpack = new ItemBackpack().setUnlocalizedName("backpack").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		portable_workbench = new ItemPortableWorkbench().setUnlocalizedName("portable_workbench").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		loaded_knife = (new ItemBallisticKnife(true, false)).setUnlocalizedName("loaded_knife").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		unloaded_knife = (new ItemBallisticKnife(false, false)).setMaxStackSize(1).setUnlocalizedName("unloaded_knife").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		ammo_part = (new ItemBallisticKnife(false, true)).setMaxStackSize(64).setUnlocalizedName("ammo_part").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		button_part = (new Item()).setMaxStackSize(1).setUnlocalizedName("button_part").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		spring_part = (new Item()).setMaxStackSize(1).setUnlocalizedName("spring_part").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		casing_part = (new Item()).setMaxStackSize(1).setUnlocalizedName("casing_part").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		rod_part = (new Item()).setMaxStackSize(64).setUnlocalizedName("rod_part").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond = (new Item()).setUnlocalizedName("black_diamond").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_boots = (new ItemArmor(blackarmor, 4, 3)).setUnlocalizedName("black_diamond_boots").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_leggings = (new ItemArmor(blackarmor, 4, 2)).setUnlocalizedName("black_diamond_leggings").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_chestplate = (new ItemArmor(blackarmor, 4, 1)).setUnlocalizedName("black_diamond_chestplate").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_helmet = (new ItemArmor(blackarmor, 4, 0)).setUnlocalizedName("black_diamond_helmet").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_sword = (new ItemSword(blackdiamond)).setUnlocalizedName("black_diamond_sword").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_hoe = (new ItemHoe(blackdiamond)).setUnlocalizedName("black_diamond_hoe").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_axe = (new AxeItem(blackdiamond)).setUnlocalizedName("black_diamond_axe").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_shovel = (new ItemSpade(blackdiamond)).setUnlocalizedName("black_diamond_shovel").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_pickaxe = (new PickaxeItem(blackdiamond)).setUnlocalizedName("black_diamond_pickaxe").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		wooden_bucket = new ItemBetterBucket(1, 0, 1000f, new ItemStack(Items.stick, 3), BucketType.wood).setUnlocalizedName("wooden_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		stone_bucket = new ItemBetterBucket(1, 0, new ItemStack(Blocks.cobblestone, 3), BucketType.stone).setUnlocalizedName("stone_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		golden_bucket = new ItemBetterBucket(4, 0, BucketType.gold).setUnlocalizedName("golden_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		diamond_bucket = new ItemBetterBucket(16, 1, BucketType.diamond).setUnlocalizedName("diamond_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		obsidian_bucket = new ItemBetterBucket(32, 2, true, BucketType.obsidian).setUnlocalizedName("obsidian_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		wooden_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) wooden_bucket).setUnlocalizedName("wooden_milk_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		stone_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) stone_bucket).setUnlocalizedName("stone_milk_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		golden_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) golden_bucket).setUnlocalizedName("golden_milk_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		diamond_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) diamond_bucket).setUnlocalizedName("diamond_milk_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		obsidian_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) obsidian_bucket).setUnlocalizedName("obsidian_milk_bucket").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		throwing_knife = (new ItemKnife()).setUnlocalizedName("throwing_knife").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		tomahawk = (new ItemTomahawk()).setUnlocalizedName("tomahawk").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		grip = (new Item()).setUnlocalizedName("grip").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		diamond_chisel = (new ItemChisel(4)).setUnlocalizedName("diamond_chisel");
		gold_ore_item = (new Item()).setUnlocalizedName("gold_ore_item").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		iron_ore_item = (new Item()).setUnlocalizedName("iron_ore_item").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		iron_chisel = (new ItemChisel(1)).setUnlocalizedName("iron_chisel");
		extinguisher = new ItemExtinguisher().setUnlocalizedName("extinguisher").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		wood_hammer = (new ItemHammer(ToolMaterial.WOOD)).setUnlocalizedName("wood_hammer");
		stone_hammer = (new ItemHammer(ToolMaterial.STONE)).setUnlocalizedName("stone_hammer");
		gold_hammer = (new ItemHammer(ToolMaterial.GOLD)).setUnlocalizedName("gold_hammer");
		iron_hammer = (new ItemHammer(ToolMaterial.IRON)).setUnlocalizedName("iron_hammer");
		diamond_hammer = (new ItemHammer(ToolMaterial.EMERALD)).setUnlocalizedName("diamond_hammer");
		machete_gold = (new ItemMachete(ToolMaterial.GOLD)).setUnlocalizedName("machete_gold").setMaxDamage(32);
		machete_iron = (new ItemMachete(ToolMaterial.IRON)).setUnlocalizedName("machete_iron").setMaxDamage(250);
		machete_diamond = (new ItemMachete(ToolMaterial.EMERALD)).setUnlocalizedName("machete_diamond").setMaxDamage(1561);
		machete_stone = (new ItemMachete(ToolMaterial.STONE)).setUnlocalizedName("machete_stone").setMaxDamage(131);
		machete_wood = (new ItemMachete(ToolMaterial.WOOD)).setUnlocalizedName("machete_wood").setMaxDamage(59);
		machete_slime = (new ItemMachete(ToolMaterial.IRON)).setUnlocalizedName("machete_slime").setMaxDamage(250);
		building_wand = (new ItemBuildingWand(false)).setUnlocalizedName("building_wand");
		breaking_wand = (new ItemBreakingWand(false)).setUnlocalizedName("breaking_wand");
		mining_wand = (new ItemMiningWand(false)).setUnlocalizedName("mining_wand");
		reinforced_building_wand = (new ItemBuildingWand(true)).setUnlocalizedName("reinforced_building_wand");
		reinforced_breaking_wand = (new ItemBreakingWand(true)).setUnlocalizedName("reinforced_breaking_wand");
		reinforced_mining_wand = (new ItemMiningWand(true)).setUnlocalizedName("reinforced_mining_wand");
		diamond_multi_tool = (new ItemMultiTool(ToolMaterial.EMERALD)).setUnlocalizedName("diamond_multi_tool");
		wooden_multi_tool = (new ItemMultiTool(ToolMaterial.WOOD)).setUnlocalizedName("wooden_multi_tool");
		stone_multi_tool = (new ItemMultiTool(ToolMaterial.STONE)).setUnlocalizedName("stone_multi_tool");
		iron_multi_tool = (new ItemMultiTool(ToolMaterial.IRON)).setUnlocalizedName("iron_multi_tool");
		golden_multi_tool = (new ItemMultiTool(ToolMaterial.GOLD)).setUnlocalizedName("golden_multi_tool");
		obsidian_multi_tool = (new ItemMultiTool(obsidianToolMaterial)).setUnlocalizedName("obsidian_multi_tool");
		pokeball = (new ItemPokeball()).setUnlocalizedName("pokeball");
		powerstaff = (new ItemPowerStaff()).setUnlocalizedName("powerstaff").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		energy_canister = (new Item()).setUnlocalizedName("energy_canister").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		element_115 = (new Item()).setUnlocalizedName("element_115").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		empty_energy_canister = (new Item()).setUnlocalizedName("empty_energy_canister").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		ray_gun = (new ItemRayg()).setUnlocalizedName("ray_gun").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		advanced_empty_energy_canister = (new Item()).setUnlocalizedName("advanced_empty_energy_canister").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		advanced_energy_canister = (new Item()).setUnlocalizedName("advanced_energy_canister").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		advanced_ray_gun = (new ItemAdvRayg()).setUnlocalizedName("advanced_ray_gun").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		dark_iron_ingot = (new Item()).setUnlocalizedName("dark_iron_ingot").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		sling_shot = (new ItemSlingshot()).setUnlocalizedName("sling_shot").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		sling_pellet = (new Item()).setUnlocalizedName("sling_pellet").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		spear = (new ItemSpear(EnumSpearType.STONE)).setUnlocalizedName("spear").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		iron_spear = (new ItemSpear(EnumSpearType.IRON)).setUnlocalizedName("iron_spear").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		diamond_spear = (new ItemSpear(EnumSpearType.DIAMOND)).setUnlocalizedName("diamond_spear").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		explosive_spear = (new ItemSpear(EnumSpearType.EXPLOSIVE)).setUnlocalizedName("explosive_spear").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		fire_spear = (new ItemSpear(EnumSpearType.FIRE)).setUnlocalizedName("fire_spear").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		slime_spear = (new ItemSpear(EnumSpearType.SLIME)).setUnlocalizedName("slime_spear").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		light_spear = (new ItemSpear(EnumSpearType.LIGHT)).setUnlocalizedName("light_spear").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		lightning_spear = (new ItemSpear(EnumSpearType.LIGHTNING)).setUnlocalizedName("lightning_spear").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		ultimate_fist = (new ItemUltimateFist()).setUnlocalizedName("ultimate_fist").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		mask = (new ItemMaskArmor(masks, 3, 0)).setUnlocalizedName("mask");

		GameRegistry.registerItem(mask, "mask");
		GameRegistry.registerItem(ultimate_fist, "ultimate_fist");
		GameRegistry.registerItem(spear, "spear");
		GameRegistry.registerItem(iron_spear, "iron_spear");
		GameRegistry.registerItem(diamond_spear, "diamond_spear");
		GameRegistry.registerItem(explosive_spear, "explosive_spear");
		GameRegistry.registerItem(fire_spear, "fire_spear");
		GameRegistry.registerItem(slime_spear, "slime_spear");
		GameRegistry.registerItem(light_spear, "light_spear");
		GameRegistry.registerItem(lightning_spear, "lightning_spear");
		GameRegistry.registerItem(sling_shot, "sling_shot");
		GameRegistry.registerItem(sling_pellet, "sling_pellet");
		GameRegistry.registerItem(element_115, "element_115");
		GameRegistry.registerItem(energy_canister, "energy_canister");
		GameRegistry.registerItem(empty_energy_canister, "empty_energy_canister");
		GameRegistry.registerItem(ray_gun, "ray_gun");
		GameRegistry.registerItem(advanced_empty_energy_canister, "advanced_empty_energy_canister");
		GameRegistry.registerItem(advanced_energy_canister, "advanced_energy_canister");
		GameRegistry.registerItem(advanced_ray_gun, "advanced_ray_gun");
		GameRegistry.registerItem(dark_iron_ingot, "dark_iron_ingot");
		GameRegistry.registerItem(powerstaff, "powerstaff");
		GameRegistry.registerItem(pokeball, "pokeball");
		GameRegistry.registerItem(diamond_multi_tool, "diamond_multi_tool");
		GameRegistry.registerItem(wooden_multi_tool, "wooden_multi_tool");
		GameRegistry.registerItem(stone_multi_tool, "stone_multi_tool");
		GameRegistry.registerItem(iron_multi_tool, "iron_multi_tool");
		GameRegistry.registerItem(golden_multi_tool, "golden_multi_tool");
		GameRegistry.registerItem(obsidian_multi_tool, "obsidian_multi_tool");
		GameRegistry.registerItem(building_wand, "building_wand");
		GameRegistry.registerItem(breaking_wand, "breaking_wand");
		GameRegistry.registerItem(mining_wand, "mining_wand");
		GameRegistry.registerItem(reinforced_building_wand, "reinforced_building_wand");
		GameRegistry.registerItem(reinforced_breaking_wand, "reinforced_breaking_wand");
		GameRegistry.registerItem(reinforced_mining_wand, "reinforced_mining_wand");
		GameRegistry.registerItem(machete_gold, "machete_gold");
		GameRegistry.registerItem(machete_iron, "machete_iron");
		GameRegistry.registerItem(machete_diamond, "machete_diamond");
		GameRegistry.registerItem(machete_stone, "machete_stone");
		GameRegistry.registerItem(machete_wood, "machete_wood");
		GameRegistry.registerItem(machete_slime, "machete_slime");
		GameRegistry.registerItem(wood_hammer, "wood_hammer");
		GameRegistry.registerItem(stone_hammer, "stone_hammer");
		GameRegistry.registerItem(gold_hammer, "gold_hammer");
		GameRegistry.registerItem(iron_hammer, "iron_hammer");
		GameRegistry.registerItem(diamond_hammer, "diamond_hammer");
		GameRegistry.registerItem(extinguisher, "extinguisher");
		GameRegistry.registerItem(iron_chisel, "iron_chisel");
		GameRegistry.registerItem(diamond_chisel, "diamond_chisel");
		GameRegistry.registerItem(gold_ore_item, "gold_ore_item");
		GameRegistry.registerItem(iron_ore_item, "iron_ore_item");
		GameRegistry.registerItem(throwing_knife, "throwing_knife");
		GameRegistry.registerItem(grip, "grip");
		GameRegistry.registerItem(tomahawk, "tomahawk");
		GameRegistry.registerItem(wooden_bucket, "wooden_bucket");
		GameRegistry.registerItem(stone_bucket, "stone_bucket");
		GameRegistry.registerItem(golden_bucket, "golden_bucket");
		GameRegistry.registerItem(diamond_bucket, "diamond_bucket");
		GameRegistry.registerItem(obsidian_bucket, "obsidian_bucket");
		GameRegistry.registerItem(wooden_milk_bucket, "wooden_milk_bucket");
		GameRegistry.registerItem(stone_milk_bucket, "stone_milk_bucket");
		GameRegistry.registerItem(golden_milk_bucket, "golden_milk_bucket");
		GameRegistry.registerItem(diamond_milk_bucket, "diamond_milk_bucket");
		GameRegistry.registerItem(obsidian_milk_bucket, "obsidian_milk_bucket");
		GameRegistry.registerItem(loaded_knife, "loaded_knife");
		GameRegistry.registerItem(unloaded_knife, "unloaded_knife");
		GameRegistry.registerItem(ammo_part, "ammo_part");
		GameRegistry.registerItem(button_part, "button_part");
		GameRegistry.registerItem(spring_part, "spring_part");
		GameRegistry.registerItem(casing_part, "casing_part");
		GameRegistry.registerItem(rod_part, "rod_part");
		GameRegistry.registerItem(backpack, "backpack");
		GameRegistry.registerItem(portable_workbench, "portable_workbench");
		GameRegistry.registerItem(black_diamond_helmet, "black_diamond_helmet");
		GameRegistry.registerItem(black_diamond_chestplate, "black_diamond_chestplate");
		GameRegistry.registerItem(black_diamond_leggings, "black_diamond_leggings");
		GameRegistry.registerItem(black_diamond_boots, "black_diamond_boots");
		GameRegistry.registerItem(black_diamond_sword, "black_diamond_sword");
		GameRegistry.registerItem(black_diamond_hoe, "black_diamond_hoe");
		GameRegistry.registerItem(black_diamond_axe, "black_diamond_axe");
		GameRegistry.registerItem(black_diamond_shovel, "black_diamond_shovel");
		GameRegistry.registerItem(black_diamond_pickaxe, "black_diamond_pickaxe");
		GameRegistry.registerItem(black_diamond, "black_diamond");
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

	@Override
	public void addRecipes() {
		// Set repair items
		obsidianToolMaterial.setRepairItem(new ItemStack(Blocks.obsidian));
		blackdiamond.setRepairItem(new ItemStack(black_diamond));

		OreDictionary.registerOre("ingotDarkIron", dark_iron_ingot);
		OreDictionary.registerOre("bucketMilk", wooden_milk_bucket);
		OreDictionary.registerOre("bucketMilk", stone_milk_bucket);
		OreDictionary.registerOre("bucketMilk", golden_milk_bucket);
		OreDictionary.registerOre("bucketMilk", diamond_milk_bucket);
		OreDictionary.registerOre("bucketMilk", obsidian_milk_bucket);
		OreDictionary.registerOre("bucketMilk", Items.milk_bucket);

		// Register chiseleable blocks
		ChiselRegistry.registerBlock(Blocks.diamond_ore, Blocks.stone, Items.diamond, 1, 0);
		ChiselRegistry.registerBlock(Blocks.emerald_ore, Blocks.stone, Items.emerald, 1, 0);
		ChiselRegistry.registerBlock(Blocks.quartz_ore, Blocks.netherrack, Items.quartz, 1, 0);
		ChiselRegistry.registerBlock(Blocks.gold_ore, Blocks.stone, gold_ore_item, 1, 0);
		ChiselRegistry.registerBlock(Blocks.iron_ore, Blocks.stone, iron_ore_item, 1, 0);
		ChiselRegistry.registerBlock(Blocks.redstone_ore, Blocks.stone, Items.redstone, 4, 0);
		ChiselRegistry.registerBlock(Blocks.lit_redstone_ore, Blocks.stone, Items.redstone, 4, 0);
		ChiselRegistry.registerBlock(Blocks.coal_ore, Blocks.stone, Items.coal, 1, 0);
		ChiselRegistry.registerBlock(Blocks.lapis_ore, Blocks.stone, Items.dye, 3, 4);

		// Replace milk buckets
		OreDictionaryHelper.replaceRecipes(new ItemStack(Items.milk_bucket), "bucketMilk", null);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(backpack, 1), new Object[] { "LLS", "LIS", "LLL", 'L', "leather", 'S', "string", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(portable_workbench, 1), new Object[] { "III", "IWI", "III", 'W', "workbench", 'I', "ingotIron" }));

		CraftingManager.getInstance().getRecipeList().add(new BackpackRecipeHandler());
		RecipeSorter.register("Backpack_Recipes", BackpackRecipeHandler.class, Category.SHAPELESS, "after:grim3212core");

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_chisel, 1), new Object[] { " B", "I ", 'B', "ingotIron", 'I', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamond_chisel, 1), new Object[] { " B", "I ", 'B', "gemDiamond", 'I', "stickWood" }));
		chisels = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addSmelting(iron_ore_item, new ItemStack(Items.iron_ingot), 0.4F);
		GameRegistry.addSmelting(gold_ore_item, new ItemStack(Items.gold_ingot), 1F);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tomahawk, 1), new Object[] { "XX ", "XY ", " Y ", 'X', "ingotIron", 'Y', grip }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(throwing_knife, 4), new Object[] { "Y", "X", 'Y', "ingotIron", 'X', grip }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(grip, 2), new Object[] { "Y", "Y", 'Y', "leather" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ammo_part, 1), new Object[] { "#  ", " # ", "  !", '#', "ingotIron", '!', rod_part }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(casing_part, 1), new Object[] { "# ", " #", '#', Items.flint }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spring_part, 1), new Object[] { "#  ", " ! ", "  #", '#', "ingotIron", '!', "string" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(button_part, 1), new Object[] { " # ", "#!#", " # ", '#', "dustRedstone", '!', Blocks.stone_button }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(unloaded_knife, 1), new Object[] { " #", "! ", " @", '#', button_part, '!', spring_part, '@', casing_part }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rod_part, 1), new Object[] { "#", "#", '#', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_pickaxe, 1), new Object[] { "###", " X ", " X ", 'X', "stickWood", '#', black_diamond }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_sword, 1), new Object[] { "#", "#", "X", 'X', "stickWood", '#', black_diamond }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_axe, 1), new Object[] { "## ", "#X ", " X ", 'X', "stickWood", '#', black_diamond }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_hoe, 1), new Object[] { "## ", " X ", " X ", 'X', "stickWood", '#', black_diamond }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_shovel, 1), new Object[] { "#", "X", "X", 'X', "stickWood", '#', black_diamond }));
		blackTools = RecipeHelper.getLatestIRecipes(5);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_helmet, 1), new Object[] { "###", "# #", '#', black_diamond }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_chestplate, 1), new Object[] { "# #", "###", "###", '#', black_diamond }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_leggings, 1), new Object[] { "###", "# #", "# #", '#', black_diamond }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_boots, 1), new Object[] { "# #", "# #", '#', black_diamond }));
		blackArmor = RecipeHelper.getLatestIRecipes(4);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wooden_bucket, 1), new Object[] { "# #", " # ", '#', "slabWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stone_bucket, 1), new Object[] { "# #", " # ", '#', "cobblestone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(golden_bucket, 1), new Object[] { "# #", " # ", '#', "ingotGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamond_bucket, 1), new Object[] { "# #", " # ", '#', "gemDiamond" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(obsidian_bucket, 1), new Object[] { "# #", " # ", '#', "obsidian" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(extinguisher, 1), new Object[] { "FX ", " X ", " T ", 'X', Items.water_bucket, 'F', "dustRedstone", 'T', "ingotIron" }));
		extinguisherRecipe = RecipeHelper.getLatestIRecipe();
		GameRegistry.addShapelessRecipe(new ItemStack(extinguisher, 1), new Object[] { Items.water_bucket, Items.water_bucket, new ItemStack(extinguisher, 1, 1) });
		extinguisherRefillRecipe = RecipeHelper.getLatestIRecipe();

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wood_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "plankWood", 'G', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stone_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "cobblestone", 'G', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gold_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "ingotGold", 'G', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "ingotIron", 'G', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamond_hammer, 1), new Object[] { "MMM", "MMM", " G ", 'M', "gemDiamond", 'G', "stickWood" }));
		hammers = RecipeHelper.getLatestIRecipes(5);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machete_wood, 1), new Object[] { "  X", " X ", "#  ", 'X', "plankWood", '#', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machete_stone, 1), new Object[] { "  X", " X ", "#  ", 'X', "cobblestone", '#', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machete_gold, 1), new Object[] { "  X", " X ", "#  ", 'X', "ingotGold", '#', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machete_iron, 1), new Object[] { "  X", " X ", "#  ", 'X', "ingotIron", '#', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machete_diamond, 1), new Object[] { "  X", " X ", "#  ", 'X', "gemDiamond", '#', "stickWood" }));
		machetes = RecipeHelper.getLatestIRecipes(5);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machete_slime, 1), new Object[] { "  X", " X ", "#  ", 'X', "slimeball", '#', "stickWood" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(building_wand), new Object[] { "PXP", "PXP", "PXP", 'P', "plankWood", 'X', "ingotGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(breaking_wand), new Object[] { "PXP", "PXP", "PXP", 'P', "plankWood", 'X', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mining_wand), new Object[] { "PXP", "PXP", "PXP", 'P', "plankWood", 'X', "gemDiamond" }));
		regular = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(reinforced_building_wand), new Object[] { "OXO", "OXO", "OXO", 'O', Blocks.obsidian, 'X', "blockGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(reinforced_breaking_wand), new Object[] { "OXO", "OXO", "OXO", 'O', Blocks.obsidian, 'X', "blockIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(reinforced_mining_wand), new Object[] { "OXO", "OXO", "OXO", 'O', Blocks.obsidian, 'X', "blockDiamond" }));
		reinforced = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(wooden_multi_tool, 1), new Object[] { Items.wooden_sword, Items.wooden_pickaxe, Items.wooden_shovel, Items.wooden_hoe, Items.wooden_axe, "logWood", "logWood", "logWood", "logWood" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(stone_multi_tool, 1), new Object[] { Items.stone_sword, Items.stone_pickaxe, Items.stone_shovel, Items.stone_hoe, Items.stone_axe, "cobblestone", "cobblestone", "cobblestone", "cobblestone" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(iron_multi_tool, 1), new Object[] { Items.iron_sword, Items.iron_pickaxe, Items.iron_shovel, Items.iron_hoe, Items.iron_axe, "ingotIron", "ingotIron", "ingotIron", "ingotIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(golden_multi_tool, 1), new Object[] { Items.golden_sword, Items.golden_pickaxe, Items.golden_shovel, Items.golden_hoe, Items.golden_axe, "ingotGold", "ingotGold", "ingotGold", "ingotGold" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(diamond_multi_tool, 1), new Object[] { Items.diamond_sword, Items.diamond_pickaxe, Items.diamond_shovel, Items.diamond_hoe, Items.diamond_axe, "gemDiamond", "gemDiamond", "gemDiamond", "gemDiamond" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(obsidian_multi_tool, 1), new Object[] { diamond_multi_tool, golden_multi_tool, wooden_multi_tool, stone_multi_tool, iron_multi_tool, Blocks.obsidian, "obsidian", "obsidian", "obsidian" }));
		tools = RecipeHelper.getLatestIRecipes(6);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pokeball), new Object[] { "RRR", "CBC", "III", 'R', "dustRedstone", 'C', Items.coal, 'B', Blocks.stone_button, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(powerstaff, 1), new Object[] { "IDI", "IRI", " I ", 'D', "gemDiamond", 'R', "dustRedstone", 'I', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(empty_energy_canister, 2), new Object[] { "YXY", "Y Y", "YXY", 'X', "ingotIron", 'Y', "blockGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(energy_canister, 1), new Object[] { "Y", "X", 'X', empty_energy_canister, 'Y', element_115 }));
		basicCanisters = RecipeHelper.getLatestIRecipes(2);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ray_gun, 1), new Object[] { "XZ ", " XY", " XX", 'X', "ingotIron", 'Y', element_115, 'Z', "dustRedstone" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dark_iron_ingot, 1), new Object[] { "XYX", 'X', element_115, 'Y', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advanced_empty_energy_canister, 2), new Object[] { "YXY", "Y Y", "YXY", 'X', "ingotDarkIron", 'Y', "blockGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advanced_energy_canister, 1), new Object[] { "XY", "YY", 'X', advanced_empty_energy_canister, 'Y', element_115 }));
		advCanisters = RecipeHelper.getLatestIRecipes(2);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(advanced_ray_gun, 1), new Object[] { "XZ ", " XY", " XX", 'X', "ingotDarkIron", 'Y', ray_gun, 'Z', "dustRedstone" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sling_shot, 1), new Object[] { "#@#", " # ", " # ", '#', "stickWood", '@', Items.string }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sling_pellet, 2), new Object[] { "#", '#', "dirt" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sling_pellet, 2), new Object[] { "#", '#', "cobblestone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sling_pellet, 4), new Object[] { "#", '#', "stone" }));
		pellets = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spear, 1), new Object[] { "#  ", " X ", "  X", '#', Items.flint, 'X', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spear, 1), new Object[] { "XX#", '#', Items.flint, 'X', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_spear, 1), new Object[] { "#", "X", '#', "ingotIron", 'X', spear }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamond_spear, 1), new Object[] { "#", "X", '#', "gemDiamond", 'X', spear }));
		basics = RecipeHelper.getLatestIRecipes(4);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(explosive_spear, 1), new Object[] { " # ", "# #", " X ", '#', "gunpowder", 'X', spear }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fire_spear, 1), new Object[] { "#", "X", '#', Items.coal, 'X', spear }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(slime_spear, 1), new Object[] { "#", "X", '#', "slimeball", 'X', spear }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(light_spear, 1), new Object[] { "#", "X", '#', "torch", 'X', spear }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lightning_spear, 1), new Object[] { "U", "#", "X", 'U', Items.water_bucket, '#', "dustRedstone", 'X', iron_spear }));
		specials = RecipeHelper.getLatestIRecipes(5);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ultimate_fist, 1), new Object[] { "XXX", "X##", "XXX", '#', "leather", 'X', "blockDiamond" }));

		ItemStack emptyStack = new ItemStack(mask, 1, 0);
		GameRegistry.addRecipe(new ShapedOreRecipe(emptyStack, new Object[] { "YXY", 'Y', "string", 'X', "paper" }));
		emptyRecipe = RecipeHelper.getLatestIRecipe();

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 1), new Object[] { emptyStack, "dyeBrown", "dyeYellow", "dyeOrange" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 2), new Object[] { emptyStack, "dyeBlue", "dyeRed", "dyeCyan" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 3), new Object[] { emptyStack, "dyeWhite", "dyeRed", "dyeOrange" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 4), new Object[] { emptyStack, "dyeWhite", "dyeBrown", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 5), new Object[] { emptyStack, "dyeGreen", "dyeBlack", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 6), new Object[] { emptyStack, "dyeBlack", "dyeMagenta", "dyePurple" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 7), new Object[] { emptyStack, "dyeWhite", "dyeGray", "dyeLightGray" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 8), new Object[] { emptyStack, "dyeBlack", "dyeRed", "dyeYellow" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 9), new Object[] { emptyStack, "dyePink", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 10), new Object[] { emptyStack, "dyePink", "dyeWhite", "dyeBrown" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 11), new Object[] { emptyStack, "dyeRed", "dyeBlack", "dyeWhite" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 12), new Object[] { emptyStack, "dyeWhite", "dyeGray" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 13), new Object[] { emptyStack, "dyeGreen", "dyeLime" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 14), new Object[] { emptyStack, "dyeRed", "dyeBlack", "dyeGray" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 15), new Object[] { emptyStack, "dyeBlue", "dyeCyan", "dyeLightBlue" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 16), new Object[] { emptyStack, "dyeLime", "dyeBrown", "dyeOrange" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 17), new Object[] { emptyStack, "dyeBlack", "dyeBrown", "dyeLightGray" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 18), new Object[] { emptyStack, "dyeGreen", "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(mask, 1, 19), new Object[] { emptyStack, "dyeLime", "dyePink", "dyeGray" }));
		mobs = RecipeHelper.getLatestIRecipes(19);
	}
}