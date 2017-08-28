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
    @IBOutlet weak var actionButton: UIButton!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func itemAdded(isAdded: Bool) {
        if isAdded {
            let image = #imageLiteral(resourceName: "IconRemoveItem")
            self.actionButton.setImage(image, for: .normal)
            self.actionButton.tintColor = Colors.buttonRemoveItem.color
        } else {
            let image = #imageLiteral(resourceName: "IconAddItem")
            self.actionButton.setImage(image, for: .normal)
            self.actionButton.tintColor = Colors.buttonAddItem.color
        }
    }
}
