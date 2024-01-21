package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeGameTest {

    @Test
    void testMazeGeneration() {
        MazeGame game = new MazeGame(10, 10);
        assertNotNull(game);

        char[][] maze = game.getMaze();
        assertNotNull(maze);
        assertEquals(10, maze.length);
        assertEquals(10, maze[0].length);
    }

    @Test
    void testStartAndEndPositions() {
        MazeGame game = new MazeGame(10, 10);
        char[][] maze = game.getMaze();

        int[] startPosition = game.getStart();
        int[] endPosition = game.getEnd();

        // Sprawdzenie, czy start i koniec są na krawędziach
        assertTrue(isOnEdge(startPosition[0], startPosition[1], 10, 10));
        assertTrue(isOnEdge(endPosition[0], endPosition[1], 10, 10));

        // Sprawdzenie, czy start i koniec są na przeciwnych krawędziach
        assertTrue(areOnOppositeEdges(startPosition, endPosition, 10, 10));

    }

    private boolean isOnEdge(int x, int y, int width, int height) {
        return x == 0 || x == width - 1 || y == 0 || y == height - 1;
    }

    private boolean areOnOppositeEdges(int[] start, int[] end, int width, int height) {
        boolean startOnLeftEdge = start[0] == 0;
        boolean startOnRightEdge = start[0] == width - 1;
        boolean startOnTopEdge = start[1] == 0;
        boolean startOnBottomEdge = start[1] == height - 1;

        boolean endOnLeftEdge = end[0] == 0;
        boolean endOnRightEdge = end[0] == width - 1;
        boolean endOnTopEdge = end[1] == 0;
        boolean endOnBottomEdge = end[1] == height - 1;

        return (startOnLeftEdge && endOnRightEdge) || (startOnRightEdge && endOnLeftEdge) ||
                (startOnTopEdge && endOnBottomEdge) || (startOnBottomEdge && endOnTopEdge);
    }

    @Test
    void testPlayerMovement() {
        MazeGame game = new MazeGame(5, 5);
        char[][] testMaze = new char[][]{
                {'#', '#', '#', '#', '#'},
                {'#', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', ' ', '#'},
                {'#', ' ', ' ', ' ', '#'},
                {'#', '#', '#', '#', '#'}
        };

        game.setMaze(testMaze);
        game.setPlayerPosition(1, 1); // Ustawienie gracza w miejscu, gdzie ruch jest możliwy

        assertTrue(game.movePlayer('d')); // Ruch w prawo jest możliwy
        assertEquals(2, game.getPlayerX()); // Gracz powinien być przesunięty w prawo

        assertFalse(game.movePlayer('w')); // Ruch w do góry jest niemożmożliwy
        assertEquals(1, game.getPlayerY()); // Gracz powinien być być na poprzedniej pozycji
        assertEquals(2, game.getPlayerX()); // Gracz powinien być być na poprzedniej pozycji

        assertFalse(game.movePlayer('s')); // Ruch w dół jest niemmożliwy
        assertEquals(1, game.getPlayerY()); // Gracz powinien być być na poprzedniej pozycji
        assertEquals(2, game.getPlayerX()); // Gracz powinien być być na poprzedniej pozycji

        assertTrue(game.movePlayer('a')); // Ruch w lewo jest możliwy
        assertEquals(1, game.getPlayerY()); // Gracz powinien być być na poprzedniej pozycji
        assertEquals(1, game.getPlayerX()); // Gracz powinien być być na poprzedniej pozycji

    }

    @Test
    void testPlayerMovementAtEdges() {
        MazeGame game = new MazeGame(10, 10);

        // Ustawienie gracza przy krawędzi i testowanie ruchu
        game.setPlayerPosition(0, 5); // Lewa krawędź
        assertFalse(game.movePlayer('a')); // Nie można iść dalej w lewo

        game.setPlayerPosition(game.getWidth() - 1, 5); // Prawa krawędź
        assertFalse(game.movePlayer('d')); // Nie można iść dalej w prawo

        // Testowanie ruchu na górnej krawędzi
        game.setPlayerPosition(5, 0); // Gracz ustawiony na górze
        assertFalse(game.movePlayer('w')); // Nie można iść dalej w górę

        // Testowanie ruchu na dolnej krawędzi
        game.setPlayerPosition(5, game.getHeight() - 1); // Gracz ustawiony na dole
        assertFalse(game.movePlayer('s')); // Nie można iść dalej w dół
    }


}
