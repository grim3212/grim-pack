package com.grim3212.mc.pack.world.client;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.manual.pages.PageItemStack;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.init.WorldRecipes;
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
	public static Page parabuzzy_page;
	public static Page bobomb_page;
	public static Page rune_page;
	public static Page fountain_page;
	public static Page pyramid_page;
	public static Page ruin_page;
	public static Page snowball_page;
	public static Page waterDome_page;
	public static Page spire_page;
	public static Page runeInfo_page;

	@Override
	public void initPages() {
		if (WorldConfig.subpartRandomite)
			randomite_page = new PageImageText("info", "randomite.png");

		if (WorldConfig.subpartFlatBedrock)
			bedrock_page = new PageImageText("info", "flat.png");

		if (WorldConfig.subpartDesertWells)
			wells_page = new PageImageText("info", "well.png");

		if (WorldConfig.subpartIcePixie)
			icePixie_page = new PageImageText("info", "pixie.png");

		if (WorldConfig.subpartTreasureMobs)
			treasureChest_page = new PageImageText("info", "treasure.png");

		if (WorldConfig.subpartWorldGenExpanded)
			worldGenExpanded_page = new PageImageText("info", "worldgen.png");

		if (WorldConfig.subpartCorruption)
			corruption_page = new PageImageText("info", "corruption.png");

		if (WorldConfig.subpartFloatingIslands)
			floatingIslands_page = new PageImageText("info", "floating.png");

		if (WorldConfig.subpartMorePeople) {
			notch_page = new PageImageText("notch", "notch.png");
			farmer_page = new PageImageText("farmer", "farmer.png");
			lumberjack_page = new PageImageText("lumber", "lumber.png");
			miner_page = new PageImageText("miner", "miner.png");
			psycho_page = new PageImageText("psycho", "psycho.png");
			suicide_page = new PageImageText("suicide", "suicide.png");
		}

		if (WorldConfig.subpartGunpowderReeds)
			greed_page = new PageCrafting("recipes", WorldRecipes.greed, 25);

		if (WorldConfig.subpartGlowstoneSeeds)
			glowSeeds_page = new PageCrafting("recipe", new ItemStack(WorldBlocks.glowstone_seeds)).appendImageUrl("glowstone_seeds.jpg");

		if (WorldConfig.subpartFungus) {
			greenFungus_page = new PageCrafting("green", WorldRecipes.greenFungus, 25);
			colorFungus_page = new PageCrafting("color", WorldRecipes.coloredFungus, 15).appendImageUrl("fungus.jpg");
			buildFungus_page = new PageCrafting("build", WorldRecipes.buildingFungus, 15);
			mazeFungus_page = new PageCrafting("maze", WorldRecipes.mazeFungus, 15);
			killingFungus_page = new PageCrafting("kill", WorldRecipes.acidFungus, 25);
			eatingFungus_page = new PageCrafting("blockEater", WorldRecipes.breakingFungus, 15);
			verticalFungus_page = new PageCrafting("vert", WorldRecipes.vertFungus, 20);
			fungicide_page = new PageCrafting("fungicide", new ItemStack(WorldItems.fungicide));
		}

		if (WorldConfig.subpart8BitMobs) {
			parabuzzy_page = new PageImageText("parabuzzy", "parabuzzy.png").appendImageUrl("parabuzzy.png");
			bobomb_page = new PageCrafting("bobomb", new ItemStack(WorldItems.bobomb));
		}

		if (WorldConfig.subpartRuins) {
			rune_page = new PageItemStack("runes", 25, WorldRecipes.runes);
			runeInfo_page = new PageInfo("rune_info");
			fountain_page = new PageImageText("fountains", "fountain.png");
			pyramid_page = new PageImageText("pyramids", "pyramid.png");
			ruin_page = new PageImageText("ruins", "ruin.png");
			snowball_page = new PageImageText("snowballs", "snowball.png");
			waterDome_page = new PageImageText("waterdomes", "waterdome.png");
			spire_page = new PageImageText("spires", "spire.png");
		}
	}

	@Override
	public void registerChapters(ManualPart part) {
		if (WorldConfig.subpartRandomite)
			ManualRegistry.addChapter("randomite", part).addPages(randomite_page);
		if (WorldConfig.subpartFlatBedrock)
			ManualRegistry.addChapter("bedrock", part).addPages(bedrock_page);
		if (WorldConfig.subpartDesertWells)
			ManualRegistry.addChapter("wells", part).addPages(wells_page);
		if (WorldConfig.subpartIcePixie)
			ManualRegistry.addChapter("pixie", part).addPages(icePixie_page);
		if (WorldConfig.subpartTreasureMobs)
			ManualRegistry.addChapter("treasure", part).addPages(treasureChest_page);
		if (WorldConfig.subpartWorldGenExpanded)
			ManualRegistry.addChapter("worldgen", part).addPages(worldGenExpanded_page);
		if (WorldConfig.subpartCorruption)
			ManualRegistry.addChapter("corruption", part).addPages(corruption_page);
		if (WorldConfig.subpartFloatingIslands)
			ManualRegistry.addChapter("floating", part).addPages(floatingIslands_page);
		if (WorldConfig.subpartMorePeople)
			ManualRegistry.addChapter("people", part).addPages(notch_page, farmer_page, lumberjack_page, miner_page, psycho_page, suicide_page);
		if (WorldConfig.subpartGunpowderReeds)
			ManualRegistry.addChapter("greed", part).addPages(greed_page);
		if (WorldConfig.subpartGlowstoneSeeds)
			ManualRegistry.addChapter("glowseeds", part).addPages(glowSeeds_page);
		if (WorldConfig.subpart8BitMobs)
			ManualRegistry.addChapter("8bit", part).addPages(parabuzzy_page, bobomb_page);

		if (WorldConfig.subpartFungus) {
			ManualRegistry.addChapter("fungus", part).addPages(greenFungus_page, colorFungus_page, buildFungus_page, mazeFungus_page, killingFungus_page, eatingFungus_page, verticalFungus_page);
			ManualRegistry.addChapter("fungicide", part).addPages(fungicide_page);
		}

		if (WorldConfig.subpartRuins) {
			ManualRegistry.addChapter("ruins", part).addPages(rune_page, runeInfo_page, fountain_page, pyramid_page, ruin_page, snowball_page, waterDome_page, spire_page);
		}
	}
}