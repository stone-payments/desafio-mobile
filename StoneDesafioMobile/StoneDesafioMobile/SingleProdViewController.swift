//
//  SingleProdViewController.swift
//  StoneDesafioMobile
//
//  Created by Gabriela Schirmer Mauricio on 26/09/17.
//  Copyright © 2017 Gabriela Schirmer Mauricio. All rights reserved.
//

import Foundation
import UIKit
import CoreData

class SingleProdViewController: UIViewController {
    
    // MARK: - IBOUTLET Methods
    
    @IBOutlet weak var imagem: UIImageView!
    @IBOutlet weak var preco: UILabel!
    @IBOutlet weak var produto: UILabel!
    @IBOutlet weak var vendedor: UILabel!
    @IBOutlet weak var addaocarrinho: UIButton!
    @IBOutlet weak var stepperqtd: UIStepper!
    @IBOutlet weak var qtd: UILabel!
    
    
    // MARK: - Vars
    
    var product = [String:AnyObject]()
    var val = 1
    var cart: [NSManagedObject] = []
    var arr = [String:Any]()
    var itemid = Int()
    
    // MARK: - View Methods
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor(patternImage: UIImage(named: "background")!)
        imagem.af_setImage(withURL: URL(string: product["thumbnailHd"] as! String)!)
        
        let largeNumber = product["price"] as! Float
        let numberFormatter = NumberFormatter()
        numberFormatter.locale = Locale(identifier: "pt_BR")
        numberFormatter.numberStyle = .currency
        let formattedNumber = numberFormatter.string(from: (largeNumber/100) as NSNumber)

        preco.text = formattedNumber
        produto.text = product["title"] as? String
        vendedor.text = product["seller"] as? String
        
        stepperqtd.value = 1
        qtd.text = String(val)
        
        addaocarrinho.layer.cornerRadius = 5.0
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Functions
    
    func save(prod: [String:Any]) {
        
        guard let appDelegate =
            UIApplication.shared.delegate as? AppDelegate else {
                return
        }
        
        let managedContext = appDelegate.persistentContainer.viewContext
        
        let entity = NSEntityDescription.entity(forEntityName: "Cart_item", in: managedContext)!
        
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Cart_item")
        fetchRequest.predicate = NSPredicate(format: "id = %d", prod["id"] as! Int)
        
        var results: [NSManagedObject] = []
        
        do {
            results = try managedContext.fetch(fetchRequest)
        }
        catch {
            print("error executing fetch request: \(error)")
        }
        
        let result = results.count > 0

        if !result {
            print("doesnt exist")
            
            let item = NSManagedObject(entity: entity, insertInto: managedContext)

            // 3
            item.setValue(prod["product_title"], forKeyPath: "product_title")
            item.setValue(prod["qtd"], forKeyPath: "qtd")
            item.setValue(prod["price"], forKeyPath: "price")
            item.setValue(prod["img_url"], forKeyPath: "img_url")
            item.setValue(prod["id"], forKeyPath: "id")
            
            // 4
            do {
                try managedContext.save()
                cart.append(item)
                print("append")
                createalert(titulo: "Sucesso", msg: "Item adicionado ao carrinho")
            } catch let error as NSError {
                print("Could not save. \(error), \(error.userInfo)")
                createalert(titulo: "Erro", msg: "Erro ao adicionar ao carrinho")

            }
        } else {
            let addqtd = results[0].value(forKeyPath: "qtd") as! Int + (prod["qtd"] as! Int)
            results[0].setValue(addqtd, forKey: "qtd")
            createalert(titulo: "Sucesso", msg: "Item adicionado ao carrinho")
        }
    }
    
    func createalert(titulo: String, msg: String) {
        let alert = UIAlertController(title: titulo, message: msg, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }

    
    // MARK: - IBACTION Methods

    @IBAction func addrmvqtd(_ sender: Any) {
        val = Int(stepperqtd.value)
        qtd.text = String(val)
    }
    
    @IBAction func addaocarrinhoAction(_ sender: Any) {
        arr = ["id" : itemid, "product_title" : product["title"] as! String, "qtd" : Int(stepperqtd.value), "price" : product["price"] as! Int, "img_url" :  product["thumbnailHd"] as! String]
        save(prod: arr)
    }
}
