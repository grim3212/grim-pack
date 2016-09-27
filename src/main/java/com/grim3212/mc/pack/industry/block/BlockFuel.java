package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockFuel extends BlockManual {

	protected BlockFuel() {
		super(Material.IRON, SoundType.METAL);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);

		if (!worldIn.isRemote)
			worldIn.createExplosion((Entity) null, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.fuel_page;
	}
}
