//
//  CartProductListSection.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//
import UIKit


class CartProductListSection: ProductSection {
    
    override init(parentOutput: SectionOutput) {
        super.init(parentOutput: parentOutput)
        
        loadCartProducts()
    }
    
    fileprivate func loadCartProducts () {
        
        if let products = CartManager.shared.getAllCarts() {
            
            for product in products {
                let key = "Cart-\(product.title)"
                if CartManager.shared.getFromCart(key: key) != nil {
                    product.isCart = true
                }
            }
            self.items = products
            
        } else {
            self.items = []
        }
        
        self.parentOutput?.reloadSection(self)
    }
    
    override func didSelectRemove(key: String) {
        super.didSelectRemove(key: key)
        
        if let products = CartManager.shared.getAllCarts() {
            
            for product in products {
                let key = "Cart-\(product.title)"
                if CartManager.shared.getFromCart(key: key) != nil {
                    product.isCart = true
                }
            }
            self.items = products
            
        } else {
            self.items = []
        }
        
        self.parentOutput?.reloadSection(self)
    }
}
