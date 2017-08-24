package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockModernDoor extends BlockDoor implements IManualBlock {

	protected BlockModernDoor(Material material) {
		super(material);

		String name;

		if (material == Material.GLASS) {
			setSoundType(SoundType.GLASS);
			name = "door_glass";
			setHardness(0.75F);
			setResistance(7.5F);
		} else if (material == Material.CIRCUITS) {
			setSoundType(SoundType.METAL);
			name = "door_chain";
			setHardness(0.5F);
			setResistance(5.0F);
		} else {
			setSoundType(SoundType.METAL);
			name = "door_steel";
			setHardness(1.0F);
			setResistance(10.0F);
		}

		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(GrimPack.modID, name));
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : this.getItem();
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this.getItem());
	}

	private Item getItem() {
		return Item.getItemFromBlock(this);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.doors_page;
	}
}
