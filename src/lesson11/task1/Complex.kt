@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
fun getRe(s: String): Double {
    val reg = Regex("""-?\d+(\.\d+)?""")
    var matchRes = reg.find(s)
    return matchRes!!.value.toDouble()
}

fun getIm(s: String): Double {
    val reg = Regex("""(\+|-)?\d+(\.\d+)?""")
    var matchRes = reg.findAll(s)
    val l = mutableListOf<Double>()
    for (i in matchRes) {
        l.add(i.value.toDouble())
    }
    return l[1]
}

class Complex(val re: Double, val im: Double) {
    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */
    constructor(s: String) : this(getRe(s), getIm(s))

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(re + other.re, im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex = Complex(re * other.re - im * other.im, re * other.im + im * other.re)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex = Complex(
        (re * other.re + im * other.im) / (other.re * other.re + other.im * other.im),
        (other.re * im - re * other.im) / (other.re * other.re + other.im * other.im)
    )

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (other is Complex && re == other.re && other.im == im) {
            return true
        }
        return false
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = "$re + (${im}i)"


}
