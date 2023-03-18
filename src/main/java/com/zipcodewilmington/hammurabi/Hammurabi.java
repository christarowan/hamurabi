package com.zipcodewilmington.hammurabi;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    void playGame() {
        int people = 100;
        int acres = 1000;
        int grainBushels = 2800;
        int landVal = 19;

        System.out.println("O great Hammurabi!\n" +
                "You are in year 1 of your ten year rule.\n" +
                "In the previous year 0 people starved to death.\n" +
                "In the previous year 5 people entered the kingdom.\n" +
                "The population is now 100.\n" +
                "We harvested 3000 bushels at 3 bushels per acre.\n" +
                "Rats destroyed 200 bushels, leaving 2800 bushels in storage.\n" +
                "The city owns 1000 acres of land.\n" +
                "Land is currently worth 19 bushels per acre.");

        int acresToBuy = askHowManyAcresToBuy(landVal, grainBushels);
        if (grainBushels >= acres * 19) {
            System.out.println("You bought " + Integer.toString(acresToBuy) + " acres");
        } else {System.out.println("There is not enough grain available to buy " + Integer.toString(acresToBuy) +" acres. " +
                "Please enter another number.");
             acresToBuy = askHowManyAcresToBuy(landVal, grainBushels);
            System.out.println("You bought " + Integer.toString(acresToBuy) + " acres");
        }

        int acresToSell = askHowManyAcresToSell(acres);
        if (acres >= acres) {
            System.out.println("You sold " + Integer.toString(acresToSell) + " acres");
        } else {System.out.println("There are not enough acres available to buy " + Integer.toString(acresToSell) +" acres. " +
                "Please enter another number.");
            acresToSell = askHowManyAcresToSell(acres);
            System.out.println("You bought " + Integer.toString(acresToSell) + " acres");
        }

        int grainToFeedPeople = askHowMuchGrainToFeedPeople(grainBushels);
        if (grainBushels >= grainBushels) {
            System.out.println("You fed your people " + Integer.toString(grainToFeedPeople) + " bushels");
        } else {System.out.println("There are not enough bushels available to provide " + Integer.toString(grainToFeedPeople) +" grain. " +
                "Please enter another number.");
            grainToFeedPeople = askHowMuchGrainToFeedPeople(grainBushels);
            System.out.println("You bought " + Integer.toString(grainBushels) + " acres");
        }

    }

    int askHowManyAcresToBuy(int price, int bushels) {
        int acres = getNumber("\n How many acres do you wish to buy?\n");
        return acres;

    }

    int askHowManyAcresToSell(int acresOwned) {
        int acres = getNumber("\n How many acres to do you wish to sell?");
        return acres;
    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        int grainBushels = getNumber("\n How many bushels of grain do you wish to feed your people?");
        return grainBushels;
    }

    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }
    //other methods go here
}

