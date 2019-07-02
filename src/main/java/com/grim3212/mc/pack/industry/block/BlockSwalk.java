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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockSwalk extends BlockManual {

	public BlockSwalk() {
		super(IndustryNames.SIDEWALK, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.0f, 15.0f));
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		double maxSpeed = 1.5D;
		double speedMultiplier = 2.0D;

		if (entityIn instanceof PlayerEntity) {
			Vec3d entMot = entityIn.getMotion();
			double speedUp = Math.sqrt(entMot.x * entMot.x + entMot.z * entMot.x);

			if (speedUp != 0.0D) {
				double var12 = speedUp * speedMultiplier;
				var12 = Math.min(var12, maxSpeed);
				entMot.mul(var12 / speedUp, 1.0D, var12 / speedUp);
			}
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.sidewalk_page;
	}
}
