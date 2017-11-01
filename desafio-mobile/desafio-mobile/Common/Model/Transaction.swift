//
//  Transaction.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//
import Foundation
import ObjectMapper


class Transaction : NSObject, Mappable {
    
    public var card     : Card = Card()
    
    public var value    : Int = 0
    
    public var date     : Date = Date()
    
    public typealias blockCompletion = ([Transaction], NSError?) -> Void
    
    init(card: Card, value: Int, date: Date) {
        self.card = card
        self.value = value
        self.date = date
    }
    
    required init?(map: Map) {
        super.init()
        self.mapping(map: map)
    }
    
    func mapping(map: Map) {
        card.cardNumber     <- map["card_number"]
        card.cardHolderName <- map["card_holder_name"]
        value               <- map["value"]
        date                <- (map["date"], Transaction.dateTransform())
    }
    
    public class func dateTransform() -> DateFormatterTransform {
        return self.dateTransform(format: "dd/MM/yyyy HH:mm")
    }
    
    final public class func dateTransform(format: String) -> DateFormatterTransform {
        return CustomDateFormatTransform(formatString: format)
    }
}
