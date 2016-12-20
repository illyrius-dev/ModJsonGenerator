# ModJsonGenerator
Basic program that can be run to generate JSON files for textures that are used in Minecraft mods.

This program will setup the required JSON files based on a template that will provide limited functionality but enough for the items or blocks to render in game.

The program takes either 2 or 3 arguments: The first must be the ModID that you are using to develop your mod. The second is a filepath that stores the unlocalised names of either items or blocks that are used by your mod. The third optional argument is a filepath that stores the unlocalised names of either items or blocks (whichever was not entered as the second argument) that are used by your mod. (if you specify 3 arguments they must be in the order: ModID, blocks unlocalised names, items unlocalised names.

The files that contain the unlocalised names of the blocks or items must be setup is a specific way. If you only wish to use a single file then the header of the file must contain either the word blocks or items, depending on what you are storing in the file. The file then can be filled with the unlocalised names of the items or blocks with one per line. If you are using both files then the headed with items or blocks is not needed.

Sample files are provided.
