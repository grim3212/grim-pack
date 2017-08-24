package com.grim3212.mc.pack.compat.jei.transfers;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.industry.inventory.ContainerDiamondWorkbench;

import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.Slot;

public class DiamondWorkbenchTransferInfo implements IRecipeTransferInfo<ContainerDiamondWorkbench> {

	@Override
	public Class<ContainerDiamondWorkbench> getContainerClass() {
		return ContainerDiamondWorkbench.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return VanillaRecipeCategoryUid.CRAFTING;
	}

	@Override
	public List<Slot> getRecipeSlots(ContainerDiamondWorkbench container) {
		List<Slot> slots = new ArrayList<Slot>();
		for (int i = 1; i < 10; i++) {
			slots.add(container.getSlot(i));
		}
		return slots;
	}

	@Override
	public List<Slot> getInventorySlots(ContainerDiamondWorkbench container) {
		List<Slot> slots = new ArrayList<Slot>();
		for (int i = 10; i < container.inventorySlots.size(); i++) {
			slots.add(container.getSlot(i));
		}
		return slots;
	}

	@Override
	public boolean canHandle(ContainerDiamondWorkbench container) {
		return true;
	}

}
