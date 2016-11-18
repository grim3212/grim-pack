package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityRayw;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemRayg extends ItemManual {

	public ItemRayg() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.raygun_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.capabilities.isCreativeMode || Utils.consumePlayerItem(playerIn, new ItemStack(ToolsItems.energy_canister)) != null) {
			worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), GrimTools.raygunSound, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				EntityRayw ray = new EntityRayw(worldIn, playerIn);
				ray.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.5F, 0.0F);
				worldIn.spawnEntityInWorld(ray);
			}
			if (!playerIn.capabilities.isCreativeMode)
				playerIn.inventory.addItemStackToInventory(new ItemStack(ToolsItems.empty_energy_canister));
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
}
