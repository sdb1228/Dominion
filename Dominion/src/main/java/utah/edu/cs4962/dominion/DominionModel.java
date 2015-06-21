package utah.edu.cs4962.dominion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by StevenBurnett on 12/2/13.
 */
public class DominionModel
{
    public static int copper;
    public static int silver;
    public static int gold;
    public static int province;
    public static int duchy;
    public static int estate;
    public static ArrayList<String> player1deck = null;
    public static ArrayList<String> player1discard = null;
    public static ArrayList<String> player2discard = null;
    public static ArrayList<String> player2deck = null;
    public static DominionModel model = null;
    public static HashMap<String, Integer> cardCost = null;
    public static HashMap<String, Integer> deck = null;
    public static boolean bureaucrat = false;

    private DominionModel()
    {
        player1discard = new ArrayList<String>();
        player2discard = new ArrayList<String>();
        copper = 32;
        silver = 38;
        gold = 30;
        province = 12;
        duchy=12;
        estate = 12;
        player1deck = new ArrayList<String>();
        player2deck = new ArrayList<String>();
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("estate");
        player1deck.add("estate");
        player1deck.add("estate");
        Collections.shuffle(player1deck);

        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("estate");
        player2deck.add("estate");
        player2deck.add("estate");
        Collections.shuffle(player2deck);

        deck = new HashMap<String, Integer>();

        cardCost = new HashMap<String, Integer>();
        cardCost.put("gold", 6);
        cardCost.put("silver", 3);
        cardCost.put("copper", 0);
        cardCost.put("province", 8);
        cardCost.put("duchy", 5);
        cardCost.put("estate", 2);
        cardCost.put("adventurer", 6);
        cardCost.put("bureaucrat", 4);
        cardCost.put("cellar", 2);
        cardCost.put("chancellor", 3);
        cardCost.put("chapel", 2);
        cardCost.put("councilroom", 5);
        cardCost.put("festival", 5);
        cardCost.put("garden", 4);
        cardCost.put("lab", 5);
        cardCost.put("library", 5);
        cardCost.put("market", 5);
        cardCost.put("militia", 4);
        cardCost.put("mine", 5);
        cardCost.put("moat", 2);
        cardCost.put("moneylender", 4);
        cardCost.put("remodel", 4);
        cardCost.put("smithy", 4);
        cardCost.put("spy", 4);
        cardCost.put("throneroom", 4);
        cardCost.put("village", 3);
        cardCost.put("witch", 5);
        cardCost.put("workshop", 3);
        cardCost.put("woodcutter", 3);
        cardCost.put("feast", 4);

    }

    public static DominionModel getInstance()
    {
        if(model == null)
        {
            model = new DominionModel();
        }
        return model;
    }
    public static void addDeckCard(String card)
    {
        if(card.equals("gold"))
        {
            deck.put(card, gold);
        }
        else if(card.equals("silver"))
        {
            deck.put(card,silver);
        }
        else if(card.equals("copper"))
        {
            deck.put(card,copper);
        }
        else if(card.equals("province"))
        {
            deck.put(card,province);
        }
        else if(card.equals("duchy"))
        {
            deck.put(card,duchy);
        }
        else if(card.equals("estate"))
        {
            deck.put(card,estate);
        }
        else
        {
            deck.put(card, 10);
        }

    }
    public String getPlayer1card()
    {
        if(player1deck.isEmpty() || player1deck == null)
        {
            Collections.shuffle(player1discard);
            player1deck.addAll(player1discard);
            player1discard.clear();

        }
        String temp = player1deck.get(0);
        player1deck.remove(0);
        return temp;

    }

    public int getPlayer1count()
    {
        player1deck.addAll(player1discard);
        if(DeckView.player.equals("Player ones' turn"))
        {
            player1deck.addAll(HandFragment.playerDeck);

        }
        return player1deck.size();
    }
    public int getPlayer2count()
    {
        player2deck.addAll(player2discard);
        if(DeckView.player.equals("Player twos' turn"))
        {
            player2deck.addAll(HandFragment.playerDeck);

        }
        return player2deck.size();
    }
    public String getPlayer2card()
    {
        if(player2deck.isEmpty() || player2deck == null)
        {
            Collections.shuffle(player2discard);
            player2deck.addAll(player2discard);
            player2discard.clear();

        }
        String temp = player2deck.get(0);
        player2deck.remove(0);
        return temp;

    }
    public void discard(String player, ArrayList<String> playerdeck)
    {
        if(player.equals("Player ones' turn"))
        {
            player1discard.addAll(playerdeck);
        }
        else
        {
            player2discard.addAll(playerdeck);
        }

    }
    public void discardSingle(String player, String card)
    {
        if(player.equals("Player ones' turn"))
        {
            player1discard.add(card);
        }
        else
        {
            player2discard.add(card);
        }

    }
    public int getCardCount(String card)
    {
        return deck.get(card);
    }
    public void decrementCard(String card)
    {
        int temp = deck.get(card);
        deck.remove(card);
        deck.put(card, temp-1);
    }
    public void addPlayerCard(String player, String card)
    {
        if(player.equals("Player ones' turn"))
        {
            player1discard.add(card);
        }
        else
        {
            player2discard.add(card);
        }
    }
    public int getCardCost(String card)
    {
        return cardCost.get(card);
    }
    public void setBureaucratPlayed()
    {
        bureaucrat = true;
    }
    public void setBureaucratNotPlayed()
    {
        bureaucrat = false;
    }
    public boolean getBureaucrat()
    {
        return bureaucrat;
    }
    public HashMap<String,Integer> getDeck()
    {
        return deck;
    }
    public int player1Score()
    {
        int garden = 0;
        int score = 0;
        for(int i = 0; i<player1deck.size(); i++)
        {
            if(player1deck.get(i).equals("estate"))
            {
                score++;
            }
            else if(player1deck.get(i).equals("duchy"))
            {
                score = score + 3;
            }
            else if(player1deck.get(i).equals("province"))
            {
                score = score + 6;
            }
            else if (player1deck.get(i).equals("garden"))
            {
                garden++;
            }
            else if(player1deck.get(i).equals("curse"))
            {
                score--;
            }
        }
        for(int i = 0; i <player1discard.size(); i++)
        {
            if(player1discard.get(i).equals("estate"))
            {
                score++;
            }
            else if(player1discard.get(i).equals("duchy"))
            {
                score = score + 3;
            }
            else if(player1discard.get(i).equals("province"))
            {
                score = score + 6;
            }
            else if (player1discard.get(i).equals("garden"))
            {
                garden++;
            }
            else if(player1discard.get(i).equals("curse"))
            {
                score--;
            }

        }
        if(DeckView.player.equals("Player ones' turn"))
        {
            ArrayList<String> temp = HandFragment.playerDeck;
            for(int t=0; t<temp.size(); t++)
            {
                if(temp.get(t).equals("estate"))
                {
                    score++;
                }
                else if(temp.get(t).equals("duchy"))
                {
                    score = score + 3;
                }
                else if(temp.get(t).equals("province"))
                {
                    score = score + 6;
                }
                else if (temp.get(t).equals("garden"))
                {
                    garden++;
                }
                else if(temp.get(t).equals("curse"))
                {
                    score--;
                }

            }
        }
        if(garden>0)
        {
            score = score +(this.getPlayer1count()/10) *garden;

        }
        return score;

    }
    public int player2Score()
    {
        int score = 0;
        int garden = 0;
        for(int i = 0; i<player2deck.size(); i++)
        {
            if(player2deck.get(i).equals("estate"))
            {
                score++;
            }
            else if(player2deck.get(i).equals("duchy"))
            {
                score = score + 3;
            }
            else if(player2deck.get(i).equals("province"))
            {
                score = score + 6;
            }
            else if(player2deck.get(i).equals("garden"))
            {
                garden++;
            }
            else if(player2deck.get(i).equals("curse"))
            {
                score--;
            }
        }
        for(int i = 0; i <player2discard.size(); i++)
        {
            if(player2discard.get(i).equals("estate"))
            {
                score++;
            }
            else if(player2discard.get(i).equals("duchy"))
            {
                score = score + 3;
            }
            else if(player2discard.get(i).equals("province"))
            {
                score = score + 6;
            }
            else if(player2discard.get(i).equals("garden"))
            {
                garden++;
            }
            else if(player2discard.get(i).equals("curse"))
            {
                score--;
            }

        }
        if(DeckView.player.equals("Player twos' turn"))
        {
            ArrayList<String> temp = HandFragment.playerDeck;
            for(int t=0; t<temp.size(); t++)
            {
                if(temp.get(t).equals("estate"))
                {
                    score++;
                }
                else if(temp.get(t).equals("duchy"))
                {
                    score = score + 3;
                }
                else if(temp.get(t).equals("province"))
                {
                    score = score + 6;
                }
                else if(temp.get(t).equals("garden"))
                {
                    garden++;
                }
                else if(temp.get(t).equals("curse"))
                {
                    score--;
                }

            }
        }
        if(garden>0)
        {
            score = score +(this.getPlayer2count()/10) *garden;

        }

        return score;
    }
    public void discardAll(String player)
    {
        if(player.equals("Player ones' turn"))
        {
            player1discard.addAll(player1deck);
            player1deck.clear();

        }
        else
        {
            player2discard.addAll(player2deck);
            player2deck.clear();
        }

    }
    public Boolean endGame()
    {
        if(deck.get("province") == 0)
        {
            return true;
        }
        int zeroPiles = 0;
        for(HashMap.Entry<String,Integer> entry: deck.entrySet())
        {
            if(entry.getValue() == 0)
            {
                zeroPiles++;
            }

        }
        if(zeroPiles == 3)
        {
            return true;
        }
        return false;
    }
    public void reset()
    {
        player1deck.clear();
        player2discard.clear();
        player2deck.clear();
        player1discard.clear();
        player1discard = new ArrayList<String>();
        player2discard = new ArrayList<String>();
        copper = 32;
        silver = 38;
        gold = 30;
        province = 12;
        duchy=12;
        estate = 12;
        player1deck = new ArrayList<String>();
        player2deck = new ArrayList<String>();
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("copper");
        player1deck.add("estate");
        player1deck.add("estate");
        player1deck.add("estate");
        Collections.shuffle(player1deck);

        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("copper");
        player2deck.add("estate");
        player2deck.add("estate");
        player2deck.add("estate");
        Collections.shuffle(player2deck);
        deck.clear();

    }

}
