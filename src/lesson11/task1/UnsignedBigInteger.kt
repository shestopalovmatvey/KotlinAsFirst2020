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
    val x = UnsignedBigInteger("1232312312312312312312313132213")
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
        for (i in x.indices.reversed()) {
            if (currentY >= 0) {
                answer[currentAnswer] = (x[i] + y[currentY] + answer[currentAnswer]) % 10
                answer[currentAnswer - 1] = (x[i] + y[currentY] + answer[currentAnswer - 1]) / 10
            } else {
                answer[currentAnswer] = (x[i] + answer[currentAnswer]) % 10
            }
            currentY--
            currentAnswer--
        }

        var l = ""
        for (i in answer.indices) {
            if (answer[0] == 0 && i == 0) {
                continue
            } else {
                l += answer[i]
            }
        }
        return UnsignedBigInteger(l)
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        TODO()
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

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
        if (other is UnsignedBigInteger && number == other.number) {
            return true
        }
        return false
    }

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int = TODO()

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