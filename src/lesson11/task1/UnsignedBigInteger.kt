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
    val x = UnsignedBigInteger("10")
    val z = UnsignedBigInteger("2")
    print(x / z)
}

class UnsignedBigInteger : Comparable<UnsignedBigInteger> {
    private val number: String

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
        val answer = MutableList(x.size + 1) { 0 }
        if (y.size > x.size) {
            val l = x
            x = y
            y = l
        }
        var currentY = y.lastIndex
        var currentAnswer = answer.lastIndex
        var currentX = x.lastIndex

        while (currentX >= 0) {
            if (currentY >= 0) {
                answer[currentAnswer - 1] = (x[currentX] + y[currentY] + answer[currentAnswer]) / 10
                answer[currentAnswer] = (x[currentX] + y[currentY] + answer[currentAnswer]) % 10
            } else {
                answer[currentAnswer - 1] = (x[currentX] + answer[currentAnswer]) / 10
                answer[currentAnswer] = (x[currentX] + answer[currentAnswer]) % 10
            }
            currentAnswer--
            currentX--
            currentY--
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
        if (this.compareTo(other) == -1) {
            throw ArithmeticException()
        }
        val x = number.toMutableList().map { it.toString().toInt() }.toMutableList()
        val y = other.number.toMutableList().map { it.toString().toInt() }.toMutableList()
        val res = MutableList(x.size) { 0 }
        var currentX = x.lastIndex
        var currentY = y.lastIndex
        var currentRes = res.lastIndex
        while (currentX >= 0) {
            if (currentY >= 0) {
                res[currentRes] = x[currentX] - y[currentY]
            } else {
                res[currentRes] = x[currentX]
            }
            currentX--
            currentY--
            currentRes--
        }
        for (i in res.lastIndex downTo 0) {
            if (res[i] < 0) {
                res[i] += 10
                res[i - 1]--
            }
        }
        var str = ""
        var start = true
        for (i in res) {
            if (i == 0 && start) {
                continue
            } else {
                start = false
                str += i
            }
        }
        if (str == "") {
            str = "0"
        }
//        var str = ""
//        var start = true
//        for (i in res.lastIndex downTo 0) {
//            if (res[i] < 0) {
//                res[i] += 10
//                res[i - 1]--
//            }
//            str = res[i].toString() + str
//        }
//        for (i in str) {
//            if (i == '0' && start) {
//
//            }
//        }
        return UnsignedBigInteger(str)
    }

    /**
     * Умножение
     *
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        val x = number.toMutableList().map { it.toString().toInt() }.toMutableList()
        val y = other.number.toMutableList().map { it.toString().toInt() }.toMutableList()
        val answer = MutableList(x.size + y.size) { 0 }
        var currentY = y.lastIndex
        var shift = 0
        while (currentY >= 0) {
            var currentX = x.lastIndex
            var currentAnswer = answer.lastIndex - shift
            while (currentX >= 0) {
                answer[currentAnswer] += y[currentY] * x[currentX]
                currentX--
                currentAnswer--
            }
            currentY--
            shift++
        }
        for (i in answer.lastIndex downTo 1) {
            answer[i - 1] += answer[i] / 10
            answer[i] %= 10
        }
        if (answer[0] == 0) {
            answer.removeAt(0)
        }
        var str = ""
        for (i in answer) {
            str += i
        }
        return UnsignedBigInteger(str)
    }

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger {
        TODO()
    }


    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger {
        if (other.number == "0") throw ArithmeticException()
        var count = UnsignedBigInteger(number)
        if (count < other) return count
        while (count >= other) {
            count -= other
        }
        return count
    }

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean = other is UnsignedBigInteger && other.number == number

    override fun hashCode(): Int = number.hashCode()

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        val num = mutableListOf<Int>()
        val oth = mutableListOf<Int>()
        for (i in number) {
            num.add(i.toString().toInt())
        }
        for (i in other.number) {
            oth.add(i.toString().toInt())
        }
        if (num.size > oth.size) return 1
        if (num.size < oth.size) return -1
        for (i in num.indices) {
            if (num[i] > oth[i]) return 1
            if (num[i] < oth[i]) return -1
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