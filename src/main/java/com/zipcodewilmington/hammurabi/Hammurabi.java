package com.zipcodewilmington.hammurabi;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    int population = 100;
    int acres = 1000;
    int grainBushels = 2800;
    int landVal = 19;
    int year = 1;
    int acresToPlant = 0;
    int bushelsUsedAsSeed = 0;



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
            int acresToPlant = askHowManyAcresToPlant(acres, population, grainBushels);

            acres = acres + acresToBuy - acresToSell;
            grainBushels = grainBushels - (acresToBuy * landVal);
            grainBushels = grainBushels + (acresToSell * landVal);
            grainBushels = grainBushels - grainToFeedPeople;
          //  grainBushels = grainBushels - bushelsUsedAsSeed;


         //   int plagueDeaths = plagueDeaths(population);
         //   population = population - plagueDeaths;

            int starved = starvationDeaths(population,grainToFeedPeople);
            population = population - starved;

           /* boolean isUprising = uprising(people,starved);
            if (isUprising) {
                System.out.println("You have been overthrown!  Thank you for playing!");
                System.exit(0);
            } */

            if (starved == 0) {
                int immigrants = immigrants(population, acres, grainBushels);
                population = population + immigrants;
            }

            int grainHarvested = harvest(acresToPlant,0);
            grainBushels = grainBushels + grainHarvested;

           // int grainEaten = grainEatenByRats(grainBushels);
          //  grainBushels = grainBushels - grainEaten;

            landVal = newCostOfLand();

            year ++;

            System.out.println (population);
            System.out.println (acres);
            System.out.println (grainBushels);
            System.out.println (acresToPlant);
            System.out.println (bushelsUsedAsSeed);


            System.out.println("\nO great Hammurabi!\n" +
                "You are in year " + year + " of your ten year rule.\n" +
                "In the previous year 0 people starved to death.\n" +
                "In the previous year 5 people entered the kingdom.\n" +
                "The population is now 100.\n" +
                "We harvested " + grainBushels + " bushels at 3 bushels per acre.\n" +
                "Rats destroyed 200 bushels, leaving " + grainBushels + " bushels in storage.\n" +
                "The city owns " + acres + " acres of land.\n" +
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
            System.out.println("There are " + grainBushels + " bushels in stock. You can buy " + grainBushels/landVal +  " or less acres.");
            acresToBuy = getNumber("Enter a number:");
            acres += acresToBuy;
            grainBushels -= cost;
            System.out.println("You have purchased " + acresToBuy + " acres.");

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
            System.out.println("There are " + acres + " available to sell, how many would you like to sell?");
            acresToSell = getNumber("Enter a number less than or equal to " + acres +":");
            acres += acresToSell;
            grainBushels += profit;
            System.out.println("You have sold " + acresToSell + " acres.");
        }
        return acres;

    }

    int askHowMuchGrainToFeedPeople (int bushels){

        System.out.println("\nHow many bushels would you like to feed to your people? You need to use at least " + population *20 + " to feed all of your people.");
        int grainToFeedPeople = getNumber("Enter a number:");

        if (grainToFeedPeople <= grainBushels) {
            grainBushels -= grainToFeedPeople;
            System.out.println("You have fed your people " + grainToFeedPeople + " bushels.");
        } else {
            System.out.println("There are " + grainBushels + " bushels available.");
            grainToFeedPeople = getNumber("Enter a number less than or equal to " + grainBushels + ":");
            System.out.println("You have fed your people " + grainToFeedPeople + " bushels.");

        }
        return bushels;
    }

    int askHowManyAcresToPlant ( int acresOwned, int population, int bushels) {

        int maxAcresToPlant = Math.min(acresOwned, population * 10);
        System.out.println("\nYou can plant up to " + maxAcresToPlant + " acres.");
        int acresToPlant = getNumber("How many acres do you want to plant?");
        while (acresToPlant > maxAcresToPlant) {
            System.out.println("You can't plant more acres than you own or than the population can plant.");
            acresToPlant = getNumber("How many acres do you want to plant?");
        }
      //  int bushelsUsedAsSeed = acresToPlant * 2;
        while (bushelsUsedAsSeed > bushels) {
            System.out.println("You don't have enough bushels of seed to plant " + acresToPlant + " acres.");
            acresToPlant = getNumber("How many acres do you want to plant?");
           // bushelsUsedAsSeed = acresToPlant * 2;
        }
        System.out.println("You have planted " + acresToPlant + " acres.");
        bushelsUsedAsSeed = acresToPlant * 2;
        grainBushels -= bushelsUsedAsSeed;
        return acresToPlant;

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
            return (int)(population*.5);
        }
        else return 0;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
        int grainNeeded = population*20;
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
       int bushelsHarvested = acresToPlant;
       return bushelsHarvested;


    }
    int grainEatenByRats(int bushels) {
        Random rand = new Random();
        return 0;
    }

    int newCostOfLand() {
        return 19;
    }



}

