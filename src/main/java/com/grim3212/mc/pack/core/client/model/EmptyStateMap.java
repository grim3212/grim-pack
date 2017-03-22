package com.grim3212.mc.pack.core.client.model;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

public class EmptyStateMap extends StateMapperBase {

	@Override
	public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
		return Maps.newHashMap();
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return null;
	}

}
