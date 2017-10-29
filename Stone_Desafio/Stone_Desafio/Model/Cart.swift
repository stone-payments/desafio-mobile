//
//  Cart.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 21/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import Foundation

let cart = Cart()

// Singleton representing cart
class Cart{
    
    class var sharedInstance: Cart{
        return cart
    }
    
    var itemsInCart: [Item] = []
    var priceOfItems: Int = 0
    
    func addItemsInCart(item: Item) {
        itemsInCart.append(item)
        priceOfItems = priceOfItems + item.price
    }
    
    func removeItemsInCart(index: Int) {
        print("Items in Cart", itemsInCart.count)
        print("Index: ", index)
        priceOfItems = priceOfItems - itemsInCart[index].price
        itemsInCart.remove(at: index)
    }

}
