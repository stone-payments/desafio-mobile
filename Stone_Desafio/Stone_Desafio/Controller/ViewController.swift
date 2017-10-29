//
//  ViewController.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 21/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import UIKit



class ViewController: UIViewController {
    
    // Vector to be populated with the Items from the URL
    var itemsForSale: [Item] = [] {
        didSet {
            goToStoreVC()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view, typically from a nib.
        
        // Play Star Wars Theme on loop
        AudioManager.sharedInstance.playBackgroundMusic()
        
        // Get info from Plist (local DB)
        TransactionsManager.sharedInstance.updateDAO()
        
        // Get items from URL
        populateItems()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // Function to get items from the URL
    func populateItems()  {
        do {
            try DispatchQueue.main.async {
            let url = URL(string: "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json")
            var json: [Any]?
            let sessionConfig = URLSessionConfiguration.default
            sessionConfig.timeoutIntervalForResource = 3.0
            sessionConfig.timeoutIntervalForRequest = 3.0
            let task = URLSession(configuration: sessionConfig).dataTask(with: url!) { data, response, error in
                guard error == nil else {
                    print("Error: ",error!)
                    let alert = UIAlertController(title: "Houve um erro na conexão com a loja", message: "Por favor, feche o aplicativo e tente novamente mais tarde", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                    return
                }
                guard let data = data else {
                    print("Data is empty")
                    let alert = UIAlertController(title: "Houve um erro na conexão com a loja", message: "Por favor, feche o aplicativo e tente novamente mais tarde", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                    return
                    return
                }
                do {
                    json = try JSONSerialization.jsonObject(with: data) as? [Any]
                    self.itemsForSale = self.createItems(json: json)
                } catch {
                    print("Failed in error: ",error)
                    let alert = UIAlertController(title: "Houve um erro na conexão com a loja", message: "Por favor, feche o aplicativo e tente novamente mais tarde", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                    return
                }
            }
            task.resume()
            }
        } catch {
            let alert = UIAlertController(title: "Houve um erro na conexão com a loja", message: "Por favor, feche o aplicativo e tente novamente mais tarde", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        }
    }
    
    // Create Items from the Json received
    func createItems(json: [Any]?) -> [Item] {
        
        var items: [Item] = []
        
        for i in json!  {
            guard let item = i as? [String : Any],
                let title = item["title"] as? String,
                let price = item["price"] as? Int,
                let zipcode = item["zipcode"] as? String,
                let seller = item["seller"] as? String,
                let thumbnailHD = item["thumbnailHd"] as? String,
                let date = item["date"] as? String
                else { return [] }
            let itemForSale = Item(title: title, price: price, zipcode: zipcode, seller: seller, thumbnailHD: thumbnailHD, date: date)
            items.append(itemForSale)
        }
        return items
    }
    
    
    // Go to ItemViewController
    func goToStoreVC() {
        sleep(UInt32(0.2))
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let storeVC  = storyboard.instantiateViewController(withIdentifier: "itemVC") as! ItemViewController
        storeVC.itemsForSale = itemsForSale
        
        let navigationController = UINavigationController(rootViewController: storeVC)
        self.present(navigationController, animated: false,
                     completion: nil)
    }
    
}


