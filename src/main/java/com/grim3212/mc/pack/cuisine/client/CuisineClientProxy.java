package com.grim3212.mc.pack.cuisine.client;

import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.ModSection;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.core.proxy.ClientProxy;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.cuisine.item.ItemSodaBottle;

import net.minecraft.item.ItemStack;

public class CuisineClientProxy extends ClientProxy {

	@Override
	public void registerModels() {
		// ITEMS
		RenderHelper.renderItem(CuisineItems.powered_sweets);
		RenderHelper.renderItem(CuisineItems.powered_sugar);
		RenderHelper.renderItem(CuisineItems.sweets);
		RenderHelper.renderItem(CuisineItems.bandage);
		RenderHelper.renderItem(CuisineItems.healthpack_super);
		RenderHelper.renderItem(CuisineItems.healthpack);
		RenderHelper.renderItem(CuisineItems.dragon_fruit);
		RenderHelper.renderItem(CuisineItems.butter);
		RenderHelper.renderItem(CuisineItems.cheese);
		RenderHelper.renderItem(CuisineItems.cocoa_fruit);
		RenderHelper.renderItem(CuisineItems.cocoa_dust);
		RenderHelper.renderItem(CuisineItems.chocolate_bowl);
		RenderHelper.renderItem(CuisineItems.chocolate_bowl_hot);
		RenderHelper.renderItem(CuisineItems.chocolate_bar);
		RenderHelper.renderItem(CuisineItems.chocolate_bar_wrapped);
		RenderHelper.renderItem(CuisineItems.chocolate_ball);
		RenderHelper.renderItem(CuisineItems.wrapper);
		RenderHelper.renderItem(CuisineItems.bread_slice);
		RenderHelper.renderItem(CuisineItems.cheese_burger);
		RenderHelper.renderItem(CuisineItems.hot_cheese);
		RenderHelper.renderItem(CuisineItems.eggs_unmixed);
		RenderHelper.renderItem(CuisineItems.eggs_mixed);
		RenderHelper.renderItem(CuisineItems.eggs_cooked);
		RenderHelper.renderItem(CuisineItems.pan);
		RenderHelper.renderItem(CuisineItems.dough);
		RenderHelper.renderItem(CuisineItems.knife);
		RenderHelper.renderItem(CuisineItems.mixer);
		RenderHelper.renderItem(CuisineItems.raw_empty_pie);
		RenderHelper.renderItem(CuisineItems.raw_apple_pie);
		RenderHelper.renderItem(CuisineItems.raw_chocolate_pie);
		RenderHelper.renderItem(CuisineItems.raw_pork_pie);
		RenderHelper.renderItem(CuisineItems.raw_pumpkin_pie);
		RenderHelper.renderItem(CuisineItems.raw_melon_pie);
		RenderHelper.renderItem(CuisineItems.pumpkin_slice);
		RenderHelper.renderVariantForge(CuisineItems.soda, ItemSodaBottle.sodaTypes);

		// BLOCKS
		RenderHelper.renderBlock(CuisineBlocks.cheese_block);
		RenderHelper.renderBlock(CuisineBlocks.butter_churn);
		RenderHelper.renderBlock(CuisineBlocks.cheese_maker);
		RenderHelper.renderBlock(CuisineBlocks.cocoa_block);
		RenderHelper.renderBlock(CuisineBlocks.cocoa_tree_sapling);
		RenderHelper.renderBlock(CuisineBlocks.chocolate_bar_mould);
		RenderHelper.renderBlock(CuisineBlocks.chocolate_cake);
		RenderHelper.renderBlock(CuisineBlocks.chocolate_block);
		RenderHelper.renderBlock(CuisineBlocks.apple_pie);
		RenderHelper.renderBlock(CuisineBlocks.melon_pie);
		RenderHelper.renderBlock(CuisineBlocks.pumpkin_pie);
		RenderHelper.renderBlock(CuisineBlocks.chocolate_pie);
		RenderHelper.renderBlock(CuisineBlocks.pork_pie);
	}

	@Override
	public void registerManual(ModSection modSection) {
		ManualRegistry.addSection("soda", modSection).addSubSectionPages(new PageCrafting("carbon", CuisineItems.carbon, 25), new PageCrafting("types", CuisineItems.sodas, 15));
		ManualRegistry.addSection("dragonfruit", modSection).addSubSectionPages(new PageImageText("dragonfruit", "dragonFruitPage.png"));
		ManualRegistry.addSection("sugar", modSection).addSubSectionPages(new PageCrafting("sweets", CuisineItems.food, 25));
		ManualRegistry.addSection("health", modSection).addSubSectionPages(new PageCrafting("recipes", CuisineItems.health, 25));
		ManualRegistry.addSection("dairy", modSection).addSubSectionPages(new PageCrafting("butter", new ItemStack(CuisineBlocks.butter_churn)), new PageCrafting("cheese", new ItemStack(CuisineBlocks.cheese_maker)), new PageCrafting("cheeseblock", CuisineItems.cheeseRecipe, 25));
		ManualRegistry.addSection("food", modSection).addSubSectionPages(new PageCrafting("utensils", CuisineItems.utensils, 25), new PageCrafting("extras", CuisineItems.extra, 25), new PageCrafting("sandwiches", CuisineItems.sandwiches, 25), new PageCrafting("eggs", CuisineItems.eggs, 25), new PageFurnace("cooked", new ItemStack(CuisineItems.eggs_mixed)));
		ManualRegistry.addSection("pie", modSection).addSubSectionPages(new PageCrafting("craft", CuisineItems.pie, 25), new PageFurnace("bake", new ItemStack[] { new ItemStack(CuisineItems.raw_apple_pie), new ItemStack(CuisineItems.raw_melon_pie), new ItemStack(CuisineItems.raw_melon_pie), new ItemStack(CuisineItems.raw_pork_pie), new ItemStack(CuisineItems.raw_pumpkin_pie) }, 25));
		ManualRegistry.addSection("cocoa", modSection).addSubSectionPages(new PageImageText("tree", "cocoaTreePage.png"), new PageCrafting("fruit", new ItemStack(CuisineItems.cocoa_dust)), new PageCrafting("dye", CuisineItems.cocoaRecipe));
		ManualRegistry.addSection("bowlchoc", modSection).addSubSectionPages(new PageCrafting("bowlChoc", new ItemStack(CuisineItems.chocolate_bowl)), new PageFurnace("bowlChocHot", new ItemStack(CuisineItems.chocolate_bowl)), new PageCrafting("chocBall", new ItemStack(CuisineItems.chocolate_ball)), new PageCrafting("cake", new ItemStack(CuisineBlocks.chocolate_cake)));
		ManualRegistry.addSection("choco", modSection).addSubSectionPages(new PageCrafting("mould", new ItemStack(CuisineBlocks.chocolate_bar_mould)), new PageCrafting("bars", CuisineItems.choc, 25), new PageCrafting("candy", CuisineItems.candy, 25));
	}
}