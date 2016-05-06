package com.grim3212.mc.tools.network;

import java.io.IOException;

import com.grim3212.mc.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.core.util.NBTHelper;
import com.grim3212.mc.tools.items.ItemWand;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageWandKeys extends AbstractServerMessage<MessageWandKeys> {

	private int keys;

	public MessageWandKeys() {
	}

	public MessageWandKeys(int keys) {
		this.keys = keys;
	}

	@Override
	public void read(PacketBuffer buffer) throws IOException {
		this.keys = buffer.readInt();
	}

	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(this.keys);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (player.getCurrentEquippedItem() != null) {
			if (player.getCurrentEquippedItem().getItem() instanceof ItemWand) {
				NBTHelper.setInteger(player.getCurrentEquippedItem(), "keys", keys);
			}
		}
	}
}
