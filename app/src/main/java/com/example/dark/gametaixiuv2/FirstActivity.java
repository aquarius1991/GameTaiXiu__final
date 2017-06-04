package com.example.dark.gametaixiuv2;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dark.gametaixiuv2.DAO.PlayerDAO;
import com.example.dark.gametaixiuv2.DTO.Player;

public class FirstActivity extends AppCompatActivity {
    PlayerDAO playerDAO;
    Button btnPlayGame,btnBuy,btnExit;
    String username,password;
    int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        btnPlayGame=(Button)findViewById(R.id.btnPlayGame);
        btnExit=(Button)findViewById(R.id.btnExit);
        btnBuy=(Button)findViewById(R.id.btnBuy);
        playerDAO=new PlayerDAO(getApplicationContext());
        final Dialog d=new Dialog(this);
        d.setTitle("Login");
        d.setContentView(R.layout.dialog_dangnhap);
        final Button btnLogin=(Button)d.findViewById(R.id.btnLogin);
        final Button btnCreate=(Button)d.findViewById(R.id.btnCreate);
        final EditText edtUser=(EditText)d.findViewById(R.id.edtUsername);
        final EditText edtPass=(EditText)d.findViewById(R.id.edtPassword);
        final CheckBox chkremember=(CheckBox)d.findViewById(R.id.chbRemember);
        SharedPreferences prefs=getSharedPreferences("key",MODE_PRIVATE);
        if(prefs!=null){
            edtUser.setText(prefs.getString("username",""));
            edtPass.setText(prefs.getString("password",""));
        }
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(FirstActivity.this);
                dialog.setTitle("CREATE ACCOUNT");
                dialog.setContentView(R.layout.dialog_create);
                final Button btnCreateAcc=(Button)dialog.findViewById(R.id.btnCreateAccount);
                final EditText edtCreateUser=(EditText)dialog.findViewById(R.id.edtCreateUser);
                final EditText edtCreatePass=(EditText)dialog.findViewById(R.id.edtCreatePassword);
                final EditText edtCreateConfirm=(EditText)dialog.findViewById(R.id.edtCreateCofirmPassword);
                btnCreateAcc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edtCreateUser.getText().toString().isEmpty() || edtCreatePass.getText().toString().isEmpty()){
                            Toast.makeText(FirstActivity.this,"Làm ơn nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                        }else {
                            if(playerDAO.checkUsername(edtCreateUser.getText().toString().trim().toLowerCase())){
                                Toast.makeText(FirstActivity.this,"Username đã tồn tại",Toast.LENGTH_LONG).show();
                            }else {
                                if(edtCreatePass.getText().toString().equals(edtCreateConfirm.getText().toString())){
                                    Player player=new Player(edtCreateUser.getText().toString().trim().toLowerCase(),edtCreatePass.getText().toString().trim().toLowerCase());
                                    playerDAO.addPlayer(player);
                                    Toast.makeText(FirstActivity.this,"Tạo thành công",Toast.LENGTH_SHORT).show();
                                    username=edtCreateUser.getText().toString().trim().toLowerCase();
                                    password=edtCreatePass.getText().toString().trim().toLowerCase();
                                    edtUser.setText(username);
                                    edtPass.setText(password);
                                    dialog.dismiss();
                                }
                                else {
                                    Toast.makeText(FirstActivity.this,"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                dialog.show();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtUser.getText().toString().isEmpty()==false && edtPass.getText().toString().isEmpty()==false){
                    if(playerDAO.checkLogin(edtUser.getText().toString().trim().toLowerCase(),edtPass.getText().toString().trim().toLowerCase())){
                        if(chkremember.isChecked()){
                            SaveInfo(edtUser.getText().toString().trim().toLowerCase(),edtPass.getText().toString().trim().toLowerCase());
                        }else {
                            SharedPreferences.Editor editor=getSharedPreferences("key",MODE_PRIVATE).edit();
                            if(editor!=null){
                                editor.remove("username");
                                editor.remove("password");
                                editor.commit();
                            }
                        }
                        money=playerDAO.getMoney(edtUser.getText().toString().trim().toLowerCase());
                        username=edtUser.getText().toString().trim().toLowerCase();
                        d.dismiss();
                    }else {
                        Toast.makeText(FirstActivity.this,"tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(FirstActivity.this,"Làm ơn nhập tài khoản và mật khẩu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
        d.setCanceledOnTouchOutside(false);
        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money=playerDAO.getMoney(edtUser.getText().toString().trim().toLowerCase());
                Intent intent =new Intent(FirstActivity.this,MainActivity.class);
                intent.putExtra("name",edtUser.getText().toString().trim().toLowerCase());
                intent.putExtra("money",money);
                startActivity(intent);
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d1=new Dialog(FirstActivity.this);
                d1.setTitle("Nạp Tiền");
                d1.setContentView(R.layout.dialog_naptien);
                final RadioGroup group=(RadioGroup)d1.findViewById(R.id.group);
                final RadioButton rd5000=(RadioButton)d1.findViewById(R.id.rd5000);
                final RadioButton rd10000=(RadioButton)d1.findViewById(R.id.rd5000);
                final RadioButton rd15000=(RadioButton)d1.findViewById(R.id.rd5000);
                final Button btnnap=(Button)d1.findViewById(R.id.btnnap);
                btnnap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int check=group.getCheckedRadioButtonId();
                        money=playerDAO.getMoney(username);
                        switch (check){
                            case R.id.rd5000:
                                playerDAO.updateMoney(username,money+5000);
                                money+=5000;
                                break;
                            case R.id.rd10000:
                                playerDAO.updateMoney(username,money+10000);
                                money+=10000;
                                break;
                            case R.id.rd15000:
                                playerDAO.updateMoney(username,money+15000);
                                money+=15000;
                                break;
                        }
                        d1.dismiss();
                        Toast.makeText(FirstActivity.this,"Nạp Tiền Thành Công",Toast.LENGTH_SHORT).show();
                    }
                });
                d1.show();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtUser.setText("");
                edtPass.setText("");
                edtPass.clearFocus();
                d.show();
            }
        });

    }

    private void SaveInfo(String username,String password){
        SharedPreferences.Editor editor=getSharedPreferences("key",MODE_PRIVATE).edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.commit();
    }

    private void ReloadInfo(){
        SharedPreferences prefs=getSharedPreferences("key",MODE_PRIVATE);
        if(prefs!=null){

        }
    }
}
