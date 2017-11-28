//
//  RCard.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//Copyright © 2017 Kennedy Noia. All rights reserved.
//

import Foundation
import RealmSwift

class RCard: Object {
    @objc dynamic var holder_name = ""
    @objc dynamic var number = ""
    @objc dynamic var cvv = 0
    @objc dynamic var exp_date = Date()
    
//    "card_number":"1234123412341234",
//    "cvv":789,
//    "card_holder_name":"Luke Skywalker",
//    "exp_date":"12/24"
}
