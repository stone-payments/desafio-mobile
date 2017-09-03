//
//  Environment.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 03/09/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import Foundation

enum ServiceURL {
    case products
    case transcations
    
    var value: String {
        switch self {
        case .products: return "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json"
        case .transcations: return "http://private-f17fa-mobcommerce.apiary-mock.com/transaction"
        }
    }
}
