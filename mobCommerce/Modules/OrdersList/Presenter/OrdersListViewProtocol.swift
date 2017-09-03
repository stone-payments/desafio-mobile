//
//  OrdersListViewProtocol.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 03/09/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

protocol OrdersListViewProtocol : class {
    
    func showLoading()
    func hideLoading()
    func reloadTableView()
    func showAlertError(with title: String, message: String, buttonTitle: String)
}
