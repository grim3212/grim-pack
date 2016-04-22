package com.grim3212.mc.tools.items;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.grim3212.mc.tools.config.ToolsConfig;
import com.grim3212.mc.tools.util.WandCoord3D;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ItemMiningWand extends ItemWand {

	public static ArrayList<Block> m_ores = new ArrayList<Block>();

	private static final int MINE_ALL = 100;
	private static final int MINE_DIRT = 10;
	private static final int MINE_WOOD = 1;
	private static final int MINE_ORES = 110;

	public ItemMiningWand(boolean reinforced) {
		super(reinforced);
		this.setMaxDamage(reinforced ? 120 : 15);
	}

	private static boolean isMiningOre(Block block) {
		return m_ores.contains(block);
	}

	@Override
	protected boolean canBreak(int keys, Block block) {
		switch (keys) {
		case MINE_ALL:
			return (block != Blocks.bedrock || ToolsConfig.ENABLE_bedrock_breaking) && (block != Blocks.obsidian || ToolsConfig.ENABLE_easy_mining_obsidian);
		case MINE_DIRT:
			return (block == Blocks.grass || block == Blocks.dirt || block == Blocks.sand || block == Blocks.gravel || block == Blocks.leaves || block == Blocks.farmland || block == Blocks.snow || block == Blocks.soul_sand || block == Blocks.vine || block instanceof BlockFlower);
		case MINE_WOOD:
			return block.getMaterial() == Material.wood;
		case MINE_ORES:
			return isMiningOre(block) && (block != Blocks.bedrock || ToolsConfig.ENABLE_bedrock_breaking) && (block != Blocks.obsidian || ToolsConfig.ENABLE_easy_mining_obsidian);
		}
		return false;
	}

	@Override
	protected boolean isTooFar(int range, int maxDiff, int range2D, int keys) {
		switch (keys) {
		case MINE_ALL:
		case MINE_DIRT:
			return range - 250 > maxDiff;
		case MINE_WOOD:
			return range2D - 400 > maxDiff;
		case MINE_ORES:
			return range2D - 60 > maxDiff;
		}
		return true;
	}

	@Override
	protected double[] getParticleColor() {
		return new double[] { 0.01D, 0.8D, 1.0D };
	}

	@Override
	protected boolean doEffect(World world, EntityPlayer entityplayer, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, Block block, int meta) throws Exception {
		// TODO: Probably fix this
		Field scheduleUpdatesField = ReflectionHelper.findField(World.class, "scheduledUpdatesAreImmediate", "field_72999_e");
		scheduleUpdatesField.setAccessible(true);

		if (ToolsConfig.disableNotify)
			scheduleUpdatesField.setBoolean(world, true);
		boolean damage = do_Mining(world, start, end, clicked, keys, entityplayer);
		if (damage)
			world.playSoundEffect(clicked.pos.getX(), clicked.pos.getY(), clicked.pos.getZ(), "random.explode", 2.5F, 0.5F + world.rand.nextFloat() * 0.3F);
		if (ToolsConfig.disableNotify)
			scheduleUpdatesField.setBoolean(world, false);
		return damage;
	}

	private boolean do_Mining(World world, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, EntityPlayer entityplayer) {
		int blocks2Dig = 0;

		BlockPos obsidianPos = new BlockPos(start.pos.getX(), start.pos.getY(), start.pos.getZ());
		if ((keys == MINE_ALL) && (start.pos.getX() == end.pos.getX()) && (start.pos.getY() == end.pos.getY()) && (start.pos.getZ() == end.pos.getZ()) && (!ToolsConfig.ENABLE_easy_mining_obsidian) && (world.getBlockState(obsidianPos).getBlock() == Blocks.obsidian)) {
			Blocks.obsidian.onBlockDestroyedByPlayer(world, obsidianPos, world.getBlockState(obsidianPos));
			Blocks.obsidian.harvestBlock(world, entityplayer, obsidianPos, world.getBlockState(obsidianPos), null);
			particles(world, obsidianPos, 1);

			return true;
		}

		if (keys == MINE_ORES) {
			for (int X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (int Y = 1; Y < 128; Y++) {
					for (int Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						Block blockAt = world.getBlockState(new BlockPos(X, Y, Z)).getBlock();
						if (isMiningOre(blockAt)) {
							if ((blockAt == Blocks.redstone_ore || blockAt == Blocks.lit_redstone_ore || blockAt == Blocks.lapis_ore))
								blocks2Dig += 4;
							else {
								blocks2Dig++;
							}
						}
					}
				}
			}

			int max = (this.reinforced) || (FREE) ? 1024 : 512;

			if (blocks2Dig - max > 10) {
				this.sendMessage(entityplayer, StatCollector.translateToLocal("error.wand.toomany") + " (" + blocks2Dig + ", " + StatCollector.translateToLocal("error.wand.toomany.limit") + " = " + max + ").", false);
				return true;
			}

			boolean underground = false;
			int surface = 127;
			long cnt = 0L;
			boolean surfaceBlock = false;
			for (int X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (int Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					underground = false;
					for (int Y = 127; Y > 1; Y--) {
						BlockPos newPos = new BlockPos(X, Y, Z);
						Block blockAt = world.getBlockState(newPos).getBlock();

						if ((!underground) && (world.isAirBlock(newPos))) {
							surface = Y;
						}

						surfaceBlock = isSurface(blockAt);

						if ((!underground) && (surfaceBlock)) {
							underground = true;
						}
						if (isMiningOre(blockAt)) {
							world.setBlockState(newPos, Blocks.stone.getDefaultState());

							BlockPos surfacePos = new BlockPos(X, surface, Z);
							blockAt.onBlockDestroyedByPlayer(world, surfacePos, world.getBlockState(newPos));
							blockAt.harvestBlock(world, entityplayer, surfacePos, world.getBlockState(newPos), null);
							cnt += 1L;
						}
					}
				}
			}

			if (cnt == 0L) {
				if (!world.isRemote)
					sendMessage(entityplayer, "result.wand.mine", true);
				return false;
			}
			return true;
		}

		for (int X = start.pos.getX(); X <= end.pos.getX(); X++) {
			for (int Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
				for (int Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					Block blockAt = world.getBlockState(new BlockPos(X, Y, Z)).getBlock();

					if ((blockAt != null) && (canBreak(keys, blockAt))) {
						if ((blockAt == Blocks.redstone_ore || blockAt == Blocks.lit_redstone_ore || blockAt == Blocks.lapis_ore))
							blocks2Dig += 4;
						else {
							blocks2Dig++;
						}
					}
				}
			}
		}
		if (blocks2Dig >= ((this.reinforced) || (FREE) ? 1024 : 512)) {
			this.sendMessage(entityplayer, StatCollector.translateToLocal("error.wand.toomany") + " (" + blocks2Dig + ", " + StatCollector.translateToLocal("error.wand.toomany.limit") + " = " + Integer.toString((this.reinforced) || (FREE) ? 1024 : 512) + ").", false);
			return false;
		}

		if (blocks2Dig == 0) {
			if (!world.isRemote)
				error(entityplayer, clicked, "nowork");
			return false;
		}

		for (int X = start.pos.getX(); X <= end.pos.getX(); X++) {
			for (int Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
				for (int Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					BlockPos newPos = new BlockPos(X, Y, Z);
					Block blockAt = world.getBlockState(newPos).getBlock();

					if ((blockAt != null) && (canBreak(keys, blockAt))) {
						world.setBlockToAir(newPos);
						blockAt.onBlockDestroyedByPlayer(world, newPos, world.getBlockState(newPos));
						blockAt.harvestBlock(world, entityplayer, newPos, world.getBlockState(newPos), null);
						if (this.rand.nextInt(blocks2Dig / 50 + 1) == 0)
							particles(world, newPos, 1);
					}
				}
			}
		}
		return true;
	}
}
