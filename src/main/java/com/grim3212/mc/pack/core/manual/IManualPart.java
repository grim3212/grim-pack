package com.grim3212.mc.pack.core.manual;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IManualPart {

	public void initPages();

	public void registerChapters(ManualPart part);

}
