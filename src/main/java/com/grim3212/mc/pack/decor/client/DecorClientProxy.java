package com.grim3212.mc.pack.decor.client;

import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.ModSection;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.decor.DecorCommonProxy;
import com.grim3212.mc.pack.decor.block.BlockChimney;
import com.grim3212.mc.pack.decor.block.BlockFirepit;
import com.grim3212.mc.pack.decor.block.BlockFireplace;
import com.grim3212.mc.pack.decor.block.BlockFirering;
import com.grim3212.mc.pack.decor.block.BlockGrill;
import com.grim3212.mc.pack.decor.block.BlockLantern.EnumLanternType;
import com.grim3212.mc.pack.decor.block.BlockStove;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.entity.RenderFrame.FrameFactory;
import com.grim3212.mc.pack.decor.client.entity.RenderWallpaper.WallpaperFactory;
import com.grim3212.mc.pack.decor.client.event.ModelEvent;
import com.grim3212.mc.pack.decor.client.model.DecorModelLoader;
import com.grim3212.mc.pack.decor.client.tile.TileEntityCalendarRenderer;
import com.grim3212.mc.pack.decor.entity.EntityFrame;
import com.grim3212.mc.pack.decor.entity.EntityWallpaper;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;

import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
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
	public void registerModels() {
		ModelLoaderRegistry.registerLoader(DecorModelLoader.instance);

		MinecraftForge.EVENT_BUS.register(new ModelEvent());

		ModelLoader.setCustomStateMapper(DecorBlocks.fireplace, new StateMap.Builder().ignore(BlockFireplace.ACTIVE).build());
		ModelLoader.setCustomStateMapper(DecorBlocks.firepit, new StateMap.Builder().ignore(BlockFirepit.ACTIVE).build());
		ModelLoader.setCustomStateMapper(DecorBlocks.firering, new StateMap.Builder().ignore(BlockFirering.ACTIVE).build());
		ModelLoader.setCustomStateMapper(DecorBlocks.chimney, new StateMap.Builder().ignore(BlockChimney.ACTIVE).build());
		ModelLoader.setCustomStateMapper(DecorBlocks.stove, new StateMap.Builder().ignore(BlockStove.ACTIVE).build());
		ModelLoader.setCustomStateMapper(DecorBlocks.grill, new StateMap.Builder().ignore(BlockGrill.ACTIVE).build());

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

		RenderHelper.renderBlock(DecorBlocks.counter);
		RenderHelper.renderBlock(DecorBlocks.table);
		RenderHelper.renderBlock(DecorBlocks.stool);

		ModelEvent.renderCustomModel(DecorItems.lamp_item, "inventory");
		ModelEvent.renderCustomModel(DecorBlocks.fireplace, "inventory", "east=false,north=false,south=false,west=false", "east=false,north=true,south=false,west=false", "east=true,north=false,south=false,west=false", "east=false,north=false,south=true,west=false", "east=false,north=false,south=false,west=true", "east=true,north=true,south=false,west=false", "east=true,north=false,south=true,west=false", "east=false,north=false,south=true,west=true", "east=false,north=true,south=false,west=true", "east=false,north=true,south=true,west=false", "east=true,north=false,south=false,west=true",
				"east=true,north=true,south=true,west=false", "east=true,north=false,south=true,west=true", "east=false,north=true,south=true,west=true", "east=true,north=true,south=false,west=true", "east=true,north=true,south=true,west=true");
		ModelEvent.renderCustomModel(DecorBlocks.chair, "inventory", "facing=north", "facing=south", "facing=west", "facing=east");
		ModelEvent.renderCustomModel(DecorBlocks.wall, "inventory", "facing=north", "facing=south", "facing=west", "facing=east");
		ModelEvent.renderCustomModel(DecorBlocks.fence, "inventory", "east=false,north=false,south=false,west=false", "east=false,north=true,south=false,west=false", "east=true,north=false,south=false,west=false", "east=false,north=false,south=true,west=false", "east=false,north=false,south=false,west=true", "east=true,north=true,south=false,west=false", "east=true,north=false,south=true,west=false", "east=false,north=false,south=true,west=true", "east=false,north=true,south=false,west=true", "east=false,north=true,south=true,west=false", "east=true,north=false,south=false,west=true",
				"east=true,north=true,south=true,west=false", "east=true,north=false,south=true,west=true", "east=false,north=true,south=true,west=true", "east=true,north=true,south=false,west=true", "east=true,north=true,south=true,west=true");
		ModelEvent.renderCustomModel(DecorBlocks.fence_gate, "inventory", "facing=south,open=false,powered=false", "facing=west,open=false,powered=false", "facing=north,open=false,powered=false", "facing=east,open=false,powered=false", "facing=south,open=true,powered=false", "facing=west,open=true,powered=false", "facing=north,open=true,powered=false", "facing=east,open=true,powered=false", "facing=south,open=false,powered=true", "facing=west,open=false,powered=true", "facing=north,open=false,powered=true", "facing=east,open=false,powered=true", "facing=south,open=true,powered=true",
				"facing=west,open=true,powered=true", "facing=north,open=true,powered=true", "facing=east,open=true,powered=true");

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
	public void registerManual(ModSection modSection) {
		ManualRegistry.addSection("intro", modSection).addSubSectionPages(new PageInfo("fancy"), new PageCrafting("moss", DecorBlocks.mossy), new PageCrafting("stone", DecorBlocks.stone, 25), new PageFurnace("road", new ItemStack(Blocks.GRAVEL)));
		ManualRegistry.addSection("hanging", modSection).addSubSectionPages(new PageCrafting("calendar", new ItemStack(DecorBlocks.calendar)), new PageCrafting("clock", DecorBlocks.clocks, 20), new PageInfo("wallpaperinfo"), new PageCrafting("wallpaper", new ItemStack(DecorItems.wallpaper)), new PageImageText("frameinfo", "framesInfoPage.png"), new PageCrafting("frames", DecorItems.frames, 25));
		ManualRegistry.addSection("deco", modSection).addSubSectionPages(new PageCrafting("cage", DecorBlocks.chains, 20), new PageCrafting("lantern", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.lantern)), 20), new PageCrafting("crafts", DecorBlocks.crafts, 25), new PageFurnace("firing", new ItemStack[] { new ItemStack(DecorItems.unfired_craft), new ItemStack(DecorItems.unfired_pot) }, 20), new PageCrafting("lights", DecorBlocks.lights, 25));
		ManualRegistry.addSection("furniture", modSection).addSubSectionPages(new PageCrafting("table", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.table)), 15), new PageCrafting("chair", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.chair)), 15), new PageCrafting("stool", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.stool)), 15), new PageCrafting("counter", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.counter)), 15));
		ManualRegistry.addSection("other", modSection).addSubSectionPages(new PageCrafting("fence", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.fence)), 15), new PageCrafting("fencegate", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.fence_gate)), 15), new PageCrafting("wall", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.wall)), 20));
		ManualRegistry.addSection("fires", modSection).addSubSectionPages(new PageCrafting("fireplace", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.fireplace)), 15), new PageCrafting("chimney", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.chimney)), 15), new PageCrafting("stove", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.stove)), 15), new PageCrafting("firepit", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.firepit)), 15),
				new PageCrafting("firering", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.firering)), 15));
		ManualRegistry.addSection("grill", modSection).addSubSectionPages(new PageCrafting("grill", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorBlocks.grill)), 15));
		ManualRegistry.addSection("lamps", modSection).addSubSectionPages(new PageCrafting("recipes", RecipeHelper.getAllIRecipesForItem(new ItemStack(DecorItems.lamp_item)), 10));
	}
}
