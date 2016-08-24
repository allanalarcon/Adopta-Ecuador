package com.proyecto.allanalarcon.adoptaecuador.Animals;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.proyecto.allanalarcon.adoptaecuador.AddEditAnimal.AddEditAnimalActivity;
import com.proyecto.allanalarcon.adoptaecuador.AnimalDetail.AnimalDetailActivity;
import com.proyecto.allanalarcon.adoptaecuador.R;
import com.proyecto.allanalarcon.adoptaecuador.data.AnimalsContract;
import com.proyecto.allanalarcon.adoptaecuador.data.AnimalsDbHelper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnimalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimalsFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_ANIMAL = 2;

    private AnimalsDbHelper mAnimalsDbHelper;

    private ListView mAnimalsList;
    private AnimalsCursorAdapter mAnimalsAdapter;
    private FloatingActionButton mAddButton;

    public AnimalsFragment() {
        // Required empty public constructor
    }

    public static AnimalsFragment newInstance() {
        return new AnimalsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_animals, container, false);

        // Referencias UI
        mAnimalsList = (ListView) root.findViewById(R.id.animals_list);
        mAnimalsAdapter = new AnimalsCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mAnimalsList.setAdapter(mAnimalsAdapter);

        mAnimalsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mAnimalsAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(AnimalsContract.AnimalEntry.ID));

                showDetailScreen(currentLawyerId);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        getActivity().deleteDatabase(AnimalsDbHelper.DATABASE_NAME);

        // Instancia de helper
        mAnimalsDbHelper = new AnimalsDbHelper(getActivity());

        // Carga de datos
        loadAnimals();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditAnimalActivity.REQUEST_ADD_ANIMAL:
                    showSuccessfullSavedMessage();
                    loadAnimals();
                    break;
                case REQUEST_UPDATE_DELETE_ANIMAL:
                    loadAnimals();
                    break;
            }
        }
    }

    private void loadAnimals() {
        new AnimalsLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Mascota guardada correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditAnimalActivity.class);
        startActivityForResult(intent, AddEditAnimalActivity.REQUEST_ADD_ANIMAL);
    }

    private void showDetailScreen(String animalId) {
        Intent intent = new Intent(getActivity(), AnimalDetailActivity.class);
        intent.putExtra(AnimalsActivity.EXTRA_ANIMAL_ID, animalId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_ANIMAL);
    }

    private class AnimalsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAnimalsDbHelper.getAllAnimals();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mAnimalsAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
}
