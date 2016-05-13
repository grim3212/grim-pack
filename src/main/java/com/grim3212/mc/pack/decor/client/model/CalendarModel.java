package com.grim3212.mc.pack.decor.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CalendarModel extends ModelBase {
	
	public ModelRenderer calBoard;

	public CalendarModel() {
		calBoard = new ModelRenderer(this, "CalenderModel");
		calBoard.addBox(-6F, -18F, 0F, 12, 20, 2, 0.0F);
	}

	public void renderCalendar() {
		calBoard.render(0.0625F);
	}
}
