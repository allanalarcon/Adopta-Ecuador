package com.proyecto.allanalarcon.adoptaecuador.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by AllanAlarcon on 22/8/2016.
 */
public class AnimalsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Animal.db";

    public AnimalsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AnimalsContract.AnimalEntry.TABLE_NAME + " ("
                + AnimalsContract.AnimalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AnimalsContract.AnimalEntry.ID + " TEXT NOT NULL,"
                + AnimalsContract.AnimalEntry.CIUDAD + " TEXT NOT NULL,"
                + AnimalsContract.AnimalEntry.SEXO + " TEXT NOT NULL,"
                + AnimalsContract.AnimalEntry.RAZA + " TEXT NOT NULL,"
                + AnimalsContract.AnimalEntry.DESCRIPCION + " TEXT NOT NULL,"
                + AnimalsContract.AnimalEntry.EDAD + " TEXT NOT NULL,"
                + AnimalsContract.AnimalEntry.TIPO + " TEXT NOT NULL,"
                + AnimalsContract.AnimalEntry.AVATAR + " TEXT,"
                + AnimalsContract.AnimalEntry.ESTADO + " TEXT NOT NULL,"
                + "UNIQUE (" + AnimalsContract.AnimalEntry.ID + "))");

        // Insertar datos iniciales
        mockData(db);
        upgrade_2(db);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockAnimal(sqLiteDatabase, new Animal("Esmeraldas", "Macho",
                "Labrador", "Buen estado", "Mediano",
                "Perro", "labrador.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Esmeraldas", "Hembra",
                "Chihuahua", "Pelaje en mal estado", "Cachorro",
                "Perro", "chihuahua.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Esmeraldas", "Macho",
                "Danés", "Pata herida", "Adulto",
                "Perro", "danes.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Esmeraldas", "Hembra",
                "Toyger", "Buen estado", "Adulto",
                "Gato", "toyger.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Esmeraldas", "Macho",
                "Siamés", "Desnutrición", "Mediano",
                "Gato", "siames.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Manta", "Macho",
                "Mudi", "Desnutrición", "Mediano",
                "Perro", "mudi.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Manta", "Hembra",
                "Mastin", "Pata fracturada", "Mediano",
                "Perro", "mastin.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Manta", "Hembra",
                "Persa", "Buen estado", "Cachorro",
                "Gato", "persa.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Chone", "Macho",
                "Boxer", "Desnutrición", "Mediano",
                "Perro", "boxer.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Chone", "Macho",
                "Pitbull", "Ciego", "Adulto",
                "Perro", "pitbull.jpg"));
        mockAnimal(sqLiteDatabase, new Animal("Chone", "Hembra",
                "Maine Coon", "Sin orejas", "Mediano",
                "Gato", "mainecoon.jpg"));
    }

    public long mockAnimal(SQLiteDatabase db, Animal animal) {
        return db.insert(
                AnimalsContract.AnimalEntry.TABLE_NAME,
                null,
                animal.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2)
        {
            upgrade_2(db);
        }
    }

    private void upgrade_2(SQLiteDatabase db)
    {
        //
        // Upgrade versión 2: definir algunos datos de ejemplo
        //
        Animal animal = new Animal("Chone", "Hembra",
                "Bengala", "Buen estado", "Adulto",
                "Gato", "bengala.jpg");
        db.insert(AnimalsContract.AnimalEntry.TABLE_NAME,
                null,
                animal.toContentValues());

        Log.i(this.getClass().toString(), "Actualización versión 2 finalizada");
    }

    public Cursor getAllAnimals() {
        return getReadableDatabase().query(
                        AnimalsContract.AnimalEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }


    public int updateAnimal(Animal animal, String animalId) {
        return getWritableDatabase().update(
                AnimalsContract.AnimalEntry.TABLE_NAME,
                animal.toContentValues(),
                AnimalsContract.AnimalEntry.ID + " LIKE ?",
                new String[]{animalId}
        );
    }

    public Cursor getAnimalById(String animalId) {
        Cursor c = getReadableDatabase().query(
                AnimalsContract.AnimalEntry.TABLE_NAME,
                null,
                AnimalsContract.AnimalEntry.ID + " LIKE ?",
                new String[]{animalId},
                null,
                null,
                null);
        return c;
    }

    public long saveAnimal(Animal animal) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                AnimalsContract.AnimalEntry.TABLE_NAME,
                null,
                animal.toContentValues());

    }
}
