//
//  PurchaseListPresenter.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 29/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

final class PurchaseListPresenter {
    
    fileprivate unowned let view: PurchaseListViewProtocol
    fileprivate let service: ProductService
    
    init(view: PurchaseListViewProtocol) {
        self.view = view
        self.service = ProductService()
    }
}

// MARK: - Public methods -

extension PurchaseListPresenter {
    
    func removeItem(with item: Purchase, at index: Int) {
        
        if itemPurchased(with: item.title) {
            (UIApplication.shared.delegate as! AppDelegate).purchaseItems
                .remove(at: self.indexItemPurchased(with: item.title))
        }
        
        self.view.updateBadgeToValue(with: "\(self.quantityItems())")
        self.view.reloadTableView()
    }
    
    func itemPurchased(with product: String) -> Bool {
        
        if (UIApplication.shared.delegate as! AppDelegate).purchaseItems.contains(where: { $0.title == product }) {
            return true
        }
        return false
    }
    
    func calculateOrder(with quantity: Int, at index: Int) {
        
        var item: Purchase = (UIApplication.shared.delegate as! AppDelegate).purchaseItems[index]
        item.quantity = quantity
        item.total = item.price * quantity
        
        (UIApplication.shared.delegate as! AppDelegate).purchaseItems[index] = item
        
        self.view.reloadTableView()
    }
    
}

// MARK: - Private methods -

extension PurchaseListPresenter {
    
    fileprivate func quantityItems() -> Int {
        return (UIApplication.shared.delegate as! AppDelegate).purchaseItems.count
    }
    
    fileprivate func indexItemPurchased(with value: String) -> Int {
        return (UIApplication.shared.delegate as! AppDelegate).purchaseItems.index(where: { $0.title == value})!
    }
}
