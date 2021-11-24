package com.example.magic;

import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.support.design.widget.*;


public class MainActivity extends AppCompatActivity{
    ViewPager viewPager;
    int currentPosition=0;
    boolean isAbove;
    public static boolean isOnAnswerPage=false;
    MyPagerAdapter adapter;
    List<Integer> firstNumberList;
   public static FloatingActionButton restartButton;
    public static  List<Boolean> yesNolist;
    public static List<Integer> addToAnswerList;

    @Override
    protected void onCreate ( Bundle savedInstanceState ){
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        yesNolist=new ArrayList<>();
        addToAnswerList=new ArrayList<>();
        viewPager = (ViewPager) findViewById ( R.id.view_pager );
        adapter =new MyPagerAdapter ( getSupportFragmentManager ( ) );
        viewPager.setAdapter ( adapter );
        initList ( );
        restartButton = (FloatingActionButton) findViewById(R.id.restartButton);
        restartButton.setOnClickListener ( new View.OnClickListener ( ){

              @Override
              public void onClick ( View p1 ){
                  reStart();
                }
              
          
        });
        viewPager.addOnPageChangeListener ( new ViewPager.OnPageChangeListener ( ){

              @Override
              public void onPageScrolled ( int p1, float p2, int p3 ){

                }

              @Override
              public void onPageSelected ( int p1 ){
                
                  if ( p1 > currentPosition )
                    {
                      if ( isAbove ){
                          yesNolist.add ( true );
                          
                      }
                        
                      else{
                          yesNolist.add ( false );
                         
                      }
                      if(p1==6)
                        isOnAnswerPage=true;
                        AnswerFragment.answerShown=false;
                        AnswerFragment.revealed=false;
                    }

                  else if ( p1 < currentPosition )
                    {
                      yesNolist.remove(yesNolist.size()-1);
                      if(p1==5){
                          restartButton.setVisibility(View.GONE);
                          AnswerFragment.answerTextView.setVisibility(View.INVISIBLE);
                          isOnAnswerPage=false;
                      }
                        
                    }
                  currentPosition = p1;
                }

              @Override
              public void onPageScrollStateChanged ( int p1 ){
                  // TODO: Implement this method
                }


            } );
        viewPager.setOnTouchListener ( new View.OnTouchListener ( ){

              @Override
              public boolean onTouch ( View p1, MotionEvent event ){
                  float y=event.getY ( );
                  if ( y >= getMidLocation ( ) )
                    {
                      isAbove = false;
                    }
                  else
                    {
                      isAbove = true;
                    }
                  return false;
                }
            } );
      }

    private void initList ( ){
        firstNumberList = new ArrayList<> ( );
        for ( int i=1;i <= 6;i++ )
          {
            firstNumberList.add ( i );
          }
      }

    private int getMidLocation ( ){
        int[] location = new int[2];
        View lineView =viewPager.findViewById ( R.id.view );
        lineView.getLocationOnScreen ( location );
        //   Toast.makeText(MainActivity.this,"X axis is "+location[0] +"and Y axis is "+location[1],Toast.LENGTH_LONG).show();
        int midLoc=location[1];
        return midLoc;
      }
    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter ( FragmentManager fm ){
            super ( fm );
          };
        @Override
        public int getCount ( ){

            return 7;
          }

        @Override
        public Fragment getItem ( int p1 ){
            if ( p1 < 6 )
              {
                Random random=new Random ( );
                int randomIndex=random.nextInt ( firstNumberList.size ( ) );
                Integer randomNumber= firstNumberList.get ( randomIndex );
                firstNumberList.remove ( randomIndex );
                addToAnswerList.add ( randomNumber );
                NumberFragment fragment=new NumberFragment ( randomNumber );
                return fragment;
              }
            else if ( p1 == 6 )
              {
                return new AnswerFragment();
              }
            return null;
          }
      }
      
      public void reStart(){
          Intent intent=new Intent(this,MainActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        this.finish();
        }
  }
  

