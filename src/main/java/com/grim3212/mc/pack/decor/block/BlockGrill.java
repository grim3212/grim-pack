package com.grim3212.mc.pack.decor.block;

import java.lang.reflect.Constructor;
import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.PackGuiHandler;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.network.MessageParticles;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;
import com.grim3212.mc.pack.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
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

public class BlockGrill extends BlockFireplaceBase {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockGrill() {
		setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityGrill();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { ACTIVE }, new IUnlistedProperty[] { BLOCKID, BLOCKMETA });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta == 1 ? true : false);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (DecorConfig.isFireParticles) {
			if (worldIn.getBlockState(pos).getValue(ACTIVE)) {
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
		if (worldIn.getBlockState(pos).getValue(ACTIVE)) {
			if (!worldIn.isRemote) {
				PacketDispatcher.sendToDimension(new MessageParticles(pos), playerIn.dimension);
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(ACTIVE, false));
			}
			worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;

		ItemStack stack = playerIn.inventory.getStackInSlot(playerIn.inventory.currentItem);

		if ((stack != null) && ((stack.getItem() == Items.FLINT_AND_STEEL) || (stack.getItem() == Items.FIRE_CHARGE))) {
			if (!worldIn.getBlockState(pos).getValue(ACTIVE)) {
				stack.damageItem(1, playerIn);
				worldIn.playSound(playerIn, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
				worldIn.setBlockState(pos, state.withProperty(ACTIVE, true));
			}
			return true;
		}

		playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.GRILL_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
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
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getValue(ACTIVE)) {
			return 15;
		}
		return super.getLightValue(state, world, pos);
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityGrill && state instanceof IExtendedBlockState) {
			IExtendedBlockState blockState = (IExtendedBlockState) state;
			TileEntityGrill tef = (TileEntityGrill) te;
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
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityGrill) {
			ItemStack itemstack = new ItemStack(this, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityGrill) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityGrill) te).getBlockMeta());
			return itemstack;
		}
		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	@SuppressWarnings("deprecation")
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityGrill) {
			return Minecraft.getMinecraft().getBlockColors().colorMultiplier(Block.getBlockById(((TileEntityGrill) te).getBlockID()).getStateFromMeta(((TileEntityGrill) te).getBlockMeta()), worldIn, pos, tintIndex);
		} else {
			return 16777215;
		}
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
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (te instanceof TileEntityGrill) {
			ItemStack itemstack = new ItemStack(this, 1);
			NBTHelper.setInteger(itemstack, "blockID", ((TileEntityGrill) te).getBlockID());
			NBTHelper.setInteger(itemstack, "blockMeta", ((TileEntityGrill) te).getBlockMeta());
			spawnAsEntity(worldIn, pos, itemstack);
		} else {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityGrill) {
			IBlockState heldState = Block.getBlockById(((TileEntityGrill) tileentity).getBlockID()).getStateFromMeta(((TileEntityGrill) tileentity).getBlockMeta());
			return heldState.getBlockHardness(worldIn, pos);
		} else {
			return super.getBlockHardness(blockState, worldIn, pos);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
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
	@SuppressWarnings("deprecation")
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityGrill) {
			IBlockState blockState = Block.getBlockById(((TileEntityGrill) tileentity).getBlockID()).getStateFromMeta(((TileEntityGrill) tileentity).getBlockMeta());
			return BlockHelper.blockStrength(blockState, player, worldIn, pos);
		} else {
			return super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isWood(IBlockAccess world, BlockPos pos) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityGrill) {
			IBlockState blockState = Block.getBlockById(((TileEntityGrill) tileentity).getBlockID()).getStateFromMeta(((TileEntityGrill) tileentity).getBlockMeta());
			return blockState.getBlock().isWood(world, pos);
		} else {
			return super.isWood(world, pos);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityGrill) {
			IBlockState blockState = Block.getBlockById(((TileEntityGrill) tileentity).getBlockID()).getStateFromMeta(((TileEntityGrill) tileentity).getBlockMeta());
			return blockState.getBlock().canEntityDestroy(state, world, pos, entity);
		} else {
			return super.canEntityDestroy(state, world, pos, entity);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
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
	@SuppressWarnings("deprecation")
	public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
		TileEntityGrill tileentity = (TileEntityGrill) worldObj.getTileEntity(target.getBlockPos());
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
		TileEntityGrill tileentity = (TileEntityGrill) world.getTileEntity(pos);
		manager.clearEffects(world);
		manager.addBlockDestroyEffects(pos, Block.getBlockById(tileentity.getBlockID()).getStateFromMeta(tileentity.getBlockMeta()));

		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		TileEntity tileentity = (TileEntity) worldObj.getTileEntity(blockPosition);
		if (tileentity instanceof TileEntityGrill) {
			TileEntityGrill te = (TileEntityGrill) tileentity;
			((WorldServer) worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(Block.getBlockById(te.getBlockID()).getStateFromMeta(te.getBlockMeta())) });
		}
		return true;
	}
}
