# Pizza Maker App

This project was carried out by <a href="https://github.com/Pierrafrom" target="_blank">Pierre Fromont</a>, <a href="https://github.com/Samuelito78" target ="_blank">Samuel Boix-Segura</a> and <a href="https://github.com/Gayar78" target="_blank">Rémi Thibault</a>.

## Application Objective

Our application aims to create a complete computer system for a pizzeria. The application presented here is specifically designed for pizzaiolos, aiming to simplify and speed up their work through an intuitive interface. The goal is to minimize the steps and interactions required to prepare a pizza order, optimizing efficiency and providing a fast and satisfying customer experience.

## Application Features

The application offers a comprehensive range of features for order management in a pizzeria, including:
1. Quick validation of order acceptance.
2. Simple and fast refusal of orders that cannot be processed.
3. Access to detailed order information for effective priority management.
4. Automatic refresh for instant addition of new orders.
5. Detailed view of each order, including the recipe for each ordered dish.

## How to Launch the Application?

A file `PizzaMakerApp.jar` is available in the `out/artifact/` folder with the code. Follow these steps to run it:
1. Ensure you have Java installed on your machine.
2. Open the cmd.
3. Navigate to the directory containing the `PizzaMakerApp.jar` file using the `cd` command.
4. Execute the following command to launch the application:
   > java -jar PizzaMakerApp.jar


## How to Test the Application?

Test the application by placing an order on our <a href="https://iut2orsaybestpizza.duckdns.org/" target="_blank">website</a>. Experience the simplicity and efficiency of our system within minutes.

## Code Organization

### MVC Structure

The project adopts an MVC architecture with dedicated packages for each component:

**config**: Essential configuration constants.
**controller**: User action handling classes.
**main**: Main class launching the application.
**model**: Data structuring classes.
**style**: Graphic elements of the user interface.
**utils**: Utility functions.
**view**: Elements visible to the user.
This structure makes the project modular, maintainable, and scalable.

### Management of Libraries and Dependencies

The project uses Maven for efficient dependency management, offering clear organization, automatic handling of third-party libraries, easy compilation, and seamless integration.

### Use of SQL Views
The application uses SQL views to simplify the code, anticipate future database changes, optimize performance by precalculating results, and facilitate maintenance.

### Code Documentation (JavaDoc)
JavaDoc documentation is provided in the `out/java-doc` folder. You can view it by opening the `index.html` file in the javadoc folder.
