package com.grim3212.mc.pack.decor.entity;

import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

public class DecorEntities {

	@ObjectHolder(DecorNames.ENTITY_WALLPAPER)
	public static EntityType<EntityWallpaper> WALLPAPER;
	@ObjectHolder(DecorNames.ENTITY_FRAME)
	public static EntityType<EntityFrame> FRAME;

	@SubscribeEvent
	public void initEntities(RegistryEvent.Register<EntityType<?>> evt) {
		evt.getRegistry().register(EntityType.Builder.<EntityWallpaper>create(EntityWallpaper::new, EntityClassification.MISC).setTrackingRange(250).setUpdateInterval(2147483647).setShouldReceiveVelocityUpdates(false).size(0.5F, 0.5F).setCustomClientFactory((spawnEnt, world) -> WALLPAPER.create(world)).build(DecorNames.ENTITY_WALLPAPER).setRegistryName(DecorNames.ENTITY_WALLPAPER));
		evt.getRegistry().register(EntityType.Builder.<EntityFrame>create(EntityFrame::new, EntityClassification.MISC).setTrackingRange(250).setUpdateInterval(2147483647).setShouldReceiveVelocityUpdates(false).size(0.5F, 0.5F).setCustomClientFactory((spawnEnt, world) -> FRAME.create(world)).build(DecorNames.ENTITY_FRAME).setRegistryName(DecorNames.ENTITY_FRAME));
	}
}