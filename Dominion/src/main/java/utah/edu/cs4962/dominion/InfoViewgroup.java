package utah.edu.cs4962.dominion;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by StevenBurnett on 12/7/13.
 */
public class InfoViewgroup extends ViewGroup
{
    static TextView buys = null;
    static TextView money = null;
    static TextView actions = null;
    static TextView estate = null;
    static TextView duchy = null;
    static TextView province = null;
    static TextView copper = null;
    static TextView silver = null;
    static TextView gold = null;
    public ArrayList<TextView> views  = null;
    public ArrayList<TextView> actionsCard = null;
    public static HashMap<String, TextView> cardCount = null;
    static int buying = 1;
    static int moneies = 0;
    static int actionses = 1;


    public InfoViewgroup(Context context)
    {
        super(context);
        cardCount = new HashMap<String, TextView>();
        views = new ArrayList<TextView>();
        actionsCard = new ArrayList<TextView>();
        buys = new TextView(context);
        buys.setText("Buys\n" + buying);
        money = new TextView(context);
        money.setText("Money\n" + moneies);
        actions = new TextView(context);
        actions.setText("Actions\n" + actionses);
        estate = new TextView(context);
        estate.setText("Estate\n" + DominionModel.getInstance().getCardCount("estate"));
        duchy = new TextView(context);
        duchy.setText("Duchy\n" + DominionModel.getInstance().getCardCount("duchy"));
        province = new TextView(context);
        province.setText("Prov.\n" + DominionModel.getInstance().getCardCount("province"));
        copper = new TextView(context);
        copper.setText("Copper\n" + DominionModel.getInstance().getCardCount("copper"));
        silver = new TextView(context);
        silver.setText("Silver\n" + DominionModel.getInstance().getCardCount("silver"));
        gold = new TextView(context);
        gold.setText("Gold\n" + DominionModel.getInstance().getCardCount("gold"));
        HashMap<String, Integer> deck = DominionModel.getInstance().getDeck();
        for(HashMap.Entry<String,Integer> entry: deck.entrySet())
        {
            if(!(entry.getKey().equals("estate")) && !(entry.getKey().equals("duchy")) && !(entry.getKey().equals("province")) && !(entry.getKey().equals("copper")) && !(entry.getKey().equals("silver")) && !(entry.getKey().equals("gold")))
            {
                TextView temp = new TextView(context);
                temp.setTextSize(9.0f);
                temp.setText(entry.getKey() + "\n" + entry.getValue().toString());
                cardCount.put(entry.getKey(), temp);
                this.addView(temp);
                actionsCard.add(temp);

            }
        }

        this.addView(buys);
        this.addView(money);
        this.addView(actions);
        this.addView(estate);
        this.addView(duchy);
        this.addView(province);
        this.addView(gold);
        this.addView(silver);
        this.addView(copper);

        views.add(buys);
        views.add(money);
        views.add(actions);
        views.add(estate);
        views.add(duchy);
        views.add(province);
        views.add(gold);
        views.add(silver);
        views.add(copper);
    }
    @Override
    @TargetApi(17)
    protected void onLayout(boolean b, int i, int i2, int i3, int i4)
    {
        int textSize = this.getWidth()/9;
        int textSize2 = this.getWidth()/10;
        for(int j = 0; j <views.size(); j++)
        {
            views.get(j).setTextSize(12.0f);
        }
        for (int k = 0; k<views.size(); k++)
        {
            views.get(k).layout(textSize*k, 0 , textSize*(k+1), this.getHeight()/2);

        }
        for (int l = 0; l<actionsCard.size(); l++)
        {
            actionsCard.get(l).layout(textSize2*l, this.getHeight()/2, textSize2*(l+1),this.getHeight());
        }


    }
    public static void addMoney(String card)
    {
        if(card.equals("copper"))
        {
             moneies++;
        }
        else if (card.equals("silver"))
        {
            moneies = moneies + 2;
        }
        else if(card.equals("gold"))
        {
            moneies = moneies + 3;
        }
        else
        {
            int temp = Integer.parseInt(card);
            moneies = moneies + temp;
        }
        money.setText("Money\n" + moneies);
    }
    public static void clearMoney()
    {
        moneies = 0;
        money.setText("Money\n" + moneies);
    }
    public static void clearBuys()
    {
        buying = 1;
        buys.setText("Buys\n" + buying);
    }
    public static void clearActions()
    {
        actionses = 1;
        actions.setText("Actions\n" + actionses);
    }

    public static void addActions()
    {
        actionses++;
        actions.setText("Actions\n" + actionses);
    }
    public static void addBuys()
    {
        buying++;
        buys.setText("Buys\n" + buying);
    }
    public static void addCardDecrement(String card)
    {
        if(card.equals("estate"))
        {
            estate.setText("Estate\n" + (DominionModel.getInstance().getCardCount(card)));
        }
        else if(card.equals("duchy"))
        {
            duchy.setText("Duchy\n" + (DominionModel.getInstance().getCardCount(card)));
        }
        else if(card.equals("province"))
        {
            province.setText("Prov.\n" + (DominionModel.getInstance().getCardCount(card)));
        }
        else if(card.equals("gold"))
        {
            gold.setText("Gold\n" + (DominionModel.getInstance().getCardCount(card)));
        }
        else if(card.equals("silver"))
        {
            silver.setText("Silver\n" + (DominionModel.getInstance().getCardCount(card)));
        }
        else if(card.equals("copper"))
        {
            copper.setText("Copper\n" + (DominionModel.getInstance().getCardCount(card)));
        }
        else
        {
            cardCount.get(card).setText(card + "\n" + DominionModel.getInstance().getCardCount(card));
        }
    }
    public static void decrementBuy()
    {
        buying--;
        buys.setText("Buys\n" + buying);
    }
    public static void decrementActions()
    {
        actionses--;
        actions.setText("Actions\n" + actionses);
    }
    public static int getBuys()
    {
        return buying;
    }
    public static int getMoneies()
    {
        return moneies;
    }
    public static void setMoneies(int moneys)
    {
        moneies = moneys;
        money.setText("Money\n" + moneies);
    }
    public static int getActionses()
    {
        return actionses;
    }



}
