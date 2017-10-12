package com.grim3212.mc.pack.world.util;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.DungeonHooks;

/**
 * Straight from net.minecraftforge.common.DungeonHooks
 * 
 * I wanted to keep the two separate so that the extra mobs added don't change
 * regular dungeons.
 *
 */
public class RuinUtil {
	private static ArrayList<RuneMob> runeMobs = new ArrayList<RuneMob>();

	/**
	 * Adds a mob to the possible list of creatures the spawner will create. If
	 * the mob is already in the spawn list, the rarity will be added to the
	 * existing one, causing the mob to be more common.
	 *
	 * @param name
	 *            The name of the monster, use the same name used when
	 *            registering the entity.
	 * @param rarity
	 *            The rarity of selecting this mob over others. Must be greater
	 *            then 0. Vanilla Minecraft has the following mobs: Spider 100
	 *            Skeleton 100 Zombie 200 Meaning, Zombies are twice as common
	 *            as spiders or skeletons.
	 * @return The new rarity of the monster,
	 */
	public static float addRuneMob(ResourceLocation name, int rarity) {
		if (rarity <= 0) {
			throw new IllegalArgumentException("Rarity must be greater then zero");
		}

		for (RuneMob mob : runeMobs) {
			if (name.equals(mob.type)) {
				return mob.itemWeight += rarity;
			}
		}

		runeMobs.add(new RuneMob(rarity, name));
		return rarity;
	}

	/**
	 * Will completely remove a Mob from the dungeon spawn list.
	 *
	 * @param name
	 *            The name of the mob to remove
	 * @return The rarity of the removed mob, prior to being removed.
	 */
	public static int removeRuneMob(ResourceLocation name) {
		for (RuneMob mob : runeMobs) {
			if (name.equals(mob.type)) {
				runeMobs.remove(mob);
				return mob.itemWeight;
			}
		}
		return 0;
	}

	/**
	 * Gets a random mob name from the list.
	 * 
	 * @param rand
	 *            World generation random number generator
	 * @return The mob name
	 */
	public static ResourceLocation getRandomRuneMob(Random rand) {
		if (rand.nextInt(3) > 0) {
			RuneMob mob = WeightedRandom.getRandomItem(rand, runeMobs);
			return mob.type;
		} else {

			// We still want to be able to pull from this in case of modded mobs
			// added
			return DungeonHooks.getRandomDungeonMob(rand);
		}
	}

	public static class RuneMob extends WeightedRandom.Item {
		public ResourceLocation type;

		public RuneMob(int weight, ResourceLocation type) {
			super(weight);
			this.type = type;
		}

		@Override
		public boolean equals(Object target) {
			return target instanceof RuneMob && type.equals(((RuneMob) target).type);
		}
	}

	static {
		addRuneMob(new ResourceLocation("skeleton"), 100);
		addRuneMob(new ResourceLocation("zombie"), 200);
		addRuneMob(new ResourceLocation("spider"), 100);
		addRuneMob(new ResourceLocation("cave_spider"), 100);
		addRuneMob(new ResourceLocation("creeper"), 50);
		addRuneMob(new ResourceLocation("enderman"), 25);
		addRuneMob(new ResourceLocation("silverfish"), 50);
	}
}