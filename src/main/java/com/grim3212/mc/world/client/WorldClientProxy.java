package com.grim3212.mc.world.client;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.manual.pages.PageImageText;
import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.world.GrimWorld;
import com.grim3212.mc.world.blocks.BlockFungusBuilding;
import com.grim3212.mc.world.blocks.BlockFungusGrowing;
import com.grim3212.mc.world.blocks.BlockFungusKilling;
import com.grim3212.mc.world.blocks.BlockGlowstoneSeed;
import com.grim3212.mc.world.blocks.BlockGunpowderReed;
import com.grim3212.mc.world.blocks.WorldBlocks;
import com.grim3212.mc.world.client.entity.RenderIceCube.RenderIceCubeFactory;
import com.grim3212.mc.world.client.entity.RenderIcePixie.RenderIcePixieFactory;
import com.grim3212.mc.world.client.entity.RenderPerson.RenderPersonFactory;
import com.grim3212.mc.world.client.entity.RenderTreasureMob.RenderTreasureMobFactory;
import com.grim3212.mc.world.config.WorldConfig;
import com.grim3212.mc.world.entity.EntityBomber;
import com.grim3212.mc.world.entity.EntityFarmer;
import com.grim3212.mc.world.entity.EntityIceCube;
import com.grim3212.mc.world.entity.EntityIcePixie;
import com.grim3212.mc.world.entity.EntityLumberJack;
import com.grim3212.mc.world.entity.EntityMiner;
import com.grim3212.mc.world.entity.EntityNotch;
import com.grim3212.mc.world.entity.EntityPsycho;
import com.grim3212.mc.world.entity.EntityTreasureMob;
import com.grim3212.mc.world.items.WorldItems;

import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class WorldClientProxy extends ClientProxy {

	@Override
	protected void registerModels() {
		ModelLoader.setCustomStateMapper(WorldBlocks.gunpowder_reed_block, new StateMap.Builder().ignore(BlockGunpowderReed.AGE).build());
		ModelLoader.setCustomStateMapper(WorldBlocks.glowstone_seeds, new StateMap.Builder().ignore(BlockGlowstoneSeed.STEP).build());
		ModelLoader.setCustomStateMapper(WorldBlocks.fungus_building, new StateMap.Builder().ignore(BlockFungusBuilding.TYPE).build());
		ModelLoader.setCustomStateMapper(WorldBlocks.fungus_growing, new StateMap.Builder().ignore(BlockFungusGrowing.TYPE).build());
		ModelLoader.setCustomStateMapper(WorldBlocks.fungus_killing, new StateMap.Builder().ignore(BlockFungusKilling.TYPE).build());

		RenderHelper.renderBlock(WorldBlocks.randomite);
		RenderHelper.renderBlock(WorldBlocks.gunpowder_reed_block);
		RenderHelper.renderBlock(WorldBlocks.glowstone_seeds);
		RenderHelper.renderBlock(WorldBlocks.corruption_block);
		RenderHelper.renderBlockWithMetaInInventory(WorldBlocks.fungus_building, 16);
		RenderHelper.renderBlockWithMetaInInventory(WorldBlocks.fungus_growing, 16);
		RenderHelper.renderBlockWithMetaInInventory(WorldBlocks.fungus_killing, 16);

		RenderHelper.renderItem(WorldItems.gunpowder_reed_item);
		RenderHelper.renderItem(WorldItems.fungicide);

		// Entities
		RenderingRegistry.registerEntityRenderingHandler(EntityIcePixie.class, new RenderIcePixieFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityIceCube.class, new RenderIceCubeFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityTreasureMob.class, new RenderTreasureMobFactory());

		if (WorldConfig.spawnMorePeople) {
			RenderingRegistry.registerEntityRenderingHandler(EntityNotch.class, new RenderPersonFactory(new ResourceLocation(GrimWorld.modID, "textures/entities/notch.png")));
			RenderingRegistry.registerEntityRenderingHandler(EntityPsycho.class, new RenderPersonFactory(new ResourceLocation(GrimWorld.modID, "textures/entities/psycho.png")));
			RenderingRegistry.registerEntityRenderingHandler(EntityFarmer.class, new RenderPersonFactory(new ResourceLocation(GrimWorld.modID, "textures/entities/farmer.png")));
			RenderingRegistry.registerEntityRenderingHandler(EntityLumberJack.class, new RenderPersonFactory(new ResourceLocation(GrimWorld.modID, "textures/entities/lumberjack.png")));
			RenderingRegistry.registerEntityRenderingHandler(EntityMiner.class, new RenderPersonFactory(new ResourceLocation(GrimWorld.modID, "textures/entities/miner.png")));
			RenderingRegistry.registerEntityRenderingHandler(EntityBomber.class, new RenderPersonFactory(new ResourceLocation(GrimWorld.modID, "textures/entities/bomber.png")));
		}
	}

	@Override
	protected void registerManual(ModSection modSection) {
		ManualRegistry.addSection("randomite", modSection).addSubSectionPages(new PageImageText("info", "randomite.png"));
		ManualRegistry.addSection("bedrock", modSection).addSubSectionPages(new PageImageText("info", "flat.png"));
		ManualRegistry.addSection("wells", modSection).addSubSectionPages(new PageImageText("info", "well.png"));
		ManualRegistry.addSection("pixie", modSection).addSubSectionPages(new PageImageText("info", "pixie.png"));
		ManualRegistry.addSection("treasure", modSection).addSubSectionPages(new PageImageText("info", "treasure.png"));
		ManualRegistry.addSection("worldgen", modSection).addSubSectionPages(new PageImageText("info", "worldgen.png"));
		if (WorldConfig.corruption)
			ManualRegistry.addSection("corruption", modSection).addSubSectionPages(new PageImageText("info", "corruption.png"));
		if (WorldConfig.generateFI)
			ManualRegistry.addSection("floating", modSection).addSubSectionPages(new PageImageText("info", "floating.png"));
		if (WorldConfig.spawnMorePeople)
			ManualRegistry.addSection("people", modSection).addSubSectionPages(new PageImageText("notch", "notch.png"), new PageImageText("farmer", "farmer.png"), new PageImageText("lumber", "lumber.png"), new PageImageText("miner", "miner.png"), new PageImageText("psycho", "psycho.png"), new PageImageText("suicide", "suicide.png"));
		ManualRegistry.addSection("greed", modSection).addSubSectionPages(new PageCrafting("recipes", WorldItems.greed, 25));
		ManualRegistry.addSection("glowseeds", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(WorldBlocks.glowstone_seeds)));
		ManualRegistry.addSection("basic", modSection).addSubSectionPages(new PageCrafting("green", WorldBlocks.greenFungus, 25), new PageCrafting("color", WorldBlocks.coloredFungus, 15));
		ManualRegistry.addSection("build", modSection).addSubSectionPages(new PageCrafting("build", WorldBlocks.buildingFungus, 15), new PageCrafting("maze", WorldBlocks.mazeFungusRecipe));
		ManualRegistry.addSection("break", modSection).addSubSectionPages(new PageCrafting("kill", WorldBlocks.acidFungus, 25), new PageCrafting("blockEater", WorldBlocks.breakingFungus, 15), new PageCrafting("vert", WorldBlocks.vertFungus, 20));
		ManualRegistry.addSection("fungicide", modSection).addSubSectionPages(new PageCrafting("fungicide", new ItemStack(WorldItems.fungicide)));
	}

}
