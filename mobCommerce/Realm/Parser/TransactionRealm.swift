//
//  TransactionRealm.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 03/09/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import Foundation
import RealmSwift

class TransactionRealm: Object {
    dynamic var orderValue = 0
    dynamic var orderDate = Date(timeIntervalSince1970: 1)
    dynamic var creditCardLastNumbers = ""
    dynamic var creditCardHolderName = ""
}

// MARK: - Parsers -

extension TransactionRealm {
    
    static func parse(with order: Order) -> TransactionRealm {
        
        let transaction = TransactionRealm()
        transaction.orderValue = order.orderValue
        transaction.orderDate = order.orderDate
        transaction.creditCardHolderName = order.holderName
        transaction.creditCardLastNumbers = order.cardNumber.substring(from: order.cardNumber.index(order.cardNumber.endIndex, offsetBy: -4))
        
        return transaction
    }
    
    static func parse(with transactionRealm: RealmSwift.Results<TransactionRealm>) -> [Order] {
        
        return transactionRealm.flatMap { Order(cardNumber: $0.creditCardLastNumbers,
                                                orderValue: $0.orderValue,
                                                cardCVV: "",
                                                holderName: $0.creditCardHolderName,
                                                expireDate: "",
                                                orderDate: $0.orderDate) }
    }
}
