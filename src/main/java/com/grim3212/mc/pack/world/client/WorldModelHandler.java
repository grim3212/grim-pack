package com.grim3212.mc.pack.world.client;

import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.world.blocks.BlockFungusBuilding;
import com.grim3212.mc.pack.world.blocks.BlockFungusGrowing;
import com.grim3212.mc.pack.world.blocks.BlockFungusKilling;
import com.grim3212.mc.pack.world.blocks.BlockFungusLayer;
import com.grim3212.mc.pack.world.blocks.BlockGlowstoneSeed;
import com.grim3212.mc.pack.world.blocks.BlockGunpowderReed;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.items.WorldItems;

import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldModelHandler {

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent evt) {
		ModelLoader.setCustomStateMapper(WorldBlocks.gunpowder_reed_block, new StateMap.Builder().ignore(BlockGunpowderReed.AGE).build());
		ModelLoader.setCustomStateMapper(WorldBlocks.glowstone_seeds, new StateMap.Builder().ignore(BlockGlowstoneSeed.STEP).build());
		ModelLoader.setCustomStateMapper(WorldBlocks.fungus_building, new StateMap.Builder().ignore(BlockFungusBuilding.TYPE).build());
		ModelLoader.setCustomStateMapper(WorldBlocks.fungus_layer_building, new StateMap.Builder().ignore(BlockFungusLayer.TYPE).build());
		ModelLoader.setCustomStateMapper(WorldBlocks.fungus_growing, new StateMap.Builder().ignore(BlockFungusGrowing.TYPE).build());
		ModelLoader.setCustomStateMapper(WorldBlocks.fungus_killing, new StateMap.Builder().ignore(BlockFungusKilling.TYPE).build());

		RenderHelper.renderBlock(WorldBlocks.randomite);
		RenderHelper.renderBlock(WorldBlocks.gunpowder_reed_block);
		RenderHelper.renderBlock(WorldBlocks.glowstone_seeds);
		RenderHelper.renderBlock(WorldBlocks.corruption_block);
		RenderHelper.renderBlock(WorldBlocks.fungus_maze);
		RenderHelper.renderBlock(WorldBlocks.fungus_ore_building);
		RenderHelper.renderBlockWithMetaInInventory(WorldBlocks.fungus_building, 16);
		RenderHelper.renderBlockWithMetaInInventory(WorldBlocks.fungus_layer_building, 16);
		RenderHelper.renderBlockWithMetaInInventory(WorldBlocks.fungus_growing, 16);
		RenderHelper.renderBlockWithMetaInInventory(WorldBlocks.fungus_killing, 16);

		RenderHelper.renderItem(WorldItems.gunpowder_reed_item);
		RenderHelper.renderItem(WorldItems.fungicide);
		RenderHelper.renderItem(WorldItems.parabuzzy_shell);
		RenderHelper.renderItem(WorldItems.bobomb);
	}

}
