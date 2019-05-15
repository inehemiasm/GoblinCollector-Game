package nehemias.game.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {


  private static final int width = 32, height = 32;
  private static final int gwidth = 50, gheight = 50;


  public static Font font28, FontPlayer, font45;

  public static BufferedImage dirt, grass, stone, tree, rock, fence_sides, fence_up, finishline,
      healthheart;
  public static BufferedImage wood, graveyard;
  public static BufferedImage[] player_down, player_up, player_left, player_right;
  public static BufferedImage[] skeleton_down, skeleton_up, skeleton_left, skeleton_right;
  public static BufferedImage btn_start, btn_exit, btn_help, btn_credits;
  public static BufferedImage inventoryScreen, tile_grass, apple, background;

  public static BufferedImage[] goblin_down, goblin_up, goblin_left, goblin_right, goblin_pushup;
  public static BufferedImage[] goblin2_down, goblin2_up, goblin2_left, goblin2_right,
      goblin_attack_up, goblin_attack_down, goblin_attack_left, goblin_attack_right;
  public static BufferedImage[] coin, treeanim, trees, tree2;
  private static int sizew;



  public static void init() {
    font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);
    font45 = FontLoader.loadFont("res/fonts/slkscrb.ttf", 55);
    FontPlayer = FontLoader.loadFont("res/fonts/slkscr.ttf", 13);

    SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
    // SpriteSheet fruits = new SpriteSheet(ImageLoader.loadImage("/textures/fruitsss.jpg"));
    SpriteSheet arbolito = new SpriteSheet(ImageLoader.loadImage("/textures/trees1.png"));
    SpriteSheet arbolito2 = new SpriteSheet(ImageLoader.loadImage("/textures/fruittree.png"));
    SpriteSheet menuOptions = new SpriteSheet(ImageLoader.loadImage("/textures/startExit.png"));
    SpriteSheet fruits = new SpriteSheet(ImageLoader.loadImage("/textures/apple.png"));
    SpriteSheet end = new SpriteSheet(ImageLoader.loadImage("/textures/finishline1.png"));

    inventoryScreen = ImageLoader.loadImage("/textures/inventoryScreen.png");
    background = ImageLoader.loadImage("/textures/inventorypic.jpeg");
    tile_grass = ImageLoader.loadImage("/textures/fencetilegrass.png");
    fence_sides = ImageLoader.loadImage("/textures/fencetilesides.png");
    fence_up = ImageLoader.loadImage("/textures/fencetile.png");



    wood = sheet.crop(width, height, width, height);
    // apple = fruits.crop(5, 60, 40, 50);
    apple = fruits.crop(0, 0, 50, 50);
    // btn_start = sheet.crop(width * 6, height * 4, width * 2, height);
    btn_start = menuOptions.crop(0, 0, 490, 145);
    btn_help = menuOptions.crop(0, 258, 490, 145);
    btn_exit = menuOptions.crop(0, 850, 490, 145);
    // btn_credits = menuOptions.crop(566, 0, 490, 145);
    finishline = end.crop(0, 0, 255, 255);



    dirt = sheet.crop(width, 0, width, height);
    grass = tile_grass;
    stone = sheet.crop(width * 3, 0, width, height);
    tree = ImageLoader.loadImage("/textures/tree.png");
    rock = sheet.crop(0, height * 2, width, height);
    graveyard = ImageLoader.loadImage("/textures/graveyard.png");

    String arbol = "/textures/trees1.png";
    int arboles = 3;
    trees = new BufferedImage[3];
    treeanim = new BufferedImage[5];
    trees[0] = arbolito.crop(0, 0, 40, height * 2);
    trees[1] = arbolito.crop(40, 0, 40, height * 2);
    trees[2] = arbolito.crop(79, 0, 40, height * 2);


    tree2 = new BufferedImage[3];
    tree2[0] = arbolito2.crop(0, 0, 40, 29);
    tree2[1] = arbolito2.crop(40, 0, 40, 29);
    tree2[2] = arbolito2.crop(79, 0, 40, 29);


    // For Gob_sheet
    String path = "/textures/goblin3.png";
    int imgs = 10;
    goblin_down = new BufferedImage[imgs];
    goblin_up = new BufferedImage[imgs];
    goblin_left = new BufferedImage[imgs];
    goblin_right = new BufferedImage[imgs];
    goblin_pushup = new BufferedImage[5];


    // Cropping sprite sheet to make goblin animations
    int gcurrwidth = 50;
    goblin_up = generateSheets(imgs, 100, gcurrwidth, path);
    goblin_down = generateSheets(imgs, 0, gcurrwidth, path);
    goblin_left = generateSheets(imgs, 156, gcurrwidth, path);
    goblin_right = generateSheets(imgs, 50, gcurrwidth, path);
    goblin_pushup = generateSheets(5, 200, gcurrwidth, path);


    // For d_sheet
    int drags = 6;
    int attacks = 4;
    goblin2_down = new BufferedImage[drags];
    goblin2_up = new BufferedImage[drags];
    goblin2_left = new BufferedImage[drags];
    goblin2_right = new BufferedImage[drags];

    // For d_sheet
    int skels = 8;
    skeleton_down = new BufferedImage[skels];
    skeleton_up = new BufferedImage[skels];
    skeleton_left = new BufferedImage[skels];
    skeleton_right = new BufferedImage[skels];

    // Initialize arrays for attacks
    goblin_attack_down = new BufferedImage[attacks];
    goblin_attack_up = new BufferedImage[attacks];
    goblin_attack_left = new BufferedImage[attacks];
    goblin_attack_right = new BufferedImage[attacks];


    // Cropping Images for movements
    int dwidth = 52;
    String dpath = "/textures/aligned_goblin1.png";
    goblin2_up = generateSheets(drags, 0, dwidth, dpath);
    goblin2_down = generateSheets(drags, (dwidth - 2) * 2, dwidth, dpath);
    goblin2_left = generateSheets(drags, dwidth - 8, dwidth, dpath);
    goblin2_right = generateSheets(drags, (dwidth - 2) * 3, dwidth, dpath);

    // Cropping Images for attacks
    goblin_attack_up = generateSheets(attacks, (dwidth - 1) * 4, dwidth, dpath);
    goblin_attack_down = generateSheets(attacks, (dwidth - 1) * 6, dwidth, dpath);
    goblin_attack_left = generateSheets(attacks, (dwidth - 1) * 5, dwidth, dpath);
    goblin_attack_right = generateSheets(attacks, (dwidth - 1) * 7, dwidth, dpath);


    // Coin goldanim.png
    String coins = "/textures/goldanim.png";
    coin = new BufferedImage[9];
    coin = generateSheets(9, 0, dwidth, coins);

    // Cropping Images for skeleton movements
    int swidth = 50;
    String spath = "/textures/skeleton.png";
    skeleton_up = generateSheets(skels, 0, swidth, spath);
    skeleton_down = generateSheets(skels, (swidth) * 2, swidth, spath);
    skeleton_left = generateSheets(skels, swidth - 3, swidth, spath);
    skeleton_right = generateSheets(skels, (swidth) * 3, swidth, spath);


  }

  public static BufferedImage[] generateSheets(int imgs, int currh, int currw, String url) {
    BufferedImage[] arr = new BufferedImage[imgs];
    SpriteSheet gob_sheet = new SpriteSheet(ImageLoader.loadImage(url));
    sizew = 0;
    for (int i = 0; i < imgs; i++) {
      arr[i] = gob_sheet.crop(sizew, currh, gwidth, gheight);
      sizew += currw;
    }
    return arr;
  }

}
