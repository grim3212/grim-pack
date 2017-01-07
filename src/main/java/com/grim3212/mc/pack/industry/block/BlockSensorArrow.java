package com.grim3212.mc.pack.industry.block;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class BlockSensorArrow extends BlockManual {

	private int previousArrowId = 0;
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockSensorArrow() {
		super(Material.CLOTH, SoundType.CLOTH);
		this.setTickRandomly(true);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(POWERED, false));
	}

	@Override
	public int tickRate(World worldIn) {
		return 20;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (!worldIn.isRemote && entityIn != null && entityIn instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow) entityIn;

			// Make sure we aren't colliding with the same entity
			// This is because this gets called twice
			if (previousArrowId == entityIn.getEntityId()) {
				return;
			}
			previousArrowId = entityIn.getEntityId();

			try {
				// Get the ArrowStack using Reflection
				Method getArrowStack = ReflectionHelper.findMethod(EntityArrow.class, arrow, new String[] { "getArrowStack" }, (Class<?>[]) null);
				getArrowStack.setAccessible(true);
				Object o = getArrowStack.invoke(arrow, (Object[]) null);
				// Kill the arrow entity
				arrow.setDead();

				// Spawn in the new EntityItem and power the arrow sensor
				if (o != null && o instanceof ItemStack) {
					EntityItem entityitem = new EntityItem(worldIn, (float) entityIn.posX, (float) entityIn.posY, (float) entityIn.posZ, (ItemStack) o);
					worldIn.spawnEntity(entityitem);
					worldIn.scheduleUpdate(pos.toImmutable(), this, 20);
					worldIn.setBlockState(pos, this.getDefaultState().withProperty(POWERED, true));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(POWERED)) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, false));
		}
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return state.getValue(POWERED);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, meta == 1);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { POWERED });
	}

	@Override
	public Page getPage(IBlockState state) {
		return null;
	}

}
