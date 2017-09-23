[![](http://cf.way2muchnoise.eu/full_grim-pack_downloads.svg)](http://minecraft.curseforge.com/projects/grim-pack) [![](http://cf.way2muchnoise.eu/versions/Minecraft_grim-pack_all.svg)](http://minecraft.curseforge.com/projects/grim-pack)
#[Grim Pack](http://minecraft.curseforge.com/projects/grim-pack)
### Master is at Minecraft 1.12.1

This is the combined mods previously known as Grims Mods.

These are seperated into 7 parts.

Core, Cuisine, Decor, Industry, Tools, Util, and World

* Core is what all of the sub parts rely on.
* Cuisine is foody stuff.
* Decor is for decorating and making everything look nice.
* Industry is more technical blocks mostly.
* Tools is tools and weapons.
* Util is utilities like FusRoDah.
* World is for world gen and entities.

## Setting Up A Workspace
* Clone or download the repository
* To Setup dev environment run `gradlew(.bat) setupDevWorkspace or setupDecompWorkspace`
* Optional IDE choice run `gradlew(.bat) eclipse or idea`
* To build simply run `gradlew(.bat) build`

All required dependencies should download automatically when setup is run so once that is done you should be good to go.

## Issue Reporting
Please include the following

* Minecraft Version
* Forge version
* GrimPack version
* If it crashed then the crash report. 
* Any other information that may be helpful like steps to reproduce or if it only happens occasionaly.

## Pull Requests
All pull requests need to target master currently at 1.12.1

Please include a description of what you changed.
This can be a fixed bug or even new features.
In the case of new features a brief description of what you added should do would be nice.

## Versioning
For the most part I am using the versioning conventions found at [http://mcforge.readthedocs.io/en/latest/conventions/versioning/](http://mcforge.readthedocs.io/en/latest/conventions/versioning/).
But instead of resetting all lesser values to 0 I will only do that when updating to a new version. Or, using that page, when incrementing MAJORMOD.
