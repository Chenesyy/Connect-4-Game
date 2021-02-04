package connect4_2.pkg0;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

class Connect4_20 {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    public static String play;
    public static String choose;
    public static char player;
    public static char AIcolor;
    public static boolean AI;
    public static String[] AImove = new String[2];
    public static char[][] grid = new char[6][7];
    public static int counter = 0;
    public static int lipat = 0;

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        MiniMax mimax = new MiniMax();
        //Random rand = new Random();
        //String ch = "ABCG";

        //initialize array
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = ' ';
            }
        }

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("A", 0);
        map.put("B", 1);
        map.put("C", 2);
        map.put("D", 3);
        map.put("E", 4);
        map.put("F", 5);
        map.put("G", 6);

        HashMap<Integer, String> map2 = new HashMap<Integer, String>();
        map2.put(0, "A");
        map2.put(1, "B");
        map2.put(2, "C");
        map2.put(3, "D");
        map2.put(4, "E");
        map2.put(5, "F");
        map2.put(6, "G");

        HashMap<Character, Character> disc = new HashMap<Character, Character>();
        disc.put('B', '⚫');
        disc.put('W', '○');

        HashMap<Character, String> turn = new HashMap<Character, String>();
        turn.put('B', "Black");
        turn.put('W', "White");

        int winner = 0;

        System.out.println("~~~WELCOME TO SHIFTABLE CONNECT FOUR GAME~~~");
        System.out.println("Presented to you by: CHEN, SABLAYA & SY");
        System.out.println();

        System.out.println("Hello! Choose your color:\n[B] Black\n[W] White");
        System.out.print("Color: ");
        player = read.next().toUpperCase().charAt(0);
        while (!(player == 'B' || player == 'W')) {
            System.out.print("Please choose between [B] or [W]: ");
            player = read.next().toUpperCase().charAt(0);
        }

        if (player == 'B') {
            System.out.println("You are Player 2! White player(AI) goes first");
            player = 'W';
            AI = true;
            AIcolor = 'W';
        } else {
            System.out.println("You are Player 1! You take the first move");
            player = 'W';
            AI = false;
            AIcolor = 'B';
        }

        //play a turn
        do {
            boolean validPlay = false;
            //String choose;

            System.out.println("[1] Drop piece\n[2] Shift\n[3] Forfeit");
            System.out.print(turn.get(player) + " player choose: ");
            if ((AI && player == 'W') || AI && player == 'B') {
                choose = Integer.toString(AImove());
            } else {
                choose = read.next();
            }
            while (!(choose.equals("1") || choose.equals("2") || choose.equals("3"))) {
                System.out.print("Hey " + turn.get(player) + " player! Choose between [1] [2] or [3]: ");
                choose = read.next();
            }

            while (validPlay == false) {
                if (choose.equals("1")) {
                    display();
                    while (validPlay == false) {
                        System.out.print("[A-G] Column\n[X] Back\nPlayer " + turn.get(player) + ", choose a column: ");
                        if ((AI && player == 'W') || AI && player == 'B') {
                            //play = map2.get(mimax.bestMove(grid, 7));
                            play = map2.get(mimax.minimax(grid, 4));
                            //play = map2.get(mimax.decideMove(grid, 10));
                            //play = Character.toString(ch.charAt(rand.nextInt(ch.length())));
                        } else {
                            play = read.next().toUpperCase();
                        }
                        validPlay = drop(map, disc);
                    }
                } else if (choose.equals("2")) {
                    display();
                    while (validPlay == false) {
                        System.out.println("[L] Left-Shift\n[R] Right-Shift\n[X] Back");
                        System.out.print("Shift to what direction player " + turn.get(player) + ": ");
                        if ((AI && player == 'W') || AI && player == 'B') {
                            play = AIshift();
                        } else {
                            play = read.next().toUpperCase();
                        }
                        validPlay = shift();
                    }
                } else if (choose.equals("3")) {
                    validPlay = true;
                } else {
                    validPlay = true;
                }
                display();
            }
            //determine if there is a winner
            winner = isWinner();

            //exit
            if (choose.equals("3")) {
                break;
            } else if (play.equals("X")) {
                if (player == 'B') {
                    player = 'W';
                } else {
                    player = 'B';
                }
                if (AI) {
                    AI = false;
                } else {
                    AI = true;
                }
            }

            //switch players
            if (player == 'B') {
                player = 'W';
            } else {
                player = 'B';
            }

            if (AI) {
                AI = false;
            } else {
                AI = true;
            }
        } while (winner == 0 && counter <= 41 && !(choose.equals("3")));
        display();

        df2.setRoundingMode(RoundingMode.UP);

        if (winner != 0) {
            if (winner == 1) {
                System.out.println("White player's score: " + df2.format(scoreWhite()));
                System.out.println("Black player's score: " + df2.format(scoreBlack()));
                System.out.println("Congratulations! You won white player!!");
            } else if (winner == 2) {
                System.out.println("White player's score: " + df2.format(scoreWhite()));
                System.out.println("Black player's score: " + df2.format(scoreBlack()));
                System.out.println("Congratulations! You won black player!!");
            }
        } else if (choose.equals("3")) {
            System.out.println("White player's score: " + df2.format(scoreWhite()));
            System.out.println("Black player's score: " + df2.format(scoreBlack()));
            System.out.println("The game has been forfeited");
        } else {
            System.out.println("White player's score: " + df2.format(scoreWhite()));
            System.out.println("Black player's score: " + df2.format(scoreBlack()));
            System.out.println("Draw Game!");
        }
    }

    public static void display() {
        System.out.println("\n  ---------------");
        for (int row = 0; row < grid.length; row++) {
            System.out.print(row + 1 + " |");
            for (int col = 0; col < grid[0].length; col++) {
                System.out.print(grid[row][col]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("  ---------------");
        }
        System.out.println("   A B C D E F G");
        System.out.println();
    }

    public static boolean drop(HashMap<String, Integer> map, HashMap<Character, Character> disc) {
        if (play.equals("X")) {
            return true;
        } else if (!(play.equals("A") || play.equals("B") || play.equals("C") || play.equals("D") || play.equals("E") || play.equals("F") || play.equals("G"))) { //check if tama ba na within A-G yng tinpye ni player
            System.out.println("Pick a column that's within letters A to G!");
            return false;
        } else if (grid[0][map.get(play)] != ' ') {
            System.out.println("Column " + play.toUpperCase() + " is full."); //pagpuno na si col, need new input
            return false;
        } else if ((play.equals("A") || play.equals("B") || play.equals("C") || play.equals("D") || play.equals("E") || play.equals("F") || play.equals("G"))) { //check if tama ba na within A-G yng tinpye ni player
            for (int row = grid.length - 1; row >= 0; row--) {
                if (grid[row][map.get(play)] == ' ') {
                    grid[row][map.get(play)] = player;
                    counter++;
                    lipat = 0;
                    return true;
                }
            }
        }
        return true;
    }

    public static boolean shift() {
        char[][] temp = new char[6][1];
        if (play.equals("X")) {
            return true;
        } else if (!(play.equals("L") || play.equals("R"))) { //check if tama ba na within A-G yng tinpye ni player
            System.out.println("Hey! Pick between [L] or [R]");
            return false;
        }
        switch (lipat) {
            case 0:
                if (play.equals("L")) {
                    for (int i = 0; i < 6; i++) { //h=6
                        temp[i][0] = grid[i][0];
                        grid[i][0] = grid[i][1];
                        grid[i][1] = grid[i][2];
                        grid[i][2] = grid[i][3];
                        grid[i][3] = grid[i][4];
                        grid[i][4] = grid[i][5];
                        grid[i][5] = grid[i][6];
                        grid[i][6] = temp[i][0];
                    }
                    lipat = 1;
                    break;
                } else if (play.equals("R")) {
                    for (int i = 0; i < 6; i++) { //h=6
                        temp[i][0] = grid[i][6];
                        grid[i][6] = grid[i][5];
                        grid[i][5] = grid[i][4];
                        grid[i][4] = grid[i][3];
                        grid[i][3] = grid[i][2];
                        grid[i][2] = grid[i][1];
                        grid[i][1] = grid[i][0];
                        grid[i][0] = temp[i][0];
                    }
                    lipat = 2;
                    break;
                }
            case 1:
                if (play.equals("L")) {
                    for (int i = 0; i < 6; i++) { //h=6
                        temp[i][0] = grid[i][0];
                        grid[i][0] = grid[i][1];
                        grid[i][1] = grid[i][2];
                        grid[i][2] = grid[i][3];
                        grid[i][3] = grid[i][4];
                        grid[i][4] = grid[i][5];
                        grid[i][5] = grid[i][6];
                        grid[i][6] = temp[i][0];
                    }
                    lipat = 1;
                    break;
                } else {
                    System.out.println("Sorry, you can only shift to the left [L]...");
                    return false;
                }
            case 2:
                if (play.equals("R")) {
                    for (int i = 0; i < 6; i++) { //h=6
                        temp[i][0] = grid[i][6];
                        grid[i][6] = grid[i][5];
                        grid[i][5] = grid[i][4];
                        grid[i][4] = grid[i][3];
                        grid[i][3] = grid[i][2];
                        grid[i][2] = grid[i][1];
                        grid[i][1] = grid[i][0];
                        grid[i][0] = temp[i][0];
                    }
                    lipat = 2;
                    break;
                } else {
                    System.out.println("Sorry, you can only shift to the right [R]...");
                    return false;
                }
        }
        return true;
    }

    public static int isWinner() {
        if (player == 'W') {
            //check for 4 across
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row][col + 1] == 'B' && grid[row][col + 2] == 'B' && grid[row][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row][col + 1] == 'W' && grid[row][col + 2] == 'W' && grid[row][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            //check for 4 up and down
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    if (grid[row][col] == 'B' && grid[row + 1][col] == 'B' && grid[row + 2][col] == 'B' && grid[row + 3][col] == 'B') {
                        return 2;
                    } else if (grid[row][col] == 'W' && grid[row + 1][col] == 'W' && grid[row + 2][col] == 'W' && grid[row + 3][col] == 'W') {
                        return 1;
                    }
                }
            }
            //check upward diagonal
            for (int row = 3; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row - 1][col + 1] == 'B' && grid[row - 2][col + 2] == 'B' && grid[row - 3][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            for (int row = 3; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row - 1][col + 1] == 'W' && grid[row - 2][col + 2] == 'W' && grid[row - 3][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            //check downward diagonal
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row + 1][col + 1] == 'B' && grid[row + 2][col + 2] == 'B' && grid[row + 3][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row + 1][col + 1] == 'W' && grid[row + 2][col + 2] == 'W' && grid[row + 3][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
        } else if (player == 'B') {
            //check for 4 across
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row][col + 1] == 'W' && grid[row][col + 2] == 'W' && grid[row][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row][col + 1] == 'B' && grid[row][col + 2] == 'B' && grid[row][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            //check for 4 up and down
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    if (grid[row][col] == 'W' && grid[row + 1][col] == 'W' && grid[row + 2][col] == 'W' && grid[row + 3][col] == 'W') {
                        return 1;
                    } else if (grid[row][col] == 'B' && grid[row + 1][col] == 'B' && grid[row + 2][col] == 'B' && grid[row + 3][col] == 'B') {
                        return 2;
                    }
                }
            }
            //check upward diagonal
            for (int row = 3; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row - 1][col + 1] == 'W' && grid[row - 2][col + 2] == 'W' && grid[row - 3][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            for (int row = 3; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row - 1][col + 1] == 'B' && grid[row - 2][col + 2] == 'B' && grid[row - 3][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
            //check downward diagonal
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'W' && grid[row + 1][col + 1] == 'W' && grid[row + 2][col + 2] == 'W' && grid[row + 3][col + 3] == 'W') {
                        return 1;
                    }
                }
            }
            for (int row = 0; row < grid.length - 3; row++) {
                for (int col = 0; col < grid[0].length - 3; col++) {
                    if (grid[row][col] == 'B' && grid[row + 1][col + 1] == 'B' && grid[row + 2][col + 2] == 'B' && grid[row + 3][col + 3] == 'B') {
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    public static double scoreWhite() {
        double total = 0;
        double count = 0;
        if (choose.equals("3") && player == 'W') {
            double forfeit = 0;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col] == 'W' || grid[row][col] == 'B') {
                        count = count + 0.01;
                    }
                }
            }
            if (isWinner() == 1) {
                total = 1 - count;
            } else if (isWinner() == 2) {
                total = 0 + count;
            } else if (counter == 42) {
                total += 0.5;
            }
            total = total + forfeit;
        } else if (choose.equals("3") && player == 'B') {
            double forfeit = 0.8;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col] == 'W' || grid[row][col] == 'B') {
                        count += 0.01;
                    }
                }
            }
            if (isWinner() == 1) {
                total = 1 - count;
            } else if (isWinner() == 2) {
                total = 0 + count;
            } else if (counter == 42) {
                total += 0.5;
            }
            total = total + forfeit;
        } else {
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col] == 'W' || grid[row][col] == 'B') {
                        count += 0.01;
                    }
                }
            }
            if (isWinner() == 1) {
                total = 1 - count;
            } else if (isWinner() == 2) {
                total = 0 + count - 0.01;
            } else if (counter == 42) {
                total += 0.5;
            }
        }
        return total;
    }

    public static double scoreBlack() {
        double total = 0;
        double count = 0;
        if (choose.equals("3") && player == 'W') {
            double forfeit = 0.8;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col] == 'W' || grid[row][col] == 'B') {
                        count = count + 0.01;
                    }
                }
            }
            if (isWinner() == 1) {
                total = 0 + count;
            } else if (isWinner() == 2) {
                total = 1 - count;
            } else if (counter == 42) {
                total += 0.5;
            }
            total = total + forfeit;
        } else if (choose.equals("3") && player == 'B') {
            double forfeit = 0;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col] == 'W' || grid[row][col] == 'B') {
                        count = count + 0.01;
                    }
                }
            }
            if (isWinner() == 1) {
                total = 0 + count;
            } else if (isWinner() == 2) {
                total = 1 - count;
            } else if (counter == 42) {
                total += 0.5;
            }
            total = total + forfeit;
        } else {
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col] == 'W' || grid[row][col] == 'B') {
                        count = count + 0.01;
                    }
                }
            }
            if (isWinner() == 1) {
                total = 0 + count - 0.01;
            } else if (isWinner() == 2) {
                total = 1 - count;
            } else if (counter == 42) {
                total += 0.5;
            }
        }
        return total;
    }

    public static int AImove() {
        ArrayList<Integer> rw = new ArrayList<Integer>();
        ArrayList<Integer> rws = new ArrayList<Integer>();
        boolean meron = false;
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][0] == AIcolor && grid[row][1] == AIcolor && grid[row][2] == AIcolor) {
                rw.add(row);
                meron = true;
            }
        }
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][4] == AIcolor && grid[row][5] == AIcolor && grid[row][6] == AIcolor) {
                rws.add(row);
                meron = true;
            }
        }
        if (meron) {
            for (int i = 0; i < rw.size(); i++) {
                if (grid[rw.get(i)][6] == AIcolor) {
                    return 2;
                }
            }
            for (int j = 0; j < rws.size(); j++) {
                if (grid[rws.get(j)][0] == AIcolor) {
                    return 2;
                }
            }
        }
        return 1;
    }

    public static String AIshift() {
        String where = "";
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][0] == AIcolor && grid[row][1] == AIcolor && grid[row][2] == AIcolor) {
                where = "R";
            }
        }
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][4] == AIcolor && grid[row][5] == AIcolor && grid[row][6] == AIcolor) {
                where = "L";
            }
        }
        return where;
    }
}
