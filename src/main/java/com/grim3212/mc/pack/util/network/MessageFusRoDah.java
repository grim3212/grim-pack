package com.grim3212.mc.pack.util.network;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.event.UtilAchievements;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageFusRoDah extends AbstractServerMessage<MessageFusRoDah> {

	public MessageFusRoDah() {
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		float var5 = (float) (2.0D * UtilConfig.frd_power);
		List<Entity> list = player.world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB((double) ((float) player.posX - var5), player.posY - (double) var5, (double) ((float) player.posZ - var5), (double) ((float) player.posX + var5), player.posY + (double) var5, (double) ((float) player.posZ + var5)));
		float var7 = player.rotationYaw * 0.01745329F;
		double xPower = (double) (-MathHelper.sin(var7)) * UtilConfig.frd_power;
		double zPower = (double) MathHelper.cos(var7) * UtilConfig.frd_power;
		Iterator<Entity> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object obj = iterator.next();
			Entity entity = (Entity) obj;
			if (!(entity instanceof EntityLivingBase)) {
				continue;
			}
			if (!player.isDead) {
				entity.addVelocity(xPower, UtilConfig.frd_lift, zPower);
			}
		}

		// Trigger Fus Ro Dah achivement
		player.addStat(UtilAchievements.FUS_RO_DAH);

		if (UtilConfig.soundEnabled) {
			if (UtilConfig.useOldSound)
				player.world.playSound((EntityPlayer) null, player.getPosition(), GrimUtil.fusrodahOldSound, SoundCategory.PLAYERS, 1.0F, 1.0F);
			else
				player.world.playSound((EntityPlayer) null, player.getPosition(), GrimUtil.fusrodahSound, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
	}

}
