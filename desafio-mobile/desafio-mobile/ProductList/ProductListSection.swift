//
//  ProductListSection.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import UIKit

class ProductListSection: ProductSection {
    
    var requesting = false
    
    override init(parentOutput: SectionOutput) {
        super.init(parentOutput: parentOutput)
        
        loadProducts()
    }
    
    fileprivate func loadProducts () {
        
        guard !requesting else { return }
        
        self.requesting = !requesting
        
        _ = GitHubAPI.getProducts () { (products, error) in
            
            self.requesting = !self.requesting
            
            var _items = self.items as? [Product]
            _items?.insert(contentsOf: products, at: self.items.endIndex)
            self.items = _items ?? self.items
            
            self.parentOutput?.insertItems(atSection: self)
        }
    }
}
