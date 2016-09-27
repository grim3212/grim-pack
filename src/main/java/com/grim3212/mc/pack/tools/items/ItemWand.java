package com.grim3212.mc.pack.tools.items;

import java.util.Random;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.util.WandCoord3D;

import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public abstract class ItemWand extends ItemManual {

	protected boolean FREE = ToolsConfig.ENABLE_free_build_mode;
	protected boolean BEDROCK = ToolsConfig.ENABLE_bedrock_breaking;
	protected boolean OBSIDIAN = ToolsConfig.ENABLE_easy_mining_obsidian;
	public boolean reinforced;
	protected Random rand = new Random();

	protected IBlockState stateOrig = Blocks.AIR.getDefaultState();
	protected IBlockState stateClicked = Blocks.AIR.getDefaultState();

	public ItemWand(boolean reinforced) {
		this.maxStackSize = 1;
		this.reinforced = reinforced;
		this.setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
	}

	protected abstract boolean canBreak(int keys, World worldIn, BlockPos pos);

	public ItemStack getNeededItem(IBlockState state) {
		if (state.getBlock() == Blocks.LEAVES || state.getBlock() == Blocks.LEAVES2) {
			return new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
		} else if (state.getBlock() == Blocks.STONE || state.getBlock() == Blocks.COAL_ORE || state.getBlock() == Blocks.CLAY || state.getBlock() == Blocks.DEADBUSH || state.getBlock() == Blocks.BOOKSHELF || state.getBlock() == Blocks.FIRE || state.getBlock() instanceof BlockStairs || state.getBlock() == Blocks.FARMLAND || state.getBlock() == Blocks.DIAMOND_ORE || state.getBlock() == Blocks.LAPIS_ORE || state.getBlock() instanceof BlockRedstoneOre || state.getBlock() == Blocks.GLOWSTONE || state.getBlock() == Blocks.ICE || state.getBlock() == Blocks.SNOW
				|| state.getBlock() == Blocks.STONEBRICK) {
			return new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state));
		}
		return new ItemStack(state.getBlock().getItemDropped(state, this.rand, 0), 1, state.getBlock().damageDropped(state));
	}

	public int getNeededCount(IBlockState state) {
		if (state.getBlock() instanceof BlockSlab) {
			return 2;
		}
		return 1;
	}

	public boolean isSurface(IBlockState state) {
		return (state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.STONE || state.getBlock() == Blocks.GRAVEL || state.getBlock() == Blocks.SANDSTONE || state.getBlock() == Blocks.SAND || state.getBlock() == Blocks.BEDROCK || state.getBlock() == Blocks.COAL_ORE || state.getBlock() == Blocks.IRON_ORE || state.getBlock() == Blocks.GOLD_ORE || state.getBlock() == Blocks.DIAMOND_ORE || state.getBlock() == Blocks.LAPIS_ORE);
	}

	protected abstract boolean isTooFar(int range, int maxDiff, int range2D, int keys);

	public boolean isTooFar(WandCoord3D a, WandCoord3D b, int keys) {
		if (FREE) {
			return a.getDistance(b) > 1500.0D;
		}
		return this.isTooFar((int) a.getDistance(b), 10, (int) a.getDistanceFlat(b), keys);
	}

	protected abstract boolean doEffect(World world, EntityPlayer entityplayer, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, IBlockState state);

	protected void sendMessage(EntityPlayer player, ITextComponent message) {
		if (!player.worldObj.isRemote) {
			player.addChatComponentMessage(message);
		}
	}

	protected void error(EntityPlayer entityplayer, WandCoord3D p, String reason) {
		entityplayer.worldObj.playSound(entityplayer, p.pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, (entityplayer.worldObj.rand.nextFloat() + 0.7F) / 2.0F, 0.5F + entityplayer.worldObj.rand.nextFloat() * 0.3F);
		sendMessage(entityplayer, new TextComponentTranslation("error.wand." + reason));
		particles(entityplayer.worldObj, p.pos, 3);
	}

	protected abstract double[] getParticleColor();

	private void particles(World world, WandCoord3D c, int effect) {
		particles(world, c.pos, effect);
	}

	protected void particles(World world, BlockPos pos, int effect) {
		double d = 0.0625D;

		if (effect == 1) {
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
			return;
		}
		if (effect == 2) {
			world.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
			return;
		}

		double R = 0.0D;
		double G = 0.0D;
		double B = 0.0D;

		if (effect == 0) {
			double[] color = getParticleColor();
			R = color[0];
			G = color[1];
			B = color[2];
		} else {
			R = 0.8D;
		}

		for (int l = 0; l < 6; l++) {
			double d1 = pos.getX() + this.rand.nextFloat();
			double d2 = pos.getY() + this.rand.nextFloat();
			double d3 = pos.getZ() + this.rand.nextFloat();

			if ((l == 0) && (!world.getBlockState(pos.up()).isOpaqueCube())) {
				d2 = pos.getY() + 1 + d;
			}
			if ((l == 1) && (!world.getBlockState(pos.down()).isOpaqueCube())) {
				d2 = pos.getY() + 0 - d;
			}
			if ((l == 2) && (!world.getBlockState(pos.south()).isOpaqueCube())) {
				d3 = pos.getZ() + 1 + d;
			}
			if ((l == 3) && (!world.getBlockState(pos.north()).isOpaqueCube())) {
				d3 = pos.getZ() + 0 - d;
			}
			if ((l == 4) && (!world.getBlockState(pos.east()).isOpaqueCube())) {
				d1 = pos.getX() + 1 + d;
			}
			if ((l == 5) && (!world.getBlockState(pos.west()).isOpaqueCube())) {
				d1 = pos.getX() + 0 - d;
			}
			if ((d1 < pos.getX()) || (d1 > pos.getX() + 1) || (d2 < 0.0D) || (d2 > pos.getY() + 1) || (d3 < pos.getZ()) || (d3 > pos.getZ() + 1))
				world.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, R, G, B);
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		FREE = (ToolsConfig.ENABLE_free_build_mode) || (playerIn.capabilities.isCreativeMode);

		this.stateOrig = worldIn.getBlockState(pos);
		IBlockState state = this.stateOrig;

		if (state.getBlock() == Blocks.GRASS) {
			state = Blocks.DIRT.getDefaultState();
		}
		if (state.getBlock() == Blocks.UNLIT_REDSTONE_TORCH) {
			state = Blocks.REDSTONE_TORCH.getDefaultState();
		}
		if (state.getBlock() == Blocks.UNPOWERED_REPEATER) {
			state = Blocks.POWERED_REPEATER.getDefaultState();
		}

		WandCoord3D clicked_current = new WandCoord3D(pos, state);

		if (isIncompatible(state)) {
			error(playerIn, clicked_current, "cantbuild");
			return EnumActionResult.SUCCESS;
		}

		int keys = NBTHelper.getInt(stack, "keys");
		if (keys == 0) {
			worldIn.playSound((EntityPlayer) null, pos, state.getBlock().getSoundType(state, worldIn, pos, null).getBreakSound(), SoundCategory.BLOCKS, (state.getBlock().getSoundType(state, worldIn, pos, null).getVolume() + 1.0F) / 2.0F, state.getBlock().getSoundType(state, worldIn, pos, null).getPitch() * 0.8F);

			this.stateClicked = state;
			clicked_current.writeToNBT(stack.getTagCompound(), "Start");

			particles(worldIn, clicked_current, 0);

			NBTHelper.setBoolean(stack, "firstUse", false);
			return EnumActionResult.SUCCESS;
		} else {
			clicked_current.writeToNBT(stack.getTagCompound(), "End");

			if (NBTHelper.getBoolean(stack, "firstUse")) {
				error(playerIn, clicked_current, "nostart");
				return EnumActionResult.SUCCESS;
			}

			WandCoord3D start = WandCoord3D.getFromNBT(stack.getTagCompound(), "Start");
			WandCoord3D end = WandCoord3D.getFromNBT(stack.getTagCompound(), "End");
			WandCoord3D.findEnds(start, end);

			if (isTooFar(start, end, keys)) {
				error(playerIn, end, "toofar");
				return EnumActionResult.SUCCESS;
			}

			boolean damage = this.doEffect(worldIn, playerIn, start, end, clicked_current, keys, state);

			if (damage) {
				NBTHelper.setBoolean(stack, "firstUse", true);
				if (!FREE) {
					stack.damageItem(1, playerIn);
					return EnumActionResult.SUCCESS;
				}
			}
		}

		return EnumActionResult.SUCCESS;
	}

	protected boolean isIncompatible(IBlockState state) {
		return false;
	}
}