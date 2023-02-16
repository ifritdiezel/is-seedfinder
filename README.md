# IS-Seedfinder (cloned from [alessiomarotta/shpd-seed-finder](https://github.com/alessiomarotta/shpd-seed-finder))

Application to find seeds for Shattered Pixel Dungeon given constraints (e.g. wand of disintegration +2 and ring of evasion in the first 4 floors).
It can also display items found on a specific seed.

New feature summary:
- Specify the seed to start scanning with. Can be used to continue scanning after terminating the application or to run multiple instances to make use of multiple threads
- Specify final seed to stop at, useful for running multiple instances
- Skips every boss floor to slightly improve performance

# How to use

## Seed display mode

If no more than two arguments are provided, the items found in a given seed will be printed on the screen:

```
java -jar seed-finder.jar floors seed [output_file]
```

- **floors**: maximum depth to display
- **seed**: dungeon seed to analyze
- **output_file**: if specified, scan results will be written to this file instead of console

## Finder mode

If al least 3 arguments are provided, the application will try to find a specific seed:

```
java -jar seed-finder.jar floors condition item_list output_file [starting_seed] [ending_seed] [option_flags]
```

- **floors**: maximum depth to look for the items
- **condition**: can be either `any` or `all`: the first will consider a seed valid if any of the specified items has been found, the second one requires _all_ of the items to spawn instead
- **item_list**: file name containing a list of items, one item per line
- **output_file**: file name to save the item list for each seed
- **starting_seed**: the first seed the script scans. useful for running multiple instances to utilize more threads, stays at 0 if unspecified
- **ending_seed**: the script terminates upon reaching this seed, the last possible seed by default
- **option_flags**: if this contains q, skip printing the intro message and only output seeds in AAA-AAA-AAA format to console. if this contains r, enable forbidden runes

The entries in the item list need to be in english, all lowercase and can optionally specify the enchantement and the upgrade level, so both `projecting crossbow +3` and `sword` are valid item names.

The application will run until the set final seed is scanned or all the seeds have been tested by default (virtually indefinitely), so stop it using ctrl-C when you have found enough seeds for your needs.

Any valid seeds will be printed during the execution in the 9 letter code and numeric format.

# How to build
The patch is already applied, see [Shattered PD desktop building instructions](https://github.com/00-Evan/shattered-pixel-dungeon/blob/master/docs/getting-started-desktop.md) to generate a release. 
A modified [patch](https://github.com/ifritdiezel/is-seedfinder/blob/master/is-seedfinder.patch) to apply to an existing project is also available in the root directory.
