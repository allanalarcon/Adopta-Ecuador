package com.proyecto.allanalarcon.adoptaecuador.AnimalDetail;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.proyecto.allanalarcon.adoptaecuador.AddEditAnimal.AddEditAnimalActivity;
import com.proyecto.allanalarcon.adoptaecuador.Animals.AnimalsActivity;
import com.proyecto.allanalarcon.adoptaecuador.Animals.AnimalsFragment;
import com.proyecto.allanalarcon.adoptaecuador.R;
import com.proyecto.allanalarcon.adoptaecuador.data.Animal;
import com.proyecto.allanalarcon.adoptaecuador.data.AnimalsDbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnimalDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimalDetailFragment extends Fragment {
    private static final String ARG_ANIMAL_ID = "animalId";

    private String mAnimalId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mCiudad;
    private TextView mSexo;
    private TextView mDescripcion;
    private TextView mEdad;
    private TextView mTipo;

    private String avatar;
    private String raza;
    private String estado;

    private AnimalsDbHelper mAnimalsDbHelper;


    public AnimalDetailFragment() {
        // Required empty public constructor
    }

    public static AnimalDetailFragment newInstance(String animalId) {
        AnimalDetailFragment fragment = new AnimalDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ANIMAL_ID, animalId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mAnimalId = getArguments().getString(ARG_ANIMAL_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_animal_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mCiudad = (TextView) root.findViewById(R.id.tv_ciudad);
        mSexo = (TextView) root.findViewById(R.id.tv_sexo);
        mDescripcion = (TextView) root.findViewById(R.id.tv_descripcion);
        mEdad = (TextView) root.findViewById(R.id.tv_edad);
        mTipo = (TextView) root.findViewById(R.id.tv_tipo);


        mAnimalsDbHelper = new AnimalsDbHelper(getActivity());

        loadAnimal();

        return root;
    }

    private void loadAnimal() {
        new GetAnimalByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                if (estado.equals("1")) showAdoptadoError();
                else showEditScreen();
                break;
            case R.id.action_adoptar:
                if (estado.equals("1")) showAdoptadoError();
                else
                {
                    boolean error = false;

                    String ciudad = mCiudad.getText().toString();
                    String sexo = mSexo.getText().toString();
                    String descripcion = mDescripcion.getText().toString();
                    String edad = mEdad.getText().toString();
                    String tipo = mTipo.getText().toString();

                    Animal animal;
                    animal = new Animal(mAnimalId, ciudad, sexo, raza + " (Adoptado)", descripcion, edad, tipo, avatar, "1");

                    new AdoptarAnimalTask().execute(animal);
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private class AdoptarAnimalTask extends AsyncTask<Animal, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Animal... animals) {
            return mAnimalsDbHelper.updateAnimal(animals[0], mAnimalId) > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            showAnimalsScreen(result);
        }
    }

    private void showAnimalsScreen(Boolean requery) {
        if (!requery) {
            showAdoptarError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            showAdoptarExito();
            getActivity().setResult(Activity.RESULT_OK);
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showAdoptarError() {
        Toast.makeText(getActivity(),
                "Error al adoptar mascota", Toast.LENGTH_SHORT).show();
    }

    private void showAdoptadoError() {
        Toast.makeText(getActivity(),
                "La mascosta ya ha sido adoptada", Toast.LENGTH_SHORT).show();
    }

    private void showAdoptarExito() {
        Toast.makeText(getActivity(),
                "Mascota adoptada", Toast.LENGTH_SHORT).show();
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditAnimalActivity.class);
        intent.putExtra(AnimalsActivity.EXTRA_ANIMAL_ID, mAnimalId);
        startActivityForResult(intent, AnimalsFragment.REQUEST_UPDATE_DELETE_ANIMAL);
    }

    private void showAnimal(Animal animal) {
        mCollapsingView.setTitle(animal.getRaza());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + animal.getAvatar()))
                .centerCrop()
                .into(mAvatar);
        mCiudad.setText(animal.getCiudad());
        mSexo.setText(animal.getSexo());
        mDescripcion.setText(animal.getDescripcion());
        mEdad.setText(animal.getEdad());
        mTipo.setText(animal.getTipo());
        raza = animal.getRaza();
        avatar = animal.getAvatar();
        estado = animal.getEstado();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private class GetAnimalByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAnimalsDbHelper.getAnimalById(mAnimalId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showAnimal(new Animal(cursor));
            } else {
                showLoadError();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AnimalsFragment.REQUEST_UPDATE_DELETE_ANIMAL) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }
}
