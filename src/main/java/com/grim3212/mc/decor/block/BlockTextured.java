package com.grim3212.mc.decor.block;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import com.grim3212.mc.core.property.UnlistedPropertyInteger;
import com.grim3212.mc.core.util.NBTHelper;
import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.tile.TileEntityTextured;
import com.grim3212.mc.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockTextured extends Block implements ITileEntityProvider {

	public static final UnlistedPropertyInteger BLOCKID = UnlistedPropertyInteger.create("blockid");
	public static final UnlistedPropertyInteger BLOCKMETA = UnlistedPropertyInteger.create("blockmeta");

	public BlockTextured() {
		super(Material.rock);
		this.setHardness(1.0F);
		this.setResistance(10F);
		this.setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
	}

	@Override
	protected BlockState createBlockState() {
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
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
		if (te instanceof TileEntityTextured) {
			ItemStack itemstack = new ItemStack(this, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityTextured) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityTextured) te).getBlockMeta());
			spawnAsEntity(worldIn, pos, itemstack);
		} else {
			super.harvestBlock(worldIn, player, pos, state, te);
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityTextured) {
			ItemStack itemstack = new ItemStack(this, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityTextured) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityTextured) te).getBlockMeta());
			return itemstack;
		}
		return super.getPickBlock(target, world, pos, player);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityTextured) {
			return Block.getBlockById(((TileEntityTextured) te).getBlockID()).colorMultiplier(worldIn, pos, renderPass);
		} else {
			return 16777215;
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public int getRenderType() {
		return 3;
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
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTextured();
	}

	@Override
	public float getBlockHardness(World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityTextured) {
			IBlockState blockState = Block.getBlockById(((TileEntityTextured) tileentity).getBlockID()).getStateFromMeta(((TileEntityTextured) tileentity).getBlockMeta());
			return blockState.getBlock().getBlockHardness(worldIn, pos);
		} else {
			return super.getBlockHardness(worldIn, pos);
		}
	}

	@Override
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
	public float getPlayerRelativeBlockHardness(EntityPlayer playerIn, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityTextured) {
			IBlockState blockState = Block.getBlockById(((TileEntityTextured) tileentity).getBlockID()).getStateFromMeta(((TileEntityTextured) tileentity).getBlockMeta());
			return BlockHelper.blockStrength(blockState, playerIn, worldIn, pos);
		} else {
			return super.getPlayerRelativeBlockHardness(playerIn, worldIn, pos);
		}
	}

	@Override
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
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityTextured) {
			IBlockState blockState = Block.getBlockById(((TileEntityTextured) tileentity).getBlockID()).getStateFromMeta(((TileEntityTextured) tileentity).getBlockMeta());
			return blockState.getBlock().canEntityDestroy(world, pos, entity);
		} else {
			return super.canEntityDestroy(world, pos, entity);
		}
	}

	@Override
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
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
		TileEntityTextured tileentity = (TileEntityTextured) worldObj.getTileEntity(target.getBlockPos());

		IBlockState iblockstate = Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta());
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

		worldObj.getBlockState(target.getBlockPos()).getBlock().setStepSound(block.stepSound);

		return true;
	}

	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer) {
		TileEntityTextured tileentity = (TileEntityTextured) world.getTileEntity(pos);
		effectRenderer.clearEffects(world);
		effectRenderer.addBlockDestroyEffects(pos, Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta()));
		world.getBlockState(pos).getBlock().setStepSound(Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta()).getBlock().stepSound);

		return true;
	}
}