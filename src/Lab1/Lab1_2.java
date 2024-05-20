package Lab1;

import java.util.Locale;

public class Lab1_2 {
    // Функция для вычисления НОД по алгоритму Евклида в GF(2)
    public static int gf2_gcd(int a, int b) {
        int temp;
        // Выполняем цикл до тех пор, пока b не станет равно нулю
        while (b != 0) {
            // Запоминаем текущее значение b
            temp = b;
            // Обновляем b как остаток от деления a на b
            b = a % b;
            // Присваиваем a значение temp
            a = temp;
        }
        // Возвращаем НОД, который теперь равен a
        return a;
    }

    // Функция для вычисления коэффициентов расширенного алгоритма Евклида в GF(2)
    public static void gf2_extended_gcd(int a, int b, int[] result) {
        // Если b равно нулю, устанавливаем начальные коэффициенты
        if (b == 0) {
            result[0] = a; // НОД
            result[1] = 1; // коэффициент для a
            result[2] = 0; // коэффициент для b
        } else {
            // Иначе вычисляем частное и остаток от деления a на b
            int q = a / b;
            int r = a % b;
            int[] tempResult = new int[3];
            // Рекурсивно вызываем функцию с аргументами b и r
            gf2_extended_gcd(b, r, tempResult);
            // Обновляем результат
            result[0] = tempResult[0]; // НОД
            result[1] = tempResult[2]; // коэффициент для a
            result[2] = tempResult[1] ^ (q * tempResult[2]); // коэффициент для b
        }
    }

    // Функция для возведения в степень в GF(2)
    public static int gf2_power(int base, int exponent) {
        int result = 1;
        // Выполняем цикл, пока показатель степени больше нуля
        while (exponent > 0) {
            // Если текущий показатель нечетный, обновляем результат
            if (exponent % 2 == 1) {
                result = (result * base) % 256; // Применяем модуль 256
            }
            // Обновляем основание и делим показатель на 2
            base = (base * base) % 256; // Применяем модуль 256
            exponent /= 2;
        }
        // Возвращаем результат возведения в степень
        return result;
    }

    public static void main(String[] args) {
        int n = 21; // Произвольное значение n

        // Вычисление N(x) и K(x) на основе заданных формул
        int N = (9 * n) % 256;
        int K = (7 * (41 - n)) % 256;

        // Вычисление НОД(N(x), K(x)) по алгоритму Евклида в GF(2)
        int gcdNK = gf2_gcd(N, K);

        // Вычисление коэффициентов расширенного алгоритма Евклида в GF(2)
        int[] result = new int[3];
        gf2_extended_gcd(N, K, result);
        int coeff_a = result[1];
        int coeff_b = result[2];

        // Вывод результатов в бинарном и десятичном виде
        System.out.printf("N(x) binary = \"9𝑛 𝑚𝑜𝑑 256\" = %08d%n", Integer.parseInt(Integer.toBinaryString(N)));
        System.out.printf("K(x) binary = \"7(41 − n) mod 256\" = %08d%n", Integer.parseInt(Integer.toBinaryString(K)));
        System.out.printf("N(x) = \"9𝑛 𝑚𝑜𝑑 256\" = %d\n", N);
        System.out.printf("K(x) = \"7(41 − n) mod 256\" = %d\n", K);
        System.out.printf("GCD(N(x), K(x)): %d\n", gcdNK);
        System.out.printf("A(x): %d\n", coeff_a);
        System.out.printf("B(x): %d\n", coeff_b);

        // Проверка равенства A(x)*N(x) + B(x)*K(x) = GCD(N(x), K(x))
        int tempResult = coeff_a * N + coeff_b * K;
        System.out.printf("A(x)*N(x) + B(x)*K(x) = GCD(N(x), K(x)) : %d*%d + %d*%d = %d. %d %% 256 = %d \n", coeff_a, N, coeff_b, K, tempResult, tempResult, gcdNK);

        // Вычисление N(x)^8 mod (x^2 + x + 1)
        int resultPower = gf2_power(N, 8);
        int mod = 5; // x^2 + x + 1 в десятичном представлении это 5
        int resultPowerMod = resultPower % mod;
        System.out.printf("N(x)^8 mod (x^2 + x + 1) = %d\n", resultPowerMod);

        // Решение сравнения K(x) * A(x) ? N(x) мoд x^8 + x^4 + x^3 + x + 1
        int polyMod = 283; // x^8 + x^4 + x^3 + x + 1 в десятичном представлении это 283
        int equation_result = (K * coeff_a) % polyMod;
        System.out.printf(new Locale("ru", "RU"), "Comparison result: %d\n", equation_result);
    }
}
