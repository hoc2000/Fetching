package com.example.fetching;

import android.content.Context;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.fetching.model.Person;
import com.example.fetching.network.APIClient;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fetching.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    GridView gridView;

    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        //make network call
        Call<List<Person>> call = APIClient.apIinterface().getPerson();

        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if(response.isSuccessful()){
                   customAdapter = new CustomAdapter(response.body(),MainActivity.this);

                   gridView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error"+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    public class CustomAdapter extends BaseAdapter{

        public List<Person> personList;
        public Context context;

        public CustomAdapter(List<Person> personList, Context context) {
            this.personList = personList;
            this.context = context;
        }


        @Override
        public int getCount() {
            return personList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.row,null);

            //find view
            TextView name = view.findViewById(R.id.textView);
            ImageView image = view.findViewById(R.id.imageView);

            //set data
            name.setText(personList.get(position).getName());

            //set Image
            Glide.with(context)
                    .load(personList.get(position).getEmail())
                    .into(image);

            return view;
        }
    }
}