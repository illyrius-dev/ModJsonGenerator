import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class JSONGenerator
{
	public static String modID;
	public static File blocksFile;
	public static File itemsFile;
	
	/**
	 * @param args args[0] is the ModId, args[1] is either the blocks.txt file or the items.txt filepath,
	 *  args[2] is optional but if specified must be the items.txt filepath
	 */
	public static void main(String[] args)
	{
		try
		{
			processArgs(args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (blocksFile != null)
		{
			boolean itemsFileExists = true;
			
			if (itemsFile == null)
			{
				itemsFileExists = false;
			}
			
			ArrayList<String> unlocalizedNames = processFile(blocksFile, itemsFileExists);
			
			if (!unlocalizedNames.isEmpty())
			{
				for (String unlocalizedName : unlocalizedNames)
				{
					createFile(unlocalizedName, true);
				}
			}
		}
		
		if (itemsFile != null)
		{
			boolean blocksFileExists = true;
			
			if (blocksFile == null)
			{
				blocksFileExists = false;
			}
			
			ArrayList<String> unlocalizedNames = processFile(itemsFile, blocksFileExists);
			
			if (!unlocalizedNames.isEmpty())
			{
				for (String unlocalizedName : unlocalizedNames)
				{
					createFile(unlocalizedName, false);
				}
			}
		}
	}
	
	public static ArrayList<String> processFile(File file, boolean otherFileExists)
	{
		ArrayList<String> unlocalizedNames = new ArrayList<String>();
		
		try
		{
			Scanner fileScanner = new Scanner(file);
			
			if (!otherFileExists)
			{
				fileScanner.nextLine();
			}
			
			while (fileScanner.hasNextLine())
			{
				unlocalizedNames.add(fileScanner.nextLine());
			}
			
			fileScanner.close();
		} 
		catch (Exception e)
		{
			System.err.println("File Reading Error");
			e.printStackTrace();
		}
		
		return unlocalizedNames;
	}
	
	public static void processArgs(String[] args) throws Exception
	{
		if (args.length == 1 || args.length == 0)
		{
			throw new Exception("Not enough arguments");
		}
		
		if (args.length == 2)
		{
			try
			{
				Scanner fileTest = new Scanner(new File(args[1]));
				
				String inspectedLine = fileTest.nextLine();
				fileTest.close();
				
				if (inspectedLine.equalsIgnoreCase("blocks"))
				{
					blocksFile = new File(args[1]);
					itemsFile = null;
				}
				else if (inspectedLine.equalsIgnoreCase("items"))
				{
					itemsFile = new File(args[1]);
					blocksFile = null;
				}
				else
				{
					throw new Exception("File is setup incorrectly");
				}
				
				modID = args[0];
				
				return;
			} 
			catch (Exception e)
			{
				System.err.println("File inspection error");
				e.printStackTrace();
			}
		}
		
		if (args.length == 3)
		{
			modID = args[0];
			
			try
			{
				blocksFile = new File(args[1]);
				itemsFile = new File(args[2]);
				
				return;
			}
			catch (Exception e)
			{
				System.err.println("Invalid file");
				e.printStackTrace();
			}
		}
		
		throw new Exception("Too many arguments");
	}
	
	public static String getRegistryNameFromUnlocalized(String unlocalizedName)
	{
		String output = "";
		
		String[] words = unlocalizedName.toLowerCase().split("_");
		
		for (int i = words.length; i > 0; --i)
		{
			output += words[i - 1];
		}
		
		return output;
	}
	
	public static void createFile(String unlocalizedName, boolean namesAreBlocks)
	{
		String filePath = getRegistryNameFromUnlocalized(unlocalizedName);
		
		if (filePath.equals(""))
		{
			try
			{
				if (namesAreBlocks)
				{
					PrintWriter writer = new PrintWriter("blockstates/" + filePath + ".json", "UTF-8");
				    writer.println("{");
				    writer.println("    \"variants\": {");
				    writer.println("        \"normal\": { \"model\": \"" + modID + ":" + getRegistryNameFromUnlocalized(unlocalizedName) + "\" }");
				    writer.println("    }");
				    writer.println("}");
				    writer.close();
				    
				    writer = new PrintWriter("models/blocks/" + filePath + ".json", "UTF-8");
				    writer.println("{");
				    writer.println("    \"parent\": \"block/cube_all\",");
				    writer.println("    \"textures\": { \"all\": \"" + modID + ":blocks/" + unlocalizedName + "\" }");
				    writer.println("}");
				    writer.close();
				    
				    writer = new PrintWriter("models/items/" + filePath + ".json", "UTF-8");
				    writer.println("{");
				    writer.println("    \"parent\": \"block/cube_all\",");
				    writer.println("    \"textures\": { \"all\": \"" + modID + ":blocks/" + unlocalizedName + "\" }");
				    writer.println("}");
				    writer.close();
				}
				else
				{
					PrintWriter writer = new PrintWriter("models/items/" + filePath + ".json", "UTF-8");
				    writer.println("{");
				    writer.println("    \"parent\": \"builtin/generated\",");
				    writer.println("    \"textures\": {");
				    writer.println("        \"layer0\": \"" + modID + ":items/" + unlocalizedName + "\"");
				    writer.println("    },");
				    writer.println("    \"display\": {");
				    writer.println("        \"thirdperson\": {");
				    writer.println("            \"rotation\": [ -90, 0, 0 ],");
				    writer.println("            \"translation\": [ 0, 1, -3 ],");
				    writer.println("            \"scale\": [ 0.55, 0.55, 0.55 ]");
				    writer.println("        },");
				    writer.println("        \"firstperson\": {");
				    writer.println("            \"rotation\": [ 0, -135, 25 ],");
				    writer.println("            \"translation\": [ 0, 4, 2 ],");
				    writer.println("            \"scale\": [ 1.7, 1.7, 1.7 ]");
				    writer.println("        }");
				    writer.println("    }");
				    writer.println("}");
				    writer.close();
				}
			}
			catch (Exception e)
			{
				System.err.println("Error writing file");
				e.printStackTrace();
			}
		}
	}
}
