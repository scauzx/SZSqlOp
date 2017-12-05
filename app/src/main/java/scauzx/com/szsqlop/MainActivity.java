package scauzx.com.szsqlop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.scauzx.db.UserDaoImp;
import com.scauzx.models.User;


/**
 * @author scauzx
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button add,delete,query,queryAll,update,deleteAll;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                UserDaoImp.getInstance().addUser(new User(editAddUserName.getText().toString().trim(), editAddPassword.getText().toString().trim()));
                break;
            case R.id.delete:
                UserDaoImp.getInstance().deleteUser(editQuery.getText().toString().trim());
                break;
            case R.id.query:
                UserDaoImp.getInstance().queryUserByName(editQuery.getText().toString().trim());
                break;
            case R.id.query_all:
                UserDaoImp.getInstance().getUsers();
                break;
            case R.id.update:
                UserDaoImp.getInstance().updateUser(new User(editUpdateUserName.getText().toString().trim(), editUpdatePassword.getText().toString().trim()));
                break;
            case R.id.delete_all:
                UserDaoImp.getInstance().deleteAllUser();
                break;
            default:
                break;
        }
    }
}
