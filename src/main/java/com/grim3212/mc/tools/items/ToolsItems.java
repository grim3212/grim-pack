package com.grim3212.mc.tools.items;

import java.util.List;

import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.OreDictionaryHelper;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.items.ItemBetterBucket.BucketType;
import com.grim3212.mc.tools.util.BackpackRecipeHandler;

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

public class ToolsItems implements IPartItems {

	public static Item backpack;
	public static Item portable_workbench;
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
	// Milk buckets
	public static Item wooden_milk_bucket;
	public static Item stone_milk_bucket;
	public static Item golden_milk_bucket;
	public static Item diamond_milk_bucket;
	public static Item obsidian_milk_bucket;

	public static ToolMaterial blackdiamond = EnumHelper.addToolMaterial("black_diamond", 3, 3122, 15F, 3F, 20);
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

	@Override
	public void addRecipes() {
		OreDictionary.registerOre("bucketMilk", wooden_milk_bucket);
		OreDictionary.registerOre("bucketMilk", stone_milk_bucket);
		OreDictionary.registerOre("bucketMilk", golden_milk_bucket);
		OreDictionary.registerOre("bucketMilk", diamond_milk_bucket);
		OreDictionary.registerOre("bucketMilk", obsidian_milk_bucket);
		OreDictionary.registerOre("bucketMilk", Items.milk_bucket);

		OreDictionaryHelper.replaceRecipes(new ItemStack(Items.milk_bucket), "bucketMilk", null);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(backpack, 1), new Object[] { "LLS", "LIS", "LLL", 'L', "leather", 'S', "string", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(portable_workbench, 1), new Object[] { "III", "IWI", "III", 'W', "workbench", 'I', "ingotIron" }));

		CraftingManager.getInstance().getRecipeList().add(new BackpackRecipeHandler());
		RecipeSorter.register("Backpack_Recipes", BackpackRecipeHandler.class, Category.SHAPELESS, "after:grim3212core");

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

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wooden_bucket, 1), new Object[] { "# #", " # ", '#', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stone_bucket, 1), new Object[] { "# #", " # ", '#', "stone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(golden_bucket, 1), new Object[] { "# #", " # ", '#', "ingotGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamond_bucket, 1), new Object[] { "# #", " # ", '#', "gemDiamond" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(obsidian_bucket, 1), new Object[] { "# #", " # ", '#', "obsidian" }));
	}

}
