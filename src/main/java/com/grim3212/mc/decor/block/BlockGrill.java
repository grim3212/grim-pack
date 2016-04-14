package com.grim3212.mc.decor.block;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import com.grim3212.mc.core.network.PacketDispatcher;
import com.grim3212.mc.core.property.UnlistedPropertyBoolean;
import com.grim3212.mc.core.property.UnlistedPropertyInteger;
import com.grim3212.mc.core.util.NBTHelper;
import com.grim3212.mc.decor.GrimDecor;
import com.grim3212.mc.decor.config.DecorConfig;
import com.grim3212.mc.decor.network.MessageExtinguish;
import com.grim3212.mc.decor.network.MessageUpdateFireplace;
import com.grim3212.mc.decor.tile.TileEntityGrill;
import com.grim3212.mc.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
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

public class BlockGrill extends BlockContainer {

	public static final UnlistedPropertyInteger BLOCKID = UnlistedPropertyInteger.create("blockid");
	public static final UnlistedPropertyInteger BLOCKMETA = UnlistedPropertyInteger.create("blockmeta");
	public static final UnlistedPropertyBoolean ACTIVE = UnlistedPropertyBoolean.create("active");

	public BlockGrill() {
		super(Material.rock);
		setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityGrill();
	}

	@Override
	protected BlockState createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { BLOCKID, BLOCKMETA, ACTIVE });
	}

	@Override
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		TileEntityGrill tef = (TileEntityGrill) worldIn.getTileEntity(pos);
		if (DecorConfig.isFireParticles) {
			if (tef.isActive()) {
				for (int i = 0; i < 5; i++) {
					double xVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;
					double yVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;
					double zVar = (worldIn.rand.nextDouble() - 0.5D) / 5.0D;

					double height = rand.nextDouble();
					if (height > 0.85D || height < 0.7D)
						height = 0.7D;
					worldIn.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5D + xVar, pos.getY() + height + yVar, pos.getZ() + 0.5D + zVar, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		TileEntityGrill tef = (TileEntityGrill) worldIn.getTileEntity(pos);
		if (tef.isActive()) {
			if (!worldIn.isRemote) {
				PacketDispatcher.sendToDimension(new MessageExtinguish(pos), playerIn.dimension);
				tef.setActive(false);
			}
			worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.fizz", 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;

		TileEntityGrill tef = (TileEntityGrill) worldIn.getTileEntity(pos);
		ItemStack stack = playerIn.inventory.getStackInSlot(playerIn.inventory.currentItem);

		if ((stack != null) && ((stack.getItem() == Items.flint_and_steel) || (stack.getItem() == Items.fire_charge))) {
			if (!tef.isActive()) {
				stack.damageItem(1, playerIn);
				worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "fire.ignite", 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
				PacketDispatcher.sendToDimension(new MessageUpdateFireplace(pos, true), playerIn.dimension);
				tef.setActive(true);
			}
			return true;
		}

		playerIn.openGui(GrimDecor.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityGrill tileentity = (TileEntityGrill) worldIn.getTileEntity(pos);

		if (tileentity != null) {
			if (!worldIn.isRemote) {
				for (int var8 = 0; var8 < tileentity.getSizeInventory(); var8++) {
					ItemStack var9 = tileentity.getStackInSlot(var8);

					if (var9 != null) {
						float var10 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
						float var11 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
						EntityItem var14;
						for (float var12 = worldIn.rand.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; worldIn.spawnEntityInWorld(var14)) {
							int var13 = worldIn.rand.nextInt(21) + 10;

							if (var13 > var9.stackSize) {
								var13 = var9.stackSize;
							}

							var9.stackSize -= var13;
							var14 = new EntityItem(worldIn, pos.getX() + var10, pos.getY() + var11, pos.getZ() + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
							float var15 = 0.05F;
							var14.motionX = ((float) worldIn.rand.nextGaussian() * var15);
							var14.motionY = ((float) worldIn.rand.nextGaussian() * var15 + 0.2F);
							var14.motionZ = ((float) worldIn.rand.nextGaussian() * var15);

							if (var9.hasTagCompound()) {
								var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());
							}
						}
					}
				}
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos) {
		TileEntityGrill tef = (TileEntityGrill) world.getTileEntity(pos);

		if (tef != null) {
			return tef.getLightValue();
		} else {
			return super.getLightValue(world, pos);
		}
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityGrill && state instanceof IExtendedBlockState) {
			IExtendedBlockState blockState = (IExtendedBlockState) state;
			TileEntityGrill tef = (TileEntityGrill) te;
			return blockState.withProperty(BLOCKID, tef.getBlockID()).withProperty(BLOCKMETA, tef.getBlockMeta()).withProperty(ACTIVE, tef.isActive());
		}
		return state;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		LinkedHashMap<Block, Integer> loadedBlocks = BlockHelper.getBlocks();
		Block[] blocks = loadedBlocks.keySet().toArray(new Block[loadedBlocks.keySet().size()]);

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].getMaterial() == Material.wood || blocks[i].getMaterial() == Material.cloth || blocks[i].getMaterial() == Material.gourd || blocks[i].getMaterial() == Material.ice || blocks[i].getMaterial() == Material.glass || blocks[i].getMaterial() == Material.packedIce || blocks[i].getMaterial() == Material.sponge || blocks[i].getMaterial() == Material.grass || blocks[i].getMaterial() == Material.ground) {
				continue;
			}

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
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityGrill) {
			ItemStack itemstack = new ItemStack(this, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityGrill) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityGrill) te).getBlockMeta());
			return itemstack;
		}
		return super.getPickBlock(target, world, pos, player);
	}

	@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityGrill) {
			return Block.getBlockById(((TileEntityGrill) te).getBlockID()).colorMultiplier(worldIn, pos, renderPass);
		} else {
			return 16777215;
		}
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		ItemStack itemstack = new ItemStack(this, 1);
		if (tileentity instanceof TileEntityGrill) {
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityGrill) tileentity).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityGrill) tileentity).getBlockMeta());
			spawnAsEntity(worldIn, pos, itemstack);
		} else {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
		}
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
		if (te instanceof TileEntityGrill) {
			ItemStack itemstack = new ItemStack(this, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityGrill) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityGrill) te).getBlockMeta());
			spawnAsEntity(worldIn, pos, itemstack);
		} else {
			super.harvestBlock(worldIn, player, pos, state, te);
		}
	}

	@Override
	public float getBlockHardness(World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityGrill) {
			IBlockState blockState = Block.getBlockById(((TileEntityGrill) tileentity).getBlockID()).getStateFromMeta(((TileEntityGrill) tileentity).getBlockMeta());
			return blockState.getBlock().getBlockHardness(worldIn, pos);
		} else {
			return super.getBlockHardness(worldIn, pos);
		}
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityGrill) {
			IBlockState blockState = Block.getBlockById(((TileEntityGrill) tileentity).getBlockID()).getStateFromMeta(((TileEntityGrill) tileentity).getBlockMeta());
			return blockState.getBlock().getExplosionResistance(world, pos, exploder, explosion);
		} else {
			return super.getExplosionResistance(world, pos, exploder, explosion);
		}
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer playerIn, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityGrill) {
			IBlockState blockState = Block.getBlockById(((TileEntityGrill) tileentity).getBlockID()).getStateFromMeta(((TileEntityGrill) tileentity).getBlockMeta());
			return BlockHelper.blockStrength(blockState, playerIn, worldIn, pos);
		} else {
			return super.getPlayerRelativeBlockHardness(playerIn, worldIn, pos);
		}
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityGrill) {
			IBlockState blockState = Block.getBlockById(((TileEntityGrill) tileentity).getBlockID()).getStateFromMeta(((TileEntityGrill) tileentity).getBlockMeta());
			return blockState.getBlock().canEntityDestroy(world, pos, entity);
		} else {
			return super.canEntityDestroy(world, pos, entity);
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityGrill) {
			IBlockState blockState = Block.getBlockById(((TileEntityGrill) tileentity).getBlockID()).getStateFromMeta(((TileEntityGrill) tileentity).getBlockMeta());
			return BlockHelper.canHarvestBlock(blockState, player, world, pos);
		} else {
			return super.canHarvestBlock(world, pos, player);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
		TileEntityGrill tileentity = (TileEntityGrill) worldObj.getTileEntity(target.getBlockPos());

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
		TileEntityGrill tileentity = (TileEntityGrill) world.getTileEntity(pos);
		effectRenderer.clearEffects(world);
		effectRenderer.addBlockDestroyEffects(pos, Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta()));
		world.getBlockState(pos).getBlock().setStepSound(Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta()).getBlock().stepSound);
		return true;
	}
}
