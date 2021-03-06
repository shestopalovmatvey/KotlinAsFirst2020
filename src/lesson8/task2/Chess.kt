@file:Suppress("UNUSED_PARAMETER")

package lesson8.task2

import lesson4.task1.abs
import java.lang.IllegalArgumentException
import kotlin.math.abs

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
fun main() {
    val x = Square(4, 1)
    val y = Square(2, 1)
    print(bishopTrajectory(x, y))
}

data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая (2 балла)
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        val map =
            mutableMapOf<Int, String>(1 to "a", 2 to "b", 3 to "c", 4 to "d", 5 to "e", 6 to "f", 7 to "g", 8 to "h")
        return if (inside()) {
            "${map[column]}$row"
        } else {
            ""
        }
    }
}

/**
 * Простая (2 балла)
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    val map =
        mutableMapOf<String, Int>("a" to 1, "b" to 2, "c" to 3, "d" to 4, "e" to 5, "f" to 6, "g" to 7, "h" to 8)
    val reg = Regex("""[a-h][1-8]""")
    if (reg.matches(notation)) {
        return Square(map[notation[0].toString()]!!.toInt(), notation[1].toString().toInt())
    } else {
        throw IllegalArgumentException()
    }
}

/**
 * Простая (2 балла)
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) {
        throw IllegalArgumentException()
    }
    if (start == end) return 0
    return if (start.column == end.column || start.row == end.row) 1
    else 2
}

/**
 * Средняя (3 балла)
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    val res = mutableListOf<Square>()
    if (!start.inside() || !end.inside()) {
        throw IllegalArgumentException()
    }
    if (start == end) {
        res.add(start)
        return res
    }
    if (start.column == end.column || start.row == end.row) {
        res.add(start)
        res.add(end)
        return res
    }
    res.add(start)
    res.add(Square(start.column, end.row))
    res.add(end)
    return res
}

/**
 * Простая (2 балла)
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) {
        throw IllegalArgumentException()
    }
    if (start == end) return 0
    if ((start.row + start.column) % 2 != (end.row + end.column) % 2) return -1
    return if (abs(start.column - end.column) == abs(start.row - end.row)) 1
    else 2
}

/**
 * Сложная (5 баллов)
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val res = mutableListOf<Square>()
    if (start == end) {
        res.add(start)
        return res
    }
    if (abs(start.column - end.column) == abs(start.row - end.row)) {
        res.add(start)
        res.add(end)
        return res
    }
    if ((start.row + start.column) % 2 != (end.row + end.column) % 2) return res
    else {
        if (start.column < end.column) {
            val list = mutableListOf<Pair<Int, Int>>()
            var x = end.column
            var y = end.row
            while (x > 0 && y < 9) {
                list.add(x to y)
                x--
                y++
            }
            x = end.column
            y = end.row
            while (x > 0 && y > 0) {
                list.add(x to y)
                x--
                y--
            }
            var x1 = start.column
            var y1 = start.row
            while (x1 < 9 && y1 < 9) {
                if ((x1 to y1) in list) {
                    res.add(start)
                    res.add(Square(x1, y1))
                    res.add((end))
                    return res
                }
                x1++
                y1++
            }
            x1 = start.column
            y1 = start.row
            while (x1 > 0 && y1 > 0) {
                if ((x1 to y1) in list) {
                    res.add(start)
                    res.add(Square(x1, y1))
                    res.add((end))
                    return res
                }
                x1++
                y1--
            }
        }
        if (start.column > end.column) {
            val list1 = mutableListOf<Pair<Int, Int>>()
            var z = start.column
            var l = start.row
            while (z > 0 && l < 9) {
                list1.add(z to l)
                z--
                l++
            }
            z = start.column
            l = start.row
            while (z > 0 && l > 0) {
                list1.add(z to l)
                z--
                l--
            }
            var z1 = end.column
            var l1 = end.row
            while (z1 > 0 && l1 < 9) {
                if ((z1 to l1) in list1) {
                    res.add(start)
                    res.add(Square(z1, l1))
                    res.add(end)
                    return res
                }
                z1--
                l1++
            }
            z1 = end.column
            l1 = end.row
            while (z1 > 0 && l1 > 0) {
                if ((z1 to l1) in list1) {
                    res.add(start)
                    res.add(Square(z1, l1))
                    res.add(end)
                    return res
                }
                z1--
                l1--
            }
        } else {
            if ((start.row + start.column) % 2 != (end.row + end.column) % 2) return res
            else {
                if (start.row > end.row) {
                    val list = mutableListOf<Pair<Int, Int>>()
                    var x = start.column
                    var y = start.row
                    while (x < 9 && y > 0) {
                        list.add(x to y)
                        x++
                        y--
                    }
                    x = start.column
                    y = start.row
                    while (x > 0 && y > 0) {
                        list.add(x to y)
                        x--
                        y--
                    }
                    var x1 = end.column
                    var y1 = end.row
                    while (x1 < 9 && y1 < 9) {
                        if ((x1 to y1) in list) {
                            res.add(start)
                            res.add(Square(x1, y1))
                            res.add(end)
                            return res
                        }
                        x1++
                        y1++
                    }
                    x1 = end.column
                    y1 = end.row
                    while (x1 > 0 && y1 < 9) {
                        if ((x1 to y1) in list) {
                            res.add(start)
                            res.add(Square(x1, y1))
                            res.add(end)
                            return res
                        }
                        x1--
                        y1++
                    }
                } else {
                    val list = mutableListOf<Pair<Int, Int>>()
                    var z = end.column
                    var l = end.row
                    while (z < 9 && l > 0) {
                        list.add(z to l)
                        z++
                        l--
                    }
                    z = end.column
                    l = end.row
                    while (z > 0 && l > 0) {
                        list.add(z to l)
                        z--
                        l--
                    }
                    var z1 = start.column
                    var l1 = start.row
                    while (z1 < 9 && l1 < 9) {
                        if ((z1 to l1) in list) {
                            res.add(start)
                            res.add(Square(z1, l1))
                            res.add(end)
                            return res
                        }
                        z1++
                        l1++
                    }
                    z1 = start.column
                    l1 = start.row
                    while (z1 > 0 && l1 < 9) {
                        if ((z1 to l1) in list) {
                            res.add(start)
                            res.add(Square(z1, l1))
                            res.add(end)
                            return res
                        }
                        z1--
                        l1++
                    }
                }
            }
        }
    }
    return res
}

/**
 * Средняя (3 балла)
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int = TODO()

/**
 * Сложная (5 баллов)
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> = TODO()

/**
 * Сложная (6 баллов)
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int = TODO()

/**
 * Очень сложная (10 баллов)
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = TODO()
