package com.grim3212.mc.pack.industry.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSensorSetRender extends AbstractServerMessage<MessageSensorSetRender> {

	private boolean shouldRender;
	private BlockPos pos;

	public MessageSensorSetRender() {
	}

	public MessageSensorSetRender(BlockPos pos, boolean shouldRender) {
		this.pos = pos;
		this.shouldRender = shouldRender;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.shouldRender = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(pos);
		buffer.writeBoolean(shouldRender);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.world.getTileEntity(pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).setRenderSensorPos(shouldRender);
		}
	}

}
