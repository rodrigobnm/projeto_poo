package br.gov.cesarschool.poo.daogenerico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class DAOSerializadorObjetos {
    private String nomeDiretorio;

    public DAOSerializadorObjetos(Class<?> tipoEntidade) {
        this.nomeDiretorio = "." + File.separator + tipoEntidade.getSimpleName();
        File dir = new File(nomeDiretorio);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public boolean incluir(Entidade entidade) {
        File file = new File(nomeDiretorio, entidade.getIdUnico());
        if (file.exists()) {
            return false;
        }
        entidade.setDataHoraInclusao(LocalDateTime.now());
        return salvarEntidade(file, entidade);
    }

    public boolean alterar(Entidade entidade) {
        File file = new File(nomeDiretorio, entidade.getIdUnico());
        if (!file.exists()) {
            return false;
        }
        entidade.setDataHoraUltimaAlteracao(LocalDateTime.now());
        return salvarEntidade(file, entidade);
    }

    public boolean excluir(String idUnico) {
        File file = new File(nomeDiretorio, idUnico);
        return file.delete();
    }

    public Entidade buscar(String idUnico) {
        File file = new File(nomeDiretorio, idUnico);
        if (!file.exists()) {
            return null;
        }
        return carregarEntidade(file);
    }

    public Entidade[] buscarTodos() {
        File dir = new File(nomeDiretorio);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return new Entidade[0];
        }
        List<Entidade> entidades = new ArrayList<>();
        for (File file : files) {
            Entidade entidade = carregarEntidade(file);
            if (entidade != null) {
                entidades.add(entidade);
            }
        }
        return entidades.toArray(new Entidade[0]);
    }

    private boolean salvarEntidade(File file, Entidade entidade) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(entidade);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Entidade carregarEntidade(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Entidade) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}