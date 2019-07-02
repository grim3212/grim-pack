package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSensorSetBox extends AbstractServerMessage<MessageSensorSetBox> {

	private double minX;
	private double minY;
	private double minZ;
	private double maxX;
	private double maxY;
	private double maxZ;
	private BlockPos pos;

	public MessageSensorSetBox() {
	}

	public MessageSensorSetBox(BlockPos pos, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this.pos = pos;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	@Override
	protected MessageSensorSetBox read(PacketBuffer buffer) throws IOException {
		return new MessageSensorSetBox(buffer.readBlockPos(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

	@Override
	protected void write(MessageSensorSetBox msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		buffer.writeDouble(msg.minX);
		buffer.writeDouble(msg.minY);
		buffer.writeDouble(msg.minZ);
		buffer.writeDouble(msg.maxX);
		buffer.writeDouble(msg.maxY);
		buffer.writeDouble(msg.maxZ);
	}

	@Override
	public void process(MessageSensorSetBox msg, Supplier<Context> ctx) {
		TileEntity te = ctx.get().getSender().world.getTileEntity(msg.pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).setSenseBox(new AxisAlignedBB(msg.minX, msg.minY, msg.minZ, msg.maxX, msg.maxY, msg.maxZ));
		}
	}

}
