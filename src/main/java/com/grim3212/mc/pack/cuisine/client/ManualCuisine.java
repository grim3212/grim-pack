package com.grim3212.mc.pack.cuisine.client;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.cuisine.GrimCuisine;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.cuisine.init.CuisineNames;

public class ManualCuisine implements IManualPart {

	public static ManualCuisine INSTANCE = new ManualCuisine();

	public static Page soda_page;
	public static Page carbon_page;
	public static Page dragonFruit_page;
	public static Page sweets_page;
	public static Page health_page;
	public static Page cheeseMaker_page;
	public static Page butterChurn_page;
	public static Page cheeseBlock_page;
	public static Page extras_page;
	public static Page sandwiches_page;
	public static Page eggs_page;
	public static Page cookedEggs_page;
	public static Page rawPie_page;
	public static Page bakedPie_page;
	public static Page cocoaTree_page;
	public static Page cocoaFruit_page;
	public static Page cocoaDye_page;
	public static Page chocolateBowl_page;
	public static Page hotChocolate_page;
	public static Page chocolateBall_page;
	public static Page chocolateCake_page;
	public static Page chocolateMould_page;
	public static Page chocolateBars_page;
	public static Page chocolateCandy_page;

	@Override
	public void initPages() {
		if (CuisineConfig.subpartSoda.get()) {
			soda_page = new PageCrafting("types", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.SODA_APPLE), RecipeHelper.getRecipePath(CuisineNames.SODA_ORANGE), RecipeHelper.getRecipePath(CuisineNames.SODA_CREAM_ORANGE), RecipeHelper.getRecipePath(CuisineNames.SODA_GOLDEN_APPLE), RecipeHelper.getRecipePath(CuisineNames.SODA_DIAMOND), RecipeHelper.getRecipePath(CuisineNames.SODA_MUSHROOM), RecipeHelper.getRecipePath(CuisineNames.SODA_ROOT_BEER),
					RecipeHelper.getRecipePath(CuisineNames.SODA_SLURM), RecipeHelper.getRecipePath(CuisineNames.SODA_COCOA), RecipeHelper.getRecipePath(CuisineNames.SODA_SPIKED_ORANGE)), 15);
			carbon_page = new PageCrafting("carbon", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.BOTTLE), RecipeHelper.getRecipePath(CuisineNames.CO2), RecipeHelper.getRecipePath(CuisineNames.SODA_CARBONATED_WATER)), 25);
		}

		if (CuisineConfig.subpartDragonFruit.get())
			dragonFruit_page = new PageImageText("dragonfruit", "dragon_fruit_page.png");

		if (CuisineConfig.subpartHealth.get()) {
			sweets_page = new PageCrafting("sweets", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.SWEETS), RecipeHelper.getRecipePath(CuisineNames.POWERED_SUGAR), RecipeHelper.getRecipePath(CuisineNames.POWERED_SWEETS)), 25);
			health_page = new PageCrafting("recipes", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.BANDAGE), RecipeHelper.getRecipePath(CuisineNames.HEALTHPACK), RecipeHelper.getRecipePath(CuisineNames.HEALTHPACK_SUPER)), 25);
		}

		if (CuisineConfig.subpartDairy.get()) {
			cheeseMaker_page = new PageCrafting("cheese", RecipeHelper.getRecipePath(CuisineNames.CHEESE_MAKER));
			butterChurn_page = new PageCrafting("butter", RecipeHelper.getRecipePath(CuisineNames.BUTTER_CHURN)).appendImageUrl("butter.png");
			cheeseBlock_page = new PageCrafting("cheeseblock", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.CHEESE), RecipeHelper.getRecipePath(CuisineNames.CHEESE_BLOCK)), 25);
			sandwiches_page = new PageCrafting("sandwiches", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.KNIFE), RecipeHelper.getRecipePath(CuisineNames.BREAD_SLICE), RecipeHelper.getRecipePath(CuisineNames.CHEESE_BURGER), RecipeHelper.getRecipePath(CuisineNames.HOT_CHEESE)), 25);
			eggs_page = new PageCrafting("eggs", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.MIXER), RecipeHelper.getRecipePath(CuisineNames.EGGS_UNMIXED), RecipeHelper.getRecipePath(CuisineNames.EGGS_MIXED)), 25);
			cookedEggs_page = new PageFurnace("cooked", RecipeHelper.getRecipePath(CuisineNames.EGGS_COOKED));
		}

		if (CuisineConfig.subpartPie.get()) {
			extras_page = new PageCrafting("extras", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.PAN), RecipeHelper.getRecipePath(CuisineNames.DOUGH), RecipeHelper.getRecipePath(CuisineNames.KNIFE), RecipeHelper.getRecipePath(CuisineNames.PUMPKIN_SLICE)), 25);
			rawPie_page = new PageCrafting("craft", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.RAW_EMPTY_PIE), RecipeHelper.getRecipePath(CuisineNames.RAW_APPLE_PIE), RecipeHelper.getRecipePath(CuisineNames.RAW_MELON_PIE), RecipeHelper.getRecipePath(CuisineNames.RAW_PUMPKIN_PIE), RecipeHelper.getRecipePath(CuisineNames.RAW_PORK_PIE), RecipeHelper.getRecipePath(CuisineNames.RAW_CHOCOLATE_PIE)), 25);
			bakedPie_page = new PageFurnace("bake", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.APPLE_PIE), RecipeHelper.getRecipePath(CuisineNames.MELON_PIE), RecipeHelper.getRecipePath(CuisineNames.PUMPKIN_PIE), RecipeHelper.getRecipePath(CuisineNames.PORK_PIE), RecipeHelper.getRecipePath(CuisineNames.CHOCOLATE_PIE)), 25);
		}

		if (CuisineConfig.subpartChocolate.get()) {
			cocoaTree_page = new PageImageText("tree", "cocoa_tree_page.png");
			cocoaFruit_page = new PageCrafting("fruit", RecipeHelper.getRecipePath(CuisineNames.COCOA_DUST));
			cocoaDye_page = new PageCrafting("dye", RecipeHelper.getRecipePath(GrimCuisine.partId, "cocoa_beans"));
			chocolateBowl_page = new PageCrafting("bowlChoc", RecipeHelper.getRecipePath(CuisineNames.CHOCOLATE_BOWL));
			hotChocolate_page = new PageFurnace("bowlChocHot", RecipeHelper.getRecipePath(CuisineNames.CHOCOLATE_BOWL_HOT));
			chocolateBall_page = new PageCrafting("chocBall", RecipeHelper.getRecipePath(CuisineNames.CHOCOLATE_BALL));
			chocolateCake_page = new PageCrafting("cake", RecipeHelper.getRecipePath(CuisineNames.CHOCOLATE_CAKE));
			chocolateMould_page = new PageCrafting("mould", RecipeHelper.getRecipePath(CuisineNames.CHOCOLATE_BAR_MOULD)).appendImageUrl("chocolate_mould.png");
			chocolateBars_page = new PageCrafting("bars", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.CHOCOLATE_BAR), RecipeHelper.getRecipePath(CuisineNames.CHOCOLATE_BLOCK)), 25);
			chocolateCandy_page = new PageCrafting("candy", ImmutableList.of(RecipeHelper.getRecipePath(CuisineNames.WRAPPER), RecipeHelper.getRecipePath(CuisineNames.CHOCOLATE_BAR_WRAPPED)), 25);
		}
	}

	@Override
	public void registerChapters(ManualPart modPart) {
		if (CuisineConfig.subpartSoda.get())
			ManualRegistry.addChapter("soda", modPart).addPages(carbon_page, soda_page);

		if (CuisineConfig.subpartDragonFruit.get())
			ManualRegistry.addChapter("dragonfruit", modPart).addPages(dragonFruit_page);

		if (CuisineConfig.subpartHealth.get()) {
			ManualRegistry.addChapter("sugar", modPart).addPages(sweets_page);
			ManualRegistry.addChapter("health", modPart).addPages(health_page);
		}

		if (CuisineConfig.subpartDairy.get()) {
			ManualRegistry.addChapter("dairy", modPart).addPages(butterChurn_page, cheeseMaker_page, cheeseBlock_page);
			ManualRegistry.addChapter("food", modPart).addPages(sandwiches_page, eggs_page, cookedEggs_page);
		}

		if (CuisineConfig.subpartPie.get())
			ManualRegistry.addChapter("pie", modPart).addPages(extras_page, rawPie_page, bakedPie_page);

		if (CuisineConfig.subpartChocolate.get()) {
			ManualRegistry.addChapter("cocoa", modPart).addPages(cocoaTree_page, cocoaFruit_page, cocoaDye_page);
			ManualRegistry.addChapter("bowlchoc", modPart).addPages(chocolateBowl_page, hotChocolate_page, chocolateBall_page, chocolateCake_page);
			ManualRegistry.addChapter("choco", modPart).addPages(chocolateMould_page, chocolateBars_page, chocolateCandy_page);
		}
	}

}
