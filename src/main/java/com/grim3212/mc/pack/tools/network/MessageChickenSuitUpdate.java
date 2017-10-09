package com.grim3212.mc.pack.tools.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageChickenSuitUpdate extends AbstractServerMessage<MessageChickenSuitUpdate> {

	private int glideJumps;

	public MessageChickenSuitUpdate() {
		this.glideJumps = -1;

	}

	public MessageChickenSuitUpdate(int glideJumps) {
		this.glideJumps = glideJumps;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.glideJumps = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(this.glideJumps);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (this.glideJumps != -1) {
			double d = -0.14999999999999999D - 0.14999999999999999D * (1.0D - (double) glideJumps / 5D);
			if (player.motionY < d) {
				player.motionY = d;
			}
			player.fallDistance = 0.0F;
		} else {
			player.jump();

			// Calculate fall distance so the double jumps do not hurt the
			// player
			player.fallDistance = 0.0f;
		}
	}

}
