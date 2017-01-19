package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class BlockConveyorBelt extends BlockManual {

	public static final PropertyInteger TIME = PropertyInteger.create("time", 0, 6);
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockConveyorBelt() {
		super(Material.IRON, SoundType.METAL);
		this.blockState.getBaseState().withProperty(TIME, 0).withProperty(FACING, EnumFacing.NORTH);
	}

	@Override
	public Page getPage(IBlockState state) {
		return null;
	}

}
