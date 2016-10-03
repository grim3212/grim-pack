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
<<<<<<< HEAD
=======
		GameRegistry.registerTileEntity(TileEntitySpecificSensor.class, "specific_sensor");
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
	}

}
