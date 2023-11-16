package model;

public class Kitchen {
    private PizzaMaker[] pizzaMakersTeam;
    private int[] orderList; //for now lets say that you just input in this array the id of every order

    public Kitchen(PizzaMaker[] PMT, int[] cL){
        this.pizzaMakersTeam = PMT;
        this.orderList = cL;
    }


}
