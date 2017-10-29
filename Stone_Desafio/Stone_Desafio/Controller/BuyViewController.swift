//
//  BuyViewController.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 27/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import UIKit

class BuyViewController: UIViewController{
    
    // Outlets
    @IBOutlet weak var transactionValue: UILabel!
    @IBOutlet weak var cardNumber: UITextField!
    @IBOutlet weak var cardholderName: UITextField!
    @IBOutlet weak var expirationDate: UITextField!
    @IBOutlet weak var securityCode: UITextField!
    @IBOutlet weak var scrollView: UIScrollView!
    
    var alert: UIAlertController?
    var isAlertOn: Bool = false
    var cardNumberOk: Bool = false
    var cardHolderNameOk: Bool = false
    var expirationDateOk: Bool = false
    var securityCodeOk: Bool = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Keyboard configuration
        self.hideKeyboardWhenTappedAround()
        
        // Scroll View configuration
        scrollView.contentSize = CGSize(width: self.view.frame.width, height: 2 * self.view.frame.height)
        
        transactionValue.text = "Valor total da compra: \(priceToCurrency(price: Cart.sharedInstance.priceOfItems))"
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func finishBuyButton(_ sender: Any) {
        checkTextFieldsAndSendRequest()
    }
    
    // Function to check if text fields follow the patterns and are not empty
    func checkTextFieldsAndSendRequest()  {
        
        // Check if there are items to be bought
        if (Cart.sharedInstance.priceOfItems == 0) {
            if (!isAlertOn) {
                alert = UIAlertController(title: "Você não possui itens no carrinho", message: "Por favor, adicione itens para a compra", preferredStyle: .alert)
                alert!.addAction(UIAlertAction(title: "OK", style: .default, handler: {(alert: UIAlertAction!) in self.isAlertOn = false })
                )
                self.present(alert!, animated: true, completion: nil)
                return
            }
        }
        
        // Check if card number is empty or smaller than 16 digits
        let cardNumberC = cardNumber.text?.characters.count as! Int
        if ((cardNumber.text?.isEmpty)! || cardNumberC > 16) {
            if (!isAlertOn) {
                alert = UIAlertController(title: "Erro no número do cartão", message: "Por favor, reveja o campo", preferredStyle: .alert)
                alert!.addAction(UIAlertAction(title: "OK", style: .default, handler: {(alert: UIAlertAction!) in self.isAlertOn = false })
                )
                self.present(alert!, animated: true, completion: nil)
                return
                
            }
        }
        // Check if cardholder name is empty
        if (cardholderName.text?.isEmpty)! {
            if (!isAlertOn) {
                alert = UIAlertController(title: "Erro no nome do portador do cartão", message: "Por favor, reveja o campo", preferredStyle: .alert)
                alert!.addAction(UIAlertAction(title: "OK", style: .default, handler: {(alert: UIAlertAction!) in self.isAlertOn = false })
                )
                self.present(alert!, animated: true, completion: nil)
                return
            }
        }
        
        // Check if expiration date is empty, smaller than 5 digits or doesn't contain "/"
        let expirationDateC = expirationDate.text?.characters.count as! Int
        if (expirationDateC != 5 || !(expirationDate.text?.contains("/"))!) {
            if (!isAlertOn) {
                alert = UIAlertController(title: "Erro na data de validade", message: "Por favor, reveja o campo", preferredStyle: .alert)
                alert!.addAction(UIAlertAction(title: "OK", style: .default, handler: {(alert: UIAlertAction!) in self.isAlertOn = false })
                )
                self.present(alert!, animated: true, completion: nil)
                return
            }
        }
        
        // Check if security code is different than 3 digits or is not an Int
        let securityCodeC = securityCode.text?.characters.count as! Int
        if (securityCodeC != 3 || !isStringAnInt(string: securityCode.text!)) {
            if (!isAlertOn) {
                alert = UIAlertController(title: "Erro no CVV", message: "Por favor, reveja o campo", preferredStyle: .alert)
                alert!.addAction(UIAlertAction(title: "OK", style: .default, handler: {(alert: UIAlertAction!) in self.isAlertOn = false })
                )
                self.present(alert!, animated: true, completion: nil)
                return
            }
        }
        sendPostRequest()
    }
    
    // Function to check if String is convertible to Int
    func isStringAnInt(string: String) -> Bool {
        return Int(string) != nil
    }
    
    // Function to send post request to Apiary URL
    func sendPostRequest() {
        do {
        try DispatchQueue.main.async {
            let url = URL(string: "https://private-eba37a-stonemobileblueprint.apiary-mock.com/transactions")!
            var request = URLRequest(url: url)
            request.httpMethod = "POST"
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            
            request.httpBody = "{\n  \"card_number\": \(String(describing: self.cardNumber.text)),\n  \"value\": \(String(describing: self.transactionValue.text)),\n  \"cvv\": \(String(describing: self.securityCode.text)),\n  \"card_holder_name\": \"\(String(describing: self.cardholderName.text))\",\n  \"exp_date\": \"\(String(describing: self.expirationDate.text))\"\n}".data(using: .utf8)
            
            let task = URLSession.shared.dataTask(with: request) { data, response, error in
                if let response = response, let data = data {
                    print(response)
                    print(String(data: data, encoding: .utf8))
                    self.alert = UIAlertController(title: "Sua compra foi concluída!", message: "Espere seu produto dentro de alguns dias", preferredStyle: .alert)
                    self.alert!.addAction(UIAlertAction(title: "OK", style: .default, handler: {(alert: UIAlertAction!) in self.saveInManagerAndPlist() })
                    )
                    self.present(self.alert!, animated: true, completion: nil)
                    
                } else {
                    print(error)
                    self.alert = UIAlertController(title: "Houve um erro na compra", message: "Por favor, tente novamente mais tarde", preferredStyle: .alert)
                    self.alert!.addAction(UIAlertAction(title: "OK", style: .default, handler: nil)
                    )
                    self.present(self.alert!, animated: true, completion: nil)
                }
            }
            
            task.resume()
        }
        } catch {
            self.alert = UIAlertController(title: "Houve um erro na compra", message: "Por favor, tente novamente mais tarde", preferredStyle: .alert)
            self.alert!.addAction(UIAlertAction(title: "OK", style: .default, handler: nil)
            )
            self.present(self.alert!, animated: true, completion: nil)
        }
    }
    
    // Function to save transaction to Transaction Manager Singleton and to Plist (Local DB)
    func saveInManagerAndPlist() {
        let dateTime = Date()
        
        let value = Cart.sharedInstance.priceOfItems
        
        let last4digits = cardNumber.text?.substring(from: (cardNumber.text?.index((cardNumber.text?.endIndex)!, offsetBy: -4))!)
        print("Last 4 digits: ", last4digits)
        
        let cardholder = cardholderName.text
        
        TransactionsManager.sharedInstance.addAndSaveTransactions(transaction: Transactions(value: value, dateTime: dateTime, last4Digits: last4digits!, cardholderName: cardholder!))
    }
    
    
}
