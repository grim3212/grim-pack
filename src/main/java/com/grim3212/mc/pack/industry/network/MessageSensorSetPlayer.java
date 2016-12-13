package com.grim3212.mc.pack.industry.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

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
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.playerName = buffer.readString(64);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(pos);
		buffer.writeString(playerName);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.world.getTileEntity(pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).getSpecific().setPlayerSpecific(playerName);
		}
	}

}
