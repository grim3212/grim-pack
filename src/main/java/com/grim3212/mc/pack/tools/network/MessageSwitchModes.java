package com.grim3212.mc.pack.tools.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.tools.items.ItemPowerStaff;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSwitchModes extends AbstractServerMessage<MessageSwitchModes> {

	public MessageSwitchModes() {
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (player.getHeldItem().getItem() != null && player.getHeldItem().getItem() instanceof ItemPowerStaff) {
			if (player.getHeldItem().getItemDamage() == 0) {
				player.getHeldItem().setItemDamage(1);
			} else {
				player.getHeldItem().setItemDamage(0);
			}
		}
	}
}