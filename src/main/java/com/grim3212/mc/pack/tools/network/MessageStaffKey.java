package com.grim3212.mc.pack.tools.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.items.ItemStaff;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStaffKey extends AbstractServerMessage<MessageStaffKey> {

	private EnumHand hand;
	private int keys;

	public MessageStaffKey() {
	}

	public MessageStaffKey(EnumHand hand, int keys) {
		this.hand = hand;
		this.keys = keys;
	}

	@Override
	public void read(PacketBuffer buffer) throws IOException {
		this.hand = buffer.readByte() == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
		this.keys = buffer.readInt();
	}

	@Override
	public void write(PacketBuffer buffer) throws IOException {
		buffer.writeByte(hand.ordinal());
		buffer.writeInt(this.keys);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (player.getHeldItem(hand) != null) {
			if (player.getHeldItem(hand).getItem() instanceof ItemStaff) {
				NBTHelper.setInteger(player.getHeldItem(hand), "Keys", keys);
			}
		}
	}

}
