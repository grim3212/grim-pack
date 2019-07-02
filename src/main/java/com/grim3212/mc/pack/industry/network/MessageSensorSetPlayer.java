package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSensorSetPlayer extends AbstractServerMessage<MessageSensorSetPlayer> {

	private String playerName;
	private BlockPos pos;

	public MessageSensorSetPlayer() {
	}

	public MessageSensorSetPlayer(BlockPos pos, String playerName) {
		this.pos = pos;
		this.playerName = playerName;
	}

	@Override
	protected MessageSensorSetPlayer read(PacketBuffer buffer) throws IOException {
		return new MessageSensorSetPlayer(buffer.readBlockPos(), buffer.readString(64));
	}

	@Override
	protected void write(MessageSensorSetPlayer msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		buffer.writeString(msg.playerName);
	}

	@Override
	public void process(MessageSensorSetPlayer msg, Supplier<Context> ctx) {
		TileEntity te = ctx.get().getSender().world.getTileEntity(msg.pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).getSpecific().setPlayerSpecific(msg.playerName);
		}
	}

}
