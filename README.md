# IS-Seedfinder (cloned from [alessiomarotta/shpd-seed-finder](https://github.com/alessiomarotta/shpd-seed-finder))

Application to find seeds for Shattered Pixel Dungeon given constraints (e.g. wand of disintegration +2 and ring of evasion in the first 4 floors).
It can also display items found on a specific seed.

New feature summary:
- Specify the seed to start scanning with. Can be used to continue scanning after terminating the application or to run multiple instances to make use of multiple threads
- Specify final seed to stop at, useful for running multiple instances
- Skips every boss floor to slightly improve performance
- Fixes an issue with not finding armor from armored statues
- Guarantees all items are obtainable by only counting 1 quest reward/crystal chest per floor
- Checks quest rewards
- Supports enabling generation-altering challenges before scanning
- (arguably) Improved output formatting with extra options
- Some extra output options for ease of automation (the [discord bot](https://github.com/ifritdiezel/is-seedfinder-bot))

# How to use

## Seed scanning mode

Prints out all the items a given seed has.

```
java -jar seed-finder.jar scan floors seed [output_file] <-option_flags>
```

- **floors**: maximum depth to display
- **seed**: dungeon seed to analyze
- **output_file**: if specified, scan results will be written to this file instead of console
- **option_flags**:
    - if this contains r, enable Forbidden Runes (generation-altering challenge)
    - if this contains b, enable Barren Land (generation-altering challenge)

## Finder mode

Finds seeds containing specified items.

```
java -jar seed-finder.jar find floors condition item_list output_file [starting_seed] [ending_seed] <-option_flags>
example: java -jar desktop-2.0.1.jar find 9 all in.txt out.txt 100000 99999999 -br 
```

- **floors**: maximum depth to look for the items
- **condition**: can be either `any` or `all`: the first will consider a seed valid if any of the specified items has been found, the second one requires _all_ of the items to spawn instead
- **item_list**: file name containing a list of items, one item per line
- **output_file**: file name to save the item list for each seed
- **starting_seed**: the first seed the script scans. useful for running multiple instances to utilize more threads, stays at 0 if unspecified
- **ending_seed**: the script terminates upon reaching this seed, the last possible seed by default
- **option_flags**:
    - if this contains q, skip printing the intro message and only output seeds in AAA-AAA-AAA format to console
    - if this contains r, enable Forbidden Runes (generation-altering challenge)
    - if this contains b, enable Barren Land (generation-altering challenge)
    - if this contains d, enable Into Darkness (generation-altering challenge)
    - if this contains s, don't log consumables in the output
    - if this contains c, remove most whitespace from the output to make it compact

The entries in the item list need to be in english, all lowercase and can optionally specify the enchantment and the upgrade level, so both `projecting crossbow +3` and `sword` are valid item names.

The application will run until the set final seed is scanned or all the seeds have been tested by default (virtually indefinitely), so stop it using ctrl-C when you have found enough seeds for your needs.

Any valid seeds will be printed during the execution in the 9 letter code and numeric format.

# How to build
The patch is already applied, see [Shattered PD desktop building instructions](https://github.com/00-Evan/shattered-pixel-dungeon/blob/master/docs/getting-started-desktop.md) to generate a release. 
A modified [patch](https://github.com/ifritdiezel/is-seedfinder/blob/master/is-seedfinder.patch) to apply to an existing project is also available in the root directory.
