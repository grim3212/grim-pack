package com.grim3212.mc.pack.util.grave;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.util.init.UtilBlocks;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class PlaceGrave {

	private static IGraveSignInscription gsi = new GraveSignInscription();
	private static IGraveSignInscription gsi_modder;

	public static void setGraveSignInscription(IGraveSignInscription gsi_m) throws Exception {
		if (gsi_modder == null) {
			GrimLog.info(GrimUtil.partName, "A mod set a new method to inscribe the grave sign.");
			gsi_modder = gsi_m;
		} else {
			GrimLog.error(GrimUtil.partName, "Another mod tried to set a new method to inscribe the grave sign.");
			throw new Exception();
		}
	}

	private static final int getTotalXP(EntityPlayerMP player) {
		int total_xp = 0;
		int level = player.experienceLevel - 1;

		if ((player.experience < 0.0F) || (player.experience >= 1.0F)) {
			GrimLog.info(GrimUtil.partName, player.getName() + "'s grave: wrong value in the xp bar of '" + player.experience + "'");
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

	private static void fillGrave(EntityPlayerMP player, TileEntityGrave grave) {
		if (grave == null)
			return;

		for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
			grave.setInventorySlotContents(i, player.inventory.mainInventory.get(i).copy());
			player.inventory.mainInventory.set(i, ItemStack.EMPTY);
		}

		for (int i = 0; i < player.inventory.armorInventory.size(); i++) {
			grave.setInventorySlotContents(i + player.inventory.mainInventory.size(), player.inventory.armorInventory.get(i).copy());
			player.inventory.armorInventory.set(i, ItemStack.EMPTY);
		}

		for (int i = 0; i < player.inventory.offHandInventory.size(); i++) {
			grave.setInventorySlotContents(i + player.inventory.mainInventory.size() + player.inventory.armorInventory.size(), player.inventory.offHandInventory.get(i).copy());
			player.inventory.offHandInventory.set(i, ItemStack.EMPTY);
		}

		// Baubles inventory
		if (GrimUtil.baubles) {
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);

			for (int i = 0; i < baubles.getSlots(); ++i) {
				grave.setInventorySlotContents(i + player.inventory.mainInventory.size() + player.inventory.armorInventory.size() + player.inventory.offHandInventory.size(), baubles.getStackInSlot(i).copy());
				baubles.setStackInSlot(i, ItemStack.EMPTY);
			}

		}

		int total_xp = getTotalXP(player);

		int amount_of_flasks = total_xp / 11;
		int slot = 53;

		while ((amount_of_flasks > 0) && (slot > 40)) {
			int stack_size = amount_of_flasks > 64 ? 64 : amount_of_flasks;

			grave.setInventorySlotContents(slot, new ItemStack(Items.EXPERIENCE_BOTTLE, stack_size));

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

	public static void placeGrave(EntityPlayerMP player) {
		if (player.world == null)
			return;

		GrimLog.info(GrimUtil.partName, "Dig grave for '" + player.getName() + "'");

		BlockPos pos = null;

		if (player.world.getBlockState(player.getPosition()).getBlock() == Blocks.AIR || player.world.getBlockState(player.getPosition()).getBlock().isReplaceable(player.world, player.getPosition())) {
			player.world.setBlockState(player.getPosition(), UtilBlocks.grave.getDefaultState().withProperty(BlockHorizontal.FACING, player.getHorizontalFacing()));
			pos = player.getPosition();
		} else {
			// Try to find a new location for the grave
			for (EnumFacing facing : EnumFacing.VALUES) {
				if (player.world.getBlockState(player.getPosition().offset(facing)).getBlock() == Blocks.AIR || player.world.getBlockState(player.getPosition().offset(facing)).getBlock().isReplaceable(player.world, player.getPosition().offset(facing))) {
					player.world.setBlockState(player.getPosition().offset(facing), UtilBlocks.grave.getDefaultState().withProperty(BlockHorizontal.FACING, player.getHorizontalFacing()));
					pos = player.getPosition().offset(facing);
					break;
				}
			}
		}

		if (pos != null) {
			TileEntityGrave grave = (TileEntityGrave) player.world.getTileEntity(pos);
			if (grave != null) {
				String[] text = gsi_modder == null ? gsi.getInscription(player) : gsi_modder.getInscription(player);

				if (gsi_modder == null) {
					text = gsi.getInscription(player);
				} else {
					text = gsi_modder.getInscription(player);

					if (text.length != 4) {
						GrimLog.error(GrimUtil.partName, "The by mod set method to inscribe the grave sign has an invalid return array size of '" + text.length + "', restore to default!");

						text = gsi.getInscription(player);
						gsi_modder = null;
					}
				}

				// Set sign text
				grave.signText = new ITextComponent[] { new TextComponentString(trimString(text[0], 14)), new TextComponentString(trimString(text[1], 14)), new TextComponentString(trimString(text[2], 14)), new TextComponentString(trimString(text[3], 14)) };
			}

			fillGrave(player, (TileEntityGrave) player.world.getTileEntity(pos));
			// Trigger grave achievement
			// player.addStat(UtilAchievements.DIE);
		} else {
			GrimLog.info(GrimUtil.partName, "Could not find valid position for grave!");
		}
	}
}