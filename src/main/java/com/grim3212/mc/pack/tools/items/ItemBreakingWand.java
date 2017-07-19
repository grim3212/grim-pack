package com.grim3212.mc.pack.tools.items;

import java.util.Map;

import com.google.common.collect.Maps;
import com.grim3212.mc.pack.core.config.ConfigUtils;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.util.WandCoord3D;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBreakingWand extends ItemWand {

	public static Map<IBlockState, Boolean> ores = Maps.newHashMap();

	private static final int BREAK_XORES = 100;
	private static final int BREAK_ALL = 10;
	private static final int BREAK_WEAK = 1;

	public ItemBreakingWand(boolean reinforced) {
		super(reinforced ? "reinforced_breaking_wand" : "breaking_wand", reinforced);
		this.setMaxDamage(reinforced ? 120 : 15);
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getItem() == ToolsItems.breaking_wand)
			return ManualTools.regularWand_page;

		return ManualTools.reinforcedWand_page;
	}

	protected static boolean isOre(IBlockState state) {
		return ConfigUtils.isStateFound(ores, state);
	}

	@Override
	protected boolean canBreak(int keys, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);

		switch (keys) {
		case BREAK_XORES:
			return (state.getBlock() != Blocks.BEDROCK || ToolsConfig.ENABLE_bedrock_breaking) && !isOre(state);
		case BREAK_ALL:
			return (state.getBlock() != Blocks.BEDROCK || ToolsConfig.ENABLE_bedrock_breaking);
		case BREAK_WEAK:
			return (state.getBlock() == Blocks.LEAVES || state.getBlock() == Blocks.LEAVES2 || state.getBlock() == Blocks.SNOW || state.getBlock() == Blocks.FIRE || state.getBlock() == Blocks.VINE || state.getBlock() instanceof BlockFlower || state.getBlock() instanceof BlockCrops || state.getBlock() instanceof BlockLiquid);
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
	protected boolean doEffect(World world, EntityPlayer entityplayer, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, IBlockState state) {
		boolean damage = do_Breaking(world, start, end, clicked, keys, entityplayer);
		if (damage)
			world.playSound((EntityPlayer) null, end.pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, 0.5F + world.rand.nextFloat() * 0.3F);
		return damage;
	}

	private boolean do_Breaking(World world, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, EntityPlayer entityplayer) {
		int cnt = 0;
		for (int X = start.pos.getX(); X <= end.pos.getX(); X++) {
			for (int Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
				for (int Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					BlockPos newPos = new BlockPos(X, Y, Z);
					IBlockState stateAt = world.getBlockState(newPos);
					if ((stateAt.getBlock() != Blocks.AIR) && (canBreak(keys, world, newPos))) {
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
					IBlockState stateAt = world.getBlockState(newPos);

					if (stateAt.getBlock() != Blocks.AIR) {
						if (canBreak(keys, world, newPos)) {
							stateAt.getBlock().onBlockDestroyedByPlayer(world, newPos, stateAt);
							world.playSound((EntityPlayer) null, clicked.pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.5F, 0.5F + world.rand.nextFloat() * 0.3F);
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
