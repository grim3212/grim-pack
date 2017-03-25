package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.core.part.IPartEntities.IPartTileEntities;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class IndustryTileEntities implements IPartTileEntities {

	@Override
	public void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySensor.class, "sensors");
		GameRegistry.registerTileEntity(TileEntityGravity.class, "gravity");
		GameRegistry.registerTileEntity(TileEntityCamo.class, "camo_plate");
		GameRegistry.registerTileEntity(TileEntityDGravity.class, "directional_gravity");
		GameRegistry.registerTileEntity(TileEntityMachine.class, "machine");
		GameRegistry.registerTileEntity(TileEntityMFurnace.class, "modern_furnace");
		GameRegistry.registerTileEntity(TileEntityFan.class, "fan");
		GameRegistry.registerTileEntity(TileEntitySpecificSensor.class, "specific_sensor");
		GameRegistry.registerTileEntity(TileEntityFireSensor.class, "fire_sensor");
		GameRegistry.registerTileEntity(TileEntityWarehouseCrate.class, "grimpack_warehouse_crate");
		GameRegistry.registerTileEntity(TileEntityGlassCabinet.class, "grimpack_glass_cabinet");
		GameRegistry.registerTileEntity(TileEntityWoodCabinet.class, "grimpack_wood_cabinet");
		GameRegistry.registerTileEntity(TileEntityObsidianSafe.class, "grimpack_obsidian_safe");
		GameRegistry.registerTileEntity(TileEntityGoldSafe.class, "grimpack_gold_safe");
	}

}
