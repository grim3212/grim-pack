package com.grim3212.mc.pack.core.client;

import com.grim3212.mc.pack.core.common.CommonItems;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.CoreItems;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.manual.pages.PageItemStack;

import net.minecraft.init.Items;
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
		instructionManual_page = new PageCrafting("instructionmanual", new ItemStack(CoreItems.instruction_manual));

		if (CoreConfig.subpartIron)
			ironStick_page = new PageCrafting("iron_stick", new ItemStack(CommonItems.iron_stick)).setExtraInfo("grimpack.doc.core.common.iron_stick");

		if (CoreConfig.subpartAluminum) {
			aluminum_page = new PageFurnace("aluminum", new ItemStack(CommonItems.aluminum_ore));
			aluminumShaft_page = new PageCrafting("aluminum_shaft", new ItemStack(CommonItems.aluminum_shaft));
			aluminumCan_page = new PageCrafting("aluminum_can", new ItemStack(CommonItems.aluminum_can));
		}

		if (CoreConfig.subpartRubber)
			rubber_page = new PageItemStack("rubber", new ItemStack(CommonItems.rubber));

		if (CoreConfig.subpartIron)
			coalDust_page = new PageCrafting("coal_dust", new ItemStack(CommonItems.coal_dust));

		if (CoreConfig.subpartSteel) {
			steelIngot_page = new PageItemStack("steel_ingot", new ItemStack(CommonItems.steel_ingot));
			steelShaft_page = new PageCrafting("steel_shaft", new ItemStack(CommonItems.steel_shaft));
			steelBlock_page = new PageCrafting("steel_block", new ItemStack(CommonItems.steel_block));
		}

		if (CoreConfig.subpartGraphite) {
			graphite_page = new PageFurnace("graphite", new ItemStack(Items.FLINT));
			graphiteRod_page = new PageCrafting("graphite_rod", new ItemStack(CommonItems.graphite_rod));
		}
	}

	@Override
	public void registerChapters(ManualPart modSection) {
		ManualRegistry.addChapter("im", modSection).addPages(info_page, instructionManual_page).addImageUrl("assets/grimpack/images/im.png");

		if (CoreConfig.subpartCoal || CoreConfig.subpartIron || CoreConfig.subpartGraphite || CoreConfig.subpartRubber)
			ManualRegistry.addChapter("common", modSection).addPages(ironStick_page, coalDust_page, graphite_page, graphiteRod_page, rubber_page).setExtraInfo("grimpack.doc.core.common");

		if (CoreConfig.subpartAluminum)
			ManualRegistry.addChapter("aluminum", modSection).addPages(aluminum_page, aluminumShaft_page, aluminumCan_page);

		if (CoreConfig.subpartSteel)
			ManualRegistry.addChapter("steel", modSection).addPages(steelIngot_page, steelShaft_page, steelBlock_page);
	}

}
