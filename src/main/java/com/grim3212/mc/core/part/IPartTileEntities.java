package com.grim3212.mc.core.part;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPartTileEntities {

	public void initTileEntities();

	@SideOnly(Side.CLIENT)
	public void renderTileEntities();
	
}
