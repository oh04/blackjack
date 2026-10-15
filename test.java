import java.util.Scanner;

public class test {

    public static double money;
    public static double bet;
    public static int dealerTotal;
    public static int playerTotal;
    public static int turn;
    public static int dealAce;
    public static int playerAce;
    public static int end;
    public static void main (String[] args){
        Scanner input = new Scanner(System.in);
        money = 500;
        int gameNum = 1;
        turn = 1;

        System.out.println("Welcome to Blackjack");
        while(money > 0){
            turn = 2;
            int aceChoice;
            int temp;
            end = 0;
            playerAce = 0;
            dealAce = 0;
            dealerTotal = 0;
            playerTotal = 0;
            System.out.println("Your funds: " + money);
            System.out.println("\n----------Game " + gameNum + "----------\n");
            gameNum++;
            System.out.print("How much would you like to bet? ");
            bet = input.nextDouble();
            while ((money - bet) < 0. || bet < 0){
                if ((money - bet) < 0) System.out.println("You dont have enough funds. Try again.");
                else if (bet < 0) System.out.println("Bet can't be negative.");
                System.out.print("How much would you like to bet? ");
                bet = input.nextDouble();

            }
            System.out.print("\nDealer's cards: \t");
            dealerTotal += dealVal(pullCard());
            System.out.println("\tX");
            turn = 1;
            System.out.print("Your cards: \t\t");
            playerTotal += dealVal(pullCard());
            System.out.print("\t");
            playerTotal += dealVal(pullCard());
            temp = playerAce;
            while(playerAce > 0){
                System.out.println("\nWhat would you like the value of your ace " + playerAce + " to be?");
                aceChoice = input.nextInt();
                if (aceChoice == 11)
                    playerTotal += 10;
                playerAce--;
            }
            playerAce = temp;
            System.out.println("\nYour total: " + playerTotal);
            String hit;
            int dd = 0;
            int aceVal = 0;
            while (playerTotal < 21 && dd == 0){
                System.out.print("\nWould you like to Hit (H), Stand (S), or Double Down (D) ");
                hit = input.next();
                if (hit.toLowerCase().equals("h") || (hit.toLowerCase().equals("d"))){
                    if (hit.toLowerCase().equals("d") && (money - bet) < 0)
                        System.out.println("You don't have enough to double down, you will hit.");
                    else if(hit.toLowerCase().equals("d")){
                        bet *= 2;
                        hit();
                        break;
                    }
                    hit();
                    playerTotal -= aceVal;
                    aceVal = 0;
                    while(playerAce > 0){
                        System.out.print("What would you like the value of your ace " + playerAce + " to be? ");
                        aceChoice = input.nextInt();
                        if (aceChoice == 11){
                            playerTotal += 10;
                            aceVal += 11;
                        } else
                            aceVal += 1;
                        playerAce--;
                    }
                    playerAce = temp;
                } else {
                    break;
                }
            }
            turn = 2;
            int dAceVal = 0;
            if (end == 0){
                System.out.print("The Dealer's hidden card was a(n) ");
                dealerTotal += dealVal(pullCard());
                if (dealerTotal < 12 && dealAce == 1){
                    dealerTotal += 10;
                    dAceVal += 11;
                } else if (dealAce == 1){
                    dAceVal += 1;
                }
                System.out.println(".\nThe Dealer's total is " + dealerTotal + ".");
                while (dealerTotal < 17){
                    System.out.println("The dealer hits.");
                    System.out.print("The dealer pulled a(n) ");
                    dealerTotal += dealVal(pullCard());
                    temp = dealAce;
                    dealerTotal -= dAceVal;
                    for(int j = 0; j < dealAce; j++){
                        if (dealerTotal < 12){
                            dealerTotal += 11;
                            dAceVal += 11;
                            System.out.println("The dealer chose ace " + (dealAce + 1) + "to be 11");
                        } else {
                            System.out.println("The dealer chose ace " + (dealAce + 1) + "to be 1");
                            dealerTotal += 1;
                            dAceVal += 1;
                        }
                        dealAce--;
                    }
                    dealAce = temp;
                    System.out.println(".\nThe Dealer's total is " + dealerTotal + ".");
                }
            }
            turn = 3;
            check();

        }
        System.out.println("Game Over." + money);
        input.close();

    }

    public static int pullCard(){
        return (int)(Math.random() * 13) + 1;
    }

    public static int dealVal(int x){
        switch(x){
            case 1:
                System.out.print("Ace");
                if(turn == 1) playerAce++;
                else
                    dealAce++;
                break;
            case 11:
                System.out.print("Jack");
                x = 10;
                break;
            case 12:
                System.out.print("Queen");
                x = 10;
                break;
            case 13:
                System.out.print("King");
                x = 10;
                break;
            default:
                System.out.print(x);
        }
        return x;
    }

    public static void hit(){
        System.out.print("Your new card: ");
        playerTotal += dealVal(pullCard());
        System.out.println("\nYour new total: " + playerTotal);
        check();
    }

    public static void check(){
        if (playerTotal > 21){
            System.out.println("You busted.");
            end = 1;
            money -= bet;
        } else if (playerTotal == 21){
            System.out.println("You win.");
            money += bet;
        }else if (dealerTotal > 21){
            System.out.println("The dealer busted, You win.");
            money += bet;
        } else if (playerTotal == dealerTotal && turn == 3){
            System.out.println("Draw. Dealer wins.");
            money -= bet;
        } else if (playerTotal > dealerTotal && turn == 3){
            System.out.println("You Win.");
            money += bet;
        } else if (turn == 3){
            System.out.println("You Lose.");
            money -= bet;
        }
    }

}