//
//  OrdersListPresenter.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 03/09/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

final class OrdersListPresenter {
    
    fileprivate unowned let view: OrdersListViewProtocol
    fileprivate let db: TransactionDataManager
    fileprivate(set) var orders: [Order] = []
    
    init(view: OrdersListViewProtocol) {
        self.view = view
        self.db = TransactionDataManager()
    }
}

// MARK: - Public methods -

extension OrdersListPresenter {

    func loadHistoryData() {
        self.view.showLoading()
        orders.append(contentsOf: self.db.getAllOrders())
        self.view.hideLoading()
    }
}
