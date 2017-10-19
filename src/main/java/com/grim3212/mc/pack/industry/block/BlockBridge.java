package com.grim3212.mc.pack.industry.block;

import java.lang.reflect.Constructor;
import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.property.UnlistedPropertyBlockState;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.tile.TileEntityBridge;
import com.grim3212.mc.pack.industry.tile.TileEntityBridgeGravity;
import com.grim3212.mc.pack.industry.util.EnumBridgeType;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBridge extends BlockManual {

	public static final UnlistedPropertyBlockState BLOCK_STATE = UnlistedPropertyBlockState.create("blockstate");
	public static PropertyEnum<EnumBridgeType> TYPE = PropertyEnum.create("type", EnumBridgeType.class);
	public static List<Block> laserBreakeables = Lists.newArrayList(Blocks.WATER, Blocks.FLOWING_WATER, Blocks.LAVA, Blocks.FLOWING_LAVA, Blocks.ICE, Blocks.REEDS, Blocks.SNOW_LAYER, Blocks.WHEAT, Blocks.POTATOES, Blocks.CARROTS, Blocks.BEETROOTS);

	public BlockBridge() {
		super("bridge", Material.IRON, SoundType.METAL);
		setBlockUnbreakable();
		setResistance(6000000.0F);
		setTickRandomly(true);
		disableStats();
	}

	@Override
	public Page getPage(IBlockState state) {
		return state.getValue(TYPE).getPage();
	}

	@Override
	protected IBlockState getState() {
		return super.getState().withProperty(TYPE, EnumBridgeType.LASER);
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return false;

		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		if (!blockState.getValue(TYPE).isSolid()) {
			return NULL_AABB;
		}

		return FULL_BLOCK_AABB;
	}

	@Override
	public boolean causesSuffocation(IBlockState state) {
		return state.getValue(TYPE).isSolid();
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return state.getValue(TYPE).isSolid();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return !worldIn.getBlockState(pos).getValue(TYPE).isSolid();
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if (worldIn.getBlockState(pos).getValue(TYPE) == EnumBridgeType.ACCEL && entityIn instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) entityIn;
			living.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("speed"), 10, 2));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		EnumBridgeType type = worldIn.getBlockState(pos).getValue(TYPE);

		if (type == EnumBridgeType.DEATH) {
			entityIn.attackEntityFrom(DamageSource.MAGIC, 4);
		} else if (type == EnumBridgeType.GRAVITY) {

			if (entityIn instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) entityIn;

				living.getArmorInventoryList().forEach(stack -> {
					// Don't apply lift if wearing gravity boots
					if (stack.getItem() == IndustryItems.gravity_boots) {
						return;
					}
				});
			}

			TileEntity te = worldIn.getTileEntity(pos);

			if (te instanceof TileEntityBridgeGravity) {
				TileEntityBridgeGravity grav = (TileEntityBridgeGravity) te;

				// Vector3f vector3f =
				// Node_TileEntityBridgeControl.getProjectionVector(i1);
				double d = 0.40000000000000002D;
				EnumFacing facing = grav.getGravFacing();
				int offset = facing.getAxisDirection().getOffset();

				if (facing.getAxis() == EnumFacing.Axis.X) {
					entityIn.motionX += (double) offset * d;
					if (offset > 0.0F && entityIn.motionX > 1.0D) {
						entityIn.motionX = 1.0D;
					}
					if (offset < 0.0F && entityIn.motionX < -1D) {
						entityIn.motionX = -1D;
					}
					if (entityIn.motionY < 0.0D) {
						entityIn.motionY = 0.0D;
					} else {
						entityIn.motionY *= 0.5D;
					}
					entityIn.motionZ *= 0.5D;
				} else if (facing.getAxis() == EnumFacing.Axis.Y) {
					entityIn.motionY += (double) facing.getAxisDirection().getOffset() * d;
					if (offset > 0.0F && entityIn.motionY > 1.0D) {
						entityIn.motionY = 1.0D;
					}
					if (offset < 0.0F && entityIn.motionY < -1D) {
						entityIn.motionY = -1D;
					}
					entityIn.motionX *= 0.5D;
					entityIn.motionZ *= 0.5D;
				} else if (facing.getAxis() == EnumFacing.Axis.Z) {
					entityIn.motionZ += (double) facing.getAxisDirection().getOffset() * d;
					if (offset > 0.0F && entityIn.motionZ > 1.0D) {
						entityIn.motionZ = 1.0D;
					}
					if (offset < 0.0F && entityIn.motionZ < -1D) {
						entityIn.motionZ = -1D;
					}
					entityIn.motionX *= 0.5D;
					if (entityIn.motionY < 0.0D) {
						entityIn.motionY = 0.0D;
					} else {
						entityIn.motionY *= 0.5D;
					}
				}
				entityIn.fallDistance = 0.0F;
			}
		}
	}

	public static boolean canLaserBreak(World world, BlockPos pos, Block b) {
		if (b.isReplaceable(world, pos)) {
			return true;
		}

		return laserBreakeables.contains(b);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumBridgeType.values[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { TYPE }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityBridge && state instanceof IExtendedBlockState) {
			IExtendedBlockState blockState = (IExtendedBlockState) state;
			TileEntityBridge tef = (TileEntityBridge) te;
			return blockState.withProperty(BLOCK_STATE, tef.getBlockState());
		}
		return state;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		if (state.getValue(TYPE) == EnumBridgeType.GRAVITY)
			return new TileEntityBridgeGravity();

		return new TileEntityBridge();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
		TileEntity te = worldObj.getTileEntity(target.getBlockPos());

		BlockPos pos = target.getBlockPos();

		if (te instanceof TileEntityBridge) {
			TileEntityBridge tileentity = (TileEntityBridge) te;
			IBlockState iblockstate = tileentity.getBlockState();

			if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
				int i = pos.getX();
				int j = pos.getY();
				int k = pos.getZ();
				float f = 0.1F;
				AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox(worldObj, pos);
				double d0 = (double) i + RANDOM.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - (double) (f * 2.0F)) + (double) f + axisalignedbb.minX;
				double d1 = (double) j + RANDOM.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - (double) (f * 2.0F)) + (double) f + axisalignedbb.minY;
				double d2 = (double) k + RANDOM.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - (double) (f * 2.0F)) + (double) f + axisalignedbb.minZ;

				EnumFacing side = target.sideHit;

				if (side == EnumFacing.DOWN) {
					d1 = (double) j + axisalignedbb.minY - (double) f;
				}

				if (side == EnumFacing.UP) {
					d1 = (double) j + axisalignedbb.maxY + (double) f;
				}

				if (side == EnumFacing.NORTH) {
					d2 = (double) k + axisalignedbb.minZ - (double) f;
				}

				if (side == EnumFacing.SOUTH) {
					d2 = (double) k + axisalignedbb.maxZ + (double) f;
				}

				if (side == EnumFacing.WEST) {
					d0 = (double) i + axisalignedbb.minX - (double) f;
				}

				if (side == EnumFacing.EAST) {
					d0 = (double) i + axisalignedbb.maxX + (double) f;
				}

				try {
					Constructor<ParticleDigging> constructor = ParticleDigging.class.getDeclaredConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class, IBlockState.class);
					constructor.setAccessible(true);
					ParticleDigging digging = constructor.newInstance(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, iblockstate);
					digging.setBlockPos(target.getBlockPos()).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f);
					manager.addEffect(digging);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return super.addHitEffects(state, worldObj, target, manager);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityBridge) {
			TileEntityBridge tileentity = (TileEntityBridge) te;
			if (tileentity.getBlockState() == Blocks.AIR.getDefaultState()) {
				return super.addDestroyEffects(world, pos, manager);
			} else {
				manager.clearEffects(world);
				manager.addBlockDestroyEffects(pos, tileentity.getBlockState());
			}
		}

		return true;
	}

	@Override
	public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		TileEntity tileentity = (TileEntity) worldObj.getTileEntity(blockPosition);
		if (tileentity instanceof TileEntityBridge) {
			TileEntityBridge te = (TileEntityBridge) tileentity;
			if (te.getBlockState() == Blocks.AIR.getDefaultState()) {
				return super.addLandingEffects(state, worldObj, blockPosition, iblockstate, entity, numberOfParticles);
			} else {
				worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(te.getBlockState()) });
			}
		}
		return true;
	}
}