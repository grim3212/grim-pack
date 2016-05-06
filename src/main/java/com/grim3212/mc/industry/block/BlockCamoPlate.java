package com.grim3212.mc.industry.block;

import java.lang.reflect.Constructor;

import com.grim3212.mc.core.property.UnlistedPropertyInteger;
import com.grim3212.mc.industry.tile.TileEntityCamo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCamoPlate extends BlockPressurePlate implements ITileEntityProvider {

	public static final UnlistedPropertyInteger BLOCKID = UnlistedPropertyInteger.create("blockid");
	public static final UnlistedPropertyInteger BLOCKMETA = UnlistedPropertyInteger.create("blockmeta");

	protected BlockCamoPlate() {
		super(Material.iron, Sensitivity.EVERYTHING);
		float var2 = 0.0625F;
		this.setBlockBounds(var2, 0.0F, var2, 1.0F - var2, 0.03125F, 1.0F - var2);
	}

	@Override
	protected BlockState createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { POWERED }, new IUnlistedProperty[] { BLOCKID, BLOCKMETA });
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityCamo) {
			TileEntityCamo tef = (TileEntityCamo) te;

			IBlockState belowState = worldIn.getBlockState(pos.down());

			if (belowState.getBlock() == null || belowState.getBlock() == Blocks.air || belowState.getBlock() instanceof BlockSlab || belowState.getBlock() instanceof BlockSilverfish || belowState.getBlock().hasTileEntity(belowState.getBlock().getDefaultState()) || !belowState.getBlock().isNormalCube() || !belowState.getBlock().isOpaqueCube() || belowState.getBlock() instanceof BlockHugeMushroom)
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
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		return worldIn.getBlockState(pos.down()).getBlock().colorMultiplier(worldIn, pos.down(), renderPass);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCamo();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
		IBlockState iblockstate = worldObj.getBlockState(target.getBlockPos().down());
		Block block = iblockstate.getBlock();

		if (block.getRenderType() != -1) {
			int i = target.getBlockPos().getX();
			int j = target.getBlockPos().getY();
			int k = target.getBlockPos().getZ();
			float f = 0.1F;
			double d0 = (double) i + RANDOM.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (double) (f * 2.0F)) + (double) f + block.getBlockBoundsMinX();
			double d1 = (double) j + RANDOM.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (double) (f * 2.0F)) + (double) f + block.getBlockBoundsMinY();
			double d2 = (double) k + RANDOM.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (double) (f * 2.0F)) + (double) f + block.getBlockBoundsMinZ();

			if (target.sideHit == EnumFacing.DOWN) {
				d1 = (double) j + block.getBlockBoundsMinY() - (double) f;
			}

			if (target.sideHit == EnumFacing.UP) {
				d1 = (double) j + block.getBlockBoundsMaxY() + (double) f;
			}

			if (target.sideHit == EnumFacing.NORTH) {
				d2 = (double) k + block.getBlockBoundsMinZ() - (double) f;
			}

			if (target.sideHit == EnumFacing.SOUTH) {
				d2 = (double) k + block.getBlockBoundsMaxZ() + (double) f;
			}

			if (target.sideHit == EnumFacing.WEST) {
				d0 = (double) i + block.getBlockBoundsMinX() - (double) f;
			}

			if (target.sideHit == EnumFacing.EAST) {
				d0 = (double) i + block.getBlockBoundsMaxX() + (double) f;
			}

			try {
				Constructor<EntityDiggingFX> constructor = EntityDiggingFX.class.getDeclaredConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class, IBlockState.class);
				constructor.setAccessible(true);
				EntityDiggingFX digging = constructor.newInstance(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, iblockstate);
				digging.func_174846_a(target.getBlockPos()).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f);
				effectRenderer.addEffect(digging);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer) {
		effectRenderer.clearEffects(world);
		addBlockDestroyEffects(pos, world.getBlockState(pos.down()), world, effectRenderer);
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addLandingEffects(WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(worldObj.getBlockState(blockPosition.down())) });
		return true;
	}

	@SideOnly(Side.CLIENT)
	private void addBlockDestroyEffects(BlockPos pos, IBlockState state, World worldObj, EffectRenderer renderer) {
		if (!state.getBlock().isAir(worldObj, pos) && !state.getBlock().addDestroyEffects(worldObj, pos, renderer)) {
			state = state.getBlock().getActualState(state, worldObj, pos);
			int i = 4;

			for (int j = 0; j < i; ++j) {
				for (int k = 0; k < i; ++k) {
					for (int l = 0; l < i; ++l) {
						double d0 = (double) pos.getX() + ((double) j + 0.5D) / (double) i;
						double d1 = (double) pos.getY() + ((double) k + 0.5D) / (double) i;
						double d2 = (double) pos.getZ() + ((double) l + 0.5D) / (double) i;
						try {
							Constructor<EntityDiggingFX> constructor = EntityDiggingFX.class.getDeclaredConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class, IBlockState.class);
							constructor.setAccessible(true);
							EntityDiggingFX digging = constructor.newInstance(worldObj, d0, d1, d2, d0 - (double) pos.getX() - 0.5D, d1 - (double) pos.getY() - 0.5D, d2 - (double) pos.getZ() - 0.5D, state).func_174846_a(pos.down());
							renderer.addEffect(digging);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
