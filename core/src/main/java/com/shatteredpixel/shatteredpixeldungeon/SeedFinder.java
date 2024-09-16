package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ArmoredStatue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.CrystalMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.GoldenMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blacksmith;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.EnergyCrystal;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap.Type;
import com.shatteredpixel.shatteredpixeldungeon.items.Honeypot;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Stylus;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.Key;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CeremonialCandle;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CorpseDust;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Embers;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Alchemize;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.Trinket;
import com.shatteredpixel.shatteredpixeldungeon.items.trinkets.TrinketCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.utils.DungeonSeed;
import com.watabou.noosa.Game;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SeedFinder {
	enum Condition {ANY, ALL};
	enum Mode {SCAN, FIND, TEST}



	public static class Options {
		public static Mode mode;

		public static int floors;
		public static int seedsToFind;
		public static Condition condition;
		public static String itemListFile;
		public static String outputFile;
		public static long seed;
		public static long startingSeed;
		public static long endingSeed;

		public static boolean quietMode;
		public static boolean runesOn;
		public static boolean barrenOn;
		public static boolean intoDarknessOn;
		public static boolean compactOutput;
		public static boolean skipConsumables;

		public static boolean uncurse;
		public static boolean exactUpgrades;

		public static boolean renderMinimap;
		public static boolean renderEnemies;
		public static boolean renderItems;
		public static boolean renderTerrain;
	}


	public class HeapItem {
		public Item item;
		public Heap heap;

		public HeapItem(Item item, Heap heap) {
			this.item = item;
			this.heap = heap;
		}
	}

	List<Class<? extends Item>> blacklist;
	ArrayList<String> itemList;
	ArrayList<ArrayList<String>> itemMultiList = new ArrayList<>();
	ArrayList<ArrayList<Integer>> itemUpgradeMultiList = new ArrayList<>();
	ArrayList<Integer> floorList = new ArrayList<>();


	// TODO: make it parse the item list directly from the arguments
	private void parseArgs(String[] args) {
		final org.apache.commons.cli.Options options = new org.apache.commons.cli.Options();

		options.addOption(new Option("mode", true, "scan/find/test"));
		options.addOption(new Option("condition", true, "ANY to find any listed items or ALL to find all items"));

		options.addOption(new Option("floors", true, "how deep to scan"));
		options.addOption(new Option("seeds","seeds_to_find", true, "how many seeds to find before stopping"));
		options.addOption(new Option("start", "starting_seed", true, "seed to start with, default 0"));
		options.addOption(new Option("seed", true, "seed to scan"));
		options.addOption(new Option("end", "ending_seed", true, "seed to end with, default max seed"));


		options.addOption(new Option("items", true, "file containing items"));
		options.addOption(new Option("output", true, "file to write the results to"));

		options.addOption(new Option("q", "quiet", false, "skip printing the intro message and only output seeds in AAA-AAA-AAA format to console"));
		options.addOption(new Option("r", "runes", false, "enable Forbidden Runes (generation-altering challenge)"));
		options.addOption(new Option("c", "compact", false, "remove most whitespace from the output to make it compact"));
		options.addOption(new Option("s", "skip_consumables", false, "don't print consumables in the report"));
		options.addOption(new Option("b", "barren_land", false, "enable Barren Land (generation-altering challenge)"));
		options.addOption(new Option("d", "into_darkness", false, "enable Into Darkness (generation-altering challenge)"));
		options.addOption(new Option("e", "exact_upgrades", false, "only detect items when they're exactly the specified level"));
		options.addOption(new Option("u", "uncurse", false, "only finds seeds with enough scrolls of remove curse to uncurse all requested items"));

		options.addOption(new Option("minimap", true, "flag string (E)nemies/(I)tems/(T)errain features/(N)one, e.g minimap EI, anything else for just passable tiles"));

		HelpFormatter formatter = new HelpFormatter();

		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (ParseException exp) {
			System.out.println("Parsing failed.  Reason: " + exp.getMessage());
		}

		if (args.length == 0){
			System.out.println("(ignore the warnings above)");
			formatter.printHelp("is-seedfinder", options);
			System.out.println("usage example: java -jar seedfinder.jar -mode find -items in.txt");
			System.exit(1);
		}

		assert line != null;
		String mode = line.getOptionValue("mode").toLowerCase();
			switch (mode){
				case "find":
					Options.mode = Mode.FIND;
					break;
				case "test":
					Options.mode = Mode.TEST;
					break;
				default:
					Options.mode = Mode.SCAN;

			}

		if (line.getOptionValue("condition", "ALL").equalsIgnoreCase("all")) Options.condition = Condition.ALL;
		else Options.condition = Condition.ANY;

		Options.floors = Integer.parseInt(line.getOptionValue("floors", "24"));
		Options.seedsToFind = Integer.parseInt(line.getOptionValue("seeds", "-1"));

		Options.startingSeed = Long.parseLong(line.getOptionValue("start", "0"));
		Options.endingSeed = Long.parseLong(line.getOptionValue("end", "5429503678975"));
		Options.seed = DungeonSeed.convertFromText(line.getOptionValue("seed", "0"));
		Options.itemListFile = line.getOptionValue("items");

		Options.outputFile = line.getOptionValue("output", "stdout");
		if (Options.outputFile.equals("stdout") && Options.mode == Mode.FIND) Options.outputFile = "out.txt";

		if(line.hasOption("q")) Options.quietMode = true;
		if(line.hasOption("r")) Options.runesOn = true;
		if(line.hasOption("c")) Options.compactOutput = true;
		if(line.hasOption("s")) Options.skipConsumables = true;
		if(line.hasOption("b")) Options.barrenOn = true;
		if(line.hasOption("d")) Options.intoDarknessOn = true;
		if(line.hasOption("u")) Options.uncurse = true;

		if(line.hasOption("e")) Options.exactUpgrades = true;

		Options.renderMinimap = line.hasOption("minimap");
		String minimapFlags = line.getOptionValue("minimap", "").toLowerCase();
		if (minimapFlags.contains("e")) Options.renderEnemies = true;
		if (minimapFlags.contains("i")) Options.renderItems = true;
		if (minimapFlags.contains("t")) Options.renderTerrain = true;
	}

	private ArrayList<ArrayList<String>> getItemMultiList() {
		if (Options.mode != Mode.FIND) return null;
		int curItemList = 0;
		floorList.add(Options.floors);
		itemMultiList.add(new ArrayList<String>());
		itemUpgradeMultiList.add(new ArrayList<Integer>());

		try {
			Scanner scanner = new Scanner(new File(Options.itemListFile));
			while (scanner.hasNextLine()) {
				String curNextLine = scanner.nextLine();
				if (curNextLine.startsWith("multirange")) {
					curItemList++;
					itemMultiList.add(new ArrayList<String>());
					itemUpgradeMultiList.add(new ArrayList<Integer>());
					floorList.add(Integer.valueOf(curNextLine.split(" ")[1].trim()));
				}
				else {
					String[] splitByUpgrades = curNextLine.split("\\+");
					if (curNextLine.contains("+")){
						if (splitByUpgrades.length > 1) {
							String afterplus = splitByUpgrades[1].trim();
							if (isInteger(afterplus)) {
								itemUpgradeMultiList.get(curItemList).add(Integer.valueOf(afterplus));
							} else if (Objects.equals(afterplus, "")) itemUpgradeMultiList.get(curItemList).add(1);
							else {
								System.out.println("error: upgrade level not an integer for item " + curNextLine);
								System.exit(1);
							}
						} else itemUpgradeMultiList.get(curItemList).add(1);
					}
					else itemUpgradeMultiList.get(curItemList).add(null);
					if (splitByUpgrades.length > 0) itemMultiList.get(curItemList).add(splitByUpgrades[0].trim());
					else itemMultiList.get(curItemList).add("");
				}
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



		return itemMultiList;
	}

	private void addTextItems(String caption, ArrayList<HeapItem> items, StringBuilder builder) {
		if (!items.isEmpty()) {
			if (!Options.compactOutput) builder.append(caption + ":\n");

			for (HeapItem item : items) {
				Item i = item.item;
				Heap h = item.heap;

				if (h.type == Type.CRYSTAL_CHEST) builder.append("* ");
				else builder.append("- ");

				if (((i instanceof Armor && ((Armor) i).hasGoodGlyph()) ||
						(i instanceof Weapon && ((Weapon) i).hasGoodEnchant()) ||
						(i instanceof Ring) || (i instanceof Wand) || (i instanceof Artifact)) && i.cursed)
					builder.append("cursed " + i.title().toLowerCase());

				else
					builder.append(i.title().toLowerCase());

				if (i instanceof Potion){
					builder.append(" (" + ((Potion) i).color + ")" );
				}
				if (i instanceof Scroll){
					builder.append(" (" + ((Scroll) i).rune.toLowerCase() + ")" );
				}
				if (i instanceof Ring){
					builder.append(" (" + ((Ring) i).gem + ")" );
				}

				if (h.type != Type.HEAP)
					builder.append(" (" + h.title().toLowerCase() + ")");

				builder.append("\n");
			}

			if (!Options.compactOutput) builder.append("\n");
		}
	}

	private void addTextQuest(String caption, ArrayList<Item> items, StringBuilder builder) {
		if (!items.isEmpty()) {
			builder.append(caption).append(":\n");

			for (Item i : items) {
				if (i.cursed)
					builder.append(" * cursed ").append(i.title().toLowerCase()).append("\n");

				else
					builder.append(" * ").append(i.title().toLowerCase()).append("\n");
			}

			if (!Options.compactOutput) builder.append("\n");
		}
	}

	private void addSingleReward(String caption, Item item, StringBuilder builder) {
			builder.append(caption).append(": ");
				if (item.cursed)
					builder.append("cursed ").append(item.title().toLowerCase()).append("\n");
				else
					builder.append(" ").append(item.title().toLowerCase()).append("\n");
			if (!Options.compactOutput) builder.append("\n");

	}

	public SeedFinder(String[] args) throws InterruptedException {
		parseArgs(args);
		if (!Options.quietMode) System.out.print("Starting IS-Seedfinder, game version: " + Game.version + "\n");

		try {
			Writer outputFile = new FileWriter(Options.outputFile);
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (Options.mode == Mode.SCAN) {
			logSeedItems(Options.seed, Options.floors);
			return;
		}

		getItemMultiList();
		int highestFloor = 0;
		for (int i = 0; i < floorList.size(); i++){
			if (floorList.get(i) > highestFloor) highestFloor = floorList.get(i);
		}
		Options.floors = highestFloor;

		int tofind = Options.seedsToFind;

		for (long i = Options.startingSeed; i < Options.endingSeed && tofind != 0; i++) {
			if (testSeed(i, Options.floors)) {
				tofind--;
				logSeedItems(i, Options.floors);
				if (Options.quietMode) System.out.print(DungeonSeed.convertToCode(Dungeon.seed));
				else System.out.printf("Found valid seed %s (%d)\n", DungeonSeed.convertToCode(Dungeon.seed), Dungeon.seed);
			}
		}
		if (Options.quietMode) Thread.sleep(3000); //give the poor thing some time
	}

	private ArrayList<Heap> getMobDrops(Level l) {
		ArrayList<Heap> heaps = new ArrayList<>();

		for (Mob m : l.mobs) {

			if (m instanceof ArmoredStatue) {
				Heap h = new Heap();
				h.items = new LinkedList<>();
				h.items.add(((ArmoredStatue) m).armor.identify());
				h.items.add(((ArmoredStatue) m).weapon.identify());
				h.type = Type.STATUE;
				heaps.add(h);
			}

			else if (m instanceof Statue) {
				Heap h = new Heap();
				h.items = new LinkedList<>();
				h.items.add(((Statue) m).weapon.identify());
				h.type = Type.STATUE;
				heaps.add(h);
			}

			else if (m instanceof Mimic) {
				Heap h = new Heap();
				h.items = new LinkedList<>();

				for (Item item : ((Mimic) m).items)
					h.items.add(item.identify());

				if (m instanceof GoldenMimic) h.type = Type.GOLDEN_MIMIC;
				else if (m instanceof CrystalMimic) h.type = Type.CRYSTAL_MIMIC;
				else h.type = Type.MIMIC;
				heaps.add(h);
			}
		}

		return heaps;
	}

	private boolean testSeed(Long seed, int highestFloor) {
		Dungeon.seed = seed;
		int chals = 0;
		if (Options.runesOn) chals += Challenges.NO_SCROLLS;
		if (Options.barrenOn) chals += Challenges.NO_HERBALISM;
		if (Options.intoDarknessOn) chals += Challenges.DARKNESS;
		int cursedRequestedItems = 0;
		int removeCurseScrolls = 0;
		SPDSettings.challenges(chals);
		GamesInProgress.selectedClass = HeroClass.WARRIOR;
		Dungeon.init();

		//boolean[][] itemsFound = new boolean[itemList.size()][];
		ArrayList<ArrayList<Boolean>> itemsFound = new ArrayList<>();
		for (int i = 0; i < itemMultiList.size(); i++){
			itemsFound.add(new ArrayList<>());
			for (int j = 0; j < itemMultiList.get(i).size(); j++){
				itemsFound.get(i).add(false);
			}
		}

		boolean trinketFound = false;
		for (int tr = 0; tr < 4; tr++) {
			Trinket trinket = (Trinket) Generator.random(Generator.Category.TRINKET);
			for (int j = 0; j < itemMultiList.size(); j++) {
				for (int z = 0; z < itemMultiList.get(j).size(); z++) {
					if (floorList.get(j) < Dungeon.depth) continue;
					if (
							trinket.title().toLowerCase().contains(itemMultiList.get(j).get(z))
							&& itemUpgradeMultiList.get(j).get(z) == null
					) {
						if (!itemsFound.get(j).get(z)) {
							itemsFound.get(j).set(z, true);
							trinketFound = true;
							break;
						}
					}
				}
				if (trinketFound) break;
			}
		}


		for (int i = 0; i < highestFloor; i++) {
			Level l = Dungeon.newLevel();

			boolean crystalChestFound = false;
			boolean questRewardFound = false;
			boolean questItemRequested = false;
			if(Dungeon.depth % 5 != 0) {

				boolean itemFound = false;
				if (l.sacrificeRoomPrize != null){
				for (int j = 0; j < itemMultiList.size(); j++) {
					for (int z = 0; z < itemMultiList.get(j).size(); z++) {
						if (floorList.get(j) < Dungeon.depth) continue;
						if (l.sacrificeRoomPrize instanceof ScrollOfRemoveCurse) removeCurseScrolls++;
						if (
								l.sacrificeRoomPrize.title().toLowerCase().contains(itemMultiList.get(j).get(z))
								&& (itemUpgradeMultiList.get(j).get(z) == null || (itemUpgradeMultiList.get(j).get(z) <= l.sacrificeRoomPrize.level()))
								&& (!Options.exactUpgrades || itemUpgradeMultiList.get(j).get(z) == l.sacrificeRoomPrize.level())
						) {
							if (!itemsFound.get(j).get(z)) {
								itemsFound.get(j).set(z, true);
								itemFound = true;
								if (l.sacrificeRoomPrize.cursed) cursedRequestedItems++;
								if (l.sacrificeRoomPrize instanceof ScrollOfRemoveCurse) removeCurseScrolls++;
								break;
							}
						}
					}
					if (itemFound) break;
				}
				}

				ArrayList<Heap> heaps = new ArrayList<>(l.heaps.valueList());
				heaps.addAll(getMobDrops(l));

				for (Heap h : heaps) {
					for (Item item : h.items) {
						item.identify();
						itemFound = false;
						for (int j = 0; j < itemMultiList.size(); j++) {
							for (int z = 0; z < itemMultiList.get(j).size(); z++) {
								if (floorList.get(j) < Dungeon.depth) continue;
								if (crystalChestFound && h.type == Type.CRYSTAL_CHEST) continue;
								if (item instanceof ScrollOfRemoveCurse) removeCurseScrolls++;
								if (
										item.title().toLowerCase().contains(itemMultiList.get(j).get(z))
										&& (itemUpgradeMultiList.get(j).get(z) == null || (itemUpgradeMultiList.get(j).get(z) <= item.level()))
										&& (!Options.exactUpgrades || itemUpgradeMultiList.get(j).get(z) == item.level())
								){
									if (!itemsFound.get(j).get(z)) {
										itemsFound.get(j).set(z, true);
										itemFound = true;
										if (item.cursed) cursedRequestedItems++;
										if (item.questItem) questItemRequested = true;
										if (h.type == Type.CRYSTAL_CHEST) crystalChestFound = true;
										break;
									}
								}
							}
							if (itemFound) break;
						}
					}
				}

				ArrayList<Item> rewards = getPossibleQuestRewards(l);
				for (Item item : rewards) {
					if (questItemRequested) break;
					item.identify();
					itemFound = false;
					for (int j = 0; j < itemMultiList.size(); j++) {
						for (int z = 0; z < itemMultiList.get(j).size(); z++) {
							if (floorList.get(j) < Dungeon.depth) continue;
							if (questRewardFound) continue;
							if (item instanceof ScrollOfRemoveCurse) removeCurseScrolls++;
							if (
									item.title().toLowerCase().contains(itemMultiList.get(j).get(z))
											&& (itemUpgradeMultiList.get(j).get(z) == null || (itemUpgradeMultiList.get(j).get(z) <= item.level()))
											&& (!Options.exactUpgrades || itemUpgradeMultiList.get(j).get(z) == item.level())
							) {
								if (!itemsFound.get(j).get(z)) {
									itemsFound.get(j).set(z, true);
									itemFound = true;
									if (item.cursed) cursedRequestedItems++;
									questRewardFound = true;
									break;
								}
							}
						}
						if (itemFound) break;
					}
				}
			}
			Dungeon.depth++;
		}

		if (Options.uncurse && removeCurseScrolls < cursedRequestedItems) return false;

		if (Options.condition == Condition.ANY) {
			for (int j = 0; j < itemMultiList.size(); j++) {
				for (int z = 0; z < itemMultiList.get(j).size(); z++) {
					if (itemsFound.get(j).get(z))
						return true;
				}
			}

			return false;
		}

		else {
			for (int j = 0; j < itemMultiList.size(); j++) {
				for (int z = 0; z < itemMultiList.get(j).size(); z++) {
					if (!itemsFound.get(j).get(z))
						return false;
				}
			}

			return true;
		}
	}

	private ArrayList<Item> getPossibleQuestRewards(Level level){
		ArrayList<Item> rewards = new ArrayList<>();
		if (Ghost.Quest.armor != null) {
			rewards.add(Ghost.Quest.armor.inscribe(Ghost.Quest.glyph).identify());
			rewards.add(Ghost.Quest.weapon.enchant(Ghost.Quest.enchant).identify());
			Ghost.Quest.complete();
		}
		if (Wandmaker.Quest.wand1 != null) {
			rewards.add(Wandmaker.Quest.wand1.identify());
			rewards.add(Wandmaker.Quest.wand2.identify());
			Wandmaker.Quest.complete();
		}
		if (Imp.Quest.reward != null) {
			rewards.add(Imp.Quest.reward.identify());
			Imp.Quest.complete();
		}
		return rewards;
	}

	private void logSeedItems(Long seed, int floors) {
		PrintWriter out = null;
		OutputStream out_fd = System.out;

		try {
			if (!Objects.equals(Options.outputFile, "stdout")) out_fd = new FileOutputStream(Options.outputFile, true);
			out = new PrintWriter(out_fd);
		} catch (FileNotFoundException e) { // gotta love Java mandatory exceptions
			e.printStackTrace();
		}

		Dungeon.seed = (seed);
		int chals = 0;
		if (Options.runesOn) chals |= Challenges.NO_SCROLLS;
		if (Options.barrenOn) chals |= Challenges.NO_HERBALISM;
		if (Options.intoDarknessOn) chals |= Challenges.DARKNESS;
		SPDSettings.challenges(chals);
		GamesInProgress.selectedClass = HeroClass.WARRIOR;
		Dungeon.init();

		blacklist = Arrays.asList(Gold.class, Dewdrop.class, IronKey.class, GoldenKey.class,
				Embers.class, CeremonialCandle.class, VelvetPouch.class, ScrollHolder.class, PotionBandolier.class, MagicalHolster.class);

		out.printf("Items for seed %s (%d):\n" + (Options.compactOutput ? "":"\n"), DungeonSeed.convertToCode(Dungeon.seed), Dungeon.seed);


		{
			out.printf("* Trinkets:\n");
			for (int tr = 0; tr < 4; tr++) {
				out.printf(" * " + Generator.random(Generator.Category.TRINKET).title() + "\n");
			}
			if (!Options.compactOutput) out.printf("\n");
		}

		for (int i = 0; i < floors; i++) {
			Level l = Dungeon.newLevel();

			if (Dungeon.depth % 5 == 0){
				Dungeon.depth++;
				continue;
			}

			out.printf("=== floor %d ===\n" + (Options.compactOutput ? "":"\n"), Dungeon.depth);
			if (l.feeling != Level.Feeling.NONE) {
				out.printf("Feeling: " + l.feeling.title());
				out.printf("\n");
			}

			if (Options.renderMinimap) {
				out.printf(levelEmojiMap(l));
				out.printf("\n\n");
			}
			ArrayList<Heap> heaps = new ArrayList<>(l.heaps.valueList());
			StringBuilder builder = new StringBuilder();
			ArrayList<HeapItem> scrolls = new ArrayList<>();
			ArrayList<HeapItem> potions = new ArrayList<>();
			ArrayList<HeapItem> equipment = new ArrayList<>();
			ArrayList<HeapItem> rings = new ArrayList<>();
			ArrayList<HeapItem> artifacts = new ArrayList<>();
			ArrayList<HeapItem> wands = new ArrayList<>();
			ArrayList<HeapItem> missiles = new ArrayList<>();
			ArrayList<HeapItem> shopitems = new ArrayList<>();
			ArrayList<HeapItem> others = new ArrayList<>();

			// list quest rewards
			if (Ghost.Quest.armor != null) {
				ArrayList<Item> rewards = new ArrayList<>();
				rewards.add(Ghost.Quest.armor.inscribe(Ghost.Quest.glyph).identify());
				rewards.add(Ghost.Quest.weapon.enchant(Ghost.Quest.enchant).identify());


				Ghost.Quest.complete();

				addTextQuest("* Ghost quest rewards", rewards, builder);
			}

			if (Wandmaker.Quest.wand1 != null) {
				ArrayList<Item> rewards = new ArrayList<>();
				rewards.add(Wandmaker.Quest.wand1.identify());
				rewards.add(Wandmaker.Quest.wand2.identify());
				Wandmaker.Quest.complete();

				builder.append("Wandmaker quest item: ");

				switch (Wandmaker.Quest.type) {
					case 1: default:
						builder.append("corpse dust\n");
						break;
					case 2:
						builder.append("fresh embers\n");
						break;
					case 3:
						builder.append("rotberry seed\n");
						break;

				}
				if (!Options.compactOutput) builder.append("\n");
				addTextQuest("* Wandmaker quest rewards", rewards, builder);
			}

			if (Blacksmith.Quest.Type() != 0 && !Blacksmith.Quest.completed()) {
				builder.append("Blacksmith quest type: ");
				switch (Blacksmith.Quest.Type()) {
					case 1: default:
						builder.append("crystal\n");
						break;
					case 2:
						builder.append("gnoll\n");
						break;
					case 3:
						builder.append("fungi\n");
						break;
				}
				String[] nameArray = Blacksmith.Quest.smithRewards.stream()
						.map(Item::title)
						.toArray(String[]::new);
				builder.append("Blacksmith quest smithing items: ").append(String.join(", ", nameArray)).append("\n");
				Blacksmith.Quest.complete();
			}

			if (Imp.Quest.reward != null) {
				ArrayList<Item> rewards = new ArrayList<>();
				rewards.add(Imp.Quest.reward.identify());
				Imp.Quest.complete();

				addTextQuest("* Imp quest reward", rewards, builder);
			}

			if (l.sacrificeRoomPrize != null) {
				addSingleReward("- Sacrificial room item", l.sacrificeRoomPrize.identify(), builder);
			}

			heaps.addAll(getMobDrops(l));

			// list items
			for (Heap h : heaps) {
				for (Item item : h.items) {
					item.identify();
					if (blacklist.contains(item.getClass())) continue;
					else if (item instanceof MeleeWeapon || item instanceof Armor) equipment.add(new HeapItem(item, h));
					else if (item instanceof Ring) rings.add(new HeapItem(item, h));
					else if (item instanceof Artifact) artifacts.add(new HeapItem(item, h));
					else if (item instanceof Wand) wands.add(new HeapItem(item, h));
					else if (item instanceof MissileWeapon) missiles.add(new HeapItem(item, h));
					else if (h.type == Type.FOR_SALE && Options.quietMode) continue;
					else if (item instanceof Scroll) scrolls.add(new HeapItem(item, h));
					else if (item instanceof Potion) potions.add(new HeapItem(item, h));
					else others.add(new HeapItem(item, h));
				}
			}
			if (!Options.skipConsumables) {
				addTextItems("Scrolls", scrolls, builder);
				addTextItems("Potions", potions, builder);
			}
			addTextItems("Equipment", equipment, builder);
			addTextItems("Rings", rings, builder);
			addTextItems("Artifacts", artifacts, builder);
			addTextItems("Wands", wands, builder);
			addTextItems("Missiles", missiles, builder);
			if (!Options.skipConsumables) addTextItems("Other", others, builder);
			out.print(builder);

			Dungeon.depth++;
		}

		out.close();
	}

	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	private static String levelEmojiMap(Level l){
		StringBuilder emojiString = new StringBuilder();

		int width = l.width();
		int height = l.map.length / l.width();
		int left = 0;
		int right = width - 1;
		int top = 0;
		int bottom = height - 1;
		int refTileLT = l.map[0];
		int refTileRB = l.map[l.map.length-1];

		while (left < width && isBorderColumn(l.map, left, width, height, refTileLT)) left++;
		while (right >= 0 && isBorderColumn(l.map, right, width, height, refTileRB)) right--;
		while (top < height && isBorderRow(l.map, top, width, refTileLT)) top++;
		while (bottom >= 0 && isBorderRow(l.map, bottom, width, refTileRB)) bottom--;

		top++; //seems to always get left over idk why

		int newWidth = right - left + 1;
		int newHeight = bottom - top + 1;

		int offset = top * width + left;

		int[] trimmedMap = trimMap(l.map, newWidth, newHeight, width, top, left);

		for (int i = 0; i < trimmedMap.length; i++) {
			if ( (i+1) % newWidth == 0) offset += left;
			emojiString.append(getEmojiForTile(trimmedMap[i], i+offset, l));
			if ((i + 1) % newWidth == 0) emojiString.append("\n");
			if ( (i+1) % newWidth == 0) offset += (width-right) - 1;
		}
		return emojiString.toString();
	}

	public static int[] trimMap(int[] map, int newWidth, int newHeight, int width, int top,int left) {
		int[] trimmedMap = new int[newWidth * newHeight];
		for (int y = 0; y < newHeight; y++) {
			for (int x = 0; x < newWidth; x++) {
				trimmedMap[y * newWidth + x] = map[(top + y) * width + left + x];
			}
		}
		return trimmedMap;
	}
	private static boolean isBorderColumn(int[] map, int col, int width, int height, int reftile) {
		for (int y = 0; y < height; y++) if (map[y * width + col] != reftile) return false;
		return true;

	}
	private static boolean isBorderRow(int[] map, int row, int width, int reftile) {
		for (int x = 0; x < width; x++) if (map[row * width + x] != reftile) return false;
		return true;
	}


	private static String getEmojiForTile(int tile, int origPos, Level l){

		if (Options.renderEnemies){
			Char ch = l.findMob(origPos);
			if( ch != null && ch.alignment == Char.Alignment.ENEMY) return "\uD83D\uDCA2"; //angry jp
		}

		if (Options.renderItems){
			Heap h = l.heaps.get(origPos);
			if (h != null && h.items.peek() != null){
				Item i = h.items.peek();
				if (i instanceof Armor) return "\uD83D\uDC55"; //shirt
				else if (i instanceof Artifact) return "\uD83E\uDDFF"; //
				else if (i instanceof Bag) return "\uD83C\uDF92"; //backpack
				else if (i instanceof Bomb) return "\uD83D\uDCA3"; //bomb
				else if (i instanceof Food) return "\uD83C\uDF59"; //riceball
				else if (i instanceof Key) {
					if (i instanceof IronKey) return "\uD83D\uDCCE"; //paperclip, key is halfwidth (ow)
					else if (i instanceof GoldenKey) return "\uD83D\uDD11"; //golden key
					else if (i instanceof CrystalKey) return "\uD83D\uDC8E"; //crystal key
					else return "\uD83D\uDCCE"; //paperclip again
 				}
				else if (i instanceof Potion) return "\uD83E\uDDEA"; //vial
				else if (i instanceof CeremonialCandle) return "\uD83E\uDE94"; //oil burner, candle non-emoji
				else if (i instanceof Embers) return "\uD83D\uDD25"; //fire emoji. apparently the embers are always there. huh
				else if (i instanceof CorpseDust) return "\uD83E\uDDB4"; //bone
				else if (i instanceof Ring) return "\uD83D\uDC8D"; //ring
				else if (i instanceof Scroll) return "\uD83D\uDCDC"; //scroll
				else if (i instanceof Alchemize) return "\uD83D\uDCB2"; //dollar sign
				else if (i instanceof Runestone) return "\uD83E\uDEA8"; //rock
				else if (i instanceof TrinketCatalyst) return "\uD83C\uDFB2"; //die
				else if (i instanceof Wand) return "\uD83E\uDE84"; //magic wand
				else if (i instanceof Weapon) {
					if (i instanceof MeleeWeapon) return "\uD83E\uDE93"; //axe, sword and knife emojis halfwidth
					else if (i instanceof MissileWeapon) return "\uD83E\uDE83"; //boomerang
				}
				else if (i instanceof Ankh) return "\uD83C\uDFC6"; //trophy
				else if (i instanceof Dewdrop) return "\uD83D\uDCA7"; //water drop
				else if (i instanceof EnergyCrystal) return "\uD83E\uDEE7"; //bubbles
				else if (i instanceof Gold) return "\uD83E\uDE99"; //coin
				else if (i instanceof Honeypot) return "\uD83C\uDF6F"; //honeypot
				else if (i instanceof Stylus) return "\uD83D\uDCDD"; //normal pen
				else if (i instanceof Torch) return "\uD83D\uDD26"; //flashlight
				else if (i instanceof Plant.Seed) return "\uD83E\uDED8"; //beans
				else return "\uD83D\uDD23"; //various symbols


			}
		}

		if (Options.renderTerrain) switch (tile){
			case (Terrain.CHASM): return "\uD83D\uDD33️"; //white in black square (hole emoji breaks)
			case (Terrain.EMPTY_DECO):
			case (Terrain.CUSTOM_DECO):
			case (Terrain.CUSTOM_DECO_EMPTY):
			case (Terrain.EMPTY): return "⬛️";
			case (Terrain.FURROWED_GRASS):
			case (Terrain.GRASS): return "\uD83D\uDFE2️"; //green circle

			case (Terrain.EMPTY_WELL): return "\uD83D\uDEB1️"; //no drinkable water
			case (Terrain.WALL_DECO):
			case (Terrain.WALL): return "⬜️";
			case (Terrain.OPEN_DOOR):
			case (Terrain.SECRET_DOOR):
			case (Terrain.DOOR): return "\uD83D\uDEAA️"; //door
			case (Terrain.ENTRANCE_SP):
			case (Terrain.ENTRANCE): return "⏫";
			case (Terrain.LOCKED_EXIT):
			case (Terrain.UNLOCKED_EXIT):
			case (Terrain.EXIT): return "⏬";
			case (Terrain.EMBERS): return "\uD83D\uDFE7"; //orange square
			case (Terrain.LOCKED_DOOR): return "\uD83D\uDD12️"; //lock
			case (Terrain.CRYSTAL_DOOR): return "\uD83D\uDCD8️"; //blue book
			case (Terrain.PEDESTAL): return "\uD83D\uDCE5"; //incoming symbol
			case (Terrain.BARRICADE): return "\uD83E\uDEB5️"; //log emoji
			case (Terrain.EMPTY_SP): return "\uD83D\uDFEB"; //brown square
			case (Terrain.HIGH_GRASS): return "\uD83D\uDFE9"; //green square

			case (Terrain.SECRET_TRAP):
			case (Terrain.INACTIVE_TRAP):
			case (Terrain.TRAP): return "\uD83D\uDFE5"; //red square

			case (Terrain.WELL): return "\uD83D\uDEB0️"; //drinkable water
			case (Terrain.BOOKSHELF): return "\uD83D\uDCDA️"; //stack of books
			case (Terrain.ALCHEMY): return "\uD83E\uDED5"; //

			case (Terrain.STATUE_SP):
			case (Terrain.STATUE): return "\uD83D\uDDFF️"; //moai
			case (Terrain.WATER): return "\uD83D\uDFE6"; //blue square
			default:  return "❔️";
		}

		return (l.passable[origPos] ? "⬛️" : "⬜️");

	}


}
