package com.grim3212.mc.pack.tools.items;

import java.util.ArrayList;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.util.WandCoord3D;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
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

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getItem() == ToolsItems.mining_wand)
			return ManualTools.regularWand_page;

		return ManualTools.reinforcedWand_page;
	}

	private static boolean isMiningOre(Block block) {
		return m_ores.contains(block);
	}

	@Override
	protected boolean canBreak(int keys, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);

		switch (keys) {
		case MINE_ALL:
			return (state.getBlock() != Blocks.BEDROCK || ToolsConfig.ENABLE_bedrock_breaking) && (state.getBlock() != Blocks.OBSIDIAN || ToolsConfig.ENABLE_easy_mining_obsidian);
		case MINE_DIRT:
			return (state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.SAND || state.getBlock() == Blocks.GRAVEL || state.getBlock() == Blocks.LEAVES || state.getBlock() == Blocks.LEAVES2 || state.getBlock() == Blocks.FARMLAND || state.getBlock() == Blocks.SNOW || state.getBlock() == Blocks.SOUL_SAND || state.getBlock() == Blocks.VINE || state.getBlock() instanceof BlockFlower);
		case MINE_WOOD:
			return state.getMaterial() == Material.WOOD;
		case MINE_ORES:
			return isMiningOre(state.getBlock()) && (state.getBlock() != Blocks.BEDROCK || ToolsConfig.ENABLE_bedrock_breaking) && (state.getBlock() != Blocks.OBSIDIAN || ToolsConfig.ENABLE_easy_mining_obsidian);
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
	protected boolean doEffect(World world, EntityPlayer entityplayer, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, IBlockState state) {
		boolean damage = do_Mining(world, start, end, clicked, keys, entityplayer);
		if (damage)
			world.playSound((EntityPlayer) null, clicked.pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.5F, 0.5F + world.rand.nextFloat() * 0.3F);
		return damage;
	}

	private boolean do_Mining(World world, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, EntityPlayer entityplayer) {
		// MINING OBSIDIAN 1x1x1
		BlockPos startPos = start.pos;
		BlockPos endPos = end.pos;
		if (keys == MINE_ALL && startPos.equals(endPos) && !ToolsConfig.ENABLE_easy_mining_obsidian) {
			IBlockState state = world.getBlockState(startPos);
			if (state.getBlock() == Blocks.OBSIDIAN) {
				state.getBlock().onBlockDestroyedByPlayer(world, startPos, state);
				state.getBlock().harvestBlock(world, entityplayer, startPos, state, world.getTileEntity(startPos), entityplayer.getActiveItemStack());
				particles(world, startPos, 1);
				return true;
			}
		}
		int X, Y, Z;
		IBlockState stateAt;
		int blocks2Dig = 0;
		boolean FREE = ToolsConfig.ENABLE_free_build_mode || entityplayer.capabilities.isCreativeMode;
		int max = (reinforced || FREE) ? 1024 : 512;
		// MINING ORES
		if (keys == MINE_ORES) {
			// counting ores to mine
			Iterable<BlockPos> iterable = BlockPos.getAllInBox(new BlockPos(start.pos.getX(), 1, start.pos.getZ()), new BlockPos(end.pos.getX(), 128, end.pos.getZ()));
			for (BlockPos pos : iterable) {
				stateAt = world.getBlockState(pos);
				if (isMiningOre(stateAt.getBlock())) {
					// add more drops for redstone and lapis
					if (stateAt.getBlock() == Blocks.REDSTONE_ORE || stateAt.getBlock() == Blocks.LIT_REDSTONE_ORE || stateAt.getBlock() == Blocks.LAPIS_ORE) {
						blocks2Dig += 4;
					} else {
						blocks2Dig++;
					}
				}
			}
			if (blocks2Dig - max > 10) {// 10 blocks tolerance
				this.sendMessage(entityplayer, new TextComponentString(I18n.translateToLocal("error.wand.toomany") + " (" + blocks2Dig + ", " + I18n.translateToLocal("error.wand.toomany.limit") + " = " + max + ")."));
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
						stateAt = world.getBlockState(pos);
						if (!underground && world.isAirBlock(pos)) {
							surface = Y;
						}
						surfaceBlock = isSurface(stateAt);
						if (!underground && surfaceBlock)
							underground = true;
						if (isMiningOre(stateAt.getBlock())) {
							TileEntity tile = world.getTileEntity(pos);
							if (world.setBlockState(pos, Blocks.STONE.getDefaultState())) {
								pos = new BlockPos(X, surface, Z);
								stateAt.getBlock().onBlockDestroyedByPlayer(world, pos, stateAt);
								stateAt.getBlock().harvestBlock(world, entityplayer, pos, stateAt, tile, entityplayer.getActiveItemStack());
								cnt++;
							}
						}
					}
				}
			}
			if (cnt == 0) {
				if (!world.isRemote)
					sendMessage(entityplayer, new TextComponentTranslation("result.wand.mine"));
				return false;
			}
			return true;
		}
		// NORMAL MINING
		Iterable<BlockPos> iterable = BlockPos.getAllInBox(startPos, endPos.up());
		for (BlockPos pos : iterable) {
			stateAt = world.getBlockState(pos);
			if (canBreak(keys, world, pos)) {
				// add more drops for redstone and lapis
				if (stateAt.getBlock() == Blocks.REDSTONE_ORE || stateAt.getBlock() == Blocks.LIT_REDSTONE_ORE || stateAt.getBlock() == Blocks.LAPIS_ORE) {
					blocks2Dig += 4;
				} else {
					blocks2Dig++;
				}
			}
		}
		if (blocks2Dig >= max) {
			this.sendMessage(entityplayer, new TextComponentString(I18n.translateToLocal("error.wand.toomany") + " (" + blocks2Dig + ", " + I18n.translateToLocal("error.wand.toomany.limit") + " = " + Integer.toString((this.reinforced) || (FREE) ? 1024 : 512) + ")."));
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
			BlockPos newPos = (BlockPos) object;
			stateAt = world.getBlockState(newPos);
			if (canBreak(keys, world, newPos)) {
				TileEntity tile = world.getTileEntity(newPos);
				if (stateAt.getBlock().removedByPlayer(stateAt, world, newPos, entityplayer, true)) {
					stateAt.getBlock().onBlockDestroyedByPlayer(world, newPos, stateAt);
					stateAt.getBlock().harvestBlock(world, entityplayer, newPos, stateAt, tile, entityplayer.getActiveItemStack());
					if (itemRand.nextInt(blocks2Dig / 50 + 1) == 0)
						particles(world, newPos, 1);
				}
			}
		}
		return true;
	}
}
