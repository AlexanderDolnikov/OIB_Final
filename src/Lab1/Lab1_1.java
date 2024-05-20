package Lab1;

import java.math.BigInteger;

public class Lab1_1 {

    // Функция для нахождения НОД (наибольшего общего делителя) и коэффициентов x и y
    // с помощью расширенного алгоритма Евклида.
    public static BigInteger[] extendedGCD(BigInteger n, BigInteger k) {
        // Если k равно нулю, возвращаем n как НОД и коэффициенты 1 и 0.
        if (k.equals(BigInteger.ZERO)) {
            return new BigInteger[]{n, BigInteger.ONE, BigInteger.ZERO};
        }
        // Рекурсивный вызов функции с параметрами (k, n % k).
        BigInteger[] values = extendedGCD(k, n.mod(k));
        BigInteger gcd = values[0];
        BigInteger x = values[2];
        BigInteger y = values[1].subtract(n.divide(k).multiply(values[2]));
        // Возвращаем НОД и коэффициенты x и y.
        return new BigInteger[]{gcd, x, y};
    }

    // Функция для проверки, что два числа взаимно просты (их НОД равен 1).
    public static boolean areCoprime(BigInteger a, BigInteger b) {
        return a.gcd(b).equals(BigInteger.ONE);
    }

    public static void main(String[] args) {
        int n = 21; // замените на нужный номер по журналу

        // Вычисление N и K на основе формул с использованием n.
        BigInteger N = BigInteger.valueOf((long) Math.floor(Math.sqrt(30202020 + 20190 * n)) + 24 * n);
        BigInteger K = BigInteger.valueOf((long) Math.floor(Math.sqrt(20192019 - 20200 * n)) - 38 * n);

        // Задача 1: Нахождение НОД (N, K) и коэффициентов x и y с помощью расширенного алгоритма Евклида.
        BigInteger[] gcdResult = extendedGCD(N, K);
        BigInteger gcd = gcdResult[0];
        BigInteger x = gcdResult[1];
        BigInteger y = gcdResult[2];

        // Вывод результатов первой задачи.
        System.out.println("Task1:");
        System.out.println("GCD(N, K) = " + gcd);
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println();

        // Задача 2: Вычисление 2^N mod (2n + 143).
        System.out.println("Task2:");
        BigInteger base = BigInteger.valueOf(2);
        BigInteger exponent = N;
        BigInteger mod = BigInteger.valueOf(2L * n + 143);
        BigInteger result = base.modPow(exponent, mod);

        // Вывод результата второй задачи.
        System.out.println("2^N mod (2n + 143) = " + result);

        // Задача 3: Поиск наименьших чисел, взаимно простых с 2027.
        System.out.println("\nTask3:");
        BigInteger coprimeWith2027 = BigInteger.valueOf(2027);

        // Поиск наименьшего числа N', меньшего чем N, которое взаимно просто с 2027.
        BigInteger Nprime = N.subtract(BigInteger.ONE);
        while (!areCoprime(Nprime, coprimeWith2027)) {
            Nprime = Nprime.subtract(BigInteger.ONE);
        }

        // Поиск наименьшего числа K', меньшего чем K, которое взаимно просто с 2027.
        BigInteger Kprime = K.subtract(BigInteger.ONE);
        while (!areCoprime(Kprime, coprimeWith2027)) {
            Kprime = Kprime.subtract(BigInteger.ONE);
        }

        // Вывод найденных значений N' и K'.
        System.out.println("N' = " + Nprime);
        System.out.println("K' = " + Kprime);

        // Решение уравнения 2027x ≡ K' mod N'.
        BigInteger KmodN = Kprime.mod(Nprime);
        BigInteger xValue = KmodN.multiply(coprimeWith2027.modInverse(Nprime)).mod(Nprime);

        // Вывод значения x, удовлетворяющего уравнению 2027x ≡ K' mod N'.
        System.out.println("2027x = K' mod N': x = " + xValue);
    }
}
