package com.example.dark.gametaixiuv2;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dark.gametaixiuv2.DAO.PlayerDAO;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    PlayerDAO playerDAO;
    String username;
    TextView tvmoney,tvname;
    RadioButton rdtai,rdxiu,rd100,rd200,rd300,rd400,rd500;
    RadioGroup rdgdatcuoc;
    Integer[] dsHinhXiNgau={R.drawable.xingau1,R.drawable.xingau2,R.drawable.xingau3,R.drawable.xingau4,
            R.drawable.xingau5,R.drawable.xingau6};
    AnimationDrawable cdXingau1, cdXiNgau2, cdXiNgau3;
    ImageView hinhXiNgau1, hinhXiNgau2, hinhXiNgau3;
    int GTXingau1, GTXingau2, GTXingau3,TongGT;
    int tienThuong,tienCuoc;
    int old_Money,new_money;
    Random randomXiNgau;
    boolean isTai;
    Timer timer=new Timer();
    Handler handler;
    Handler.Callback callback=new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            randomXiNgau1();
            randomXiNgau2();
            randomXiNgau3();
            TongGT=GTXingau1+GTXingau2+GTXingau3;
            int isChecked=rdgdatcuoc.getCheckedRadioButtonId();
            switch (isChecked){
                case R.id.rd100:
                    tienCuoc=100;
                    break;
                case R.id.rd200:
                    tienCuoc=200;
                    break;
                case R.id.rd300:
                    tienCuoc=300;
                    break;
                case R.id.rd400:
                    tienCuoc=400;
                    break;
                case R.id.rd500:
                    tienCuoc=500;
                    break;
            }
            if(rdtai.isChecked()){
                isTai=true;
            }
            if (rdxiu.isChecked()){
                isTai=false;
            }
            if(isTai && TongGT>10){
                tienThuong+=tienCuoc;
            }else if(isTai==false && TongGT<11){
                tienThuong+=tienCuoc;
            }else if(isTai && TongGT<11){
                tienThuong-=tienCuoc;
            }else if(isTai==false && TongGT>10){
                tienThuong-=tienCuoc;
            }
            luuTien();

            if(tienThuong>0){
                Toast.makeText(MainActivity.this,"Bạn Nhận Được "+tienThuong,Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this,"Bạn Bị "+tienThuong,Toast.LENGTH_SHORT).show();
            }
            tvmoney.setText(String.valueOf(new_money));
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerDAO=new PlayerDAO(getApplicationContext());
        rdtai=(RadioButton)findViewById(R.id.rdTai);
        rdtai=(RadioButton)findViewById(R.id.rdTai);
        rdxiu=(RadioButton)findViewById(R.id.rdxiu);
        rdgdatcuoc=(RadioGroup)findViewById(R.id.rdg2);
        rd100=(RadioButton)findViewById(R.id.rd100);
        rd200=(RadioButton)findViewById(R.id.rd200);
        rd300=(RadioButton)findViewById(R.id.rd300);
        rd400=(RadioButton)findViewById(R.id.rd400);
        rd500=(RadioButton)findViewById(R.id.rd500);
        tvmoney=(TextView)findViewById(R.id.tvTien);
        tvname=(TextView)findViewById(R.id.tvname);
        Intent intent=getIntent();
        username=intent.getStringExtra("name");
        old_Money=intent.getIntExtra("money",0);
        tvmoney.setText(String.valueOf(old_Money));
        tvname.setText(username);
        handler=new Handler(callback);
        hinhXiNgau1=(ImageView)findViewById(R.id.xingau1);
        hinhXiNgau2=(ImageView)findViewById(R.id.xingau2);
        hinhXiNgau3=(ImageView)findViewById(R.id.xingau3);

    }

    private void luuTien(){
        new_money=old_Money+tienThuong;
        playerDAO.updateMoney(username,new_money);

        old_Money=new_money;
    }
    public void LacXiNgau(View view){
        if(old_Money>0) {
            hinhXiNgau1.setImageResource(R.drawable.hinhdongxingau);
            hinhXiNgau2.setImageResource(R.drawable.hinhdongxingau);
            hinhXiNgau3.setImageResource(R.drawable.hinhdongxingau);
            cdXingau1=(AnimationDrawable)hinhXiNgau1.getDrawable();
            cdXiNgau2=(AnimationDrawable)hinhXiNgau2.getDrawable();
            cdXiNgau3=(AnimationDrawable)hinhXiNgau3.getDrawable();
            if(rdtai.isChecked()==false && rdxiu.isChecked()==false){
                Toast.makeText(MainActivity.this,"Bạn Chưa Chọn Tài Hay Xỉu",Toast.LENGTH_SHORT).show();
            }else {
                cdXingau1.start();
                cdXiNgau2.start();
                cdXiNgau3.start();
                tienThuong=0;
                timer.schedule(new LacXiNgau(),1000);
            }
        }else {
            Toast.makeText(MainActivity.this,"Bạn đã hết tiền! Làm ơn nạp tiền",Toast.LENGTH_SHORT).show();
        }


    }

    class LacXiNgau extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }


    private void randomXiNgau1(){
        randomXiNgau=new Random();
        int rd = randomXiNgau.nextInt(6);
        switch (rd) {
            case 0:
                hinhXiNgau1.setImageResource(dsHinhXiNgau[0]);
                GTXingau1 = rd+1;
                break;
            case 1:
                hinhXiNgau1.setImageResource(dsHinhXiNgau[1]);
                GTXingau1 = rd+1;
                break;
            case 2:
                hinhXiNgau1.setImageResource(dsHinhXiNgau[2]);
                GTXingau1 = rd+1;
                break;
            case 3:
                hinhXiNgau1.setImageResource(dsHinhXiNgau[3]);
                GTXingau1 = rd+1;
                break;
            case 4:
                hinhXiNgau1.setImageResource(dsHinhXiNgau[4]);
                GTXingau1 = rd+1;
                break;
            case 5:
                hinhXiNgau1.setImageResource(dsHinhXiNgau[5]);
                GTXingau1 = rd+1;
                break;
        }
    }

    private void randomXiNgau2(){
        randomXiNgau=new Random();
        int rd = randomXiNgau.nextInt(6);
        switch (rd) {
            case 0:
                hinhXiNgau2.setImageResource(dsHinhXiNgau[0]);
                GTXingau2 = rd+1;
                break;
            case 1:
                hinhXiNgau2.setImageResource(dsHinhXiNgau[1]);
                GTXingau2 = rd+1;
                break;
            case 2:
                hinhXiNgau2.setImageResource(dsHinhXiNgau[2]);
                GTXingau2 = rd+1;
                break;
            case 3:
                hinhXiNgau2.setImageResource(dsHinhXiNgau[3]);
                GTXingau2 = rd+1;
                break;
            case 4:
                hinhXiNgau2.setImageResource(dsHinhXiNgau[4]);
                GTXingau2 = rd+1;
                break;
            case 5:
                hinhXiNgau2.setImageResource(dsHinhXiNgau[5]);
                GTXingau2 = rd+1;
                break;
        }
    }

    private void randomXiNgau3(){
        randomXiNgau=new Random();
        int rd = randomXiNgau.nextInt(6);
        switch (rd) {
            case 0:
                hinhXiNgau3.setImageResource(dsHinhXiNgau[0]);
                GTXingau3 = rd+1;
                break;
            case 1:
                hinhXiNgau3.setImageResource(dsHinhXiNgau[1]);
                GTXingau3 = rd+1;
                break;
            case 2:
                hinhXiNgau3.setImageResource(dsHinhXiNgau[2]);
                GTXingau3 = rd+1;
                break;
            case 3:
                hinhXiNgau3.setImageResource(dsHinhXiNgau[3]);
                GTXingau3 = rd+1;
                break;
            case 4:
                hinhXiNgau3.setImageResource(dsHinhXiNgau[4]);
                GTXingau3 = rd+1;
                break;
            case 5:
                hinhXiNgau3.setImageResource(dsHinhXiNgau[5]);
                GTXingau3 = rd+1;
                break;
        }
    }

}
