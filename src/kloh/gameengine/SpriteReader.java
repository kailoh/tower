package kloh.gameengine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import cs195n.Vec2i;

/*INSTRUCTIONS FOR SPRITE READER
 * infoArray[0] = no of cols of sprite sheet
 * infoArray[1] = no of rows of sprite sheet
 * infoArray[2] = width of cell
 * infoArray[3] = border in x direction
 * infoArray[4] = height of cell
 * infoArray[5] = border in y direction
 * infoArray[6] = dimension of desired image in x direction
 * infoArray[7] = dimension of desired image in y direction
 */

/*THIS CODE FLIPS AN IMAGE VERTICALLY
 * AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
 * tx.translate(0, -_enemyImage.getHeight(null));
 * AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
 * _enemyImage = op.filter(_enemyImage, null);
*/
public class SpriteReader {

private HashMap<String, HashMap<Vec2i, BufferedImage>> _imageMap;

public SpriteReader(ArrayList<String> spriteFiles, HashMap<String, int[]> spriteFilesInfo) {
	_imageMap = new HashMap<String, HashMap<Vec2i, BufferedImage>>();
	BufferedImage fullImage = null;
	for (int i=0; i<spriteFiles.size(); i++) {
		if (!_imageMap.containsKey(spriteFiles.get(i))) {
			int infoArray[] = spriteFilesInfo.get(spriteFiles.get(i));
			HashMap<Vec2i, BufferedImage> subimageMap = new HashMap<Vec2i, BufferedImage>();
			String filename = spriteFiles.get(i);
		    URL url = SpriteReader.class.getResource(filename);
		    
			try {
				fullImage = ImageIO.read(url);
			} 
			catch (IOException e) {
			}
			for (int x = 0; x<infoArray[0]; x++) {
				for (int y=0;y<infoArray[1];y++) {
					Vec2i coordinate = new Vec2i(x,y);
					BufferedImage subimage = fullImage.getSubimage(x*infoArray[2]+infoArray[3], y*infoArray[4]+infoArray[5], infoArray[6], infoArray[7]);
					subimageMap.put(coordinate, subimage);
				}
			}
				
			_imageMap.put(spriteFiles.get(i), subimageMap);
		}
	} 
}

public HashMap<String, HashMap<Vec2i, BufferedImage>> getImageMap() {
	return _imageMap;
}

}