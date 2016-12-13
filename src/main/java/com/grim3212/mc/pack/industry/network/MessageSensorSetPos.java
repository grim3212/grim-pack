package com.grim3212.mc.pack.industry.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSensorSetPos extends AbstractServerMessage<MessageSensorSetPos> {

	private BlockPos sensePos;
	private BlockPos pos;

	public MessageSensorSetPos() {
	}

	public MessageSensorSetPos(BlockPos pos, BlockPos sensePos) {
		this.pos = pos;
		this.sensePos = sensePos;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.sensePos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(pos);
		buffer.writeBlockPos(sensePos);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.world.getTileEntity(pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).setSensorPos(sensePos);
		}
	}

}
