package com.grim3212.mc.pack.decor.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractClientMessage;
import com.grim3212.mc.pack.decor.client.gui.GuiEditNeonSign;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageNeonOpen extends AbstractClientMessage<MessageNeonOpen> {

	private BlockPos pos;

	public MessageNeonOpen() {
	}

	public MessageNeonOpen(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	protected MessageNeonOpen read(PacketBuffer buffer) throws IOException {
		return new MessageNeonOpen(buffer.readBlockPos());
	}

	@Override
	protected void write(MessageNeonOpen msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
	}

	@Override
	public void process(MessageNeonOpen msg, Supplier<Context> ctx) {
		TileEntity tileentity = Minecraft.getInstance().player.getEntityWorld().getTileEntity(msg.pos);

		// Make sure TileEntity exists
		if (!(tileentity instanceof TileEntityNeonSign)) {
			tileentity = new TileEntityNeonSign();
			tileentity.setWorld(Minecraft.getInstance().player.getEntityWorld());
			tileentity.setPos(msg.pos);
		}

		Minecraft.getInstance().displayGuiScreen(new GuiEditNeonSign((TileEntityNeonSign) tileentity));
	}

}
