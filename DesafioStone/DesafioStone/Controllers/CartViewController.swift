//
//  CartViewController.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 26/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import UIKit
import CoreData

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
  
  //MARK: - Button touches
  
  @IBAction func closeButtonTouched() {
    self.dismiss(animated: true, completion: nil)
  }
  
  @IBAction func confirmButtonTouched() {
    guard let cardNumber = self.cardNumberTextField.text,
      let totalAmount = self.totalAmountLabel.text,
      let cardHolder = self.cardNameTextField.text,
      let cvv = self.cardCVVTextField.text,
      let expDate = self.cardExpireDateTextField.text else {
      return
    }
    
    let transaction = PurchaseModel(cardNumber, totalAmount, cvv, cardHolder, expDate)
    CartAPI.shared.chekoutPurchase(transaction) {
      error in
      guard error == nil else {
        print("Requisição falhou")
        let alertMessage = UIAlertController(title: "Erro", message: "Desculpe-nos pelo erro. Não foi possível completar a compra.", preferredStyle: .alert)
        alertMessage.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        self.present(alertMessage, animated: true, completion: nil)
        return
      }
      
      if let amountString = self.totalAmountLabel.text,
        let cardHolderName = self.cardNameTextField.text,
        let cardDigits = self.cardNumberTextField.text {
        let lastCardDigit = "\(cardDigits.replacingOccurrences(of: " ", with: "").suffix(4))"
        
        self.saveTransaction(amountString, lastCardDigit, cardHolderName)
      }
      
      
      let alertMessage = UIAlertController(title: "Sucesso", message: "Compra completada. A seu caminho a compra agora está!", preferredStyle: .alert)
      alertMessage.addAction(UIAlertAction(title: "Voltar para a loja", style: .default, handler: { action in
        self.dismiss(animated: true, completion: nil)
      }))
      self.present(alertMessage, animated: true, completion: nil)
    }
  }
  
  //MARK: - TextFields formatting
  
  @IBAction func cardNumberTextChanged(_ sender: UITextField) {
    guard let currentText = sender.text else { return }
    sender.text = currentText.grouping(every: 4, with: " ")
  }
  
  @IBAction func expireDateTextChanged(_ sender: UITextField) {
    guard let currentText = sender.text else { return }
    sender.text = currentText.grouping(every: 2, with: "/")
  }
  
  //MARK: - CoreData
  
  func saveTransaction(_ amount: String, _ cardLastDigits: String, _ ownerName: String) {
    guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
        return
    }
    
    let managedContext = appDelegate.persistentContainer.viewContext
    let entity = NSEntityDescription.entity(forEntityName: "Transaction", in: managedContext)!
    let transactionData = NSManagedObject(entity: entity, insertInto: managedContext)
    
    transactionData.setValue(amount, forKeyPath: "amount")
    transactionData.setValue(ownerName, forKeyPath: "card_holder_name")
    transactionData.setValue(Date(), forKeyPath: "date")
    transactionData.setValue(cardLastDigits, forKeyPath: "card_last_digits")
    
    do {
      try managedContext.save()
    } catch let error as NSError {
      print("Could not save. \(error), \(error.userInfo)")
    }
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
