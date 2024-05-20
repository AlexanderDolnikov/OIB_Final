package Lab1;

import java.math.BigInteger;

public class Lab1_1{

    // Функция для нахождения НОД и коэффициентов x и y с помощью расширенного алгоритма Евклида
    public static BigInteger[] extendedGCD(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};
        }
        BigInteger[] values = extendedGCD(b, a.mod(b));
        BigInteger gcd = values[0];
        BigInteger x = values[2];
        BigInteger y = values[1].subtract(a.divide(b).multiply(values[2]));
        return new BigInteger[]{gcd, x, y};
    }

    // Функция для проверки, что два числа взаимно просты
    public static boolean areCoprime(BigInteger a, BigInteger b) {
        return a.gcd(b).equals(BigInteger.ONE);
    }

    public static void main(String[] args) {
        int n = 21; // замените на нужный номер по журналу

        // Вычисление N и K
        BigInteger N = BigInteger.valueOf((long) Math.floor(Math.sqrt(30202020 + 20190 * n)) + 24 * n);
        BigInteger K = BigInteger.valueOf((long) Math.floor(Math.sqrt(20192019 - 20200 * n)) - 38 * n);

        // Задача 1: НОД(N, K) и коэффициенты x и y
        BigInteger[] gcdResult = extendedGCD(N, K);
        BigInteger gcd = gcdResult[0];
        BigInteger x = gcdResult[1];
        BigInteger y = gcdResult[2];

        System.out.println("Task1:");
        System.out.println("GCD(N, K) = " + gcd);
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println();

        // Задача 2: 2^N mod (2n + 143)
        System.out.println("Task2:");
        BigInteger base = BigInteger.valueOf(2);
        BigInteger exponent = N;
        BigInteger mod = BigInteger.valueOf(2L * n + 143);
        BigInteger result = base.modPow(exponent, mod);

        System.out.println("2^N mod (2n + 143) = " + result);

        // Задача 3: Поиск наименьших чисел, взаимно простых с 2027
        System.out.println("\nTask3:");
        BigInteger coprimeWith2027 = BigInteger.valueOf(2027);

        BigInteger Nprime = N.subtract(BigInteger.ONE);
        while (!areCoprime(Nprime, coprimeWith2027)) {
            Nprime = Nprime.subtract(BigInteger.ONE);
        }

        BigInteger Kprime = K.subtract(BigInteger.ONE);
        while (!areCoprime(Kprime, coprimeWith2027)) {
            Kprime = Kprime.subtract(BigInteger.ONE);
        }

        System.out.println("N' = " + Nprime);
        System.out.println("K' = " + Kprime);

        // Решение уравнения 2027x ≡ K' mod N'
        BigInteger KmodN = Kprime.mod(Nprime);
        BigInteger xValue = KmodN.multiply(coprimeWith2027.modInverse(Nprime)).mod(Nprime);

        System.out.println("2027x = K' mod N': x = " + xValue);
    }
}
