package model;

public class PizzaMaker {
    private Level level;
    private int idPM;
    private String nomPM;
    private String prenomPM;
    private boolean available;
    private long lastOrderTime;

    public enum Level {
        BEGINNER(60), INTERMEDIATE(45), PRO(30);

        private final int preparationTime;

        Level(int preparationTime) {
            this.preparationTime = preparationTime;
        }

        public int getPreparationTime() {
            return preparationTime;
        }
    }

    /*-------------------------------------------------
      ----------------- CONSTRUCTOR -------------------
      -------------------------------------------------*/
    public PizzaMaker(int idPM, String nomPM, String prenomPM, boolean available, Level level) {
        this.idPM = idPM;
        this.nomPM = nomPM;
        this.prenomPM = prenomPM;
        this.available = available;
        this.level = level;
    }

    /*-------------------------------------------------
      ---------------GETTERS & SETTERS-----------------
      -------------------------------------------------*/

    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean status){
        this.available = status;
    }
    public String getPrenomPM() {
        return prenomPM;
    }

    public String getNomPM() {
        return nomPM;
    }

    public int getIdPM() {
        return idPM;
    }

    /*-------------------------------------------------
      ------------------- METHODS ---------------------
      -------------------------------------------------*/
    public void takeOrder(int i) {
        if (isAvailable() && canTakeOrder()) {
            setAvailable(false); // Set the PizzaMaker as unavailable
            lastOrderTime = System.currentTimeMillis(); // Record the time when the order was taken

            // Your order processing logic goes here
            int preparationTime = level.getPreparationTime();

            // Ensure the preparation time does not exceed the maximum allowed (2 minutes)
            preparationTime = Math.min(preparationTime, 120);

            // Assuming order processing takes some time...
            try {
                Thread.sleep(preparationTime * 1000); // Sleep for preparationTime seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Pizza Maker is available");
            setAvailable(true); // Set the PizzaMaker as available again
        } else {
            System.out.println("Pizza Maker is not available or needs to wait before taking another order.");
        }
    }

    private boolean canTakeOrder() {
        // Check if two minutes have passed since the last order was taken
        return System.currentTimeMillis() - lastOrderTime >= 120000;
    }


}
