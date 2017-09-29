//
//  CartViewController.swift
//  StoneDesafioMobile
//
//  Created by Gabriela Schirmer Mauricio on 26/09/17.
//  Copyright © 2017 Gabriela Schirmer Mauricio. All rights reserved.
//

import Foundation
import UIKit
import CoreData

class CartViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    // MARK: - IBOutlets

    @IBOutlet weak var finalizarpedidobtn: UIButton!
    @IBOutlet weak var total: UILabel!
    @IBOutlet weak var footerview: UIView!
    @IBOutlet weak var mytableview: UITableView!
    
    
    // MARK: - Vars
    
    var cart: [NSManagedObject] = []
    
    
    // MARK: - View Methods
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor(patternImage: UIImage(named: "background")!)
        finalizarpedidobtn.layer.cornerRadius = 5.0
        mytableview.delegate = self
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        fetchdata()
        total.text = "Total: " + transformnumber(number: calctot())
    }
    
    
    // MARK: - Functions
    
    func calctot()->Int {
        var tot = 0
        
        for item in cart {
            let precounitario = item.value(forKeyPath: "price") as! Int
            let qtd = item.value(forKeyPath: "qtd") as! Int
            let preco = precounitario * qtd
            
            tot += preco
        }
        
        return tot
    }
    
    func fetchdata() {
        //1
        guard let appDelegate =
            UIApplication.shared.delegate as? AppDelegate else {
                return
        }
        
        let managedContext =
            appDelegate.persistentContainer.viewContext
        
        //2
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Cart_item")
        let sortDescriptor = NSSortDescriptor(key: "id", ascending: true)
        let sortDescriptors = [sortDescriptor]
        fetchRequest.sortDescriptors = sortDescriptors
        
        //3
        do {
            cart = try managedContext.fetch(fetchRequest)
        } catch let error as NSError {
            print("Could not fetch. \(error), \(error.userInfo)")
        }
    }
    
    func transformnumber(number: Int)->String {
        
        let largeNumber = Float(number)
        let numberFormatter = NumberFormatter()
        numberFormatter.locale = Locale(identifier: "pt_BR")
        numberFormatter.numberStyle = .currency
        let formattedNumber = numberFormatter.string(from: (largeNumber/100) as NSNumber)
        return formattedNumber!
        
    }
    
    func deleteobj(id: Int) {
        guard let appDelegate =
            UIApplication.shared.delegate as? AppDelegate else {
                return
        }
        
        let managedContext = appDelegate.persistentContainer.viewContext
        
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Cart_item")
        fetchRequest.predicate = NSPredicate.init(format: "id==\(id)")
        
        if let result = try? managedContext.fetch(fetchRequest) {
            for object in result {
                managedContext.delete(object)
            }
        }
        
        do {
            try managedContext.save() // <- remember to put this :)
        } catch {
            // Do something... fatalerror
        }

    }
    
    func createalert(titulo: String, msg: String) {
        let alert = UIAlertController(title: titulo, message: msg, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
    
    
    // MARK: - TableView Methods
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return cart.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "0", for: indexPath as IndexPath) as! CartTableCellViewController
        let item = cart[indexPath.item]
        cell.produto.text = item.value(forKeyPath: "product_title") as? String
        
        let precounitario = item.value(forKeyPath: "price") as! Int
        let qtd = item.value(forKeyPath: "qtd") as! Int
        let preco = precounitario * qtd
        
        cell.imagem.af_setImage(withURL: URL(string: (item.value(forKeyPath: "img_url")) as! String)!)
        cell.quantidade.text = "x" + String(qtd)
        cell.preco.text = transformnumber(number: preco)
        cell.precounitario.text = transformnumber(number: precounitario)
        cell.produto.text = item.value(forKeyPath: "product_title") as? String

        return cell
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            print("count: \(cart[indexPath.row])")
            deleteobj(id: cart[indexPath.row].value(forKeyPath: "id") as! Int)
            cart.remove(at: indexPath.row)
            tableView.reloadData()
            total.text = "Total: " + transformnumber(number: calctot())
        }
    }
    

    // MARK: - IBActions

    
    
    // MARK: - Segue Methods
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        if cart.count < 1 {
            createalert(titulo: "Atenção", msg: "Seu carrinho está vazio.")
            return false
        } else {
            return true
        }
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if segue.identifier == "gotocheckout" {
            
            let checkout = segue.destination as! CheckoutViewController
            
            checkout.precototal = calctot()
            
        }
    }
}
