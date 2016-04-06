package com.grim3212.mc.decor;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.decor.block.BlockLantern.EnumLanternType;
import com.grim3212.mc.decor.block.DecorBlocks;
import com.grim3212.mc.decor.client.entity.RenderFrame.FrameFactory;
import com.grim3212.mc.decor.client.entity.RenderWallpaper.WallpaperFactory;
import com.grim3212.mc.decor.client.tile.TileEntityCalendarRenderer;
import com.grim3212.mc.decor.entity.EntityFrame;
import com.grim3212.mc.decor.entity.EntityWallpaper;
import com.grim3212.mc.decor.item.DecorItems;
import com.grim3212.mc.decor.tile.TileEntityCalendar;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class DecorClientProxy extends ClientProxy {

	@Override
	public void registerModels() {
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
