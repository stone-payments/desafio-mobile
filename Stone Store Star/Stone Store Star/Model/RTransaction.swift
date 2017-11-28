//
//  RTransaction.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//Copyright © 2017 Kennedy Noia. All rights reserved.
//

import Foundation
import RealmSwift

class RTransaction: Object {
    @objc dynamic var card: RCard?
    @objc dynamic var created_at = Date()
    @objc dynamic var value = 0
    
    //    "card_number":"1234123412341234",
    //    "value":7990,
    //    "cvv":789,
    //    "card_holder_name":"Luke Skywalker",
    //    "exp_date":"12/24"
    // Specify properties to ignore (Realm won't persist these)
    
}
