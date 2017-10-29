//
//  ItemViewController.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 26/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import UIKit

class ItemViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    // Outlets
    
    // Function to increment cart with selected item
    @IBAction func buyButton(_ sender: Any) {
        let buttonPosition: CGPoint = (sender as AnyObject).convert(CGPoint.zero, to:self.tableView)
        let indexPath = self.tableView.indexPathForRow(at: buttonPosition)
        Cart.sharedInstance.addItemsInCart(item: self.itemsForSale![(indexPath?.row)!])
        
        let alert = UIAlertController(title: "Item adicionado ao carrinho!", message: "", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        alert.addAction(UIAlertAction(title: "Ver carrinho", style: .default, handler: {(alert: UIAlertAction!) in self.goToCartVC() }))
        
        self.present(alert, animated: true, completion: nil)
        
        AudioManager.sharedInstance.playCartSound()
        cartValue.text = priceToCurrency(price: Cart.sharedInstance.priceOfItems)
    }
    @IBOutlet weak var cartValue: UILabel!
    @IBOutlet weak var tableView: UITableView!
    
    var itemsForSale: [Item]?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Set tableView data source and delegate as self
        tableView.dataSource = self
        tableView.delegate = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        cartValue.text = priceToCurrency(price: Cart.sharedInstance.priceOfItems)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: Table View Data Source
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        print("Items for sale", (itemsForSale?.count)!)
        
        return (itemsForSale?.count)!
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath) as! ItemTableViewCell
        cell.title.text = "Nome: \(itemsForSale![indexPath.row].title)"
        
        let price = itemsForSale![indexPath.row].price
        cell.price.text = "Preço: \(priceToCurrency(price: price))"
        
        let image = setImageFromURl(stringImageUrl: itemsForSale![indexPath.row].thumbnailHD)
        cell.thumbnail.image = image!
        
        cell.backgroundImage.image = image
        
        return cell
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    // MARK: - Navigation
    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let indexPath = tableView.indexPathForSelectedRow{
            let selectedRow = indexPath.row
            let detailVC = segue.destination as! DetailViewController
            detailVC.item = self.itemsForSale![selectedRow]
            detailVC.cartValue = cartValue
            tableView.deselectRow(at: indexPath, animated: true)
        }
        
    }
    
    // Function to go to CartViewController
    func goToCartVC() {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let cartVC  = storyboard.instantiateViewController(withIdentifier: "cartVC") as! CartViewController
        self.navigationController?.pushViewController(cartVC, animated: true)
    }
    
}
