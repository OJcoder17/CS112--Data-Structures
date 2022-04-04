package art;

import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno 
 * @author: Olivier Jolius NETID: orj2 EMAIL: orj2@scarletmail.rutgers.edu
 */     

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine a collagePicture as a 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color 
    // of the pixel on the screen.
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage (String filename) {
       this.collageDimension = 4; 
       this.tileDimension = 150; 
       this.originalPicture = new Picture(filename); 

       this.collagePicture = new Picture(this.tileDimension * this.collageDimension, this.tileDimension * this.collageDimension);
         
       for (int col = 0; col < (this.tileDimension * this.collageDimension); col++){
        for (int row = 0; row < (this.tileDimension * this.collageDimension); row++){
            int scol = col * this.originalPicture.width() / (this.tileDimension * this.collageDimension);
            int srow = row * this.originalPicture.height() / (this.tileDimension * this.collageDimension);
            Color color = this.originalPicture.get(scol, srow);
            this.collagePicture.set(col, row, color);
        }
    }
        
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */    
    public Collage (String filename, int td, int cd) {

        this.collageDimension = cd;
        this.tileDimension = td;
        this.originalPicture = new Picture(filename);

        this.collagePicture = new Picture(this.tileDimension * this.collageDimension, this.tileDimension * this.collageDimension);

        for (int col = 0; col < (this.tileDimension * this.collageDimension); col++){
            for (int row = 0; row < (this.tileDimension * this.collageDimension); row++){
                int scaledcol = col * this.originalPicture.width() / (this.tileDimension * this.collageDimension);
                int scaledrow = row * this.originalPicture.height() / (this.tileDimension * this.collageDimension);
                Color color = this.originalPicture.get(scaledcol, scaledrow);
                this.collagePicture.set(col, row, color);
            }
        }
    }


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
     * @param source is the image to be scaled.
     * @param target is the 
     */
    public static void scale (Picture source, Picture target) {
            int width = 600; 
            int height = 600; 

           
             target = new Picture(width, height);
                    
             for (int targetCol = 0; targetCol < width; targetCol++) {
                for (int targetRow = 0; targetRow < height; targetRow++) {
                    int sourceCol = targetCol * source.width()  / width;
                    int sourceRow = targetRow * source.height() / height;
                    Color color = source.get(sourceCol, sourceRow);
                    target.set(targetCol, targetRow, color);
                }
            }
    
            source.show();
            target.show();
    }

     /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    
    public void makeCollage () {
            
        Picture scaled = new Picture(this.tileDimension, this.tileDimension);
        
        for (int col = 0; col < this.tileDimension; col++){
            for (int row = 0; row < this.tileDimension; row++){

                int scaledcol = col * this.originalPicture.width() / this.tileDimension;
                int scaledrow = row * this.originalPicture.height() / this.tileDimension;
                Color color = this.originalPicture.get(scaledcol, scaledrow);
                scaled.set(col, row, color);
            }
        }

        int fcol = 0;
        for (int col = 0; col < (this.tileDimension * this.collageDimension); col++){

            if (fcol == this.tileDimension){
                fcol = 0;
            }
            
            int frow = 0;
            for (int row = 0; row < (this.tileDimension * this.collageDimension); row++){

                if (frow == this.tileDimension){
                    frow = 0;
                }
                Color color = scaled.get(fcol, frow);
                this.collagePicture.set(col, row, color);
                frow++;
            }
            fcol++;
        }
		
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        int red = 0;
        
        int green = 0;
       
        int blue = 0;

        int replaceColumn = collageCol * this.tileDimension;
       
        int replaceRow = collageRow * this.tileDimension;
        
        for (int col = replaceColumn; col < (replaceColumn + this.tileDimension); col++){
            for (int row = replaceRow; row < (replaceRow + this.tileDimension); row++){
                
                Color color = this.collagePicture.get(col, row);

                if (component.equals("red")){
                    red = color.getRed();
                }
                      else if (component.equals("green") ){
                    green = color.getGreen();
                }
                  else if (component.equals("blue") ){
                   blue = color.getBlue(); 
                }

                this.collagePicture.set(col, row, new Color(red, green, blue));
            }
        }

    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {

        int replaceColumn = collageCol * this.tileDimension;
        int replaceRow = collageRow * this.tileDimension;

        Picture temp = new Picture(filename);
        Picture scaled = new Picture(this.tileDimension, this.tileDimension);

        for (int col = 0; col < this.tileDimension; col++){
            for (int row = 0; row < this.tileDimension; row++){

                int scol = col * temp.width() / this.tileDimension;
                int srow = row * temp.height() / this.tileDimension;
                Color color = temp.get(scol, srow);
                scaled.set(col, row, color);
            }
        }

        for (int col = 0; col < this.tileDimension; col++){
            replaceRow = collageRow * this.tileDimension;
            
            for (int row = 0; row < this.tileDimension; row++){

                Color color = scaled.get(col, row);
                this.collagePicture.set(replaceColumn, replaceRow, color);
                replaceRow++;
            }

            replaceColumn++;
        }

    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void grayscaleTile (int collageCol, int collageRow) {

        int replaceColumn = collageCol * this.tileDimension;
        int replaceRow = collageRow * this.tileDimension;
        
        for (int col = replaceColumn; col < (replaceColumn + this.tileDimension); col++){
            for (int row = replaceRow; row < (replaceRow + this.tileDimension); row++){

                Color color = this.collagePicture.get(col, row);
                Color gray = toGray(color);
                this.collagePicture.set(col, row, gray);
            }
        }
    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        if (red == green && red == blue) return red;   // to avoid floating-point issues
        return 0.299*red + 0.587*green + 0.114*blue;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return a grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}
