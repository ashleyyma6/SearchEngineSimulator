import java.util.*;
import java.lang.String;

//*******************************************************************
// Class Name: main
//
// Description: This is the main class where the program starts
// It contains main() method
//*******************************************************************
public class Main {
    //*******************************************************************
//  Method Name: main
//
// Description: This is the method where the program starts
// It will print the menu of the search engine
//*******************************************************************
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Simulator aSimulator = new Simulator();
        boolean stopProgram = false;
        long startTime;
        long endTime;
        long duration;
        while (!stopProgram) {
            System.out.println("Hello! Please enter your choice.\n[S]search\n[C]change website score\n[P]Show today's top 10 keywords\n[Q]quit");
            char choice = input.next().toUpperCase().charAt(0);
            switch (choice) {
                case 'S':
                    startTime = System.nanoTime();
                    aSimulator.search();
                    endTime = System.nanoTime();
                    duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                    System.out.println("search complexity: " + duration);
                    System.out.println();
                    break;
                case 'C':
                    startTime = System.nanoTime();
                    aSimulator.changeScore();
                    endTime = System.nanoTime();
                    duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                    System.out.println("change socre complexity: " + duration);
                    System.out.println();
                    break;
                case 'P':
                    startTime = System.nanoTime();
                    aSimulator.printTop10Keyword();
                    endTime = System.nanoTime();
                    duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                    System.out.println("print top 10 keyword complexity: " + duration);
                    System.out.println();
                    break;
                case 'Q':
                    System.out.println();
                    stopProgram = true;
                    break;
                default:
                    System.out.println("Wrong input, enter again.");
                    break;
            }
        }
    }
}

