package com.example.tuandeptrai.thuchi3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tuandeptrai.thuchi3.Model.Atm;
import com.example.tuandeptrai.thuchi3.Model.Loaithuchi;

import java.util.ArrayList;

/**
 * Created by tuan lun on 10/2/2016.
 */

public class thuchi extends android.support.v4.app.Fragment {
    Button btnluu,btnthoat,thuchi_btnseach;
    Spinner loaithuchi,loaithe;
    EditText tien,ngay,ghichu,thuchi_edt_seach;
    ArrayList<Atm> mangLoaiAtm= new ArrayList<Atm>();
    ArrayList<String> mangLoaiAtmspin = new ArrayList<String>();
    ArrayList<Loaithuchi> mangLoaithuchi= new ArrayList<Loaithuchi>();
    ArrayList<String> mangspinloaithuchi = new ArrayList<String>();
    Database db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.layout_thuchi,container,false);

        btnluu=(Button)view.findViewById(R.id.btn_thuchi_luu);
        btnthoat=(Button)view.findViewById(R.id.btn_thuchi_thoat);
        tien=(EditText)view.findViewById(R.id.edt_thuchi_tien);
        ngay=(EditText)view.findViewById(R.id.edt_thuchi_ngay);
        ghichu=(EditText)view.findViewById(R.id.edt_thuchi_ghichu);
        loaithuchi=(Spinner)view.findViewById(R.id.spinner_thuchi);
        loaithe=(Spinner)view.findViewById(R.id.spinner2_loaithe);

        // spinner
       db = new Database(view.getContext(),"Quan_ly_thuchi1",null,1);
        getall_loaithuchi();
        getall_loaithe();
        ArrayAdapter<String> adp1=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,mangLoaiAtmspin);
        adp1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);


        loaithe.setAdapter(adp1);
        // spinner loai thu chi



        ArrayAdapter<String> adp2=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,mangspinloaithuchi);
        adp2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);


        loaithuchi.setAdapter(adp2);


        // ínert
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String a = loaithe.getSelectedItem().toString();
                    for (int i = 0; i < mangLoaiAtm.size(); i++) {
                        String b = mangLoaiAtm.get(i).getID();
                        String loai=loaithuchi.getSelectedItem().toString();



                        if (a.equals(b) && loai.equals("Chi") && loai.equals("No") ) {
                            int k = Integer.parseInt(mangLoaiAtm.get(i).getTien());
                            int l = Integer.parseInt(tien.getText().toString());
                            int ketqua = k - l;
                            if (ketqua < 0) {
                                Toast.makeText(getContext(), "Số Tiền trong Tài Khoản " + a + "Qua ý Hãy Nạp Thêm", Toast.LENGTH_LONG).show();


                            } else {

                                if (db.execute_data("INSERT INTO Thuchi VALUES(null,'" + loaithuchi.getSelectedItem().toString() + "','" + ghichu.getText().toString() + "','" + tien.getText().toString() + "','" + loaithe.getSelectedItem().toString() + "','" + ngay.getText().toString() + "')")) {


                                    db.execute_data("UPDATE  ATM1 SET tien = '" + ketqua + "' where ID_ATM = '" + a + "'   ");


                                    Toast.makeText(getContext(), "Them Thanh Cong", Toast.LENGTH_LONG).show();
                                } else {

                                    Toast.makeText(getContext(), "Them That bai", Toast.LENGTH_LONG).show();
                                }

                            }


                        }else{

                            if (a.equals(b)  ) {
                                int k = Integer.parseInt(mangLoaiAtm.get(i).getTien());
                                int l = Integer.parseInt(tien.getText().toString());
                                int ketqua = k + l;


                                    if (db.execute_data("INSERT INTO Thuchi VALUES(null,'" + loaithuchi.getSelectedItem().toString() + "','" + ghichu.getText().toString() + "','" + tien.getText().toString() + "','" + loaithe.getSelectedItem().toString() + "','" + ngay.getText().toString() + "')")) {


                                        db.execute_data("UPDATE  ATM1 SET tien = '" + ketqua + "' where ID_ATM = '" + a + "'   ");


                                        Toast.makeText(getContext(), "Them Thanh Cong", Toast.LENGTH_LONG).show();
                                    } else {

                                        Toast.makeText(getContext(), "Them That bai", Toast.LENGTH_LONG).show();
                                    }

                                }

                        }


                    }

                }catch(Exception ex){
                    Toast.makeText(getContext(),"Bạn phải nhập Tiền Bằng số",Toast.LENGTH_LONG).show();
                }


            }
        });

        // thoat
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent    intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        return view;





    }
    public  void getall_loaithe(){


        Cursor ATM = db.KhoaPham_getData("SELECT * FROM ATM1");
        while (ATM.moveToNext()) {
            Atm loaithuchi = new Atm();
            loaithuchi.setID(ATM.getString(0));
            loaithuchi.setTen(ATM.getString(1));
            loaithuchi.setTien(ATM.getString(2));
            mangLoaiAtmspin.add(ATM.getString(0));
            mangLoaiAtm.add(loaithuchi);


        }


    }
    public   void getall_loaithuchi(){

        Cursor loaithuchi1 = db.KhoaPham_getData("SELECT * FROM LoaiThuchi");
        while (loaithuchi1.moveToNext()) {
            Loaithuchi loaithuchi = new Loaithuchi();
            loaithuchi.setID_thuchi(loaithuchi1.getString(0));
            loaithuchi.setTen(loaithuchi1.getString(1));
            mangspinloaithuchi.add(loaithuchi1.getString(0));
            mangLoaithuchi.add(loaithuchi);

        }

    }



}
