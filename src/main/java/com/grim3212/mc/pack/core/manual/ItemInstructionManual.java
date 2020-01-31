package com.grim3212.mc.pack.core.manual;

import java.util.List;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.client.ClientUtil;
import com.grim3212.mc.pack.core.client.ManualCore;
import com.grim3212.mc.pack.core.init.CoreNames;
import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimItemGroups;

import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemInstructionManual extends ItemManual {

	public ItemInstructionManual() {
		super(CoreNames.INSTRUCTION_MANUAL, new Item.Properties().maxStackSize(1).group(GrimItemGroups.GRIM_CORE));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		if (worldIn.isRemote) {
			RayTraceResult raytrace = ClientUtil.getMouseOver();
			if (raytrace != null) {
				if (raytrace.getType() == Type.ENTITY) {
					EntityRayTraceResult entityResult = (EntityRayTraceResult) raytrace;

					if (entityResult.getEntity() != null) {
						if (entityResult.getEntity() instanceof IManualEntity) {
							updatePage(((IManualEntity) entityResult.getEntity()).getPage(entityResult.getEntity()));
						} else if (entityResult.getEntity() instanceof ItemEntity) {
							ItemEntity item = (ItemEntity) entityResult.getEntity();
							if (item.getItem().getItem() instanceof IManualItem) {
								updatePage(((IManualItem) item.getItem().getItem()).getPage(item.getItem()));
							}
						}
					}
				} else if (raytrace.getType() == Type.BLOCK) {
					BlockRayTraceResult blockResult = (BlockRayTraceResult) raytrace;
					if (worldIn.getBlockState(blockResult.getPos()).getBlock() instanceof IManualBlock) {
						updatePage(((IManualBlock) worldIn.getBlockState(blockResult.getPos()).getBlock()).getPage(worldIn.getBlockState(blockResult.getPos())));
					}
				}
			}

			GrimCore.proxy.openManual();
		}

		return ActionResult.func_226248_a_(playerIn.getHeldItem(hand));
	}

	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
		BlockState state = context.getWorld().getBlockState(context.getPos());
		if (state.getBlock() instanceof IManualBlock) {
			if (context.getWorld().isRemote) {
				updatePage(((IManualBlock) state.getBlock()).getPage(context.getWorld(), context.getPos(), state));

				GrimCore.proxy.openManual();
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	@OnlyIn(Dist.CLIENT)
	private void updatePage(@Nullable Page page) {
		if (page != null)
			GuiManualIndex.activeManualPage = page.getLink().copySelf();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent(I18n.format("tooltip.manual.number") + ManualRegistry.getLoadedParts().size()));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualCore.instructionManual_page;
	}
}
