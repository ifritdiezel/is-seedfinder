package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ArmoredStatue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.CrystalMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.GoldenMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.EnergyCrystal;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap.Type;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CeremonialCandle;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Embers;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Pickaxe;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
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

		HelpFormatter formatter = new HelpFormatter();

		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (ParseException exp) {
			System.out.println("Parsing failed.  Reason: " + exp.getMessage());
		}

		if (args.length == 0){
			formatter.printHelp("is-seedfinder", options);
			System.out.println("test");
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
		Options.seed = Long.parseLong(line.getOptionValue("seed", "0"));

		Options.itemListFile = line.getOptionValue("items");

		if(line.hasOption("output")) Options.outputFile = line.getOptionValue("output", "stdout");
		if (Options.outputFile.equals("stdout") && Options.mode == Mode.FIND) Options.outputFile = "out.txt";

		if(line.hasOption("q")) Options.quietMode = true;
		if(line.hasOption("r")) Options.runesOn = true;
		if(line.hasOption("c")) Options.compactOutput = true;
		if(line.hasOption("s")) Options.skipConsumables = true;
		if(line.hasOption("b")) Options.barrenOn = true;
		if(line.hasOption("d")) Options.intoDarknessOn = true;
	}

	private ArrayList<String> getItemList() {
		if (Options.mode != Mode.FIND) return null;
		ArrayList<String> itemList = new ArrayList<>();

		try {
			Scanner scanner = new Scanner(new File(Options.itemListFile));

			while (scanner.hasNextLine()) {
				itemList.add(scanner.nextLine());
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return itemList;
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
			logSeedItems(Long.toString(Options.seed), Options.floors);
			return;
		}

		itemList = getItemList();
		int tofind = Options.seedsToFind;

		for (long i = Options.startingSeed; i < Options.endingSeed && tofind != 0; i++) {
			if (testSeed(Long.toString(i), Options.floors)) {
				tofind--;
				logSeedItems(Long.toString(i), Options.floors);
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

	private boolean testSeed(String seed, int floors) {
		SPDSettings.customSeed(seed);
		int chals = 0;
		if (Options.runesOn) chals += Challenges.NO_SCROLLS;
		if (Options.barrenOn) chals += Challenges.NO_HERBALISM;
		if (Options.intoDarknessOn) chals += Challenges.DARKNESS;
		SPDSettings.challenges(chals);
		GamesInProgress.selectedClass = HeroClass.WARRIOR;
		Dungeon.init();

		boolean[] itemsFound = new boolean[itemList.size()];

		for (int i = 0; i < floors; i++) {
			Level l = Dungeon.newLevel();

			boolean crystalChestFound = false;
			boolean questRewardFound = false;
			boolean questItemRequested = false;
			if(Dungeon.depth % 5 != 0) {

				if (l.sacrificeRoomPrize != null){
				for (int j = 0; j < itemList.size(); j++) {
					if (l.sacrificeRoomPrize.title().toLowerCase().contains(itemList.get(j))) {
						if (!itemsFound[j]) {
							itemsFound[j] = true;
							break;
						}
					}
				}
				}

				ArrayList<Heap> heaps = new ArrayList<>(l.heaps.valueList());
				heaps.addAll(getMobDrops(l));

				for (Heap h : heaps) {
					for (Item item : h.items) {
						item.identify();

						for (int j = 0; j < itemList.size(); j++) {
							if (crystalChestFound && h.type == Type.CRYSTAL_CHEST) continue;
							if (item.title().toLowerCase().contains(itemList.get(j))) {
								if (!itemsFound[j]) {
									itemsFound[j] = true;
									if (item.questItem) questItemRequested = true;
									if (h.type == Type.CRYSTAL_CHEST) crystalChestFound = true;
									break;
								}
							}
						}
					}
				}

				ArrayList<Item> rewards = getPossibleQuestRewards(l);
				for (Item item : rewards) {
					if (questItemRequested) break;
					item.identify();
					for (int j = 0; j < itemList.size(); j++) {
						if (questRewardFound) continue;

						if (item.title().toLowerCase().contains(itemList.get(j))) {
							if (!itemsFound[j]) {
								itemsFound[j] = true;
								questRewardFound = true;
								break;
							}
						}
					}
				}
			}
			Dungeon.depth++;
		}



		if (Options.condition == Condition.ANY) {
			for (int i = 0; i < itemList.size(); i++) {
				if (itemsFound[i])
					return true;
			}

			return false;
		}

		else {
			for (int i = 0; i < itemList.size(); i++) {
				if (!itemsFound[i])
					return false;
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

	private void logSeedItems(String seed, int floors) {
		PrintWriter out = null;
		OutputStream out_fd = System.out;

		try {
			if (!Objects.equals(Options.outputFile, "stdout")) out_fd = new FileOutputStream(Options.outputFile, true);
			out = new PrintWriter(out_fd);
		} catch (FileNotFoundException e) { // gotta love Java mandatory exceptions
			e.printStackTrace();
		}

		SPDSettings.customSeed(seed);
		int chals = 0;
		if (Options.runesOn) chals += Challenges.NO_SCROLLS;
		if (Options.barrenOn) chals += Challenges.NO_HERBALISM;
		if (Options.intoDarknessOn) chals += Challenges.DARKNESS;
		SPDSettings.challenges(chals);
		GamesInProgress.selectedClass = HeroClass.WARRIOR;
		Dungeon.init();

		blacklist = Arrays.asList(Gold.class, Dewdrop.class, IronKey.class, GoldenKey.class, CrystalKey.class, EnergyCrystal.class,
				Embers.class, CeremonialCandle.class, Pickaxe.class);

		out.printf("Items for seed %s (%d):\n" + (Options.compactOutput ? "":"\n"), DungeonSeed.convertToCode(Dungeon.seed), Dungeon.seed);

		for (int i = 0; i < floors; i++) {

			out.printf("=== floor %d ===\n" + (Options.compactOutput ? "":"\n"), Dungeon.depth);

			Level l = Dungeon.newLevel();

			if (Dungeon.depth % 5 == 0){
				Dungeon.depth++;
				continue;
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

}
