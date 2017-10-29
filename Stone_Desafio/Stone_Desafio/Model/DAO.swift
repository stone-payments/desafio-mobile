//
//  DAO.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 28/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import Foundation
import UIKit


// Class interfacing between application and Plist
class DAO {
    
    private var database: [String:AnyObject]?
    let tManager = TransactionsManager.sharedInstance
    private let plist = SimplePList(plistName: "Transactions")
    
    init () {
        self.plist.saveDataToDirectory()
        self.database = plist.getData()
    }
    
    func saveDataTotal(newDatabase: [String:AnyObject]) {
        print(newDatabase)
        plist.saveData(dataToSave: newDatabase)
    }
    
    func setTransactions() {
        
        let transactions = database?["Transacoes"] as! [AnyObject]
        for trans in transactions {
            
            let t = trans as! [String:AnyObject]
            
            tManager.transactions.append(Transactions(value: (t["valor"] as? Int)!, dateTime: (t["datahora"] as? Date)!, last4Digits: (t["4digitos"] as? String)!, cardholderName: (t["nome"] as? String)!))
        }
    }
    
    func saveTransactionsLocally() {
        
        var newDatabase: [String:AnyObject] = [:]
        var transactions: [AnyObject] = []
        
        for trans in tManager.transactions {
            
            var newTransaction: [String:AnyObject] = [:]
            newTransaction["valor"] = trans.value as AnyObject?
            newTransaction["datahora"] = trans.dateTime as AnyObject?
            newTransaction["4digitos"] = trans.last4Digits as AnyObject?
            newTransaction["nome"] = trans.cardholderName as AnyObject?
            transactions.append(newTransaction as AnyObject)
            
        }
        newDatabase["Transacoes"] = transactions as AnyObject?
        saveDataTotal(newDatabase: newDatabase)
    }

}


