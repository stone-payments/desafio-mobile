//
//  TransactionListSection.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import UIKit


class TransactionListSection: TransactionSection {
    
    override init(parentOutput: SectionOutput) {
        super.init(parentOutput: parentOutput)
        
        loadTransactions()
    }
    
    fileprivate func loadTransactions () {
        
        if let transactions = CartManager.shared.getAllTransactions() {
            
            self.items = transactions
            
        } else {
            self.items = []
        }
        
        self.parentOutput?.reloadSection(self)
    }
}
