//
//  Item.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import Foundation
import RealmSwift

//  Product model data example:
//    "title": "Sabre de luz",
//    "price": 150000,
//    "zipcode": "13537-000",
//    "seller": "Mario Mota",
//    "thumbnailHd": "http://www.obrigadopelospeixes.com/wp-content/uploads/2015/12/kalippe_lightsaber_by_jnetrocks-d4dyzpo1-1024x600.jpg",
//    "date": "20/11/2015"
class RProduct: Object {
    @objc dynamic var title = ""
    @objc dynamic var price = 0
    @objc dynamic var zipcode = ""
    @objc dynamic var seller = ""
    @objc dynamic var thumbnailHd = ""
    @objc dynamic var date = ""
}
