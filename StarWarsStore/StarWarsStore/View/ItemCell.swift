//
//  ItemCell.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 28/11/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

class ItemCell: UITableViewCell {

	@IBOutlet weak var backgoundView: UIImageView!
	@IBOutlet weak var nameLabel: UILabel!
	@IBOutlet weak var sellerLabel: UILabel!
	@IBOutlet weak var priceLabel: UILabel!
	
	override func awakeFromNib() {
        super.awakeFromNib()
		
		nameLabel.adjustsFontSizeToFitWidth = true
		sellerLabel.adjustsFontSizeToFitWidth = true
		priceLabel.adjustsFontSizeToFitWidth = true
    }

}
