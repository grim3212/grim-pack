package com.grim3212.mc.pack.decor.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.decor.tile.TileEntityAlarm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

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
	protected void read(PacketBuffer buffer) throws IOException {
		this.alarmType = buffer.readInt();
		this.alarmPos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(alarmType);
		buffer.writeBlockPos(alarmPos);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.world.getTileEntity(alarmPos);

		if (te instanceof TileEntityAlarm) {
			((TileEntityAlarm) te).alarmType = this.alarmType;
		}
	}

}
