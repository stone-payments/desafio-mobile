//
//  ProductsListPresenter.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 27/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

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
        
        self.service.getProducts(success: {
            result in
            self.products = result
            self.view.reloadCollectionView()
        }, fail: {
            failure in
            self.view.showAlertError(with: "Erro",
                                     message: failure.description,
                                     buttonTitle: "OK")
        })
    }
}

// MARK: - Private methods -

extension ProductsListPresenter {
    
    
}
