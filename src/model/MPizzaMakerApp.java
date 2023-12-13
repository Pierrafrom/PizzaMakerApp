package model;


/**
 * The MPizzaMakerApp class represents the model in the Model-View-Controller (MVC) architecture
 * for the pizza maker application. It holds the application's data and provides methods for accessing
 * and modifying that data. In this case, it specifically manages the currently selected pizza.
 *
 * @author Samuel Boix-Segura
 * @since 2023-12-10
 */

public class MPizzaMakerApp {
    private int selectedPizza;

    /**
     * Gets the currently selected pizza.
     *
     * @return A string representing the currently selected pizza.
     */
    public int getSelectedPizza() {
        return selectedPizza;
    }

    /**
     * Sets the selected pizza to the specified value.
     *
     * @param selectedPizza A string representing the newly selected pizza.
     */
    public void setSelectedPizza(int selectedPizza) {
        this.selectedPizza = selectedPizza;
    }

}
