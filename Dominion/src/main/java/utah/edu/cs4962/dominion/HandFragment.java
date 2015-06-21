package utah.edu.cs4962.dominion;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by StevenBurnett on 12/1/13.
 */
@TargetApi(17)
public class HandFragment extends ListFragment implements ListAdapter
{

    static ArrayList<String> playerDeck = new ArrayList<String>();
    HashMap<Integer, Float> cardPlayed = null;
    public static boolean councilroom = false;
    public static boolean chapel = false;
    public static boolean workshop = false;
    public static boolean feast = false;
    public static boolean militia = false;
    public static int discardCount = 0;
    public static boolean remodel = false;
    public static boolean cellar = false;
    public static boolean militiaPlayed = false;
    public static boolean endGame = false;
    public static boolean chosen = false;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setListAdapter(this);
        cardPlayed = new HashMap<Integer, Float>();
        playerDeck.add(DominionModel.getInstance().getPlayer1card());
        playerDeck.add(DominionModel.getInstance().getPlayer1card());
        playerDeck.add(DominionModel.getInstance().getPlayer1card());
        playerDeck.add(DominionModel.getInstance().getPlayer1card());
        playerDeck.add(DominionModel.getInstance().getPlayer1card());

    }

    @Override
    @TargetApi(17)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return false;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public int getCount()
    {
        return playerDeck.size();
    }

    @Override
    public Object getItem(int position)
    {
        return playerDeck.get(0);
    }


    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // use param
        final LinearLayout cardRowView = new LinearLayout(getActivity());
        cardRowView.setOrientation(LinearLayout.VERTICAL);


        final CardView card = new CardView(getActivity(), playerDeck.get(position));
        if (cardPlayed.get(position) == null)
        {
            cardPlayed.put(position, 1f);
        }
        float alphaValue = cardPlayed.get(position);
        if (alphaValue == .3f)
        {
            cardRowView.setAlpha(.3f);
        }
        cardRowView.addView(card, new ViewGroup.LayoutParams(180, 200));

        cardRowView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (endGame)
                {
                    return true;
                }

                if (cardRowView.getAlpha() != .3f)
                {
                    if (chapel)
                    {
                        playerDeck.remove(card.getCardName());
                        cardPlayed.remove(position);
                        getListView().invalidateViews();
                    }
                    if (cellar)
                    {
                        DominionModel.getInstance().discardSingle(DeckView.player, card.getCardName());
                        playerDeck.remove(card.getCardName());
                        cardPlayed.remove(position);
                        discardCount++;
                        getListView().invalidateViews();

                    }
                    else if (remodel && !chosen)
                    {
                        playerDeck.remove(card.getCardName());
                        cardPlayed.remove(position);
                        Integer cost = DominionModel.getInstance().getCardCost(card.getCardName())+2;
                        InfoViewgroup.addMoney(cost + "");
                        getListView().invalidate();
                        chosen = true;
                    }
                    else if (militia && discardCount != 2)
                    {
                        if (playerDeck.contains("moat"))
                        {
                            militia = false;
                            discardCount = 0;
                            DeckView.score.setText("You have a moat! Way to be safe");
                            DeckView.endTurn.setEnabled(true);
                            DeckView.score.invalidate();
                        }
                        else
                        {
                            discardCount++;
                            DominionModel.getInstance().discardSingle(DeckView.player, card.getCardName());
                            playerDeck.remove(card.getCardName());
                            cardPlayed.remove(position);
                            getListView().invalidateViews();
                        }

                    }
                    else if (card.getCardName().equals("copper") || card.getCardName().equals("silver") || card.getCardName().equals("gold"))
                    {
                        if (militia && discardCount != 2)
                        {
                            DominionModel.getInstance().discardSingle("Player ones' turn", card.getCardName());
                            playerDeck.remove(card.getCardName());
                            cardPlayed.remove(position);
                            discardCount++;
                            getListView().invalidateViews();
                        }
                        else
                        {
                            InfoViewgroup.addMoney(card.getCardName());
                            cardRowView.setAlpha(.3f);
                            cardPlayed.put(position, .3f);
                        }
                    }
                    else if (InfoViewgroup.getActionses() > 0 && !card.getCardName().equals("estate") && !card.getCardName().equals("duchy") && !card.getCardName().equals("province") && !card.getCardName().equals("garden"))
                    {
                        InfoViewgroup.decrementActions();
                        String temp = DeckView.getPlayer();
                        if (temp.equals("Player ones' turn"))
                        {

                            if (card.getCardName().equals("village"))
                            {
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    InfoViewgroup.addActions();
                                    InfoViewgroup.addActions();
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("adventurer"))
                            {
                                    int advenCount = 0;
                                    while (advenCount < 2)
                                    {
                                        String cardName = DominionModel.getInstance().getPlayer1card();
                                        if (cardName.equals("copper") || cardName.equals("silver") || cardName.equals("gold"))
                                        {
                                            advenCount++;
                                            playerDeck.add(cardName);
                                        }
                                        else
                                        {
                                            DominionModel.getInstance().discardSingle("Player ones' turn", cardName);
                                        }

                                    }
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("bureaucrat"))
                            {
                                    DominionModel.getInstance().discardSingle("Player ones' turn", "silver");
                                    DominionModel.getInstance().setBureaucratPlayed();
                                    DominionModel.getInstance().decrementCard("silver");
                                    InfoViewgroup.addCardDecrement("silver");
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("cellar"))
                            {
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                                    InfoViewgroup.addActions();
                                    cellar = true;
                                    DeckView.score.setText("Click here when you are done discarding.");
                                    DeckView.score.invalidate();
                            }
                            else if (card.getCardName().equals("chancellor"))
                            {
                                    InfoViewgroup.addMoney("silver");
                                    DominionModel.getInstance().discardAll("Player ones' turn");
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("chapel"))
                            {
                                    chapel = true;
                                    DeckView.setScore();
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("councilroom"))
                            {
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    InfoViewgroup.addBuys();
                                    councilroom = true;
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("festival"))
                            {
                                    InfoViewgroup.addActions();
                                    InfoViewgroup.addActions();
                                    InfoViewgroup.addMoney("silver");
                                    InfoViewgroup.addBuys();
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("lab"))
                            {
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    InfoViewgroup.addActions();
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("market"))
                            {
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    InfoViewgroup.addActions();
                                    InfoViewgroup.addMoney("copper");
                                    InfoViewgroup.addBuys();
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("militia"))
                            {
                                    militiaPlayed = true;
                                    InfoViewgroup.addMoney("silver");
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("mine"))
                            {
                                    int minelocp1 = 0;
                                    for (int i = 0; i < playerDeck.size(); i++)
                                    {
                                        if (playerDeck.get(i).equals("copper"))
                                        {
                                            playerDeck.add("silver");
                                            playerDeck.remove(i);
                                            cardPlayed.remove(i);
                                            cardPlayed.put(playerDeck.size() - 1, 1f);
                                            getListView().invalidateViews();
                                            minelocp1 = i;
                                            break;
                                        }
                                        else if (playerDeck.get(i).equals("silver"))
                                        {
                                            playerDeck.remove(i);
                                            cardPlayed.remove(i);
                                            playerDeck.add("gold");
                                            cardPlayed.put(playerDeck.size() - 1, 1f);
                                            getListView().invalidateViews();
                                            minelocp1 = i;
                                            break;
                                        }
                                        else
                                        {
                                            continue;
                                        }
                                    }
                                    if (minelocp1 < position)
                                    {
                                        cardRowView.setAlpha(.3f);
                                        cardPlayed.put(position - 1, .3f);
                                    }
                            }
                            else if (card.getCardName().equals("moat"))
                            {
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("moneylender"))
                            {
                                    int moneylen = 0;
                                    for (int i = 0; i < playerDeck.size(); i++)
                                    {
                                        if (playerDeck.get(i).equals("copper"))
                                        {
                                            InfoViewgroup.addMoney("gold");
                                            playerDeck.remove(i);
                                            cardPlayed.remove(i);
                                            getListView().invalidateViews();
                                            moneylen = i;
                                            break;
                                        }
                                    }
                                    if (moneylen < position)
                                    {
                                        cardRowView.setAlpha(.3f);
                                        cardPlayed.put(position - 1, .3f);
                                    }
                                }
                            else if (card.getCardName().equals("remodel"))
                            {
                                    remodel = true;
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position, .3f);
                                    DeckView.setScoreRemodel();
                            }
                            else if (card.getCardName().equals("smithy"))
                            {
                                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("witch"))
                            {
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);
                                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                DominionModel.getInstance().addPlayerCard("Player ones' turn", "curse");
                            }
                            else if (card.getCardName().equals("workshop"))
                            {
                                DeckView.setScoreWorkshop();
                                workshop = true;
                                DeckView.endTurn.setEnabled(false);
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("woodcutter"))
                            {
                                InfoViewgroup.addBuys();
                                InfoViewgroup.addMoney("silver");
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("feast"))
                            {
                                DeckView.setScoreFeast();
                                feast = true;
                                DeckView.endTurn.setEnabled(false);
                                playerDeck.remove(position);
                                cardPlayed.remove(position);
                                getListView().invalidateViews();
                            }
                        }
                        else
                        {
                            if (card.getCardName().equals("village"))
                            {
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                InfoViewgroup.addActions();
                                InfoViewgroup.addActions();
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("adventurer"))
                            {
                                int advenCount = 0;
                                while (advenCount < 2)
                                {
                                    String cardName = DominionModel.getInstance().getPlayer2card();
                                    if (cardName.equals("copper") || cardName.equals("silver") || cardName.equals("gold"))
                                    {
                                        advenCount++;
                                        playerDeck.add(cardName);
                                    }
                                    else
                                    {
                                        DominionModel.getInstance().discardSingle("Player twos' turn", cardName);
                                    }

                                }
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("bureaucrat"))
                            {
                                DominionModel.getInstance().discardSingle("Player twos' turn", "silver");
                                DominionModel.getInstance().setBureaucratPlayed();
                                DominionModel.getInstance().decrementCard("silver");
                                InfoViewgroup.addCardDecrement("silver");
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("cellar"))
                            {
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);
                                InfoViewgroup.addActions();
                                cellar = true;
                                DeckView.score.setText("Click here when you are done discarding.");
                                DeckView.score.invalidate();

                            }
                            else if (card.getCardName().equals("chancellor"))
                            {
                                InfoViewgroup.addMoney("silver");
                                DominionModel.getInstance().discardAll("Player twos' turn");
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("chapel"))
                            {
                                chapel = true;
                                DeckView.setScore();
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("councilroom"))
                            {
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                InfoViewgroup.addBuys();
                                councilroom = true;
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("festival"))
                            {
                                InfoViewgroup.addActions();
                                InfoViewgroup.addActions();
                                InfoViewgroup.addMoney("silver");
                                InfoViewgroup.addBuys();
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);
                            }
                            else if (card.getCardName().equals("lab"))
                            {
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                InfoViewgroup.addActions();
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("market"))
                            {
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                InfoViewgroup.addActions();
                                InfoViewgroup.addMoney("copper");
                                InfoViewgroup.addBuys();
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("militia"))
                            {
                                militia = true;
                                InfoViewgroup.addMoney("silver");
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);


                            }
                            else if (card.getCardName().equals("mine"))
                            {
                                int mineloc = 0;
                                for (int i = 0; i < playerDeck.size(); i++)
                                {
                                    if (playerDeck.get(i).equals("copper"))
                                    {
                                        playerDeck.add("silver");
                                        playerDeck.remove(i);
                                        cardPlayed.remove(i);
                                        cardPlayed.put(playerDeck.size() - 1, 1f);
                                        getListView().invalidateViews();
                                        mineloc = i;
                                        break;
                                    }
                                    else if (playerDeck.get(i).equals("silver"))
                                    {
                                        playerDeck.remove(i);
                                        cardPlayed.remove(i);
                                        playerDeck.add("gold");
                                        cardPlayed.put(playerDeck.size() - 1, 1f);
                                        getListView().invalidateViews();
                                        mineloc = i;
                                        break;
                                    }
                                    else
                                    {
                                        continue;
                                    }
                                }
                                if (mineloc < position)
                                {
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position - 1, .3f);
                                }

                            }
                            else if (card.getCardName().equals("moat"))
                            {
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("moneylender"))
                            {
                                int moneylen = 0;
                                for (int i = 0; i < playerDeck.size(); i++)
                                {
                                    if (playerDeck.get(i).equals("copper"))
                                    {
                                        InfoViewgroup.addMoney("gold");
                                        playerDeck.remove(i);
                                        cardPlayed.remove(i);
                                        getListView().invalidateViews();
                                        moneylen = i;
                                        break;
                                    }
                                }
                                if (moneylen < position)
                                {
                                    cardRowView.setAlpha(.3f);
                                    cardPlayed.put(position - 1, .3f);
                                }
                            }
                            else if (card.getCardName().equals("remodel"))
                            {
                                remodel = true;
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);
                                DeckView.setScoreRemodel();
                            }
                            else if (card.getCardName().equals("smithy"))
                            {
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                playerDeck.add(DominionModel.getInstance().getPlayer2card());
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("witch"))
                            {
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);
                                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                                DominionModel.getInstance().addPlayerCard("Player twos' turn", "curse");
                            }
                            else if (card.getCardName().equals("workshop"))
                            {
                                DeckView.setScoreWorkshop();
                                workshop = true;
                                DeckView.endTurn.setEnabled(false);
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);


                            }
                            else if (card.getCardName().equals("woodcutter"))
                            {
                                InfoViewgroup.addBuys();
                                InfoViewgroup.addMoney("silver");
                                cardRowView.setAlpha(.3f);
                                cardPlayed.put(position, .3f);

                            }
                            else if (card.getCardName().equals("feast"))
                            {
                                DeckView.setScoreFeast();
                                feast = true;
                                DeckView.endTurn.setEnabled(false);
                                playerDeck.remove(position);
                                cardPlayed.remove(position);
                                getListView().invalidateViews();

                            }
                        }

                    }

                }


                return true;
            }
        });

        return cardRowView;
    }

    @Override
    public int getItemViewType(int position)
    {
        return 0;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public boolean isEmpty()
    {
        return playerDeck.size() == 0;
    }

    public static void addCard(String card)
    {
        playerDeck.add(card);

    }

    public void newPlayer()
    {
        playerDeck = new ArrayList<String>();
        cardPlayed = new HashMap<Integer, Float>();
        int handcount = 0;
        if (DeckView.getPlayer().equals("Player twos' turn"))
        {
            if (DominionModel.getInstance().getBureaucrat())
            {
                Boolean returnedProvince = false;
                while (handcount < 5)
                {
                    String temp = DominionModel.getInstance().getPlayer2card();
                    if (temp.equals("estate") || temp.equals("duchy") || temp.equals("province") && !returnedProvince)
                    {
                        handcount++;
                        returnedProvince = true;

                    }
                    else
                    {
                        playerDeck.add(temp);
                        handcount++;
                    }
                }
            }
            else
            {
                if (councilroom)
                {
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    councilroom = false;

                }
                else
                {
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                    playerDeck.add(DominionModel.getInstance().getPlayer2card());
                }
            }
        }
        else
        {
            if (DominionModel.getInstance().getBureaucrat())
            {
                Boolean returnedProvince = false;
                while (handcount < 5)
                {
                    String temp = DominionModel.getInstance().getPlayer1card();
                    if (temp.equals("estate") || temp.equals("duchy") || temp.equals("province") && !returnedProvince)
                    {
                        handcount++;
                        returnedProvince = true;
                    }
                    else
                    {
                        playerDeck.add(temp);
                        handcount++;
                    }


                }
            }
            else
            {
                if (councilroom)
                {
                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                    playerDeck.add(DominionModel.getInstance().getPlayer1card());
                    councilroom = false;

                }
                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                playerDeck.add(DominionModel.getInstance().getPlayer1card());
                playerDeck.add(DominionModel.getInstance().getPlayer1card());
            }
        }
        DominionModel.getInstance().setBureaucratNotPlayed();

    }

    public ArrayList<String> getPlayerDeck()
    {
        return playerDeck;
    }
}
