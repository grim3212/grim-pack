package com.grim3212.mc.pack.decor.client;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.DecorCommonProxy;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizer;
import com.grim3212.mc.pack.decor.client.entity.RenderFlatItemFrame.FlatItemFrameFactory;
import com.grim3212.mc.pack.decor.client.entity.RenderFrame.FrameFactory;
import com.grim3212.mc.pack.decor.client.entity.RenderWallpaper.WallpaperFactory;
import com.grim3212.mc.pack.decor.client.tile.TileEntityCageRenderer;
import com.grim3212.mc.pack.decor.client.tile.TileEntityCalendarRenderer;
import com.grim3212.mc.pack.decor.entity.EntityFlatItemFrame;
import com.grim3212.mc.pack.decor.entity.EntityFrame;
import com.grim3212.mc.pack.decor.entity.EntityWallpaper;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityCage;
import com.grim3212.mc.pack.decor.tile.TileEntityCalendar;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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
		// TILE ENTITYS
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCalendar.class, new TileEntityCalendarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCage.class, new TileEntityCageRenderer());

		// ENTITYS
		RenderingRegistry.registerEntityRenderingHandler(EntityFrame.class, new FrameFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityWallpaper.class, new WallpaperFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityFlatItemFrame.class, new FlatItemFrameFactory());
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
		}, DecorBlocks.decor_stairs, DecorBlocks.decor_trap_door, DecorBlocks.decor_door, DecorBlocks.corner, DecorBlocks.sloped_post, DecorBlocks.full_pyramid, DecorBlocks.slope, DecorBlocks.sloped_angle, DecorBlocks.sloped_intersection, DecorBlocks.oblique_slope, DecorBlocks.slanted_corner, DecorBlocks.pyramid, DecorBlocks.colorizer, DecorBlocks.colorizer_light, DecorBlocks.counter, DecorBlocks.table, DecorBlocks.stool, DecorBlocks.chair, DecorBlocks.wall, DecorBlocks.fence, DecorBlocks.fence_gate, DecorBlocks.lamp_post_bottom, DecorBlocks.lamp_post_middle, DecorBlocks.lamp_post_top,
				DecorBlocks.grill, DecorBlocks.chimney, DecorBlocks.stove, DecorBlocks.firepit, DecorBlocks.firering, DecorBlocks.fireplace, DecorBlocks.pillar);

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
		}, DecorBlocks.decor_stairs, DecorBlocks.decor_trap_door, DecorBlocks.decor_door, DecorBlocks.corner, DecorBlocks.sloped_post, DecorBlocks.full_pyramid, DecorBlocks.slope, DecorBlocks.sloped_angle, DecorBlocks.sloped_intersection, DecorBlocks.oblique_slope, DecorBlocks.slanted_corner, DecorBlocks.pyramid, DecorBlocks.colorizer, DecorBlocks.colorizer_light, DecorBlocks.grill, DecorBlocks.chimney, DecorBlocks.stove, DecorBlocks.firepit, DecorBlocks.firering, DecorBlocks.fireplace, DecorBlocks.counter, DecorBlocks.table, DecorBlocks.stool, DecorBlocks.chair, DecorBlocks.wall,
				DecorBlocks.fence, DecorBlocks.fence_gate, DecorBlocks.lamp_post_bottom, DecorBlocks.lamp_post_middle, DecorBlocks.lamp_post_top, DecorBlocks.pillar);

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
		}, DecorItems.lamp_item, DecorItems.decor_door_item);
	}
}
