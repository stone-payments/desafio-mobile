package br.com.tiago.desafiomobile.interactor.impl;

import android.content.Context;

import java.util.Date;
import java.util.List;

import br.com.tiago.desafiomobile.interactor.TransactionInteractor;
import br.com.tiago.desafiomobile.model.TransactionModel;
import br.com.tiago.desafiomobile.repository.local.TransactionDao;
import br.com.tiago.desafiomobile.service.TransactionService;
import br.com.tiago.desafiomobile.service.impl.TransactionServiceImpl;
import br.com.tiago.desafiomobile.utils.FormatItemValue;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by TiagoCabral on 8/23/2017.
 */

public class TransactionInteractorImpl implements TransactionInteractor {

    private TransactionService transactionService;
    private TransactionDao transactionDao;

    public TransactionInteractorImpl(Context context) {
        this.transactionService = new TransactionServiceImpl();
        this.transactionDao = new TransactionDao(context);
    }

    @Override
    public Observable<Void> insertTransaction(TransactionModel transactionModel) {
        transactionModel.setValue((long) (transactionModel.getValueDouble() * 100));
        transactionModel.setDate(FormatItemValue.fateFormat(new Date()));
        transactionModel.setCardNumber(FormatItemValue.cardNumberFormat(transactionModel.getCardNumber()));
        transactionDao.insert(transactionModel);
        return transactionService.insertTransaction(transactionModel);
    }

    @Override
    public Observable<List<TransactionModel>> getTransactions() {
        return transactionDao.getTransactions().map(new Func1<List<TransactionModel>, List<TransactionModel>>() {
            @Override
            public List<TransactionModel> call(List<TransactionModel> transactionModels) {
                for (TransactionModel transactionModel : transactionModels) {
                    transactionModel.setValueDouble((double) transactionModel.getValue() / 100);
                }
                return transactionModels;
            }
        });
    }
}
