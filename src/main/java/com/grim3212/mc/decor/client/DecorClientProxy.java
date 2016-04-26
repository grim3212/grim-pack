package com.grim3212.mc.decor.client;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.decor.DecorCommonProxy;
import com.grim3212.mc.decor.block.BlockLantern.EnumLanternType;
import com.grim3212.mc.decor.block.DecorBlocks;
import com.grim3212.mc.decor.client.entity.RenderFrame.FrameFactory;
import com.grim3212.mc.decor.client.entity.RenderWallpaper.WallpaperFactory;
import com.grim3212.mc.decor.client.event.ModelEvent;
import com.grim3212.mc.decor.client.tile.TileEntityCalendarRenderer;
import com.grim3212.mc.decor.entity.EntityFrame;
import com.grim3212.mc.decor.entity.EntityWallpaper;
import com.grim3212.mc.decor.item.DecorItems;
import com.grim3212.mc.decor.tile.TileEntityCalendar;

import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class DecorClientProxy extends DecorCommonProxy {

	@Override
	public void produceSmoke(World world, BlockPos pos, double xMod, double yMod, double zMod, int amount, boolean makelarge) {
		for (int i = 0; i < amount; i++) {
			double xVar = (world.rand.nextDouble() - 0.5D) / 5.0D;
			double yVar = (world.rand.nextDouble() - 0.5D) / 5.0D;
			double zVar = (world.rand.nextDouble() - 0.5D) / 5.0D;
			world.spawnParticle(makelarge ? EnumParticleTypes.SMOKE_LARGE : EnumParticleTypes.SMOKE_NORMAL, pos.getX() + xMod + xVar, pos.getY() + yMod + yVar, pos.getZ() + zMod + zVar, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected void registerModels() {
		MinecraftForge.EVENT_BUS.register(new ModelEvent());
		RenderHelper.registerExtraModel(DecorBlocks.lamp_post_top, "lamp_post_top_lamp");
		RenderHelper.registerExtraModel(DecorItems.lamp_item, "lamp_item_lamp");
		RenderHelper.registerExtraModels(DecorBlocks.firepit, "firepit_wood", "firepit_covered", "fire_high");
		RenderHelper.registerExtraModel(DecorBlocks.firering, "firering_wood");
		RenderHelper.registerExtraModels(DecorBlocks.fireplace, "fireplace_wood", "fire");
		RenderHelper.registerExtraModel(DecorBlocks.chimney, "chimney_top");
		RenderHelper.registerExtraModels(DecorBlocks.stove, "stove_wood", "stove_covered");
		RenderHelper.registerExtraModels(DecorBlocks.grill, "grill_coal", "grill_fire");

		ModelEvent.renderNormalModel(DecorBlocks.chimney);
		ModelEvent.renderNormalModel(DecorBlocks.stove);
		ModelEvent.renderNormalModel(DecorBlocks.firepit);
		ModelEvent.renderNormalModel(DecorBlocks.firering);
		ModelEvent.renderNormalModel(DecorBlocks.grill);
		ModelEvent.renderNormalModel(DecorBlocks.lamp_post_top);
		ModelEvent.renderNormalModel(DecorBlocks.lamp_post_middle);
		ModelEvent.renderNormalModel(DecorBlocks.lamp_post_bottom);
		ModelEvent.renderNormalModel(DecorBlocks.counter);

		ModelEvent.renderCustomModel(DecorItems.lamp_item, "inventory");
		ModelEvent.renderCustomModel(DecorBlocks.stool, "inventory", "up=false", "up=true");
		ModelEvent.renderCustomModel(DecorBlocks.fireplace, "inventory", "east=false,north=false,south=false,west=false", "east=false,north=true,south=false,west=false", "east=true,north=false,south=false,west=false", "east=false,north=false,south=true,west=false", "east=false,north=false,south=false,west=true", "east=true,north=true,south=false,west=false", "east=true,north=false,south=true,west=false", "east=false,north=false,south=true,west=true", "east=false,north=true,south=false,west=true", "east=false,north=true,south=true,west=false", "east=true,north=false,south=false,west=true",
				"east=true,north=true,south=true,west=false", "east=true,north=false,south=true,west=true", "east=false,north=true,south=true,west=true", "east=true,north=true,south=false,west=true", "east=true,north=true,south=true,west=true");
		ModelEvent.renderCustomModel(DecorBlocks.chair, "inventory", "facing=north", "facing=south", "facing=west", "facing=east");
		ModelEvent.renderCustomModel(DecorBlocks.wall, "inventory", "facing=north", "facing=south", "facing=west", "facing=east");
		ModelEvent.renderCustomModel(DecorBlocks.fence, "inventory", "east=false,north=false,south=false,west=false", "east=false,north=true,south=false,west=false", "east=true,north=false,south=false,west=false", "east=false,north=false,south=true,west=false", "east=false,north=false,south=false,west=true", "east=true,north=true,south=false,west=false", "east=true,north=false,south=true,west=false", "east=false,north=false,south=true,west=true", "east=false,north=true,south=false,west=true", "east=false,north=true,south=true,west=false", "east=true,north=false,south=false,west=true",
				"east=true,north=true,south=true,west=false", "east=true,north=false,south=true,west=true", "east=false,north=true,south=true,west=true", "east=true,north=true,south=false,west=true", "east=true,north=true,south=true,west=true");
		ModelEvent.renderCustomModel(DecorBlocks.fence_gate, "inventory", "facing=south,open=false,powered=false", "facing=west,open=false,powered=false", "facing=north,open=false,powered=false", "facing=east,open=false,powered=false", "facing=south,open=true,powered=false", "facing=west,open=true,powered=false", "facing=north,open=true,powered=false", "facing=east,open=true,powered=false", "facing=south,open=false,powered=true", "facing=west,open=false,powered=true", "facing=north,open=false,powered=true", "facing=east,open=false,powered=true", "facing=south,open=true,powered=true",
				"facing=west,open=true,powered=true", "facing=north,open=true,powered=true", "facing=east,open=true,powered=true");
		ModelEvent.renderCustomModel(DecorBlocks.table, "inventory", "east=false,north=false,south=false,west=false", "east=false,north=true,south=false,west=false", "east=true,north=false,south=false,west=false", "east=false,north=false,south=true,west=false", "east=false,north=false,south=false,west=true", "east=true,north=true,south=false,west=false", "east=true,north=false,south=true,west=false", "east=false,north=false,south=true,west=true", "east=false,north=true,south=false,west=true", "east=false,north=true,south=true,west=false", "east=true,north=false,south=false,west=true",
				"east=true,north=true,south=true,west=false", "east=true,north=false,south=true,west=true", "east=false,north=true,south=true,west=true", "east=true,north=true,south=false,west=true", "east=true,north=true,south=true,west=true");

		// ITEMS
		RenderHelper.renderItem(DecorItems.glass_shard);
		RenderHelper.renderItem(DecorItems.wallpaper);
		RenderHelper.renderVariantForge(DecorItems.frame, new String[] { "wood", "iron" });
		RenderHelper.renderItem(DecorItems.unfired_craft);
		RenderHelper.renderItem(DecorItems.unfired_pot);

		// BLOCKS
		RenderHelper.renderBlock(DecorBlocks.calendar);
		RenderHelper.renderBlock(DecorBlocks.wall_clock);
		RenderHelper.renderBlockWithMetaInInventory(DecorBlocks.light_bulb, 2);
		RenderHelper.renderVariantForge(DecorBlocks.lantern, EnumLanternType.names());
		RenderHelper.renderBlock(DecorBlocks.road);
		RenderHelper.renderBlock(DecorBlocks.fancy_stone);
		RenderHelper.renderBlock(DecorBlocks.chain);
		RenderHelper.renderBlock(DecorBlocks.cage);
		RenderHelper.renderBlock(DecorBlocks.craft_bone);
		RenderHelper.renderBlock(DecorBlocks.craft_clay);
		RenderHelper.renderBlock(DecorBlocks.pot);

		// TILE ENTITYS
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalendar.class, new TileEntityCalendarRenderer());

		// ENTITYS
		RenderingRegistry.registerEntityRenderingHandler(EntityFrame.class, new FrameFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityWallpaper.class, new WallpaperFactory());
	}

	@Override
	protected void registerManual(ModSection modSection) {
		ManualRegistry.addSection("calendar", modSection).addSubSectionPages(new PageCrafting("calendar", new ItemStack(DecorBlocks.calendar)));
		ManualRegistry.addSection("wall", modSection).addSubSectionPages(new PageCrafting("clock", DecorBlocks.clocks, 20));
		ManualRegistry.addSection("lights", modSection).addSubSectionPages(new PageCrafting("recipes", DecorBlocks.lights, 25));
	}

	@Override
	public void preInit(ModSection modSection) {
		registerModels();
		registerManual(modSection);
	}
}
