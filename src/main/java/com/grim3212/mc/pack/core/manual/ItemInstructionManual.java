package com.grim3212.mc.pack.core.manual;

import java.util.List;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.client.ClientUtil;
import com.grim3212.mc.pack.core.client.ManualCore;
import com.grim3212.mc.pack.core.init.CoreNames;
import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemInstructionManual extends ItemManual {

	public ItemInstructionManual() {
		super(CoreNames.INSTRUCTION_MANUAL, new Item.Properties().maxStackSize(1).group(GrimItemGroups.GRIM_CORE));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (worldIn.isRemote) {
			RayTraceResult raytrace = ClientUtil.getMouseOver();
			if (raytrace.type == Type.ENTITY) {
				if (raytrace.entity != null) {
					if (raytrace.entity instanceof IManualEntity) {
						updatePage(((IManualEntity) raytrace.entity).getPage(raytrace.entity));
					} else if (raytrace.entity instanceof EntityItem) {
						EntityItem item = (EntityItem) raytrace.entity;
						if (item.getItem().getItem() instanceof IManualItem) {
							updatePage(((IManualItem) item.getItem().getItem()).getPage(item.getItem()));
						}
					}
				}
			}

			Minecraft.getInstance().displayGuiScreen(GuiManualIndex.activeManualPage);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, ItemUseContext context) {
		IBlockState state = context.getWorld().getBlockState(context.getPos());
		if (state.getBlock() instanceof IManualBlock) {
			if (context.getWorld().isRemote) {
				updatePage(((IManualBlock) state.getBlock()).getPage(context.getWorld(), context.getPos(), state));

				Minecraft.getInstance().displayGuiScreen(GuiManualIndex.activeManualPage);
			}

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.PASS;
	}

	@OnlyIn(Dist.CLIENT)
	private void updatePage(@Nullable Page page) {
		if (page != null)
			GuiManualIndex.activeManualPage = page.getLink().copySelf();
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentString(I18n.format("tooltip.manual.number") + ManualRegistry.getLoadedParts().size()));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualCore.instructionManual_page;
	}
}
