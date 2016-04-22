package com.grim3212.mc.tools.items;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.grim3212.mc.tools.config.ToolsConfig;
import com.grim3212.mc.tools.util.WandCoord3D;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ItemBreakingWand extends ItemWand {

	public static ArrayList<Block> ores = new ArrayList<Block>();

	private static final int BREAK_XORES = 100;
	private static final int BREAK_ALL = 10;
	private static final int BREAK_WEAK = 1;

	public ItemBreakingWand(boolean reinforced) {
		super(reinforced);
		this.setMaxDamage(reinforced ? 120 : 15);
	}

	protected static boolean isOre(Block id) {
		return ores.contains(id);
	}

	@Override
	protected boolean canBreak(int keys, Block block) {
		switch (keys) {
		case BREAK_XORES:
			return (block != Blocks.bedrock || ToolsConfig.ENABLE_bedrock_breaking) && !isOre(block);
		case BREAK_ALL:
			return (block != Blocks.bedrock || ToolsConfig.ENABLE_bedrock_breaking);
		case BREAK_WEAK:
			return (block == Blocks.leaves || block == Blocks.snow || block == Blocks.fire || block == Blocks.vine || block instanceof BlockFlower || block instanceof BlockCrops || block instanceof BlockLiquid);
		}
		return false;
	}

	@Override
	protected boolean isTooFar(int range, int maxDiff, int range2d, int keys) {
		return range - 250 > maxDiff;
	}

	@Override
	protected double[] getParticleColor() {
		return new double[] { 0.5D, 0.5D, 0.5D };
	}

	@Override
	protected boolean doEffect(World world, EntityPlayer entityplayer, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, Block block, int meta) throws Exception {
		// TODO: Probably change
		Field scheduleUpdatesField = ReflectionHelper.findField(World.class, "scheduledUpdatesAreImmediate", "field_72999_e");
		scheduleUpdatesField.setAccessible(true);

		if (ToolsConfig.disableNotify)
			scheduleUpdatesField.setBoolean(world, true);
		boolean damage = do_Breaking(world, start, end, clicked, keys, entityplayer);
		if (damage)
			world.playSoundEffect(end.pos.getX(), end.pos.getY(), end.pos.getZ(), "random.explode", 2.5F, 0.5F + world.rand.nextFloat() * 0.3F);
		if (ToolsConfig.disableNotify)
			scheduleUpdatesField.setBoolean(world, false);
		return damage;
	}

	private boolean do_Breaking(World world, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, EntityPlayer entityplayer) {
		int cnt = 0;
		for (int X = start.pos.getX(); X <= end.pos.getX(); X++) {
			for (int Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
				for (int Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					Block blockAt = world.getBlockState(new BlockPos(X, Y, Z)).getBlock();
					if ((blockAt != Blocks.air) && (canBreak(keys, blockAt))) {
						cnt++;
					}
				}
			}
		}

		if (cnt == 0) {
			if (!world.isRemote)
				error(entityplayer, clicked, "nowork");
			return false;
		}

		for (int X = start.pos.getX(); X <= end.pos.getX(); X++) {
			for (int Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
				for (int Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					BlockPos newPos = new BlockPos(X, Y, Z);
					Block blockAt = world.getBlockState(newPos).getBlock();

					if (blockAt != Blocks.air) {
						if (canBreak(keys, blockAt)) {
							blockAt.onBlockDestroyedByPlayer(world, newPos, world.getBlockState(newPos));
							world.playSoundEffect(clicked.pos.getX(), clicked.pos.getY(), clicked.pos.getZ(), "random.explode", 2.5F, 0.5F + world.rand.nextFloat() * 0.3F);
							world.setBlockToAir(newPos);
							if (this.rand.nextInt(cnt / 50 + 1) == 0)
								particles(world, newPos, 1);
						}
					}
				}
			}
		}
		return true;
	}
}
