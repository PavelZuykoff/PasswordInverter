package zuykov.pavel.passinv;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private TextView outEngTextView;
    private EditText inRusEditText;
    private ImageView catImage;
    private String savedText = "";
    private String savedEngText = "";
    private final String TAG = "Happy";
    final int maxLength = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        inRusEditText = (EditText) findViewById(R.id.inRuText);
        outEngTextView = (TextView) findViewById(R.id.outEngText);


        inRusEditText.setText(savedText);
        inRusEditText.setSelection(inRusEditText.getText().length());
        outEngTextView.setText(savedEngText);

        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                StringBuilder filteredBuilder = new StringBuilder();

                for (int i = start; i < end; i++) {
                    if (Character.isSpaceChar(source.charAt(i))) {

                        Log.d(TAG, "source: " + source.toString() + ", start: " + start + ", end: " + end + ", dest: " + dest.toString() + ", dstart: " + dstart + ", dend: " + dend);
                        Toast filterToast = Toast.makeText(getApplicationContext(), "Пробелы недопустимы", Toast.LENGTH_SHORT);
                        filterToast.setGravity(Gravity.CENTER, 0, 0);
                        filterToast.show();
                        inRusEditText.setText(savedText);
                        inRusEditText.setSelection(inRusEditText.getText().length());


                    } else {
                        filteredBuilder.append(source.charAt(i));
                        savedText = filteredBuilder.toString();
                        Log.d(TAG, "source: " + source.toString() + ", start: " + start + ", end: " + end + ", dest: " + dest.toString() + ", dstart: " + dstart + ", dend: " + dend);
                        Log.d(TAG, "builder: " + filteredBuilder.toString());
                    }
/*                    if (savedText.length() == maxLength && ){
                        Toast filterToast = Toast.makeText(getApplicationContext(), "Максимум 20 символов", Toast.LENGTH_SHORT);
                        filterToast.setGravity(Gravity.CENTER, 0, 0);
                        filterToast.show();
                    }*/


                }
                return null;
            }
        };

        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(maxLength);


        inRusEditText.setFilters(new InputFilter[]{inputFilter, lengthFilter});




        //разные котики в портретной и ландшафтной ориентации
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            catImage = (ImageView) findViewById(R.id.cat);
            catImage.setImageResource(R.drawable.cat);
        } else {
            catImage = (ImageView) findViewById(R.id.cat);
            catImage.setImageResource(R.drawable.cat_landscape);
        }


    }

    public void onClick(View view) {

        if (inRusEditText.length() == 0) {

            Toast voidEditText = Toast.makeText(getApplicationContext(), "Вы ничего не ввели", Toast.LENGTH_SHORT);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                voidEditText.setGravity(Gravity.CENTER, 0, 0);
                voidEditText.show();
            } else {
                voidEditText.setGravity(Gravity.CENTER, 0, 300);
                voidEditText.show();
            }

        } else {

            String rusInputedText = inRusEditText.getText().toString();
            String engOutputText;
            Log.d("Happy", rusInputedText);

            RusToEngKeyboardInverter inverter = new RusToEngKeyboardInverter();
            engOutputText = inverter.invertRusToEng(rusInputedText);
            outEngTextView.setText(engOutputText);
            savedEngText = engOutputText;
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("password", engOutputText);
            clipboard.setPrimaryClip(clip);

            Toast bufferToastMessage = Toast.makeText(getApplicationContext(), "Пароль скопирован в буфер обмена.", Toast.LENGTH_LONG);

// Проверяем ориентацию экрана и выводим тост с сообщением;
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                bufferToastMessage.setGravity(Gravity.CENTER, 0, 0);
                bufferToastMessage.show();
            } else {
                bufferToastMessage.setGravity(Gravity.CENTER, 0, 300);
                bufferToastMessage.show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        inRusEditText.setText(savedText);
        inRusEditText.setSelection(inRusEditText.getText().length());
        outEngTextView.setText(savedEngText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}
