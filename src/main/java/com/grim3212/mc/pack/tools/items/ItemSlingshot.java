package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntitySlingpellet;
<<<<<<< HEAD
import com.grim3212.mc.pack.tools.entity.EntityFireSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntityExplosiveSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntityLightSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntityNetherrackSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntitySlimeSlingpellet;
import com.grim3212.mc.pack.tools.util.EnumSlingshotType;
import com.grim3212.mc.pack.tools.util.EnumSpearType;
=======
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemSlingshot extends ItemManual {
<<<<<<< HEAD
	
	private EnumSlingshotType type;

	public ItemSlingshot(EnumSlingshotType type) {
		this.maxStackSize = 1;
		this.type = type;
	}
	
	public EnumSlingshotType getType() {
		return type;
=======

	public ItemSlingshot() {
		this.maxStackSize = 1;
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
	}

	@Override
	public Page getPage(ItemStack stack) {
<<<<<<< HEAD
		//if (stack.getItem() == ToolsItems.sling_shot || stack.getItem() == ToolsItems.iron_slingshot || stack.getItem() == ToolsItems.black_diamond_slingshot)
		 return ManualTools.slingshot_page;
		
		//return ManualTools.specialSlingshots_page;
=======
		return ManualTools.slingshot_page;
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (!playerIn.capabilities.isCreativeMode && !playerIn.inventory.hasItemStack(new ItemStack(ToolsItems.sling_pellet))) {
			return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
		} else {
			Utils.consumeInventoryItem(playerIn, ToolsItems.sling_pellet);
			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				EntitySlingpellet pellet = new EntitySlingpellet(worldIn, playerIn);
				pellet.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.8F);
				worldIn.spawnEntityInWorld(pellet);
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
}
<<<<<<< HEAD

//Do I need MAINHAND stuff as in spear?
=======
>>>>>>> 22fd8b1d8d5d5162d98e857979c97722f5731c37
