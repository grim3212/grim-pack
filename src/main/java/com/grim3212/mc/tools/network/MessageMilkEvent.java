package com.grim3212.mc.tools.network;

import java.io.IOException;

import com.grim3212.mc.core.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageMilkEvent extends AbstractServerMessage<MessageMilkEvent> {
	
	private ItemStack stack;
	private int slot;
	
	public MessageMilkEvent() {
	}
	
	public MessageMilkEvent(ItemStack stack, int slot) {
		this.stack = stack;
		this.slot = slot;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.stack = buffer.readItemStackFromBuffer();
		this.slot = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeItemStackToBuffer(stack);
		buffer.writeInt(slot);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		player.inventory.setInventorySlotContents(player.inventory.currentItem, stack.copy());
	}

}
