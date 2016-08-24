package com.proyecto.allanalarcon.adoptaecuador.AddEditAnimal;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.proyecto.allanalarcon.adoptaecuador.R;
import com.proyecto.allanalarcon.adoptaecuador.data.Animal;
import com.proyecto.allanalarcon.adoptaecuador.data.AnimalsDbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditAnimalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditAnimalFragment extends Fragment {
    private static final String ARG_ANIMAL_ID = "arg_animal_id";

    private String mAnimalId;
    private String avatar;

    private AnimalsDbHelper mAnimalsDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mRazaField;
    private TextInputEditText mCiudadField;
    private TextInputEditText mSexoField;
    private TextInputEditText mDescripcionField;
    private TextInputEditText mEdadField;
    private TextInputEditText mTipoField;

    private TextInputLayout mRazaLabel;
    private TextInputLayout mCiudadLabel;
    private TextInputLayout mSexoLabel;
    private TextInputLayout mDescripcionLabel;
    private TextInputLayout mEdadLabel;
    private TextInputLayout mTipoLabel;


    public AddEditAnimalFragment() {
        // Required empty public constructor
    }

    public static AddEditAnimalFragment newInstance(String animalId) {
        AddEditAnimalFragment fragment = new AddEditAnimalFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_animal, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        mRazaField = (TextInputEditText) root.findViewById(R.id.et_raza);
        mCiudadField = (TextInputEditText) root.findViewById(R.id.et_ciudad);
        mSexoField = (TextInputEditText) root.findViewById(R.id.et_sexo);
        mDescripcionField = (TextInputEditText) root.findViewById(R.id.et_descripcion);
        mEdadField = (TextInputEditText) root.findViewById(R.id.et_edad);
        mTipoField = (TextInputEditText) root.findViewById(R.id.et_tipo);

        mRazaLabel = (TextInputLayout) root.findViewById(R.id.til_raza);
        mCiudadLabel = (TextInputLayout) root.findViewById(R.id.til_ciudad);
        mSexoLabel = (TextInputLayout) root.findViewById(R.id.til_sexo);
        mDescripcionLabel = (TextInputLayout) root.findViewById(R.id.til_descripcion);
        mEdadLabel = (TextInputLayout) root.findViewById(R.id.til_edad);
        mTipoLabel = (TextInputLayout) root.findViewById(R.id.til_tipo);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditAnimal();
            }
        });

        mAnimalsDbHelper = new AnimalsDbHelper(getActivity());

        // Carga de datos
        if (mAnimalId != null) {
            loadAnimal();
        }

        return root;
    }

    private void loadAnimal() {
        new GetAnimalByIdTask().execute();
    }

    private class AddEditAnimalTask extends AsyncTask<Animal, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Animal... animals) {
            if (mAnimalId != null) {
                return mAnimalsDbHelper.updateAnimal(animals[0], mAnimalId) > 0;

            } else {
                return mAnimalsDbHelper.saveAnimal(animals[0]) > 0;
            }

        }


        @Override
        protected void onPostExecute(Boolean result) {
            showAnimalsScreen(result);
        }
    }

    private void showAnimalsScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void addEditAnimal() {
        boolean error = false;

        String raza = mRazaField.getText().toString();
        String ciudad = mCiudadField.getText().toString();
        String sexo = mSexoField.getText().toString();
        String descripcion = mDescripcionField.getText().toString();
        String edad = mEdadField.getText().toString();
        String tipo = mTipoField.getText().toString();

        if (TextUtils.isEmpty(raza)) {
            mRazaLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(ciudad)) {
            mCiudadLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(sexo)) {
            mSexoLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(descripcion)) {
            mDescripcionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(edad)) {
            mEdadLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(tipo)) {
            mTipoLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Animal animal;
        if (mAnimalId != null)
        {
            animal = new Animal(mAnimalId, ciudad, sexo, raza, descripcion, edad, tipo, avatar);
        }
        else animal = new Animal(ciudad, sexo, raza, descripcion, edad, tipo, " ");

        new AddEditAnimalTask().execute(animal);

    }

    private void showAnimal(Animal animal) {
        mRazaField.setText(animal.getRaza());
        mCiudadField.setText(animal.getCiudad());
        mSexoField.setText(animal.getSexo());
        mDescripcionField.setText(animal.getDescripcion());
        mEdadField.setText(animal.getEdad());
        mTipoField.setText(animal.getTipo());
        avatar = animal.getAvatar();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar mascota", Toast.LENGTH_SHORT).show();
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
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }
}
