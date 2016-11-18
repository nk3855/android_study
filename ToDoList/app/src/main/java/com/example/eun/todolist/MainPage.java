package com.example.eun.todolist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.StringTokenizer;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        SharedPreferences data = getSharedPreferences("main_data", Activity.MODE_PRIVATE);

        ProgressBar monster_xp = (ProgressBar)findViewById(R.id.monster_xp);
        TextView level = (TextView)findViewById(R.id.moster_level_info);

        monster_xp.setProgress(data.getInt("xp", 0));
        level.setText(data.getString("level", "Lv0"));

        ImageButton move_list_page = (ImageButton)findViewById(R.id.to_do_list);
        move_list_page.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                add_xp(20);
              //  Intent list_page = new Intent(MainPage.this, ToDoList.class);
              //  startActivity(list_page);
            }
        });

    }
    public boolean add_xp(int xp){ // add to current xp(if xp is full, return true)
        ProgressBar monster_xp = (ProgressBar)findViewById(R.id.monster_xp);
        int sum = xp + monster_xp.getProgress();
        if(sum >= 100){
            sum -= 100;
            monster_xp.setProgress(sum);
            TextView level = (TextView)findViewById(R.id.moster_level_info);
            level.setText("Lv" + get_level(level.getText()));
            return true;
        }
        else{
            monster_xp.setProgress(sum);
            return false;
        }
    }
    int get_level(CharSequence level_str){
        StringTokenizer number = new StringTokenizer((String)level_str);
        String str = (String) number.nextToken("Lv");
        int lv = Integer.parseInt(str);
        return (lv + 1);
    }

    public void onStop(){
        super.onStop();
        SharedPreferences data = getSharedPreferences("main_data", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();

        ProgressBar monster_xp = (ProgressBar)findViewById(R.id.monster_xp);
        TextView level = (TextView)findViewById(R.id.moster_level_info);

        editor.putInt("xp", monster_xp.getProgress());
        editor.putString("level", (String) level.getText());

        editor.commit();
    }
}