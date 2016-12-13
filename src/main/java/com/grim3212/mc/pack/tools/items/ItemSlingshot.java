package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntitySlingpellet;
import com.grim3212.mc.pack.tools.util.EnumPelletType;
import com.grim3212.mc.pack.tools.util.EnumSlingshotModes;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
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
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		String mode = NBTHelper.getString(stack, "Mode");
		tooltip.add(I18n.format("tooltip.slingshot.currentMode") + I18n.format("grimtools.slingshot." + mode));
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		NBTHelper.setString(stack, "Mode", EnumSlingshotModes.RANDOM.getUnlocalized());
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		ItemStack self = new ItemStack(itemIn, 1, 0);
		subItems.add(NBTHelper.setStringItemStack(self, "Mode", EnumSlingshotModes.RANDOM.getUnlocalized()));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		// Will set mode if there is no value
		EnumSlingshotModes mode = EnumSlingshotModes.getFromString(NBTHelper.getString(playerIn.getHeldItem(hand), "Mode"));

		if (playerIn.capabilities.isCreativeMode) {
			EnumPelletType ammo = this.getAmmo(mode, playerIn, worldIn, true);

			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				EntitySlingpellet pellet = new EntitySlingpellet(worldIn, playerIn, ammo);
				pellet.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.8F);
				worldIn.spawnEntity(pellet);
			}
			return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
		} else {

		}

		if (!playerIn.capabilities.isCreativeMode && !playerIn.inventory.hasItemStack(new ItemStack(ToolsItems.sling_pellet))) {
			return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
		} else {
			ItemStack stack = Utils.consumePlayerItem(playerIn, new ItemStack(ToolsItems.sling_pellet));
			EnumPelletType type = ItemSlingPellet.getPelletType(stack);

			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				EntitySlingpellet pellet = new EntitySlingpellet(worldIn, playerIn, type);
				pellet.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.8F);
				worldIn.spawnEntity(pellet);
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	public EnumPelletType getAmmo(EnumSlingshotModes mode, EntityPlayer player, World worldIn, boolean isCreative) {
		switch (mode) {
		case ALL:
			if (isCreative) {
				return EnumPelletType.values[worldIn.rand.nextInt(EnumPelletType.values.length)];
			}

			ItemStack stack = Utils.consumePlayerItem(player, new ItemStack(ToolsItems.sling_pellet), 1, true);
			return ItemSlingPellet.getPelletType(stack);
		case EXPLOSIVE:
			return EnumPelletType.EXPLOSION;
		case FIRE:
			return EnumPelletType.FIRE;
		case IRON:
			return EnumPelletType.IRON;
		case LIGHT:
			return EnumPelletType.LIGHT;
		case NETHERRACK:
			return EnumPelletType.NETHERRACK;
		case RANDOM:
			if (isCreative) {
				return EnumPelletType.values[worldIn.rand.nextInt(EnumPelletType.values.length)];
			}

			boolean[] foundTypes = new boolean[EnumPelletType.values.length];

			for (int i = 0; i < EnumPelletType.values.length; i++) {
				if (player.inventory.hasItemStack(new ItemStack(ToolsItems.sling_pellet, 1, i))) {
					foundTypes[i] = true;
				}
			}

			if (player.inventory.hasItemStack(new ItemStack(ToolsItems.pellet_bag))) {
				for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
					ItemStack foundBag = player.inventory.getStackInSlot(i);
					if (foundBag.getItem() == ToolsItems.pellet_bag) {
						for (int j = 0; j < EnumPelletType.values.length; j++) {
							if (Utils.hasItemHandler(foundBag)) {
								foundTypes[j] = !Utils.consumeHandlerItem(Utils.getItemHandler(foundBag), new ItemStack(ToolsItems.sling_pellet, 1, j), 1, true).isEmpty();
							}
						}
					}
				}
			}

			break;
		case STONE:
			return EnumPelletType.STONE;
		}
		return EnumPelletType.STONE;
	}
}
