//
//  PurchaseListPresenter.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 29/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

final class PurchaseListPresenter {
    
    fileprivate unowned let view: ProductsListViewProtocol
    fileprivate let service: ProductService
    fileprivate(set) var products: [Product] = []
    
    init(view: ProductsListViewProtocol) {
        self.view = view
        self.service = ProductService()
    }
}

// MARK: - Public methods -
