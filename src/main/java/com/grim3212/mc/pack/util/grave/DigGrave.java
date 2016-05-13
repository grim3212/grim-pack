package com.grim3212.mc.pack.util.grave;

import java.lang.reflect.Field;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.util.GrimUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockWallSign;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class DigGrave {

	private static IGraveSignInscription gsi = new GraveSignInscription();
	private static IGraveSignInscription gsi_modder;

	private static final Block[] _allowed_blocks = { Blocks.air, Blocks.stone, Blocks.grass, Blocks.dirt, Blocks.sapling, Blocks.water, Blocks.lava, Blocks.sand, Blocks.gravel, Blocks.log, Blocks.log2, Blocks.leaves, Blocks.leaves2, Blocks.sandstone, Blocks.web, Blocks.tallgrass, Blocks.deadbush, Blocks.yellow_flower, Blocks.red_flower, Blocks.brown_mushroom, Blocks.red_mushroom, Blocks.torch, Blocks.fire, Blocks.farmland, Blocks.wall_sign, Blocks.snow, Blocks.ice, Blocks.snow_layer,
			Blocks.cactus, Blocks.clay, Blocks.reeds, Blocks.pumpkin, Blocks.netherrack, Blocks.soul_sand, Blocks.brown_mushroom_block, Blocks.red_mushroom_block, Blocks.melon_block, Blocks.pumpkin_stem, Blocks.melon_stem, Blocks.vine, Blocks.mycelium, Blocks.waterlily, Blocks.nether_wart, Blocks.end_stone, Blocks.cocoa, Blocks.flower_pot, Blocks.carrots, Blocks.potatoes };

	public static void setGraveSignInscription(IGraveSignInscription gsi_m) throws Exception {
		if (gsi_modder == null) {
			GrimLog.info(GrimUtil.modName, "A mod set a new method to inscribe the grave sign.");
			gsi_modder = gsi_m;
		} else {
			GrimLog.error(GrimUtil.modName, "An other mod tries to set a new method to inscribe the grave sign.");
			throw new Exception();
		}
	}

	private static boolean permittedBlock(Block block) {
		for (int i = 0; i < _allowed_blocks.length; i++) {
			if (block == _allowed_blocks[i])
				return true;
		}

		return false;
	}

	private static int checkGround(World world, GraveBlocks tmp_grave) {
		int blocking_blocks = 0;

		if (!permittedBlock(world.getBlockState(tmp_grave.gravesign.pos).getBlock()))
			blocking_blocks++;
		if (!permittedBlock(world.getBlockState(tmp_grave.coffin_a.pos).getBlock()))
			blocking_blocks++;
		if (!permittedBlock(world.getBlockState(tmp_grave.coffin_b.pos).getBlock()))
			blocking_blocks++;

		return blocking_blocks;
	}

	private static final int getTotalXP(EntityPlayerMP player) {
		int total_xp = 0;
		int level = player.experienceLevel - 1;

		if ((player.experience < 0.0F) || (player.experience >= 1.0F)) {
			GrimLog.info(GrimUtil.modName, player.getName() + "'s grave: wrong value in the xp bar of '" + player.experience + "'");
		} else if (player.experienceLevel < 15)
			total_xp += (int) (player.experience * 17.0F + 0.5F);
		else if (player.experienceLevel < 30)
			total_xp += (int) (player.experience * (17 + (player.experienceLevel - 15) * 3) + 0.5F);
		else {
			total_xp += (int) (player.experience * (62 + (player.experienceLevel - 30) * 7) + 0.5F);
		}

		while (level >= 0) {
			if (level < 15)
				total_xp += 17;
			else if (level < 30)
				total_xp += 17 + (level - 15) * 3;
			else {
				total_xp += 62 + (level - 30) * 7;
			}
			level--;
		}

		return total_xp;
	}

	private static void fillCoffin(EntityPlayerMP player, TileEntityChest coffin_a, TileEntityChest coffin_b) {
		if ((coffin_a == null) || (coffin_b == null))
			return;

		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			if (i < 9) {
				coffin_b.setInventorySlotContents(i, player.inventory.mainInventory[i]);
			} else {
				coffin_a.setInventorySlotContents(i - 9, player.inventory.mainInventory[i]);
			}

			player.inventory.mainInventory[i] = null;
		}

		for (int i = 0; i < player.inventory.armorInventory.length; i++) {
			coffin_b.setInventorySlotContents(i + 9, player.inventory.armorInventory[i]);

			player.inventory.armorInventory[i] = null;
		}

		int total_xp = getTotalXP(player);

		int amount_of_flasks = total_xp / 11;
		int slot = 26;

		GrimLog.info(GrimUtil.modName, player.getName() + "'s grave: level='" + player.experienceLevel + "'; xp='" + player.experience + "'; total1='" + player.experienceTotal + "'; total2='" + total_xp + "'; flasks='" + amount_of_flasks + "'");

		while ((amount_of_flasks > 0) && (slot > 14)) {
			int stack_size = amount_of_flasks > 64 ? 64 : amount_of_flasks;

			coffin_b.setInventorySlotContents(slot, new ItemStack(Items.experience_bottle, stack_size));

			amount_of_flasks -= 64;
			slot--;
		}

		player.experienceLevel = 0;
		player.experienceTotal = 0;
		player.experience = 0.0F;
	}

	private static String trimString(String str, int positions) {
		if (positions <= 0)
			return new String("");

		if (str.length() <= positions)
			return str;

		return str.substring(0, positions);
	}

	private static void convertGraveGround(World world, BlockPos pos) {
		Block block_id = world.getBlockState(pos).getBlock();
		Block cover_block_id = Block.getBlockById(-1);

		if (block_id == Blocks.stone)
			cover_block_id = Blocks.cobblestone;
		else if (block_id == Blocks.grass)
			cover_block_id = Blocks.dirt;
		else if (block_id == Blocks.sandstone)
			cover_block_id = Blocks.sand;
		else if (block_id == Blocks.double_stone_slab)
			cover_block_id = Blocks.cobblestone;
		else if (block_id == Blocks.stone_slab)
			cover_block_id = Blocks.cobblestone;

		if (cover_block_id != Blocks.air) {
			world.setBlockState(pos, cover_block_id.getDefaultState(), 2);
		}
	}

	public static void digGrave(EntityPlayerMP player) {
		if (player.worldObj == null)
			return;

		GrimLog.info(GrimUtil.modName, "Dig grave for '" + player.getName() + "'");

		int player_view = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		GraveBlocks tmp_grave = new GraveBlocks((int) player.posX, (int) player.posY, (int) player.posZ, player_view);

		int blocking_blocks_0 = checkGround(player.worldObj, tmp_grave);

		if (blocking_blocks_0 != 0) {
			int rotation_yaw = MathHelper.wrapAngleTo180_float(player.rotationYaw) < 0.0F ? MathHelper.floor_double(MathHelper.wrapAngleTo180_float(player.rotationYaw) + 360.0D) : MathHelper.floor_double(MathHelper.wrapAngleTo180_float(player.rotationYaw));

			GraveBlocks tmp_grave_90_left = new GraveBlocks((int) player.posX, (int) player.posY, (int) player.posZ, player_view - 1 & 0x3);
			GraveBlocks tmp_grave_90_right = new GraveBlocks((int) player.posX, (int) player.posY, (int) player.posZ, player_view + 1 & 0x3);
			GraveBlocks tmp_grave_180 = new GraveBlocks((int) player.posX, (int) player.posY, (int) player.posZ, player_view + 2 & 0x3);

			int blocking_blocks_90_left = checkGround(player.worldObj, tmp_grave_90_left);
			int blocking_blocks_90_right = checkGround(player.worldObj, tmp_grave_90_right);
			int blocking_blocks_180 = checkGround(player.worldObj, tmp_grave_180);

			if (rotation_yaw % 90 < 45) {
				if (blocking_blocks_90_right == 0)
					tmp_grave = tmp_grave_90_right;
				else if (blocking_blocks_90_left == 0)
					tmp_grave = tmp_grave_90_left;
				else if (blocking_blocks_180 == 0)
					tmp_grave = tmp_grave_180;
				else if ((blocking_blocks_90_right < blocking_blocks_0) && (blocking_blocks_90_right <= blocking_blocks_90_left) && (blocking_blocks_90_right <= blocking_blocks_180)) {
					tmp_grave = tmp_grave_90_right;
				} else if ((blocking_blocks_90_left < blocking_blocks_0) && (blocking_blocks_90_left <= blocking_blocks_180)) {
					tmp_grave = tmp_grave_90_left;
				} else if (blocking_blocks_180 < blocking_blocks_0) {
					tmp_grave = tmp_grave_180;
				}

			} else if (blocking_blocks_90_left == 0)
				tmp_grave = tmp_grave_90_left;
			else if (blocking_blocks_90_right == 0)
				tmp_grave = tmp_grave_90_right;
			else if (blocking_blocks_180 == 0)
				tmp_grave = tmp_grave_180;
			else if ((blocking_blocks_90_left < blocking_blocks_0) && (blocking_blocks_90_left <= blocking_blocks_90_right) && (blocking_blocks_90_left <= blocking_blocks_180)) {
				tmp_grave = tmp_grave_90_left;
			} else if ((blocking_blocks_90_right < blocking_blocks_0) && (blocking_blocks_90_right <= blocking_blocks_180)) {
				tmp_grave = tmp_grave_90_right;
			} else if (blocking_blocks_180 < blocking_blocks_0) {
				tmp_grave = tmp_grave_180;
			}

		}

		GrimLog.info(GrimUtil.modName, player.getName() + "'s grave: set blocks");

		player.worldObj.setBlockState(tmp_grave.gravestone.pos, Blocks.quartz_block.getDefaultState().withProperty(BlockQuartz.VARIANT, BlockQuartz.EnumType.CHISELED), 2);
		player.worldObj.setBlockState(tmp_grave.graveplant.pos, Blocks.flower_pot.getDefaultState().withProperty(BlockFlowerPot.CONTENTS, BlockFlowerPot.EnumFlowerType.DEAD_BUSH).withProperty(BlockFlowerPot.LEGACY_DATA, 10), 2);
		player.worldObj.setBlockState(tmp_grave.gravesign.pos, Blocks.wall_sign.getDefaultState().withProperty(BlockWallSign.FACING, EnumFacing.getFront(tmp_grave.gravesign_dir)), 2);
		player.worldObj.setBlockState(tmp_grave.coffin_a.pos, Blocks.chest.getDefaultState(), 2);
		player.worldObj.setBlockState(tmp_grave.coffin_b.pos, Blocks.chest.getDefaultState(), 2);

		GrimLog.info(GrimUtil.modName, player.getName() + "'s grave: create sign");

		TileEntitySign sign = (TileEntitySign) player.worldObj.getTileEntity(tmp_grave.gravesign.pos);
		if (sign != null) {
			String[] text = gsi_modder == null ? gsi.getInscription(player) : gsi_modder.getInscription(player);

			if (gsi_modder == null) {
				text = gsi.getInscription(player);
			} else {
				text = gsi_modder.getInscription(player);

				if (text.length != 4) {
					GrimLog.error(GrimUtil.modName, "The by mod set method to inscribe the grave sign has an invalid return array size of '" + text.length + "', restore to default!");

					text = gsi.getInscription(player);
					gsi_modder = null;
				}
			}

			//Change the signs text using a bit of reflection
			try {
				IChatComponent[] newText = new IChatComponent[] { new ChatComponentText(trimString(text[0], 14)), new ChatComponentText(trimString(text[1], 14)), new ChatComponentText(trimString(text[2], 14)), new ChatComponentText(trimString(text[3], 14)) };
				Field signTextField = ReflectionHelper.findField(TileEntitySign.class, "signText", "field_145915_a");
				signTextField.setAccessible(true);
				signTextField.set(sign, newText);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		GrimLog.info(GrimUtil.modName, player.getName() + "'s grave: fill coffin");

		fillCoffin(player, (TileEntityChest) player.worldObj.getTileEntity(tmp_grave.coffin_a.pos), (TileEntityChest) player.worldObj.getTileEntity(tmp_grave.coffin_b.pos));

		convertGraveGround(player.worldObj, tmp_grave.graveground_a.pos);
		convertGraveGround(player.worldObj, tmp_grave.graveground_b.pos);

		GrimLog.info(GrimUtil.modName, player.getName() + "'s grave: grave was dug");
	}

	private static class GraveBlocks {

		public final DigGrave.BlockCoord gravestone;
		public final DigGrave.BlockCoord graveplant;
		public final DigGrave.BlockCoord gravesign;
		public final DigGrave.BlockCoord coffin_a;
		public final DigGrave.BlockCoord coffin_b;
		public final DigGrave.BlockCoord graveground_a;
		public final DigGrave.BlockCoord graveground_b;
		public final int gravesign_dir;

		public GraveBlocks(int x, int y, int z, int player_view) {
			int my_y = y < 3 ? 3 : y;

			switch (player_view) {
			case 1:
				this.gravestone = new DigGrave.BlockCoord(x, my_y, z);
				this.graveplant = new DigGrave.BlockCoord(x, my_y + 1, z);
				this.gravesign = new DigGrave.BlockCoord(x - 1, my_y, z);
				this.coffin_a = new DigGrave.BlockCoord(x - 2, my_y - 2, z);
				this.coffin_b = new DigGrave.BlockCoord(x - 1, my_y - 2, z);
				this.graveground_a = new DigGrave.BlockCoord(x - 1, my_y - 1, z);
				this.graveground_b = new DigGrave.BlockCoord(x - 2, my_y - 1, z);
				this.gravesign_dir = 4;
				break;
			case 2:
				this.gravestone = new DigGrave.BlockCoord(x, my_y, z);
				this.graveplant = new DigGrave.BlockCoord(x, my_y + 1, z);
				this.gravesign = new DigGrave.BlockCoord(x, my_y, z - 1);
				this.coffin_a = new DigGrave.BlockCoord(x, my_y - 2, z - 2);
				this.coffin_b = new DigGrave.BlockCoord(x, my_y - 2, z - 1);
				this.graveground_a = new DigGrave.BlockCoord(x, my_y - 1, z - 1);
				this.graveground_b = new DigGrave.BlockCoord(x, my_y - 1, z - 2);
				this.gravesign_dir = 2;
				break;
			case 3:
				this.gravestone = new DigGrave.BlockCoord(x, my_y, z);
				this.graveplant = new DigGrave.BlockCoord(x, my_y + 1, z);
				this.gravesign = new DigGrave.BlockCoord(x + 1, my_y, z);
				this.coffin_a = new DigGrave.BlockCoord(x + 1, my_y - 2, z);
				this.coffin_b = new DigGrave.BlockCoord(x + 2, my_y - 2, z);
				this.graveground_a = new DigGrave.BlockCoord(x + 1, my_y - 1, z);
				this.graveground_b = new DigGrave.BlockCoord(x + 2, my_y - 1, z);
				this.gravesign_dir = 5;
				break;
			default:
				this.gravestone = new DigGrave.BlockCoord(x, my_y, z);
				this.graveplant = new DigGrave.BlockCoord(x, my_y + 1, z);
				this.gravesign = new DigGrave.BlockCoord(x, my_y, z + 1);
				this.coffin_a = new DigGrave.BlockCoord(x, my_y - 2, z + 1);
				this.coffin_b = new DigGrave.BlockCoord(x, my_y - 2, z + 2);
				this.graveground_a = new DigGrave.BlockCoord(x, my_y - 1, z + 1);
				this.graveground_b = new DigGrave.BlockCoord(x, my_y - 1, z + 2);
				this.gravesign_dir = 3;
			}
		}
	}

	private static class BlockCoord {
		public final BlockPos pos;

		public BlockCoord(int new_x, int new_y, int new_z) {
			this.pos = new BlockPos(new_x, new_y, new_z);
		}
	}
}