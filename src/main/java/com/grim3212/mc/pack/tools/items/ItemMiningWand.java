package com.grim3212.mc.pack.tools.items;

import java.util.ArrayList;

import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.util.WandCoord3D;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

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
	protected boolean doEffect(World world, EntityPlayer entityplayer, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, Block block, int meta) {
		boolean damage = do_Mining(world, start, end, clicked, keys, entityplayer);
		if (damage)
			world.playSoundEffect(clicked.pos.getX(), clicked.pos.getY(), clicked.pos.getZ(), "random.explode", 2.5F, 0.5F + world.rand.nextFloat() * 0.3F);
		return damage;
	}

	private boolean do_Mining(World world, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, EntityPlayer entityplayer) {
		// MINING OBSIDIAN 1x1x1
		BlockPos startPos = start.pos;
		BlockPos endPos = end.pos;
		if (keys == MINE_ALL && startPos.equals(endPos) && !ToolsConfig.ENABLE_easy_mining_obsidian) {
			IBlockState state = world.getBlockState(startPos);
			if (state.getBlock() == Blocks.obsidian) {
				Blocks.obsidian.onBlockDestroyedByPlayer(world, startPos, state);
				Blocks.obsidian.harvestBlock(world, entityplayer, startPos, state, world.getTileEntity(startPos));
				particles(world, startPos, 1);
				return true;
			}
		}
		int X, Y, Z;
		IBlockState metaAt;
		Block blockAt;
		int blocks2Dig = 0;
		boolean FREE = ToolsConfig.ENABLE_free_build_mode || entityplayer.capabilities.isCreativeMode;
		int max = (reinforced || FREE) ? 1024 : 512;
		// MINING ORES
		if (keys == MINE_ORES) {
			// counting ores to mine
			Iterable<BlockPos> iterable = BlockPos.getAllInBox(new BlockPos(start.pos.getX(), 1, start.pos.getZ()), new BlockPos(end.pos.getX(), 128, end.pos.getZ()));
			for (BlockPos pos : iterable) {
				blockAt = world.getBlockState(pos).getBlock();
				if (isMiningOre(blockAt)) {
					// add more drops for redstone and lapis
					if (blockAt == Blocks.redstone_ore || blockAt == Blocks.lit_redstone_ore || blockAt == Blocks.lapis_ore) {
						blocks2Dig += 4;
					} else {
						blocks2Dig++;
					}
				}
			}
			if (blocks2Dig - max > 10) {// 10 blocks tolerance
				this.sendMessage(entityplayer, StatCollector.translateToLocal("error.wand.toomany") + " (" + blocks2Dig + ", " + StatCollector.translateToLocal("error.wand.toomany.limit") + " = " + max + ").", false);
				return true;
			}
			// harvesting the ores
			boolean underground;
			int surface = 127;
			long cnt = 0;
			boolean surfaceBlock;
			BlockPos pos;
			for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					underground = false;
					for (Y = 127; Y > 1; Y--) {
						pos = new BlockPos(X, Y, Z);
						metaAt = world.getBlockState(pos);
						if (!underground && world.isAirBlock(pos)) {
							surface = Y;
						}
						surfaceBlock = isSurface(metaAt.getBlock());
						if (!underground && surfaceBlock)
							underground = true;
						if (isMiningOre(metaAt.getBlock())) {
							TileEntity tile = world.getTileEntity(pos);
							if (world.setBlockState(pos, Blocks.stone.getDefaultState())) {
								pos = new BlockPos(X, surface, Z);
								metaAt.getBlock().onBlockDestroyedByPlayer(world, pos, metaAt);
								metaAt.getBlock().harvestBlock(world, entityplayer, pos, metaAt, tile);
								cnt++;
							}
						}
					}
				}
			}
			if (cnt == 0) {
				if (!world.isRemote)
					sendMessage(entityplayer, "result.wand.mine", true);
				return false;
			}
			return true;
		}
		// NORMAL MINING
		Iterable<BlockPos> iterable = BlockPos.getAllInBox(startPos, endPos.up());
		for (BlockPos pos : iterable) {
			blockAt = world.getBlockState(pos).getBlock();
			if (canBreak(keys, blockAt)) {
				// add more drops for redstone and lapis
				if (blockAt == Blocks.redstone_ore || blockAt == Blocks.lit_redstone_ore || blockAt == Blocks.lapis_ore) {
					blocks2Dig += 4;
				} else {
					blocks2Dig++;
				}
			}
		}
		if (blocks2Dig >= max) {
			this.sendMessage(entityplayer, StatCollector.translateToLocal("error.wand.toomany") + " (" + blocks2Dig + ", " + StatCollector.translateToLocal("error.wand.toomany.limit") + " = " + Integer.toString((this.reinforced) || (FREE) ? 1024 : 512) + ").", false);
			return false;
		}
		// now the mining itself.
		if (blocks2Dig == 0) {
			if (!world.isRemote)
				error(entityplayer, clicked, "nowork");
			return false;
		}
		iterable = BlockPos.getAllInBox(startPos, endPos.up());
		for (Object object : iterable) {
			metaAt = world.getBlockState((BlockPos) object);
			if (canBreak(keys, metaAt.getBlock())) {
				TileEntity tile = world.getTileEntity((BlockPos) object);
				if (metaAt.getBlock().removedByPlayer(world, (BlockPos) object, entityplayer, true)) {
					metaAt.getBlock().onBlockDestroyedByPlayer(world, (BlockPos) object, metaAt);
					metaAt.getBlock().harvestBlock(world, entityplayer, (BlockPos) object, metaAt, tile);
					if (itemRand.nextInt(blocks2Dig / 50 + 1) == 0)
						particles(world, (BlockPos) object, 1);
				}
			}
		}
		return true;
	}
}
