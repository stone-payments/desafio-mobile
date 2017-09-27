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
  
}

extension CartViewController: UITextFieldDelegate {
  
}
