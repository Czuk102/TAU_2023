package org.example;

import java.util.*;

public class MazeGame {
    private char[][] maze;
    private int width, height;
    private int playerX, playerY;
    private int endX, endY;

    public MazeGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new char[height][width];
        generateMaze();
        placePlayerAndExit();
    }

    private void generateMaze() {
        for (int y = 0; y < height; y++) {
            Arrays.fill(maze[y], '#');
        }

        Random rand = new Random();
        int startX = rand.nextInt(width);
        int startY = rand.nextInt(height);
        maze[startY][startX] = ' ';

        List<int[]> fronts = new ArrayList<>();
        fronts.add(new int[]{startX, startY});

        while (!fronts.isEmpty()) {
            int[] front = fronts.remove(rand.nextInt(fronts.size()));
            List<int[]> neighbours = getNeighbours(front[0], front[1]);

            if (!neighbours.isEmpty()) {
                int[] next = neighbours.get(rand.nextInt(neighbours.size()));
                maze[next[1]][next[0]] = ' ';
                fronts.add(next);

                // Połączenie nowego segmentu z labiryntem
                int dx = (next[0] - front[0]) / 2;
                int dy = (next[1] - front[1]) / 2;
                maze[front[1] + dy][front[0] + dx] = ' ';
            }
        }
    }

    private List<int[]> getNeighbours(int x, int y) {
        List<int[]> neighbours = new ArrayList<>();

        for (int dx = -2; dx <= 2; dx += 2) {
            for (int dy = -2; dy <= 2; dy += 2) {
                if (dx == 0 || dy == 0) {
                    int nx = x + dx;
                    int ny = y + dy;

                    if (nx >= 0 && nx < width && ny >= 0 && ny < height && maze[ny][nx] == '#') {
                        neighbours.add(new int[]{nx, ny});
                    }
                }
            }
        }

        return neighbours;
    }

    private void placePlayerAndExit() {
        // Wybieramy losową krawędź dla startu (S)
        Random rand = new Random();
        int[] start = chooseAccessibleEdgePosition(rand);
        playerX = start[0];
        playerY = start[1];
        maze[playerY][playerX] = 'S';

        // Umieszczamy koniec (E) na przeciwległej krawędzi
        int[] end = chooseOppositeEdgePosition(start, rand);
        endX = end[0];
        endY = end[1];
        maze[endY][endX] = 'E';
    }

    private int[] chooseAccessibleEdgePosition(Random rand) {
        int[] position;
        do {
            position = chooseEdgePosition(rand);
        } while (!isAccessible(position[0], position[1]));
        return position;
    }

    private boolean isAccessible(int x, int y) {
        int accessibleSides = 0;
        if (x > 0 && maze[y][x - 1] == ' ') accessibleSides++;
        if (x < width - 1 && maze[y][x + 1] == ' ') accessibleSides++;
        if (y > 0 && maze[y - 1][x] == ' ') accessibleSides++;
        if (y < height - 1 && maze[y + 1][x] == ' ') accessibleSides++;
        return accessibleSides > 0;
    }
    private int[] chooseEdgePosition(Random rand) {
        int side = rand.nextInt(4);
        switch (side) {
            case 0: // Górna krawędź
                return new int[]{rand.nextInt(width), 0};
            case 1: // Prawa krawędź
                return new int[]{width - 1, rand.nextInt(height)};
            case 2: // Dolna krawędź
                return new int[]{rand.nextInt(width), height - 1};
            default: // Lewa krawędź
                return new int[]{0, rand.nextInt(height)};
        }
    }

    private int[] chooseOppositeEdgePosition(int[] start, Random rand) {
        if (start[0] == 0) { // Start na lewej krawędzi
            return new int[]{width - 1, rand.nextInt(height)};
        } else if (start[0] == width - 1) { // Start na prawej krawędzi
            return new int[]{0, rand.nextInt(height)};
        } else if (start[1] == 0) { // Start na górnej krawędzi
            return new int[]{rand.nextInt(width), height - 1};
        } else { // Start na dolnej krawędzi
            return new int[]{rand.nextInt(width), 0};
        }
    }
    private void displayMaze() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == playerX && y == playerY) {
                    System.out.print('P');
                } else {
                    System.out.print(maze[y][x]);
                }
            }
            System.out.println();
        }
    }

    boolean movePlayer(char direction) {
        int newX = playerX;
        int newY = playerY;

        switch (direction) {
            case 'w': newY--; break;
            case 's': newY++; break;
            case 'a': newX--; break;
            case 'd': newX++; break;
            default: return false;
        }

        if (newX >= 0 && newX < width && newY >= 0 && newY < height && maze[newY][newX] != '#') {
            playerX = newX;
            playerY = newY;
            return true;
        }

        return false;
    }

    private boolean isGameWon() {
        return playerX == endX && playerY == endY;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMaze();
            System.out.print("Twoj ruch (w, a, s, d): ");
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                char move = input.charAt(0);
                if (!movePlayer(move)) {
                    System.out.println("Nie możesz tam iść!");
                } else if (isGameWon()) {
                    System.out.println("Gratulacje, wygrałeś!");
                    break;
                }
            }
        }
    }

    public char[][] getMaze() {
        return maze;
    }
    public void setMaze(char[][] maze) {
        this.maze = maze;
    }

    public int[] getStart() {
        return new int[]{playerX, playerY};
    }

    // Metoda zwracająca pozycję końcową
    public int[] getEnd() {
        return new int[]{endX, endY};
    }
    public void setPlayerPosition(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            playerX = x;
            playerY = y;
        }
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static void main(String[] args) {
        MazeGame game = new MazeGame(5, 5);
        game.startGame();
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
