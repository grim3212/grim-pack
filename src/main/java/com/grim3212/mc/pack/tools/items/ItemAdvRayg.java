package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityAdvRayw;
import com.grim3212.mc.pack.tools.init.ToolsSounds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemAdvRayg extends ItemManual {

	public ItemAdvRayg() {
		super("advanced_ray_gun");
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.advRaygun_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.capabilities.isCreativeMode || !Utils.consumePlayerItem(playerIn, new ItemStack(ToolsItems.advanced_energy_canister)).isEmpty()) {
			worldIn.playSound((EntityPlayer) null, playerIn.getPosition(), ToolsSounds.raygunSound, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				EntityAdvRayw ray = new EntityAdvRayw(worldIn, playerIn);
				ray.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.0F, 1.0F);
				worldIn.spawnEntity(ray);
			}
			if (!playerIn.capabilities.isCreativeMode)
				playerIn.inventory.addItemStackToInventory(new ItemStack(ToolsItems.advanced_empty_energy_canister));
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}
}
