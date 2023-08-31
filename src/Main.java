import java.util.*;
import java.io.*;

public class Main {
    static class PublicDeck{
        ArrayList<Integer> deck;
        PublicDeck(){
            deck = new ArrayList<Integer>();
            for(int i = 1; i <= 10; i++){
                for(int j = 0; j < 4; j++){
                    deck.add(i);
                }
            }
            Collections.shuffle(deck);
        }
        int draw_card(){
            return deck.remove(deck.size()-1); // removes and returns top item of public deck, which will be added to player deck
        }
    }
    static class Player{
        ArrayList<Integer> pdeck;
        boolean ready;
        Player(){
            pdeck = new ArrayList<Integer>();
            ready = false;
        }
        void add_card(int card){
            pdeck.add(card);
        }
        void display(){
            System.out.println(pdeck);
        }
        int sum_up(){
            int s = 0;
            for (int i = 0; i < pdeck.size(); i++) {
                s += pdeck.get(i);
            }
            return s;
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Random rng = new Random();
        PublicDeck bjdeck = new PublicDeck();
        Player user = new Player();
        Player computer = new Player();
        String action;

        for (int i = 0; i < 4; i++) {
            if(i%2 == 0){
                user.add_card(bjdeck.draw_card());
            }
            else{
                computer.add_card(bjdeck.draw_card());
            }
        }
        while(true){
            if(!user.ready) {
                user.display();
                System.out.println("What would you like to do?");
                action = s.nextLine();
                if (action.compareTo("Add") == 0) {
                    user.add_card(bjdeck.draw_card());
                } else if (action.compareTo("Done") == 0) {
                    user.ready = true;
                } else {
                    System.out.println("I don't recognize this command!");
                    continue; // succeeding code can only run if command is recognized
                }
            }
            System.out.println("The computer is thinking... ");
            // Moment of truth
            if(computer.ready && user.ready){
                user.display();
                computer.display();
                if(computer.sum_up() > 21 || user.sum_up() > 21){
                    // If both >21, then the person who goes over the least wins
                    // If one >21, then the person who doesn't go over wins
                    if(computer.sum_up() > user.sum_up()){
                        System.out.println("You won!");
                    }
                    else if(computer.sum_up() < user.sum_up()){
                        System.out.println("The computer won!");
                    }
                    else{
                        System.out.println("Its a draw!");
                    }
                }
                else{
                    if(computer.sum_up() > user.sum_up()){
                        System.out.println("The computer won!");
                    }
                    else if(computer.sum_up() < user.sum_up()){
                        System.out.println("You won!");
                    }
                    else{
                        System.out.println("Its a draw!");
                    }
                }
                break;
            }
            if(!computer.ready){ // if computer ready but player not it doesn't do anything. 
                if(computer.sum_up() >= 17 && computer.sum_up() < 20){
                    // it takes a gamble assuming that the sum is between 16-19
                    int mode = rng.nextInt(10);
                    if(mode%2 == 0){
                        computer.add_card(bjdeck.draw_card());
                    }
                    else{
                        computer.ready = true;
                    }

                } else if (computer.sum_up() < 17) {
                    computer.add_card(bjdeck.draw_card());
                }
                else{
                    computer.ready = true;
                }
            }
        }
    }
}