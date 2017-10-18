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
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.init.ToolsRecipes;
import com.grim3212.mc.pack.tools.items.ToolsItems;

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
	public static Page spears_page;
	public static Page specialSpears_page;
	public static Page ultimateFist_page;
	public static Page emptyMask_page;
	public static Page mobMask_page;
	public static Page boomerang_page;
	public static Page diamondBoomerang_page;
	public static Page pellets_page;
	public static Page slingshot_page;
	public static Page pelletBag_page;
	public static Page grenadeLauncher_page;
	public static Page neptuneStaff_page;
	public static Page phoenixStaff_page;
	public static Page chickenSuit_page;
	public static Page detonators_page;

	@Override
	public void initPages() {
		if (ToolsConfig.subpartBoomerangs) {
			boomerang_page = new PageCrafting("wood", new ItemStack(ToolsItems.boomerang));
			diamondBoomerang_page = new PageCrafting("diamond", new ItemStack(ToolsItems.diamond_boomerang));
		}

		if (ToolsConfig.subpartChickenSuit) {
			chickenSuit_page = new PageCrafting("recipes", 20, new ItemStack[] { new ItemStack(ToolsItems.chicken_suit_helmet), new ItemStack(ToolsItems.chicken_suit_chestplate), new ItemStack(ToolsItems.chicken_suit_leggings), new ItemStack(ToolsItems.chicken_suit_boots) });
		}

		if (ToolsConfig.subpartBackpacks) {
			backpack_page = new PageCrafting("backpacks", new ItemStack(ToolsItems.backpack));
			backpackColors_page = new PageImageText("colors", "colors_image.png");
		}

		if (ToolsConfig.subpartStaffs) {
			neptuneStaff_page = new PageCrafting("neptune", new ItemStack(ToolsItems.neptune_staff));
			phoenixStaff_page = new PageCrafting("phoenix", new ItemStack(ToolsItems.phoenix_staff));
		}

		if (ToolsConfig.subpartGrenadeLauncher)
			grenadeLauncher_page = new PageCrafting("grenade", 25, new ItemStack(ToolsItems.grenade_launcher), new ItemStack(ToolsItems.grenade));

		if (ToolsConfig.subpartPortableWorkbench)
			portableWorkbench_page = new PageCrafting("portableworkbench", new ItemStack(ToolsItems.portable_workbench));

		if (ToolsConfig.subpartBlackDiamond) {
			blackOre_page = new PageImageText("blackore", "blackore.png");
			blackDiamond_page = new PageFurnace("cookOre", new ItemStack(ToolsBlocks.black_diamond_ore));
			blackBlocks_page = new PageCrafting("craftblack", ToolsRecipes.black, 25);
			blackTools_page = new PageCrafting("tools", ToolsRecipes.blackTools, 18);
			blackArmor_page = new PageCrafting("armor", ToolsRecipes.blackArmor, 18);
		}

		if (ToolsConfig.subpartBuckets) {
			woodBucket_page = new PageCrafting("wooden", new ItemStack(ToolsItems.wooden_bucket));
			stoneBucket_page = new PageCrafting("stone", new ItemStack(ToolsItems.stone_bucket));
			goldBucket_page = new PageCrafting("golden", new ItemStack(ToolsItems.golden_bucket));
			diamondBucket_page = new PageCrafting("diamond", new ItemStack(ToolsItems.diamond_bucket));
			obsidianBucket_page = new PageCrafting("obsidian", new ItemStack(ToolsItems.obsidian_bucket));
			milkBucket_page = new PageCrafting("milk", RecipeHelper.getRecipePath("minecraft:cake"));
		}

		if (ToolsConfig.subpartKnives) {
			grip_page = new PageCrafting("grip", new ItemStack(ToolsItems.grip));
			spring_page = new PageCrafting("part2", new ItemStack(ToolsItems.spring_part));
			button_page = new PageCrafting("part3", new ItemStack(ToolsItems.button_part));
			ballistic_page = new PageCrafting("ballistic", new ItemStack(ToolsItems.unloaded_knife));
			ballisticKnife_page = new PageCrafting("knives", new ItemStack(ToolsItems.ammo_part));
			throwingKnife_page = new PageCrafting("knife", new ItemStack(ToolsItems.throwing_knife));
			tomahawk_page = new PageCrafting("tomahawk", new ItemStack(ToolsItems.tomahawk));
		}

		if (ToolsConfig.subpartChisel) {
			chisel_page = new PageCrafting("chisels", ToolsRecipes.chisels, 25);
			chiselOre_page = new PageFurnace("ore", new ItemStack[] { new ItemStack(ToolsItems.gold_ore_item), new ItemStack(ToolsItems.iron_ore_item) }, 25);
		}

		if (ToolsConfig.subpartExtinguisher) {
			extinguisher_page = new PageCrafting("extinguisher", ToolsRecipes.extinguisherRecipe);
			extinguisherRefill_page = new PageCrafting("refill", ToolsRecipes.extinguisherRefillRecipe);
		}

		if (ToolsConfig.subpartHammers)
			hammer_page = new PageCrafting("recipes", ToolsRecipes.hammers, 20);

		if (ToolsConfig.subpartMachetes) {
			machete_page = new PageCrafting("base", ToolsRecipes.machetes, 20);
			macheteSlime_page = new PageCrafting("slime", new ItemStack(ToolsItems.machete_slime));
		}

		if (ToolsConfig.subpartWands) {
			wandInfo_page = new PageInfo("info");
			regularWand_page = new PageCrafting("regular", ToolsRecipes.regular, 20);
			reinforcedWand_page = new PageCrafting("reinforced", ToolsRecipes.reinforced, 20).appendImageUrl("spare_ores.png");
		}

		if (ToolsConfig.subpartMultiTools)
			multiTool_page = new PageCrafting("tools", ToolsRecipes.tools, 20);

		if (ToolsConfig.subpartPokeball)
			pokeball_page = new PageCrafting("recipe", new ItemStack(ToolsItems.pokeball));

		if (ToolsConfig.subpartPowerstaff)
			powerstaff_page = new PageCrafting("recipe", new ItemStack(ToolsItems.powerstaff));

		if (ToolsConfig.subpartRayGuns) {
			element115_page = new PageImageText("element", "element115.png");
			canister_page = new PageCrafting("canisters", ToolsRecipes.basicCanisters, 25);
			raygun_page = new PageCrafting("raygun", new ItemStack(ToolsItems.ray_gun));
			darkIron_page = new PageCrafting("darkIron", new ItemStack(ToolsItems.dark_iron_ingot));
			advCanister_page = new PageCrafting("advCanisters", ToolsRecipes.advCanisters, 25);
			advRaygun_page = new PageCrafting("advraygun", new ItemStack(ToolsItems.advanced_ray_gun));
		}

		if (ToolsConfig.subpartSpears) {
			spears_page = new PageCrafting("basics", ToolsRecipes.basics, 20);
			specialSpears_page = new PageCrafting("specials", ToolsRecipes.specials, 20);
		}

		if (ToolsConfig.subpartUltimateFist)
			ultimateFist_page = new PageCrafting("fist", new ItemStack(ToolsItems.ultimate_fist));

		if (ToolsConfig.subpartMasks) {
			emptyMask_page = new PageCrafting("empty", ToolsRecipes.emptyRecipe);
			mobMask_page = new PageCrafting("mobs", ToolsRecipes.mobs, 15);
		}

		if (ToolsConfig.subpartSlingshots) {
			pellets_page = new PageCrafting("pellets", ToolsRecipes.pellets, 20);
			slingshot_page = new PageCrafting("slingshot", new ItemStack(ToolsItems.sling_shot));
			pelletBag_page = new PageCrafting("pelletBag", new ItemStack(ToolsItems.pellet_bag));
		}

		if (ToolsConfig.subpartDetonators) {
			detonators_page = new PageCrafting("recipes", 20, new ItemStack[] { new ItemStack(ToolsItems.thermal_detonator), new ItemStack(ToolsItems.solar_detonator), new ItemStack(ToolsItems.nukeulator) });
		}
	}

	@Override
	public void registerChapters(ManualPart part) {
		if (ToolsConfig.subpartBackpacks)
			ManualRegistry.addChapter("backpacks", part).addPages(backpack_page, backpackColors_page);

		if (ToolsConfig.subpartPortableWorkbench)
			ManualRegistry.addChapter("portable", part).addPages(portableWorkbench_page);

		if (ToolsConfig.subpartBlackDiamond) {
			ManualRegistry.addChapter("black", part).addPages(blackOre_page, blackDiamond_page, blackBlocks_page);
			ManualRegistry.addChapter("tools", part).addPages(blackTools_page, blackArmor_page);
		}

		if (ToolsConfig.subpartBuckets)
			ManualRegistry.addChapter("buckets", part).addPages(woodBucket_page, stoneBucket_page, goldBucket_page, diamondBucket_page, obsidianBucket_page, milkBucket_page);

		if (ToolsConfig.subpartKnives) {
			ManualRegistry.addChapter("ballistic", part).addPages(grip_page, spring_page, button_page, ballistic_page, ballisticKnife_page);
			ManualRegistry.addChapter("knives", part).addPages(throwingKnife_page, tomahawk_page);
		}

		if (ToolsConfig.subpartBoomerangs)
			ManualRegistry.addChapter("boomerang", part).addPages(boomerang_page, diamondBoomerang_page).addImageUrl("boomerang.png");

		if (ToolsConfig.subpartChisel)
			ManualRegistry.addChapter("chisels", part).addPages(chisel_page, chiselOre_page);

		if (ToolsConfig.subpartExtinguisher)
			ManualRegistry.addChapter("extinguisher", part).addPages(extinguisher_page, extinguisherRefill_page);

		if (ToolsConfig.subpartHammers)
			ManualRegistry.addChapter("hammers", part).addPages(hammer_page);

		if (ToolsConfig.subpartMachetes)
			ManualRegistry.addChapter("machetes", part).addPages(machete_page, macheteSlime_page);

		if (ToolsConfig.subpartWands)
			ManualRegistry.addChapter("wands", part).addPages(wandInfo_page, regularWand_page, reinforcedWand_page);

		if (ToolsConfig.subpartMultiTools)
			ManualRegistry.addChapter("multi", part).addPages(multiTool_page);

		if (ToolsConfig.subpartPokeball)
			ManualRegistry.addChapter("pokeball", part).addPages(pokeball_page);

		if (ToolsConfig.subpartPowerstaff || ToolsConfig.subpartStaffs)
			ManualRegistry.addChapter("staff", part).addPages(powerstaff_page, neptuneStaff_page, phoenixStaff_page);

		if (ToolsConfig.subpartRayGuns)
			ManualRegistry.addChapter("raygun", part).addPages(element115_page, canister_page, raygun_page, darkIron_page, advCanister_page, advRaygun_page);

		if (ToolsConfig.subpartSlingshots)
			ManualRegistry.addChapter("sling", part).addPages(pellets_page, slingshot_page, pelletBag_page);

		if (ToolsConfig.subpartSpears)
			ManualRegistry.addChapter("spears", part).addPages(spears_page, specialSpears_page);

		if (ToolsConfig.subpartGrenadeLauncher)
			ManualRegistry.addChapter("grenade", part).addPages(grenadeLauncher_page);

		if (ToolsConfig.subpartUltimateFist)
			ManualRegistry.addChapter("ultimate", part).addPages(ultimateFist_page);

		if (ToolsConfig.subpartMasks)
			ManualRegistry.addChapter("masks", part).addPages(emptyMask_page, mobMask_page);

		if (ToolsConfig.subpartChickenSuit)
			ManualRegistry.addChapter("chicken_suit", part).addPages(chickenSuit_page);

		if (ToolsConfig.subpartDetonators)
			ManualRegistry.addChapter("detonators", part).addPages(detonators_page);
	}
}