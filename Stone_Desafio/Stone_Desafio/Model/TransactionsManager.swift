//
//  TransactionsManager.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 28/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import Foundation

let transactionsManager = TransactionsManager()
let dao = DAO()

// Singleton controlling transactions throughout the application
class TransactionsManager{
    
    class var sharedInstance: TransactionsManager{
        return transactionsManager
    }
    
    var transactions: [Transactions] = []
    
    func addAndSaveTransactions(transaction: Transactions) {
        transactions.append(transaction)
        dao.saveTransactionsLocally()
        
    }
    
    func updateDAO() {
        
        
        dao.setTransactions()
        
    }
    
}

