package com.hernand.starwars.model.dao;

import com.hernand.starwars.model.Transact;
import com.hernand.starwars.vo.TransactionVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nando on 28/05/2017.
 */

public class TransactDao {

    /**
     * Metodo responsavel por salvar a transacao
     * @param transacaoVO
     */
    public void saveTransaction(TransactionVO transacaoVO){
        Transact transacao = new Transact();
        transacao.setNumeroCartao(transacaoVO.getNumeroCartao().substring(transacaoVO.getNumeroCartao().length() - 4));
        transacao.setNomePortador(transacaoVO.getNomePortador());
        transacao.setDataHora(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        transacao.setValor(transacaoVO.getValor().toString());
        transacao.save();

    }

    /**
     * Metodo responsavel por buscar todos os registros de transacoes do historico.
     * @return
     */
    public List<Transact> listAll(){
        Iterator<Transact> transacaoIterator =  Transact.findAll(Transact.class);
        List<Transact> lista = new ArrayList<>();

        while(transacaoIterator.hasNext()){
            Transact t = transacaoIterator.next();

            lista.add(t);
        }
        return lista;
    }
}
