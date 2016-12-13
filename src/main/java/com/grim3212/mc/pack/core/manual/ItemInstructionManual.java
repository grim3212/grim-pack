package com.grim3212.mc.pack.core.manual;

import java.util.List;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.ClientUtil;
import com.grim3212.mc.pack.core.client.ManualCore;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

public class ItemInstructionManual extends Item implements IManualItem {

	public ItemInstructionManual() {
		this.setMaxStackSize(1);
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
						if (item.getEntityItem().getItem() instanceof IManualItem) {
							updatePage(((IManualItem) item.getEntityItem().getItem()).getPage(item.getEntityItem()));
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
				updatePage(((IManualBlock) state.getBlock()).getPage(state));

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
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag) {
		list.add("Mods Registered: " + ManualRegistry.getLoadedMods().size());
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualCore.instructionManual_page;
	}
}
