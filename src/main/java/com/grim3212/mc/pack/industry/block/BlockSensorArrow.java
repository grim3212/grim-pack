package com.grim3212.mc.pack.industry.block;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class BlockSensorArrow extends BlockManual {

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
		if (entityIn != null && entityIn instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow) entityIn;

			// TODO: Test this
			try {
				Method getArrowStack = ReflectionHelper.findMethod(EntityArrow.class, arrow, new String[] { "getArrowStack", "" }, (Class<?>[]) null);
				getArrowStack.setAccessible(true);
				Object o = getArrowStack.invoke(arrow, (Object[]) null);

				if (o != null && o instanceof ItemStack) {
					System.out.println(((ItemStack) o).toString());
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
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	@Override
	public Page getPage(IBlockState state) {
		return null;
	}

}
