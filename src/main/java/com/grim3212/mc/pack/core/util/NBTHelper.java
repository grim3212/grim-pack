package com.grim3212.mc.pack.core.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class NBTHelper {

	public static boolean hasTag(ItemStack itemStack, String keyName) {
		return !itemStack.isEmpty() && itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey(keyName);
	}

	public static void removeTag(ItemStack itemStack, String keyName) {
		if (itemStack.getTagCompound() != null) {
			itemStack.getTagCompound().removeTag(keyName);
		}
	}

	/**
	 * Initializes the NBT Tag Compound for the given ItemStack if it is null
	 * 
	 * @param itemStack
	 *            The ItemStack for which its NBT Tag Compound is being checked
	 *            for initialization
	 */
	private static void initNBTTagCompound(ItemStack itemStack) {
		if (itemStack.getTagCompound() == null) {
			itemStack.setTagCompound(new NBTTagCompound());
		}
	}

	private static void initNBTTagCompound(NBTTagCompound compound) {
		if (compound == null) {
			compound = new NBTTagCompound();
		}
	}

	public static NBTTagCompound getTagCompound(NBTTagCompound compound, String keyName) {
		if (compound == null || !compound.hasKey(keyName)) {
			return new NBTTagCompound();
		}

		return compound.getCompoundTag(keyName);
	}

	public static NBTTagCompound getTagCompound(ItemStack stack, String keyName) {
		initNBTTagCompound(stack);

		if (!stack.getTagCompound().hasKey(keyName)) {
			setTagCompound(stack, keyName, new NBTTagCompound());
		}

		return stack.getTagCompound().getCompoundTag(keyName);
	}

	public static void setTagCompound(ItemStack stack, String keyName, NBTTagCompound compound) {
		initNBTTagCompound(stack);

		stack.getTagCompound().setTag(keyName, compound);
	}

	// =============== STRING ===============
	public static String getString(NBTTagCompound compound, String keyName) {
		initNBTTagCompound(compound);

		if (compound == null || !compound.hasKey(keyName)) {
			setString(compound, keyName, "");
		}

		return compound.getString(keyName);
	}

	public static void setString(NBTTagCompound compound, String keyName, String keyValue) {
		initNBTTagCompound(compound);

		compound.setString(keyName, keyValue);
	}

	public static String getString(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setString(itemStack, keyName, "");
		}

		return itemStack.getTagCompound().getString(keyName);
	}

	public static void setString(ItemStack itemStack, String keyName, String keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setString(keyName, keyValue);
	}

	public static ItemStack setStringItemStack(ItemStack itemStack, String keyName, String keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setString(keyName, keyValue);

		return itemStack;
	}
	// =============== END STRING ===============

	// =============== BOOLEAN ===============
	public static boolean getBoolean(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setBoolean(itemStack, keyName, false);
		}

		return itemStack.getTagCompound().getBoolean(keyName);
	}

	public static void setBoolean(ItemStack itemStack, String keyName, boolean keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setBoolean(keyName, keyValue);
	}

	public static ItemStack setBooleanItemStack(ItemStack itemStack, String keyName, boolean keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setBoolean(keyName, keyValue);

		return itemStack;
	}
	// =============== END BOOLEAN ===============

	// =============== BYTE ===============
	public static byte getByte(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setByte(itemStack, keyName, (byte) 0);
		}

		return itemStack.getTagCompound().getByte(keyName);
	}

	public static void setByte(ItemStack itemStack, String keyName, byte keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setByte(keyName, keyValue);
	}

	public static ItemStack setByteItemStack(ItemStack itemStack, String keyName, byte keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setByte(keyName, keyValue);

		return itemStack;
	}
	// =============== END BYTE ===============

	// =============== SHORT ===============
	public static short getShort(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setShort(itemStack, keyName, (short) 0);
		}

		return itemStack.getTagCompound().getShort(keyName);
	}

	public static void setShort(ItemStack itemStack, String keyName, short keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setShort(keyName, keyValue);
	}

	public static ItemStack setShortItemStack(ItemStack itemStack, String keyName, short keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setShort(keyName, keyValue);
		return itemStack;
	}
	// =============== END SHORT ===============

	// =============== INTEGER ===============
	public static int getInt(NBTTagCompound compound, String keyName) {
		initNBTTagCompound(compound);

		if (compound == null || !compound.hasKey(keyName)) {
			setInt(compound, keyName, 0);
		}

		return compound.getInteger(keyName);
	}

	public static void setInt(NBTTagCompound compound, String keyName, int keyValue) {
		initNBTTagCompound(compound);

		compound.setInteger(keyName, keyValue);
	}

	public static int getInt(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setInteger(itemStack, keyName, 0);
		}

		return itemStack.getTagCompound().getInteger(keyName);
	}

	public static int getInt(ItemStack itemStack, String keyName, int fallback) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setInteger(itemStack, keyName, fallback);
		}

		return itemStack.getTagCompound().getInteger(keyName);
	}

	public static void setInteger(ItemStack itemStack, String keyName, int keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setInteger(keyName, keyValue);
	}

	public static ItemStack setIntegerItemStack(ItemStack itemStack, String keyName, int keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setInteger(keyName, keyValue);

		return itemStack;
	}
	// =============== END INTEGER ===============

	// =============== LONG ===============
	public static long getLong(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setLong(itemStack, keyName, 0);
		}

		return itemStack.getTagCompound().getLong(keyName);
	}

	public static void setLong(ItemStack itemStack, String keyName, long keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setLong(keyName, keyValue);
	}

	public static ItemStack setLongItemStack(ItemStack itemStack, String keyName, long keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setLong(keyName, keyValue);

		return itemStack;
	}
	// =============== END LONG ===============

	// =============== FLOAT ===============
	public static float getFloat(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setFloat(itemStack, keyName, 0);
		}

		return itemStack.getTagCompound().getFloat(keyName);
	}

	public static void setFloat(ItemStack itemStack, String keyName, float keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setFloat(keyName, keyValue);
	}

	public static ItemStack setFloatItemStack(ItemStack itemStack, String keyName, float keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setFloat(keyName, keyValue);

		return itemStack;
	}
	// =============== END FLOAT ===============

	// =============== DOUBLE ===============
	public static double getDouble(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setDouble(itemStack, keyName, 0);
		}

		return itemStack.getTagCompound().getDouble(keyName);
	}

	public static void setDouble(ItemStack itemStack, String keyName, double keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setDouble(keyName, keyValue);
	}

	public static ItemStack setDoubleItemStack(ItemStack itemStack, String keyName, double keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setDouble(keyName, keyValue);

		return itemStack;
	}
	// =============== END DOUBLE ===============

	// =============== BLOCK POS ===============
	public static BlockPos getBlockPos(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);

		if (!itemStack.getTagCompound().hasKey(keyName)) {
			setBlockPos(itemStack, keyName, new BlockPos(0, 0, 0));
		}

		int[] pos = itemStack.getTagCompound().getIntArray(keyName);
		return new BlockPos(pos[0], pos[1], pos[2]);
	}

	public static BlockPos getBlockPos(NBTTagCompound tag, String keyName) {
		initNBTTagCompound(tag);

		if (!tag.hasKey(keyName)) {
			setBlockPos(tag, keyName, new BlockPos(0, 0, 0));
		}

		int[] pos = tag.getIntArray(keyName);
		return new BlockPos(pos[0], pos[1], pos[2]);
	}

	public static void setBlockPos(NBTTagCompound tag, String keyName, BlockPos keyValue) {
		initNBTTagCompound(tag);

		tag.setIntArray(keyName, new int[] { keyValue.getX(), keyValue.getY(), keyValue.getZ() });
	}

	public static void setBlockPos(ItemStack itemStack, String keyName, BlockPos keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setIntArray(keyName, new int[] { keyValue.getX(), keyValue.getY(), keyValue.getZ() });
	}

	public static ItemStack setBlockPosItemStack(ItemStack itemStack, String keyName, BlockPos keyValue) {
		initNBTTagCompound(itemStack);

		itemStack.getTagCompound().setIntArray(keyName, new int[] { keyValue.getX(), keyValue.getY(), keyValue.getZ() });

		return itemStack;
	}
	// =============== END BLOCK POS ===============
}