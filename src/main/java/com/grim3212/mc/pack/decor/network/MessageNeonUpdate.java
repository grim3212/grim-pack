package com.grim3212.mc.pack.decor.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;

public class MessageNeonUpdate extends AbstractServerMessage<MessageNeonUpdate> {

	private BlockPos pos;
	private String[] lines;

	public MessageNeonUpdate() {
	}

	public MessageNeonUpdate(BlockPos pos, ITextComponent[] linesIn) {
		this.pos = pos;

		// We want to save formatting codes
		this.lines = new String[] { linesIn[0].getFormattedText(), linesIn[1].getFormattedText(), linesIn[2].getFormattedText(), linesIn[3].getFormattedText() };
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.lines = new String[4];

		for (int i = 0; i < 4; ++i) {
			this.lines[i] = buffer.readString(384);
		}
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(this.pos);

		for (int i = 0; i < 4; ++i) {
			buffer.writeString(this.lines[i]);
		}
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		IBlockState state = player.world.getBlockState(pos);
		TileEntity te = player.world.getTileEntity(pos);

		if (te instanceof TileEntityNeonSign) {
			TileEntityNeonSign neonSign = (TileEntityNeonSign) te;
			if (!neonSign.getIsEditable() || neonSign.getPlayer() != player) {
				GrimLog.warn(GrimDecor.partName, "Player " + player.getName() + " just tried to change non-editable neon sign");
				return;
			}

			for (int i = 0; i < this.lines.length; ++i) {
				// Again do not strip away formatting codes
				neonSign.signText[i] = new TextComponentString(this.lines[i]);
			}

			neonSign.markDirty();
			player.world.notifyBlockUpdate(pos, state, state, 3);
		}
	}

}
