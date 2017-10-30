//
//  ProductListCell.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import UIKit


class ProductListCell: UITableViewCell {
    
    @IBOutlet weak var thumbnailHd      : UIImageView!
    
    @IBOutlet weak var titleLabel       : UILabel!
    
    @IBOutlet weak var priceLabel       : UILabel!
    
    @IBOutlet weak var zipcodeLabel     : UILabel!
    
    @IBOutlet weak var sellerLabel      : UILabel!
    
    @IBOutlet weak var dateLabel        : UILabel!
    
    func setup(product: Product) {
        
        thumbnailHd.sd_setImage(with: URL(string: product.thumbnailHd ?? ""), placeholderImage: UIImage(named: "image-placeholder.png"))
        
        titleLabel.text     = product.title
        
        priceLabel.text     = product.price?.description
        
        zipcodeLabel.text   = product.zipcode
        
        sellerLabel.text    = product.seller
        
        dateLabel.text      = product.date
    }
}
