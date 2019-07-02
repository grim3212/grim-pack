package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class BlockFuel extends BlockManual {

	protected BlockFuel() {
		super(IndustryNames.FUEL_TANK, Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(1.0f, 5.0f));
	}

	@Override
	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isRemote)
			worldIn.createExplosion((Entity) null, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true, Mode.BREAK);
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.fuel_page;
	}
}
