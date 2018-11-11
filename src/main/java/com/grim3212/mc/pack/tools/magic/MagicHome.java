package com.grim3212.mc.pack.tools.magic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicHome extends BaseMagic {

	public MagicHome() {
		super("home");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		float x = world.getSpawnPoint().getX();
		float y = world.getSpawnPoint().getY();
		float z = world.getSpawnPoint().getZ();
		if (!world.isRemote) {
			if (playerIn instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) playerIn;
				BlockPos spawnPoint = player.getBedLocation(player.world.provider.getDimension());
				boolean flag = playerIn.isSpawnForced(world.provider.getDimension());

				if (spawnPoint != null) {

					BlockPos blockpos1 = EntityPlayer.getBedSpawnLocation(world, spawnPoint, flag);

					if (blockpos1 != null) {
						player.setSpawnPoint(spawnPoint, flag);

						if (player.timeUntilPortal == 0) {
							player.connection.setPlayerLocation(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ(), player.cameraYaw, player.cameraPitch);

							while (!world.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty() && player.posY < 256.0D) {
								player.connection.setPlayerLocation(player.posX, player.posY + 1.0D, player.posZ, player.cameraYaw, player.cameraPitch);
							}

							player.timeUntilPortal = 3;
						}
					} else {
						player.connection.sendPacket(new SPacketChangeGameState(0, 0.0F));

						BlockPos pos = world.getSpawnPoint();

						if (player.timeUntilPortal == 0) {
							player.connection.setPlayerLocation(pos.getX(), pos.getY(), pos.getZ(), player.cameraYaw, player.cameraPitch);

							while (!world.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty() && player.posY < 256.0D) {
								player.connection.setPlayerLocation(player.posX, player.posY + 1.0D, player.posZ, player.cameraYaw, player.cameraPitch);
							}

							player.timeUntilPortal = 3;
						}
					}
				} else {
					if (player.timeUntilPortal == 0) {
						player.connection.setPlayerLocation(x, y, z, player.cameraYaw, player.cameraPitch);

						while (!world.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty() && player.posY < 256.0D) {
							player.connection.setPlayerLocation(player.posX, player.posY + 1.0D, player.posZ, player.cameraYaw, player.cameraPitch);
						}

						player.timeUntilPortal = 3;
					}
				}
			} else {
				playerIn.setPosition(x, y, z);

				while (!world.getCollisionBoxes(playerIn, playerIn.getEntityBoundingBox()).isEmpty() && playerIn.posY < 256.0D) {
					playerIn.setPosition(playerIn.posX, playerIn.posY + 1.0D, playerIn.posZ);
				}
			}
		}

		playerIn.world.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.AMBIENT, 1, 1);

		for (int i = 0; i < 20; i++) {
			double d3 = world.rand.nextGaussian() * 0.02D;
			double d4 = world.rand.nextGaussian() * 0.02D;
			double d5 = world.rand.nextGaussian() * 0.02D;
			world.spawnParticle(EnumParticleTypes.PORTAL, playerIn.posX + (double) (world.rand.nextFloat() * playerIn.width), playerIn.posY + (double) (world.rand.nextFloat() * playerIn.height), playerIn.posZ + (double) (world.rand.nextFloat() * playerIn.width), d3, d4, d5);
		}

		return 1;
	}

}
