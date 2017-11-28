//
//  Order.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import Foundation
import RealmSwift

//  Open order corresponds to the selected items contained in the shopping cart, whose purchase has not yet been finalized.
//  Closed orders correspond to orders already completed, when finalizing a request and obtaining approval of the payment a new transaction must be registered.
enum OrderStatus: Int {
    case Open = 0
    case Closed = 1
}

class ROrder: Object {
    @objc dynamic var status = OrderStatus.Open.rawValue
    let products = List<RProduct>()
// TO DO: Future implementation, allowing the user change the quantity of the same product
//    let productsQuantities = List<RProductQuantity>()
    var statusEnum: OrderStatus {
        get {
            return OrderStatus(rawValue: status)!
        }
        set {
            status = newValue.rawValue
        }
    }
}

// TO DO: Future implementation, allowing the user change the quantity of the same product
//class RProductQuantity: Object {
//    @objc dynamic var product: RProduct?
//    @objc dynamic var quantity: Int = 1
//}

