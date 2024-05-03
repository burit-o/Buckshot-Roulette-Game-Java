import java.util.Scanner;

public class BuckshotRoulette {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        printlnSlow("**** Buckshot Roulette Game ****");
        System.out.println();
        String name1, name2;
        println("Enter your name (Player 1): ");
        name1 = sc.nextLine();
        println("Enter your name (Player 2): ");
        name2 = sc.nextLine();
        Player currentPlayer = new Player(name1);
        Player waitingPlayer = new Player(name2);
        printlnSlow("Shotgun is loaded randomly with live and empty shells.");
        Thread.sleep(300);
        printlnSlow("Players take turns taking the gun and deciding what to do.");
        Thread.sleep(300);
        printlnSlow("Players start the game with 1 Medicine and 2 Magnifying Glass");
        Thread.sleep(300);
        printlnSlow("Medicine: Increases health by 1");
        Thread.sleep(300);
        printlnSlow("Magnifying Glass: Allows player to see the bullet in the chamber");
        Thread.sleep(100);
        boolean load[];
        int round = 1;
        while (currentPlayer.getHealth() > 0 || waitingPlayer.getHealth() > 0) {
            int index = 0;
            System.out.println();
            println(" ---- Round " + round + " ---- ");
            Thread.sleep(1000);
            System.out.println();
            int size = (int) (3 + (Math.random() * 3)); // Shell number is randomly generated between 3 - 6
            int alive = 0;
            int empty = 0;
            // Loading Shotgun
            load = new boolean[size];
            while (alive == 0 || empty == 0) {
                for (int i = 0; i < size; i++) {
                    if (Math.random() > 0.35) {
                        load[i] = true;
                        alive++;
                    } else {
                        empty++;
                    }
                }
                if (empty == 0 || alive == 0) {
                    empty = 0;
                    alive = 0;
                }
            }
            System.out.println();
            run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
        }

    }

    public static void run(Player currentPlayer, Player waitingPlayer, int index, boolean[] load, int alive, int empty, int round) throws Exception {
        Player tempPlayer;
        Scanner sc = new Scanner(System.in);
        println("Shotgun is loaded with " + (load.length - index) + " shells");
        println(alive + " Alive, " + empty + " Empty");
        // Display Choice Menu
        println(" ** " + currentPlayer.getName() + "'s Turn ** ");
        println("Health : " + currentPlayer.getHealth());
        System.out.println();
        printlnFast("-> Magnifying Glass = " + currentPlayer.getMagnifyingGlass() + " (Enter 1)");
        printlnFast("-> Medicine = " + currentPlayer.getMedic() + " (Enter 2)");
        printlnFast("-> Pull trigger to enemy (Enter 3)");
        printlnFast("-> Pull trigger to yourself (Enter 4)");
        int choice = sc.nextInt();
        System.out.println();

        switch (choice) {
            case 1:
                if (currentPlayer.getMagnifyingGlass() == 0) {
                    println("Out of magnifying glasses");
                    System.out.println();
                    run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                } else {
                    print("Using magnifying glasses");
                    System.out.print(".");
                    Thread.sleep(400);
                    System.out.print(".");
                    Thread.sleep(400);
                    System.out.print(".");
                    Thread.sleep(400);
                    System.out.println();
                    useGlass(index, load);
                    currentPlayer.setMagnifyingGlass(currentPlayer.getMagnifyingGlass() - 1);
                    run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                }
                break;
            case 2:
                if (currentPlayer.getMedic() == 0) {
                    println("Out of medicines");
                    System.out.println();
                    run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                } else {
                    currentPlayer.setHealth(currentPlayer.getHealth() + 1);
                    currentPlayer.setMedic(currentPlayer.getMedic() - 1);
                    run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                }
                break;
            case 3:
                print("Pulling trigger to enemy!");
                System.out.print(".");
                Thread.sleep(400);
                System.out.print(".");
                Thread.sleep(400);
                System.out.print(".");
                Thread.sleep(400);
                System.out.println();
                if (load[index]) {
                    println("...Shell was Alive! You shot your enemy!");
                    System.out.println();
                    waitingPlayer.reduceHealth();
                    alive--;
                    check(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                    tempPlayer = currentPlayer;
                    currentPlayer = waitingPlayer;
                    waitingPlayer = tempPlayer;
                    index++;
                    check(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                    run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                } else {
                    println("...Shell was Empty! Enemy's turn");
                    System.out.println();
                    empty--;
                    tempPlayer = currentPlayer;
                    currentPlayer = waitingPlayer;
                    waitingPlayer = tempPlayer;
                    index++;
                    check(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                    run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                }
                break;
            case 4:
                print("Pulling trigger to yourself");
                System.out.print(".");
                Thread.sleep(400);
                System.out.print(".");
                Thread.sleep(400);
                System.out.print(".");
                Thread.sleep(400);
                System.out.println();
                if (load[index]) {
                    println("...Shell was Alive! You shot yourself!");
                    System.out.println();
                    currentPlayer.reduceHealth();
                    alive--;
                    index++;
                    check(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                    tempPlayer = currentPlayer;
                    currentPlayer = waitingPlayer;
                    waitingPlayer = tempPlayer;
                    run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                } else {
                    println("...Shell was Empty!");
                    System.out.println();
                    empty--;
                    index++;
                    check(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                    run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
                }
                break;
            default:
                println("Invalid value entered");
                System.out.println();
                run(currentPlayer, waitingPlayer, index, load, alive, empty, round);
        }
    }

    public static void useGlass(int index, boolean[] load) throws Exception {
        if (load[index]) {
            println(" ... Shell is Alive!");
            System.out.println();
        } else {
            println("... Shell is Empty");
            System.out.println();
        }
    }

    public static void check(Player player1, Player player2, int index, boolean[] load, int alive, int empty, int round) throws Exception {
        if (player1.getHealth() == 0) {
            println(player1.getName() + " is Dead!.. " + player2.getName() + " Wins!!!");
            System.exit(0);
        } else if (player2.getHealth() == 0) {

            println(player2.getName() + " is Dead!.. " + player1.getName() + " Wins!!!");
            System.exit(0);
        } else {
            if (load.length - index == 0) {
                println(" ---- Round " + round + " Ended! ----");
                round++;
                System.out.println();
                println(" ---- Round " + round + " ----");
                int size = (int) (3 + (Math.random() * 3));
                alive = 0;
                empty = 0;
                index = 0;

                load = new boolean[size];
                while (alive == 0 || empty == 0) {
                    for (int i = 0; i < size; i++) {
                        if (Math.random() > 0.35) {
                            load[i] = true;
                            alive++;
                        } else {
                            empty++;
                        }
                    }
                    if (empty == 0 || alive == 0) {
                        empty = 0;
                        alive = 0;
                    }
                }
                run(player1, player2, index, load, alive, empty, round);
            }
        }

    }

    // Printing methods
    public static void print(String text) throws Exception {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(10);
        }
    }

    public static void printSlow(String text) throws Exception {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(35);
        }
    }

    public static void printFast(String text) throws Exception {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(4);
        }
    }

    public static void println(String text) throws Exception {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(10);
        }
        System.out.println();
    }

    public static void printlnSlow(String text) throws Exception {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(35);
        }
        System.out.println();
    }

    public static void printlnFast(String text) throws Exception {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            Thread.sleep(4);
        }
        System.out.println();
    }
}

class Player {
    private String name;
    private int health;
    private int magnifyingGlass;
    private int medic;

    public Player(String name) {
        this.name = name;
        this.health = 5;
        this.magnifyingGlass = 2;
        this.medic = 1;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void reduceHealth() {
        this.health--;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMagnifyingGlass() {
        return magnifyingGlass;
    }

    public void setMagnifyingGlass(int magnifyingGlass) {
        this.magnifyingGlass = magnifyingGlass;
    }

    public int getMedic() {
        return medic;
    }

    public void setMedic(int medic) {
        this.medic = medic;
    }
}
