package Mini_Survival_Game.utilities.noise;

public class Noise {
    private long seed;
    int width, height;


    // Small noise value - high frequency in noise, rough function, more detailed
    // Large noise value - small frequency in noise, smooth function, less detailed
    //

    private double[] noise1;
    private double[] noise2;
    private double[] noise4;
    private double[] noise8;
    private double[] noise16;
    private double[] noise32;
    private double[] noise64;
    private double[] noise128;
    private double[] noise256;
    private double[] noise512;
    private double[] noise1024;
    private double[] noise2048;
    private double[] noise4096;

    private double[] getNoise1() { if (noise1 == null) { noise1 = new double[width * height];} return noise1;}
    private double[] getNoise2() { if (noise2 == null) { noise2 = new double[width * height];} return noise2;}
    private double[] getNoise4() { if (noise4 == null) { noise4 = new double[width * height]; } return noise4;}
    private double[] getNoise8() { if (noise8 == null) { noise8 = new double[width * height]; } return noise8;}
    private double[] getNoise16() { if (noise16 == null) { noise16 = new double[width * height]; } return noise16;}
    private double[] getNoise32() { if (noise32 == null) { noise32 = new double[width * height]; } return noise32;}
    private double[] getNoise64() { if (noise64 == null) { noise64 = new double[width * height]; } return noise64;}
    private double[] getNoise128() { if (noise128 == null) { noise128 = new double[width * height]; } return noise128;}
    private double[] getNoise256() { if (noise256 == null) { noise256 = new double[width * height]; } return noise256;}
    private double[] getNoise512() { if (noise512 == null) { noise512 = new double[width * height]; } return noise512;}
    private double[] getNoise1024() { if (noise1024 == null) { noise1024 = new double[width * height]; } return noise1024;}
    private double[] getNoise2048() { if (noise2048 == null) { noise2048 = new double[width * height]; } return noise2048;}
    private double[] getNoise4096() { if (noise4096 == null) { noise4096 = new double[width * height]; } return noise4096;}


    public Noise(long seed, int offsetX, int offsetY, int width, int height) {
        this.seed = seed;
        this.width = width;
        this.height = height;

        noise1 = new double[width * height];
        noise2 = new double[width * height];
        noise4 = new double[width * height];
        noise8 = new double[width * height];
        noise16 = new double[width * height];
        noise32 = new double[width * height];
        noise64 = new double[width * height];
        noise128 = new double[width * height];
        noise256 = new double[width * height];
        noise512 = new double[width * height];
        noise1024 = new double[width * height];
        noise2048 = new double[width * height];
        noise4096 = new double[width * height];

        long s1 = seed;
        long s2 = seed + 1000;
        long s4 = seed + 2000;
        long s8 = seed + 3000;
        long s16 = seed + 4000;
        long s32 = seed + 5000;
        long s64 = seed + 6000;
        long s128 = seed + 7000;
        long s256 = seed + 8000;
        long s512 = seed + 9000;
        long s1024 = seed + 10000;
        long s2048 = seed + 11000;
        long s4096 = seed + 12000;

        // Noise generation for width x height
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = x + y * width;

                double rx = x + offsetX;
                double ry = y + offsetY;

                noise1[index] = Simplex.noise2(s1, rx / 1.0, ry / 1.0);
                noise2[index] = Simplex.noise2(s2, rx / 2.0, ry / 2.0);
                noise4[index] = Simplex.noise2(s4, rx / 4.0, ry / 4.0);
                noise8[index] = Simplex.noise2(s8, rx / 8.0, ry / 8.0);
                noise16[index] = Simplex.noise2(s16, rx / 16.0, ry / 16.0);
                noise32[index] = Simplex.noise2(s32, rx / 32.0, ry / 32.0);
                noise64[index] = Simplex.noise2(s64, rx / 64.0, ry / 64.0);
                noise128[index] = Simplex.noise2(s128, rx / 128.0, ry / 128.0);
                noise256[index] = Simplex.noise2(s256, rx / 256.0, ry / 256.0);
                noise512[index] = Simplex.noise2(s512, rx / 512.0, ry / 512.0);
                noise1024[index] = Simplex.noise2(s1024, rx / 1024.0, ry / 1024.0);
                noise2048[index] = Simplex.noise2(s2048, rx / 2048.0, ry / 2048.0);
                noise4096[index] = Simplex.noise2(s4096, rx / 4096.0, ry / 4096.0);
            }
        }
    }

    private double sample(double[] values, int x, int y) {
        return values[x + y * width];
    }

    public double getScale1Noise(int x, int y) { return sample(getNoise1(), x, y); }
    public double getScale2Noise(int x, int y) { return sample(getNoise2(), x, y); }
    public double getScale4Noise(int x, int y) { return sample(getNoise4(), x, y); }
    public double getScale8Noise(int x, int y) { return sample(getNoise8(), x, y); }
    public double getScale16Noise(int x, int y) { return sample(getNoise16(), x, y); }
    public double getScale32Noise(int x, int y) { return sample(getNoise32(), x, y); }
    public double getScale64Noise(int x, int y) { return sample(getNoise64(), x, y); }
    public double getScale128Noise(int x, int y) { return sample(getNoise128(), x, y); }
    public double getScale256Noise(int x, int y) { return sample(getNoise256(), x, y); }
    public double getScale512Noise(int x, int y) { return sample(getNoise512(), x, y); }
    public double getScale1024Noise(int x, int y) { return sample(getNoise1024(), x, y); }
    public double getScale2048Noise(int x, int y) { return sample(getNoise2048(), x, y); }
    public double getScale4096Noise(int x, int y) { return sample(getNoise4096(), x, y); }

    private double octave(int x, int y, double s1, double s2, double s4, double s8, double s16, double s32, double s64, double s128, double s256, double s512, double s1024, double s2048, double s4096) {
        return getScale1Noise(x, y) * s1
                + getScale2Noise(x, y) * s2
                + getScale4Noise(x, y) * s4
                + getScale8Noise(x, y) * s8
                + getScale16Noise(x, y) * s16
                + getScale32Noise(x, y) * s32
                + getScale64Noise(x, y) * s64
                + getScale128Noise(x, y) * s128
                + getScale256Noise(x, y) * s256
                + getScale512Noise(x, y) * s512
                + getScale1024Noise(x, y) * s1024
                + getScale2048Noise(x, y) * s2048
                + getScale4096Noise(x, y) * s4096;
    }

    public double getTemperature(int x, int y) {
        return octave(x, y, 0.01, 0.02, 0.04, 0.08, 0.16, 0.32, 0.64, 0.05, 0.04, 0.01, 0.01, 0.01, 0.01);
    }

    public double getHeight(int x, int y) {
        return octave(x, y, 0.005, 0.01, 0.02, 0.01, 0.02, 0.05, 0.1, 0.2, 0.7, 0.2, 0.01, 0.01, 0.01);
    }

    public double getHumidity(int x, int y) {
        return octave(x, y, 0.02, 0.04, 0.07, 0.1, 0.4, 0.3, 0.1, 0.05, 0.02, 0.01, 0.01, 0.01, 0.01);
    }
}
