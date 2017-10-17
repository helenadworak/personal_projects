import java.awt.Color;
import javalib.funworld.*;
import javalib.worldimages.*;
import tester.*;

// to represent a fish
abstract class Fish {
    Posn center;
    int size;
    final int FISHY_WORLD_SIZE = 500;

    /*
   * Template: Fields: this.center ... Posn this.size ... int
   * this.FISHY_WORLD_SIZE ... int methods: this.getX() ... int this.getY() ...
   * int this.isBigger(Fish) ... boolean methods for fields: this.center.getX()
   * ... int this.center.getY() ... int
   */

    Fish(Posn center, int size) {
        this.center = center;
        this.size = size;
    }

    // produces the x position of this Fish
    int getX() {
        return this.center.x;
    }

    // produces the y position of this Fish
    int getY() {
        return this.center.y;
    }

    // determines if this fish is larger than the given one
    boolean isBigger(Fish that) {
        return this.size > that.size;
    }
}

// to represent the fishplayer
class FishyPlayer extends Fish {

    /*
   * TEMPLATE: FIELDS: this.center ... Posn this.size ... int METHODS:
   * this.fishyPlayerImage() ... WorldImage this.moveFishyPlayer(String) ...
   * FishyPlayer this.outsideBounds(FishyPlayer) ... FishyPlayer
   * this.hittingFish(FishyRandom) ... boolean this.eatFish(ILoFishy) ...
   * FishyPlayer this.addedSize(FishyRandom) ... int METHODS FOR FIELDS:
   * this.center.getX() ... int this.center.getY() ... int
   */

    /**
     * The constructor
     */
    FishyPlayer(Posn center, int size) {
        super(center, size);
    }

    /**
     * produce the image of this player
     */
    WorldImage fishyPlayerImage() {
        //make our fishy image a square
        return new RectangleImage(this.size, this.size, "solid", Color.YELLOW);
    }

    /**
     * move this player 10 pixels in the direction given by the ke or change its
     */
    public FishyPlayer moveFishyPlayer(String ke) {
        if (ke.equals("right")) {
            return this.outsideBounds(new FishyPlayer(new Posn(this.getX() + 15, this.getY()), this.size));
        }
        else if (ke.equals("left")) {
            return this.outsideBounds(new FishyPlayer(new Posn(this.getX() - 15, this.getY()), this.size));
        }
        else if (ke.equals("up")) {
            return this.outsideBounds(new FishyPlayer(new Posn(this.getX(), this.getY() - 15), this.size));
        }
        else if (ke.equals("down")) {
            return this.outsideBounds(new FishyPlayer(new Posn(this.getX(), this.getY() + 15), this.size));
        }
        else {
            return this;
        }
    }

    // ensures that when this FishPlayer leaves the bounds of the game, the reappear on the
    // opposite side
    public FishyPlayer outsideBounds(FishyPlayer player) {
        if (player.getX() > (FISHY_WORLD_SIZE / 2)) {
            return new FishyPlayer(new Posn((FISHY_WORLD_SIZE / -2), this.getY()), this.size);
        } else if (player.getX() < (FISHY_WORLD_SIZE / -2)) {
            return new FishyPlayer(new Posn((FISHY_WORLD_SIZE / 2), this.getY()), this.size);
        } else if (player.getY() > (FISHY_WORLD_SIZE / 2)) {
            return new FishyPlayer(new Posn(this.getX(), (FISHY_WORLD_SIZE / -2)), this.size);
        } else if (player.getY() < (FISHY_WORLD_SIZE / -2)) {
            return new FishyPlayer(new Posn(this.getX(), (FISHY_WORLD_SIZE / 2)), this.size);
        } else {
            return player;
        }
    }

    // determines if this FishyPlayer is touching a Random Fish
    boolean hittingFish(FishyRandom ran) {
        double distance = Math.sqrt(Math.pow(this.getX() - ran.getX(), 2) + Math.pow(this.getY() -
                ran.getY(), 2));
        double halfSizes = (this.size / 2) + (ran.size / 2);
        return distance <= halfSizes;
    }

    // Returns a new FishPlayer with the size of the eaten fish added to this fish's size
    FishyPlayer eatFish(ILoFishy randFish) {
        return new FishyPlayer(this.center, this.size + randFish.getSizeEaten(this));
    }

    // produces size needed to be added to fishPlayer.size after eating a fish
    int addedSize(FishyRandom fish) {
        if (this.isBigger(fish) && this.hittingFish(fish)) {
            return fish.size;
        }
        else {
            return 0;
        }
    }
}

// represents the background fish
class FishyRandom extends Fish {
    Color color;
    boolean direction;

    /*
   * TEMPLATE: FIELDS: this.center ... Posn this.size ... int this.color ... Color
   * this.direction ... boolean METHODS: this.fishyRandomImage() ... WorldImage
   * this.moveFish() ... FishyRandom this.wrapFish() ... FishyRandom
   * METHODS FOR FIELDS: this.center.getX() ... int this.center.getY() ... int
   */

    FishyRandom(Posn center, int size, Color color, boolean direction) {
        super(center, size);
        this.color = color;
        this.direction = direction;
    }

    // produces the WorldImage of this random fish
    WorldImage fishyRandomImage() {
        return new RectangleImage(size, size, OutlineMode.SOLID, color);
    }

    // moves this FishyRandom left to right
    FishyRandom moveFish() {
        int stepSize = 10 - (this.size / 10);
        if (direction) {
            return new FishyRandom(new Posn(this.getX() + stepSize,
                    this.getY()), size, color, direction).wrapFish();
        }
        else {
            return new FishyRandom(new Posn(this.getX() - stepSize,
                    this.getY()), size, color, direction).wrapFish();
        }
    }

    // insures a fish that comes in one side leaves through the other
    public FishyRandom wrapFish() {
        if (this.getX() > (FISHY_WORLD_SIZE / 2)) {
            return new FishyRandom(new Posn((FISHY_WORLD_SIZE / -2), this.getY()),
                    this.size, this.color, this.direction);
        }
        else if (this.getX() < (FISHY_WORLD_SIZE / -2)) {
            return new FishyRandom(new Posn((FISHY_WORLD_SIZE / 2), this.getY()),
                    this.size, this.color, this.direction);
        }
        else if (this.getY() > (FISHY_WORLD_SIZE / 2)) {
            return new FishyRandom(new Posn(this.getX(), (FISHY_WORLD_SIZE / -2)),
                    this.size, this.color, this.direction);
        }
        else if (this.getY() < (FISHY_WORLD_SIZE / -2)) {
            return new FishyRandom(new Posn(this.getX(), (FISHY_WORLD_SIZE / 2)),
                    this.size, this.color, this.direction);
        }
        else {
            return this;
        }
    }
}

// to represent a list of fish
interface ILoFishy {
    // produces the image of the random fish in this list
    WorldImage randomFishesImage();

    // determines if this fish has been eaten by the FishPLayer
    boolean eatPlayer(FishyPlayer player);

    // determines if this List is empty
    boolean isEmpty();

    // returns a new list of fish with the eaten fish removed
    ILoFishy removeEaten(FishyPlayer player);

    // returns this list of fish when they move
    ILoFishy moveFish();

    // produces the size needed to add to the FishPlayer when this fish is eaten
    int getSizeEaten(FishyPlayer fish);
}

class MtLoFishy implements ILoFishy {

    /*
   * TEMPLATE: METHODS: this.randomFishesImage() ... WorldImage
   * this.eatPlayer(FishyPlayer) ... boolean this.removeEaten(FishyPlayer) ...
   * ILoFishy this.moveFish() ... ILOFishy this.getSizeEaten() ... int
   */

    MtLoFishy() {
    }

    // produces the image of the random fish in this list
    @Override
    public WorldImage randomFishesImage() {

        return new RectangleImage(500, 500, OutlineMode.OUTLINE, Color.CYAN);
    }

    // determines if this fish has been eaten by the FishPLayer
    @Override
    public boolean eatPlayer(FishyPlayer player) {
        return false;
    }

    // determines if this List is empty
    @Override
    public boolean isEmpty() {
        return true;
    }

    // returns a new list of fish with the eaten fish removed
    @Override
    public ILoFishy removeEaten(FishyPlayer player) {
        return this;
    }

    // returns this list of fish when they move
    @Override
    public ILoFishy moveFish() {
        return this;
    }

    // produces the size needed to add to the FishPlayer when this fish is eaten
    @Override
    public int getSizeEaten(FishyPlayer fish) {
        return 0;
    }
}

class ConsLoFishy implements ILoFishy {
    FishyRandom first;
    ILoFishy rest;

    /*
   * TEMPLATE: FIELDS: this.first ... FishyRandom this.rest ... ILoFishy METHODS:
   * this.randomFishesImage() ... WorldImage this.eatPlayer(FishyPlayer) ...
   * boolean this.removeEaten(FishyPlayer) ... ILoFishy this.moveFish() ...
   * ILOFishy this.getSizeEaten() ... int METHODS FOR FIELDS:
   * this.rest.randomFishesImage() ... WorldImage this.rest.eatPlayer(FishyPlayer)
   * ... boolean this.rest.removeEaten(FishyPlayer) ... ILoFishy
   * this.rest.moveFish() ... ILOFishy this.rest.getSizeEaten() ... int
   * this.first.fishyRandomImage() ... WorldImage this.first.moveFish() ...
   * FishyRandom this.first.wrapFish(FishyRandom) ... FishyRandom
   */

    ConsLoFishy(FishyRandom first, ILoFishy rest) {
        this.first = first;
        this.rest = rest;
    }

    // produces the image of the random fish in this list
    @Override
    public WorldImage randomFishesImage() {
        return new OverlayOffsetImage(this.first.fishyRandomImage(),
                -(this.first.getX()), -(this.first.getY()), this.rest.randomFishesImage());
    }

    // determines if this fish has been eaten by the FishPLayer
    @Override
    public boolean eatPlayer(FishyPlayer player) {
        return (player.hittingFish(this.first) && this.first.isBigger(player))
                || this.rest.eatPlayer(player);
    }

    // determines if this List is empty
    @Override
    public boolean isEmpty() {
        return false;
    }

    // returns a new list of fish with the eaten fish removed
    @Override
    public ILoFishy removeEaten(FishyPlayer player) {
        if (player.hittingFish(this.first) && player.isBigger(this.first)) {
            return this.rest;
        }
        else {
            return new ConsLoFishy(this.first, this.rest.removeEaten(player));
        }
    }

    // returns this list of fish when they move
    @Override
    public ILoFishy moveFish() {
        return new ConsLoFishy(this.first.moveFish(), this.rest.moveFish());
    }

    // produces the size needed to add to the FishPlayer when this fish is eaten
    @Override
    public int getSizeEaten(FishyPlayer fish) {
        return fish.addedSize(this.first) + this.rest.getSizeEaten(fish);
    }
}

/**
 * Represent the world of a Fishy
 */
class FishyWorld extends World {

    final int FISHY_WORLD_SIZE = 500;
    FishyPlayer player;
    ILoFishy randomFish;

    /**
     * The constructor
     */
    public FishyWorld(FishyPlayer player, ILoFishy randomFish) {
        super();
        this.player = player;
        this.randomFish = randomFish;
    }

    /**
     * Moves FishPlayer on key event
     */
    public World onKeyEvent(String ke) {
        if (ke.equals("x")) {
            return this.endOfWorld("Goodbye");
        }
        else {
            return new FishyWorld(this.player.moveFishyPlayer(ke), this.randomFish); //do we pass in random fish??
        }
    }

    /**
     * Calls methods for eating, and removing eating on each tick of World
     */
    public World onTick() {
        return new FishyWorld(this.player.eatFish(this.randomFish),
                this.randomFish.removeEaten(this.player).moveFish());
    }

    /**
     * The entire background image for this world
     */
    public WorldImage background = new RectangleImage(FISHY_WORLD_SIZE,
            FISHY_WORLD_SIZE, OutlineMode.SOLID, Color.CYAN);

    /**
     * produce the image of this world by adding the moving player to the background image
     */
    public WorldScene makeScene() {
        return this
                .getEmptyScene()
                .placeImageXY(this.background, FISHY_WORLD_SIZE / 2, FISHY_WORLD_SIZE / 2)
                .placeImageXY(this.randomFish.randomFishesImage(), FISHY_WORLD_SIZE / 2, FISHY_WORLD_SIZE / 2)
                .placeImageXY(this.player.fishyPlayerImage(), ((FISHY_WORLD_SIZE / 2) + this.player.getX()),
                        ((FISHY_WORLD_SIZE / 2) + this.player.getY()));
    }

    /**
     * produce the last image of this world by adding text to the image
     */
    public WorldScene lastScene(String s) {
        return this.makeScene().placeImageXY(new TextImage(s, Color.red), 100,
                40);
    }

    // checks if this FishPlayer has been eaten or eaten all of the fish in the World
    public WorldEnd worldEnds() {
        if (this.randomFish.isEmpty()) {
            return new WorldEnd(true, this.makeScene().placeImageXY(
                    new TextImage("Congrats you win!", 13, FontStyle.BOLD_ITALIC, Color.red),
                    100, 40));
        }
        else if (this.randomFish.eatPlayer(this.player)) {
            return new WorldEnd(true, this.makeScene().placeImageXY(
                    new TextImage("You were eaten. Sux to suxk.", 13, FontStyle.BOLD_ITALIC, Color.red),
                    100, 40));
        }
        else {
            return new WorldEnd(false, this.makeScene());
        }
    }
}

// examples of Fish
class ExamplesFishy {

    FishyRandom randtest1 = new FishyRandom(new Posn(75, -150), 75, Color.WHITE, true);
    FishyRandom randtest2 = new FishyRandom(new Posn(150, -150), 40, Color.GREEN, false);
    FishyRandom randtest3 = new FishyRandom(new Posn(40, 40), 15, Color.GRAY, true);
    FishyRandom randtest4 = new FishyRandom(new Posn(-40, -40), 20, Color.PINK, true);
    FishyRandom randtest5 = new FishyRandom(new Posn(-200, 200), 15, Color.RED, false);
    FishyRandom randtest6 = new FishyRandom(new Posn(-130, 45), 25, Color.ORANGE, false);
    FishyRandom randtest7 = new FishyRandom(new Posn(-255, 45), 25, Color.ORANGE, false);
    ILoFishy testList = new ConsLoFishy(randtest6, new ConsLoFishy(randtest5, new ConsLoFishy(randtest4,
            new ConsLoFishy(randtest3, new ConsLoFishy(randtest2, new ConsLoFishy(randtest1, new MtLoFishy()))))));
    ILoFishy testList2 = new ConsLoFishy(randtest6, new ConsLoFishy(randtest5, new ConsLoFishy(randtest4,
            new ConsLoFishy(randtest2, new ConsLoFishy(randtest1, new MtLoFishy())))));
    ILoFishy mtList = new MtLoFishy();
    ILoFishy smallList = new ConsLoFishy(randtest1, mtList);
    FishyPlayer player = new FishyPlayer(new Posn(0, 0), 10);
    FishyPlayer player2 = new FishyPlayer(new Posn(30, 40), 20);
    FishyPlayer player3 = new FishyPlayer(new Posn(240, 240), 10);
    FishyPlayer player4 = new FishyPlayer(new Posn(30, 40), 9);
    FishyWorld emptyWorld = new FishyWorld(player, mtList);
    FishyWorld nonEmptyWorld = new FishyWorld(player, testList);

    public static void main(String[] args) {

        // run the tests - showing only the failed test results
        ExamplesFishy ef = new ExamplesFishy();
        Tester.runReport(ef, false, false);

        FishyRandom test1 = new FishyRandom(new Posn(75, -150), 75, Color.WHITE, true);
        FishyRandom test2 = new FishyRandom(new Posn(150, -150), 40, Color.GREEN, false);
        FishyRandom test3 = new FishyRandom(new Posn(40, 40), 10, Color.GRAY, true);
        FishyRandom test4 = new FishyRandom(new Posn(-40, -40), 20, Color.PINK, true);
        FishyRandom test5 = new FishyRandom(new Posn(-200, 200), 15, Color.RED, false);
        FishyRandom test6 = new FishyRandom(new Posn(-130, 45), 25, Color.ORANGE, false);
        ILoFishy fishList = new ConsLoFishy(test6, new ConsLoFishy(test5, new ConsLoFishy(test4,
                new ConsLoFishy(test3, new ConsLoFishy(test2, new ConsLoFishy(test1, new MtLoFishy()))))));

        // run the game
        FishyWorld w = new FishyWorld(new FishyPlayer(new Posn(0, 0), 11), fishList);
        //FishyWorld w = new FishyWorld(new FishyPlayer(new Posn(0, 0), 10), new MtLoFishy());
        w.bigBang(500, 500, 0.1);

        /*
         * Canvas c = new Canvas(200, 300); c.show();
         * System.out.println(" let's see: \n\n" +
         * Printer.produceString(w.makeImage())); c.drawImage(new
         * OverlayImages(new CircleImage(new Posn(50, 50), 20, Color.RED), new
         * RectangleImage(new Posn(20, 30), 40, 20, Color.BLUE)));
         */
    }

    //test the getter methods
    boolean testGetters(Tester t) {
        return t.checkExpect(this.player.getX(), 0)
                && t.checkExpect(this.player.getY(), 0)
                && t.checkExpect(this.randtest3.getX(), 40)
                && t.checkExpect(this.randtest3.getY(), 40);
    }

    //tests the bigger method
    boolean testIsBigger(Tester t) {
        return t.checkExpect(this.player.isBigger(this.randtest2), false)
                && t.checkExpect(this.randtest2.isBigger(this.player), true);
    }

    //tests all fishy images
    boolean testFishyImage(Tester t) {
        return t.checkExpect(player.fishyPlayerImage(),
                new RectangleImage(10, 10, OutlineMode.SOLID, Color.YELLOW))
                && t.checkExpect(randtest3.fishyRandomImage(),
                new RectangleImage(15, 15, OutlineMode.SOLID, Color.GRAY));
    }

    //tests if we move the fishy player out of bounds
    boolean testMoveFishAndOutsideBounds(Tester t) {
        return t.checkExpect(this.player.moveFishyPlayer("right"),
                new FishyPlayer(new Posn(this.player.getX() + 15, this.player.getY()), this.player.size))
                && t.checkExpect(this.player.moveFishyPlayer("left"),
                new FishyPlayer(new Posn(this.player.getX() - 15, this.player.getY()), this.player.size))
                && t.checkExpect(this.player.moveFishyPlayer("up"),
                new FishyPlayer(new Posn(this.player.getX(), this.player.getY() - 15), this.player.size))
                && t.checkExpect(this.player.moveFishyPlayer("down"),
                new FishyPlayer(new Posn(this.player.getX(), this.player.getY() + 15), this.player.size))
                && t.checkExpect(this.player3.moveFishyPlayer("down"),
                new FishyPlayer(new Posn(this.player3.getX(), -250), this.player.size))
                && t.checkExpect(this.player3.moveFishyPlayer("right"),
                new FishyPlayer(new Posn(-250, this.player3.getY()), this.player.size));
    }

    //tests if two fish are hitting each other
    boolean testHittingFish(Tester t) {
        return t.checkExpect(this.player.hittingFish(this.randtest2), false)
                && t.checkExpect(this.player.hittingFish(this.randtest2), false)
                && t.checkExpect(this.player.hittingFish(this.randtest3), false)
                && t.checkExpect(this.player2.hittingFish(this.randtest3), true);
    }

    //tests when this player eats a fish
    boolean testEatFish(Tester t){
        return t.checkExpect(player.eatFish(testList), player)
                && t.checkExpect(player2.eatFish(testList), new FishyPlayer(this.player2.center, 20));
    }

    //tests added size method
    boolean testAddedSize(Tester t) {
        return t.checkExpect(player.addedSize(randtest3), 0)
                && t.checkExpect(player2.addedSize(randtest3), 15);
    }

    //tests move fish method
    boolean testMoveFish(Tester t) {
        return t.checkExpect(randtest1.moveFish(),
                new FishyRandom(new Posn(78, -150), 75, Color.WHITE, true))
                && t.checkExpect(randtest2.moveFish(),
                new FishyRandom(new Posn(144, -150), 40, Color.GREEN, false));

    }

    boolean testWrap(Tester t) {
        return t.checkExpect(randtest1.wrapFish(), randtest1)
                && t.checkExpect(randtest7.wrapFish(),
                new FishyRandom(new Posn(250, 45), 25, Color.orange, false));
    }

    boolean testListImages(Tester t) {
        return t.checkExpect(mtList.randomFishesImage(),
                new RectangleImage(500, 500, OutlineMode.OUTLINE, Color.CYAN))
                && t.checkExpect(smallList.randomFishesImage(),
                new OverlayOffsetImage(this.randtest1.fishyRandomImage(),
                        -(this.randtest1.getX()), -(this.randtest1.getY()),
                new RectangleImage(500, 500, OutlineMode.OUTLINE, Color.CYAN)));
    }

    boolean testEatPlayer(Tester t) {
        return t.checkExpect(mtList.eatPlayer(player), false)
                && t.checkExpect(testList.eatPlayer(player), false)
                && t.checkExpect(testList.eatPlayer(player4), true);

    }

    boolean testEmpty(Tester t) {
        return t.checkExpect(mtList.isEmpty(), true)
                && t.checkExpect(testList.isEmpty(), false);
    }

    boolean testRemoveEaten(Tester t) {
        return t.checkExpect(testList.removeEaten(player2), testList2)
                && t.checkExpect(testList2.removeEaten(player), testList2)
                && t.checkExpect(mtList.removeEaten(player), mtList);
    }

    boolean testMoveRandFish(Tester t) {
        return t.checkExpect(mtList.moveFish(), mtList)
                && t.checkExpect(smallList.moveFish(),
                new ConsLoFishy(new FishyRandom(new Posn(78, -150), 75, Color.WHITE, true),
                        mtList));
    }

    boolean testSizeOfEaten(Tester t) {
        return t.checkExpect(mtList.getSizeEaten(player), 0)
                && t.checkExpect(smallList.getSizeEaten(player), 0)
                && t.checkExpect(testList.getSizeEaten(player2), 15);
    }

    // test the method worldEnds for the class FishyWorld
    boolean testWorldEnds(Tester t) {
        return t.checkExpect(
                this.emptyWorld.worldEnds(),
                new WorldEnd(true, this.emptyWorld.makeScene().placeImageXY(
                        new TextImage("You were eaten. Sux to suxk.", Color.red),
                        100, 40)))
                && t.checkExpect(this.nonEmptyWorld.worldEnds(), new WorldEnd(false,
                this.nonEmptyWorld.makeScene()));
    }
}