package com.grim3212.mc.pack.tools.client;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.tools.blocks.ToolsBlocks;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ManualTools implements IManualPart {

	public static ManualTools INSTANCE = new ManualTools();

	public static Page backpack_page;
	public static Page backpackColors_page;
	public static Page portableWorkbench_page;
	public static Page blackOre_page;
	public static Page blackDiamond_page;
	public static Page blackBlocks_page;
	public static Page blackTools_page;
	public static Page blackArmor_page;
	public static Page woodBucket_page;
	public static Page stoneBucket_page;
	public static Page goldBucket_page;
	public static Page diamondBucket_page;
	public static Page obsidianBucket_page;
	public static Page milkBucket_page;
	public static Page grip_page;
	public static Page spring_page;
	public static Page button_page;
	public static Page rod_page;
	public static Page ballistic_page;
	public static Page ballisticKnife_page;
	public static Page throwingKnife_page;
	public static Page tomahawk_page;
	public static Page chisel_page;
	public static Page chiselOre_page;
	public static Page extinguisher_page;
	public static Page extinguisherRefill_page;
	public static Page hammer_page;
	public static Page machete_page;
	public static Page macheteSlime_page;
	public static Page wandInfo_page;
	public static Page regularWand_page;
	public static Page reinforcedWand_page;
	public static Page multiTool_page;
	public static Page pokeball_page;
	public static Page powerstaff_page;
	public static Page element115_page;
	public static Page canister_page;
	public static Page raygun_page;
	public static Page darkIron_page;
	public static Page advCanister_page;
	public static Page advRaygun_page;
	public static Page pellets_page;
	public static Page specialSlingpellets_page;
	public static Page slingshot_page;
	public static Page spears_page;
	public static Page specialSpears_page;
	public static Page ultimateFist_page;
	public static Page emptyMask_page;
	public static Page mobMask_page;
	public static Page boomerang_page;
	public static Page diamondBoomerang_page;

	@Override
	public void initPages() {
		boomerang_page = new PageCrafting("wood", new ItemStack(ToolsItems.boomerang));
		diamondBoomerang_page = new PageCrafting("diamond", new ItemStack(ToolsItems.diamond_boomerang));
		backpack_page = new PageCrafting("backpacks", new ItemStack(ToolsItems.backpack));
		backpackColors_page = new PageImageText("colors", "colorsImage.png");
		portableWorkbench_page = new PageCrafting("portableworkbench", new ItemStack(ToolsItems.portable_workbench));
		blackOre_page = new PageImageText("blackore", "blackore.png");
		blackDiamond_page = new PageFurnace("cookOre", new ItemStack(ToolsBlocks.black_diamond_ore));
		blackBlocks_page = new PageCrafting("craftblack", ToolsBlocks.black, 25);
		blackTools_page = new PageCrafting("tools", ToolsItems.blackTools, 18);
		blackArmor_page = new PageCrafting("armor", ToolsItems.blackArmor, 18);
		woodBucket_page = new PageCrafting("wooden", new ItemStack(ToolsItems.wooden_bucket));
		stoneBucket_page = new PageCrafting("stone", new ItemStack(ToolsItems.stone_bucket));
		goldBucket_page = new PageCrafting("golden", new ItemStack(ToolsItems.golden_bucket));
		diamondBucket_page = new PageCrafting("diamond", new ItemStack(ToolsItems.diamond_bucket));
		obsidianBucket_page = new PageCrafting("obsidian", new ItemStack(ToolsItems.obsidian_bucket));
		milkBucket_page = new PageCrafting("milk", RecipeHelper.getQuickIRecipeForItemStack(new ItemStack(Items.CAKE)));
		grip_page = new PageCrafting("grip", new ItemStack(ToolsItems.grip));
		spring_page = new PageCrafting("part2", new ItemStack(ToolsItems.spring_part));
		button_page = new PageCrafting("part3", new ItemStack(ToolsItems.button_part));
		rod_page = new PageCrafting("part4", new ItemStack(ToolsItems.rod_part));
		ballistic_page = new PageCrafting("ballistic", new ItemStack(ToolsItems.unloaded_knife));
		ballisticKnife_page = new PageCrafting("knives", new ItemStack(ToolsItems.ammo_part));
		throwingKnife_page = new PageCrafting("knife", new ItemStack(ToolsItems.throwing_knife));
		tomahawk_page = new PageCrafting("tomahawk", new ItemStack(ToolsItems.tomahawk));
		chisel_page = new PageCrafting("chisels", ToolsItems.chisels, 25);
		chiselOre_page = new PageFurnace("ore", new ItemStack[] { new ItemStack(ToolsItems.gold_ore_item), new ItemStack(ToolsItems.iron_ore_item) }, 25);
		extinguisher_page = new PageCrafting("extinguisher", ToolsItems.extinguisherRecipe);
		extinguisherRefill_page = new PageCrafting("refill", ToolsItems.extinguisherRefillRecipe);
		hammer_page = new PageCrafting("recipes", ToolsItems.hammers, 20);
		machete_page = new PageCrafting("base", ToolsItems.machetes, 20);
		macheteSlime_page = new PageCrafting("slime", new ItemStack(ToolsItems.machete_slime));
		wandInfo_page = new PageInfo("info");
		regularWand_page = new PageCrafting("regular", ToolsItems.regular, 20);
		reinforcedWand_page = new PageCrafting("reinforced", ToolsItems.reinforced, 20);
		multiTool_page = new PageCrafting("tools", ToolsItems.tools, 20);
		pokeball_page = new PageCrafting("recipe", new ItemStack(ToolsItems.pokeball));
		powerstaff_page = new PageCrafting("recipe", new ItemStack(ToolsItems.powerstaff));
		element115_page = new PageImageText("element", "element115.png");
		canister_page = new PageCrafting("canisters", ToolsItems.basicCanisters, 25);
		raygun_page = new PageCrafting("raygun", new ItemStack(ToolsItems.ray_gun));
		darkIron_page = new PageCrafting("darkIron", new ItemStack(ToolsItems.dark_iron_ingot));
		advCanister_page = new PageCrafting("advCanisters", ToolsItems.advCanisters, 25);
		advRaygun_page = new PageCrafting("advraygun", new ItemStack(ToolsItems.advanced_ray_gun));
		pellets_page = new PageCrafting("pellets", ToolsItems.pellets, 20);
		slingshot_page = new PageCrafting("slingshot", new ItemStack(ToolsItems.sling_shot));
		specialSlingpellets_page = new PageCrafting("specials", ToolsItems.specials, 20);
		spears_page = new PageCrafting("basics", ToolsItems.basics, 20);
		specialSpears_page = new PageCrafting("specials", ToolsItems.specials, 20); //Maybe duplication SH
		spears_page = new PageCrafting("basics", ToolsItems.basics, 20);
		specialSpears_page = new PageCrafting("specials", ToolsItems.specials, 20);
		ultimateFist_page = new PageCrafting("fist", new ItemStack(ToolsItems.ultimate_fist));
		emptyMask_page = new PageCrafting("empty", ToolsItems.emptyRecipe);
		mobMask_page = new PageCrafting("mobs", ToolsItems.mobs, 15);
	}

	@Override
	public void registerChapters(ManualPart part) {
		ManualRegistry.addChapter("backpacks", part).addPages(backpack_page, backpackColors_page);
		ManualRegistry.addChapter("portable", part).addPages(portableWorkbench_page);
		ManualRegistry.addChapter("black", part).addPages(blackOre_page, blackDiamond_page, blackBlocks_page);
		ManualRegistry.addChapter("tools", part).addPages(blackTools_page, blackArmor_page);
		ManualRegistry.addChapter("buckets", part).addPages(woodBucket_page, stoneBucket_page, goldBucket_page, diamondBucket_page, obsidianBucket_page, milkBucket_page);
		ManualRegistry.addChapter("ballistic", part).addPages(grip_page, spring_page, button_page, rod_page, ballistic_page, ballisticKnife_page);
		ManualRegistry.addChapter("knives", part).addPages(throwingKnife_page, tomahawk_page);
		ManualRegistry.addChapter("boomerang", part).addPages(boomerang_page, diamondBoomerang_page);
		ManualRegistry.addChapter("chisels", part).addPages(chisel_page, chiselOre_page);
		ManualRegistry.addChapter("extinguisher", part).addPages(extinguisher_page, extinguisherRefill_page);
		ManualRegistry.addChapter("hammers", part).addPages(hammer_page);
		ManualRegistry.addChapter("machetes", part).addPages(machete_page, macheteSlime_page);
		ManualRegistry.addChapter("wands", part).addPages(wandInfo_page, regularWand_page, reinforcedWand_page);
		ManualRegistry.addChapter("multi", part).addPages(multiTool_page);
		ManualRegistry.addChapter("pokeball", part).addPages(pokeball_page);
		ManualRegistry.addChapter("staff", part).addPages(powerstaff_page);
		ManualRegistry.addChapter("raygun", part).addPages(element115_page, canister_page, raygun_page, darkIron_page, advCanister_page, advRaygun_page);
		ManualRegistry.addChapter("sling", part).addPages(pellets_page, slingshot_page, specialSlingpellets_page);
		ManualRegistry.addChapter("sling", part).addPages(pellets_page, slingshot_page);
		ManualRegistry.addChapter("spears", part).addPages(spears_page, specialSpears_page);
		ManualRegistry.addChapter("ultimate", part).addPages(ultimateFist_page);
		ManualRegistry.addChapter("masks", part).addPages(emptyMask_page, mobMask_page);
	}

}
