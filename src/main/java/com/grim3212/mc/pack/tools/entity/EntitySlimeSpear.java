package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.tools.util.EnumSpearType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySlimeSpear extends EntitySpear {

	private int bounceCount;

	public EntitySlimeSpear(World worldIn) {
		super(worldIn);
	}

	public EntitySlimeSpear(World world, double x, double y, double z) {
		super(world, x, y, z, EnumSpearType.SLIME);
	}

	public EntitySlimeSpear(World world, EntityLivingBase shooter) {
		super(world, shooter, EnumSpearType.SLIME);
	}

	@Override
	protected void arrowLand(RayTraceResult raytraceResultIn, BlockPos pos, IBlockState state) {
		if (bounceCount == 5) {
			this.inGround = true;
		} else {
			bounceCount++;
		}
		motionY *= -1D;
		worldObj.playSound((EntityPlayer) null, pos, SoundEvents.ENTITY_SMALL_SLIME_SQUISH, SoundCategory.PLAYERS, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
		float mod = 0.25F;
		worldObj.spawnParticle(EnumParticleTypes.SLIME, posX - motionX * (double) mod, posY - motionY * (double) mod, posZ - motionZ * (double) mod, motionX, motionY, motionZ);
		this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}
}
