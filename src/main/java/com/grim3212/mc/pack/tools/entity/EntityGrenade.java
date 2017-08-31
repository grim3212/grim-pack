package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.entity.EntityProjectile;
import com.grim3212.mc.pack.tools.config.ToolsConfig;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGrenade extends EntityProjectile {

	public EntityGrenade(World worldIn) {
		super(worldIn);
	}

	public EntityGrenade(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityGrenade(World world, EntityLivingBase shooter) {
		super(world, shooter);
	}

	@Override
	protected ItemStack getPickupStack() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void projectileHit(EntityLivingBase living) {
		super.projectileHit(living);
		this.explode();
	}

	@Override
	protected void projectileLand(RayTraceResult raytraceResultIn, BlockPos pos, IBlockState state) {
		super.projectileLand(raytraceResultIn, pos, state);
		this.explode();
	}

	private void explode() {
		if (!world.isRemote) {
			this.world.createExplosion(this, this.posX, this.posY, this.posZ, ToolsConfig.grenadeLauncherExplosion, true);
			this.setDead();
		}
	}
}
