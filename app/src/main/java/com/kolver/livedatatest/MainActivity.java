package com.kolver.livedatatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    public MyDatasetViewModel viewModel;
    private Button increase;
    private TextView score;
    private EditText surname;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MyDatasetViewModel.class);
        username = findViewById(R.id.username);
        surname = findViewById(R.id.surname);
        score = findViewById(R.id.score);
        increase = findViewById(R.id.button);


        viewModel.getUserData().observe(this, new Observer<MyDataset>() {
            @Override //When we got the data; THIS SHIT AUTOMATICLY UPDATES IT!
            public void onChanged(MyDataset myDataset) {
                if (!username.getText().toString().equals(myDataset.getUserName())) {
                    username.setText(myDataset.getUserName());
                }
                if (!surname.getText().toString().equals(myDataset.getUserSurname())) {
                    surname.setText(myDataset.getUserSurname());
                }
                score.setText(myDataset.getScore() + "");
            }
        });
        //viewModel.loadLiveData();

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getUserData() !=null) {
                    int scorePoint = viewModel.getUserData().getValue().getScore();
                    String nameee = username.getText().toString();
                    String surnamee = surname.getText().toString();
                    viewModel.getUserData().getValue().setUserName(nameee);
                    viewModel.getUserData().getValue().setUserSurname(surnamee);
                    viewModel.getUserData().getValue().setScore(scorePoint + 1);
                    System.out.println("viewModel.hasActiveObservers() = " + viewModel.getUserData().hasActiveObservers());
                    Toast.makeText(MainActivity.this, "scorepoint : "+ scorePoint, Toast.LENGTH_SHORT).show();
                }
            }
        });





    }



    public static  class MyDatasetViewModel extends ViewModel {

        public MutableLiveData<MyDataset> liveData;

        public LiveData<MyDataset> getUserData() {
            System.out.println("liveData = " + liveData);
            if (liveData == null) {
                liveData = new MutableLiveData<>();
                System.out.println("liveData = " + "null");
                loadLiveData();// Automaticly updates layout when clicked!
            }
            liveData.setValue(liveData.getValue());/// Birebir böyle yapmalısın!
            return liveData;
        }

        public void loadLiveData() {
            //Bunu Hiçbirzaman layout içerisinde kullanma!
            liveData.setValue(new MyDataset("Emirhan","Kolver",0));
        }

    }

    public static class MyDataset {
        String userName;
        String userSurname;
        int score;

        public MyDataset(String userName, String userSurname, int score) {
            this.userName = userName;
            this.userSurname = userSurname;
            this.score = score;
        }

        public MyDataset() {
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserSurname() {
            return userSurname;
        }

        public void setUserSurname(String userSurname) {
            this.userSurname = userSurname;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}