package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.tools.util.EnumPelletType;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySlingpellet extends EntityThrowable {

	private EnumPelletType type;

	public EntitySlingpellet(World world, EnumPelletType type) {
		super(world);
		this.type = type;
	}

	public EntitySlingpellet(World world, EntityLivingBase entity, EnumPelletType type) {
		super(world, entity);
		this.type = type;
	}

	public EntitySlingpellet(World world, double par2, double par4, double par6, EnumPelletType type) {
		super(world, par2, par4, par6);
		this.type = type;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit != null) {
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.type.getDamage());
		}

		switch (this.type) {
		case EXPLOSION:
			if (!worldObj.isRemote)
				worldObj.createExplosion(null, posX, posY, posZ, 3F, true);
			break;
		case FIRE:
			for (int fire = 0; fire < 6; ++fire) {
				BlockPos blockPos = this.getPosition().add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);

				if (worldObj.getBlockState(blockPos).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(worldObj, blockPos)) {
					worldObj.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
				}
			}
			break;
		case LIGHT:
			if (result.entityHit == null) {
				BlockPos blockpos = result.getBlockPos();
				IBlockState state = this.worldObj.getBlockState(blockpos);

				if (result.sideHit == EnumFacing.UP) {
					if (!worldObj.isRemote && state.getBlock() != Blocks.AIR && this.getEntityWorld().getBlockState(result.getBlockPos().up(2)).getBlock() == Blocks.AIR) {
						worldObj.setBlockState(result.getBlockPos().up(), Blocks.TORCH.getDefaultState());
					}
				} else {
					if (!worldObj.isRemote && state.getBlock() != Blocks.AIR && this.getEntityWorld().getBlockState(result.getBlockPos().offset(result.sideHit, 2)).getBlock() == Blocks.AIR) {
						worldObj.setBlockState(result.getBlockPos().offset(result.sideHit), Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, result.sideHit));
					}
				}
			}
			break;
		default:
			// NO-OP
			break;
		}

		for (int i = 0; i < 8; ++i) {
			this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}
}
