//
//  Colors.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 27/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

enum Colors {
    case navigationBar
    case tabBar(type: tabBarState)
    case itemDescription
    case itemPrice
    case buttonRemoveItem
    case buttonAddItem
    case refreshControl
    
    enum tabBarState {
        case selected
        case unselected
        case background
    }
    
    var color: UIColor {
        switch self {
        case .navigationBar: return UIColor(red: 50.0/255.0, green: 179.0/255.0, blue: 231.0/255.0, alpha: 1)
        case .tabBar(let type):
            switch type {
            case .selected: return UIColor.darkGray
            case .unselected: return UIColor.lightGray
            case .background: return UIColor.white
            }
        case .itemDescription: return UIColor.darkGray
        case .itemPrice: return UIColor.orange
        case .buttonAddItem: return UIColor(red: 0.0/255.0, green: 202.0/255.0, blue: 145.0/255.0, alpha: 1)
        case .buttonRemoveItem: return UIColor(red: 215.0/255.0, green: 0.0/255.0, blue: 38.0/255.0, alpha: 1)
        case .refreshControl: return UIColor(red: 50.0/255.0, green: 179.0/255.0, blue: 231.0/255.0, alpha: 0.35)
        }
    }
}
