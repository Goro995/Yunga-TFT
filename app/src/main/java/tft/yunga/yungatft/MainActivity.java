package tft.yunga.yungatft;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView previousText;
    TextView currentText;
    TextView nextText;
    ImageView plusImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        previousText = findViewById(R.id.prev_month_text);
        currentText = findViewById(R.id.curr_month_text);
        nextText = findViewById(R.id.next_month_text);
        plusImage = findViewById(R.id.plus_button);

        plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "plusImage clicked!");
                AddEventFragment addEventFragment = new AddEventFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_screen_layout, addEventFragment)
                        .addToBackStack(MainActivity.class.getSimpleName())
                        .commit();
            }
        });

        previousText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "previousText clicked!");
            }
        });

        currentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "currentText clicked!");

                AlertDialog alertDialog = new AlertDialog.Builder(
                        MainActivity.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("Свадьба Руслана и Людмилы");

                // Setting Dialog Message
                alertDialog.setMessage("Аппаратура: Микрофон, пульт, 2 саба, 5 паров, 2 бима");

                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        Toast.makeText(getApplicationContext(), "Мероприятие удалено", Toast.LENGTH_SHORT).show();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });

        nextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "nextText clicked!");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
