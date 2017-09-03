//
//  OrderDataManager.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 03/09/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import Foundation
import RealmSwift

final class TransactionDataManager {
    fileprivate var db: Realm?
    init() {
        db = try! Realm()
    }
}

// MARK: - Public methods -

extension TransactionDataManager {
 
    func saveOrder(with order: Order) {
        guard let db = self.db else { return }
        try! db.write {
            db.add(TransactionRealm.parse(with: order))
        }
    }
    
    func getAllOrders() -> [Order] {
        guard let db = self.db else { return [Order]() }
        let orders = db.objects(TransactionRealm.self)
        return TransactionRealm.parse(with: orders)
    }
    
    func ordersExist() -> Bool {
        return getAllOrders().count == 0
    }
}
