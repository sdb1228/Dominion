package utah.edu.cs4962.dominion;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class MainController extends ActionBarActivity {

    HandFragment handFragment = null;




    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout mainLayout = new LinearLayout(this);
        LinearLayout couple = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        handFragment = new HandFragment();

       LinearLayout bottom = new LinearLayout(this);
        bottom.setBackgroundColor(Color.rgb(100,149,237));
        bottom.setOrientation(LinearLayout.VERTICAL);
        bottom.setId(10);


       final DeckView deck = new DeckView(this);

        couple.addView(bottom, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        couple.addView(deck, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2));
        deck.setOnEndGameListener(new DeckView.EndGameListener()
        {
            @Override
            public void endGame()
            {
                finish();
                startActivity(getIntent());
            }
        });

        InfoViewgroup info = new InfoViewgroup(this);
        info.setBackgroundColor(Color.GRAY);
        deck.setOnPlayerChangeListener(new DeckView.OnPlayerChangeListener() {
            @Override
            public void onPlayerChangeListener() {
               handFragment.newPlayer();
                DominionModel.getInstance().discard(DeckView.player, HandFragment.playerDeck);
               handFragment.getListView().invalidateViews();
            }
        });




        mainLayout.addView(couple, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0, 0.75f));
        mainLayout.addView(info, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0, 0.25f));
        setContentView(mainLayout);





        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(10, handFragment);
        transaction.commit();





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    }



}
