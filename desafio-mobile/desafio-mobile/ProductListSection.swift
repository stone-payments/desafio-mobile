//
//  ProductListSection.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import UIKit


protocol ProductListOutput: SectionOutput {
    func didSelect(product: Product)
}

class ProductListSection: Section {
    
    var requesting = false
    
    override init(parentOutput: SectionOutput) {
        super.init(parentOutput: parentOutput)
        
        loadProducts()
    }
    
    override func didSelectRow(atIndexPath indexPath: IndexPath) {
        
        guard let product = items[indexPath.row] as? Product else { return }
        (parentOutput as? ProductListOutput)?.didSelect(product: product)
    }
    
    override func heightForCell(atIndexPath indexPath: IndexPath) -> CGFloat {
        return UIScreen.main.bounds.size.height * 0.20
    }
    
    override func tableViewCell() -> UITableViewCell.Type {
        return ProductListCell.self
    }
    
    override func willDisplayCell(_ cell: UITableViewCell, atIndexPath indexPath: IndexPath) {
        
        guard let _cell = cell as? ProductListCell,
            let product = self.items[indexPath.row] as? Product else { return }
        
        _cell.setup(product: product)
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
