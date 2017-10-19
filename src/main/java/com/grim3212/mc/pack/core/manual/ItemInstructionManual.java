package com.grim3212.mc.pack.core.manual;

import java.util.List;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.ClientUtil;
import com.grim3212.mc.pack.core.client.ManualCore;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemInstructionManual extends ItemManual {

	public ItemInstructionManual() {
		super("instruction_manual");
		this.setMaxStackSize(1);
		this.setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (worldIn.isRemote) {
			RayTraceResult raytrace = ClientUtil.getMouseOver();
			if (raytrace.typeOfHit == Type.ENTITY) {
				if (raytrace.entityHit != null) {
					if (raytrace.entityHit instanceof IManualEntity) {
						updatePage(((IManualEntity) raytrace.entityHit).getPage(raytrace.entityHit));
					} else if (raytrace.entityHit instanceof EntityItem) {
						EntityItem item = (EntityItem) raytrace.entityHit;
						if (item.getItem().getItem() instanceof IManualItem) {
							updatePage(((IManualItem) item.getItem().getItem()).getPage(item.getItem()));
						}
					}
				}
			}
		}

		playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.MANUAL_GUI_ID, worldIn, 0, 0, 0);
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof IManualBlock) {
			if (world.isRemote)
				updatePage(((IManualBlock) state.getBlock()).getPage(world, pos, state));

			player.openGui(GrimPack.INSTANCE, PackGuiHandler.MANUAL_GUI_ID, world, 0, 0, 0);
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.PASS;
	}

	@SideOnly(Side.CLIENT)
	private void updatePage(@Nullable Page page) {
		if (page != null)
			GuiManualIndex.activeManualPage = page.getLink().copySelf();
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("tooltip.manual.number") + ManualRegistry.getLoadedParts().size());
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualCore.instructionManual_page;
	}
}
