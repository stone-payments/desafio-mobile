//
//  CartViewController.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 27/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import UIKit

class CartViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    // Outlets
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var cartValue: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Set tableView data source and delegate as self
        tableView.dataSource = self
        tableView.delegate = self
        
        //If there is no cart items, present alert
        if (Cart.sharedInstance.itemsInCart.count == 0) {
            let alert = UIAlertController(title: "Você ainda não possui itens no carrinho", message: "Por favor adicione itens", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        }
        
        cartValue.text = priceToCurrency(price: Cart.sharedInstance.priceOfItems)
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: Table View Data Source
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return Cart.sharedInstance.itemsInCart.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier1", for: indexPath) as! CartTableViewCell
        cell.title.text = "Nome: \(Cart.sharedInstance.itemsInCart[indexPath.row].title)"
        
        let price = Cart.sharedInstance.itemsInCart[indexPath.row].price
        cell.price.text = "Preço: \(priceToCurrency(price: price))"
        
        let image = setImageFromURl(stringImageUrl: Cart.sharedInstance.itemsInCart[indexPath.row].thumbnailHD)
        cell.thumbnail.image = image!
        cell.background.image = image!
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView,
                   commit editingStyle: UITableViewCellEditingStyle,
                   forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            Cart.sharedInstance.removeItemsInCart(index: indexPath.row)
            cartValue.text = priceToCurrency(price: Cart.sharedInstance.priceOfItems)
            tableView.reloadData()
        }
    }
}
