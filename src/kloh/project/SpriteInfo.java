package kloh.project;

import java.util.ArrayList;
import java.util.HashMap;

public class SpriteInfo {
		
	public SpriteInfo() {
	}
	
	public ArrayList<String> createSpriteFiles() {
		ArrayList<String> spriteFiles = new ArrayList<String>();
		//spriteFiles.add("sprites.png");
		spriteFiles.add("grass.png");
		spriteFiles.add("soldier-up.png");
		spriteFiles.add("soldier-down.png");
		spriteFiles.add("soldier-right.png");
		spriteFiles.add("soldier-left.png");
		spriteFiles.add("barracks.png");
		spriteFiles.add("explosions.png");
		return spriteFiles;
	}

	public HashMap<String, int[]> createSpriteInfo() {
		HashMap<String, int[]> spriteFilesInfo = new HashMap<String, int[]>();
		/*
		int[] infoArray1 = new int[8];
		infoArray1[0] = 22; //change all these values if you're using a different sprites file, and you know the dimensions e.g. how many cells along x, y, width, height of cells, border, dimensions of images, etc
		infoArray1[1] = 12;
		infoArray1[2] = 33;
		infoArray1[3] = 1;
		infoArray1[4] = 33;
		infoArray1[5] = 1;
		infoArray1[6] = 32;
		infoArray1[7] = 32;
		spriteFilesInfo.put("sprites.png", infoArray1); //if you're using more than one sprite file, just put in a new array into the spriteFilesInfo hashmap with the same name as the sprite file
		 */
		int[] infoArray2 = new int[8];
		infoArray2[0] = 1;
		infoArray2[1] = 1;
		infoArray2[2] = 32;
		infoArray2[3] = 0;
		infoArray2[4] = 32;
		infoArray2[5] = 0;
		infoArray2[6] = 32;
		infoArray2[7] = 32;
		spriteFilesInfo.put("grass.png", infoArray2); 
		
		int[] infoArray3 = new int[8];
		infoArray3[0] = 1;
		infoArray3[1] = 1;
		infoArray3[2] = 24;
		infoArray3[3] = 0;
		infoArray3[4] = 41;
		infoArray3[5] = 0;
		infoArray3[6] = 24;
		infoArray3[7] = 41;
		spriteFilesInfo.put("soldier-up.png", infoArray3); 
		
		int[] infoArray4 = new int[8];
		infoArray4[0] = 1;
		infoArray4[1] = 1;
		infoArray4[2] = 24;
		infoArray4[3] = 0;
		infoArray4[4] = 41;
		infoArray4[5] = 0;
		infoArray4[6] = 24;
		infoArray4[7] = 41;
		spriteFilesInfo.put("soldier-down.png", infoArray4); 
		
		int[] infoArray5 = new int[8];
		infoArray5[0] = 1;
		infoArray5[1] = 1;
		infoArray5[2] = 41;
		infoArray5[3] = 0;
		infoArray5[4] = 24;
		infoArray5[5] = 0;
		infoArray5[6] = 41;
		infoArray5[7] = 24;
		spriteFilesInfo.put("soldier-right.png", infoArray5); 
		
		int[] infoArray6 = new int[8];
		infoArray6[0] = 1;
		infoArray6[1] = 1;
		infoArray6[2] = 41;
		infoArray6[3] = 0;
		infoArray6[4] = 24;
		infoArray6[5] = 0;
		infoArray6[6] = 41;
		infoArray6[7] = 24;
		spriteFilesInfo.put("soldier-left.png", infoArray6); 
		
		int[] infoArray7 = new int[8];
		infoArray7[0] = 1;
		infoArray7[1] = 1;
		infoArray7[2] = 128;
		infoArray7[3] = 0;
		infoArray7[4] = 128;
		infoArray7[5] = 0;
		infoArray7[6] = 128;
		infoArray7[7] = 128;
		spriteFilesInfo.put("barracks.png", infoArray7); 
		
		int[] infoArray8 = new int[8];
		infoArray8[0] = 5;
		infoArray8[1] = 5;
		infoArray8[2] = 64;
		infoArray8[3] = 0;
		infoArray8[4] = 64;
		infoArray8[5] = 0;
		infoArray8[6] = 64;
		infoArray8[7] = 64;
		spriteFilesInfo.put("explosions.png", infoArray8); 
		
		/*
		int[] infoArray9 = new int[8];
		infoArray9[0] = 1;
		infoArray9[1] = 1;
		infoArray9[2] = 128;
		infoArray9[3] = 0;
		infoArray9[4] = 128;
		infoArray9[5] = 0;
		infoArray9[6] = 128;
		infoArray9[7] = 128;
		spriteFilesInfo.put("metal.jpg", infoArray9); */
		
		return spriteFilesInfo;
	}
}
