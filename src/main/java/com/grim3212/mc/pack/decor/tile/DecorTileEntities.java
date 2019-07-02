package com.grim3212.mc.pack.decor.tile;

import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class DecorTileEntities {

	@ObjectHolder(DecorNames.TILE_ENTITY_ALARM)
	public static TileEntityType<?> ALARM;
	@ObjectHolder(DecorNames.TILE_ENTITY_CAGE)
	public static TileEntityType<?> CAGE;
	@ObjectHolder(DecorNames.TILE_ENTITY_CALENDAR)
	public static TileEntityType<?> CALENDAR;
	@ObjectHolder(DecorNames.TILE_ENTITY_COLORIZER)
	public static TileEntityType<?> COLORIZER;
	@ObjectHolder(DecorNames.TILE_ENTITY_GRILL)
	public static TileEntityType<?> GRILL;
	@ObjectHolder(DecorNames.TILE_ENTITY_NEON_SIGN)
	public static TileEntityType<?> NEON_SIGN;
	@ObjectHolder(DecorNames.TILE_ENTITY_WALL_CLOCK)
	public static TileEntityType<?> WALL_CLOCK;

	@SubscribeEvent
	public void initTileEntities(RegistryEvent.Register<TileEntityType<?>> evt) {
		IForgeRegistry<TileEntityType<?>> r = evt.getRegistry();

		r.register(TileEntityType.Builder.create(TileEntityAlarm::new, DecorBlocks.alarm).build(null).setRegistryName(DecorNames.TILE_ENTITY_ALARM));
		r.register(TileEntityType.Builder.create(TileEntityCalendar::new, DecorBlocks.calendar).build(null).setRegistryName(DecorNames.TILE_ENTITY_CALENDAR));
		r.register(TileEntityType.Builder.create(TileEntityColorizer::new, DecorBlocks.colorizer, DecorBlocks.colorizer_light, DecorBlocks.counter, DecorBlocks.chair, DecorBlocks.stool, DecorBlocks.wall, DecorBlocks.table, DecorBlocks.fence, DecorBlocks.fence_gate, DecorBlocks.door, DecorBlocks.trap_door, DecorBlocks.lamp_post_bottom, DecorBlocks.lamp_post_middle, DecorBlocks.lamp_post_top, DecorBlocks.firepit, DecorBlocks.fireplace, DecorBlocks.firering, DecorBlocks.chimney, DecorBlocks.stove,
				DecorBlocks.corner, DecorBlocks.slope, DecorBlocks.slanted_corner, DecorBlocks.sloped_angle, DecorBlocks.oblique_slope, DecorBlocks.sloped_intersection, DecorBlocks.pyramid, DecorBlocks.full_pyramid, DecorBlocks.sloped_post, DecorBlocks.pillar, DecorBlocks.stairs).build(null).setRegistryName(DecorNames.TILE_ENTITY_COLORIZER));
		r.register(TileEntityType.Builder.create(TileEntityGrill::new, DecorBlocks.grill).build(null).setRegistryName(DecorNames.TILE_ENTITY_GRILL));
		r.register(TileEntityType.Builder.create(TileEntityNeonSign::new, DecorBlocks.neon_sign_standing, DecorBlocks.neon_sign_wall).build(null).setRegistryName(DecorNames.TILE_ENTITY_NEON_SIGN));
		r.register(TileEntityType.Builder.create(TileEntityWallClock::new, DecorBlocks.wall_clock).build(null).setRegistryName(DecorNames.TILE_ENTITY_WALL_CLOCK));
		r.register(TileEntityType.Builder.create(TileEntityCage::new, DecorBlocks.cage).build(null).setRegistryName(DecorNames.TILE_ENTITY_CAGE));
	}
}