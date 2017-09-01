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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialize()
    }
}

// MARK: - UITexField -

extension ConfirmPurchaseTableViewController {
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        guard let text = textField.text else { return true }
        let newLength = text.characters.count + string.characters.count - range.length
        var maxLength = 50
        
        if textField == self.creditCardNumber {
            maxLength = 16
        } else if textField == self.expireCardNumber {
            maxLength = 5
        } else if textField == self.secureCodeNumber {
            maxLength = 3
        }
    
        let isMax = newLength <= maxLength
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
        self.totalValue.text = value.toCurrencyString
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
                                               orderValue: self.totalValue.text!,
                                               cardCVV: self.secureCodeNumber.text!,
                                               holderName: self.holderName.text!,
                                               expireDate: self.expireCardNumber.text!))
    }
}
