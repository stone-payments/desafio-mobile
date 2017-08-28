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
    
    func buyItem(product: Product) {
        
        let item = Purchase(title: product.title, price: product.price, thumbnail: product.thumbnail,
                            quantity: 1, total: (product.price * 1))
        
        (UIApplication.shared.delegate as! AppDelegate).purchaseItems.append(item)
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
}
