package com.grim3212.mc.pack.decor.inventory;

import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotGrill extends Slot {

	private boolean isFuel = false;

	public SlotGrill(IInventory par2IInventory, int par3, int par4, int par5, boolean isFuel) {
		super(par2IInventory, par3, par4, par5);
		this.isFuel = isFuel;
	}

	@Override
	public int getSlotStackLimit() {
		if (!this.isFuel)
			return 1;
		return super.getSlotStackLimit();
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		if (this.isFuel)
			return (item.getItem() != null) && (item.getItem() == Items.COAL);

		return DecorConfig.grillRecipesContain(item);
	}
}