package jp.co.atschool.testspeech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textDisplay;
    private Button speechButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDisplay = (TextView) findViewById(R.id.TextView);
        speechButton = (Button) findViewById(R.id.SpeechButton);
        speechButton.setOnClickListener(this);
    }

    public void onClick(View v){
        if (getPackageManager().queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0).size()==0){
            Log.d("音声入力がサポートされているか","サポートされていません");
            return;
        }
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声を入力");
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){
            if (data.hasExtra(RecognizerIntent.EXTRA_RESULTS)){

                // 認識結果を ArrayList で取得
                ArrayList<String> candidates =
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if(candidates.size() > 0) {
                    // 認識結果候補で一番有力なものを表示
                    textDisplay.setText(candidates.get(0));
                }

//上かこれで精度検証                List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//
//                if (results.size() > 0){
//                    String str = "";
//                    for (String s: results){
//                        str += s + "¥n";
//                    }
//                    textDisplay.setText(str);
//                }
            }

        }
    }
}
