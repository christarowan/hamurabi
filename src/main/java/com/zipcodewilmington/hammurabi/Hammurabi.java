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
          

            int starved = starvationDeaths(population, grainToFeedPeople);

           // boolean isPlague =

            if (grainToFeedPeople < population * 20) {
                int grainDeficit = population * 20 - grainToFeedPeople;
                int peopleStarved = grainDeficit / 20;
                starved += peopleStarved;
            }
            population = population - starved;


            /* boolean isUprising = uprising(people,starved);
            if (isUprising) {
                System.out.println("You have been overthrown!  Thank you for playing!");
                System.exit(0);
            } */

            int immigrants = 0;
            if (starved == 0) {
                immigrants = immigrants(population, acres, grainBushels);
                population = population + immigrants;

            }

            int grainHarvested = harvest(acresToPlant);
            grainBushels = grainBushels + grainHarvested;

             int grainEaten = grainEatenByRats(grainBushels);
             grainBushels = grainBushels - grainEaten;

            landVal = newCostOfLand();

            year++;

            /*System.out.println(population);
            System.out.println(acres);
            System.out.println(grainBushels);
            System.out.println(acresToPlant);
            System.out.println(bushelsUsedAsSeed);
            System.out.println(grainHarvested);*/


            System.out.println("\nO great Hammurabi!\n" +
                    "You are in year " + year + " of your ten year rule.\n" +
                    "In the previous year " + starved + " people starved to death.\n" +
                    "In the previous year " + immigrants + " people entered the kingdom.\n" +
                    "The population is now " + population + ".\n" +
                    "We harvested " + grainHarvested + " bushels at 3 bushels per acre.\n" +
                    "Rats destroyed " + grainEaten + " bushels, leaving " + grainBushels + " bushels in storage.\n" +
                    "The city owns " + acres + " acres of land.\n" +
                    "Land is currently worth " + landVal + " bushels per acre.");

        }

    }
    int askHowManyAcresToBuy ( int price, int bushels) {

        System.out.println("\nHow many acres would you like to buy at " + landVal + " bushels per acre?");
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
            grainBushels -= grainToFeedPeople;

        }
        return bushels;
    }
    int askHowManyAcresToPlant ( int acresOwned, int population, int bushels) {

        int maxAcresToPlant = Math.min(acresOwned, population * 10);
       // System.out.println("\nYou can plant up to " + maxAcresToPlant + " acres.");
        int acresToPlant = getNumber("\nHow many acres do you want to plant?");
        while (acresToPlant > maxAcresToPlant) {
            System.out.println("You only have enough people to plant " + population * 10 + " acres or less.");
            acresToPlant = getNumber("How many acres do you want to plant?");
        }

        while (bushelsUsedAsSeed > bushels) {
            System.out.println("You don't have enough bushels of seed to plant " + acresToPlant + " acres.");
            System.out.println("There are " + grainBushels + " available, you can plant up to " + grainBushels/2 + " acres or less.");
            acresToPlant = getNumber("How many acres do you want to plant?");

        }
        System.out.println("You have planted " + acresToPlant + " acres.");
        bushelsUsedAsSeed = acresToPlant * 2;
        grainBushels -= bushelsUsedAsSeed;
        return acresToPlant;

    }
    int getNumber (String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("\"" + scanner.next() + "\" isn't a number!");
                        }
                    }
                }
                int plagueDeaths ( int population){
                    boolean isPlague;
                    Random rand = new Random();
                    if (rand.nextInt(100) < 45) {
                        return (int) (population * .5);
                    } else return 0;
                }

                int starvationDeaths ( int population, int bushelsFedToPeople){
                    int grainNeeded = population * 20;
                    if (grainNeeded <= bushelsFedToPeople) {
                        return 0;
                    } else {
                        return (int) ((grainNeeded - bushelsFedToPeople) / 20 +1);
                    }
                }

                boolean uprising ( int population, int howManyPeopleStarved){
                    if (howManyPeopleStarved > .45 * population) {
                        return true;
                    }
                    return false;
                }
                int immigrants ( int population, int acresOwned, int grainInStorage){
                    return (20 * acresOwned + grainInStorage) / (100 * population) + 1;
                }

                int harvest ( int acres){


                    int yieldPerAcre = (int) (Math.random() * 6) + 1; // generate random number between 1 and 6 (inclusive)
                    int totalYield = yieldPerAcre * acres; // calculate total yield
                    int grainHarvested = totalYield - bushelsUsedAsSeed;
                    return grainHarvested;


                }
                int grainEatenByRats ( int bushels){
                    if (Math.random() < 0.4) { // 40% chance of rats
                        double eatenPercentage = Math.random() * 0.2 + 0.1;
                        int grainEaten = (int) (bushels * eatenPercentage); // amount of grain eaten by rats
                        return grainEaten;
                    } else if
                    (grainBushels <= 0) {
                        return 0;
                    } else
                        return 0;
                }

                int newCostOfLand () {
                    int minPrice = 17;
                    int maxPrice = 23;
                    int range = maxPrice - minPrice + 1; // Adding 1 to include the upper bound
                    int randomValue = (int) (Math.random() * range) + minPrice;
                    return randomValue;
                }
            }


