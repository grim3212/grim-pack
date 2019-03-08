package com.grim3212.mc.pack.core.network;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractClientMessage;
import com.grim3212.mc.pack.core.util.BetterExplosion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageBetterExplosion extends AbstractClientMessage<MessageBetterExplosion> {

	private double x;
	private double y;
	private double z;
	private double motionX;
	private double motionY;
	private double motionZ;
	private float size;
	private boolean destroyBlocks;
	private List<BlockPos> affectedBlockPositions;

	public MessageBetterExplosion() {
	}

	public MessageBetterExplosion(double x, double y, double z, float size, boolean destroyBlocks, List<BlockPos> affectedBlockPositionsIn, Vec3d motion) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = size;
		this.affectedBlockPositions = Lists.newArrayList(affectedBlockPositionsIn);

		if (motion != null) {
			this.motionX = (float) motion.x;
			this.motionY = (float) motion.y;
			this.motionZ = (float) motion.z;
		}
	}

	@Override
	protected MessageBetterExplosion read(PacketBuffer buffer) throws IOException {
		double x = buffer.readDouble();
		double y = buffer.readDouble();
		double z = buffer.readDouble();

		double motionX = buffer.readDouble();
		double motionY = buffer.readDouble();
		double motionZ = buffer.readDouble();

		float size = buffer.readFloat();

		boolean destroyBlocks = buffer.readBoolean();

		int affectedBlocks = buffer.readInt();
		List<BlockPos> affectedBlockPositions = Lists.<BlockPos>newArrayListWithCapacity(affectedBlocks);

		for (int i1 = 0; i1 < affectedBlocks; ++i1) {
			int j1 = buffer.readByte() + (int) x;
			int k1 = buffer.readByte() + (int) y;
			int l1 = buffer.readByte() + (int) z;
			affectedBlockPositions.add(new BlockPos(j1, k1, l1));
		}
		return new MessageBetterExplosion(x, y, z, size, destroyBlocks, affectedBlockPositions, new Vec3d(motionX, motionY, motionZ));
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeDouble(this.x);
		buffer.writeDouble(this.y);
		buffer.writeDouble(this.z);
		buffer.writeDouble(this.motionX);
		buffer.writeDouble(this.motionY);
		buffer.writeDouble(this.motionZ);

		buffer.writeFloat(this.size);
		buffer.writeBoolean(this.destroyBlocks);

		buffer.writeInt(this.affectedBlockPositions.size());
		for (BlockPos blockpos : this.affectedBlockPositions) {
			int l = blockpos.getX() - (int) x;
			int i1 = blockpos.getY() - (int) y;
			int j1 = blockpos.getZ() - (int) z;
			buffer.writeByte(l);
			buffer.writeByte(i1);
			buffer.writeByte(j1);
		}
	}

	@Override
	public void process(EntityPlayer player, Supplier<Context> ctx) {
		BetterExplosion explosion = new BetterExplosion(player.world, (Entity) null, x, y, z, size, destroyBlocks, affectedBlockPositions);
		explosion.doExplosionB(true);

		player.motionX += this.motionX;
		player.motionY += this.motionY;
		player.motionZ += this.motionZ;
	}

}
