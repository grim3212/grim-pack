package com.grim3212.mc.pack.decor.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractClientMessage;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageParticles extends AbstractClientMessage<MessageParticles> {

	private BlockPos pos;

	public MessageParticles() {
	}

	public MessageParticles(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	protected MessageParticles read(PacketBuffer buffer) throws IOException {
		return new MessageParticles(buffer.readBlockPos());
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Supplier<Context> ctx) {
		TileEntity te = player.world.getTileEntity(pos);
		if (te instanceof TileEntityColorizer) {
			for (int i = 0; i < 3; i++) {
				double xVar = (player.world.rand.nextDouble() - 0.5D) / 5.0D;
				double yVar = (player.world.rand.nextDouble() - 0.5D) / 5.0D;
				double zVar = (player.world.rand.nextDouble() - 0.5D) / 5.0D;
				player.world.addParticle(Particles.LARGE_SMOKE, pos.getX() + 0.5D + xVar, pos.getY() + 0.3D + yVar, pos.getZ() + 0.5D + zVar, 0.0D, 0.0D, 0.0D);
			}
		}
	}

}
