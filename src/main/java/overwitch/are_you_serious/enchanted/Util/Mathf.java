package overwitch.are_you_serious.enchanted.Util;
public final class Mathf {

    public static final double MATHF_PI = 3.14159265358979323846;
    public static final double MATHF_PI_2 = MATHF_PI/2.0;

    public static final double NaN=0.0/0.0d;


// 数值函数实现：双精度（double）

    public static double Mathf_Clamp(double x, double min, double max)
    {
        return (x < min) ? min : (x > max) ? max : x;
    }

    public static double Mathf_Abs(double value)
    {
        return (value < 0) ? -value : value;
    }

    public static double Mathf_Log(double value)
    {
        if (value <= 0.0) {
            // 返回NaN而不是-1.0
            return NaN;
        }

        double guess = value - 1.0;  // 初始猜测，log(x) ≈ (x-1) - (x-1)²/2 + ...
        double epsilon = 1e-12;      // 精度容忍度

        // 牛顿迭代法
        for (int i = 0; i < 50; i++) {
            double exp_guess = Mathf_Exp(guess);  // 使用自然指数
            double delta = (exp_guess - value) / exp_guess;  // 牛顿法公式
            guess -= delta;

            if (Mathf_Abs(delta) < epsilon) {
                break;
            }
        }
        return guess;
    }

    public static double Mathf_Exp(double x)
    {
        double result = 1.0;
        double term = 1.0;
        int n = 1;

        while (term > 1e-10 || term < -1e-10) {
            term *= x / n;  // 每次增加一个新的项
            result += term;
            n++;
        }

        return result;
    }

    public static double Mathf_Sin(double angle)
    {
        double result = 0.0;
        double term = angle;  // 初始项是角度值
        double power = angle; // 随着迭代，角度的幂次逐渐增大
        double factorial = 1.0;
        int n = 1;

        while (Mathf_Abs(term) > 1e-12) {
            result += term;
            n += 2;  // n 递增 2 (正弦函数的泰勒展开项)
            factorial *= n * (n - 1);  // 计算阶乘部分
            power *= angle * angle;  // 计算 x^n
            term = ((n % 4 == 1) ? 1 : -1) * power / factorial;  // 根据正负交替的泰勒展开式
        }
        return result;
    }

    public static double Mathf_Cos(double angle)
    {
        double result = 1.0;  // cos(0) = 1
        double term = 1.0;
        double power = angle * angle; // 初始项为 x^2
        double factorial = 1.0;
        int n = 2;

        while (Mathf_Abs(term) > 1e-12) {
            term = (n % 4 == 0 ? 1 : -1) * power / factorial;
            result += term;
            n += 2;
            factorial *= n * (n - 1);  // 更新阶乘
            power *= angle * angle;  // 更新幂次
        }
        return result;
    }

    public static double Mathf_Tan(double angle)
    {
        return Mathf_Sin(angle) / Mathf_Cos(angle); // tan(x) = sin(x) / cos(x)
    }

    public static double Mathf_Asin(double value)
    {
        // 使用泰勒展开近似计算反正弦
        double result = 0.0;
        double term = value;
        double power = value;
        double factorial = 1.0;
        int n = 1;

        while (Mathf_Abs(term) > 1e-12) {
            result += term;
            n += 2;
            factorial *= n * (n - 1);
            power *= value * value;
            term = (n % 4 == 1 ? 1 : -1) * power / factorial;
        }
        return result;
    }

    public static double Mathf_Acos(double value)
    {
        return MATHF_PI_2 - Mathf_Asin(value); // acos(x) = pi/2 - asin(x)
    }

    public static double Mathf_Atan(double value)
    {
        // 通过泰勒展开近似计算反正切
        double result = 0.0;
        double term = value;
        double power = value;
        double factorial = 1.0;
        int n = 1;

        while (Mathf_Abs(term) > 1e-12) {
            result += term;
            n += 2;
            factorial *= n * (n - 1);
            power *= value * value;
            term = (n % 4 == 1 ? 1 : -1) * power / factorial;
        }
        return result;
    }

    public static double Mathf_Atan2(double y, double x)
    {
        if (x > 0) return Mathf_Atan(y / x);
        if (x < 0) return Mathf_Atan(y / x) + MATHF_PI;
        if (y > 0) return MATHF_PI_2;
        if (y < 0) return -MATHF_PI_2;
        return 0.0; // x == 0, y == 0
    }

    public static double Mathf_Sqrt(double value)
    {
        if (value < 0) return NaN; // 负数平方根返回NaN（不允许负数输入）
        double guess = value * 0.5; // 初始猜测
        double epsilon = 1e-12;  // 精度容忍度

        while (true) {
            double next_guess = 0.5 * (guess + value / guess);  // 牛顿法更新
            if (Mathf_Abs(next_guess - guess) < epsilon) break;  // 如果变化小于误差容忍度，停止
            guess = next_guess;
        }
        return guess;
    }

    public static double Mathf_Sqrtf(double value, double value1)
    {
        return Mathf_Sqrt(value * value + value1 * value1);
    }

    public static double Mathf_Min(double a, double b)
    {
        return (a < b) ? a : b;
    }

    public static double Mathf_Max(double a, double b)
    {
        return (a > b) ? a : b;
    }

    public static double Mathf_Lerp(double a, double b, double t)
    {
        return a + (b - a) * Mathf_Clamp(t, 0.0, 1.0);  // 保证 t 在 [0,1] 之间
    }

    public static double Mathf_Clamp01(double value)
    {
        return (value < 0.0) ? 0.0 : (value > 1.0) ? 1.0 : value;
    }

    public static double Mathf_LerpUnclamped(double a, double b, double t)
    {
        return a + (b - a) * t;
    }

// 数值函数实现：单精度（float）

    public static float Abs(float value)
    {
        return (value < 0) ? -value : value;
    }

    public static float Sqrt(float value)
    {
        if (value < 0) return (float) NaN;  // 负数平方根返回错误值
        float guess = value * 0.5f;  // 初始猜测
        float epsilon = 1e-6f;  // 精度容忍度

        while (true) {
            float next_guess = 0.5f * (guess + value / guess);  // 牛顿法更新
            if (Abs(next_guess - guess) < epsilon) break;  // 如果变化小于误差容忍度，停止
            guess = next_guess;
        }
        return guess;
    }

    public static float Sqrtf(float value, float value1)
    {
        return Sqrt(value * value + value1 * value1);
    }

    public static float Sin(float angle)
    {
        float result = 0.0f;
        float term = angle;
        float power = angle;
        float factorial = 1.0f;
        int n = 1;

        while (Abs(term) > 1e-6f) {
            result += term;
            n += 2;
            factorial *= n * (n - 1);
            power *= angle * angle;
            term = (n % 4 == 1 ? 1.0f : -1.0f) * power / factorial;
        }
        return result;
    }

/*
public float Sinf(float value, float value1)
{
    //return Sin(value * value1);
    //return Sin(value) * Cos(value1) + Cos(value) * Sin(value1);
}*/

    public static float Cos(float angle)
    {
        float result = 1.0f;  // cos(0) = 1
        float term = 1.0f;
        float power = angle * angle; // 初始项为 x^2
        float factorial = 1.0f;
        int n = 2;

        while (Abs(term) > 1e-6f) {
            term = (n % 4 == 0 ? 1.0f : -1.0f) * power / factorial;
            result += term;
            n += 2;
            factorial *= n * (n - 1);  // 更新阶乘
            power *= angle * angle;  // 更新幂次
        }
        return result;
    }

    public static float Sign(float x)
    {
        return (x > 0) ? 1.0f : (x < 0) ? -1.0f : 0.0f;
    }

    public static float Lerp(float a, float b, float t)
    {
        return a + (b - a) * Clamps(t);
    }

    public static float Clamps(float value)
    {
        return (value < 0.0f) ? 0.0f : (value > 1.0f) ? 1.0f : value;
    }

    public static float LerpUnclamped(float a, float b, float t)
    {
        return a + (b - a) * t;
    }

    public static float Min(float a, float b)
    {
        return (a < b) ? a : b;
    }

    public static float Max(float a, float b)
    {
        return (a > b) ? a : b;
    }

    // 浮动版夹取函数
    public static float Clamp(float x, float min, float max)
    {
        return (x < min) ? min : (x > max) ? max : x;
    }
    public static float Exp(float x)
    {
        float result = 1.0f;
        float term = 1.0f;
        int n = 1;

        while (term > 1e-6f || term < -1e-6f) {
            term *= x / n;
            result += term;
            n++;
        }

        return result;
    }
    public static long Mathf_Integer_Max(long a,long b)
    {
        return (a<b)?a:b;
    }

}
