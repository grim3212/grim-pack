package com.grim3212.mc.pack.industry.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFan.FanMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSaveFan extends AbstractServerMessage<MessageSaveFan> {

	private int range;
	private FanMode mode;
	private BlockPos pos;

	public MessageSaveFan() {
	}

	public MessageSaveFan(int range, FanMode mode, BlockPos pos) {
		this.range = range;
		this.mode = mode;
		this.pos = pos;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.mode = buffer.readEnumValue(FanMode.class);
		this.range = buffer.readInt();
		this.pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeEnumValue(mode);
		buffer.writeInt(range);
		buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.worldObj.getTileEntity(pos);

		if (te instanceof TileEntityFan) {
			if (((TileEntityFan) te).getMode() != FanMode.OFF)
				((TileEntityFan) te).setMode(mode);
			((TileEntityFan) te).setOldMode(mode);
			((TileEntityFan) te).setRange(range);
			player.worldObj.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

}
