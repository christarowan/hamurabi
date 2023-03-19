package com.zipcodewilmington.hammurabi;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    int people = 100;
    int acres = 1000;
    int grainBushels = 2800;
    int landVal = 19;
    int year = 1;



    public static void main(String[] args) {
       new Hammurabi().playGame();
    }

    void playGame() {
        System.out.println("\nO great Hammurabi!\n" +
                "You are in year " + year + " of your ten year rule.\n" +
                "In the previous year 0 people starved to death.\n" +
                "In the previous year 5 people entered the kingdom.\n" +
                "The population is now 100.\n" +
                "We harvested " + grainBushels + " bushels at 3 bushels per acre.\n" +
                "Rats destroyed 200 bushels, leaving 2800 bushels in storage.\n" +
                "The city owns " + acres + " of land.\n" +
                "Land is currently worth " + landVal + " bushels per acre.");

        for (int i = 0; i <= 9; i++) {

            int acresToBuy = askHowManyAcresToBuy(landVal, grainBushels);
            int acresToSell = askHowManyAcresToSell(acres);
            int grainToFeedPeople = askHowMuchGrainToFeedPeople(grainBushels);
            int acresToPlant = askHowManyAcresToPlant(acres, people, grainBushels);

            acres = acres + acresToBuy;
            acres = acres - acresToSell;
            grainBushels = grainBushels - (acresToBuy * landVal);
            grainBushels = grainBushels + (acresToSell * landVal);
            grainBushels = grainBushels - grainToFeedPeople;

            int plagueDeaths = plagueDeaths(people);
            people = people - plagueDeaths;

            int starved = starvationDeaths(people,grainToFeedPeople);
            people = people - starved;

            boolean isUprising = uprising(people,starved);
            if (isUprising) {
                System.out.println("You have been overthrown!  Thank you for playing!");
                System.exit(0);
            }

            if (starved == 0) {
                int immigrants = immigrants(people, acres, grainBushels);
                people = people + immigrants;
            }

            int grainHarvested = harvest(acresToPlant,0);
            grainBushels = grainBushels + grainHarvested;

            int grainEaten = grainEatenByRats(grainBushels);
            grainBushels = grainBushels - grainEaten;

            landVal = newCostOfLand();

            year ++;

            System.out.println("\nO great Hammurabi!\n" +
                "You are in year " + year + " of your ten year rule.\n" +
                "In the previous year 0 people starved to death.\n" +
                "In the previous year 5 people entered the kingdom.\n" +
                "The population is now 100.\n" +
                "We harvested " + grainBushels + " bushels at 3 bushels per acre.\n" +
                "Rats destroyed 200 bushels, leaving 2800 bushels in storage.\n" +
                "The city owns " + acres + " of land.\n" +
                "Land is currently worth " + landVal + " bushels per acre.");


    }
       // Each person needs at least 20 bushels of grain per year to survive
      //  Each person can farm at most 10 acres of land
       // It takes 2 bushels of grain to farm an acre of land
       // The market price for land fluctuates yearly
    }

    int askHowManyAcresToBuy ( int price, int bushels) {

        System.out.println("\nHow many acres would you like to buy at " + price + " bushels per acre?");
        int acresToBuy = getNumber("Enter a number:");
        int cost = acresToBuy * price;
        if (cost <= bushels) {
            acres += acresToBuy;
            grainBushels -= cost;
            System.out.println("You have purchased " + acresToBuy + " acres.");
        } else {
            System.out.println("You do not have enough bushels to buy that many acres.");
        }
        return acres;

    }

    int askHowManyAcresToSell (int acresOwned){

        System.out.println("\nHow many acres would you like to sell at " + landVal + " bushels per acre?");
        int acresToSell = getNumber("Enter a number:");
        int profit = acresToSell * landVal;
        if (acresOwned >= acresToSell) {
            acres += acresToSell;
            grainBushels += profit;
            System.out.println("You have sold " + acresToSell + " acres.");
        } else {
            System.out.println("You do not have enough acres to sell that many acres.");
        }
        return acres;

    }

    int askHowMuchGrainToFeedPeople (int bushels){

        System.out.println("\nHow many bushels would you like to feed to your people? Each person needs 20 bushels per year to survive.");
        int grainToFeedPeople = getNumber("Enter a number:");

        if (grainToFeedPeople <= grainBushels) {
            bushels -= grainBushels;
            System.out.println("You have fed your people " + grainToFeedPeople + " bushels.");
        } else {
            System.out.println("There are not " + grainToFeedPeople + " bushels available.");
        }
        return bushels;
    }

    int askHowManyAcresToPlant ( int acresOwned, int population, int bushels){

        System.out.println("\nHow many acres would you like to plant grain on?");
        System.out.println("You need 2 bushels per acre planted and each person can plant no more than ten acres.");
        int acresToPlant = getNumber("Enter a number:");

        if ((acresOwned >= acresToPlant) && (acresToPlant + acresOwned) < (people/10) && (acresToPlant) < (bushels/2)){
            acres += acresToPlant;
            System.out.println("You have planted " + acresToPlant + "acres.");
        } else {
            System.out.println("You do not have the resources to plant that many acres.");
        }
        return acres;
    }

    int getNumber (String message){
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }

    int plagueDeaths(int population) {
        boolean isPlague;
        Random rand = new Random();
        if (rand.nextInt(100) < 45) {
            return (int)(people*.5);
        }
        else return 0;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
        int grainNeeded = people*20;
        if (grainNeeded <= bushelsFedToPeople) { return 0;}
        else {
            return (int)((grainNeeded - bushelsFedToPeople)/20);
        }
    }

    boolean uprising(int population, int howManyPeopleStarved) {
        if (howManyPeopleStarved > .45 * population) {
            return true;
        }
        return false;
    }
    int immigrants(int population, int acresOwned, int grainInStorage) {
        return (20 * acresOwned + grainInStorage) / (100 * population) + 1;
    }

    int harvest(int acres, int bushelsUsedAsSeed) {
        Random rand = new Random();
        int yield = rand.nextInt(5) + 1;
        return yield * acres;
    }
    int grainEatenByRats(int bushels) {
        return 0;
    }

    int newCostOfLand() {
        return 19;
    }



}

