package com.grim3212.mc.pack.core.network;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractClientMessage;
import com.grim3212.mc.pack.core.util.BetterExplosion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion.Mode;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageBetterExplosion extends AbstractClientMessage<MessageBetterExplosion> {

	private double x;
	private double y;
	private double z;
	private double motionX;
	private double motionY;
	private double motionZ;
	private float size;
	private Mode destroyBlocks;
	private List<BlockPos> affectedBlockPositions;

	public MessageBetterExplosion() {
	}

	public MessageBetterExplosion(double x, double y, double z, float size, Mode destroyBlocks, List<BlockPos> affectedBlockPositionsIn, Vec3d motion) {
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

		Mode destroyBlocks = buffer.readEnumValue(Mode.class);

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
	protected void write(MessageBetterExplosion msg, PacketBuffer buffer) throws IOException {
		buffer.writeDouble(msg.x);
		buffer.writeDouble(msg.y);
		buffer.writeDouble(msg.z);
		buffer.writeDouble(msg.motionX);
		buffer.writeDouble(msg.motionY);
		buffer.writeDouble(msg.motionZ);

		buffer.writeFloat(msg.size);
		buffer.writeEnumValue(msg.destroyBlocks);

		buffer.writeInt(msg.affectedBlockPositions.size());
		for (BlockPos blockpos : msg.affectedBlockPositions) {
			int l = blockpos.getX() - (int) msg.x;
			int i1 = blockpos.getY() - (int) msg.y;
			int j1 = blockpos.getZ() - (int) msg.z;
			buffer.writeByte(l);
			buffer.writeByte(i1);
			buffer.writeByte(j1);
		}
	}

	@Override
	public void process(MessageBetterExplosion msg, Supplier<Context> ctx) {
		PlayerEntity player = ctx.get().getSender();
		BetterExplosion explosion = new BetterExplosion(player.world, (Entity) null, msg.x, msg.y, msg.z, msg.size, msg.destroyBlocks, msg.affectedBlockPositions);
		explosion.doExplosionB(true);

		player.setMotion(player.getMotion().add(msg.motionX, msg.motionY, msg.motionZ));
	}

}
