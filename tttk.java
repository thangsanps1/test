package com.example.tuandeptrai.thuchi3;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuandeptrai.thuchi3.Model.Atm;

import java.util.ArrayList;

/**
 * Created by tuan lun on 10/2/2016.
 */

public class tttk extends android.support.v4.app.Fragment {
Spinner taikhoan;
    TextView edttien,edtnganhang;
    Button btnnaptien,btnthoat;
    Database db;
    ArrayList<Atm> mangLoaiAtm= new ArrayList<Atm>();
    ArrayList<String> mangLoaiAtmspin = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.layout_tientk,container,false);
        taikhoan=(Spinner)view.findViewById(R.id.layout_tttk_spin);
        edttien=(TextView)view.findViewById(R.id.layout_tttk_tien);
        edtnganhang=(TextView)view.findViewById(R.id.layout_tttk_nganhang);
        btnnaptien=(Button)view.findViewById(R.id.layout_btn_naptien);
        btnthoat=(Button)view.findViewById(R.id.layout_btn_thoat);
        db = new Database(view.getContext(),"Quan_ly_thuchi1",null,1);

        //spinner
        getall_loaithe();
        final ArrayAdapter<String> adp1=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,mangLoaiAtmspin);
        adp1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);


        taikhoan.setAdapter(adp1);
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);

            }
        });

        taikhoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtnganhang.setText(mangLoaiAtm.get(position).getTien());
                edttien.setText(mangLoaiAtm.get(position).getTen());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnnaptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_naptien);
                dialog.setTitle("Nạp Tiền Tài Khoản");
                final EditText tien, ngaytao1;
                final Spinner spinner;
                Button btnthoat_naptien, btn_naptien;
                spinner=(Spinner)dialog.findViewById(R.id.dialog_naptien_spinner);
                tien=(EditText)dialog.findViewById(R.id.dialog_naptien_tien);
                ngaytao1=(EditText)dialog.findViewById(R.id.dialog_naptien_ngay);
                btn_naptien=(Button)dialog.findViewById(R.id.dialog_naptien_luu) ;
                btnthoat_naptien=(Button)dialog.findViewById(R.id.dialog_naptien_thoat) ;

                // cau hinh spinner



                spinner.setAdapter(adp1);
                btnthoat_naptien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       dialog.dismiss();
                    }
                });

               btn_naptien.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Boolean g = false;
                    String dk =   tien.getText().toString();
                       try {
                           int d = Integer.parseInt(dk);
                           for(int i = 0 ; i<mangLoaiAtm.size();i++){
                               String a = mangLoaiAtm.get(i).getID();
                               String b = spinner.getSelectedItem().toString();
                               if(a.equals(b)){
                                   int c = Integer.parseInt(tien.getText().toString())+  Integer.parseInt(mangLoaiAtm.get(i).getTien());
                                   if(  db.execute_data("UPDATE  ATM1 SET tien = '"+c+"' where ID_ATM = '"+b+"'   ")){

                                       Toast.makeText(getContext(),"Nạp Thành Công Số Tiền Hiện Tại là :"+c,Toast.LENGTH_LONG).show();
                                   }else{

                                       Toast.makeText(getContext(),"Nạp Thất bại , Xin hãy Kiểm Tra Mã Thẻ  ",Toast.LENGTH_LONG).show();
                                   }

                               }


                           }
                       }catch (Exception e){

                           Toast.makeText(getContext(),"ban phải nhập Tiền Bằng số",Toast.LENGTH_LONG).show();

                       }





                   }
               });

                dialog.show();

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
}
