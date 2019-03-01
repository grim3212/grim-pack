package com.grim3212.mc.pack.core.manual;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IManualPart {

	public void initPages();

	public void registerChapters(ManualPart part);

}
