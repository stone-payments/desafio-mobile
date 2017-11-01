//
//  TransactionCell.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import UIKit

class TransactionCell: UITableViewCell {
    
    @IBOutlet weak var date: UILabel!
    @IBOutlet weak var value: UILabel!
    @IBOutlet weak var name: UILabel!
    
    private var transaction: Transaction!
    
    func setup(transaction: Transaction) {
        self.transaction = transaction
        
        date.text = "\(transaction.date)"
        value.text = "R$ \(transaction.value.description)"
        name.text = transaction.card.cardHolderName
    }
}
