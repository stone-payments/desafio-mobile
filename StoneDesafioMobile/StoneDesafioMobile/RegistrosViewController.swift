//
//  RegistrosViewController.swift
//  StoneDesafioMobile
//
//  Created by Gabriela Schirmer Mauricio on 26/09/17.
//  Copyright © 2017 Gabriela Schirmer Mauricio. All rights reserved.
//

import Foundation
import UIKit
import CoreData

class RegistrosViewController: UITableViewController {
    
    
    var orders: [NSManagedObject] = []

    
    // MARK: - View Methods
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor(patternImage: UIImage(named: "background")!)
        self.tableView.contentInset = UIEdgeInsets(top: 20, left: 0, bottom: 0, right: 0)
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        fetchdata()
    }
    
    
    // MARK: - Functions
    
    func fetchdata() {
        guard let appDelegate =
            UIApplication.shared.delegate as? AppDelegate else {
                return
        }
        
        let managedContext =
            appDelegate.persistentContainer.viewContext
        
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Transacao")
        
        do {
            orders = try managedContext.fetch(fetchRequest)
        } catch let error as NSError {
            print("Could not fetch. \(error), \(error.userInfo)")
        }
    }
    
    
    // MARK: - TableView Methods
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "0", for: indexPath as IndexPath) as! RegistrosTableCellViewController
        cell.order.text = "Transação #" + String(indexPath.row)
        return cell
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return orders.count
    }
    
    //MARK: - Segue
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if segue.identifier == "gotodetail" {
            
            let chosenOrder = segue.destination as! DetailsViewController
            
            if let indexPath = tableView?.indexPathForSelectedRow {
                chosenOrder.order = self.orders[indexPath.row] //May it found nil please re - check array values
            }
        }
    }
}
