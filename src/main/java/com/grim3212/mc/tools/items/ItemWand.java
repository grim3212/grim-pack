package com.grim3212.mc.tools.items;

import java.util.Random;

import com.grim3212.mc.core.network.PacketDispatcher;
import com.grim3212.mc.core.util.NBTHelper;
import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.config.ToolsConfig;
import com.grim3212.mc.tools.util.WandCoord3D;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public abstract class ItemWand extends Item {

	protected boolean FREE = ToolsConfig.ENABLE_free_build_mode;
	protected boolean BEDROCK = ToolsConfig.ENABLE_bedrock_breaking;
	protected boolean OBSIDIAN = ToolsConfig.ENABLE_easy_mining_obsidian;
	protected Block id1;
	protected int meta1;
	public boolean reinforced;

	protected Random rand = new Random();

	private WandCoord3D clicked_start = new WandCoord3D();
	private WandCoord3D clicked_end = new WandCoord3D();
	private WandCoord3D clicked_current = new WandCoord3D();

	protected Block idOrig = Blocks.air;

	public ItemWand(boolean reinforced) {
		this.maxStackSize = 1;
		this.reinforced = reinforced;
		this.setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
	}

	protected abstract boolean canBreak(int keys, Block block);

	public ItemStack getNeededItem(Block block, int meta) {
		if (block == Blocks.leaves) {
			return new ItemStack(block, 1, meta & 3);
		} else if (block == Blocks.stone || block == Blocks.coal_ore || block == Blocks.clay || block == Blocks.deadbush || block == Blocks.bookshelf || block == Blocks.fire || block instanceof BlockStairs || block == Blocks.farmland || block == Blocks.diamond_ore || block == Blocks.lapis_ore || block instanceof BlockRedstoneOre || block == Blocks.glowstone || block == Blocks.ice || block == Blocks.snow || block == Blocks.stonebrick) {
			return new ItemStack(block, 1, meta);
		}
		return new ItemStack(block.getItemDropped(block.getStateFromMeta(meta), this.rand, 0), 1, block.damageDropped(block.getStateFromMeta(meta)));
	}

	public int getNeededCount(Block block, int meta) {
		if (block instanceof BlockSlab) {
			return 2;
		}
		return 1;
	}

	public boolean isSurface(Block blockAt) {
		return (blockAt == Blocks.dirt || blockAt == Blocks.grass || blockAt == Blocks.stone || blockAt == Blocks.gravel || blockAt == Blocks.sandstone || blockAt == Blocks.sand || blockAt == Blocks.bedrock || blockAt == Blocks.coal_ore || blockAt == Blocks.iron_ore || blockAt == Blocks.gold_ore || blockAt == Blocks.diamond_ore || blockAt == Blocks.lapis_ore);
	}

	protected abstract boolean isTooFar(int range, int maxDiff, int range2D, int keys);

	public boolean isTooFar(WandCoord3D a, WandCoord3D b, int keys) {
		if (FREE) {
			return a.getDistance(b) > 1500.0D;
		}
		return this.isTooFar((int) a.getDistance(b), 10, (int) a.getDistanceFlat(b), keys);
	}

	protected abstract boolean doEffect(World world, EntityPlayer entityplayer, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, Block block, int meta) throws Exception;

	protected void sendMessage(EntityPlayer player, String message, boolean autoTranslate) {
		if (!player.worldObj.isRemote) {
			if (autoTranslate)
				player.addChatComponentMessage(new ChatComponentTranslation(message));
			else
				player.addChatComponentMessage(new ChatComponentText(message));
		}

		if (player.worldObj.isRemote)
			PacketDispatcher.sendToServer(new MessageWandKeys(0, 0, 0));
	}

	protected void error(EntityPlayer entityplayer, WandCoord3D p, String reason) {
		if (entityplayer.worldObj.isRemote)
			PacketDispatcher.sendToServer(new MessageWandKeys(0, 0, 0));
		entityplayer.worldObj.playSoundEffect(p.pos.getX(), p.pos.getY(), p.pos.getZ(), "random.fizz", (entityplayer.worldObj.rand.nextFloat() + 0.7F) / 2.0F, 0.5F + entityplayer.worldObj.rand.nextFloat() * 0.3F);
		sendMessage(entityplayer, "error.wand." + reason, true);
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

			if ((l == 0) && (!world.getBlockState(pos.up()).getBlock().isOpaqueCube())) {
				d2 = pos.getY() + 1 + d;
			}
			if ((l == 1) && (!world.getBlockState(pos.down()).getBlock().isOpaqueCube())) {
				d2 = pos.getY() + 0 - d;
			}
			if ((l == 2) && (!world.getBlockState(pos.south()).getBlock().isOpaqueCube())) {
				d3 = pos.getZ() + 1 + d;
			}
			if ((l == 3) && (!world.getBlockState(pos.north()).getBlock().isOpaqueCube())) {
				d3 = pos.getZ() + 0 - d;
			}
			if ((l == 4) && (!world.getBlockState(pos.east()).getBlock().isOpaqueCube())) {
				d1 = pos.getX() + 1 + d;
			}
			if ((l == 5) && (!world.getBlockState(pos.west()).getBlock().isOpaqueCube())) {
				d1 = pos.getX() + 0 - d;
			}
			if ((d1 < pos.getX()) || (d1 > pos.getX() + 1) || (d2 < 0.0D) || (d2 > pos.getY() + 1) || (d3 < pos.getZ()) || (d3 > pos.getZ() + 1))
				world.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, R, G, B);
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		FREE = (ToolsConfig.ENABLE_free_build_mode) || (entityplayer.capabilities.isCreativeMode);
		this.clicked_current.setPos(pos);

		Block id = world.getBlockState(pos).getBlock();
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));

		this.idOrig = id;

		if (id == Blocks.grass) {
			id = Blocks.dirt;
		}
		if (id == Blocks.unlit_redstone_torch) {
			id = Blocks.redstone_torch;
		}
		if (id == Blocks.unpowered_repeater) {
			id = Blocks.powered_repeater;
		}

		if (isIncompatible(id)) {
			error(entityplayer, this.clicked_current, "cantbuild");
			return true;
		}

		int keys = KeybindHelper.getKeyCode();
		if (keys == 0) {
			world.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, id.stepSound.getBreakSound(), (id.stepSound.getVolume() + 1.0F) / 2.0F, id.stepSound.getFrequency() * 0.8F);

			this.meta1 = meta;
			this.id1 = id;
			this.clicked_start.setTo(this.clicked_current);

			particles(world, this.clicked_current, 0);

			NBTHelper.setBoolean(itemstack, "firstUse", false);
			return true;
		} else {
			this.clicked_end.setTo(this.clicked_current);

			if (NBTHelper.getBoolean(itemstack, "firstUse")) {
				error(entityplayer, this.clicked_current, "nostart");
				return true;
			}

			WandCoord3D Start = this.clicked_start.copy();
			WandCoord3D End = this.clicked_end.copy();
			WandCoord3D.findEnds(Start, End);

			if (isTooFar(Start, End, keys)) {
				error(entityplayer, this.clicked_end, "toofar");
				return true;
			}

			boolean damage = false;
			try {
				damage = this.doEffect(world, entityplayer, Start, End, clicked_current, keys, id, meta);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (damage) {
				if (world.isRemote)
					PacketDispatcher.sendToServer(new MessageWandKeys(0, 0, 0));
				NBTHelper.setBoolean(itemstack, "firstUse", true);
				if (!FREE) {
					itemstack.damageItem(1, entityplayer);
					return true;
				}
			}
		}

		return true;
	}

	protected boolean isIncompatible(Block id) {
		return false;
	}
}