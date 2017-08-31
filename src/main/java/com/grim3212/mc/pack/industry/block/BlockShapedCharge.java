package com.grim3212.mc.pack.industry.block;

import java.util.List;
import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockShapedCharge extends BlockManual {

	public static final PropertyInteger RADIUS = PropertyInteger.create("radius", 0, 15);

	public BlockShapedCharge() {
		super("shaped_charge", Material.TNT, SoundType.PLANT);
		this.setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
		this.setHardness(0.0f);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < 16; i++)
			items.add(new ItemStack(this, 1, i));
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		// tooltip.add(I18n.format("tooltip.shaped_charge.radius") +
		// stack.getMetadata());
	}

	@Override
	protected IBlockState getState() {
		return super.getState().withProperty(RADIUS, 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(RADIUS);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(RADIUS, meta);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { RADIUS });
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.shapedCharge_page;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return false;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);

		if (worldIn.isBlockPowered(pos)) {
			this.explode(worldIn, pos, state.getValue(RADIUS), true, (Explosion) null);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn.isBlockPowered(pos)) {
			this.explode(worldIn, pos, state.getValue(RADIUS), true, (Explosion) null);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (worldIn.getBlockState(pos).getBlock() == this)
			this.explode(worldIn, pos, worldIn.getBlockState(pos).getValue(RADIUS), false, explosionIn);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = playerIn.getHeldItem(hand);

		if (!itemstack.isEmpty() && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
			this.explode(worldIn, pos, state.getValue(RADIUS), true, (Explosion) null);
			worldIn.setBlockToAir(pos);

			if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
				itemstack.damageItem(1, playerIn);
			} else if (!playerIn.capabilities.isCreativeMode) {
				itemstack.shrink(1);
			}

			return true;
		} else {
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (!worldIn.isRemote && entityIn instanceof EntityArrow) {
			EntityArrow entityarrow = (EntityArrow) entityIn;

			if (entityarrow.isBurning()) {
				this.explode(worldIn, pos, state.getValue(RADIUS), false, (Explosion) null);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	protected void explode(World world, BlockPos pos, int radius, boolean reLight, Explosion explosion) {
		int yFinish = 0;
		for (int xPos = pos.getX() - radius; xPos <= pos.getX() + radius; xPos++) {
			for (int zPos = pos.getZ() - radius; zPos <= pos.getZ() + radius; zPos++) {
				for (int yPos = pos.getY() - 1; yPos > 0; yPos--) {
					BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
					IBlockState state = world.getBlockState(blockPos);

					// Set finish point for damaging entities
					yFinish = yPos;

					if (state.getBlock() != Blocks.AIR && (state.getBlock() != Blocks.BEDROCK || !(state.getBlock() instanceof BlockLiquid)) && (state.getBlock().getExplosionResistance(world, blockPos, null, explosion) < 7000.0F)) {
						state.getBlock().dropBlockAsItem(world, blockPos, state, 0);
						world.setBlockToAir(blockPos);
						state.getBlock().onBlockDestroyedByExplosion(world, blockPos, explosion);
					} else {
						if (state.getBlock() == Blocks.LAVA) {
							Blocks.OBSIDIAN.dropBlockAsItem(world, blockPos, state, 0);
							world.setBlockToAir(blockPos);
						} else if (state.getBlock() == Blocks.FLOWING_LAVA) {
							Blocks.COBBLESTONE.dropBlockAsItem(world, blockPos, state, 0);
							world.setBlockToAir(blockPos);
						} else if (state.getBlock() != Blocks.AIR && (state.getBlock() == Blocks.BEDROCK || state.getBlock().getExplosionResistance(world, blockPos, null, explosion) >= 7000.0F)) {
							break;
						}
					}

					handleLava(world, blockPos.east());
					handleLava(world, blockPos.west());
					handleLava(world, blockPos.south());
					handleLava(world, blockPos.north());
				}
			}
		}

		attackEntitiesInColumnWithinTolerance(world, pos, yFinish, radius, 0.5F, 10);
		attackEntitiesInColumnWithinTolerance(world, pos, yFinish, radius, 1.0F, 5);
		attackEntitiesInColumnWithinTolerance(world, pos, yFinish, radius, 1.5F, 5);
	}

	protected void dropItem(World world, BlockPos pos, Block blockType, int numToDrop) {
		float f1 = 0.7F;
		double d = world.rand.nextFloat() * f1 + (1.0F - f1) * 0.5D;
		double d1 = world.rand.nextFloat() * f1 + (1.0F - f1) * 0.5D + world.rand.nextInt(16);
		double d2 = world.rand.nextFloat() * f1 + (1.0F - f1) * 0.5D;
		if (!world.isRemote) {
			EntityItem entityitem = new EntityItem(world, pos.getX() + d, pos.getY() + d1, pos.getZ() + d2, new ItemStack(blockType, numToDrop, 0));
			entityitem.setDefaultPickupDelay();
			world.spawnEntity(entityitem);
		}
	}

	protected void attackEntitiesInColumnWithinTolerance(World world, BlockPos pos, int finishY, int radius, float tolerance, int damage) {
		List<Entity> list = world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos.getX() - radius + 0.5F - tolerance, finishY, pos.getZ() - radius + 0.5F - tolerance, pos.getX() + radius + 0.5F + tolerance, pos.getY(), pos.getZ() + radius + 0.5F + tolerance));

		for (Entity entity : list) {
			entity.attackEntityFrom(DamageSource.GENERIC.setExplosion(), damage);
		}
	}

	protected void handleLava(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == Blocks.LAVA) {
			world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
		} else if (state.getBlock() == Blocks.FLOWING_LAVA) {
			world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
		}
	}
}
