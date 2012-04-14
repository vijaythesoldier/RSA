package miniRSA;

import java.util.ArrayList;
import java.util.Random;

public class RSA {

	public static long GCD(long a, long b) {
		long x = a, y = b;
		while(true) {
	        long remainder = x % y;
	        x = y;
	        y = remainder;
	        if (remainder == 0) break;
		}
		return x;
	}
		
	public static long[] complicatedGCD(long a, long b) {
	    long x = GCD(a, b);
	    long y0 = 0, y = 1;
	    long z0 = 1, z = 0;
	    long aa = a, bb = b;
	    while (bb != 0) {
		    long quotient = aa / bb;
		    long temp1 = bb;
		    long temp2 = aa % bb;
		    aa = temp1;
		    bb = temp2;
		    temp1 = y - quotient * y0;
		    temp2 = y0;
		    y0 = temp1;
		    y = temp2;
		    temp1 = z - quotient * z0;
		    temp2 = z0;
		    z0 = temp1;
		    z = temp2;
	    }
    	return new long[]{x, y, z};
	}
	
	public static long coprime(long x) {
		Random rand = new Random();
		while(true) {
			long y = rand.nextLong();
			if (GCD(x, y) == 1)
				return y;
		}
	}
	
	public static long mod_inverse(long base, long m) {
	    long[] ary = complicatedGCD(base, m);
	    if (ary[0] != 1) return 0;
        if (ary[1] < 0)  ary[1] += m;
	    return ary[1];
	}
	
	private static ArrayList<Long> long2baseTwo(long x) {
	    ArrayList<Long> ret = new ArrayList<Long>();
	    long xx = x;
	    while (xx / 2 != 0) {
	    	ret.add(xx % 2);
	    	xx /= 2;
	    }
	    ret.add(xx);
	    return ret;
	}
	
	public static long modulo(long a, long b, long c) {
	    ArrayList<Long> d2base = long2baseTwo(b);
	    long base = a;
	    long base0 = 1;
	    for (int i = 0; i < d2base.size(); i++) {
	        if (d2base.get(i) == 1) {
	            base0 *= base;
	            base0 = base0 % c;
	        }
	        base = (long) Math.pow(base, 2);
	        base = base % c;
	    }
	    return base0;
	}
	
	public static long totient(long n) {
		if (isPrime(n)) return n-1; 
	
		ArrayList<Long[]> fac = factorize(n);
		
		long firstFactor = fac.get(0)[0];
		if (fac.size() == 1) return n - n / firstFactor;
		
		long secondPart = fac.get(1)[0];
		return totient(secondPart) * totient(n/secondPart);
	}
	
	private static ArrayList<Long[]> factorize(long n) {
		long x = 2;
		long count = 0;
		long m = n;
		ArrayList<Long[]> ret = new ArrayList<Long[]>();
		while (x * x <= n) {
			if (n % x == 0 && isPrime(x)) break;
			x++;
		}
		while ((m % x) == 0) {
			count++;
			m = m / x;
		}
		ret.add(new Long[]{x, count});
		if (m != 1) 
			ret.add(new Long[]{(long) m, (long) 1});
		return ret;
	}
	
	private static boolean isPrime(long n) {
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) return false;
		}
		return true;
	}
	
//	public static long endecrypt(long msg_or_cipher, long key, long c) {
//		
//	}

}
