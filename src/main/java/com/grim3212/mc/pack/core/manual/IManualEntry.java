package com.grim3212.mc.pack.core.manual;

import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IManualEntry {

	public interface IManualBlock extends IManualEntry {

		@SideOnly(Side.CLIENT)
		public Page getPage(IBlockState state);

	}

	public interface IManualItem extends IManualEntry {

		@SideOnly(Side.CLIENT)
		public Page getPage(ItemStack stack);

	}

	public interface IManualEntity extends IManualEntry {

		@SideOnly(Side.CLIENT)
		public Page getPage(Entity entity);

	}

}
