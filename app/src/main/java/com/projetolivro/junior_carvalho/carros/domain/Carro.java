package com.projetolivro.junior_carvalho.carros.domain;

/**
 * Created by Junior_Carvalho on 27/03/2016.
 */

// é recomendado usar Parcelable no lugar Serializable devido obter melhor performance
// Parcelable serializa com melhor performance que o Serializable
// Para nao precisar impelmentar os metodos referente a Parcelable
// entao adicionar
//compile 'org.parceler:parceler:1.0.4'
//compile 'org.parceler:parceler-api:1.0.4'
// na dependeicia
// feito isso passamos a notação @org.parceler.Parcel
// antes de iniciar a classe e nao precisamos implmentar Pacelable
//public class Carro implements Serializable {
//public class Carro implements Parcelable {
@org.parceler.Parcel
public class Carro {

    private static final long serialVersionUID = -647602086045131846L;
    public long id;
    public String tipo;
    public String nome;
    public String desc;
    public String urlFoto;
    public String urlIinfo;
    public String urlVideo;
    public String latitude;
    public String longitude;

    // flag para indicar que o carro está selecionado
    public boolean selected;

    @Override
    public String toString() {
        return "Carro{" + "nome='" + nome + '\'' + '}';
    }
/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // escreve os dados para serem serialziados
        dest.writeLong(id);
        dest.writeString(this.tipo);
        dest.writeString(this.nome);
        dest.writeString(this.desc);
        dest.writeString(this.urlfoto);
        dest.writeString(this.urlIinfo);
        dest.writeString(this.urlVideo);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    public void readFromParcel(Parcel parcel) {
        // lê os dados na mesma ordem em que foram escritos

        this.id = parcel.readLong();
        this.tipo = parcel.readString();
        this.nome = parcel.readString();
        this.desc = parcel.readString();
        this.urlfoto = parcel.readString();
        this.urlIinfo = parcel.readString();
        this.urlVideo = parcel.readString();
        this.latitude = parcel.readString();
        this.longitude = parcel.readString();

    }

    public static final Parcelable.Creator<Carro> CREATOR = new Parcelable.Creator<Carro>() {

        @Override
        public Carro createFromParcel(Parcel p) {
            Carro c = new Carro();
            c.readFromParcel(p);
            return c;
        }

        @Override
        public Carro[] newArray(int size) {
            return new Carro[size];
        }
    };
*/

}
