package com.grim3212.mc.pack.tools.compat.jei;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.tools.inventory.ContainerCustomWorkbench;

import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.Slot;

public class PortableWorkbenchTransferInfo implements IRecipeTransferInfo<ContainerCustomWorkbench> {

	@Override
	public Class<ContainerCustomWorkbench> getContainerClass() {
		return ContainerCustomWorkbench.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return VanillaRecipeCategoryUid.CRAFTING;
	}

	@Override
	public List<Slot> getRecipeSlots(ContainerCustomWorkbench container) {
		List<Slot> slots = new ArrayList<Slot>();
		for (int i = 1; i < 10; i++) {
			slots.add(container.getSlot(i));
		}
		return slots;
	}

	@Override
	public List<Slot> getInventorySlots(ContainerCustomWorkbench container) {
		List<Slot> slots = new ArrayList<Slot>();
		for (int i = 10; i < container.inventorySlots.size(); i++) {
			slots.add(container.getSlot(i));
		}
		return slots;
	}

	@Override
	public boolean canHandle(ContainerCustomWorkbench container) {
		return true;
	}

}