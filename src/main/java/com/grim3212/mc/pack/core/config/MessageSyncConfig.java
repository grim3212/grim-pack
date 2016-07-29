package com.grim3212.mc.pack.core.config;

import java.io.IOException;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractClientMessage;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.cuisine.GrimCuisine;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.config.WorldConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Used for syncing server side config to the client
 */
public class MessageSyncConfig extends AbstractClientMessage<MessageSyncConfig> {

	public MessageSyncConfig() {
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		// Decor
		DecorConfig.decorationBlocks = ByteBufUtils.readUTF8String(buffer).split(",");
		DecorConfig.numWallpapers = buffer.readInt();
		DecorConfig.useAllBlocks = buffer.readBoolean();

		// Util
		UtilConfig.doubleDoors = buffer.readBoolean();
		UtilConfig.enableFusRoDah = buffer.readBoolean();
		UtilConfig.fusrodahCooldown = buffer.readDouble();
		UtilConfig.frd_lift = buffer.readDouble();
		UtilConfig.frd_power = buffer.readDouble();
		UtilConfig.infiniteLava = buffer.readBoolean();
		UtilConfig.spawnGraves = buffer.readBoolean();

		// World
		WorldConfig.generateFlatBedRockSurface = buffer.readBoolean();
		WorldConfig.generateFlatBedRockNether = buffer.readBoolean();
		WorldConfig.generateFI = buffer.readBoolean();
		WorldConfig.replaceDesertWells = buffer.readBoolean();
		WorldConfig.corruption = buffer.readBoolean();
		WorldConfig.spawnMorePeople = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		// Decor
		StringBuilder builder = new StringBuilder();
		for (String block : DecorConfig.decorationBlocks)
			builder.append(block + ",");

		buffer.writeString(builder.toString());
		buffer.writeInt(DecorConfig.numWallpapers);
		buffer.writeBoolean(DecorConfig.useAllBlocks);

		// Util
		buffer.writeBoolean(UtilConfig.doubleDoors);
		buffer.writeBoolean(UtilConfig.enableFusRoDah);
		buffer.writeDouble(UtilConfig.fusrodahCooldown);
		buffer.writeDouble(UtilConfig.frd_lift);
		buffer.writeDouble(UtilConfig.frd_power);
		buffer.writeBoolean(UtilConfig.infiniteLava);
		buffer.writeBoolean(UtilConfig.spawnGraves);

		// World
		buffer.writeBoolean(WorldConfig.generateFlatBedRockSurface);
		buffer.writeBoolean(WorldConfig.generateFlatBedRockNether);
		buffer.writeBoolean(WorldConfig.generateFI);
		buffer.writeBoolean(WorldConfig.replaceDesertWells);
		buffer.writeBoolean(WorldConfig.corruption);
		buffer.writeBoolean(WorldConfig.spawnMorePeople);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		GrimLog.info(GrimPack.modName, "Syncing config from server...");

		if (side == Side.CLIENT) {
			GrimLog.info(GrimPack.modName, "Updating manual pages...");
			// Clear all manual pages currently registered on the client
			ManualRegistry.clearManual();

			// Load manual pages after config has been synced
			GrimCore.proxy.registerManual(GrimCore.INSTANCE.getModSection());
			GrimCuisine.proxy.registerManual(GrimCuisine.INSTANCE.getModSection());
			GrimDecor.proxy.registerManual(GrimDecor.INSTANCE.getModSection());
			GrimIndustry.proxy.registerManual(GrimIndustry.INSTANCE.getModSection());
			GrimTools.proxy.registerManual(GrimTools.INSTANCE.getModSection());
			GrimUtil.proxy.registerManual(GrimUtil.INSTANCE.getModSection());
			GrimWorld.proxy.registerManual(GrimWorld.INSTANCE.getModSection());
		}
	}

}
