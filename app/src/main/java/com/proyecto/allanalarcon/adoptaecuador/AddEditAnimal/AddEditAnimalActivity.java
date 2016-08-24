package com.proyecto.allanalarcon.adoptaecuador.AddEditAnimal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.proyecto.allanalarcon.adoptaecuador.Animals.AnimalsActivity;
import com.proyecto.allanalarcon.adoptaecuador.R;

public class AddEditAnimalActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_ANIMAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_animal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String animalId = getIntent().getStringExtra(AnimalsActivity.EXTRA_ANIMAL_ID);

        setTitle(animalId == null ? "Añadir mascota" : "Editar mascota");

        AddEditAnimalFragment addEditAnimalFragment = (AddEditAnimalFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_animal_container);
        if (addEditAnimalFragment == null) {
            addEditAnimalFragment = AddEditAnimalFragment.newInstance(animalId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_animal_container, addEditAnimalFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
