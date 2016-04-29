package com.grim3212.mc.industry.item;

import java.util.List;

import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.industry.GrimIndustry;
import com.grim3212.mc.industry.block.IndustryBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

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

	public static ArmorMaterial antiRadiation = EnumHelper.addArmorMaterial("antiRadiation", GrimIndustry.modID + ":radiation", 5, new int[] { 2, 5, 3, 1 }, 15);
	public static ArmorMaterial gravboots = EnumHelper.addArmorMaterial("gravityboots", GrimIndustry.modID + ":gravity", 15, new int[] { 2, 6, 5, 2 }, 9);

	@Override
	public void initItems() {
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

	@Override
	public void addRecipes() {
		OreDictionary.registerOre("ingotUranium", uranium_ingot);
		OreDictionary.registerOre("ingotRefinedUranium", refined_uranium);
		OreDictionary.registerOre("ingotPlutonium", plutonium_ingot);
		OreDictionary.registerOre("ingotRefinedPlutonium", refined_plutonium);
		OreDictionary.registerOre("ingotAluminum", aluminum_ingot);

		GameRegistry.addSmelting(Items.flint, new ItemStack(graphite), 0.35F);
		GameRegistry.addSmelting(Items.iron_ingot, new ItemStack(aluminum_ingot), 0.35F);

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
