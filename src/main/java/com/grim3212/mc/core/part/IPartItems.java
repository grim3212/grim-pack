package com.grim3212.mc.core.part;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPartItems {

	public void initItems();

	public void addRecipes();

	@SideOnly(Side.CLIENT)
	public void renderItems();
}
