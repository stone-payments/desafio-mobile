//
//  DetailViewController.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 21/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController {
    
    // Outlets
    @IBOutlet weak var itemTitle: UILabel!
    @IBOutlet weak var itemPrice: UILabel!
    @IBOutlet weak var itemZipcode: UILabel!
    @IBOutlet weak var itemSeller: UILabel!
    @IBOutlet weak var itemDate: UILabel!
    @IBOutlet weak var background: UIImageView!
    @IBOutlet weak var thumbnail: UIImageView!
    @IBAction func buyButton(_ sender: Any) {
        Cart.sharedInstance.addItemsInCart(item: item!)
        let alert = UIAlertController(title: "Item adicionado ao carrinho!", message: "", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        alert.addAction(UIAlertAction(title: "Ver carrinho", style: .default, handler: {(alert: UIAlertAction!) in self.goToCartVC() }))
        
        self.present(alert, animated: true, completion: nil)
        AudioManager.sharedInstance.playCartSound()
        cartValue?.text = priceToCurrency(price: Cart.sharedInstance.priceOfItems)
    }

    var item: Item?
    var cartValue: UILabel?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        // Set values for outlets
        thumbnail.image = setImageFromURl(stringImageUrl: (item?.thumbnailHD)!)
        background.image = setImageFromURl(stringImageUrl: (item?.thumbnailHD)!)
        itemTitle.text = "Nome: \((item?.title)!)"
        itemPrice.text = "Preço: \(priceToCurrency(price: (item?.price)!))"
        itemZipcode.text = "CEP: \((item?.zipcode)!)"
        itemSeller.text = "Vendedor: \((item?.seller)!)"
        itemDate.text = "Data: \((item?.date)!)"
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // Function to go to CartViewController
    func goToCartVC() {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let cartVC  = storyboard.instantiateViewController(withIdentifier: "cartVC") as! CartViewController
        self.navigationController?.pushViewController(cartVC, animated: true)
    }
}
