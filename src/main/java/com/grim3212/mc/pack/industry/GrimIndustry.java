package com.grim3212.mc.pack.industry;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.chunkloading.ChunkLoaderCallback;
import com.grim3212.mc.pack.industry.chunkloading.ChunkLoaderCommand;
import com.grim3212.mc.pack.industry.chunkloading.ChunkLoaderData;
import com.grim3212.mc.pack.industry.chunkloading.ChunkLoaderEvents;
import com.grim3212.mc.pack.industry.chunkloading.ChunkLoaderStorage;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.init.IndustrySounds;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.network.MessageExtruderDirection;
import com.grim3212.mc.pack.industry.network.MessageSaveFan;
import com.grim3212.mc.pack.industry.network.MessageSensorChangeMode;
import com.grim3212.mc.pack.industry.network.MessageSensorSetBox;
import com.grim3212.mc.pack.industry.network.MessageSensorSetEntity;
import com.grim3212.mc.pack.industry.network.MessageSensorSetItem;
import com.grim3212.mc.pack.industry.network.MessageSensorSetPlayer;
import com.grim3212.mc.pack.industry.network.MessageSensorSetPos;
import com.grim3212.mc.pack.industry.network.MessageSensorSetRender;
import com.grim3212.mc.pack.industry.network.MessageSetLock;
import com.grim3212.mc.pack.industry.proxy.IndustryClientProxy;
import com.grim3212.mc.pack.industry.proxy.IndustryServerProxy;
import com.grim3212.mc.pack.industry.tile.TileEntityChunkLoader;

import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class GrimIndustry extends GrimPart {

	public static GrimIndustry INSTANCE = new GrimIndustry();

	public static IndustryServerProxy proxy = DistExecutor.runForDist(() -> IndustryClientProxy::new, () -> IndustryServerProxy::new);

	public static final String partId = "industry";
	public static final String partName = "Grim Industry";

	public GrimIndustry() {
		super(GrimIndustry.partId, GrimIndustry.partName, new IndustryConfig());

		FMLJavaModLoadingContext.get().getModEventBus().register(new IndustryBlocks());
		FMLJavaModLoadingContext.get().getModEventBus().register(new IndustryItems());
		FMLJavaModLoadingContext.get().getModEventBus().register(new IndustrySounds());
	}

	@Override
	public void setup(FMLCommonSetupEvent event) {
		super.setup(event);

		if (IndustryConfig.subpartStorage.get())
			PacketDispatcher.registerMessage(MessageSetLock.class);

		if (IndustryConfig.subpartSensors.get()) {
			PacketDispatcher.registerMessage(MessageSensorChangeMode.class);
			PacketDispatcher.registerMessage(MessageSensorSetEntity.class);
			PacketDispatcher.registerMessage(MessageSensorSetItem.class);
			PacketDispatcher.registerMessage(MessageSensorSetPlayer.class);
			PacketDispatcher.registerMessage(MessageSensorSetPos.class);
			PacketDispatcher.registerMessage(MessageSensorSetRender.class);
			PacketDispatcher.registerMessage(MessageSensorSetBox.class);
		}

		if (IndustryConfig.subpartFans.get())
			PacketDispatcher.registerMessage(MessageSaveFan.class);

		if (IndustryConfig.subpartExtruder.get())
			PacketDispatcher.registerMessage(MessageExtruderDirection.class);

		if (IndustryConfig.subpartChunkLoader.get()) {
			ForgeChunkManager.setForcedChunkLoadingCallback(GrimPack.INSTANCE, new ChunkLoaderCallback());

			MinecraftForge.EVENT_BUS.register(new ChunkLoaderEvents());
		}

		// GameRegistry.registerWorldGenerator(new IndustryGenerate(), 10);
		proxy.preInit();
	}

	@Override
	public void clientSetup(FMLClientSetupEvent event) {
		super.clientSetup(event);

		proxy.initColors();
	}
	
	@Override
	public void serverSetup(FMLDedicatedServerSetupEvent event) {
		super.serverSetup(event);
		
		if (IndustryConfig.subpartChunkLoader.get()) {
			event.registerServerCommand(new ChunkLoaderCommand());
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public IManualPart getManual() {
		return ManualIndustry.INSTANCE;
	}

	@Override
	public List<String> getImageUrls() {
		return Lists.newArrayList("assets/grimpack/images/industry_main.png");
	}

	@Override
	public void serverStarted(FMLServerStartedEvent event) {
		if (IndustryConfig.subpartChunkLoader.get()) {

			World world = DimensionManager.getWorld(DimensionType.field_223227_a_.getId());
			ChunkLoaderStorage storage = ChunkLoaderStorage.getStorage(world);
			List<ChunkLoaderData> chunkLoaderData = storage.getChunkLoaders();

			for (ChunkLoaderData data : chunkLoaderData) {
				world = DimensionManager.getWorld(data.dimension);
				if (world != null && !world.isRemote) {
					TileEntityChunkLoader chunkLoader = (TileEntityChunkLoader) world.getTileEntity(data.pos);
					if (chunkLoader != null && chunkLoader.getEnabled()) {
						chunkLoader.setWorld(world);
						chunkLoader.validate();
					} else {
						storage.removeChunkLoader(data);
					}
				}
			}
		}
	}
}