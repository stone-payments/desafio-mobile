//
//  ConfirmPurchaseTableViewController.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 30/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit
import SVProgressHUD

class ConfirmPurchaseTableViewController: UITableViewController, UITextFieldDelegate {

    @IBOutlet weak var creditCardNumber: UITextField!
    @IBOutlet weak var expireCardNumber: UITextField!
    @IBOutlet weak var secureCodeNumber: UITextField!
    @IBOutlet weak var holderName: UITextField!
    @IBOutlet weak var totalValue: UILabel!
    @IBOutlet weak var confirmButton: UIButton!
    
    var presenter: ConfirmPurchasePresenter!
    var orderValue: Int = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialize()
    }
}

// MARK: - UITexField -

extension ConfirmPurchaseTableViewController {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        guard var text = textField.text else { return true }
        let newLength = text.characters.count + string.characters.count - range.length
        var isMax = false
        
        switch textField {
        case self.creditCardNumber:
            if newLength <= 16 {
                isMax = true
            }
        case self.expireCardNumber:
            if newLength <= 5 {
                isMax = true
                if range.location == 2 && newLength > 2 {
                    text.append("/")
                    textField.text = text
                }
            }
        case self.secureCodeNumber:
            if newLength <= 3 {
                isMax = true
            }
        default:
            if newLength <= 50 {
                isMax = true
            }
        }

        return isMax
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        
    }
}

// MARK: - Protocol methods -

extension ConfirmPurchaseTableViewController: ConfirmPurchaseViewProtocol {
    
    func showLoading() {
        SVProgressHUD.show()
    }
    
    func hideLoading() {
        SVProgressHUD.dismiss()
    }
    
    func showTotalOrder(with value: Int) {
        orderValue = value
        self.totalValue.text = value.toCurrencyString
    }
    
    func cleanBadge() {
        self.navigationController?.tabBarController?.tabBar.items![1].badgeValue = nil
        self.navigationController?.tabBarController?.selectedIndex = 2
    }
    
    func showAlertError(with title: String, message: String, buttonTitle: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: buttonTitle, style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}

// MARK: - Private methods -

extension ConfirmPurchaseTableViewController {
    
    fileprivate func initialize() {
        presenter = ConfirmPurchasePresenter(view: self)
        presenter.calculateTotalOrder()
    }
}

// MARK: - Action methods -

extension ConfirmPurchaseTableViewController {
    
    @IBAction func confirmTapped(_ sender: UIButton) {
        self.view.endEditing(true)
        presenter.validatePurchase(with: Order(cardNumber: self.creditCardNumber.text!,
                                               orderValue: orderValue,
                                               cardCVV: self.secureCodeNumber.text!,
                                               holderName: self.holderName.text!,
                                               expireDate: self.expireCardNumber.text!,
                                               orderDate: Date() ))
    }
}
