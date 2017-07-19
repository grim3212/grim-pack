package com.grim3212.mc.pack.world.types;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldTypeFloatingIslands extends WorldType {

	// http://worldpresets.com/?post_type=world_preset&p=568
	private static final String JSON = "{\"coordinateScale\":375.0,\"heightScale\":6000.0,\"lowerLimitScale\":1025.0,\"upperLimitScale\":200.0,\"depthNoiseScaleX\":200.0,\"depthNoiseScaleZ\":200.0,\"depthNoiseScaleExponent\":0.5,\"mainNoiseScaleX\":80.0,\"mainNoiseScaleY\":160.0,\"mainNoiseScaleZ\":80.0,\"baseSize\":1.0,\"stretchY\":5.65,\"biomeDepthWeight\":1.0,\"biomeDepthOffset\":0.0,\"biomeScaleWeight\":1.0,\"biomeScaleOffset\":0.0,\"seaLevel\":47,\"useCaves\":true,\"useDungeons\":true,\"dungeonChance\":100,\"useStrongholds\":true,\"useVillages\":true,\"useMineShafts\":true,\"useTemples\":true,\"useMonuments\":true,\"useRavines\":true,\"useWaterLakes\":true,\"waterLakeChance\":1,\"useLavaLakes\":true,\"lavaLakeChance\":80,\"useLavaOceans\":false,\"fixedBiome\":-1,\"biomeSize\":1,\"riverSize\":5,\"dirtSize\":18,\"dirtCount\":8,\"dirtMinHeight\":0,\"dirtMaxHeight\":256,\"gravelSize\":22,\"gravelCount\":8,\"gravelMinHeight\":0,\"gravelMaxHeight\":256,\"graniteSize\":22,\"graniteCount\":8,\"graniteMinHeight\":95,\"graniteMaxHeight\":255,\"dioriteSize\":22,\"dioriteCount\":8,\"dioriteMinHeight\":95,\"dioriteMaxHeight\":255,\"andesiteSize\":22,\"andesiteCount\":8,\"andesiteMinHeight\":95,\"andesiteMaxHeight\":255,\"coalSize\":17,\"coalCount\":20,\"coalMinHeight\":125,\"coalMaxHeight\":255,\"ironSize\":9,\"ironCount\":23,\"ironMinHeight\":134,\"ironMaxHeight\":255,\"goldSize\":9,\"goldCount\":4,\"goldMinHeight\":175,\"goldMaxHeight\":255,\"redstoneSize\":8,\"redstoneCount\":8,\"redstoneMinHeight\":175,\"redstoneMaxHeight\":255,\"diamondSize\":8,\"diamondCount\":3,\"diamondMinHeight\":175,\"diamondMaxHeight\":255,\"lapisSize\":7,\"lapisCount\":2,\"lapisCenterHeight\":150,\"lapisSpread\":16}";

	public WorldTypeFloatingIslands(String name) {
		super(name);
	}

	@Override
	public boolean hasInfoNotice() {
		return true;
	}

	@Override
	public float getCloudHeight() {
		return 260F;
	}

	@Override
	public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
		return new ChunkGeneratorOverworld(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), JSON);
	}

}
