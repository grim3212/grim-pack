package com.grim3212.mc.pack.decor.block;

import java.lang.reflect.Constructor;
import java.util.Random;

import com.grim3212.mc.pack.core.property.UnlistedPropertyInteger;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.tile.TileEntityTextured;
import com.grim3212.mc.pack.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
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

public abstract class BlockTextured extends Block implements ITileEntityProvider {

	public static final UnlistedPropertyInteger BLOCKID = UnlistedPropertyInteger.create("blockid");
	public static final UnlistedPropertyInteger BLOCKMETA = UnlistedPropertyInteger.create("blockmeta");

	public BlockTextured() {
		super(Material.ROCK);
		this.setHardness(1.0F);
		this.setResistance(10F);
		this.setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { BLOCKID, BLOCKMETA });
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityTextured && state instanceof IExtendedBlockState) {
			IExtendedBlockState blockState = (IExtendedBlockState) state;
			TileEntityTextured tef = (TileEntityTextured) te;
			return blockState.withProperty(BLOCKID, tef.getBlockID()).withProperty(BLOCKMETA, tef.getBlockMeta());
		}
		return state;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack itemstack = new ItemStack(this, 1);
		NBTHelper.setInteger(itemstack, "blockID", 0);
		NBTHelper.setInteger(itemstack, "blockMeta", 0);
		return itemstack;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (heldItem != null && heldItem.getItem() != null) {
			Block block = Block.getBlockFromItem(heldItem.getItem());
			if (block != null) {
				if (BlockHelper.getBlocks().containsKey(block)) {
					TileEntity tileentity = worldIn.getTileEntity(pos);
					if (tileentity instanceof TileEntityTextured) {
						((TileEntityTextured) tileentity).setBlockID(Block.getIdFromBlock(block));
						((TileEntityTextured) tileentity).setBlockMeta(heldItem.getMetadata());
						worldIn.setBlockState(pos, this.getExtendedState(state, worldIn, pos));
						worldIn.playSound(playerIn, pos, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
						if (!worldIn.isRemote) {
							worldIn.markBlockRangeForRenderUpdate(pos, pos);
						}
						return true;
					}
				}
			}
		} else {
			if (playerIn.isSneaking()) {
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof TileEntityTextured) {
					((TileEntityTextured) tileentity).setBlockID(0);
					((TileEntityTextured) tileentity).setBlockMeta(0);
					worldIn.setBlockState(pos, this.getExtendedState(state, worldIn, pos));
					worldIn.playSound(playerIn, pos, this.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (this.getSoundType().getVolume() + 1.0F) / 2.0F, this.getSoundType().getPitch() * 0.8F);
					if (!worldIn.isRemote) {
						worldIn.markBlockRangeForRenderUpdate(pos, pos);
					}
					return true;
				}
			}
		}

		return false;
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
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTextured();
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
		TileEntityTextured tileentity = (TileEntityTextured) worldObj.getTileEntity(target.getBlockPos());
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
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return super.addHitEffects(state, worldObj, target, manager);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityTextured) {
			TileEntityTextured tileentity = (TileEntityTextured) te;
			if (tileentity.getBlockID() == 0 && tileentity.getBlockMeta() == 0) {
				return super.addDestroyEffects(world, pos, manager);
			} else {
				manager.clearEffects(world);
				manager.addBlockDestroyEffects(pos, Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta()));
			}
		}

		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		TileEntity tileentity = (TileEntity) worldObj.getTileEntity(blockPosition);
		if (tileentity instanceof TileEntityTextured) {
			TileEntityTextured te = (TileEntityTextured) tileentity;
			if (te.getBlockID() == 0 && te.getBlockMeta() == 0) {
				return super.addLandingEffects(state, worldObj, blockPosition, iblockstate, entity, numberOfParticles);
			} else {
				worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(Block.getBlockById(te.getBlockID()).getStateFromMeta(te.getBlockMeta())) });
			}
		}
		return true;
	}
}