//
//  TransactionViewController.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import UIKit

class TransactionViewController: UIViewController {
    @IBOutlet weak var txtCardNum: UITextField!
    @IBOutlet weak var txtCardName: UITextField!
    @IBOutlet weak var txtExpDate: UITextField!
    @IBOutlet weak var txtCvv: UITextField!
    
    var card : Card = Card()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func buy(_ sender: UIBarButtonItem) {
        self.setCard()
        CartManager.shared.buyCart(card: self.card)
    }
    
    func setCard() {
        self.card.cardNumber        = txtCardNum.text!
        self.card.cardHolderName    = txtCardName.text!
        self.card.expDate           = txtExpDate.text!
        self.card.cvv               = Int(txtCvv.text!) != nil ? Int(txtCvv.text!)! : 0
    }
}
