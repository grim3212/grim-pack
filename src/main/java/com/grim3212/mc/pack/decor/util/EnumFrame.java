package com.grim3212.mc.pack.decor.util;

public enum EnumFrame {
	
	frame_01(1, 32, 32, new int[] { 0, 1, 2, 3 }, true), 
	frame_19(19, 32, 32, new int[] { 35, 36, 37 }, false), 
	frame_07(7, 64, 32, new int[] { 10, 11, 12, 13 }, true), 
	frame_10(10, 64, 32, new int[] { 14, 15, 10, 11, 12, 13 }, true), 
	frame_11(11, 64, 32, new int[] { 16, 17, 10, 11, 12, 13 }, true), 
	frame_37(37, 48, 32, new int[] { 10, 11, 12, 13 }, true), 
	frame_12(12, 48, 32, new int[] { 14, 15, 10, 11, 12, 13 }, true), 
	frame_13(13, 48, 32, new int[] { 16, 17, 10, 11, 12, 13 }, true), 
	frame_14(14, 32, 32, new int[] { 0, 1, 2, 3, 4, 5 }, true), 
	frame_02(2, 32, 32, new int[] { 0, 1, 2, 3, 4 }, true), 
	frame_03(3, 32, 32, new int[] { 0, 1, 2, 3, 5 }, true), 
	frame_04(4, 32, 32, new int[] { 6, 7, 0, 1, 2, 3 }, true), 
	frame_05(5, 32, 32, new int[] { 8, 9, 0, 1, 2, 3 }, true), 
	frame_06(6, 32, 32, new int[] { 6, 7, 8, 9, 0, 1, 2, 3 }, true), 
	frame_08(8, 32, 32, new int[] { 18, 0, 3 }, true), 
	frame_09(9, 32, 32, new int[] { 19, 0, 1 }, true), 
	frame_23(23, 32, 32, new int[] { 40, 1, 2 }, true), 
	frame_24(24, 32, 32, new int[] { 41, 2, 3 }, true), 
	frame_31(31, 32, 32, new int[] { 54, 55 }, true), 
	frame_32(32, 32, 32, new int[] { 56, 57 }, true), 
	frame_39(39, 32, 32, new int[] { 54, 55, 56, 57 }, true), 
	frame_15(15, 16, 16, new int[] { 20, 21, 22, 23 }, true), 
	frame_16(16, 32, 16, new int[] { 24, 25, 26, 27 }, true), 
	frame_17(17, 16, 32, new int[] { 28, 29, 30, 31 }, true), 
	frame_18(18, 16, 32, new int[] { 32, 33, 34 }, false), 
	frame_40(40, 16, 32, new int[] { 46, 47 }, true), 
	frame_41(41, 16, 32, new int[] { 48, 49 }, true), 
	frame_42(42, 16, 32, new int[] { 46, 47, 48, 49 }, true), 
	frame_29(29, 16, 16, new int[] { 46, 47 }, true), 
	frame_30(30, 16, 16, new int[] { 48, 49 }, true), 
	frame_38(38, 16, 16, new int[] { 46, 47, 48, 49 }, true), 
	frame_25(25, 16, 16, new int[] { 42, 20, 23 }, true), 
	frame_28(28, 16, 16, new int[] { 45, 22, 23 }, true), 
	frame_26(26, 16, 16, new int[] { 44, 20, 21 }, true), 
	frame_27(27, 16, 16, new int[] { 43, 21, 22 }, true), 
	frame_20(20, 16, 16, new int[] { 38 }, true), 
	frame_21(21, 16, 16, new int[] { 39 }, true), 
	frame_22(22, 16, 16, new int[] { 38, 39 }, true), 
	frame_33(33, 16, 16, new int[] { 50 }, false), 
	frame_35(35, 16, 16, new int[] { 52 }, false), 
	frame_34(34, 16, 16, new int[] { 51 }, false), 
	frame_36(36, 16, 16, new int[] { 53 }, false);

	public static final int maxArtTitleLength = "frame_01".length();
	public final int id;
	public final int sizeX;
	public final int sizeY;
	public final int[] planks;
	public final boolean isCollidable;

	private EnumFrame(int id, int x, int y, int[] planks, boolean collidable) {
		this.id = id;
		this.sizeX = x;
		this.sizeY = y;
		this.planks = planks;
		this.isCollidable = collidable;
	}

	public static EnumFrame getFrameById(int id) {
		for (int i = 0; i < EnumFrame.values().length; i++) {
			if (EnumFrame.values()[i].id == id)
				return EnumFrame.values()[i];
		}

		return null;
	}
}