# IS-Seedfinder (cloned from [alessiomarotta/shpd-seed-finder](https://github.com/alessiomarotta/shpd-seed-finder))

Application to find seeds for Shattered Pixel Dungeon given constraints (e.g. wand of disintegration +2 and ring of evasion in the first 4 floors).
It can also display items found on a specific seed.

New feature summary:
- Specify the seed to start scanning with. Can be used to continue scanning after terminating the application or to run multiple instances to make use of multiple threads
- Specify final seed to stop at, useful for running multiple instances
- Skips every boss floor to slightly improve performance
- Fixes an issue with not finding armor from armored statues
- Guarantees all items are obtainable by only counting 1 quest reward/crystal chest per floor
- Checks quest rewards and reports more info about quests
- Supports requesting multiple ranges, e.g. might before floor 4 and glaive before floor 9
- (arguably) Improved output formatting with extra options
- Some extra output options for ease of automation (the [discord bot](https://github.com/ifritdiezel/is-seedfinder-bot))

# How to use

## Seed scanning mode

Prints out all the items a given seed has.

```
java -jar seedfinder.jar -mode scan -floors 24 -seed 123456789
```

- **floors**: maximum depth to display
- **seed**: dungeon seed to analyze
- **output_file**: if specified, scan results will be written to this file instead of console

## Finder mode

Finds seeds containing specified items. Start seedfinder without arguments to see a list of extra arguments.
To use multiple ranges, add `multirange x` as an item, where x is the desired floor. All items **after** this argument will be added to a new range. 

```
example: java -jar seedfinder.jar -mode find -floors 9 -items in.txt -output out.txt 
```

- **floors**: maximum depth to look for the items
- **items**: file name containing a list of items, *one* item per line. Put it in the same folder with the .jar
- **output**: file name where a scan of each seed will be written

The entries in the item list need to be in English, all lowercase and can optionally specify the enchantment and the upgrade level, so both `projecting crossbow +3` and `sword` are valid item names. **Beware that item names should be EXACTLY as they appear in the game, NO liberties like misplacing words or numbers. There is no autocorrect.** 
Example in.txt file:
```
mail armor of swiftness +2
flail
potion of experience
potion of experience
```

The application will run until the set final seed is scanned or all the seeds have been tested by default (virtually indefinitely), so stop it using Ctrl+C when you have found enough seeds.
If you want to automatically stop seedfinder after enough seeds are found, use the **-seeds 1** argument to specify the amount.

Any valid seeds will be printed during the execution in the 9 letter code and numeric format.

# How to build
The patch is already applied, see [Shattered PD desktop building instructions](https://github.com/00-Evan/shattered-pixel-dungeon/blob/master/docs/getting-started-desktop.md) to generate a release. 
