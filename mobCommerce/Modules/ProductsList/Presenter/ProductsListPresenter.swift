//
//  ProductsListPresenter.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 27/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

final class ProductsListPresenter {
    
    fileprivate unowned let view: ProductsListViewProtocol
    fileprivate let service: ProductService
    fileprivate(set) var products: [Product] = []
    
    init(view: ProductsListViewProtocol) {
        self.view = view
        self.service = ProductService()
    }
}

// MARK: - Public methods -

extension ProductsListPresenter {
    
    func showProductsList() {
        self.view.showLoading()
        self.service.getProducts(success: {
            result in
            self.products = result
            self.showView()
        }, fail: {
            failure in
            self.requestError(errorDescription: failure.description)
        })
    }
    
    func buyItem(with product: Product, at index: Int) {
        
        if !itemPurchased(with: product.title) {
            let item = Purchase(title: product.title, price: product.price,
                                thumbnail: product.thumbnail,
                                quantity: 1, total: (product.price * 1))
            
            (UIApplication.shared.delegate as! AppDelegate).purchaseItems.append(item)
        }
        else {
            (UIApplication.shared.delegate as! AppDelegate).purchaseItems
                .remove(at: self.indexItemPurchased(with: product.title))
        }
        
        self.view.updateBadgeToValue(with: "\(self.quantityItems())")
        self.view.reloadCollectionViewCell(at: IndexPath.init(row: index, section: 0))
    }
    
    func itemPurchased(with product: String) -> Bool {
        
        if (UIApplication.shared.delegate as! AppDelegate).purchaseItems.contains(where: { $0.title == product }) {
            return true
        }
        return false
    }

}

// MARK: - Private methods -

extension ProductsListPresenter {
    
    fileprivate func showView() {
        self.view.hideLoading()
        self.view.reloadCollectionView()
    }
    
    fileprivate func requestError(errorDescription: String) {
        self.view.hideLoading()
        self.view.showAlertError(with: "Erro",
                                 message: errorDescription,
                                 buttonTitle: "OK")
    }
    
    fileprivate func quantityItems() -> Int {
        return (UIApplication.shared.delegate as! AppDelegate).purchaseItems.count
    }
    
    fileprivate func indexItemPurchased(with value: String) -> Int {
        return (UIApplication.shared.delegate as! AppDelegate).purchaseItems.index(where: { $0.title == value})!
    }
}
