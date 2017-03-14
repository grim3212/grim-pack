package com.grim3212.mc.pack.core.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.cuisine.GrimCuisine;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.world.GrimWorld;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageManualAchievement extends AbstractServerMessage<MessageManualAchievement> {

	private String partId;

	public MessageManualAchievement() {
	}

	public MessageManualAchievement(String partId) {
		this.partId = partId;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.partId = buffer.readString(30);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeString(this.partId);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		// TODO: Possibly make this more abstract?
		switch (this.partId) {
		case GrimCuisine.partId:
			player.addStat(GrimCuisine.CUISINE_START);
			break;
		case GrimDecor.partId:
			player.addStat(GrimDecor.DECOR_START);
			break;
		case GrimIndustry.partId:
			player.addStat(GrimIndustry.INDUSTRY_START);
			break;
		case GrimTools.partId:
			player.addStat(GrimTools.TOOLS_START);
			break;
		case GrimUtil.partId:
			player.addStat(GrimUtil.UTIL_START);
			break;
		case GrimWorld.partId:
			player.addStat(GrimWorld.WORLD_START);
			break;
		}
	}

}
