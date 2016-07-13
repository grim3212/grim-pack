package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.entity.EntityAdvRayw;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemAdvRayg extends Item {

	public ItemAdvRayg() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.capabilities.isCreativeMode || Utils.consumeInventoryItem(playerIn, ToolsItems.advanced_energy_canister)) {
			worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), GrimTools.raygunSound, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				EntityAdvRayw ray = new EntityAdvRayw(worldIn, playerIn);
				ray.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.0F, 1.0F);
				worldIn.spawnEntityInWorld(ray);
			}
			if (!playerIn.capabilities.isCreativeMode)
				playerIn.inventory.addItemStackToInventory(new ItemStack(ToolsItems.advanced_empty_energy_canister));
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
}
