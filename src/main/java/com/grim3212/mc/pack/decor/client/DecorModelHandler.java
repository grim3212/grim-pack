package com.grim3212.mc.pack.decor.client;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.decor.block.BlockAlarm;
import com.grim3212.mc.pack.decor.block.BlockLantern.EnumLanternType;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerChimney;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate;
import com.grim3212.mc.pack.decor.client.model.DecorModelLoader;
import com.grim3212.mc.pack.decor.client.model.SlopedModelLoader;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DecorModelHandler {

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent evt) {
		if (DecorConfig.subpartColorizer) {

			ModelLoaderRegistry.registerLoader(DecorModelLoader.instance);

			RenderHelper.renderItem(DecorItems.brush);
			RenderHelper.renderBlock(DecorBlocks.hardened_wood);
			RenderHelper.renderBlock(DecorBlocks.colorizer);
			RenderHelper.renderBlock(DecorBlocks.colorizer_light);

			if (DecorConfig.subpartSlopes) {
				// Needed to be able to get the OBJ models and modify with a
				// different
				// model loader
				OBJLoader.INSTANCE.addDomain(GrimPack.modID);
				// Register all custom models for furniture, fireplaces, and
				// lamp posts
				ModelLoaderRegistry.registerLoader(SlopedModelLoader.instance);

				RenderHelper.renderItem(DecorItems.pruners);
				RenderHelper.renderBlock(DecorBlocks.pillar);
				RenderHelper.renderBlock(DecorBlocks.decor_stairs);
				RenderHelper.renderBlock(DecorBlocks.sloped_post);
				RenderHelper.renderBlock(DecorBlocks.full_pyramid);
				RenderHelper.renderBlock(DecorBlocks.pyramid);
				RenderHelper.renderBlock(DecorBlocks.corner);
				RenderHelper.renderBlock(DecorBlocks.slanted_corner);
				RenderHelper.renderBlock(DecorBlocks.sloped_angle);
				RenderHelper.renderBlock(DecorBlocks.slope);
				RenderHelper.renderBlock(DecorBlocks.oblique_slope);
				RenderHelper.renderBlock(DecorBlocks.sloped_intersection);

			}

			if (DecorConfig.subpartFurniture) {
				ModelLoader.setCustomStateMapper(DecorBlocks.corner, new StateMap.Builder().ignore(BlockColorizerSlopedRotate.HALF).build());
				ModelLoader.setCustomStateMapper(DecorBlocks.fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
				ModelLoader.setCustomStateMapper(DecorBlocks.decor_door, new StateMap.Builder().ignore(BlockDoor.POWERED).build());

				RenderHelper.renderBlock(DecorBlocks.decor_trap_door);
				RenderHelper.renderBlock(DecorBlocks.decor_door);
				RenderHelper.renderBlock(DecorBlocks.counter);
				RenderHelper.renderBlock(DecorBlocks.table);
				RenderHelper.renderBlock(DecorBlocks.stool);
				RenderHelper.renderBlock(DecorBlocks.chair);
				RenderHelper.renderBlock(DecorBlocks.wall);
				RenderHelper.renderBlock(DecorBlocks.fence);
				RenderHelper.renderBlock(DecorBlocks.fence_gate);
			}

			if (DecorConfig.subpartFireplaces) {
				ModelLoader.setCustomStateMapper(DecorBlocks.chimney, new StateMap.Builder().ignore(BlockColorizerChimney.ACTIVE).build());

				RenderHelper.renderBlock(DecorBlocks.burning_wood);
				RenderHelper.renderBlock(DecorBlocks.chimney);
				RenderHelper.renderBlock(DecorBlocks.stove);
				RenderHelper.renderBlock(DecorBlocks.firepit);
				RenderHelper.renderBlock(DecorBlocks.firering);
				RenderHelper.renderBlock(DecorBlocks.grill);
				RenderHelper.renderBlock(DecorBlocks.fireplace);
			}

			if (DecorConfig.subpartLampPosts) {
				RenderHelper.renderItem(DecorItems.lamp_item);
				RenderHelper.renderBlockNormal(DecorBlocks.lamp_post_bottom);
				RenderHelper.renderBlockNormal(DecorBlocks.lamp_post_middle);
				RenderHelper.renderBlockNormal(DecorBlocks.lamp_post_top);
			}
		}

		if (DecorConfig.subpartFlatItemFrame)
			RenderHelper.renderVariantForge(DecorItems.flat_item_frame, "held", "base", "map");

		if (DecorConfig.subpartLightBulbs) {
			RenderHelper.renderItem(DecorItems.glass_shard);
			RenderHelper.renderBlockWithMetaInInventory(DecorBlocks.light_bulb, 2);
		}

		if (DecorConfig.subpartAlarm) {
			ModelLoader.setCustomStateMapper(DecorBlocks.alarm, new StateMap.Builder().ignore(BlockAlarm.POWERED).build());

			RenderHelper.renderBlock(DecorBlocks.alarm);
		}

		if (DecorConfig.subpartWallpaper)
			RenderHelper.renderItem(DecorItems.wallpaper);

		if (DecorConfig.subpartFrames)
			RenderHelper.renderVariantForge(DecorItems.frame, new String[] { "wood", "iron" });

		if (DecorConfig.subpartDecorations) {
			RenderHelper.renderItem(DecorItems.unfired_craft);
			RenderHelper.renderItem(DecorItems.unfired_pot);
			RenderHelper.renderBlock(DecorBlocks.road);
			RenderHelper.renderBlock(DecorBlocks.fancy_stone);
			RenderHelper.renderBlock(DecorBlocks.craft_bone);
			RenderHelper.renderBlock(DecorBlocks.craft_clay);
			RenderHelper.renderBlock(DecorBlocks.pot);
		}

		if (DecorConfig.subpartCalendar)
			RenderHelper.renderBlock(DecorBlocks.calendar);

		if (DecorConfig.subpartWallClock)
			RenderHelper.renderBlock(DecorBlocks.wall_clock);

		if (DecorConfig.subpartLanterns)
			RenderHelper.renderVariantForge(DecorBlocks.lantern, EnumLanternType.names());

		if (DecorConfig.subpartCages) {
			RenderHelper.renderBlock(DecorBlocks.chain);
			RenderHelper.renderBlock(DecorBlocks.cage);
		}

		if (DecorConfig.subpartNeonSign) {
			ModelLoader.setCustomStateMapper(DecorBlocks.neon_sign_standing, new StateMap.Builder().ignore(BlockStandingSign.ROTATION).build());
			ModelLoader.setCustomStateMapper(DecorBlocks.neon_sign_wall, new StateMap.Builder().ignore(BlockWallSign.FACING).build());

			RenderHelper.renderBlockNormal(DecorBlocks.neon_sign_standing);
			RenderHelper.renderBlockNormal(DecorBlocks.neon_sign_wall);

			RenderHelper.renderItem(DecorItems.neon_sign);
		}
	}
}
