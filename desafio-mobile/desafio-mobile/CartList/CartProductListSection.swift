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
        self.items = CartManager.shared.getAllCarts() ?? []
        self.parentOutput?.reloadSection(self)
    }
    
    override func didSelectRemove(key: String) {
        super.didSelectRemove(key: key)
        
        self.items = CartManager.shared.getAllCarts() ?? []
        self.parentOutput?.reloadSection(self)
    }
}
