package com.grim3212.mc.pack.decor.client;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.DecorCommonProxy;
import com.grim3212.mc.pack.decor.block.BlockChimney;
import com.grim3212.mc.pack.decor.block.BlockColorizer;
import com.grim3212.mc.pack.decor.block.BlockFenceGate;
import com.grim3212.mc.pack.decor.block.BlockLantern.EnumLanternType;
import com.grim3212.mc.pack.decor.block.BlockSloped;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.entity.RenderFrame.FrameFactory;
import com.grim3212.mc.pack.decor.client.entity.RenderWallpaper.WallpaperFactory;
import com.grim3212.mc.pack.decor.client.model.DecorModelLoader;
import com.grim3212.mc.pack.decor.client.model.SlopedModelLoader;
import com.grim3212.mc.pack.decor.client.tile.TileEntityCalendarRenderer;
import com.grim3212.mc.pack.decor.entity.EntityFrame;
import com.grim3212.mc.pack.decor.entity.EntityWallpaper;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
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
	public void preInit() {
		// Needed to be able to get the OBJ models and modify with a different
		// model loader
		OBJLoader.INSTANCE.addDomain(GrimPack.modID);
		// Register all custom models for furniture, fireplaces, and lamp posts
		ModelLoaderRegistry.registerLoader(SlopedModelLoader.instance);
		ModelLoaderRegistry.registerLoader(DecorModelLoader.instance);

		ModelLoader.setCustomStateMapper(DecorBlocks.corner, new StateMap.Builder().ignore(BlockSloped.HALF).build());
		ModelLoader.setCustomStateMapper(DecorBlocks.fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
		ModelLoader.setCustomStateMapper(DecorBlocks.chimney, new StateMap.Builder().ignore(BlockChimney.ACTIVE).build());

		// ITEMS
		RenderHelper.renderItem(DecorItems.pruners);
		RenderHelper.renderItem(DecorItems.brush);
		RenderHelper.renderItem(DecorItems.glass_shard);
		RenderHelper.renderItem(DecorItems.wallpaper);
		RenderHelper.renderVariantForge(DecorItems.frame, new String[] { "wood", "iron" });
		RenderHelper.renderItem(DecorItems.unfired_craft);
		RenderHelper.renderItem(DecorItems.unfired_pot);
		RenderHelper.renderItem(DecorItems.lamp_item);

		// BLOCKS
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
		RenderHelper.renderBlock(DecorBlocks.burning_wood);
		RenderHelper.renderBlock(DecorBlocks.hardened_wood);
		RenderHelper.renderBlock(DecorBlocks.colorizer);
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

		// TILE ENTITYS
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalendar.class, new TileEntityCalendarRenderer());

		// ENTITYS
		RenderingRegistry.registerEntityRenderingHandler(EntityFrame.class, new FrameFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityWallpaper.class, new WallpaperFactory());
	}

	@Override
	public void initColors() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				if (pos != null) {
					TileEntity te = worldIn.getTileEntity(pos);
					if (te != null && te instanceof TileEntityColorizer) {
						// Just in case...
						if (!(((TileEntityColorizer) te).getBlockState().getBlock() instanceof BlockColorizer))
							return Minecraft.getMinecraft().getBlockColors().colorMultiplier(((TileEntityColorizer) te).getBlockState(), worldIn, pos, tintIndex);
					}
				}
				return 16777215;
			}
		}, DecorBlocks.decor_stairs, DecorBlocks.corner, DecorBlocks.sloped_post, DecorBlocks.full_pyramid, DecorBlocks.slope, DecorBlocks.sloped_angle, DecorBlocks.sloped_intersection, DecorBlocks.oblique_slope, DecorBlocks.slanted_corner, DecorBlocks.pyramid, DecorBlocks.colorizer, DecorBlocks.counter, DecorBlocks.table, DecorBlocks.stool, DecorBlocks.chair, DecorBlocks.wall, DecorBlocks.fence, DecorBlocks.fence_gate, DecorBlocks.lamp_post_bottom, DecorBlocks.lamp_post_middle, DecorBlocks.lamp_post_top, DecorBlocks.grill, DecorBlocks.chimney, DecorBlocks.stove, DecorBlocks.firepit,
				DecorBlocks.firering, DecorBlocks.fireplace);

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (stack != null && stack.hasTagCompound()) {
					if (stack.getTagCompound().hasKey("registryName")) {
						Block block = Block.REGISTRY.getObject(new ResourceLocation(NBTHelper.getString(stack, "registryName")));
						if (stack.getTagCompound().hasKey("meta")) {
							ItemStack colorStack = new ItemStack(block, 1, NBTHelper.getInt(stack, "meta"));
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
		}, DecorBlocks.decor_stairs, DecorBlocks.corner, DecorBlocks.sloped_post, DecorBlocks.full_pyramid, DecorBlocks.slope, DecorBlocks.sloped_angle, DecorBlocks.sloped_intersection, DecorBlocks.oblique_slope, DecorBlocks.slanted_corner, DecorBlocks.pyramid, DecorBlocks.colorizer, DecorBlocks.grill, DecorBlocks.chimney, DecorBlocks.stove, DecorBlocks.firepit, DecorBlocks.firering, DecorBlocks.fireplace, DecorBlocks.counter, DecorBlocks.table, DecorBlocks.stool, DecorBlocks.chair, DecorBlocks.wall, DecorBlocks.fence, DecorBlocks.fence_gate, DecorBlocks.lamp_post_bottom,
				DecorBlocks.lamp_post_middle, DecorBlocks.lamp_post_top);

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (stack != null && stack.hasTagCompound()) {
					if (stack.getTagCompound().hasKey("registryName")) {
						Block block = Block.REGISTRY.getObject(new ResourceLocation(NBTHelper.getString(stack, "registryName")));
						if (stack.getTagCompound().hasKey("meta")) {
							ItemStack colorStack = new ItemStack(block, 1, NBTHelper.getInt(stack, "meta"));
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
