package com.proyecto.allanalarcon.adoptaecuador.Animals;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.proyecto.allanalarcon.adoptaecuador.R;
import com.proyecto.allanalarcon.adoptaecuador.data.AnimalsDbHelper;

public class AnimalsActivity extends AppCompatActivity {

    private AnimalsCursorAdapter mAnimalsAdapter;
    private AnimalsDbHelper mAnimalsDbHelper;

    public static final String EXTRA_ANIMAL_ID = "extra_animal_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AnimalsFragment fragment = (AnimalsFragment)
                getSupportFragmentManager().findFragmentById(R.id.animals_container);

        if (fragment == null) {
            fragment = AnimalsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.animals_container, fragment)
                    .commit();
        }


    }

}
