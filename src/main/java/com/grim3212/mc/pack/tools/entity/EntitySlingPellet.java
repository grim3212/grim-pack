package com.grim3212.mc.pack.tools.entity;

import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.util.EntityDataSerializers;
import com.grim3212.mc.pack.tools.util.EnumPelletType;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySlingPellet extends EntityThrowable {

	private static final DataParameter<EnumPelletType> TYPE = EntityDataManager.createKey(EntitySlingPellet.class, EntityDataSerializers.PELLET_TYPE);

	public EntitySlingPellet(World world) {
		super(world);
	}

	public EntitySlingPellet(World world, EntityLivingBase entity, EnumPelletType type) {
		super(world, entity);
		this.getDataManager().set(TYPE, type);
	}

	public EntitySlingPellet(World world, double par2, double par4, double par6, EnumPelletType type) {
		super(world, par2, par4, par6);
		this.getDataManager().set(TYPE, type);
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(TYPE, EnumPelletType.STONE);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit != null) {
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.getType().getDamage());
		}

		switch (this.getType()) {
		case EXPLOSION:
			if (!world.isRemote) {
				Utils.createExplosion(world, null, posX, posY, posZ, 3F, true, false, true);
			}
			break;
		case FIRE:
			for (int fire = 0; fire < 6; ++fire) {
				BlockPos blockPos = this.getPosition().add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);

				if (world.getBlockState(blockPos).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(world, blockPos)) {
					world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
				}
			}
			break;
		case LIGHT:
			if (result.entityHit == null) {
				BlockPos blockpos = result.getBlockPos();
				IBlockState state = this.world.getBlockState(blockpos);

				if (result.sideHit == EnumFacing.UP) {
					if (!world.isRemote && state.getBlock() != Blocks.AIR && this.getEntityWorld().getBlockState(result.getBlockPos().up(2)).getBlock() == Blocks.AIR) {
						world.setBlockState(result.getBlockPos().up(), Blocks.TORCH.getDefaultState());
					}
				} else {
					if (!world.isRemote && state.getBlock() != Blocks.AIR && this.getEntityWorld().getBlockState(result.getBlockPos().offset(result.sideHit, 2)).getBlock() == Blocks.AIR) {
						world.setBlockState(result.getBlockPos().offset(result.sideHit), Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, result.sideHit));
					}
				}
			}
			break;
		default:
			// NO-OP
			break;
		}

		if (!this.world.isRemote) {
			this.world.setEntityState(this, (byte) 3);
			this.setDead();
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

	public EnumPelletType getType() {
		return this.getDataManager().get(TYPE);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setByte("Type", (byte) this.getType().ordinal());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.getDataManager().set(TYPE, EnumPelletType.values[compound.getByte("Type")]);
	}
}
