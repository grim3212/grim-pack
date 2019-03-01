package com.grim3212.mc.pack.industry;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.chunkloading.*;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.entity.IndustryEntities;
import com.grim3212.mc.pack.industry.init.IndustryRecipes;
import com.grim3212.mc.pack.industry.init.IndustrySounds;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.network.*;
import com.grim3212.mc.pack.industry.tile.TileEntityChunkLoader;
import com.grim3212.mc.pack.industry.world.IndustryGenerate;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class GrimIndustry extends GrimPart {

	public static GrimIndustry INSTANCE = new GrimIndustry();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.industry.client.IndustryClientProxy", serverSide = "com.grim3212.mc.pack.industry.IndustryCommonProxy")
	public static IndustryCommonProxy proxy;

	public static final String partId = "industry";
	public static final String partName = "Grim Industry";

	public GrimIndustry() {
		super(GrimIndustry.partId, GrimIndustry.partName, new IndustryConfig());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new IndustryBlocks());
		MinecraftForge.EVENT_BUS.register(new IndustryItems());
		MinecraftForge.EVENT_BUS.register(new IndustrySounds());

		if (IndustryConfig.subpartStorage)
			PacketDispatcher.registerMessage(MessageSetLock.class);

		if (IndustryConfig.subpartSensors) {
			PacketDispatcher.registerMessage(MessageSensorChangeMode.class);
			PacketDispatcher.registerMessage(MessageSensorSetEntity.class);
			PacketDispatcher.registerMessage(MessageSensorSetItem.class);
			PacketDispatcher.registerMessage(MessageSensorSetPlayer.class);
			PacketDispatcher.registerMessage(MessageSensorSetPos.class);
			PacketDispatcher.registerMessage(MessageSensorSetRender.class);
			PacketDispatcher.registerMessage(MessageSensorSetBox.class);
		}

		if (IndustryConfig.subpartFans)
			PacketDispatcher.registerMessage(MessageSaveFan.class);

		if (IndustryConfig.subpartExtruder)
			PacketDispatcher.registerMessage(MessageExtruderDirection.class);

		if (IndustryConfig.subpartChunkLoader) {
			ForgeChunkManager.setForcedChunkLoadingCallback(GrimPack.INSTANCE, new ChunkLoaderCallback());

			MinecraftForge.EVENT_BUS.register(new ChunkLoaderEvents());
		}

		GameRegistry.registerWorldGenerator(new IndustryGenerate(), 10);
		IndustryEntities.initEntities();
		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.initColors();

		IndustryRecipes.initRecipes();
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
	public void serverStarting(FMLServerStartingEvent event) {
		if(IndustryConfig.subpartChunkLoader){
			event.registerServerCommand(new ChunkLoaderCommand());
		}
	}

	@Override
	public void serverStarted(FMLServerStartedEvent event) {
	    if(IndustryConfig.subpartChunkLoader) {

            World world = DimensionManager.getWorld(DimensionType.OVERWORLD.getId());
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