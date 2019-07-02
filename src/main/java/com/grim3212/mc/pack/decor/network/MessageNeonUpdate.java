package com.grim3212.mc.pack.decor.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageNeonUpdate extends AbstractServerMessage<MessageNeonUpdate> {

	private BlockPos pos;
	private String[] lines;

	public MessageNeonUpdate() {
	}

	public MessageNeonUpdate(BlockPos pos, ITextComponent[] linesIn) {
		this.pos = pos;

		// We want to save formatting codes
		this.lines = new String[] { linesIn[0].getUnformattedComponentText(), linesIn[1].getUnformattedComponentText(), linesIn[2].getUnformattedComponentText(), linesIn[3].getUnformattedComponentText() };
	}

	public MessageNeonUpdate(BlockPos pos, String[] linesIn) {
		this.pos = pos;
		this.lines = linesIn;
	}

	@Override
	protected MessageNeonUpdate read(PacketBuffer buffer) throws IOException {
		BlockPos pos = buffer.readBlockPos();

		String[] lines = new String[4];
		for (int i = 0; i < 4; i++) {
			lines[i] = buffer.readString();
		}

		return new MessageNeonUpdate(pos, lines);
	}

	@Override
	protected void write(MessageNeonUpdate msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);

		for (int i = 0; i < 4; i++) {
			buffer.writeString(msg.lines[i]);
		}
	}

	@Override
	public void process(MessageNeonUpdate msg, Supplier<Context> ctx) {
		PlayerEntity player = ctx.get().getSender();
		BlockState state = player.world.getBlockState(msg.pos);
		TileEntity te = player.world.getTileEntity(msg.pos);

		if (te instanceof TileEntityNeonSign) {
			TileEntityNeonSign neonSign = (TileEntityNeonSign) te;
			if (!neonSign.getOwner().getUniqueID().equals(player.getUniqueID())) {
				GrimLog.warn(GrimDecor.partName, "Player " + player.getName().getString() + " just tried to change a neon sign they don't own");
				return;
			}

			for (int i = 0; i < msg.lines.length; ++i) {
				// Again do not strip away formatting codes
				neonSign.signText[i] = new StringTextComponent(msg.lines[i]);
			}

			neonSign.markDirty();
			player.world.notifyBlockUpdate(msg.pos, state, state, 3);
		}
	}

}
