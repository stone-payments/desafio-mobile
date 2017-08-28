//
//  ListItemViewCell.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 27/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import UIKit

class ListItemViewCell: UICollectionViewCell {
    
    @IBOutlet weak var itemDescription: UILabel!
    @IBOutlet weak var itemPrice: UILabel!
    @IBOutlet weak var itemImage: UIImageView!
    @IBOutlet weak var addItemButton: UIButton!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func itemAdded(isAdded: Bool) {
        if isAdded {
            let image = #imageLiteral(resourceName: "IconRemoveItem")
            self.addItemButton.setImage(image, for: .normal)
            self.addItemButton.tintColor = Colors.buttonRemoveItem.color
        } else {
            let image = #imageLiteral(resourceName: "IconAddItem")
            self.addItemButton.setImage(image, for: .normal)
            self.addItemButton.tintColor = Colors.buttonAddItem.color
        }
    }
}
