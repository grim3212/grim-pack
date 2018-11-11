package com.grim3212.mc.pack.tools.magic;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MagicPolymorph extends BaseMagic {

	public MagicPolymorph() {
		super("polymorph");
	}

	@Override
	public int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range) {
		int i = MathHelper.floor(playerIn.posX - (double) range - 1.0D);
		int j = MathHelper.floor(playerIn.posX + (double) range + 1.0D);
		int k = MathHelper.floor(playerIn.posY - (double) range - 1.0D);
		int l = MathHelper.floor(playerIn.posY + (double) range + 1.0D);
		int i1 = MathHelper.floor(playerIn.posZ - (double) range - 1.0D);
		int j1 = MathHelper.floor(playerIn.posZ + (double) range + 1.0D);

		int count = 0;

		// Construct the bounding box and grab all of the entities
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(i, k, i1, j, l, j1));

		for (int k1 = 0; k1 < list.size(); k1++) {
			Entity entity = (Entity) list.get(k1);

			if (!(entity instanceof EntityLivingBase)) {
				continue;
			}

			if (!(entity instanceof EntityMob) && !(entity instanceof EntityFlying)) {
				continue;
			}
			
			//Make sure we have enough dmgLeft
			if (count <= dmgLeft && this.convertEntity(world, playerIn, entity)) {
				count++;
			}
		}

		return count;
	}

	public boolean convertEntity(World world, EntityPlayer playerIn, Entity entity) {
		Entity passiveEntity = EntityList.createEntityByIDFromName(new ResourceLocation(getRandomPassiveMob(world)), world);

		if (passiveEntity == null) {
			return false;
		}

		double d = entity.posX;
		double d1 = entity.posY;
		double d2 = entity.posZ;

		passiveEntity.setLocationAndAngles(d, d1, d2, world.rand.nextFloat() * 360F, 0.0F);
		if (!world.isRemote)
			world.spawnEntity(passiveEntity);
		spawnExplosionParticle(world, passiveEntity);
		entity.setDead();

		for (int i = 0; i < 20; i++) {
			double d3 = world.rand.nextGaussian() * 0.02D;
			double d4 = world.rand.nextGaussian() * 0.02D;
			double d5 = world.rand.nextGaussian() * 0.02D;
			world.spawnParticle(EnumParticleTypes.HEART, passiveEntity.posX + (double) (world.rand.nextFloat() * passiveEntity.width), passiveEntity.posY + (double) (world.rand.nextFloat() * passiveEntity.height), passiveEntity.posZ + (double) (world.rand.nextFloat() * passiveEntity.width), d3, d4, d5);
		}

		return true;
	}

	public void spawnExplosionParticle(World world, Entity entity) {
		for (int i = 0; i < 20; i++) {
			double d = world.rand.nextGaussian() * 0.02D;
			double d1 = world.rand.nextGaussian() * 0.02D;
			double d2 = world.rand.nextGaussian() * 0.02D;
			double d3 = 10D;
			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (entity.posX + (double) (world.rand.nextFloat() * entity.width * 2.0F)) - (double) entity.width - d * d3, (entity.posY + (double) (world.rand.nextFloat() * entity.height)) - d1 * d3, (entity.posZ + (double) (world.rand.nextFloat() * entity.width * 2.0F)) - (double) entity.width - d2 * d3, d, d1, d2);
		}
	}

	// TODO: Make this a config option
	public String getRandomPassiveMob(World world) {
		int i = world.rand.nextInt(17);
		if (i == 0) {
			return "minecraft:rabbit";
		} else if (i == 1) {
			return "minecraft:cow";
		} else if (i == 2) {
			return "minecraft:polar_bear";
		} else if (i == 3) {
			return "minecraft:pig";
		} else if (i == 4) {
			return "minecraft:snowman";
		} else if (i == 5) {
			return "minecraft:bat";
		} else if (i == 6) {
			return "minecraft:llama";
		} else if (i == 7) {
			return "minecraft:chicken";
		} else if (i == 8) {
			return "minecraft:donkey";
		} else if (i == 9) {
			return "minecraft:parrot";
		} else if (i == 10) {
			return "minecraft:mule";
		} else if (i == 11) {
			return "minecraft:sheep";
		} else if (i == 12) {
			return "minecraft:ocelot";
		} else if (i == 13) {
			return "minecraft:wolf";
		} else if (i == 14) {
			return "minecraft:mooshroom";
		} else if (i == 15) {
			return "minecraft:horse";
		} else if (i == 16) {
			return "minecraft:squid";
		} else {
			return "minecraft:chicken";
		}
	}
}
