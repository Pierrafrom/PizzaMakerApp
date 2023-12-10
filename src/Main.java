import controller.CPizzaMakerApp;
import model.Kitchen;
import model.MPizzaMakerApp;
import model.PizzaMaker;
import view.VPizzaMakerApp;

public class Main {
    // Constants for the number of pizza makers and maximum orders
    public static final int NB_PM = 3;
    public static final int MAX_ORDERS = 20;

    public static void main(String[] args) {
        // Creating an array to store the pizza maker team
        PizzaMaker[] team = new PizzaMaker[NB_PM];

        // Initializing pizza makers with different skill levels
        team[0] = new PizzaMaker(1, "Vinsmoke", "Sanji", PizzaMaker.Level.PRO);
        team[1] = new PizzaMaker(2, "Giovanna", "Giorno", PizzaMaker.Level.INTERMEDIATE);
        team[2] = new PizzaMaker(3, "Corleone", "Vito", PizzaMaker.Level.BEGINNER);

        // Creating a kitchen with the pizza maker team and initial orders
        // for now the orders will just be IDs but in the future it will maybe interact with the database
        Kitchen baratie = new Kitchen(team, new int[]{1, 2, 5, 3, 4, 6, 84, 51, 12, 62});

        // Setting the pizza maker team for the kitchen
        baratie.setTeam(team);

        // Starting the threads for each pizza maker
        team[0].start();
        team[1].start();
        team[2].start();


        MPizzaMakerApp model = new MPizzaMakerApp();
        VPizzaMakerApp view = new VPizzaMakerApp();
        CPizzaMakerApp controller = new CPizzaMakerApp(model, view);

        // Start the application
        controller.startApplication();
    }

}
