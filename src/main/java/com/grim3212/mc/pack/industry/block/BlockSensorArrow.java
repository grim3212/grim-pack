package com.grim3212.mc.pack.industry.block;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class BlockSensorArrow extends BlockManual {

	private int previousArrowId = 0;
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockSensorArrow() {
		super("arrow_sensor", Material.CLOTH, SoundType.CLOTH);
		this.setTickRandomly(true);
		setHardness(0.3F);
		setResistance(2.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockState getState() {
		return this.getBlockState().getBaseState().withProperty(POWERED, false);
	}

	@Override
	public int tickRate(World worldIn) {
		return 20;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, BlockState state, Entity entityIn) {
		if (!worldIn.isRemote && entityIn != null && entityIn instanceof AbstractArrowEntity) {
			AbstractArrowEntity arrow = (AbstractArrowEntity) entityIn;

			// Make sure we aren't colliding with the same entity
			// This is because this gets called twice
			if (previousArrowId == entityIn.getEntityId()) {
				return;
			}
			previousArrowId = entityIn.getEntityId();

			try {
				// Get the ArrowStack using Reflection
				Method getArrowStack = ReflectionHelper.findMethod(AbstractArrowEntity.class, "getArrowStack", "func_184550_j", (Class<?>[]) null);
				getArrowStack.setAccessible(true);
				Object o = getArrowStack.invoke(arrow, (Object[]) null);
				// Kill the arrow entity
				arrow.setDead();

				// Spawn in the new EntityItem and power the arrow sensor
				if (o != null && o instanceof ItemStack) {
					ItemEntity entityitem = new ItemEntity(worldIn, (float) entityIn.posX, (float) entityIn.posY, (float) entityIn.posZ, (ItemStack) o);
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
	public void updateTick(World worldIn, BlockPos pos, BlockState state, Random rand) {
		if (state.getValue(POWERED)) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, false));
		}
	}

	@Override
	public int getWeakPower(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public int getStrongPower(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public boolean canProvidePower(BlockState state) {
		return state.getValue(POWERED);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, meta == 1);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { POWERED });
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.arrowSensor_page;
	}

}
