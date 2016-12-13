package com.grim3212.mc.pack.world.client;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.items.WorldItems;

import net.minecraft.item.ItemStack;

public class ManualWorld implements IManualPart {

	public static ManualWorld INSTANCE = new ManualWorld();

	public static Page randomite_page;
	public static Page bedrock_page;
	public static Page wells_page;
	public static Page icePixie_page;
	public static Page treasureChest_page;
	public static Page worldGenExpanded_page;
	public static Page corruption_page;
	public static Page floatingIslands_page;
	public static Page notch_page;
	public static Page farmer_page;
	public static Page lumberjack_page;
	public static Page miner_page;
	public static Page psycho_page;
	public static Page suicide_page;
	public static Page greed_page;
	public static Page glowSeeds_page;
	public static Page greenFungus_page;
	public static Page colorFungus_page;
	public static Page buildFungus_page;
	public static Page mazeFungus_page;
	public static Page killingFungus_page;
	public static Page eatingFungus_page;
	public static Page verticalFungus_page;
	public static Page fungicide_page;

	@Override
	public void initPages() {
		randomite_page = new PageImageText("info", "randomite.png");
		bedrock_page = new PageImageText("info", "flat.png");
		wells_page = new PageImageText("info", "well.png");
		icePixie_page = new PageImageText("info", "pixie.png");
		treasureChest_page = new PageImageText("info", "treasure.png");
		worldGenExpanded_page = new PageImageText("info", "worldgen.png");
		corruption_page = new PageImageText("info", "corruption.png");
		floatingIslands_page = new PageImageText("info", "floating.png");
		notch_page = new PageImageText("notch", "notch.png");
		farmer_page = new PageImageText("farmer", "farmer.png");
		lumberjack_page = new PageImageText("lumber", "lumber.png");
		miner_page = new PageImageText("miner", "miner.png");
		psycho_page = new PageImageText("psycho", "psycho.png");
		suicide_page = new PageImageText("suicide", "suicide.png");
		greed_page = new PageCrafting("recipes", WorldItems.greed, 25);
		glowSeeds_page = new PageCrafting("recipe", new ItemStack(WorldBlocks.glowstone_seeds));
		greenFungus_page = new PageCrafting("green", WorldBlocks.greenFungus, 25);
		colorFungus_page = new PageCrafting("color", WorldBlocks.coloredFungus, 15);
		buildFungus_page = new PageCrafting("build", WorldBlocks.buildingFungus, 15);
		mazeFungus_page = new PageCrafting("maze", WorldBlocks.mazeFungus, 15);
		killingFungus_page = new PageCrafting("kill", WorldBlocks.acidFungus, 25);
		eatingFungus_page = new PageCrafting("blockEater", WorldBlocks.breakingFungus, 15);
		verticalFungus_page = new PageCrafting("vert", WorldBlocks.vertFungus, 20);
		fungicide_page = new PageCrafting("fungicide", new ItemStack(WorldItems.fungicide));
	}

	@Override
	public void registerChapters(ManualPart part) {
		ManualRegistry.addChapter("randomite", part).addPages(randomite_page);
		if (WorldConfig.generateFlatBedRockSurface || WorldConfig.generateFlatBedRockNether)
			ManualRegistry.addChapter("bedrock", part).addPages(bedrock_page);
		if (WorldConfig.replaceDesertWells)
			ManualRegistry.addChapter("wells", part).addPages(wells_page);
		ManualRegistry.addChapter("pixie", part).addPages(icePixie_page);
		ManualRegistry.addChapter("treasure", part).addPages(treasureChest_page);
		ManualRegistry.addChapter("worldgen", part).addPages(worldGenExpanded_page);
		if (WorldConfig.generateCorruption)
			ManualRegistry.addChapter("corruption", part).addPages(corruption_page);
		if (WorldConfig.generateFI)
			ManualRegistry.addChapter("floating", part).addPages(floatingIslands_page);
		if (WorldConfig.spawnMorePeople)
			ManualRegistry.addChapter("people", part).addPages(notch_page, farmer_page, lumberjack_page, miner_page, psycho_page, suicide_page);
		ManualRegistry.addChapter("greed", part).addPages(greed_page);
		ManualRegistry.addChapter("glowseeds", part).addPages(glowSeeds_page);
		ManualRegistry.addChapter("fungus", part).addPages(greenFungus_page, colorFungus_page, buildFungus_page, mazeFungus_page, killingFungus_page, eatingFungus_page, verticalFungus_page);
		ManualRegistry.addChapter("fungicide", part).addPages(fungicide_page);
	}

}
