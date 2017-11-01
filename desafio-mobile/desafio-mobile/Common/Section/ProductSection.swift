//
//  ProductSection.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 31/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import UIKit

class ProductSection: Section, ProductCellDelegate {
    
    override func heightForCell(atIndexPath indexPath: IndexPath) -> CGFloat {
        return UIScreen.main.bounds.size.height * 0.20
    }
    
    override func tableViewCell() -> UITableViewCell.Type {
        return ProductCell.self
    }
    
    override func willDisplayCell(_ cell: UITableViewCell, atIndexPath indexPath: IndexPath) {
        
        guard let _cell = cell as? ProductCell,
            let product = self.items[indexPath.row] as? Product else { return }
        
        _cell.delegate = self
        _cell.setup(product: product)
    }
    
    func didSelectAdd(product: Product, key: String) {
        CartManager.shared.addToCart(object: product, key: key)
    }
    
    func didSelectRemove(key: String) {
        CartManager.shared.deleteFromCart(key: key)
    }
}
