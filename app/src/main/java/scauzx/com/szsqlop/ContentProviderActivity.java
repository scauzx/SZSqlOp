package scauzx.com.szsqlop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.scauzx.database.content.UserProvider;
import com.scauzx.database.tables.GameTable;

/**
 * @author scauzx
 */
public class ContentProviderActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = ContentProviderActivity.class.getSimpleName();
    Button query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        initViews();
        initEvents();
    }

    public static void startActivity(Context context) {
        Intent starter = new Intent(context, ContentProviderActivity.class);
        context.startActivity(starter);
    }

    private void initEvents() {
        query.setOnClickListener(this);
    }

    private void initViews() {
        query = (Button) findViewById(R.id.query);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query:
                Cursor cursor = getContentResolver().query(UserProvider.GAME_CONTENT_URI, null, null,null,null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Log.d(TAG, GameTable.ATTRIBUTE_GAME_ID + " = " + cursor.getString(cursor.getColumnIndex(GameTable.ATTRIBUTE_GAME_ID)) + " " + GameTable.ATTRIBUTE_GAME_NAME + " = " + cursor.getString(cursor.getColumnIndex(GameTable.ATTRIBUTE_GAME_NAME)));
                    }
                }
                break;

        }

    }
}
