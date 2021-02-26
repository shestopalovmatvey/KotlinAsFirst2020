@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {

    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
    if (height <= 0 || width <= 0)
        throw IllegalArgumentException()
    else
        MatrixImpl(height, width, e)

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, val e: E) : Matrix<E> {
    private var matrix: MutableList<MutableList<E>> = createMatrix()

    private fun createMatrix(): MutableList<MutableList<E>> {
        val matrix = mutableListOf<MutableList<E>>()
        for (i in 0 until height) {
            val x = mutableListOf<E>()
            for (j in 0 until width) {
                x.add(e)
            }
            matrix.add(x)
        }
        return matrix
    }


    override fun get(row: Int, column: Int): E =
        if (row in 0 until height && column in 0 until width)
            matrix[row][column]
        else
            throw IndexOutOfBoundsException()

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        if (row in 0 until height && column in 0 until width)
            matrix[row][column] = value
        else
            throw IndexOutOfBoundsException()

    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?): Boolean {
        if (other !is Matrix<*> || height != other.height && width != other.width) return false
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (this[i, j] != other[i, j]) {
                    return false
                }
            }
        }
        return true
    }

    override fun toString(): String {
        var res = ""
        for (i in matrix) {
            for (j in i) {
                res += "$j "
            }
            res += "\n"
        }
        return res
    }
}


