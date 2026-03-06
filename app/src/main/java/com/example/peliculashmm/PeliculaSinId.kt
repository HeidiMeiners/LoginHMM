package com.example.peliculashmm

class PeliculaSinId() {
    var Nombre: String?=null
    var Genero: String?=null
    var Anio: String?=null

    constructor(Nombre:String?, Anio:String?, Genero:String?) : this(){
        this.Nombre = Nombre
        this.Anio = Anio
        this.Genero = Genero
    }
}