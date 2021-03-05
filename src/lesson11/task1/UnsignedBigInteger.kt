package lesson11.task1

import java.lang.ArithmeticException
import java.lang.NumberFormatException

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная, общая ценность в баллах -- 32.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
fun main() {
    var x = UnsignedBigInteger("0")
    var z = UnsignedBigInteger("42")
    val y = 42
    var l = 0
    for (i in 1..42) {
        x = x.plus(z)
        l += y
        println("$x $l")
    }
    print(x * z)

}

fun plus3(a: String, b: String): String {
    var x = mutableListOf<Int>()
    for (i in a) {
        x.add(i.toString().toInt())
    }
    var y = mutableListOf<Int>()
    for (i in b) {
        y.add(i.toString().toInt())
    }
    val answer = mutableListOf<Int>()
    if (y.size > x.size) {
        val l = x
        x = y
        y = l
    }
    for (i in 1..x.size + 1) {
        answer.add(0)
    }
    var currentY = y.lastIndex
    var currentAnswer = answer.lastIndex
    var currentX = x.lastIndex
    while (currentY >= 0) {
        answer[currentAnswer] = (answer[currentAnswer] + x[currentX] + y[currentY]) % 10
        answer[currentAnswer - 1] += (x[currentX] + y[currentY]) / 10
        currentY--
        currentX--
        currentAnswer--
    }
    while (currentX >= 0) {
        answer[currentAnswer - 1] = answer[currentAnswer - 1] + (answer[currentAnswer] + x[currentX]) / 10
        answer[currentAnswer] = (answer[currentAnswer] + x[currentX]) % 10
        currentX--
        currentAnswer--
    }
    var str = ""
    if (answer[0] == 0) {
        answer.removeAt(0)
    }
    for (i in answer) {
        str += i
    }
    return str
}

class UnsignedBigInteger : Comparable<UnsignedBigInteger> {
    lateinit var number: String

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        number = s
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        number = i.toString()
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        var x = mutableListOf<Int>()
        for (i in number) {
            x.add(i.toString().toInt())
        }
        var y = mutableListOf<Int>()
        for (i in other.number) {
            y.add(i.toString().toInt())
        }
        val answer = mutableListOf<Int>()
        if (y.size > x.size) {
            val l = x
            x = y
            y = l
        }
        for (i in 1..x.size + 1) {
            answer.add(0)
        }
        var currentY = y.lastIndex
        var currentAnswer = answer.lastIndex
        var currentX = x.lastIndex
        while (currentY >= 0) {
            answer[currentAnswer] = (answer[currentAnswer] + x[currentX] + y[currentY]) % 10
            answer[currentAnswer - 1] += (x[currentX] + y[currentY]) / 10
            currentY--
            currentX--
            currentAnswer--
        }
        while (currentX >= 0) {
            answer[currentAnswer - 1] = answer[currentAnswer - 1] + (answer[currentAnswer] + x[currentX]) / 10
            answer[currentAnswer] = (answer[currentAnswer] + x[currentX]) % 10
            currentX--
            currentAnswer--
        }
        var str = ""
        if (answer[0] == 0) {
            answer.removeAt(0)
        }
        for (i in answer) {
            str += i
        }
        return UnsignedBigInteger(str)
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        TODO()
    }

    /**
     * Умножение
     *
     */
    private fun generation(x: Int): UnsignedBigInteger {
        var z = UnsignedBigInteger(0)
        for (i in 1..x) {
            z += this
        }
        return z
    }

    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        var x = UnsignedBigInteger(0)
        var rank = 1
        for (i in other.number.reversed()) {
            x += this.generation(i.toString().toInt()) + this.generation(rank)
            rank *= 10
        }
        return x
    }

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean {
        if (other !is UnsignedBigInteger || number != other.number) {
            return false
        }
        for (i in 0..number.lastIndex) {
            if (number[i] != other.number[i]) {
                return false
            }
        }
        return true
    }

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        val num = mutableListOf<String>()
        val oth = mutableListOf<String>()
        for (i in number) {
            num.add(i.toString())
        }
        for (i in other.number) {
            oth.add(i.toString())
        }
        if (num.size > oth.size) return 1
        if (num.size < oth.size) return -1
        for (i in num.indices) {
            if (num[i].toInt() > oth[i].toInt()) return 1
            if (num[i].toInt() < oth[i].toInt()) return -1
        }
        return 0
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = number

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        try {
            return number.toInt()
        } catch (e: NumberFormatException) {
            throw ArithmeticException()
        }
    }

}