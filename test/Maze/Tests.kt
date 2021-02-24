package Maze

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDateTime


class Tests {

    @Test
    fun getCamerasList() {

        assertEquals(
            mutableListOf(
                (1 to 1),
                (4 to 5)
            ),
            getCamerasList(
                "input/maze",
                2,
                "output/" + LocalDateTime.now().toString().replace(":", "_").replace(".", "_")
            )
        )
        assertEquals(
            mutableListOf(
                (4 to 0),
                (8 to 0),
                (0 to 2)
            ),
            getCamerasList(
                "input/Maze2",
                3,
                "output/" + LocalDateTime.now().toString().replace(":", "_").replace(".", "_")
            )
        )
        assertEquals(
            mutableListOf(
                (4 to 0),
                (8 to 0),
                (4 to 1),
                (0 to 2),
                (4 to 2),
                (8 to 2)
            ),
            getCamerasList(
                "input/Maze2",
                6,
                "output/" + LocalDateTime.now().toString().replace(":", "_").replace(".", "_")
            )
        )
        assertEquals(
            mutableListOf(
                (1 to 1),
                (1 to 2),
                (4 to 5),
            ),
            getCamerasList(
                "input/maze",
                3,
                "output/" + LocalDateTime.now().toString().replace(":", "_").replace(".", "_")
            )
        )
        assertEquals(
            mutableListOf(
                (1 to 1),
                (1 to 2),
                (4 to 4),
                (4 to 5)
            ),
            getCamerasList(
                "input/maze",
                4,
                "output/" + LocalDateTime.now().toString().replace(":", "_").replace(".", "_")
            )
        )


    }

}