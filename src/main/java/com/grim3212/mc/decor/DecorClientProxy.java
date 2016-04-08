package com.grim3212.mc.decor;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.proxy.ClientProxy;
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

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class DecorClientProxy extends ClientProxy {

	@Override
	public void registerModels() {
		MinecraftForge.EVENT_BUS.register(new ModelEvent());
		ModelBakery.registerItemVariants(Item.getItemFromBlock(DecorBlocks.lamp_post_top), new ResourceLocation(GrimDecor.modID, "lamp_post_top_lamp"));
		ModelEvent.renderCustomModel(DecorBlocks.lamp_post_top, "inventory", "normal");
		ModelEvent.renderCustomModel(DecorBlocks.lamp_post_middle, "inventory", "normal");
		ModelEvent.renderCustomModel(DecorBlocks.lamp_post_bottom, "inventory", "normal");
		ModelEvent.renderCustomModel(DecorItems.lamp_item, "inventory");
		ModelEvent.renderCustomModel(DecorBlocks.stool, "inventory", "up=false", "up=true");
		ModelEvent.renderCustomModel(DecorBlocks.counter, "inventory", "normal");
		ModelEvent.renderCustomModel(DecorBlocks.chair, "inventory", "facing=north", "facing=south", "facing=west", "facing=east");
		ModelEvent.renderCustomModel(DecorBlocks.wall, "inventory", "facing=north", "facing=south", "facing=west", "facing=east");
		ModelEvent.renderCustomModel(DecorBlocks.fence, "inventory", "east=false,north=false,south=false,west=false", "east=false,north=true,south=false,west=false", "east=true,north=false,south=false,west=false", "east=false,north=false,south=true,west=false", "east=false,north=false,south=false,west=true", "east=true,north=true,south=false,west=false", "east=true,north=false,south=true,west=false", "east=false,north=false,south=true,west=true", "east=false,north=true,south=false,west=true",
				"east=false,north=true,south=true,west=false", "east=true,north=false,south=false,west=true", "east=true,north=true,south=true,west=false", "east=true,north=false,south=true,west=true", "east=false,north=true,south=true,west=true", "east=true,north=true,south=false,west=true", "east=true,north=true,south=true,west=true");
		ModelEvent.renderCustomModel(DecorBlocks.fence_gate, "inventory", "facing=south,open=false,powered=false", "facing=west,open=false,powered=false", "facing=north,open=false,powered=false", "facing=east,open=false,powered=false", "facing=south,open=true,powered=false", "facing=west,open=true,powered=false", "facing=north,open=true,powered=false", "facing=east,open=true,powered=false", "facing=south,open=false,powered=true", "facing=west,open=false,powered=true",
				"facing=north,open=false,powered=true", "facing=east,open=false,powered=true", "facing=south,open=true,powered=true", "facing=west,open=true,powered=true", "facing=north,open=true,powered=true", "facing=east,open=true,powered=true");
		ModelEvent.renderCustomModel(DecorBlocks.table, "inventory", "east=false,north=false,south=false,west=false", "east=false,north=true,south=false,west=false", "east=true,north=false,south=false,west=false", "east=false,north=false,south=true,west=false", "east=false,north=false,south=false,west=true", "east=true,north=true,south=false,west=false", "east=true,north=false,south=true,west=false", "east=false,north=false,south=true,west=true", "east=false,north=true,south=false,west=true",
				"east=false,north=true,south=true,west=false", "east=true,north=false,south=false,west=true", "east=true,north=true,south=true,west=false", "east=true,north=false,south=true,west=true", "east=false,north=true,south=true,west=true", "east=true,north=true,south=false,west=true", "east=true,north=true,south=true,west=true");

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

}
