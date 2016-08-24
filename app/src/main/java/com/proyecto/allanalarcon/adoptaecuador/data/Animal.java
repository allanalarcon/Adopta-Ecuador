package com.proyecto.allanalarcon.adoptaecuador.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

/**
 * Created by AllanAlarcon on 22/8/2016.
 */
public class Animal {
    private String id;
    private String ciudad;
    private String sexo;
    private String raza;
    private String descripcion;
    private String edad;
    private String tipo;
    private String avatar;
    private String estado;

    //Constructor
    public Animal(String ciudad, String sexo, String raza, String descripcion, String edad, String tipo, String avatar)
    {
        this.id = UUID.randomUUID().toString();
        this.ciudad = ciudad;
        this.sexo = sexo;
        this.raza = raza;
        this.descripcion = descripcion;
        this.edad = edad;
        this.tipo = tipo;
        this.avatar = avatar;
        this.estado = "0";
    }

    public Animal(String id, String ciudad, String sexo, String raza, String descripcion, String edad, String tipo, String avatar)
    {
        this.id = id;
        this.ciudad = ciudad;
        this.sexo = sexo;
        this.raza = raza;
        this.descripcion = descripcion;
        this.edad = edad;
        this.tipo = tipo;
        this.avatar = avatar;
        this.estado = "0";
    }

    //Constructor
    public Animal(String id, String ciudad, String sexo, String raza, String descripcion, String edad, String tipo, String avatar, String estado)
    {
        this.id = id;
        this.ciudad = ciudad;
        this.sexo = sexo;
        this.raza = raza;
        this.descripcion = descripcion;
        this.edad = edad;
        this.tipo = tipo;
        this.avatar = avatar;
        this.estado = estado;
    }

    public Animal(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(AnimalsContract.AnimalEntry.ID));
        ciudad = cursor.getString(cursor.getColumnIndex(AnimalsContract.AnimalEntry.CIUDAD));
        sexo = cursor.getString(cursor.getColumnIndex(AnimalsContract.AnimalEntry.SEXO));
        raza = cursor.getString(cursor.getColumnIndex(AnimalsContract.AnimalEntry.RAZA));
        descripcion = cursor.getString(cursor.getColumnIndex(AnimalsContract.AnimalEntry.DESCRIPCION));
        edad = cursor.getString(cursor.getColumnIndex(AnimalsContract.AnimalEntry.EDAD));
        tipo = cursor.getString(cursor.getColumnIndex(AnimalsContract.AnimalEntry.TIPO));
        avatar = cursor.getString(cursor.getColumnIndex(AnimalsContract.AnimalEntry.AVATAR));
        estado = cursor.getString(cursor.getColumnIndex(AnimalsContract.AnimalEntry.ESTADO));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AnimalsContract.AnimalEntry.ID, id);
        values.put(AnimalsContract.AnimalEntry.CIUDAD, ciudad);
        values.put(AnimalsContract.AnimalEntry.SEXO, sexo);
        values.put(AnimalsContract.AnimalEntry.RAZA, raza);
        values.put(AnimalsContract.AnimalEntry.DESCRIPCION, descripcion);
        values.put(AnimalsContract.AnimalEntry.EDAD, edad);
        values.put(AnimalsContract.AnimalEntry.TIPO, tipo);
        values.put(AnimalsContract.AnimalEntry.AVATAR, avatar);
        values.put(AnimalsContract.AnimalEntry.ESTADO, estado);
        return values;
    }

    //Getters
    public String getCiudad()
    {
        return this.ciudad;
    }

    public String getSexo()
    {
        return this.sexo;
    }

    public String getRaza()
    {
        return this.raza;
    }

    public String getDescripcion()
    {
        return this.descripcion;
    }

    public String getEdad()
    {
        return this.edad;
    }

    public String getTipo()
    {
        return this.tipo;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public String getEstado()
    {
        return estado;
    }

    //Setters
    public void setCiudad(String ciudad)
    {
        this.ciudad = ciudad;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public void setSexo(String sexo)
    {
        this.sexo = sexo;
    }

    public void setRaza(String raza)
    {
        this.raza = raza;
    }

    public void setEdad(String edad)
    {
        this.edad = edad;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }
}
