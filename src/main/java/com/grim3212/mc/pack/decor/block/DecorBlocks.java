package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.part.GrimItemGroups;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizer;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerChair;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerChimney;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerCounter;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerDoor;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFacing;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFence;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFenceGate;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFirepit;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFireplace;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFirering;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerGrill;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerHedge;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerLampPost;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerLight;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerStairs;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerStool;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerStove;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerTable;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerTrapDoor;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerWall;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.item.ItemColorizer;
import com.grim3212.mc.pack.decor.item.ItemDecorDoor;
import com.grim3212.mc.pack.decor.item.ItemGrill;
import com.grim3212.mc.pack.decor.item.ItemLantern;
import com.grim3212.mc.pack.decor.item.ItemSloped;
import com.grim3212.mc.pack.decor.util.DecorUtil.SlopeType;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class DecorBlocks {

	@ObjectHolder(DecorNames.ILLUMINATION_TUBE)
	public static Block illumination_tube;
	@ObjectHolder(DecorNames.WHITE_FLURO)
	public static Block white_fluro;
	@ObjectHolder(DecorNames.ORANGE_FLURO)
	public static Block orange_fluro;
	@ObjectHolder(DecorNames.MAGENTA_FLURO)
	public static Block magenta_fluro;
	@ObjectHolder(DecorNames.LIGHT_BLUE_FLURO)
	public static Block light_blue_fluro;
	@ObjectHolder(DecorNames.YELLOW_FLURO)
	public static Block yellow_fluro;
	@ObjectHolder(DecorNames.LIME_FLURO)
	public static Block lime_fluro;
	@ObjectHolder(DecorNames.PINK_FLURO)
	public static Block pink_fluro;
	@ObjectHolder(DecorNames.GRAY_FLURO)
	public static Block gray_fluro;
	@ObjectHolder(DecorNames.LIGHT_GRAY_FLURO)
	public static Block light_gray_fluro;
	@ObjectHolder(DecorNames.CYAN_FLURO)
	public static Block cyan_fluro;
	@ObjectHolder(DecorNames.PURPLE_FLURO)
	public static Block purple_fluro;
	@ObjectHolder(DecorNames.BLUE_FLURO)
	public static Block blue_fluro;
	@ObjectHolder(DecorNames.BROWN_FLURO)
	public static Block brown_fluro;
	@ObjectHolder(DecorNames.GREEN_FLURO)
	public static Block green_fluro;
	@ObjectHolder(DecorNames.RED_FLURO)
	public static Block red_fluro;
	@ObjectHolder(DecorNames.BLACK_FLURO)
	public static Block black_fluro;

	@ObjectHolder(DecorNames.NEON_SIGN_STANDING)
	public static Block neon_sign_standing;
	@ObjectHolder(DecorNames.NEON_SIGN_WALL)
	public static Block neon_sign_wall;
	@ObjectHolder(DecorNames.ALARM)
	public static Block alarm;
	@ObjectHolder(DecorNames.CALENDAR)
	public static Block calendar;
	@ObjectHolder(DecorNames.WALL_CLOCK)
	public static Block wall_clock;
	@ObjectHolder(DecorNames.LIGHT_BULB)
	public static Block light_bulb;
	@ObjectHolder(DecorNames.POT)
	public static Block pot;
	@ObjectHolder(DecorNames.CAGE)
	public static Block cage;
	@ObjectHolder(DecorNames.CHAIN)
	public static Block chain;
	@ObjectHolder(DecorNames.ROAD)
	public static Block road;
	@ObjectHolder(DecorNames.PAPER_LANTERN)
	public static Block paper_lantern;
	@ObjectHolder(DecorNames.BONE_LANTERN)
	public static Block bone_lantern;
	@ObjectHolder(DecorNames.IRON_LANTERN)
	public static Block iron_lantern;
	@ObjectHolder(DecorNames.FANCY_STONE)
	public static Block fancy_stone;
	@ObjectHolder(DecorNames.CRAFT_CLAY)
	public static Block craft_clay;
	@ObjectHolder(DecorNames.CRAFT_BONE)
	public static Block craft_bone;

	// =================== COLORIZER BLOCKS BELOW ===================\\
	@ObjectHolder(DecorNames.HARDENED_WOOD)
	public static Block hardened_wood;
	@ObjectHolder(DecorNames.COLORIZER)
	public static Block colorizer;
	@ObjectHolder(DecorNames.COLORIZER_LIGHT)
	public static Block colorizer_light;
	@ObjectHolder(DecorNames.BURNING_WOOD)
	public static Block burning_wood;
	@ObjectHolder(DecorNames.COUNTER)
	public static Block counter;
	@ObjectHolder(DecorNames.STOOL)
	public static Block stool;
	@ObjectHolder(DecorNames.CHAIR)
	public static Block chair;
	@ObjectHolder(DecorNames.WALL)
	public static Block wall;
	@ObjectHolder(DecorNames.TABLE)
	public static Block table;
	@ObjectHolder(DecorNames.FENCE)
	public static Block fence;
	@ObjectHolder(DecorNames.FENCE_GATE)
	public static Block fence_gate;
	@ObjectHolder(DecorNames.DOOR)
	public static Block door;
	@ObjectHolder(DecorNames.TRAP_DOOR)
	public static Block trap_door;
	@ObjectHolder(DecorNames.LAMP_POST_BOTTOM)
	public static Block lamp_post_bottom;
	@ObjectHolder(DecorNames.LAMP_POST_MIDDLE)
	public static Block lamp_post_middle;
	@ObjectHolder(DecorNames.LAMP_POST_TOP)
	public static Block lamp_post_top;
	@ObjectHolder(DecorNames.FIREPLACE)
	public static Block fireplace;
	@ObjectHolder(DecorNames.FIRERING)
	public static Block firering;
	@ObjectHolder(DecorNames.FIREPIT)
	public static Block firepit;
	@ObjectHolder(DecorNames.CHIMNEY)
	public static Block chimney;
	@ObjectHolder(DecorNames.STOVE)
	public static Block stove;
	@ObjectHolder(DecorNames.GRILL)
	public static Block grill;

	// Super slope shapes
	@ObjectHolder(DecorNames.CORNER)
	public static Block corner;
	@ObjectHolder(DecorNames.SLOPE)
	public static Block slope;
	@ObjectHolder(DecorNames.SLOPED_ANGLE)
	public static Block sloped_angle;
	@ObjectHolder(DecorNames.SLANTED_CORNER)
	public static Block slanted_corner;
	@ObjectHolder(DecorNames.OBLIQUE_SLOPE)
	public static Block oblique_slope;
	@ObjectHolder(DecorNames.SLOPED_INTERSECTION)
	public static Block sloped_intersection;
	@ObjectHolder(DecorNames.PYRAMID)
	public static Block pyramid;
	@ObjectHolder(DecorNames.FULL_PYRAMID)
	public static Block full_pyramid;
	@ObjectHolder(DecorNames.SLOPED_POST)
	public static Block sloped_post;
	@ObjectHolder(DecorNames.STAIRS)
	public static Block stairs;
	@ObjectHolder(DecorNames.PILLAR)
	public static Block pillar;

	@SubscribeEvent
	public void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (DecorConfig.subpartFluro.get()) {
			r.register(new BlockIlluminationTube());
			r.register(new BlockFluro(DecorNames.WHITE_FLURO));
			r.register(new BlockFluro(DecorNames.ORANGE_FLURO));
			r.register(new BlockFluro(DecorNames.MAGENTA_FLURO));
			r.register(new BlockFluro(DecorNames.LIGHT_BLUE_FLURO));
			r.register(new BlockFluro(DecorNames.YELLOW_FLURO));
			r.register(new BlockFluro(DecorNames.LIME_FLURO));
			r.register(new BlockFluro(DecorNames.PINK_FLURO));
			r.register(new BlockFluro(DecorNames.GRAY_FLURO));
			r.register(new BlockFluro(DecorNames.LIGHT_GRAY_FLURO));
			r.register(new BlockFluro(DecorNames.CYAN_FLURO));
			r.register(new BlockFluro(DecorNames.PURPLE_FLURO));
			r.register(new BlockFluro(DecorNames.BLUE_FLURO));
			r.register(new BlockFluro(DecorNames.BROWN_FLURO));
			r.register(new BlockFluro(DecorNames.GREEN_FLURO));
			r.register(new BlockFluro(DecorNames.RED_FLURO));
			r.register(new BlockFluro(DecorNames.BLACK_FLURO));
		}

		if (DecorConfig.subpartCalendar.get())
			r.register(new BlockCalendar());

		if (DecorConfig.subpartAlarm.get())
			r.register(new BlockAlarm());

		if (DecorConfig.subpartWallClock.get())
			r.register(new BlockWallClock());

		if (DecorConfig.subpartLightBulbs.get())
			r.register(new BlockLightBulb());

		if (DecorConfig.subpartLanterns.get()) {
			r.register(new BlockLantern(DecorNames.PAPER_LANTERN));
			r.register(new BlockLantern(DecorNames.BONE_LANTERN));
			r.register(new BlockLantern(DecorNames.IRON_LANTERN));
		}

		if (DecorConfig.subpartCages.get()) {
			r.register(new BlockCage());
			r.register(new BlockChain());
		}

		if (DecorConfig.subpartDecorations.get()) {
			r.register(new BlockRoad());
			r.register(new BlockFancyStone());
			r.register(new BlockPot());
			r.register(new BlockCraftClay());
			r.register(new BlockCraftBone());
		}

		if (DecorConfig.subpartNeonSign.get()) {
			r.register(new BlockNeonSignStanding());
			r.register(new BlockNeonSignWall());
		}

		if (DecorConfig.subpartColorizer.get()) {
			r.register(new BlockHardenedWood());
			r.register(new BlockColorizer(DecorNames.COLORIZER));
			r.register(new BlockColorizerLight());

			if (DecorConfig.subpartFurniture.get()) {
				r.register(new BlockColorizerCounter());
				r.register(new BlockColorizerStool());
				r.register(new BlockColorizerChair());
				r.register(new BlockColorizerWall());
				r.register(new BlockColorizerFence());
				r.register(new BlockColorizerFenceGate());
				r.register(new BlockColorizerDoor());
				r.register(new BlockColorizerTrapDoor());
				r.register(new BlockColorizerTable());
			}

			if (DecorConfig.subpartLampPosts.get()) {
				r.register(new BlockColorizerLampPost(DecorNames.LAMP_POST_BOTTOM, false));
				r.register(new BlockColorizerLampPost(DecorNames.LAMP_POST_MIDDLE, false));
				r.register(new BlockColorizerLampPost(DecorNames.LAMP_POST_TOP, true));
			}

			if (DecorConfig.subpartFireplaces.get()) {
				r.register(new BlockBurningWood());
				r.register(new BlockColorizerFireplace());
				r.register(new BlockColorizerFirering());
				r.register(new BlockColorizerFirepit());
				r.register(new BlockColorizerChimney());
				r.register(new BlockColorizerStove());
				r.register(new BlockColorizerGrill());
			}

			if (DecorConfig.subpartSlopes.get()) {
				r.register(new BlockColorizerSlopedRotate(SlopeType.Corner));
				r.register(new BlockColorizerHedge(SlopeType.Pyramid));
				r.register(new BlockColorizerHedge(SlopeType.FullPyramid));
				r.register(new BlockColorizerSlopedRotate(SlopeType.Slope));
				r.register(new BlockColorizerSlopedRotate(SlopeType.SlopedAngle));
				r.register(new BlockColorizerSlopedRotate(SlopeType.SlantedCorner));
				r.register(new BlockColorizerSlopedRotate(SlopeType.ObliqueSlope));
				r.register(new BlockColorizerSlopedRotate(SlopeType.SlopedIntersection));
				r.register(new BlockColorizerHedge(SlopeType.SlopedPost));
				r.register(new BlockColorizerFacing(DecorNames.PILLAR));
				r.register(new BlockColorizerStairs());
			}
		}
	}

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (DecorConfig.subpartFluro.get()) {
			r.register(new ItemManualBlock(white_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(white_fluro.getRegistryName()));
			r.register(new ItemManualBlock(orange_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(orange_fluro.getRegistryName()));
			r.register(new ItemManualBlock(magenta_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(magenta_fluro.getRegistryName()));
			r.register(new ItemManualBlock(light_blue_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(light_blue_fluro.getRegistryName()));
			r.register(new ItemManualBlock(yellow_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(yellow_fluro.getRegistryName()));
			r.register(new ItemManualBlock(lime_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(lime_fluro.getRegistryName()));
			r.register(new ItemManualBlock(pink_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(pink_fluro.getRegistryName()));
			r.register(new ItemManualBlock(gray_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(gray_fluro.getRegistryName()));
			r.register(new ItemManualBlock(light_gray_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(light_gray_fluro.getRegistryName()));
			r.register(new ItemManualBlock(cyan_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(cyan_fluro.getRegistryName()));
			r.register(new ItemManualBlock(purple_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(purple_fluro.getRegistryName()));
			r.register(new ItemManualBlock(blue_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(blue_fluro.getRegistryName()));
			r.register(new ItemManualBlock(brown_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(brown_fluro.getRegistryName()));
			r.register(new ItemManualBlock(green_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(green_fluro.getRegistryName()));
			r.register(new ItemManualBlock(red_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(red_fluro.getRegistryName()));
			r.register(new ItemManualBlock(black_fluro, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(black_fluro.getRegistryName()));
			r.register(new ItemManualBlock(illumination_tube, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(illumination_tube.getRegistryName()));
		}

		if (DecorConfig.subpartCalendar.get())
			r.register(new ItemManualBlock(calendar, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(calendar.getRegistryName()));

		if (DecorConfig.subpartAlarm.get())
			r.register(new ItemManualBlock(alarm, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(alarm.getRegistryName()));

		if (DecorConfig.subpartWallClock.get())
			r.register(new ItemManualBlock(wall_clock, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(wall_clock.getRegistryName()));

		if (DecorConfig.subpartLightBulbs.get())
			r.register(new ItemManualBlock(light_bulb, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(light_bulb.getRegistryName()));

		if (DecorConfig.subpartLanterns.get()) {
			r.register(new ItemLantern(paper_lantern).setRegistryName(paper_lantern.getRegistryName()));
			r.register(new ItemLantern(bone_lantern).setRegistryName(bone_lantern.getRegistryName()));
			r.register(new ItemLantern(iron_lantern).setRegistryName(iron_lantern.getRegistryName()));
		}

		if (DecorConfig.subpartDecorations.get()) {
			r.register(new ItemManualBlock(road, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(road.getRegistryName()));
			r.register(new ItemManualBlock(fancy_stone, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(fancy_stone.getRegistryName()));
			r.register(new ItemManualBlock(pot, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(pot.getRegistryName()));
			r.register(new ItemManualBlock(craft_clay, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(craft_clay.getRegistryName()));
			r.register(new ItemManualBlock(craft_bone, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(craft_bone.getRegistryName()));
		}

		if (DecorConfig.subpartCages.get()) {
			r.register(new ItemManualBlock(chain, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(chain.getRegistryName()));
			r.register(new ItemManualBlock(cage, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(cage.getRegistryName()));
		}

		if (DecorConfig.subpartColorizer.get()) {
			r.register(new ItemManualBlock(hardened_wood, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(hardened_wood.getRegistryName()));
			r.register(new ItemColorizer(colorizer).setRegistryName(colorizer.getRegistryName()));
			r.register(new ItemColorizer(colorizer_light).setRegistryName(colorizer_light.getRegistryName()));

			if (DecorConfig.subpartFireplaces.get()) {
				r.register(new ItemManualBlock(burning_wood, new Item.Properties().group(GrimItemGroups.GRIM_DECOR)).setRegistryName(burning_wood.getRegistryName()));
				r.register(new ItemColorizer(fireplace).setRegistryName(fireplace.getRegistryName()));
				r.register(new ItemColorizer(firering).setRegistryName(firering.getRegistryName()));
				r.register(new ItemColorizer(firepit).setRegistryName(firepit.getRegistryName()));
				r.register(new ItemColorizer(chimney).setRegistryName(chimney.getRegistryName()));
				r.register(new ItemColorizer(stove).setRegistryName(stove.getRegistryName()));
				r.register(new ItemGrill(grill).setRegistryName(grill.getRegistryName()));
			}

			if (DecorConfig.subpartFurniture.get()) {
				r.register(new ItemColorizer(table).setRegistryName(table.getRegistryName()));
				r.register(new ItemColorizer(counter).setRegistryName(counter.getRegistryName()));
				r.register(new ItemColorizer(stool).setRegistryName(stool.getRegistryName()));
				r.register(new ItemColorizer(chair).setRegistryName(chair.getRegistryName()));
				r.register(new ItemColorizer(wall).setRegistryName(wall.getRegistryName()));
				r.register(new ItemColorizer(fence).setRegistryName(fence.getRegistryName()));
				r.register(new ItemColorizer(fence_gate).setRegistryName(fence_gate.getRegistryName()));
				r.register(new ItemDecorDoor(door).setRegistryName(door.getRegistryName()));
				r.register(new ItemColorizer(trap_door).setRegistryName(trap_door.getRegistryName()));
			}

			if (DecorConfig.subpartSlopes.get()) {
				r.register(new ItemSloped(corner).setRegistryName(corner.getRegistryName()));
				r.register(new ItemSloped(slope, true).setRegistryName(slope.getRegistryName()));
				r.register(new ItemSloped(sloped_angle).setRegistryName(sloped_angle.getRegistryName()));
				r.register(new ItemSloped(slanted_corner).setRegistryName(slanted_corner.getRegistryName()));
				r.register(new ItemSloped(oblique_slope).setRegistryName(oblique_slope.getRegistryName()));
				r.register(new ItemSloped(sloped_intersection).setRegistryName(sloped_intersection.getRegistryName()));
				r.register(new ItemSloped(pyramid).setRegistryName(pyramid.getRegistryName()));
				r.register(new ItemSloped(full_pyramid).setRegistryName(full_pyramid.getRegistryName()));
				r.register(new ItemSloped(sloped_post).setRegistryName(sloped_post.getRegistryName()));
				r.register(new ItemSloped(pillar).setRegistryName(pillar.getRegistryName()));
				r.register(new ItemColorizer(stairs).setRegistryName(stairs.getRegistryName()));
			}
		}
	}

	/*
	 * private void initTileEntities() { if (DecorConfig.subpartCalendar)
	 * GameRegistry.registerTileEntity(TileEntityCalendar.class, new
	 * ResourceLocation(GrimPack.modID, "calendar")); if (DecorConfig.subpartAlarm)
	 * GameRegistry.registerTileEntity(TileEntityAlarm.class, new
	 * ResourceLocation(GrimPack.modID, "alarm")); if (DecorConfig.subpartWallClock)
	 * GameRegistry.registerTileEntity(TileEntityWallClock.class, new
	 * ResourceLocation(GrimPack.modID, "wall_clock")); if
	 * (DecorConfig.subpartColorizer) {
	 * GameRegistry.registerTileEntity(TileEntityColorizer.class, new
	 * ResourceLocation(GrimPack.modID, "colorizer")); if
	 * (DecorConfig.subpartFireplaces)
	 * GameRegistry.registerTileEntity(TileEntityGrill.class, new
	 * ResourceLocation(GrimPack.modID, "grill")); }
	 * 
	 * if (DecorConfig.subpartCages)
	 * GameRegistry.registerTileEntity(TileEntityCage.class, new
	 * ResourceLocation(GrimPack.modID, "cage"));
	 * 
	 * if (DecorConfig.subpartNeonSign)
	 * GameRegistry.registerTileEntity(TileEntityNeonSign.class, new
	 * ResourceLocation(GrimPack.modID, "neon_sign")); }
	 * 
	 * @SubscribeEvent public void registerRecipes(RegistryEvent.Register<IRecipe>
	 * evt) { if (DecorConfig.subpartDecorations)
	 * GameRegistry.addSmelting(Blocks.GRAVEL, new ItemStack(road, 1), 0.15F); }
	 */
}
