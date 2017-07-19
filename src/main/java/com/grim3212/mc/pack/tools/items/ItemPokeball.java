package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityPokeball;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemPokeball extends ItemEgg implements IManualItem {

	public ItemPokeball() {
		this.setMaxStackSize(1);
		this.setMaxDamage(10);
		this.setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
		this.setUnlocalizedName("pokeball");
		this.setRegistryName(new ResourceLocation(GrimPack.modID, "pokeball"));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.pokeball_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);

		if (!worldIn.isRemote) {
			EntityPokeball pokeball = new EntityPokeball(worldIn, playerIn, itemStackIn.copy());
			pokeball.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.spawnEntity(pokeball);
		}

		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!playerIn.capabilities.isCreativeMode) {
			itemStackIn.shrink(1);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("name")) {
				tooltip.add(I18n.format("tooltip.pokeball.stored") + stack.getTagCompound().getString("name"));
			} else {
				tooltip.add(I18n.format("tooltip.pokeball.stored") + stack.getTagCompound().getString("id"));
			}
		} else {
			tooltip.add(I18n.format("tooltip.pokeball.stored") + I18n.format("tooltip.pokeball.empty"));
		}
	}
}
