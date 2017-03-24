package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.tile.TileEntityStorage;
import com.grim3212.mc.pack.industry.util.StorageUtil;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleDigging.Factory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;

public abstract class BlockStorage extends BlockManual implements ITileEntityProvider {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	// public static final PropertyBool LOCKED = PropertyBool.create("locked");

	public BlockStorage(Material material, SoundType type) {
		super(material, type);
		this.setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH);
	}

	protected boolean isInvalidBlock(World world, BlockPos pos) {
		return !world.isAirBlock(pos) && world.getBlockState(pos).isOpaqueCube();
	}

	public boolean isDoorBlocked(World world, BlockPos pos, IBlockState state) {
		return isInvalidBlock(world, pos.offset(state.getValue(FACING)));
	}

	@Override
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);

		if (te instanceof TileEntityStorage) {
			TileEntityStorage tileentity = (TileEntityStorage) te;

			if ((tileentity.isLocked()) && (!StorageUtil.canAccess(tileentity, player)))
				return -1.0F;
		}

		return ForgeHooks.blockStrength(state, player, worldIn, pos);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	protected int getGuiId() {
		return PackGuiHandler.STORAGE_DEFAULT_GUI_ID;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityStorage) {
			TileEntityStorage teStorage = (TileEntityStorage) tileentity;

			if (teStorage.isLocked()) {
				ItemStack lockStack = new ItemStack(IndustryItems.locksmith_lock);
				NBTHelper.setString(lockStack, "Lock", teStorage.getLockCode().getLock());
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), lockStack);
				teStorage.clearLock();
			}

			InventoryHelper.dropInventoryItems(worldIn, pos, teStorage);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);

		if (te != null && te instanceof TileEntityStorage) {
			TileEntityStorage tileentity = (TileEntityStorage) te;

			if (playerIn.getHeldItem(hand).getItem() == IndustryItems.locksmith_lock) {
				if (StorageUtil.tryPlaceLock(tileentity, playerIn, hand))
					return true;
			}

			if ((!isDoorBlocked(worldIn, pos, state)) && (StorageUtil.canAccess(tileentity, playerIn)))
				playerIn.openGui(GrimPack.INSTANCE, getGuiId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityStorage) {
				((TileEntityStorage) tileentity).setCustomInventoryName(stack.getDisplayName());
			}
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	/**
	 * @Override public IBlockState getActualState(@Nonnull IBlockState state,
	 *           IBlockAccess world, BlockPos pos) { TileEntity te =
	 *           world.getTileEntity(pos);
	 * 
	 *           if (te != null && te instanceof TileEntityStorage) { return
	 *           state.withProperty(Properties.StaticProperty,
	 *           true).withProperty(LOCKED, ((TileEntityStorage)
	 *           te).isLocked()); }
	 * 
	 *           return state.withProperty(Properties.StaticProperty,
	 *           true).withProperty(LOCKED, false); }
	 **/

	@Override
	protected BlockStateContainer createBlockState() {
		// return new ExtendedBlockState(this, new IProperty[] { FACING, LOCKED,
		// Properties.StaticProperty }, new IUnlistedProperty[] {
		// Properties.AnimationProperty });
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityStorage) {
			TileEntityStorage tileentity = (TileEntityStorage) te;
			if (tileentity.getBreakTextureState() == Blocks.AIR.getDefaultState()) {
				return super.addDestroyEffects(world, pos, manager);
			} else {
				manager.clearEffects(world);
				manager.addBlockDestroyEffects(pos, tileentity.getBreakTextureState());
			}
		}

		return true;
	}

	@Override
	public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
		TileEntity te = worldObj.getTileEntity(target.getBlockPos());

		if (te instanceof TileEntityStorage) {
			TileEntityStorage tileentity = (TileEntityStorage) te;
			BlockPos pos = target.getBlockPos();

			if (tileentity.getBreakTextureState().getRenderType() != EnumBlockRenderType.INVISIBLE) {
				int i = pos.getX();
				int j = pos.getY();
				int k = pos.getZ();
				float f = 0.1F;
				AxisAlignedBB axisalignedbb = tileentity.getBreakTextureState().getBoundingBox(worldObj, pos);
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

				Factory particleFactory = new ParticleDigging.Factory();
				ParticleDigging digging = (ParticleDigging) particleFactory.createParticle(0, worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(tileentity.getBreakTextureState()));
				digging.setBlockPos(target.getBlockPos()).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f);
				manager.addEffect(digging);
				return true;
			}
		}

		return super.addHitEffects(state, worldObj, target, manager);
	}

	@Override
	public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		TileEntity tileentity = (TileEntity) worldObj.getTileEntity(blockPosition);
		if (tileentity instanceof TileEntityStorage) {
			TileEntityStorage te = (TileEntityStorage) tileentity;
			if (te.getBreakTextureState() == Blocks.AIR.getDefaultState()) {
				return super.addLandingEffects(state, worldObj, blockPosition, iblockstate, entity, numberOfParticles);
			} else {
				worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(te.getBreakTextureState()) });
			}
		}
		return true;
	}
}
