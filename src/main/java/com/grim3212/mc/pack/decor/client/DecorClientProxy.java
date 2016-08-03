package com.grim3212.mc.pack.decor.client;

import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.ModSection;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageImageText;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.decor.DecorCommonProxy;
import com.grim3212.mc.pack.decor.block.BlockChimney;
import com.grim3212.mc.pack.decor.block.BlockFenceGate;
import com.grim3212.mc.pack.decor.block.BlockLantern.EnumLanternType;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.entity.RenderFrame.FrameFactory;
import com.grim3212.mc.pack.decor.client.entity.RenderWallpaper.WallpaperFactory;
import com.grim3212.mc.pack.decor.client.model.DecorModelLoader;
import com.grim3212.mc.pack.decor.client.tile.TileEntityCalendarRenderer;
import com.grim3212.mc.pack.decor.entity.EntityFrame;
import com.grim3212.mc.pack.decor.entity.EntityWallpaper;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;
import com.grim3212.mc.pack.decor.tile.TileEntityTextured;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
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
		// Register all custom models for furniture, fireplaces, and lamp posts
		ModelLoaderRegistry.registerLoader(DecorModelLoader.instance);

		ModelLoader.setCustomStateMapper(DecorBlocks.fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
		ModelLoader.setCustomStateMapper(DecorBlocks.chimney, new StateMap.Builder().ignore(BlockChimney.ACTIVE).build());

		// ITEMS
		RenderHelper.renderItem(DecorItems.glass_shard);
		RenderHelper.renderItem(DecorItems.wallpaper);
		RenderHelper.renderVariantForge(DecorItems.frame, new String[] { "wood", "iron" });
		RenderHelper.renderItem(DecorItems.unfired_craft);
		RenderHelper.renderItem(DecorItems.unfired_pot);
		RenderHelper.renderItem(DecorItems.lamp_item);

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
		RenderHelper.renderBlock(DecorBlocks.chimney);
		RenderHelper.renderBlock(DecorBlocks.stove);
		RenderHelper.renderBlock(DecorBlocks.firepit);
		RenderHelper.renderBlock(DecorBlocks.firering);
		RenderHelper.renderBlock(DecorBlocks.grill);
		RenderHelper.renderBlock(DecorBlocks.fireplace);
		RenderHelper.renderBlock(DecorBlocks.counter);
		RenderHelper.renderBlock(DecorBlocks.table);
		RenderHelper.renderBlock(DecorBlocks.stool);
		RenderHelper.renderBlock(DecorBlocks.chair);
		RenderHelper.renderBlock(DecorBlocks.wall);
		RenderHelper.renderBlock(DecorBlocks.fence);
		RenderHelper.renderBlock(DecorBlocks.fence_gate);
		RenderHelper.renderBlock(DecorBlocks.lamp_post_bottom);
		RenderHelper.renderBlock(DecorBlocks.lamp_post_middle);
		RenderHelper.renderBlock(DecorBlocks.lamp_post_top);
		RenderHelper.renderBlock(DecorBlocks.hardened_wood);
		RenderHelper.renderBlock(DecorBlocks.colorizer);

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

	@Override
	@SuppressWarnings("deprecation")
	public void initColors() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				if (pos != null) {
					TileEntity te = worldIn.getTileEntity(pos);
					if (te != null && te instanceof TileEntityTextured) {
						return Minecraft.getMinecraft().getBlockColors().colorMultiplier(Block.getBlockById(((TileEntityTextured) te).getBlockID()).getStateFromMeta(((TileEntityTextured) te).getBlockMeta()), worldIn, pos, tintIndex);
					}
				}
				return 16777215;
			}
		}, DecorBlocks.grill, DecorBlocks.chimney, DecorBlocks.stove, DecorBlocks.firepit, DecorBlocks.firering, DecorBlocks.fireplace, DecorBlocks.counter, DecorBlocks.table, DecorBlocks.stool, DecorBlocks.chair, DecorBlocks.wall, DecorBlocks.fence, DecorBlocks.fence_gate, DecorBlocks.lamp_post_bottom, DecorBlocks.lamp_post_middle, DecorBlocks.lamp_post_top);

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (stack != null && stack.hasTagCompound()) {
					if (stack.getTagCompound().hasKey("blockID")) {
						Block block = Block.getBlockById(NBTHelper.getInt(stack, "blockID"));
						if (stack.getTagCompound().hasKey("blockMeta")) {
							ItemStack colorStack = new ItemStack(block, 1, NBTHelper.getInt(stack, "blockMeta"));
							if (colorStack.getItem() != null) {
								return Minecraft.getMinecraft().getItemColors().getColorFromItemstack(colorStack, tintIndex);
							}
						} else {
							ItemStack colorStack = new ItemStack(block);
							if (colorStack.getItem() != null) {
								return Minecraft.getMinecraft().getItemColors().getColorFromItemstack(colorStack, tintIndex);
							}
						}
					}
				}
				return 16777215;
			}
		}, DecorBlocks.grill, DecorBlocks.chimney, DecorBlocks.stove, DecorBlocks.firepit, DecorBlocks.firering, DecorBlocks.fireplace, DecorBlocks.counter, DecorBlocks.table, DecorBlocks.stool, DecorBlocks.chair, DecorBlocks.wall, DecorBlocks.fence, DecorBlocks.fence_gate, DecorBlocks.lamp_post_bottom, DecorBlocks.lamp_post_middle, DecorBlocks.lamp_post_top);

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (stack != null && stack.hasTagCompound()) {
					if (stack.getTagCompound().hasKey("blockID")) {
						Block block = Block.getBlockById(NBTHelper.getInt(stack, "blockID"));
						if (stack.getTagCompound().hasKey("blockMeta")) {
							ItemStack colorStack = new ItemStack(block, 1, NBTHelper.getInt(stack, "blockMeta"));
							if (colorStack.getItem() != null) {
								return Minecraft.getMinecraft().getItemColors().getColorFromItemstack(colorStack, tintIndex);
							}
						} else {
							ItemStack colorStack = new ItemStack(block);
							if (colorStack.getItem() != null) {
								return Minecraft.getMinecraft().getItemColors().getColorFromItemstack(colorStack, tintIndex);
							}
						}
					}
				}
				return 16777215;
			}
		}, DecorItems.lamp_item);
	}
}
