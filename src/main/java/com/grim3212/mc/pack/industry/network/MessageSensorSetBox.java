package com.grim3212.mc.pack.industry.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

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
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.minX = buffer.readDouble();
		this.minY = buffer.readDouble();
		this.minZ = buffer.readDouble();
		this.maxX = buffer.readDouble();
		this.maxY = buffer.readDouble();
		this.maxZ = buffer.readDouble();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(pos);
		buffer.writeDouble(minX);
		buffer.writeDouble(minY);
		buffer.writeDouble(minZ);
		buffer.writeDouble(maxX);
		buffer.writeDouble(maxY);
		buffer.writeDouble(maxZ);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.world.getTileEntity(pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).setSenseBox(new AxisAlignedBB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ));
		}
	}

}
