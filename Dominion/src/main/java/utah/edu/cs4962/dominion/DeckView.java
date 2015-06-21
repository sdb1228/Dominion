package utah.edu.cs4962.dominion;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by StevenBurnett on 11/29/13.
 */
@TargetApi(16)
public class DeckView extends ViewGroup
{
    public static TextView playerInfo = null;
    public ArrayList<String> card = null;
    public static CardView copper = null;
    public static CardView silver = null;
    public static CardView gold = null;
    static Button endTurn = null;
    public static CardView province = null;
    public static CardView duchy = null;
    public static CardView estate = null;
    public static ArrayList<CardView> deck = null;
    public static String player = null;
    OnPlayerChangeListener onPlayerChangeListener = null;
    static TextView score = null;
    public static boolean bought = false;
    private EndGameListener endGameListener = null;

    public interface EndGameListener
    {
        public void endGame();

    }


    public DeckView(Context context)
    {
        super(context);
        card = new ArrayList<String>();
        score = new TextView(context);

        card.add("adventurer");
        card.add("bureaucrat");
        card.add("cellar");
        card.add("chancellor");
        card.add("chapel");
        card.add("councilroom");
        card.add("feast");
        card.add("festival");
        card.add("garden");
        card.add("lab");
        card.add("market");
        card.add("militia");
        card.add("mine");
        card.add("moat");
        card.add("moneylender");
        card.add("remodel");
        card.add("smithy");
        card.add("village");
        card.add("witch");
        card.add("workshop");
        card.add("woodcutter");
        deck = new ArrayList<CardView>();

        endTurn = new Button(context);

        endTurn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (DominionModel.getInstance().endGame())
                {
                    endTurn.setEnabled(false);
                    HandFragment.endGame = true;
                    score.setText("Player 1 score:" + DominionModel.getInstance().player1Score() + "\n" + "Player 2 score:" + DominionModel.getInstance().player2Score() + "\n" + "Click here to start a new game");
                    score.invalidate();

                }
                else
                {
                    InfoViewgroup.clearMoney();
                    InfoViewgroup.clearBuys();
                    InfoViewgroup.clearActions();
                    HandFragment.chapel = false;
                    DeckView.setScoreOriginal();
                    if (HandFragment.militiaPlayed)
                    {
                        score.setText("Militia has been played! discard 2 cards and click here when done.");
                        endTurn.setEnabled(false);
                        HandFragment.militia = true;
                    }
                    onPlayerChangeListener.onPlayerChangeListener();
                    DeckView.playerChange();
                }
            }
        });


        playerInfo = new TextView(context);
        playerInfo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        // addition of treasure coins.
        copper = new CardView(context, "copper");
        DominionModel.getInstance().addDeckCard("copper");
        silver = new CardView(context, "silver");
        DominionModel.getInstance().addDeckCard("silver");
        gold = new CardView(context, "gold");
        DominionModel.getInstance().addDeckCard("gold");
        // addition of victory points
        province = new CardView(context, "province");
        DominionModel.getInstance().addDeckCard("province");
        duchy = new CardView(context, "duchy");
        DominionModel.getInstance().addDeckCard("duchy");
        estate = new CardView(context, "estate");
        DominionModel.getInstance().addDeckCard("estate");

        final ArrayList<CardView> treasureVictory = new ArrayList<CardView>();
        treasureVictory.add(copper);
        treasureVictory.add(silver);
        treasureVictory.add(gold);
        treasureVictory.add(province);
        treasureVictory.add(duchy);
        treasureVictory.add(estate);
        for (int j = 0; j < treasureVictory.size(); j++)
        {
            final CardView trevic = treasureVictory.get(j);
            treasureVictory.get(j).setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    if (DominionModel.getInstance().getCardCount(trevic.getCardName()) == 0)
                    {
                        return true;

                    }
                    else if (HandFragment.workshop && DominionModel.getInstance().getCardCost(trevic.getCardName()) <= 4 && !bought)
                    {
                        DominionModel.getInstance().decrementCard(trevic.getCardName());
                        DominionModel.getInstance().addPlayerCard(player, trevic.cardName);
                        InfoViewgroup.addCardDecrement(trevic.getCardName());
                        bought = true;

                    }
                    else if (HandFragment.feast && DominionModel.getInstance().getCardCost(trevic.getCardName()) <= 5 && !bought)
                    {
                        DominionModel.getInstance().decrementCard(trevic.getCardName());
                        DominionModel.getInstance().addPlayerCard(player, trevic.cardName);
                        InfoViewgroup.addCardDecrement(trevic.getCardName());
                        bought = true;

                    }
                    else if (InfoViewgroup.getBuys() > 0)
                    {
                        if (DominionModel.getInstance().getCardCost(trevic.getCardName()) <= InfoViewgroup.getMoneies())
                        {
                            DominionModel.getInstance().decrementCard(trevic.getCardName());
                            DominionModel.getInstance().addPlayerCard(player, trevic.cardName);
                            InfoViewgroup.addCardDecrement(trevic.getCardName());
                            InfoViewgroup.decrementBuy();
                            InfoViewgroup.setMoneies(InfoViewgroup.getMoneies() - DominionModel.getInstance().getCardCost(trevic.cardName));

                        }

                    }
                    return true;
                }
            });
        }


        Random random = new Random();
        for (int i = 0; i < 10; i++)
        {
            int number = random.nextInt(card.size() - 1);
            final CardView temp = new CardView(context, card.get(number));
            temp.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    if (DominionModel.getInstance().getCardCount(temp.getCardName()) == 0)
                    {
                        return true;

                    }

                    else if (HandFragment.workshop && DominionModel.getInstance().getCardCost(temp.getCardName()) <= 4 && !bought)
                    {
                        DominionModel.getInstance().decrementCard(temp.getCardName());
                        DominionModel.getInstance().addPlayerCard(player, temp.cardName);
                        InfoViewgroup.addCardDecrement(temp.getCardName());
                        bought = true;

                    }
                    else if (HandFragment.feast && DominionModel.getInstance().getCardCost(temp.getCardName()) <= 5 && !bought)
                    {
                        DominionModel.getInstance().decrementCard(temp.getCardName());
                        DominionModel.getInstance().addPlayerCard(player, temp.cardName);
                        InfoViewgroup.addCardDecrement(temp.getCardName());
                        bought = true;

                    }
                    else if (InfoViewgroup.getBuys() > 0)
                    {
                        if (DominionModel.getInstance().getCardCost(temp.getCardName()) <= InfoViewgroup.getMoneies())
                        {
                            DominionModel.getInstance().decrementCard(temp.getCardName());
                            DominionModel.getInstance().addPlayerCard(player, temp.cardName);
                            InfoViewgroup.addCardDecrement(temp.getCardName());
                            InfoViewgroup.decrementBuy();
                            InfoViewgroup.setMoneies(InfoViewgroup.getMoneies() - DominionModel.getInstance().getCardCost(temp.cardName));

                        }

                    }

                    return true;
                }
            });


            DominionModel.getInstance().addDeckCard(card.get(number));
            card.remove(number);
            this.addView(temp);
            deck.add(temp);
        }
        score.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (HandFragment.chapel)
                {
                    HandFragment.chapel = false;
                    score.setText("Thanks!");
                }
                else if(HandFragment.remodel)
                {
                    HandFragment.remodel = false;
                    score.setText("Thanks!");
                    endTurn.setEnabled(true);
                }
                else if (HandFragment.workshop)
                {
                    HandFragment.workshop = false;
                    score.setText("Thanks!");
                    endTurn.setEnabled(true);
                }
                else if (HandFragment.feast)
                {
                    HandFragment.feast = false;
                    score.setText("Thanks!");
                    endTurn.setEnabled(true);
                }
                else if (HandFragment.militia)
                {
                    HandFragment.militia = false;
                    HandFragment.militiaPlayed=false;
                    HandFragment.discardCount = 0;
                    score.setText("Thanks!");
                    endTurn.setEnabled(true);
                }
                else if (HandFragment.cellar)
                {
                    for (int i = 0; i < HandFragment.discardCount; i++)
                    {
                        if (player.equals("Player ones' turn"))
                        {
                            HandFragment.addCard(DominionModel.getInstance().getPlayer1card());
                        }
                        else
                        {
                            HandFragment.addCard(DominionModel.getInstance().getPlayer2card());
                        }
                    }
                    HandFragment.cellar = false;
                    score.setText("Thanks!");
                    endTurn.setEnabled(true);
                }
                else if (HandFragment.endGame)
                {
                    InfoViewgroup.clearMoney();
                    InfoViewgroup.clearBuys();
                    InfoViewgroup.clearActions();
                    HandFragment.chapel = false;
                    HandFragment.militia = false;
                    HandFragment.playerDeck.clear();
                    HandFragment.cellar = false;
                    HandFragment.workshop = false;
                    HandFragment.feast = false;
                    DeckView.setScoreOriginal();
                    DominionModel.getInstance().reset();
                    HandFragment.endGame = false;
                    endTurn.setEnabled(true);
                    DeckView.playerChange();
                    endGameListener.endGame();

                }

                return true;

            }

        });
        //adds treasures
        this.addView(gold);
        this.addView(copper);
        this.addView(silver);
        //adds victory
        this.addView(province);
        this.addView(duchy);
        this.addView(estate);
        this.addView(playerInfo);

        player = "Player ones' turn";
        score.setText("Keep going! Scores will be here after the game!");

        this.addView(endTurn);
        this.addView(score);
    }

    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4)
    {
        int cardHeight = (this.getHeight() / 5);
        endTurn.setText("End Turn");
        endTurn.layout(0, this.getHeight() - 100, 200, this.getHeight());
        score.layout((this.getWidth() / 3)+30,(this.getHeight() / 5 + 310) + cardHeight , this.getWidth(), this.getHeight());
        int cardWidth = this.getWidth() / 7;
        //treasure layout
        gold.layout(this.getWidth() - cardWidth, 100, this.getWidth(), this.getHeight() / 5 + 100);
        silver.layout(this.getWidth() - cardWidth, this.getHeight() / 5 + 110, this.getWidth(), (this.getHeight() / 5 + 100) + cardHeight);
        copper.layout(this.getWidth() - cardWidth, (this.getHeight() / 5 + 110) + cardHeight, this.getWidth(), ((this.getHeight() / 5 + 110) + cardHeight) + cardHeight);
        //victory layout
        province.layout(0, 100, cardWidth, this.getHeight() / 5 + 100);
        duchy.layout(0, this.getHeight() / 5 + 110, cardWidth, (this.getHeight() / 5 + 100) + cardHeight);
        estate.layout(0, (this.getHeight() / 5 + 110) + cardHeight, cardWidth, ((this.getHeight() / 5 + 110) + cardHeight) + cardHeight);
        int cardsIN = 2;

        // Addition of action cards
        for (int k = 0; k < deck.size() / 2; k++)
        {

            deck.get(k).layout(((k + 1) * (cardWidth)) + 2, 100, cardsIN * (cardWidth), this.getHeight() / 5 + 100);
            cardsIN++;
        }
        cardsIN = 2;
        int temp = 1;
        for (int k = 5; k < 10; k++)
        {

            deck.get(k).layout(((temp) * (cardWidth)) + 2, (this.getHeight() / 5 + 110) + cardHeight, cardsIN * (cardWidth), ((this.getHeight() / 5 + 110) + cardHeight) + cardHeight);
            temp++;
            cardsIN++;
        }


        playerInfo.layout(this.getWidth() / 4 + getWidth() / 17, 0, this.getWidth(), this.getHeight());
        this.setBackgroundColor(Color.rgb(0, 204, 102));

    }

    @Override
    public void onDraw(Canvas canvas)
    {
        playerInfo.setText(player);
    }

    public static void playerChange()
    {
        if (player.equals("Player ones' turn"))
        {
            player = "Player twos' turn";
        }
        else
        {
            player = "Player ones' turn";
        }
        playerInfo.setText(player);
    }

    public static String getPlayer()
    {
        return player;
    }

    public interface OnPlayerChangeListener
    {
        void onPlayerChangeListener();
    }

    public void setOnPlayerChangeListener(OnPlayerChangeListener listener)
    {
        onPlayerChangeListener = listener;
    }

    public static void setScore()
    {
        score.setText("Touch Here to end trashing/discarding!");
        score.invalidate();
    }

    public static void setScoreOriginal()
    {
        score.setText("Keep going! Scores will be here after the game!");
        score.invalidate();
    }

    public static void setScoreWorkshop()
    {
        score.setText("Select a card costing up to 4. Click here when you are done.");
        score.invalidate();

    }

    public static void setScoreFeast()
    {
        score.setText("Select a card costing up to 5.  Click here when you are done");
        score.invalidate();
    }

    public static void setScoreRemodel()
    {
        score.setText("Select a card to trash.  Click here when you are done");
        score.invalidate();
    }

    public void setOnEndGameListener(EndGameListener listener)
    {
        endGameListener = listener;
    }
}
