package com.grim3212.mc.pack.decor.proxy;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizer;
import com.grim3212.mc.pack.decor.client.DecorModelHandler;
import com.grim3212.mc.pack.decor.client.entity.RenderFrame;
import com.grim3212.mc.pack.decor.client.entity.RenderWallpaper;
import com.grim3212.mc.pack.decor.client.tile.TileEntityCageRenderer;
import com.grim3212.mc.pack.decor.client.tile.TileEntityCalendarRenderer;
import com.grim3212.mc.pack.decor.client.tile.TileEntityNeonSignRenderer;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.entity.EntityFrame;
import com.grim3212.mc.pack.decor.entity.EntityWallpaper;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityCage;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.ForgeRegistries;

public class DecorClientProxy extends DecorServerProxy {

	@Override
	public void produceSmoke(World world, BlockPos pos, double xMod, double yMod, double zMod, int amount, boolean makelarge) {
		for (int i = 0; i < amount; i++) {
			double xVar = (world.rand.nextDouble() - 0.5D) / 5.0D;
			double yVar = (world.rand.nextDouble() - 0.5D) / 5.0D;
			double zVar = (world.rand.nextDouble() - 0.5D) / 5.0D;
			world.addParticle(makelarge ? ParticleTypes.LARGE_SMOKE : ParticleTypes.SMOKE, pos.getX() + xMod + xVar, pos.getY() + yMod + yVar, pos.getZ() + zMod + zVar, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new DecorModelHandler());

		// TILE ENTITYS
		if (DecorConfig.subpartCalendar.get())
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalendar.class, new TileEntityCalendarRenderer());
		if (DecorConfig.subpartCages.get())
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCage.class, new TileEntityCageRenderer());
		if (DecorConfig.subpartNeonSign.get())
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNeonSign.class, new TileEntityNeonSignRenderer());

		// ENTITYS
		if (DecorConfig.subpartFrames.get())
			RenderingRegistry.registerEntityRenderingHandler(EntityFrame.class, RenderFrame::new);
		if (DecorConfig.subpartWallpaper.get())
			RenderingRegistry.registerEntityRenderingHandler(EntityWallpaper.class, RenderWallpaper::new);
	}

	@Override
	public void initColors() {
		if (DecorConfig.subpartColorizer.get()) {
			List<Item> itemColors = Lists.newArrayList();
			List<Block> blockColors = Lists.newArrayList(DecorBlocks.colorizer, DecorBlocks.colorizer_light);

			if (DecorConfig.subpartFireplaces.get()) {
				blockColors.addAll(Lists.newArrayList(DecorBlocks.grill, DecorBlocks.chimney, DecorBlocks.stove, DecorBlocks.firepit, DecorBlocks.firering, DecorBlocks.fireplace));
			}

			if (DecorConfig.subpartFurniture.get()) {
				blockColors.addAll(Lists.newArrayList(DecorBlocks.counter, DecorBlocks.table, DecorBlocks.stool, DecorBlocks.chair, DecorBlocks.wall, DecorBlocks.fence, DecorBlocks.fence_gate, DecorBlocks.trap_door, DecorBlocks.door));
			}

			if (DecorConfig.subpartLampPosts.get()) {
				itemColors.add(DecorItems.lamp_item);
				blockColors.addAll(Lists.newArrayList(DecorBlocks.lamp_post_bottom, DecorBlocks.lamp_post_middle, DecorBlocks.lamp_post_top));
			}

			if (DecorConfig.subpartSlopes.get()) {
				blockColors.addAll(Lists.newArrayList(DecorBlocks.stairs, DecorBlocks.corner, DecorBlocks.sloped_post, DecorBlocks.full_pyramid, DecorBlocks.slope, DecorBlocks.sloped_angle, DecorBlocks.sloped_intersection, DecorBlocks.oblique_slope, DecorBlocks.slanted_corner, DecorBlocks.pyramid, DecorBlocks.pillar));
			}

			Block[] blocks = blockColors.toArray(new Block[blockColors.size()]);

			Minecraft.getInstance().getBlockColors().register(new IBlockColor() {
				@Override
				public int getColor(BlockState state, IEnviromentBlockReader worldIn, BlockPos pos, int tint) {
					if (pos != null) {
						TileEntity te = worldIn.getTileEntity(pos);
						if (te != null && te instanceof TileEntityColorizer) {
							// Just in case...
							if (!(((TileEntityColorizer) te).getBlockState().getBlock() instanceof BlockColorizer))
								return Minecraft.getInstance().getBlockColors().getColor(((TileEntityColorizer) te).getBlockState(), worldIn, pos, tint);
						}
					}
					return 16777215;
				}
			}, blocks);

			Minecraft.getInstance().getItemColors().register(new IItemColor() {
				@Override
				public int getColor(ItemStack stack, int tintIndex) {
					if (stack != null && stack.hasTag()) {
						if (stack.getTag().contains("registryName")) {
							Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(NBTHelper.getString(stack, "registryName")));
							ItemStack colorStack = new ItemStack(block);
							if (colorStack.getItem() != null) {
								return Minecraft.getInstance().getItemColors().getColor(colorStack, tintIndex);
							}
						}
					}
					return 16777215;
				}
			}, blocks);

			Minecraft.getInstance().getItemColors().register(new IItemColor() {
				@Override
				public int getColor(ItemStack stack, int tintIndex) {
					if (stack != null && stack.hasTag()) {
						if (stack.getTag().contains("registryName")) {
							Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(NBTHelper.getString(stack, "registryName")));
							ItemStack colorStack = new ItemStack(block);
							if (colorStack.getItem() != null) {
								return Minecraft.getInstance().getItemColors().getColor(colorStack, tintIndex);
							}
						}
					}
					return 16777215;
				}
			}, itemColors.toArray(new Item[itemColors.size()]));
		}

		if (DecorConfig.subpartFluro.get()) {
			Block[] blocks = new Block[] { DecorBlocks.white_fluro, DecorBlocks.orange_fluro, DecorBlocks.magenta_fluro, DecorBlocks.light_blue_fluro, DecorBlocks.yellow_fluro, DecorBlocks.lime_fluro, DecorBlocks.pink_fluro, DecorBlocks.gray_fluro, DecorBlocks.light_gray_fluro, DecorBlocks.cyan_fluro, DecorBlocks.purple_fluro, DecorBlocks.blue_fluro, DecorBlocks.brown_fluro, DecorBlocks.green_fluro, DecorBlocks.red_fluro, DecorBlocks.black_fluro };
			int[] colors = new int[] { 16383998, 16351261, 13061821, 3847130, 16701501, 8439583, 15961002, 4673362, 10329495, 1481884, 8991416, 3949738, 8606770, 6192150, 11546150, 1908001 };

			Minecraft.getInstance().getBlockColors().register(new IBlockColor() {
				@Override
				public int getColor(BlockState state, IEnviromentBlockReader world, BlockPos pos, int tintIndex) {
					if (state != null) {
						for (int i = 0; i < blocks.length; i++) {
							if (state.getBlock() == blocks[i]) {
								return colors[i];
							}
						}
					}
					return 16777215;
				}
			}, blocks);

			Minecraft.getInstance().getItemColors().register(new IItemColor() {
				@Override
				public int getColor(ItemStack stack, int tintIndex) {
					if (!stack.isEmpty()) {
						for (int i = 0; i < blocks.length; i++) {
							if (ItemStack.areItemsEqual(stack, new ItemStack(blocks[i]))) {
								return colors[i];
							}
						}
					}
					return 16777215;
				}
			}, blocks);
		}
	}
}
