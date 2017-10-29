//
//  Item.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 21/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import Foundation

// Class representing an item
class Item {
    var title: String
    var price: Int
    var zipcode: String
    var seller: String
    var thumbnailHD: String
    var date: String
    
    init(title: String, price: Int, zipcode: String, seller: String, thumbnailHD: String, date: String) {
        self.title = title
        self.price = price
        self.zipcode = zipcode
        self.seller = seller
        self.thumbnailHD = thumbnailHD
        self.date = date
    }
}
