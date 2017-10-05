package com.grim3212.mc.pack.tools.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageDoubleJump extends AbstractServerMessage<MessageDoubleJump> {

	private int numJump;

	public MessageDoubleJump() {
	}

	public MessageDoubleJump(int numJump) {
		this.numJump = numJump;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.numJump = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(this.numJump);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		player.jump();

		// Calculate fall distance so the double jumps do not hurt the player
		player.fallDistance = -this.numJump;
	}

}
