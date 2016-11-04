package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntitySlingpellet;
import com.grim3212.mc.pack.tools.util.EnumPelletType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemSlingshot extends ItemManual {

	public ItemSlingshot() {
		this.maxStackSize = 1;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.slingshot_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (!playerIn.capabilities.isCreativeMode && !playerIn.inventory.hasItemStack(new ItemStack(ToolsItems.sling_pellet))) {
			return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		} else {
			ItemStack stack = Utils.consumeInventoryItemStack(playerIn, ToolsItems.sling_pellet);
			EnumPelletType type = ItemSlingPellet.getPelletType(stack);

			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				EntitySlingpellet pellet = new EntitySlingpellet(worldIn, playerIn, type);
				pellet.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.8F);
				worldIn.spawnEntityInWorld(pellet);
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
}
