package com.grim3212.mc.pack.decor.block;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public abstract class BlockTextured extends Block implements ITileEntityProvider, IBlockColor {

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
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		ItemStack itemstack = new ItemStack(this, 1);
		if (tileentity instanceof TileEntityTextured) {
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityTextured) tileentity).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityTextured) tileentity).getBlockMeta());
			spawnAsEntity(worldIn, pos, itemstack);
		} else {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
		}
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (te instanceof TileEntityTextured) {
			ItemStack itemstack = new ItemStack(this, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityTextured) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityTextured) te).getBlockMeta());
			spawnAsEntity(worldIn, pos, itemstack);
		} else {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityTextured) {
			ItemStack itemstack = new ItemStack(this, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityTextured) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityTextured) te).getBlockMeta());
			return itemstack;
		}
		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	@SuppressWarnings("deprecation")
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityTextured) {
			return Minecraft.getMinecraft().getBlockColors().colorMultiplier(Block.getBlockById(((TileEntityTextured) te).getBlockID()).getStateFromMeta(((TileEntityTextured) te).getBlockMeta()), worldIn, pos, tintIndex);
		} else {
			return 16777215;
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		LinkedHashMap<Block, Integer> loadedBlocks = BlockHelper.getBlocks();
		Block[] blocks = loadedBlocks.keySet().toArray(new Block[loadedBlocks.keySet().size()]);

		for (int i = 0; i < blocks.length; i++) {
			if (loadedBlocks.get(blocks[i]) == 0) {
				ItemStack stack = new ItemStack(itemIn, 1);
				NBTHelper.setInteger(stack, "blockID", Block.getIdFromBlock(blocks[i]));
				NBTHelper.setInteger(stack, "blockMeta", 0);
				list.add(stack);
			} else {
				for (int j = 0; j < loadedBlocks.get(blocks[i]); j++) {
					ItemStack stack = new ItemStack(itemIn, 1);
					NBTHelper.setInteger(stack, "blockID", Block.getIdFromBlock(blocks[i]));
					NBTHelper.setInteger(stack, "blockMeta", j);
					list.add(stack);
				}
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
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTextured();
	}

	@Override
	@SuppressWarnings("deprecation")
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityTextured) {
			IBlockState heldState = Block.getBlockById(((TileEntityTextured) tileentity).getBlockID()).getStateFromMeta(((TileEntityTextured) tileentity).getBlockMeta());
			return heldState.getBlockHardness(worldIn, pos);
		} else {
			return super.getBlockHardness(blockState, worldIn, pos);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityTextured) {
			IBlockState blockState = Block.getBlockById(((TileEntityTextured) tileentity).getBlockID()).getStateFromMeta(((TileEntityTextured) tileentity).getBlockMeta());
			return blockState.getBlock().getExplosionResistance(world, pos, exploder, explosion);
		} else {
			return super.getExplosionResistance(world, pos, exploder, explosion);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityTextured) {
			IBlockState blockState = Block.getBlockById(((TileEntityTextured) tileentity).getBlockID()).getStateFromMeta(((TileEntityTextured) tileentity).getBlockMeta());
			return BlockHelper.blockStrength(blockState, player, worldIn, pos);
		} else {
			return super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isWood(IBlockAccess world, BlockPos pos) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityTextured) {
			IBlockState blockState = Block.getBlockById(((TileEntityTextured) tileentity).getBlockID()).getStateFromMeta(((TileEntityTextured) tileentity).getBlockMeta());
			return blockState.getBlock().isWood(world, pos);
		} else {
			return super.isWood(world, pos);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityTextured) {
			IBlockState blockState = Block.getBlockById(((TileEntityTextured) tileentity).getBlockID()).getStateFromMeta(((TileEntityTextured) tileentity).getBlockMeta());
			return blockState.getBlock().canEntityDestroy(state, world, pos, entity);
		} else {
			return super.canEntityDestroy(state, world, pos, entity);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityTextured) {
			IBlockState blockState = Block.getBlockById(((TileEntityTextured) tileentity).getBlockID()).getStateFromMeta(((TileEntityTextured) tileentity).getBlockMeta());
			return BlockHelper.canHarvestBlock(blockState, player, world, pos);
		} else {
			return super.canHarvestBlock(world, pos, player);
		}
	}

	@Override
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return super.addHitEffects(state, worldObj, target, manager);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		TileEntityTextured tileentity = (TileEntityTextured) world.getTileEntity(pos);
		manager.clearEffects(world);
		manager.addBlockDestroyEffects(pos, Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta()));

		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		TileEntity tileentity = (TileEntity) worldObj.getTileEntity(blockPosition);
		if (tileentity instanceof TileEntityTextured) {
			TileEntityTextured te = (TileEntityTextured) tileentity;
			((WorldServer) worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(Block.getBlockById(te.getBlockID()).getStateFromMeta(te.getBlockMeta())) });
		}
		return true;
	}
}