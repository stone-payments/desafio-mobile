//
//  Product.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import Foundation
import ObjectMapper

class Product : Mappable {
    
    public var title        : String = ""
    
    public var price        : Int = 0
    
    public var zipcode      : String = ""
    
    public var seller       : String = ""
    
    public var thumbnailHd  : String = ""
    
    public var date         : String = ""
    
    public typealias blockCompletion = ([Product], NSError?) -> Void
    
    init(title: String, price: Int, zipcode: String = "", seller: String, thumbnailHd: String, date: String = "") {
        
        self.title = title
        self.price = price
        self.zipcode = zipcode
        self.seller = seller
        self.thumbnailHd = thumbnailHd
        self.date = date
    }
    
    required init?(map: Map) {
        self.mapping(map: map)
    }
    
    func mapping(map: Map) {
        title           <- map["title"]
        price           <- map["price"]
        zipcode         <- map["zipcode"]
        seller          <- map["seller"]
        thumbnailHd     <- map["thumbnailHd"]
        date            <- map["date"]
    }
}
