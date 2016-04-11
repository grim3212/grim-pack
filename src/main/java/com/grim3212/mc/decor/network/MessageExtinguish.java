package com.grim3212.mc.decor.network;

import java.io.IOException;

import com.grim3212.mc.core.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;

public class MessageExtinguish extends AbstractClientMessage<MessageExtinguish> {

	private BlockPos pos;

	public MessageExtinguish() {
	}

	public MessageExtinguish(BlockPos pos) {
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
		for (int i = 0; i < 3; i++) {
			double xVar = (player.worldObj.rand.nextDouble() - 0.5D) / 5.0D;
			double yVar = (player.worldObj.rand.nextDouble() - 0.5D) / 5.0D;
			double zVar = (player.worldObj.rand.nextDouble() - 0.5D) / 5.0D;
			player.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5D + xVar, pos.getY() + 0.3D + yVar, pos.getZ() + 0.5D + zVar, 0.0D, 0.0D, 0.0D);
		}
	}

}
