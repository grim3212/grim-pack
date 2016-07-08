package com.grim3212.mc.pack.industry.block;

import java.lang.reflect.Constructor;

import com.grim3212.mc.pack.core.property.UnlistedPropertyInteger;
import com.grim3212.mc.pack.industry.tile.TileEntityCamo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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

public class BlockCamoPlate extends BlockPressurePlate implements ITileEntityProvider {

	public static final UnlistedPropertyInteger BLOCKID = UnlistedPropertyInteger.create("blockid");
	public static final UnlistedPropertyInteger BLOCKMETA = UnlistedPropertyInteger.create("blockmeta");

	protected BlockCamoPlate() {
		super(Material.IRON, Sensitivity.EVERYTHING);
		this.setSoundType(SoundType.METAL);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { POWERED }, new IUnlistedProperty[] { BLOCKID, BLOCKMETA });
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityCamo) {
			TileEntityCamo tef = (TileEntityCamo) te;

			IBlockState belowState = worldIn.getBlockState(pos.down());

			if (belowState.getBlock() == null || belowState.getBlock() == Blocks.AIR || belowState.getBlock() instanceof BlockSlab || belowState.getBlock() instanceof BlockSilverfish || belowState.getBlock().hasTileEntity(belowState.getBlock().getDefaultState()) || !belowState.isNormalCube() || !belowState.isOpaqueCube() || belowState.getBlock() instanceof BlockHugeMushroom)
				return;

			tef.setBlockID(Block.getIdFromBlock(belowState.getBlock()));
			tef.setBlockMeta(belowState.getBlock().getMetaFromState(belowState));
		}
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityCamo && state instanceof IExtendedBlockState) {
			IExtendedBlockState blockState = (IExtendedBlockState) state;
			TileEntityCamo tef = (TileEntityCamo) te;
			return blockState.withProperty(BLOCKID, tef.getBlockID()).withProperty(BLOCKMETA, tef.getBlockMeta());
		}
		return state;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCamo();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
		TileEntityCamo tileentity = (TileEntityCamo) worldObj.getTileEntity(target.getBlockPos());
		IBlockState iblockstate = Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta());
		BlockPos pos = target.getBlockPos();

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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return super.addHitEffects(state, worldObj, target, manager);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		TileEntityCamo tileentity = (TileEntityCamo) world.getTileEntity(pos);
		manager.clearEffects(world);
		manager.addBlockDestroyEffects(pos, Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta()));

		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		TileEntity tileentity = (TileEntity) worldObj.getTileEntity(blockPosition);
		if (tileentity instanceof TileEntityCamo) {
			TileEntityCamo te = (TileEntityCamo) tileentity;
			((WorldServer) worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(Block.getBlockById(te.getBlockID()).getStateFromMeta(te.getBlockMeta())) });
		}
		return true;
	}
}
