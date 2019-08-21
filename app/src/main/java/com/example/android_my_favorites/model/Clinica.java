package com.example.android_my_favorites.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Clinica implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String descricao;
    private String uniq_id;
    private String foto;
    private String id_estabelecimento;
    private String razao_social;
    private String nome_fantasia;
    private String cnpj_cpf;
    private String contato;
    private String telefone;
    private String celular;
    private String email;
    private String endereco;
    private String numero;
    private String bairro;
    private String id_cidade;
    private String id_estado;
    private String cep;
    private String nmcidade;
    private String nmestado;
    private String pais;
    private String status;
    private String segmento;
    private String beneficios;
    private String total_likes;


    //public Clinica() {}


    private Clinica(Parcel in) {
        descricao = in.readString();
        foto = in.readString();
        uniq_id = in.readString();
        id_estabelecimento = in.readString();
        razao_social = in.readString();
        nome_fantasia = in.readString();
        cnpj_cpf = in.readString();
        contato = in.readString();
        telefone = in.readString();
        celular = in.readString();
        email = in.readString();
        endereco = in.readString();
        numero = in.readString();
        bairro = in.readString();
        id_cidade = in.readString();
        id_estado = in.readString();
        cep = in.readString();
        nmcidade = in.readString();
        nmestado = in.readString();
        pais = in.readString();
        status = in.readString();
        segmento = in.readString();
        beneficios = in.readString();
        total_likes = in.readString();
    }

    public static final Creator<Clinica> CREATOR = new Creator<Clinica>() {
        @Override
        public Clinica createFromParcel(Parcel in) {
            return new Clinica(in);
        }

        @Override
        public Clinica[] newArray(int size) {
            return new Clinica[size];
        }
    };

    @Override
    public String toString() {
        return "Credenciadas{" +
                "descricao='" + descricao + '\'' +
                ", id='" + id + '\'' +
                ", id_estabelecimento='" + id_estabelecimento + '\'' +
                ", razao_social='" + razao_social + '\'' +
                ", nome_fantasia='" + nome_fantasia + '\'' +
                ", contato='" + contato + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUniq_id() {
        return uniq_id;
    }

    public void setUniq_id(String uniq_id) {
        this.uniq_id = uniq_id;
    }

    public String getId_estabelecimento() {
        return id_estabelecimento;
    }

    public void setId_estabelecimento(String id_estabelecimento) {
        this.id_estabelecimento = id_estabelecimento;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getCnpj_cpf() {
        return cnpj_cpf;
    }

    public void setCnpj_cpf(String cnpj_cpf) {
        this.cnpj_cpf = cnpj_cpf;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(String id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getId_estado() {
        return id_estado;
    }

    public void setId_estado(String id_estado) {
        this.id_estado = id_estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNmcidade() {
        return nmcidade;
    }

    public void setNmcidade(String nmcidade) {
        this.nmcidade = nmcidade;
    }

    public String getNmestado() {
        return nmestado;
    }

    public void setNmestado(String nmestado) {
        this.nmestado = nmestado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public String getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(String total_likes) {
        this.total_likes = total_likes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descricao);
        dest.writeString(foto);
        dest.writeString(uniq_id);
        dest.writeString(id_estabelecimento);
        dest.writeString(razao_social);
        dest.writeString(nome_fantasia);
        dest.writeString(cnpj_cpf);
        dest.writeString(contato);
        dest.writeString(telefone);
        dest.writeString(celular);
        dest.writeString(email);
        dest.writeString(endereco);
        dest.writeString(numero);
        dest.writeString(bairro);
        dest.writeString(id_cidade);
        dest.writeString(id_estado);
        dest.writeString(cep);
        dest.writeString(nmcidade);
        dest.writeString(nmestado);
        dest.writeString(pais);
        dest.writeString(status);
        dest.writeString(segmento);
        dest.writeString(beneficios);
        dest.writeString(total_likes);
    }
}
