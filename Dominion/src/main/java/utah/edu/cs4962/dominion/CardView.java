package utah.edu.cs4962.dominion;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by StevenBurnett on 12/2/13.
 */
@TargetApi(17)
public class CardView extends ImageView
{

   public String cardName = null;
    public CardView(Context context, String cardName)
    {
        super(context);
        this.cardName = cardName;

    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        if(cardName.equals("gold"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.gold));
        }
        else if(cardName.equals("silver"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.silver));
        }
       else if(cardName.equals("copper"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.copper));
        }
        else if(cardName.equals("province"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.province));
        }
        else if(cardName.equals("duchy"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.duchy));
        }
        else if(cardName.equals("estate"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.estate));
        }
        else if(cardName.equals("adventurer"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.adventurer));
        }
        else if(cardName.equals("bureaucrat"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.bureaucrat));
        }
        else if(cardName.equals("cellar"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.cellar));
        }
        else if(cardName.equals("chancellor"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.chancellor));
        }
        else if(cardName.equals("chapel"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.chapel));
        }
        else if(cardName.equals("councilroom"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.councilroom));
        }
        else if(cardName.equals("festival"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.festival));
        }
        else if(cardName.equals("garden"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.garden));
        }
        else if(cardName.equals("lab"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.lab));
        }
        else if(cardName.equals("library"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.library));
        }
        else if(cardName.equals("market"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.market));
        }
        else if(cardName.equals("militia"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.militia));
        }
        else if(cardName.equals("mine"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.mine));
        }
        else if(cardName.equals("moat"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.moat));
        }
        else if(cardName.equals("moneylender"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.moneylender));
        }
        else if(cardName.equals("remodel"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.remodel));
        }
        else if(cardName.equals("smithy"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.smithy));
        }
        else if(cardName.equals("spy"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.spy));
        }
        else if(cardName.equals("throneroom"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.throneroom));
        }
        else if(cardName.equals("village"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.village));
        }
        else if(cardName.equals("witch"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.witch));
        }
        else if(cardName.equals("workshop"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.workshop));
        }
        else if(cardName.equals("woodcutter"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.woodcutter));
        }
        else if(cardName.equals("feast"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.feast));
        }
        else if(cardName.equals("curse"))
        {
            this.setBackground(getResources().getDrawable(R.drawable.curse));
        }
        else
        {
            this.setBackground(getResources().getDrawable(R.drawable.lab));
        }

    }
    public String getCardName()
    {
        return cardName;
    }
}
