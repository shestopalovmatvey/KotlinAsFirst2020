@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import lesson4.task1.abs
import java.lang.IllegalStateException
import kotlin.math.abs

/**
 * Класс "табличная функция".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса хранит таблицу значений функции (y) от одного аргумента (x).
 * В таблицу можно добавлять и удалять пары (x, y),
 * найти в ней ближайшую пару (x, y) по заданному x,
 * найти (интерполяцией или экстраполяцией) значение y по заданному x.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class TableFunction {
    private val map = mutableMapOf<Double, Double>()

    /**
     * Количество пар в таблице
     */
    val size: Int get() = map.size

    /**
     * Добавить новую пару.
     * Вернуть true, если пары с заданным x ещё нет,
     * или false, если она уже есть (в этом случае перезаписать значение y)
     */
    fun add(x: Double, y: Double): Boolean {
        return if (map.contains(x)) {
            map[x] = y
            false
        } else {
            map[x] = y
            true
        }
    }

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean {
        for ((key, value) in map) {
            if (key == x) {
                map.remove(key, value)
                return true
            }
        }
        return false
    }

    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> {
        val collection = mutableListOf<Pair<Double, Double>>()
        for ((key, value) in map) {
            collection.add(key to value)
        }
        return collection
    }

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? {
        if (map.isEmpty()) throw IllegalStateException()
        var min: Double = Double.MAX_VALUE
        var answer = Pair(1.0, 1.0)
        for ((key) in map) {
            if (abs(x - key) < min) {
                min = x - key
            }
        }
        for ((key, value) in map) {
            if (x - key == min) {
                answer = Pair(key, value)
            }
        }

        return answer
    }

    /**
     * Вернуть значение y по заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException.
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x > x2 > x1, использовать экстраполяцию.
     */
    fun getValue(x: Double): Double {
        if (map.isEmpty()) throw IllegalStateException()
        if (map.size == 1) return map[0]!!
        TODO()
    }

    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (other !is TableFunction || other.size != map.size) {
            return false
        }
        for ((key) in map) {
            if (key !in other.map) {
                return false
            }
        }
        return true
    }
}