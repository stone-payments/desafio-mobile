//
//  ProductsListViewProtocol.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 27/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

protocol ProductsListViewProtocol : class {
    
    func showLoading()
    func hideLoading()
    func reloadCollectionView()
    func addItem(at: IndexPath)
    func showAlertError(with title: String, message: String, buttonTitle: String)
}
