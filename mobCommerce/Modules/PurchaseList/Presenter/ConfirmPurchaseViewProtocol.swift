//
//  ConfirmPurchaseViewProtocol.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 31/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

protocol ConfirmPurchaseViewProtocol : class {
    
    func showLoading()
    func hideLoading()
    func cleanBadge()
    func showTotalOrder(with value: Int)
    func showAlertError(with title: String, message: String, buttonTitle: String)
}
