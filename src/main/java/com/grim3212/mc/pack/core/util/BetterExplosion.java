package com.grim3212.mc.pack.core.util;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BetterExplosion extends Explosion {

	/** whether or not the explosion sets fire to blocks around it */
	private final boolean isFlaming;
	private final Random explosionRNG;
	private final World worldObj;
	private final double explosionX;
	private final double explosionY;
	private final double explosionZ;
	private final Entity exploder;
	private final float explosionSize;
	private final List<BlockPos> affectedBlockPositions;
	private final Map<PlayerEntity, Vec3d> playerKnockbackMap;
	private final Vec3d position;
	private final Explosion.Mode destroyBlocks;
	private final boolean hurtEntities;

	@OnlyIn(Dist.CLIENT)
	public BetterExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, Explosion.Mode destroyBlocks, List<BlockPos> affectedPositions) {
		this(worldIn, entityIn, x, y, z, size, false, destroyBlocks, false, affectedPositions);
	}

	@OnlyIn(Dist.CLIENT)
	public BetterExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, Explosion.Mode destroyBlocks, boolean hurtEntities, List<BlockPos> affectedPositions) {
		this(worldIn, entityIn, x, y, z, size, flaming, destroyBlocks, hurtEntities);
		this.affectedBlockPositions.addAll(affectedPositions);
	}

	public BetterExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, Explosion.Mode destroyBlocks, boolean hurtEntities) {
		super(worldIn, entityIn, x, y, z, size, flaming, destroyBlocks);
		this.explosionRNG = new Random();
		this.affectedBlockPositions = Lists.<BlockPos>newArrayList();
		this.playerKnockbackMap = Maps.<PlayerEntity, Vec3d>newHashMap();
		this.worldObj = worldIn;
		this.exploder = entityIn;
		this.explosionSize = size;
		this.explosionX = x;
		this.explosionY = y;
		this.explosionZ = z;
		this.isFlaming = flaming;
		this.position = new Vec3d(explosionX, explosionY, explosionZ);
		this.destroyBlocks = destroyBlocks;
		this.hurtEntities = hurtEntities;
	}

	@Override
	public void doExplosionA() {
		Set<BlockPos> set = Sets.<BlockPos>newHashSet();

		for (int j = 0; j < 16; ++j) {
			for (int k = 0; k < 16; ++k) {
				for (int l = 0; l < 16; ++l) {
					if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
						double d0 = (double) ((float) j / 15.0F * 2.0F - 1.0F);
						double d1 = (double) ((float) k / 15.0F * 2.0F - 1.0F);
						double d2 = (double) ((float) l / 15.0F * 2.0F - 1.0F);
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 = d0 / d3;
						d1 = d1 / d3;
						d2 = d2 / d3;
						float f = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
						double d4 = this.explosionX;
						double d6 = this.explosionY;
						double d8 = this.explosionZ;

						for (; f > 0.0F; f -= 0.22500001F) {
							BlockPos blockpos = new BlockPos(d4, d6, d8);
							BlockState iblockstate = this.worldObj.getBlockState(blockpos);
							IFluidState ifluidstate = this.worldObj.getFluidState(blockpos);
							if (!iblockstate.isAir(worldObj, blockpos) || !ifluidstate.isEmpty()) {
								float f2 = Math.max(iblockstate.getExplosionResistance(worldObj, blockpos, exploder, this), ifluidstate.getExplosionResistance(worldObj, blockpos, exploder, this));
								if (this.exploder != null) {
									f2 = this.exploder.getExplosionResistance(this, this.worldObj, blockpos, iblockstate, ifluidstate, f2);
								}

								f -= (f2 + 0.3F) * 0.3F;
							}

							if (f > 0.0F && (this.exploder == null || this.exploder.canExplosionDestroyBlock(this, this.worldObj, blockpos, iblockstate, f))) {
								set.add(blockpos);
							}

							d4 += d0 * 0.30000001192092896D;
							d6 += d1 * 0.30000001192092896D;
							d8 += d2 * 0.30000001192092896D;
						}
					}
				}
			}
		}

		this.affectedBlockPositions.addAll(set);

		if (hurtEntities) {
			float f3 = this.explosionSize * 2.0F;
			int k1 = MathHelper.floor(this.explosionX - (double) f3 - 1.0D);
			int l1 = MathHelper.floor(this.explosionX + (double) f3 + 1.0D);
			int i2 = MathHelper.floor(this.explosionY - (double) f3 - 1.0D);
			int i1 = MathHelper.floor(this.explosionY + (double) f3 + 1.0D);
			int j2 = MathHelper.floor(this.explosionZ - (double) f3 - 1.0D);
			int j1 = MathHelper.floor(this.explosionZ + (double) f3 + 1.0D);
			List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
			net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.worldObj, this, list, f3);
			Vec3d vec3d = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);

			for (int k2 = 0; k2 < list.size(); ++k2) {
				Entity entity = (Entity) list.get(k2);

				if (!entity.isImmuneToExplosions()) {
					double d12 = (double) (MathHelper.sqrt(entity.getDistanceSq(new Vec3d(this.explosionX, this.explosionY, this.explosionZ))) / f3);

					if (d12 <= 1.0D) {
						double d5 = entity.posX - this.explosionX;
						double d7 = entity.posY + (double) entity.getEyeHeight() - this.explosionY;
						double d9 = entity.posZ - this.explosionZ;
						double d13 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

						if (d13 != 0.0D) {
							d5 = d5 / d13;
							d7 = d7 / d13;
							d9 = d9 / d13;
							double d14 = (double) func_222259_a(vec3d, entity);
							double d10 = (1.0D - d12) * d14;
							entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f3 + 1.0D)));
							double d11 = 1.0D;

							if (entity instanceof LivingEntity) {
								d11 = ProtectionEnchantment.getBlastDamageReduction((LivingEntity) entity, d10);
							}

							entity.setMotion(entity.getMotion().add(d5 * d11, d7 * d11, d9 * d11));

							if (entity instanceof PlayerEntity) {
								PlayerEntity entityplayer = (PlayerEntity) entity;

								if (!entityplayer.isSpectator() && (!entityplayer.isCreative() || !entityplayer.abilities.isFlying)) {
									this.playerKnockbackMap.put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void doExplosionB(boolean spawnParticles) {
		this.worldObj.playSound((PlayerEntity) null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		boolean flag = this.destroyBlocks != Explosion.Mode.NONE;
		if (this.explosionSize >= 2.0F && flag) {
			this.worldObj.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
		} else {
			this.worldObj.addParticle(ParticleTypes.EXPLOSION, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
		}

		if (flag) {
			for (BlockPos blockpos : this.affectedBlockPositions) {
				BlockState iblockstate = this.worldObj.getBlockState(blockpos);
				Block block = iblockstate.getBlock();

				if (spawnParticles) {
					double d0 = (double) ((float) blockpos.getX() + this.worldObj.rand.nextFloat());
					double d1 = (double) ((float) blockpos.getY() + this.worldObj.rand.nextFloat());
					double d2 = (double) ((float) blockpos.getZ() + this.worldObj.rand.nextFloat());
					double d3 = d0 - this.explosionX;
					double d4 = d1 - this.explosionY;
					double d5 = d2 - this.explosionZ;
					double d6 = (double) MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
					d3 = d3 / d6;
					d4 = d4 / d6;
					d5 = d5 / d6;
					double d7 = 0.5D / (d6 / (double) this.explosionSize + 0.1D);
					d7 = d7 * (double) (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
					d3 = d3 * d7;
					d4 = d4 * d7;
					d5 = d5 * d7;
					this.worldObj.addParticle(ParticleTypes.POOF, (d0 + this.explosionX) / 2.0D, (d1 + this.explosionY) / 2.0D, (d2 + this.explosionZ) / 2.0D, d3, d4, d5);
					this.worldObj.addParticle(ParticleTypes.SMOKE, d0, d1, d2, d3, d4, d5);
				}

				if (!iblockstate.isAir(this.worldObj, blockpos)) {
					if (this.worldObj instanceof ServerWorld && iblockstate.canDropFromExplosion(this.worldObj, blockpos, this)) {
						TileEntity tileentity = iblockstate.hasTileEntity() ? this.worldObj.getTileEntity(blockpos) : null;
						LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) this.worldObj)).withRandom(this.worldObj.rand).withParameter(LootParameters.POSITION, blockpos).withParameter(LootParameters.TOOL, ItemStack.EMPTY).withNullableParameter(LootParameters.BLOCK_ENTITY, tileentity);
						if (this.destroyBlocks == Explosion.Mode.DESTROY) {
							lootcontext$builder.withParameter(LootParameters.EXPLOSION_RADIUS, this.explosionSize);
						}

						Block.spawnDrops(iblockstate, lootcontext$builder);
					}

					this.worldObj.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
					block.onExplosionDestroy(this.worldObj, blockpos, this);
				}
			}
		}

		if (this.isFlaming) {
			for (BlockPos blockpos1 : this.affectedBlockPositions) {
				if (this.worldObj.getBlockState(blockpos1).getMaterial() == Material.AIR && this.worldObj.getBlockState(blockpos1.down()).isOpaqueCube(this.worldObj, blockpos1.down()) && this.explosionRNG.nextInt(3) == 0) {
					this.worldObj.setBlockState(blockpos1, Blocks.FIRE.getDefaultState());
				}
			}
		}
	}

	@Override
	public Map<PlayerEntity, Vec3d> getPlayerKnockbackMap() {
		return this.playerKnockbackMap;
	}

	@Override
	public LivingEntity getExplosivePlacedBy() {
		return this.exploder == null ? null : (this.exploder instanceof TNTEntity ? ((TNTEntity) this.exploder).getTntPlacedBy() : (this.exploder instanceof LivingEntity ? (LivingEntity) this.exploder : null));
	}

	@Override
	public void clearAffectedBlockPositions() {
		this.affectedBlockPositions.clear();
	}

	@Override
	public List<BlockPos> getAffectedBlockPositions() {
		return this.affectedBlockPositions;
	}

	@Override
	public Vec3d getPosition() {
		return this.position;
	}
}