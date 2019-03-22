package com.grim3212.mc.pack.core.client;

import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.init.CoreInit;
import com.grim3212.mc.pack.core.init.CoreNames;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.manual.pages.PageItemStack;
import com.grim3212.mc.pack.core.util.RecipeHelper;

import net.minecraft.item.ItemStack;

public class ManualCore implements IManualPart {

	public static ManualCore INSTANCE = new ManualCore();

	public static Page info_page;
	public static Page instructionManual_page;

	public static Page ironStick_page;
	public static Page aluminum_page;
	public static Page aluminumShaft_page;
	public static Page aluminumCan_page;
	public static Page coalDust_page;
	public static Page rubber_page;
	public static Page graphite_page;
	public static Page graphiteRod_page;
	public static Page steelIngot_page;
	public static Page steelShaft_page;
	public static Page steelBlock_page;

	@Override
	public void initPages() {
		info_page = new PageInfo("howto");
		instructionManual_page = new PageCrafting("instructionmanual", RecipeHelper.getRecipePath(CoreNames.INSTRUCTION_MANUAL));

		if (CoreConfig.subpartIron.get())
			ironStick_page = new PageCrafting("iron_stick", RecipeHelper.getRecipePath(CoreNames.IRON_STICK)).setExtraInfo("grimpack.doc.core.common.iron_stick");

		if (CoreConfig.subpartAluminum.get()) {
			aluminum_page = new PageFurnace("aluminum", RecipeHelper.getRecipePath(CoreNames.ALUMINUM_INGOT));
			aluminumShaft_page = new PageCrafting("aluminum_shaft", RecipeHelper.getRecipePath(CoreNames.ALUMINUM_SHAFT));
			aluminumCan_page = new PageCrafting("aluminum_can", RecipeHelper.getRecipePath(CoreNames.ALUMINUM_CAN));
		}

		if (CoreConfig.subpartRubber.get())
			rubber_page = new PageItemStack("rubber", new ItemStack(CoreInit.rubber));

		if (CoreConfig.subpartIron.get())
			coalDust_page = new PageCrafting("coal_dust", RecipeHelper.getRecipePath(CoreNames.COAL_DUST));

		if (CoreConfig.subpartSteel.get()) {
			steelIngot_page = new PageItemStack("steel_ingot", new ItemStack(CoreInit.steel_ingot));
			steelShaft_page = new PageCrafting("steel_shaft", RecipeHelper.getRecipePath(CoreNames.STEEL_SHAFT));
			steelBlock_page = new PageCrafting("steel_block", RecipeHelper.getRecipePath(CoreNames.STEEL_BLOCK));
		}

		if (CoreConfig.subpartGraphite.get()) {
			graphite_page = new PageFurnace("graphite", RecipeHelper.getRecipePath(CoreNames.GRAPHITE));
			graphiteRod_page = new PageCrafting("graphite_rod", RecipeHelper.getRecipePath(CoreNames.GRAPHITE_ROD));
		}
	}

	@Override
	public void registerChapters(ManualPart modSection) {
		ManualRegistry.addChapter("im", modSection).addPages(info_page, instructionManual_page).addImageUrl("assets/grimpack/images/im.png");

		if (CoreConfig.subpartCoal.get() || CoreConfig.subpartIron.get() || CoreConfig.subpartGraphite.get() || CoreConfig.subpartRubber.get())
			ManualRegistry.addChapter("common", modSection).addPages(ironStick_page, coalDust_page, graphite_page, graphiteRod_page, rubber_page).setExtraInfo("grimpack.doc.core.common");

		if (CoreConfig.subpartAluminum.get())
			ManualRegistry.addChapter("aluminum", modSection).addPages(aluminum_page, aluminumShaft_page, aluminumCan_page);

		if (CoreConfig.subpartSteel.get())
			ManualRegistry.addChapter("steel", modSection).addPages(steelIngot_page, steelShaft_page, steelBlock_page);
	}

}
