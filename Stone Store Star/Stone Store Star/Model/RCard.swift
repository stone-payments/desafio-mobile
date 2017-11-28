//
//  RCard.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import Foundation
import RealmSwift

//  Card model data example:
//    "card_number":"1234123412341234",
//    "cvv":789,
//    "card_holder_name":"Luke Skywalker",
//    "exp_date":"12/24"
class RCard: Object {
    @objc dynamic var holder_name = ""
    // To do: check if is a security rule to save only the last
    //four digits of the card number, likewise implement ask
    //confirmation data to get permition of use for each new
    //transaction.
    @objc dynamic var number = ""
    @objc dynamic var cvv = 0
    @objc dynamic var exp_date = Date()
}
