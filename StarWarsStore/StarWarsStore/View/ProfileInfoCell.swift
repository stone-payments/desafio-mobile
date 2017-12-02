//
//  profileInfoCell.swift
//  StarWarsStore
//
//  Created by Lucas Ferraço on 01/12/17.
//  Copyright © 2017 Lucas Ferraço. All rights reserved.
//

import UIKit

class ProfileInfoCell: UITableViewCell {
	
	@IBOutlet weak var cardHolderLabel: UILabel!
	@IBOutlet weak var detailInfoLabel: UILabel!
	
	override func awakeFromNib() {
		super.awakeFromNib()
		
		cardHolderLabel.adjustsFontSizeToFitWidth = true
		detailInfoLabel.adjustsFontSizeToFitWidth = true
	}
	
}
