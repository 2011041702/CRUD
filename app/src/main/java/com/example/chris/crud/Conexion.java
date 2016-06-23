package com.example.chris.crud;

/**
 * Created by Chris on 23/05/2016.
 */
public class Conexion {
    public static final String URL_GET_ALL = "http://192.168.160.220:8080/WebServicesPrueba/ListarProductos.php";
    public static final String URL_ADD_PRO= "http://192.168.160.220:8080/WebServicesPrueba/RegistrarProducto.php";



    //JSON Productos
    
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NOMBRE = "nombre";
    public static final String TAG_DESCRIPCION = "descripcion";
    public static final String TAG_CODIGOBARRA = "codigo_barra";
    public static final String TAG_ESTADO = "estado";


    public static final String KEY_PRO_NOMBRE = "nom";
    public static final String KEY_PRO_DESC = "desc";
    public static final String KEY_PRO_CODBA = "codba";
    public static final String KEY_PRO_EST = "est";






}
