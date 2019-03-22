package com.grim3212.mc.pack.decor.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.decor.tile.TileEntityAlarm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSaveAlarm extends AbstractServerMessage<MessageSaveAlarm> {

	private int alarmType;
	private BlockPos alarmPos;

	public MessageSaveAlarm() {
	}

	public MessageSaveAlarm(int alarmType, BlockPos alarmPos) {
		this.alarmType = alarmType;
		this.alarmPos = alarmPos;
	}

	@Override
	protected MessageSaveAlarm read(PacketBuffer buffer) throws IOException {
		return new MessageSaveAlarm(buffer.readInt(), buffer.readBlockPos());
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(alarmType);
		buffer.writeBlockPos(alarmPos);
	}

	@Override
	public void process(EntityPlayer player, Supplier<Context> ctx) {
		TileEntity te = player.world.getTileEntity(alarmPos);

		if (te instanceof TileEntityAlarm) {
			((TileEntityAlarm) te).alarmType = this.alarmType;
		}
	}

}
