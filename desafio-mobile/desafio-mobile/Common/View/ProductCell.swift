//
//  ProductCell.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//
import UIKit


protocol ProductCellDelegate: class {
    func didSelectAdd(product: Product, key: String)
    func didSelectRemove(key: String)
}

class ProductCell: UITableViewCell {
    
    @IBOutlet weak var thumbnailHd      : UIImageView!
    
    @IBOutlet weak var titleLabel       : UILabel!
    
    @IBOutlet weak var addToCart        : UIButton!
    
    @IBOutlet weak var priceLabel       : UILabel!
    
    @IBOutlet weak var sellerLabel      : UILabel!
    
    private var product: Product!
    
    weak var delegate: ProductCellDelegate?
    
    
    func setup(product: Product) {
        
        self.product = product
        
        thumbnailHd.sd_setImage(with: URL(string: product.thumbnailHd), placeholderImage: UIImage(named: "image-placeholder.png"))
        
        titleLabel.text     = product.title
        
        priceLabel.text     = "R$ \(product.price.description)"
        
        sellerLabel.text    = product.seller
        
        addToCart.layer.cornerRadius = 42.5
        
        addToCart.isSelected = !product.isCart
        addToCart.setImage(UIImage(named: "remove-cart.png"), for: .normal)
        addToCart.setImage(UIImage(named: "add-cart.png"), for: .selected)
    }
    
    func addRemoveToCart(buttonState: Bool) {
        guard let key = titleLabel.text, !key.isEmpty else { return }
        
        buttonState ?
            delegate?.didSelectRemove(key: key) :
            delegate?.didSelectAdd(product: product, key: key)
    }
    
    @IBAction func addToCart(_ sender: UIButton) {
        
        sender.isSelected = !sender.isSelected
        sender.setImage(UIImage(named: "remove-cart.png"), for: .normal)
        sender.setImage(UIImage(named: "add-cart.png"), for: .selected)
        sender.backgroundColor = .white
        
        self.addRemoveToCart(buttonState: sender.isSelected)
    }
}
