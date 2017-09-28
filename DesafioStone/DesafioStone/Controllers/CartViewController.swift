//
//  CartViewController.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 26/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import UIKit

class CartViewController: UIViewController {
  
  @IBOutlet weak var totalAmountLabel: UILabel!
  
  @IBOutlet weak var cardNumberTextField: UITextField!
  @IBOutlet weak var cardNameTextField: UITextField!
  @IBOutlet weak var cardExpireDateTextField: UITextField!
  @IBOutlet weak var cardCVVTextField: UITextField!
  
  var totalAmount: String?
  
  override func viewDidLoad() {
    super.viewDidLoad()
    
    if let totalAmountString = self.totalAmount {
      self.totalAmountLabel.text = totalAmountString
    }
    // Do any additional setup after loading the view.
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
    // Dispose of any resources that can be recreated.
  }
  
  @IBAction func closeButtonTouched() {
    self.dismiss(animated: true, completion: nil)
  }
  
  @IBAction func confirmButtonTouched() {
  }
  
  @IBAction func cardNumberTextChanged(_ sender: UITextField) {
    guard let currentText = sender.text else { return }
    sender.text = currentText.grouping(every: 4, with: " ")
  }
  
  @IBAction func expireDateTextChanged(_ sender: UITextField) {
    guard let currentText = sender.text else { return }
    sender.text = currentText.grouping(every: 2, with: "/")
  }
}

extension CartViewController: UITextFieldDelegate {
  
  func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {

    if textField == self.cardNumberTextField {
      if textField.text!.characters.count <= 19 && string == "" {
        return true
      } else if textField.text!.characters.count < 19 && string != "" {
        return true
      } else {
        return false
      }
    } else if textField == self.cardCVVTextField {
      if textField.text!.characters.count <= 3 && string == "" {
        return true
      } else if textField.text!.characters.count < 3 && string != "" {
        return true
      } else {
        return false
      }
    } else if textField == self.cardExpireDateTextField {
      if textField.text!.characters.count <= 5 && string == "" {
        return true
      } else if textField.text!.characters.count < 5 && string != "" {
        return true
      } else {
        return false
      }
    }
    return true
  }
  
  
}
