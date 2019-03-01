package com.grim3212.mc.pack.core.manual;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IManualEntry {

	public interface IManualBlock extends IManualEntry {

		@OnlyIn(Dist.CLIENT)
		default public Page getPage(IBlockState state) {
			return null;
		}

		@OnlyIn(Dist.CLIENT)
		default public Page getPage(@Nullable World world, @Nullable BlockPos pos, IBlockState state) {
			return getPage(state);
		}
	}

	public interface IManualItem extends IManualEntry {

		@OnlyIn(Dist.CLIENT)
		public Page getPage(ItemStack stack);

	}

	public interface IManualEntity extends IManualEntry {

		@OnlyIn(Dist.CLIENT)
		public Page getPage(Entity entity);

	}

}
