package Lab1;

import java.util.Locale;

public class Lab1_2 {
    // Функция для вычисления НОД по алгоритму Евклида в GF(2)
    public static int gf2_gcd(int a, int b) {
        int temp;
        while (b != 0) {
            temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Функция для вычисления коэффициентов расширенного алгоритма Евклида в GF(2)
    public static void gf2_extended_gcd(int a, int b, int[] result) {
        if (b == 0) {
            result[0] = a;
            result[1] = 1;
            result[2] = 0;
        } else {
            int q = a / b;
            int r = a % b;
            int[] tempResult = new int[3];
            gf2_extended_gcd(b, r, tempResult);
            result[0] = tempResult[0];
            result[1] = tempResult[2];
            result[2] = tempResult[1] ^ (q * tempResult[2]);
        }
    }

    // Функция для возведения в степень в GF(2)
    public static int gf2_power(int base, int exponent) {
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % 256; // Модуль, так как GF(2) работает по модулю 256
            }
            base = (base * base) % 256; // Модуль, так как GF(2) работает по модулю 256
            exponent /= 2;
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 21; // Произвольное значение n

        // Вычисление N(x) и K(x)
        int N = (9 * n) % 256;
        int K = (7 * (41 - n)) % 256;

        // Вычисление НОД(N(x), K(x)) по алгоритму Евклида в GF(2)
        int gcdNK = gf2_gcd(N, K);

        // Вычисление коэффициентов расширенного алгоритма Евклида в GF(2)
        int[] result = new int[3];
        gf2_extended_gcd(N, K, result);
        int coeff_a = result[1];
        int coeff_b = result[2];

        // Вывод результатов
        System.out.printf("N(x) binary = \"9𝑛 𝑚𝑜𝑑 256\" = %08d%n", Integer.parseInt(Integer.toBinaryString(N)));
        System.out.printf("K(x) binary = \"7(41 − n) mod 256\" = %08d%n", Integer.parseInt(Integer.toBinaryString(K)));
        System.out.printf("N(x) = \"9𝑛 𝑚𝑜𝑑 256\" = %d\n", N);
        System.out.printf("K(x) = \"7(41 − n) mod 256\" = %d\n", K);
        System.out.printf("GCD(N(x), K(x)): %d\n", gcdNK);
        System.out.printf("A(x): %d\n", coeff_a);
        System.out.printf("B(x): %d\n", coeff_b);
        int tempResult = coeff_a*N + coeff_b*K;
        System.out.printf("A(x)*N(x) + B(x)*K(x) = GCD(N(x), K(x)) : %d*%d + %d*%d = %d. %d %% 256 = %d \n", coeff_a, N, coeff_b, K, tempResult, tempResult, gcdNK);

        // Вычисление N(x)^8 mod (x^2 + x + 1)
        int resultPower = gf2_power(N, 8);
        int mod = 5; // x^2 + x + 1 in decimal is 5
        int resultPowerMod = resultPower % mod;
        System.out.printf("N(x)^8 mod (x^2 + x + 1) = %d\n", resultPowerMod);

        // Решение сравнения над GF(2): K(x) * A(x) ? N(x) мoд x^8 + x^4 + x^3 + x + 1
        int polyMod = 283; // x^8 + x^4 + x^3 + x + 1 in decimal is 283
        int equation_result = (K * coeff_a) % polyMod;
        System.out.printf(new Locale("ru", "RU"), "Comparison result: %d\n", equation_result);
    }
}
