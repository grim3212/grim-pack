package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.item.ItemManualBlock;
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
import com.grim3212.mc.pack.decor.item.ItemColorizer;
import com.grim3212.mc.pack.decor.item.ItemDecorDoor;
import com.grim3212.mc.pack.decor.item.ItemDecorStairs;
import com.grim3212.mc.pack.decor.item.ItemGrill;
import com.grim3212.mc.pack.decor.item.ItemLantern;
import com.grim3212.mc.pack.decor.item.ItemSloped;
import com.grim3212.mc.pack.decor.tile.TileEntityAlarm;
import com.grim3212.mc.pack.decor.tile.TileEntityCage;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;
import com.grim3212.mc.pack.decor.tile.TileEntityWallClock;
import com.grim3212.mc.pack.decor.util.DecorUtil.SlopeType;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class DecorBlocks {

	public static final Block illumination_tube = new BlockIlluminationTube();
	public static final Block fluro = new BlockFluro();
	public static final Block neon_sign_standing = new BlockNeonSignStanding();
	public static final Block neon_sign_wall = new BlockNeonSignWall();
	public static final Block alarm = new BlockAlarm();
	public static final Block calendar = new BlockCalendar();
	public static final Block wall_clock = new BlockWallClock();
	public static final Block light_bulb = new BlockLightBulb();
	public static final Block pot = new BlockPot();
	public static final Block cage = new BlockDecoration(Material.IRON, true);
	public static final Block chain = new BlockDecoration(Material.CIRCUITS, false);
	public static final Block road = new BlockRoad();
	public static final Block lantern = new BlockLantern();
	public static final Block fancy_stone = new BlockFancyStone();
	public static final Block craft_clay = new BlockCraftClay();
	public static final Block craft_bone = new BlockCraftBone();
	public static final Block counter = new BlockColorizerCounter();
	public static final Block stool = new BlockColorizerStool();
	public static final Block chair = new BlockColorizerChair();
	public static final Block wall = new BlockColorizerWall();
	public static final Block table = new BlockColorizerTable();
	public static final Block fence = new BlockColorizerFence();
	public static final Block fence_gate = new BlockColorizerFenceGate();
	public static final Block lamp_post_bottom = new BlockColorizerLampPost("lamp_post_bottom", false);
	public static final Block lamp_post_middle = new BlockColorizerLampPost("lamp_post_middle", false);
	public static final Block lamp_post_top = new BlockColorizerLampPost("lamp_post_top", true);
	public static final Block fireplace = new BlockColorizerFireplace();
	public static final Block firering = new BlockColorizerFirering();
	public static final Block firepit = new BlockColorizerFirepit();
	public static final Block chimney = new BlockColorizerChimney();
	public static final Block stove = new BlockColorizerStove();
	public static final Block grill = new BlockColorizerGrill();
	public static final Block hardened_wood = new BlockHardenedWood();
	public static final Block colorizer = new BlockColorizer("colorizer");
	public static final Block colorizer_light = new BlockColorizerLight();
	public static final Block burning_wood = new BlockBurningWood();
	// Super slope shapes
	public static final Block corner = new BlockColorizerSlopedRotate(SlopeType.Corner);
	public static final Block slope = new BlockColorizerSlopedRotate(SlopeType.Slope);
	public static final Block sloped_angle = new BlockColorizerSlopedRotate(SlopeType.SlopedAngle);
	public static final Block slanted_corner = new BlockColorizerSlopedRotate(SlopeType.SlantedCorner);
	public static final Block oblique_slope = new BlockColorizerSlopedRotate(SlopeType.ObliqueSlope);
	public static final Block sloped_intersection = new BlockColorizerSlopedRotate(SlopeType.SlopedIntersection);
	public static final Block pyramid = new BlockColorizerHedge(SlopeType.Pyramid);
	public static final Block full_pyramid = new BlockColorizerHedge(SlopeType.FullPyramid);
	public static final Block sloped_post = new BlockColorizerHedge(SlopeType.SlopedPost);
	public static final Block decor_stairs = new BlockColorizerStairs();
	public static final Block decor_door = new BlockColorizerDoor();
	public static final Block pillar = new BlockColorizerFacing("pillar");
	public static final Block decor_trap_door = new BlockColorizerTrapDoor();

	@SubscribeEvent
	public void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (DecorConfig.subpartFluro) {
			r.register(fluro);
			r.register(illumination_tube);
		}

		if (DecorConfig.subpartCalendar)
			r.register(calendar);

		if (DecorConfig.subpartAlarm)
			r.register(alarm);

		if (DecorConfig.subpartWallClock)
			r.register(wall_clock);

		if (DecorConfig.subpartLightBulbs)
			r.register(light_bulb);

		if (DecorConfig.subpartLanterns)
			r.register(lantern);

		if (DecorConfig.subpartCages) {
			r.register(chain);
			r.register(cage);
		}

		if (DecorConfig.subpartDecorations) {
			r.register(road);
			r.register(fancy_stone);
			r.register(pot);
			r.register(craft_clay);
			r.register(craft_bone);
		}

		if (DecorConfig.subpartNeonSign) {
			r.register(neon_sign_standing);
			r.register(neon_sign_wall);
		}

		if (DecorConfig.subpartColorizer) {
			r.register(hardened_wood);
			r.register(colorizer);
			r.register(colorizer_light);

			if (DecorConfig.subpartFurniture) {
				r.register(counter);
				r.register(stool);
				r.register(chair);
				r.register(wall);
				r.register(fence);
				r.register(fence_gate);
				r.register(decor_door);
				r.register(decor_trap_door);
				r.register(table);
			}

			if (DecorConfig.subpartLampPosts) {
				r.register(lamp_post_bottom);
				r.register(lamp_post_middle);
				r.register(lamp_post_top);
			}

			if (DecorConfig.subpartFireplaces) {
				r.register(burning_wood);
				r.register(fireplace);
				r.register(firering);
				r.register(firepit);
				r.register(chimney);
				r.register(stove);
				r.register(grill);
			}

			if (DecorConfig.subpartSlopes) {
				r.register(corner);
				r.register(pyramid);
				r.register(full_pyramid);
				r.register(slope);
				r.register(sloped_angle);
				r.register(slanted_corner);
				r.register(oblique_slope);
				r.register(sloped_intersection);
				r.register(sloped_post);
				r.register(pillar);
				r.register(decor_stairs);
			}
		}

		initTileEntities();
	}

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (DecorConfig.subpartFluro) {
			r.register(new ItemCloth(fluro).setRegistryName(fluro.getRegistryName()));
			r.register(new ItemManualBlock(illumination_tube).setRegistryName(illumination_tube.getRegistryName()));
		}

		if (DecorConfig.subpartCalendar)
			r.register(new ItemManualBlock(calendar).setRegistryName(calendar.getRegistryName()));

		if (DecorConfig.subpartAlarm)
			r.register(new ItemManualBlock(alarm).setRegistryName(alarm.getRegistryName()));

		if (DecorConfig.subpartWallClock)
			r.register(new ItemManualBlock(wall_clock).setRegistryName(wall_clock.getRegistryName()));

		if (DecorConfig.subpartLightBulbs)
			r.register(new ItemManualBlock(light_bulb).setRegistryName(light_bulb.getRegistryName()));

		if (DecorConfig.subpartLanterns)
			r.register(new ItemLantern(lantern).setRegistryName(lantern.getRegistryName()));

		if (DecorConfig.subpartDecorations) {
			r.register(new ItemManualBlock(road).setRegistryName(road.getRegistryName()));
			r.register(new ItemManualBlock(fancy_stone).setRegistryName(fancy_stone.getRegistryName()));
			r.register(new ItemManualBlock(pot).setRegistryName(pot.getRegistryName()));
			r.register(new ItemManualBlock(craft_clay).setRegistryName(craft_clay.getRegistryName()));
			r.register(new ItemManualBlock(craft_bone).setRegistryName(craft_bone.getRegistryName()));
		}

		if (DecorConfig.subpartCages) {
			r.register(new ItemManualBlock(chain).setRegistryName(chain.getRegistryName()));
			r.register(new ItemManualBlock(cage).setRegistryName(cage.getRegistryName()));
		}

		if (DecorConfig.subpartColorizer) {
			r.register(new ItemManualBlock(hardened_wood).setRegistryName(hardened_wood.getRegistryName()));
			r.register(new ItemColorizer(colorizer).setRegistryName(colorizer.getRegistryName()));
			r.register(new ItemColorizer(colorizer_light).setRegistryName(colorizer_light.getRegistryName()));

			if (DecorConfig.subpartFireplaces) {
				r.register(new ItemManualBlock(burning_wood).setRegistryName(burning_wood.getRegistryName()));
				r.register(new ItemColorizer(fireplace).setRegistryName(fireplace.getRegistryName()));
				r.register(new ItemColorizer(firering).setRegistryName(firering.getRegistryName()));
				r.register(new ItemColorizer(firepit).setRegistryName(firepit.getRegistryName()));
				r.register(new ItemColorizer(chimney).setRegistryName(chimney.getRegistryName()));
				r.register(new ItemColorizer(stove).setRegistryName(stove.getRegistryName()));
				r.register(new ItemGrill(grill).setRegistryName(grill.getRegistryName()));
			}

			if (DecorConfig.subpartFurniture) {
				r.register(new ItemColorizer(table).setRegistryName(table.getRegistryName()));
				r.register(new ItemColorizer(counter).setRegistryName(counter.getRegistryName()));
				r.register(new ItemColorizer(stool).setRegistryName(stool.getRegistryName()));
				r.register(new ItemColorizer(chair).setRegistryName(chair.getRegistryName()));
				r.register(new ItemColorizer(wall).setRegistryName(wall.getRegistryName()));
				r.register(new ItemColorizer(fence).setRegistryName(fence.getRegistryName()));
				r.register(new ItemColorizer(fence_gate).setRegistryName(fence_gate.getRegistryName()));
				r.register(new ItemDecorDoor(decor_door).setRegistryName(decor_door.getRegistryName()));
				r.register(new ItemColorizer(decor_trap_door).setRegistryName(decor_trap_door.getRegistryName()));
			}

			if (DecorConfig.subpartSlopes) {
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
				r.register(new ItemDecorStairs(decor_stairs).setRegistryName(decor_stairs.getRegistryName()));
			}
		}
	}

	private void initTileEntities() {
		if (DecorConfig.subpartCalendar)
			GameRegistry.registerTileEntity(TileEntityCalendar.class, new ResourceLocation(GrimPack.modID, "calendar"));
		if (DecorConfig.subpartAlarm)
			GameRegistry.registerTileEntity(TileEntityAlarm.class, new ResourceLocation(GrimPack.modID, "alarm"));
		if (DecorConfig.subpartWallClock)
			GameRegistry.registerTileEntity(TileEntityWallClock.class, new ResourceLocation(GrimPack.modID, "wall_clock"));
		if (DecorConfig.subpartColorizer) {
			GameRegistry.registerTileEntity(TileEntityColorizer.class, new ResourceLocation(GrimPack.modID, "colorizer"));
			if (DecorConfig.subpartFireplaces)
				GameRegistry.registerTileEntity(TileEntityGrill.class, new ResourceLocation(GrimPack.modID, "grill"));
		}

		if (DecorConfig.subpartCages)
			GameRegistry.registerTileEntity(TileEntityCage.class, new ResourceLocation(GrimPack.modID, "cage"));

		if (DecorConfig.subpartNeonSign)
			GameRegistry.registerTileEntity(TileEntityNeonSign.class, new ResourceLocation(GrimPack.modID, "neon_sign"));
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		if (DecorConfig.subpartDecorations)
			GameRegistry.addSmelting(Blocks.GRAVEL, new ItemStack(road, 1), 0.15F);
	}
}
