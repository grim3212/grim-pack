package com.grim3212.mc.pack.cuisine.client;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.cuisine.init.CuisineRecipes;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;

import net.minecraft.item.ItemStack;

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
		if (CuisineConfig.subpartSoda) {
			soda_page = new PageCrafting("types", CuisineRecipes.sodas, 15);
			carbon_page = new PageCrafting("carbon", CuisineRecipes.carbon, 25);
		}

		if (CuisineConfig.subpartDragonFruit)
			dragonFruit_page = new PageImageText("dragonfruit", "dragon_fruit_page.png");

		if (CuisineConfig.subpartHealth) {
			sweets_page = new PageCrafting("sweets", CuisineRecipes.food, 25);
			health_page = new PageCrafting("recipes", CuisineRecipes.health, 25);
		}

		if (CuisineConfig.subpartDairy) {
			cheeseMaker_page = new PageCrafting("cheese", new ItemStack(CuisineBlocks.cheese_maker));
			butterChurn_page = new PageCrafting("butter", new ItemStack(CuisineBlocks.butter_churn)).appendImageUrl("butter.png");
			cheeseBlock_page = new PageCrafting("cheeseblock", CuisineRecipes.cheeseRecipe, 25);
			sandwiches_page = new PageCrafting("sandwiches", CuisineRecipes.sandwiches, 25);
			eggs_page = new PageCrafting("eggs", CuisineRecipes.eggs, 25);
			cookedEggs_page = new PageFurnace("cooked", new ItemStack(CuisineItems.eggs_mixed));
		}

		if (CuisineConfig.subpartPie) {
			extras_page = new PageCrafting("extras", CuisineRecipes.extra, 25);
			rawPie_page = new PageCrafting("craft", CuisineRecipes.pie, 25);
			bakedPie_page = new PageFurnace("bake", new ItemStack[] { new ItemStack(CuisineItems.raw_apple_pie), new ItemStack(CuisineItems.raw_melon_pie), new ItemStack(CuisineItems.raw_melon_pie), new ItemStack(CuisineItems.raw_pork_pie), new ItemStack(CuisineItems.raw_pumpkin_pie) }, 25);
		}

		if (CuisineConfig.subpartChocolate) {
			cocoaTree_page = new PageImageText("tree", "cocoa_tree_page.png");
			cocoaFruit_page = new PageCrafting("fruit", new ItemStack(CuisineItems.cocoa_dust));
			cocoaDye_page = new PageCrafting("dye", CuisineRecipes.cocoaRecipe);
			chocolateBowl_page = new PageCrafting("bowlChoc", new ItemStack(CuisineItems.chocolate_bowl));
			hotChocolate_page = new PageFurnace("bowlChocHot", new ItemStack(CuisineItems.chocolate_bowl));
			chocolateBall_page = new PageCrafting("chocBall", new ItemStack(CuisineItems.chocolate_ball));
			chocolateCake_page = new PageCrafting("cake", new ItemStack(CuisineBlocks.chocolate_cake));
			chocolateMould_page = new PageCrafting("mould", new ItemStack(CuisineBlocks.chocolate_bar_mould)).appendImageUrl("chocolate_mould.png");
			chocolateBars_page = new PageCrafting("bars", CuisineRecipes.choc, 25);
			chocolateCandy_page = new PageCrafting("candy", CuisineRecipes.candy, 25);
		}
	}

	@Override
	public void registerChapters(ManualPart modPart) {
		if (CuisineConfig.subpartSoda)
			ManualRegistry.addChapter("soda", modPart).addPages(carbon_page, soda_page);

		if (CuisineConfig.subpartDragonFruit)
			ManualRegistry.addChapter("dragonfruit", modPart).addPages(dragonFruit_page);

		if (CuisineConfig.subpartHealth) {
			ManualRegistry.addChapter("sugar", modPart).addPages(sweets_page);
			ManualRegistry.addChapter("health", modPart).addPages(health_page);
		}

		if (CuisineConfig.subpartDairy) {
			ManualRegistry.addChapter("dairy", modPart).addPages(butterChurn_page, cheeseMaker_page, cheeseBlock_page);
			ManualRegistry.addChapter("food", modPart).addPages(sandwiches_page, eggs_page, cookedEggs_page);
		}

		if (CuisineConfig.subpartPie)
			ManualRegistry.addChapter("pie", modPart).addPages(extras_page, rawPie_page, bakedPie_page);

		if (CuisineConfig.subpartChocolate) {
			ManualRegistry.addChapter("cocoa", modPart).addPages(cocoaTree_page, cocoaFruit_page, cocoaDye_page);
			ManualRegistry.addChapter("bowlchoc", modPart).addPages(chocolateBowl_page, hotChocolate_page, chocolateBall_page, chocolateCake_page);
			ManualRegistry.addChapter("choco", modPart).addPages(chocolateMould_page, chocolateBars_page, chocolateCandy_page);
		}
	}

}
