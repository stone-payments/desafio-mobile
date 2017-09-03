//
//  ConfirmPurchasePresenter.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 31/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

final class ConfirmPurchasePresenter {
    
    fileprivate unowned let view: ConfirmPurchaseViewProtocol
    fileprivate let service: TransactionService
    fileprivate let db: TransactionDataManager
    
    init(view: ConfirmPurchaseViewProtocol) {
        self.view = view
        self.service = TransactionService()
        self.db = TransactionDataManager()
    }
}

// MARK: - Public methods -

extension ConfirmPurchasePresenter {

    func calculateTotalOrder() {
        
        let items: [Purchase] = (UIApplication.shared.delegate as! AppDelegate).purchaseItems
        var totalOrder: Int = 0
        
        for item: Purchase in items {
            totalOrder += item.price * item.quantity
        }
        
        self.view.showTotalOrder(with: totalOrder)
    }
    
    func validatePurchase(with order: Order) {
        
        var validateMessage: String = ""
        
        if order.cardNumber.characters.count < 16 {
            validateMessage.append("Número do cartão inválido\n")
        }
        
        if order.cardCVV.characters.count < 3 {
            validateMessage.append("Código segurança inválido\n")
        }
        
        if order.holderName.characters.count < 10 {
            validateMessage.append("Nome do titular inválido\n")
        }
        
        if order.expireDate.characters.count < 5 {
            validateMessage.append("Vencimento inválido\n")
        } else {
            
            let expireDate: [String] = order.expireDate.components(separatedBy: "/")
            
            if expireDate[0].characters.count < 2 {
                validateMessage.append("Mês do vencimento inválido\n")
            } else if Int(expireDate[0])! > 12 || Int(expireDate[0])! < 1 {
                validateMessage.append("Mês do vencimento inválido\n")
            }
            
            if expireDate[1].characters.count < 2 {
                validateMessage.append("Ano do vencimento inválido\n")
            }
        }
        
        
        if validateMessage.characters.count > 0 {
            self.view.showAlertError(with: "Erro", message: validateMessage, buttonTitle: "OK")
            return
        }

        self.postOrder(with: order)
    }
}

// MARK: - Private methods -

extension ConfirmPurchasePresenter {

    fileprivate func postOrder(with order: Order) {

        self.view.showLoading()
        
        let parameters: [String: Any] = [
            "card_number": order.cardNumber,
            "value": order.orderValue,
            "cvv": order.cardCVV,
            "card_holder_name": order.holderName,
            "exp_date": order.expireDate
        ]
        
        self.service.postTransaction(with: parameters, success: {
            result in
            self.saveOrderInDatabase(with: order)
        }, fail: {
            failure in
            self.requestError(errorDescription: failure.description)
        })
    }
    
    fileprivate func requestError(errorDescription: String) {
        self.view.hideLoading()
        self.view.showAlertError(with: "Erro",
                                 message: errorDescription,
                                 buttonTitle: "OK")
    }
    
    fileprivate func saveOrderInDatabase(with order: Order) {
        self.db.saveOrder(with: order)
        self.cleanCart()
        self.view.hideLoading()
    }
    
    fileprivate func cleanCart() {
        (UIApplication.shared.delegate as! AppDelegate).purchaseItems = []
        self.view.cleanBadge()
    }
}
