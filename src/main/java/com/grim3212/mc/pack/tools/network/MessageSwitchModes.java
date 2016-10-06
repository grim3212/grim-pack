package com.grim3212.mc.pack.tools.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.tools.items.ItemPowerStaff;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSwitchModes extends AbstractServerMessage<MessageSwitchModes> {

	private EnumHand hand;

	public MessageSwitchModes() {
	}

	public MessageSwitchModes(EnumHand hand) {
		this.hand = hand;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.hand = buffer.readByte() == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeByte(hand.ordinal());
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() instanceof ItemPowerStaff) {
			if (player.getHeldItem(hand).getItemDamage() == 0) {
				player.getHeldItem(hand).setItemDamage(1);
			} else {
				player.getHeldItem(hand).setItemDamage(0);
			}
		}
	}
}