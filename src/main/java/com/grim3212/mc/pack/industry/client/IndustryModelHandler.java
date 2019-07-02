package com.grim3212.mc.pack.industry.client;

import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.model.BridgeModel.BridgeModelLoader;
import com.grim3212.mc.pack.industry.client.model.CamoPlateModel.CamoPlateModelLoader;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.tile.TileEntityGlassCabinet;
import com.grim3212.mc.pack.industry.tile.TileEntityGoldSafe;
import com.grim3212.mc.pack.industry.tile.TileEntityItemTower;
import com.grim3212.mc.pack.industry.tile.TileEntityLocker;
import com.grim3212.mc.pack.industry.tile.TileEntityObsidianSafe;
import com.grim3212.mc.pack.industry.tile.TileEntityWarehouseCrate;
import com.grim3212.mc.pack.industry.tile.TileEntityWoodCabinet;

import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class IndustryModelHandler {

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent evt) {
		if (IndustryConfig.subpartDecoration) {
			// Register all custom models for camo plates
			ModelLoaderRegistry.registerLoader(CamoPlateModelLoader.instance);
		}

		if (IndustryConfig.subpartStorage) {
			ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(IndustryBlocks.warehouse_crate), 0, TileEntityWarehouseCrate.class);
			ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(IndustryBlocks.glass_cabinet), 0, TileEntityGlassCabinet.class);
			ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(IndustryBlocks.wood_cabinet), 0, TileEntityWoodCabinet.class);
			ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(IndustryBlocks.obsidian_safe), 0, TileEntityObsidianSafe.class);
			ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(IndustryBlocks.gold_safe), 0, TileEntityGoldSafe.class);
			ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(IndustryBlocks.locker), 0, TileEntityLocker.class);
			ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(IndustryBlocks.item_tower), 0, TileEntityItemTower.class);
		}

		if (IndustryConfig.subpartBridges) {
			ModelLoaderRegistry.registerLoader(BridgeModelLoader.instance);
		}
	}
}