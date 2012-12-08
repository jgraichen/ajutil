/*
 * A Java Utility Library
 * 
 * Copyright (C) 2010-2012 Jan Graichen <jg@altimos.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed along with this work for further
 * information.
 */
package de.altimos.util.noise;

import java.util.Random;

/**
 * An improved implementation of the improved perlin noise java implementation
 * of Ken Perlin. Supports double and single precision operations.
 * 
 * The low-leve noise generating functions {@link #noise(double, double, double)}
 * and {@link #noiseF(float, float, float)} act like a singleton and need to be
 * initialized before first usage by {@link #reinitialize()} or
 * {@link #reinitialize(long)}.
 * 
 * @author Christian Nicolai <chrnicolai@gmail.com>
 */
public final class NoiseUtil {

    private NoiseUtil() {}

    //
    // Origin of Ken Perlins hash function
    //

//    /**
//     * A very clever function using only the cube corner hash values to
//     * determine the gradient finally "dot-producted" with the incomming
//     * point (x,y,z)
//     */
//    private static double grad(int hash, double x, double y, double z) {
//        int h = hash & 15;                      // CONVERT LO 4 BITS OF HASH CODE
//        double u = h<8 ? x : y,                 // INTO 12 GRADIENT DIRECTIONS.
//			   v = h<4 ? y : h==12||h==14 ? x : z;
//        return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
//    }
//
//    private static float[] gradients = {
//        1,1,0,    -1,1,0,    1,-1,0,    -1,-1,0,
//        1,0,1,    -1,0,1,    1,0,-1,    -1,0,-1,
//        0,1,1,    0,-1,1,    0,1,-1,    0,-1,-1,
//        1,1,0,    0,-1,1,    -1,1,0,    0,-1,-1,
//    };
//
//    private static double grad_(double hash, double x, double y, double z) {
//        Vector3d p = new Vector3d(x, y, z);
//        int h = (int) Math.round(hash);
//        h = h & 15;
//        h *= 3;
//        Vector3d gr = new Vector3d(gradients[h], gradients[h+1], gradients[h+2]);
//        // key is the dot product used:
//        return p.dot(gr);
//    }

    public static double noise(double x, double y, double z) {
        double a = Math.floor(x);
        double b = Math.floor(y);
        double c = Math.floor(z);

        int X = (int)a & 255;
        int Y = (int)b & 255;
        int Z = (int)c & 255;
        x -= a;
        y -= b;
        z -= c;
        int A = p[X  ]+Y, AA = p[A]+Z, AB = p[A+1]+Z;
        int B = p[X+1]+Y, BA = p[B]+Z, BB = p[B+1]+Z;

        double u = fade(x);
        double v = fade(y);
        double w = fade(z);
        return mix(w, mix(v, mix(u, grad(p[AA  ], x  , y  , z   ),
                                    grad(p[BA  ], x-1, y  , z   )),
                             mix(u, grad(p[AB  ], x  , y-1, z   ),
                                    grad(p[BB  ], x-1, y-1, z   ))),
                      mix(v, mix(u, grad(p[AA+1], x  , y  , z-1 ),
                                    grad(p[BA+1], x-1, y  , z-1 )),
                             mix(u, grad(p[AB+1], x  , y-1, z-1 ),
                                    grad(p[BB+1], x-1, y-1, z-1 ))));
    }

    public static float noiseF(float x, float y, float z) {
        // Sourced this computation out to gain performance:
        double a = Math.floor(x);
        double b = Math.floor(y);
        double c = Math.floor(z);

        int X = (int)a & 255;
        int Y = (int)b & 255;
        int Z = (int)c & 255;
        x -= a;
        y -= b;
        z -= c;
        int A = p[X  ]+Y, AA = p[A]+Z, AB = p[A+1]+Z;
        int B = p[X+1]+Y, BA = p[B]+Z, BB = p[B+1]+Z;

        float u = fadeF(x);
        float v = fadeF(y);
        float w = fadeF(z);
        return mixF(w, mixF(v, mixF(u, gradF(p[AA  ], x  , y  , z   ),
                                       gradF(p[BA  ], x-1, y  , z   )),
                               mixF(u, gradF(p[AB  ], x  , y-1, z   ),
                                       gradF(p[BB  ], x-1, y-1, z   ))),
                       mixF(v, mixF(u, gradF(p[AA+1], x  , y  , z-1 ),
                                       gradF(p[BA+1], x-1, y  , z-1 )),
                               mixF(u, gradF(p[AB+1], x  , y-1, z-1 ),
                                       gradF(p[BB+1], x-1, y-1, z-1 ))));
    }

    /**
     * Uses a fifth order polynom to avoid the second order discontinuity
     */
    private static double fade(double x) {
        return x*x*x*(x*(x*6-15)+10);
    }
    private static float fadeF(float x) {
        return x*x*x*(x*(x*6-15)+10);
    }

    /**
     * Performs a linear blend operation of a and b based on t
     */
    public  static double mix(double x, double a, double b) {
        return a + x*(b-a);
    }
    public  static float mixF(float x, float a, float b) {
        return a + x*(b-a);
    }

    /**
     * This function are using a hard-coded switch-statement-table
     * to determine the result only based on corners hash value.
     *
     * This is up to 3 times faster the Ken Perlins original approach!
     */
    private static double grad(int hash, double x, double y, double z) {
        switch(hash & 15) {
            case 0: return x+y;
            case 1: return -x+y;
            case 2: return x-y;
            case 3: return -x-y;

            case 4: return x+z;
            case 5: return -x+z;
            case 6: return x-z;
            case 7: return -x-z;

            case 8: return y+z;
            case 9: return -y+z;
            case 10: return y-z;
            case 11: return -y-z;

            case 12: return x+y;
            case 13: return -y+z;
            case 14: return -x+y;
            case 15: return -y-z;
        }

        throw new IllegalArgumentException("Should never reach here!");
    }

    /**
     * This function are using a hard-coded switch-statement-table
     * to determine the result only based on corners hash value.
     *
     * This is up to 3 times faster the Ken Perlins original approach!
     */
    private static float gradF(int hash, float x, float y, float z) {
        switch(hash & 15) {
            case 0: return x+y;
            case 1: return -x+y;
            case 2: return x-y;
            case 3: return -x-y;

            case 4: return x+z;
            case 5: return -x+z;
            case 6: return x-z;
            case 7: return -x-z;

            case 8: return y+z;
            case 9: return -y+z;
            case 10: return y-z;
            case 11: return -y-z;

            case 12: return x+y;
            case 13: return -y+z;
            case 14: return -x+y;
            case 15: return -y-z;
        }

        throw new IllegalArgumentException("Should never reach here!");
    }

    public static int p[] = new int[512];

    /**  
	 * Reinitialize with a default seed source: millisecond time.
	 */
    public static void reinitialize() {
		reinitialize(System.currentTimeMillis());
	}

    /**  
	 * Reinitialize the permutation array based on seed for the Random.
	 */
	public static void reinitialize(long seed) {
		Random generator = new Random(seed);

        int[] tmp = new int[256];

        for(int i = 0; i < 256; i++) {
            tmp[i] = i;
        }

        // Shuffle the permutation values to create a repeatable
        // "random" series of integers. 
        for(int i = 0; i < 256; i++) {
            int j = generator.nextInt(255);
            int k = tmp[i];
            tmp[i] = tmp[j];
            tmp[j] = k;
        }

        for(int i = 0; i < 256; i++) {
            p[256+i] = p[i] = tmp[i];
        }
	}

	/** 
	 * A fast implementation of fractional Brownian motion.
	 * This implementation assumes an integer number of octaves and
	 * a lacunarity of two.  This simplification is done for speed.
	 */
	public static double fBm(double x, double y, double z, int H, int octaves) {
		double sum = 0;
		for (int i = 0; i < octaves; i++) {
			sum += noise(x, y, z) / (1 << H * i);
			x *= 2;
			y *= 2;
			z *= 2;
		}
		return sum;
	}

	/**
	 * A simple implementation of turbulance.
	 * This implementation assumes an integer number of octaves and
	 * a lacunarity of two.  This simplification is done for speed.
	 */
	public static double turbulance(double x, double y, double z, int octaves) {
		int frequency = 1;
		double sum = 0;
		for (int octave = 0; octave < octaves; octave++) {
			sum = sum + Math.abs(noise(x, y, z)) / frequency;
			x *= 2;
			y *= 2;
			z *= 2;
			frequency <<= 1;
		}
		return sum;
	}

	/**
	 * An implementation to support the generation of a wood grain.
	 */
	public static double wood(double x, double y, double z) {
		double noise = noise(x, y, z) * 15.0;
        return noise - Math.floor(noise);
	}

	/**
	 * Based on the classic (circa 1985) Perlin  
	 * implementation for generating a marble pattern.
	 */
	public static double marble(double x, double y, double z, int octaves) {
        double t = turbulance(x, y, z, octaves);
        return Math.sin(y + t);
	}

	/**
	 * A simple implementation of a ridged multifractal noise 
	 * function.  This implementation assumes an integer number 
	 * of octaves, a lacunarity of 2.0, and an H factor of 1.0.
	 * This simplification is done for speed.
	 */
	public static double ridged(double x, double y, double z, int octaves, double offset, double gain) {
		double sum = 0;
		double signal;
		double weight = 1.0;
		int frequency = 1;

		for (int octave = 0; octave < octaves; octave++) {
			signal = offset - Math.abs(noise(x, y, z));
			// Square the signal to sharpen the ridges
			signal = signal * signal * weight;
			sum = sum + signal / frequency;
			// weight successive contributions by previous signal			
			weight = signal * gain;
			// Clamp the weight 0..1
			if (weight > 1.0)
				weight = 1.0;
			if (weight < 0.0)
				weight = 0.0;
			x = x * 2;
			y = y * 2;
			z = z * 2;
			frequency = frequency << 1;
		}

		return sum;
	}

    // better
    public static float turbulance2(float x, float y, float z, int octaves) {
		int frequency = 1;
		float sum = 0;
		for(int i = 0; i < octaves; i++) {
			sum += (noiseF(x*frequency, y*frequency, z*frequency)+1)/2 / frequency;
            frequency <<= 1;
		}
		return sum;
	}

    // typically: octaves >= 4, lacunarity ~= 2.0, gain = 0.5;
    public static float fBm(float x, float y, float z, int octaves, float lacunarity, float gain) {
		float sum = 0;
        float amp = 1;
		for(int i = 0; i < octaves; i++) {
			sum += amp * noiseF(x, y, z);
            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
            amp *= gain;
		}
		return sum;
	}

    // only a proxy
    public static float turbulance(float x, float y, float z, int octaves, float lacunarity, float gain) {
        return fBm(x,y,z,octaves,lacunarity,gain);
	}

}
