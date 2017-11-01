//
//  Card.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import Foundation
import ObjectMapper

class Card : NSObject {
    
    public var cardNumber          : String    = ""
    
    public var cvv                 : Int       = 0
    
    public var cardHolderName      : String    = ""
    
    public var expDate             : String    = ""
    
}