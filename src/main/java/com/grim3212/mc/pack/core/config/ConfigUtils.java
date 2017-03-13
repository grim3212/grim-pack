package com.grim3212.mc.pack.core.config;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class ConfigUtils {

	private static final String ORE_DICT = "oredict";

	/**
	 * A list of recipes. Each sublist contains a valid recipe. If xp is true
	 * then this list will be of size 3 and otherwise of size 2
	 * 
	 * @param fromConfig
	 *            Config entry to pull string away from
	 * @param xp
	 *            Should xp be considered in this recipe
	 * @return
	 */
	public static List<Recipe> loadConfigurableRecipes(String[] fromConfig, boolean xp) {
		List<Recipe> recipes = Lists.newArrayList();

		if (fromConfig.length > 0) {
			for (int i = 0; i < fromConfig.length; i++) {
				String[] rawids = fromConfig[i].split(">");

				Object input = getInput(rawids[0], fromConfig[i]);
				ItemStack output = getOutput(rawids[1], fromConfig[i]);

				// Make sure input and output are valid
				if (input != null && !output.isEmpty()) {
					Recipe recipe = new Recipe();

					// Set recipe since it is unchanging
					recipe.output = output;

					// Set xp since it is unchanging
					if (xp) {
						if (rawids.length == 3) {
							// Try and parse experience
							recipe.experience = getExperience(rawids[2], fromConfig[i]);
						}
					} else if (rawids.length == 3) {
						GrimLog.error(GrimPack.modName, "Ignoring XP in recipe without experience: '" + recipe + "'!");
					}

					// Change recipe depending on input
					if (input instanceof ItemStack) {
						recipe.input = (ItemStack) input;

						// Add finished recipe
						recipes.add(recipe);
					} else if (input instanceof String) {
						String oreDict = (String) input;

						if (oreDict.contains(":")) {
							String[] split = oreDict.split(":");

							if (OreDictionary.doesOreNameExist(split[0])) {
								NonNullList<ItemStack> inputs = OreDictionary.getOres(split[0]);

								if (Utils.isInteger(split[1])) {
									for (ItemStack ore : inputs) {
										Recipe oreRecipe = new Recipe();
										oreRecipe.input = new ItemStack(ore.getItem(), 1, Integer.parseInt(split[1]));
										oreRecipe.output = recipe.output;
										oreRecipe.experience = recipe.experience;

										// Add recipe for every OreDictionary
										// item
										recipes.add(oreRecipe);
									}
								} else if (split[1].equals("?")) {
									for (ItemStack ore : inputs) {
										Recipe oreRecipe = new Recipe();
										oreRecipe.input = new ItemStack(ore.getItem(), 1, OreDictionary.WILDCARD_VALUE);
										oreRecipe.output = recipe.output;
										oreRecipe.experience = recipe.experience;

										// Add recipe for every OreDictionary
										// item
										recipes.add(oreRecipe);
									}
								} else {
									GrimLog.error(GrimPack.modName, "Something unknown happened with input: '" + recipe + "'!");
								}
							} else {
								GrimLog.error(GrimPack.modName, "Ore name: '" + split[0] + "' does not exist!");
							}
						} else {
							if (OreDictionary.doesOreNameExist(oreDict)) {
								NonNullList<ItemStack> inputs = OreDictionary.getOres(oreDict);

								for (ItemStack ore : inputs) {
									Recipe oreRecipe = new Recipe();
									oreRecipe.input = ore;
									oreRecipe.output = recipe.output;
									oreRecipe.experience = recipe.experience;

									// Add recipe for every OreDictionary item
									recipes.add(oreRecipe);
								}
							} else {
								GrimLog.error(GrimPack.modName, "Ore name: '" + oreDict + "' does not exist!");
							}
						}
					} else {
						GrimLog.error(GrimPack.modName, "Something unknown happened with input: '" + recipe + "'!");
					}
				}
			}
		}

		return recipes;
	}

	private static Object getInput(String s, String recipe) {
		Object input = getItemStackFromString(s);

		if (input instanceof ItemStack) {
			ItemStack in = (ItemStack) input;
			if (!in.isEmpty()) {
				return in;
			} else {
				GrimLog.error(GrimPack.modName, "Couldn't add recipe: '" + recipe + "' input is empty!");
			}
		} else if (input instanceof String) {
			return input;
		} else {
			GrimLog.error(GrimPack.modName, "Couldn't parse input in recipe: '" + recipe + "'!");
		}

		return null;
	}

	private static ItemStack getOutput(String s, String recipe) {
		Object output = getItemStackFromString(s);

		if (output instanceof ItemStack) {
			ItemStack out = (ItemStack) output;
			if (!out.isEmpty()) {
				return out;
			} else {
				GrimLog.error(GrimPack.modName, "Couldn't add recipe: '" + recipe + "' output is empty!");
			}

		} else if (output instanceof String) {
			GrimLog.error(GrimPack.modName, "Output cannot be an OreDictionary item in recipe: '" + recipe + "'!");
		} else {
			GrimLog.error(GrimPack.modName, "Couldn't parse output in recipe: '" + recipe + "'!");
		}

		return ItemStack.EMPTY;
	}

	private static float getExperience(String s, String recipe) {
		try {
			return Float.parseFloat(s);
		} catch (Exception e) {
			GrimLog.error(GrimPack.modName, "Experience couldn't be parsed as a float: '" + recipe + "'!");
		}
		return 0.0F;
	}

	public static void loadItemsOntoList(String[] config, List<ItemStack> list) {
		if (list == null) {
			list = Lists.newArrayList();
		} else {
			list.clear();
		}

		list.addAll(loadConfigurableItems(config));
	}

	public static List<ItemStack> loadConfigurableItems(String[] fromConfig) {
		List<ItemStack> itemlist = Lists.newArrayList();

		if (fromConfig.length > 0) {
			for (String u : fromConfig) {
				if (u.contains(":")) {
					String[] parts = u.split(":");
					if (parts.length == 2) {
						if (parts[0].equals(ORE_DICT)) {
							if (OreDictionary.doesOreNameExist(parts[1])) {
								itemlist.addAll(OreDictionary.getOres(parts[1]));
							} else {
								GrimLog.error(GrimPack.modName, "Ore name does not exist: '" + parts[1] + "'!");
							}
						} else {
							if (Utils.isInteger(parts[1])) {
								Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0]));
								if (item != null) {
									itemlist.add(new ItemStack(item, 1, Integer.parseInt(parts[1])));
								} else {
									GrimLog.error(GrimPack.modName, "Can't find item that matches '" + u + "'!");
								}
							} else if (parts[1].equals("?")) {
								Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0]));
								if (item != null) {
									itemlist.add(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
								} else {
									GrimLog.error(GrimPack.modName, "Can't find item that matches '" + u + "'!");
								}
							} else {
								Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
								if (item != null) {
									itemlist.add(new ItemStack(item));
								} else {
									GrimLog.error(GrimPack.modName, "Can't find item that matches '" + u + "'!");
								}
							}
						}
					} else if (parts.length == 3) {
						// OreDictionary Meta Override
						if (parts[0].equals(ORE_DICT)) {
							if (OreDictionary.doesOreNameExist(parts[1])) {

								if (Utils.isInteger(parts[2])) {
									// Use meta
									for (ItemStack stack : OreDictionary.getOres(parts[1])) {
										itemlist.add(new ItemStack(stack.getItem(), 1, Integer.parseInt(parts[2])));
									}
								} else if (parts[2].equals("?")) {
									// Ignore meta
									for (ItemStack stack : OreDictionary.getOres(parts[1])) {
										itemlist.add(new ItemStack(stack.getItem(), 1, OreDictionary.WILDCARD_VALUE));
									}
								} else {
									GrimLog.error(GrimPack.modName, "The third arg (meta) must be an int! Found '" + u + "'!");
								}

							} else {
								GrimLog.error(GrimPack.modName, "Ore name does not exist: '" + parts[1] + "'!");
							}
						} else {
							Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
							if (item != null) {
								if (Utils.isInteger(parts[2])) {
									// Use meta
									itemlist.add(new ItemStack(item, 1, Integer.parseInt(parts[2])));
								} else if (parts[2].equals("?")) {
									// Ignore meta
									itemlist.add(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
								} else {
									GrimLog.error(GrimPack.modName, "The third arg (meta) must be an int! Found '" + u + "'!");
								}
							} else {
								GrimLog.error(GrimPack.modName, "Can't find item that matches: " + u);
							}
						}
					}
				} else {
					// Without any colons
					Item item = Item.REGISTRY.getObject(new ResourceLocation(u));
					if (item != null) {
						itemlist.add(new ItemStack(item));
					} else {
						GrimLog.error(GrimPack.modName, "Can't find item with name: " + u);
					}
				}
			}
		}

		return itemlist;
	}

	/**
	 * Checks is a IBlockState is found within a map of states and if meta
	 * should be ignored
	 * 
	 * @param map
	 * @param state
	 * @return
	 */
	public static boolean isStateFound(Map<IBlockState, Boolean> map, IBlockState state) {
		if (map != null && !map.isEmpty()) {
			if (map.containsKey(state) || (map.containsKey(state.getBlock().getDefaultState()) && map.get(state.getBlock().getDefaultState()))) {
				return true;
			}
		}

		return false;
	}

	public static void loadBlocksOntoMap(String[] config, Map<IBlockState, Boolean> map) {
		if (map == null) {
			map = Maps.newHashMap();
		} else {
			map.clear();
		}

		map.putAll(loadConfigurableBlocks(config));
	}

	/**
	 * Returns a map of blocks with a state and if the meta should be ignored
	 * 
	 * @param fromConfig
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Map<IBlockState, Boolean> loadConfigurableBlocks(String[] fromConfig) {
		Map<IBlockState, Boolean> blocklist = Maps.newHashMap();

		if (fromConfig.length > 0) {
			for (String u : fromConfig) {
				if (u.contains(":")) {
					String[] parts = u.split(":");
					if (parts.length == 2) {
						if (parts[0].equals(ORE_DICT)) {
							if (OreDictionary.doesOreNameExist(parts[1])) {
								for (ItemStack stack : OreDictionary.getOres(parts[1])) {
									if (stack.getItem() instanceof ItemBlock) {
										Block block = ((ItemBlock) stack.getItem()).getBlock();
										if (stack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
											blocklist.put(block.getStateFromMeta(stack.getMetadata()), true);
										} else {
											blocklist.put(block.getStateFromMeta(stack.getMetadata()), false);
										}
									} else {
										GrimLog.error(GrimPack.modName, "Ignoring item '" + stack.getUnlocalizedName() + "' in OreDict '" + parts[1] + "'!");
									}
								}
							} else {
								GrimLog.error(GrimPack.modName, "OreDict name does not exist: '" + parts[1] + "'!");
							}
						} else {
							if (Utils.isInteger(parts[1])) {
								Block block = Block.REGISTRY.getObject(new ResourceLocation(parts[0]));
								if (block != null) {
									// Meta
									blocklist.put(block.getStateFromMeta(Integer.parseInt(parts[1])), false);
								} else {
									GrimLog.error(GrimPack.modName, "Can't find block that matches: " + u);
								}
							} else if (parts[1].equals("?")) {
								// Ignore meta
								Block block = Block.REGISTRY.getObject(new ResourceLocation(parts[0]));
								if (block != null) {
									blocklist.put(block.getDefaultState(), true);
								} else {
									GrimLog.error(GrimPack.modName, "Can't find block that matches: " + u);
								}
							} else {
								Block block = Block.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
								if (block != null) {
									blocklist.put(block.getDefaultState(), false);
								} else {
									GrimLog.error(GrimPack.modName, "Can't find block that matches: " + u);
								}
							}
						}
					} else if (parts.length == 3) {
						// OreDictionary Meta Override
						if (parts[0].equals(ORE_DICT)) {
							if (OreDictionary.doesOreNameExist(parts[1])) {
								if (Utils.isInteger(parts[2])) {
									for (ItemStack stack : OreDictionary.getOres(parts[1])) {
										if (stack.getItem() instanceof ItemBlock) {
											Block block = ((ItemBlock) stack.getItem()).getBlock();
											blocklist.put(block.getStateFromMeta(Integer.parseInt(parts[2])), false);
										} else {
											GrimLog.error(GrimPack.modName, "Ignoring item '" + stack.getUnlocalizedName() + "' in OreDict '" + parts[1] + "'!");
										}
									}
								} else if (parts[2].equals("?")) {
									for (ItemStack stack : OreDictionary.getOres(parts[1])) {
										if (stack.getItem() instanceof ItemBlock) {
											Block block = ((ItemBlock) stack.getItem()).getBlock();
											blocklist.put(block.getDefaultState(), true);
										} else {
											GrimLog.error(GrimPack.modName, "Ignoring item '" + stack.getUnlocalizedName() + "' in OreDict '" + parts[1] + "'!");
										}
									}
								} else {
									GrimLog.error(GrimPack.modName, "The third arg (meta) must be an int! Found '" + u + "'!");
								}
							} else {
								GrimLog.error(GrimPack.modName, "OreDict name does not exist: '" + parts[1] + "'!");
							}
						} else {
							Block block = Block.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
							if (block != null) {
								if (Utils.isInteger(parts[2])) {
									// Use meta
									blocklist.put(block.getStateFromMeta(Integer.parseInt(parts[2])), false);
								} else if (parts[2].equals("?")) {
									// Ignore meta
									blocklist.put(block.getDefaultState(), true);
								} else {
									GrimLog.error(GrimPack.modName, "The third arg (meta) must be an int! Found '" + u + "'!");
								}
							} else {
								GrimLog.error(GrimPack.modName, "Can't find block that matches: " + u);
							}
						}
					}
				} else {
					// Without any colons
					Block block = Block.REGISTRY.getObject(new ResourceLocation(u));
					if (block != null) {
						blocklist.put(block.getDefaultState(), false);
					} else {
						GrimLog.error(GrimPack.modName, "Can't find block with name: " + u);
					}
				}
			}
		}

		return blocklist;
	}

	/**
	 * Get an ItemStack or OreDictionary name from a string
	 * 
	 * @param s
	 * @return
	 */
	public static Object getItemStackFromString(String s) {
		if (s.contains(":")) {
			String[] split = s.split(":");

			if (split[0].equals(ORE_DICT)) {
				// Meta override
				if (split.length == 3) {
					if (Utils.isInteger(split[2]) || split[2].equals("?")) {
						return split[1] + ":" + split[2];
					} else {
						GrimLog.error(GrimPack.modName, "The third arg (meta) must be an int or '?'! Found in '" + s + "'!");
					}
				}

				return split[1];
			} else {
				if (split.length == 2) {
					return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)));
				} else if (split.length == 3) {
					if (Utils.isInteger(split[2])) {
						// Use meta
						return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)), 1, Integer.parseInt(split[2]));
					} else if (split[2].equals("?")) {
						// Ignore meta
						return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)), 1, OreDictionary.WILDCARD_VALUE);
					} else {
						GrimLog.error(GrimPack.modName, "Not sure what '" + s + "' is!");
						return ItemStack.EMPTY;
					}
				}
			}
		} else {
			return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)));
		}

		return ItemStack.EMPTY;
	}

}
