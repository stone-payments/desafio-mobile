//
//  Transactions.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 28/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import Foundation
import UIKit

// Class representing a transaction
class Transactions {
    var value: Int?
    var dateTime: Date?
    var last4Digits: String?
    var cardholderName: String?
    
    init(value: Int, dateTime: Date, last4Digits: String, cardholderName: String) {
        
        self.value = value
        self.dateTime = dateTime
        self.last4Digits = last4Digits
        self.cardholderName = cardholderName
        
        }

    }
    

