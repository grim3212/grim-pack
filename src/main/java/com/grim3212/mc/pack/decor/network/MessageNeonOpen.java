package com.grim3212.mc.pack.decor.network;

import java.io.IOException;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractClientMessage;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageNeonOpen extends AbstractClientMessage<MessageNeonOpen> {

	private BlockPos pos;

	public MessageNeonOpen() {
	}

	public MessageNeonOpen(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity tileentity = player.getEntityWorld().getTileEntity(pos);

		// Make sure TileEntity exists
		if (!(tileentity instanceof TileEntityNeonSign)) {
			tileentity = new TileEntityNeonSign();
			tileentity.setWorld(player.getEntityWorld());
			tileentity.setPos(this.pos);
		}

		// Open GUI
		player.openGui(GrimPack.INSTANCE, PackGuiHandler.NEON_SIGN_GUI_ID, player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ());
	}

}
