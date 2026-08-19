package Mini_Survival_Game.utilities.noise;

public class Noise {
    private long seed;
    int width, height;


    // Small noise value - high frequency in noise, rough function, more detailed
    // Large noise value - small frequency in noise, smooth function, less detailed
    //

    private double[] noise1;
    private double[] noise4;
    private double[] noise8;
    private double[] noise16;
    private double[] noise32;
    private double[] noise64;
    private double[] noise128;
    private double[] noise512;
    private double[] noise2048;
    private double[] noise8192;


    public Noise(long seed, int offsetX, int offsetY, int width, int height) {
        this.seed = seed;
        this.width = width;
        this.height = height;
    }




}
