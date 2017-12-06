package scauzx.com.szsqlop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.scauzx.database.caches.UserCache;
import com.scauzx.database.utils.UserDbUtils;
import com.scauzx.db.DaoFactory;
import com.scauzx.db.UserDaoImp;
import com.scauzx.models.User;


/**
 * @author scauzx
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, UserCache.UserInfoChangeListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    Button add,delete,query,queryAll,update,deleteAll,jump;
    EditText editQuery,editUpdateUserName,editUpdatePassword, editAddPassword, editAddUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    private void initEvents() {
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        query.setOnClickListener(this);
        queryAll.setOnClickListener(this);
        update.setOnClickListener(this);
        deleteAll.setOnClickListener(this);
        jump.setOnClickListener(this);
        UserCache.getInstance().register(this);
    }

    private void initViews() {
        editAddUserName = (EditText) findViewById(R.id.edit_add_username);
        editAddPassword = (EditText) findViewById(R.id.edit_add_password);
        add = (Button) findViewById(R.id.add);
        delete = (Button) findViewById(R.id.delete);
        query = (Button) findViewById(R.id.query);
        queryAll = (Button) findViewById(R.id.query_all);
        update = (Button) findViewById(R.id.update);
        editQuery = (EditText) findViewById(R.id.edit_query);
        editUpdateUserName = (EditText) findViewById(R.id.edit_update_username);
        editUpdatePassword = (EditText) findViewById(R.id.edit_update_password);
        deleteAll = (Button) findViewById(R.id.delete_all);
        jump = (Button) findViewById(R.id.jump);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                UserCache.getInstance().addUser(new User(editAddUserName.getText().toString().trim(), editAddPassword.getText().toString().trim()));
                //UserDbUtils.addUser(new User(editAddUserName.getText().toString().trim(), editAddPassword.getText().toString().trim()));
               // DaoFactory.getUserDaoInstance().addUser(new User(editAddUserName.getText().toString().trim(), editAddPassword.getText().toString().trim()));
                break;
            case R.id.delete:
                UserCache.getInstance().deleteUser(editQuery.getText().toString().trim());
                //UserDbUtils.deleteUser(editQuery.getText().toString().trim());
             //   DaoFactory.getUserDaoInstance().deleteUser(editQuery.getText().toString().trim());
                break;
            case R.id.query:
                UserCache.getInstance().queryUserByName(editQuery.getText().toString().trim(), new UserCache.GetUserInfoListener() {
                    @Override
                    public void onUserInfoSuccess(User user) {
                        Log.d(TAG, "user = " + user);
                    }

                    @Override
                    public void onUserInfoFailed(int errorCode) {
                        Log.d(TAG, "errorCode = " + errorCode);
                    }
                });
                //UserDbUtils.queryUserByName(editQuery.getText().toString().trim());
              //  DaoFactory.getUserDaoInstance().queryUserByName(editQuery.getText().toString().trim());
                break;
            case R.id.query_all:
                UserCache.getInstance().queryAllUsers();
                //UserDbUtils.getUserInfo();
//                DaoFactory.getUserDaoInstance().getUsers();
                break;
            case R.id.update:
                UserCache.getInstance().updateUser(new User(editUpdateUserName.getText().toString().trim(), editUpdatePassword.getText().toString().trim()));
                //UserDbUtils.updateUser(new User(editUpdateUserName.getText().toString().trim(), editUpdatePassword.getText().toString().trim()));
               // DaoFactory.getUserDaoInstance().updateUser(new User(editUpdateUserName.getText().toString().trim(), editUpdatePassword.getText().toString().trim()));
                break;
            case R.id.delete_all:
                UserCache.getInstance().deleteAllUsers();
//                UserDbUtils.deleteAllUsers();
//                DaoFactory.getUserDaoInstance().deleteAllUser();
                break;
            case R.id.jump:
                ContentProviderActivity.startActivity(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserCache.getInstance().unRegister(this);
    }

    @Override
    public void onUserInfoChange() {
        Log.i(TAG, "onUserInfoChange:  userIno is " + UserCache.getInstance().getUserInfo());
    }
}
