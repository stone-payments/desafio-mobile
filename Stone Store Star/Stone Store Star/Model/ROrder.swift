//
//  Order.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import Foundation
import RealmSwift

enum OrderStatus: Int {
    case Open = 0
    case Closed = 1
}

class ROrder: Object {
    @objc dynamic var status = OrderStatus.Open.rawValue
    let products = List<RProduct>()
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

//class RProductQuantity: Object {
//    @objc dynamic var product: RProduct?
//    @objc dynamic var quantity: Int = 1
//}

