package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSensorSetItem extends AbstractServerMessage<MessageSensorSetItem> {

	private ItemStack stack;
	private BlockPos pos;

	public MessageSensorSetItem() {
	}

	public MessageSensorSetItem(BlockPos pos, ItemStack stack) {
		this.pos = pos;
		this.stack = stack;
	}

	@Override
	protected MessageSensorSetItem read(PacketBuffer buffer) throws IOException {
		return new MessageSensorSetItem(buffer.readBlockPos(), buffer.readItemStack());
	}

	@Override
	protected void write(MessageSensorSetItem msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		buffer.writeItemStack(msg.stack);
	}

	@Override
	public void process(MessageSensorSetItem msg, Supplier<Context> ctx) {
		TileEntity te = ctx.get().getSender().world.getTileEntity(msg.pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).getSpecific().setItemStackSpecific(msg.stack);
		}
	}
}
