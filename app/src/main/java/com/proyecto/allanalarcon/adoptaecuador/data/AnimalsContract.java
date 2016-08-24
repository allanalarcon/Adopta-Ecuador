package com.proyecto.allanalarcon.adoptaecuador.data;

import android.provider.BaseColumns;

/**
 * Created by AllanAlarcon on 22/8/2016.
 */
public class AnimalsContract {
    public static abstract class AnimalEntry implements BaseColumns {
        public static final String TABLE_NAME ="animal";

        public static final String ID = "id";
        public static final String CIUDAD = "ciudad";
        public static final String SEXO = "sexo";
        public static final String RAZA = "raza";
        public static final String DESCRIPCION = "descripcion";
        public static final String EDAD = "edad";
        public static final String TIPO = "tipo";
        public static final String AVATAR = "avatar";
        public static final String ESTADO = "estado";
    }
}
