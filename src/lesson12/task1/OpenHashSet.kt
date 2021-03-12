@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "хеш-таблица с открытой адресацией"
 *
 * Общая сложность задания -- сложная, общая ценность в баллах -- 20.
 * Объект класса хранит данные типа T в виде хеш-таблицы.
 * Хеш-таблица не может содержать равные по equals элементы.
 * Подробности по организации см. статью википедии "Хеш-таблица", раздел "Открытая адресация".
 * Методы: добавление элемента, проверка вхождения элемента, сравнение двух таблиц на равенство.
 * В этом задании не разрешается использовать библиотечные классы HashSet, HashMap и им подобные,
 * а также любые функции, создающие множества (mutableSetOf и пр.).
 *
 * В конструктор хеш-таблицы передаётся её вместимость (максимальное количество элементов)
 */
class OpenHashSet<T>(val capacity: Int) {

    /**
     * Массив для хранения элементов хеш-таблицы
     */
    internal val elements = Array<Any?>(capacity) { null }

    /**
     * Число элементов в хеш-таблице
     */
    val size: Int
        get() {
            var count = 0
            for (i in elements.indices) {
                if (elements[i] != null) {
                    count++
                }
            }
            return count
        }

    /**
     * Признак пустоты
     */
    fun isEmpty(): Boolean {
        for (i in elements) {
            if (i != null) {
                return false
            }
        }
        return true
    }

    /**
     * Добавление элемента.
     * Вернуть true, если элемент был успешно добавлен,
     * или false, если такой элемент уже был в таблице, или превышена вместимость таблицы.
     */
    fun add(element: T): Boolean {
        for (i in elements.indices) {
            if (elements[i] == element) {
                return false
            }
            if (elements[i] == null) {
                elements[i] = element
                return true
            }
        }
        return false
    }


    /**
     * Проверка, входит ли заданный элемент в хеш-таблицу
     */
    operator fun contains(element: T): Boolean {
        for (i in elements.indices) {
            if (elements[i] == element) {
                return true
            }
        }
        return false
    }

    /**
     * Таблицы равны, если в них одинаковое количество элементов,
     * и любой элемент из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (other !is OpenHashSet<*>) {
            return false
        }
        for (i in other.elements) {
            if (i !in elements && i != null) {
                return false
            }
        }
        for (i in elements) {
            if (i !in other.elements && i != null) {
                return false
            }
        }
        return true
    }

    override fun hashCode(): Int {
        var result = capacity
        result = 31 * result + elements.contentHashCode()
        return result
    }

}