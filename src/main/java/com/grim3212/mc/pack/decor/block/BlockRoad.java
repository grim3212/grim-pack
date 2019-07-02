package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockRoad extends BlockManual {

	public BlockRoad() {
		super(DecorNames.ROAD, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(0.5f, 10f));
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		double maxSpeed = 1.5D;
		double speedMultiplier = 2.0D;

		if (entityIn instanceof PlayerEntity) {
			Vec3d entMot = entityIn.getMotion();
			double speedUp = Math.sqrt(entMot.x * entMot.x + entMot.z * entMot.z);

			if (speedUp != 0.0D) {
				double var12 = speedUp * speedMultiplier;
				var12 = Math.min(var12, maxSpeed);

				entityIn.setMotion(entMot.mul(var12 / speedUp, 1f, var12 / speedUp));
			}
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.road_page;
	}
}
