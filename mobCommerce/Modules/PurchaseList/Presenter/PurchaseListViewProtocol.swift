//
//  PurchaseListViewProtocol.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 29/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

protocol PurchaseListViewProtocol : class {
    
    func reloadTableView()
    func reloadTableViewCell(at indexPath: IndexPath)
    func updateBadgeToValue(with value: String)
    func showAlertError(with title: String, message: String, buttonTitle: String)
}
