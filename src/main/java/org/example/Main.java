package org.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static class Share {
        private int x;
        private BigInteger y;

        public Share(int x, BigInteger y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public BigInteger getY() {
            return y;
        }
    }


    public static BigInteger reconstructSecret(List<Share> shares) {
        BigInteger secret = BigInteger.ZERO;
        int k = shares.size();

        for (int j = 0; j < k; j++) {
            BigInteger num = BigInteger.ONE;
            BigInteger denom= BigInteger.ONE;

            for (int m = 0; m < k; m++) {
                if (m != j) {
                    int xj = shares.get(j).getX();
                    int xm = shares.get(m).getX();

                    num = num.multiply(BigInteger.valueOf(-xm));
                    denom= denom.multiply(BigInteger.valueOf(xj - xm));
                }
            }

            BigInteger lj = num.divide(denom);
            secret = secret.add(shares.get(j).getY().multiply(lj));
        }

        return secret;
    }

    public static void main(String[] args) {

        List<Share> sh = new ArrayList<>();
        sh.add(new Share(1, new BigInteger("12345678901234567890")));
        sh.add(new Share(2, new BigInteger("22345678901234567890")));
        sh.add(new Share(3, new BigInteger("32345678901234567890")));

        for (Share share : sh) {
            System.out.println("x = " + share.getX() + ", y = " + share.getY());
        }


        BigInteger secret = reconstructSecret(sh);
        System.out.println("\nC is " + secret);
    }
}
