package com.grim3212.mc.pack.decor.proxy;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.BlockFluro;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizer;
import com.grim3212.mc.pack.decor.client.DecorModelHandler;
import com.grim3212.mc.pack.decor.client.entity.RenderFlatItemFrame.FlatItemFrameFactory;
import com.grim3212.mc.pack.decor.client.entity.RenderFrame.FrameFactory;
import com.grim3212.mc.pack.decor.client.entity.RenderWallpaper.WallpaperFactory;
import com.grim3212.mc.pack.decor.client.tile.TileEntityCageRenderer;
import com.grim3212.mc.pack.decor.client.tile.TileEntityCalendarRenderer;
import com.grim3212.mc.pack.decor.client.tile.TileEntityNeonSignRenderer;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.entity.EntityFlatItemFrame;
import com.grim3212.mc.pack.decor.entity.EntityFrame;
import com.grim3212.mc.pack.decor.entity.EntityWallpaper;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityCage;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class DecorClientProxy extends DecorServerProxy {

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
		MinecraftForge.EVENT_BUS.register(new DecorModelHandler());

		// TILE ENTITYS
		if (DecorConfig.subpartCalendar)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalendar.class, new TileEntityCalendarRenderer());
		if (DecorConfig.subpartCages)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCage.class, new TileEntityCageRenderer());
		if (DecorConfig.subpartNeonSign)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNeonSign.class, new TileEntityNeonSignRenderer());

		// ENTITYS
		if (DecorConfig.subpartFrames)
			RenderingRegistry.registerEntityRenderingHandler(EntityFrame.class, new FrameFactory());
		if (DecorConfig.subpartWallpaper)
			RenderingRegistry.registerEntityRenderingHandler(EntityWallpaper.class, new WallpaperFactory());
		if (DecorConfig.subpartFlatItemFrame)
			RenderingRegistry.registerEntityRenderingHandler(EntityFlatItemFrame.class, new FlatItemFrameFactory());
	}

	@Override
	public void initColors() {
		if (DecorConfig.subpartColorizer) {
			List<Item> itemColors = Lists.newArrayList();
			List<Block> blockColors = Lists.newArrayList(DecorBlocks.colorizer, DecorBlocks.colorizer_light);

			if (DecorConfig.subpartFireplaces) {
				blockColors.addAll(Lists.newArrayList(DecorBlocks.grill, DecorBlocks.chimney, DecorBlocks.stove, DecorBlocks.firepit, DecorBlocks.firering, DecorBlocks.fireplace));
			}

			if (DecorConfig.subpartFurniture) {
				blockColors.addAll(Lists.newArrayList(DecorBlocks.counter, DecorBlocks.table, DecorBlocks.stool, DecorBlocks.chair, DecorBlocks.wall, DecorBlocks.fence, DecorBlocks.fence_gate, DecorBlocks.decor_trap_door, DecorBlocks.decor_door));
			}

			if (DecorConfig.subpartLampPosts) {
				itemColors.add(DecorItems.lamp_item);
				blockColors.addAll(Lists.newArrayList(DecorBlocks.lamp_post_bottom, DecorBlocks.lamp_post_middle, DecorBlocks.lamp_post_top));
			}

			if (DecorConfig.subpartSlopes) {
				blockColors.addAll(Lists.newArrayList(DecorBlocks.decor_stairs, DecorBlocks.corner, DecorBlocks.sloped_post, DecorBlocks.full_pyramid, DecorBlocks.slope, DecorBlocks.sloped_angle, DecorBlocks.sloped_intersection, DecorBlocks.oblique_slope, DecorBlocks.slanted_corner, DecorBlocks.pyramid, DecorBlocks.pillar));
			}

			Block[] blocks = blockColors.toArray(new Block[blockColors.size()]);

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
			}, blocks);

			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
				@Override
				public int colorMultiplier(ItemStack stack, int tintIndex) {
					if (stack != null && stack.hasTagCompound()) {
						if (stack.getTagCompound().hasKey("registryName")) {
							Block block = Block.REGISTRY.getObject(new ResourceLocation(NBTHelper.getString(stack, "registryName")));
							if (stack.getTagCompound().hasKey("meta")) {
								ItemStack colorStack = new ItemStack(block, 1, NBTHelper.getInt(stack, "meta"));
								if (colorStack.getItem() != null) {
									return Minecraft.getMinecraft().getItemColors().colorMultiplier(colorStack, tintIndex);
								}
							} else {
								ItemStack colorStack = new ItemStack(block);
								if (colorStack.getItem() != null) {
									return Minecraft.getMinecraft().getItemColors().colorMultiplier(colorStack, tintIndex);
								}
							}
						}
					}
					return 16777215;
				}
			}, blocks);

			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
				@Override
				public int colorMultiplier(ItemStack stack, int tintIndex) {
					if (stack != null && stack.hasTagCompound()) {
						if (stack.getTagCompound().hasKey("registryName")) {
							Block block = Block.REGISTRY.getObject(new ResourceLocation(NBTHelper.getString(stack, "registryName")));
							if (stack.getTagCompound().hasKey("meta")) {
								ItemStack colorStack = new ItemStack(block, 1, NBTHelper.getInt(stack, "meta"));
								if (colorStack.getItem() != null) {
									return Minecraft.getMinecraft().getItemColors().colorMultiplier(colorStack, tintIndex);
								}
							} else {
								ItemStack colorStack = new ItemStack(block);
								if (colorStack.getItem() != null) {
									return Minecraft.getMinecraft().getItemColors().colorMultiplier(colorStack, tintIndex);
								}
							}
						}
					}
					return 16777215;
				}
			}, itemColors.toArray(new Item[itemColors.size()]));
		}

		if (DecorConfig.subpartFluro) {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
				public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
					if (state != null) {
						return state.getValue(BlockFluro.COLOR).getColorValue();
					}
					return 16777215;
				}
			}, DecorBlocks.fluro);

			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
				@Override
				public int colorMultiplier(ItemStack stack, int tintIndex) {
					if (!stack.isEmpty()) {
						return EnumDyeColor.byMetadata(stack.getMetadata()).getColorValue();
					}
					return 16777215;
				}
			}, DecorBlocks.fluro);
		}
	}
}
