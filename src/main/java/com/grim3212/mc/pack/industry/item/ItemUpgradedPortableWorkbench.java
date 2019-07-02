package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.tools.items.ItemPortableWorkbench;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemUpgradedPortableWorkbench extends ItemPortableWorkbench {

	private final int MAIN_GUI_ID;
	private final int OFF_GUI_ID;

	public ItemUpgradedPortableWorkbench(int mainGui, int offGui) {
		super(mainGui == PackGuiHandler.PORTABLE_IRON_MAIN_GUI_ID ? "portable_iron_workbench" : "portable_diamond_workbench");
		this.MAIN_GUI_ID = mainGui;
		this.OFF_GUI_ID = offGui;
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		if (!worldIn.isRemote) {
			if (hand == Hand.MAIN_HAND)
				playerIn.openGui(GrimPack.INSTANCE, MAIN_GUI_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			else
				playerIn.openGui(GrimPack.INSTANCE, OFF_GUI_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		}

		return ActionResult.newResult(ActionResultType.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.portableUpgrade_page;
	}
}
