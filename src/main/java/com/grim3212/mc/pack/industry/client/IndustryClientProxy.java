package com.grim3212.mc.pack.industry.client;

import com.google.common.collect.ImmutableMap;
import com.grim3212.mc.pack.industry.IndustryCommonProxy;
import com.grim3212.mc.pack.industry.block.BlockBridge;
import com.grim3212.mc.pack.industry.block.BlockSiding;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.entity.RenderExtruder.ExtruderFactory;
import com.grim3212.mc.pack.industry.client.event.ClientEvents;
import com.grim3212.mc.pack.industry.client.particle.ParticleAir;
import com.grim3212.mc.pack.industry.client.tile.TileEntityGlassCabinetRenderer;
import com.grim3212.mc.pack.industry.client.tile.TileEntityGoldSafeRenderer;
import com.grim3212.mc.pack.industry.client.tile.TileEntityItemTowerRenderer;
import com.grim3212.mc.pack.industry.client.tile.TileEntityLockerRenderer;
import com.grim3212.mc.pack.industry.client.tile.TileEntityObsidianSafeRenderer;
import com.grim3212.mc.pack.industry.client.tile.TileEntitySpecificSensorRenderer;
import com.grim3212.mc.pack.industry.client.tile.TileEntityWarehouseCrateRenderer;
import com.grim3212.mc.pack.industry.client.tile.TileEntityWoodCabinetRenderer;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.entity.EntityExtruder;
import com.grim3212.mc.pack.industry.tile.TileEntityBridge;
import com.grim3212.mc.pack.industry.tile.TileEntityCamo;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityGlassCabinet;
import com.grim3212.mc.pack.industry.tile.TileEntityGoldSafe;
import com.grim3212.mc.pack.industry.tile.TileEntityItemTower;
import com.grim3212.mc.pack.industry.tile.TileEntityLocker;
import com.grim3212.mc.pack.industry.tile.TileEntityObsidianSafe;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;
import com.grim3212.mc.pack.industry.tile.TileEntityWarehouseCrate;
import com.grim3212.mc.pack.industry.tile.TileEntityWoodCabinet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class IndustryClientProxy extends IndustryCommonProxy {

	@Override
	public void airParticles(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, TileEntityFan fan) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleAir(world, x, y, z, xSpeed, ySpeed, zSpeed, fan));
	}

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new IndustryModelHandler());
		MinecraftForge.EVENT_BUS.register(new ClientEvents());

		if (IndustryConfig.subpartStorage) {
			// TileEntities
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpecificSensor.class, new TileEntitySpecificSensorRenderer());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWarehouseCrate.class, new TileEntityWarehouseCrateRenderer());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGlassCabinet.class, new TileEntityGlassCabinetRenderer());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodCabinet.class, new TileEntityWoodCabinetRenderer());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityObsidianSafe.class, new TileEntityObsidianSafeRenderer());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGoldSafe.class, new TileEntityGoldSafeRenderer());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLocker.class, new TileEntityLockerRenderer());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemTower.class, new TileEntityItemTowerRenderer());
		}

		// TODO: Look into animations again now that you can set rotation origin
		/*
		 * ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWarehouseCrate
		 * .class, new AnimationTESR<TileEntityWarehouseCrate>() {
		 * 
		 * /** Event Handler for events triggered from animations. Normally we
		 * just hand this off to the tileEntity for processing. This gets called
		 * mid {@link AnimationTESR#renderTileEntityFast(TileEntity, double,
		 * double, double, float, int, VertexBuffer)} before rendering occurs
		 * for the frame.
		 * 
		 * @param tileEntity our tileEntity
		 * 
		 * @param time The global world time for the current tick + partial tick
		 * progress, in seconds.
		 * 
		 * @param pastEvents List of events that were triggered since last tick.
		 *
		 * @Override public void handleEvents(TileEntityWarehouseCrate
		 * tileEntity, float time, Iterable<Event> pastEvents) {
		 * super.handleEvents(tileEntity, time, pastEvents);
		 * tileEntity.handleEvents(time, pastEvents); } });
		 */

		// Entities
		if (IndustryConfig.subpartExtruder)
			RenderingRegistry.registerEntityRenderingHandler(EntityExtruder.class, new ExtruderFactory());
	}

	@Override
	public void initColors() {
		if (IndustryConfig.subpartDecoration) {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
				public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
					if (state.getBlock() instanceof BlockSiding) {
						return state.getValue(BlockSiding.COLOR).color;
					}

					return -1;
				}
			}, IndustryBlocks.horizontal_siding, IndustryBlocks.vertical_siding);

			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
				public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
					if (pos != null) {
						TileEntity te = worldIn.getTileEntity(pos);
						if (te != null && te instanceof TileEntityCamo) {
							return Minecraft.getMinecraft().getBlockColors().colorMultiplier(worldIn.getBlockState(pos.down()), worldIn, pos, tintIndex);
						}
					}
					return 16777215;
				}
			}, IndustryBlocks.camo_plate);
		}

		if (IndustryConfig.subpartBridges) {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
				public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
					if (state != null) {
						if (pos != null) {
							TileEntity te = worldIn.getTileEntity(pos);
							if (te != null && te instanceof TileEntityBridge) {
								TileEntityBridge bridge = (TileEntityBridge) te;

								if (bridge.getBlockState() != Blocks.AIR.getDefaultState()) {
									return Minecraft.getMinecraft().getBlockColors().colorMultiplier(bridge.getBlockState(), worldIn, pos, tintIndex);
								}

								return state.getValue(BlockBridge.TYPE).getRenderColor();
							}
						}
					}
					return 16777215;
				}
			}, IndustryBlocks.bridge);
		}
	}

	/**
	 * loads the animation state machine definition file. Only needed to be
	 * loaded on the client side.
	 * 
	 * @param location
	 *            The Resource location of the definition file. Usually located
	 *            in "modid:asms/block/*.json"
	 * @param parameters
	 *            A mapping between animated variables in java and their names
	 *            in the definition file.
	 * @return The loaded state machine.
	 */
	@Override
	public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
		return ModelLoaderRegistry.loadASM(location, parameters);
	}
}
