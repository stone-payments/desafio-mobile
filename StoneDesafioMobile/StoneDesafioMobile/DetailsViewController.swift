//
//  DetailsViewController.swift
//  StoneDesafioMobile
//
//  Created by Gabriela Schirmer Mauricio on 26/09/17.
//  Copyright © 2017 Gabriela Schirmer Mauricio. All rights reserved.
//

import Foundation
import UIKit
import CoreData

class DetailsViewController: UITableViewController {
    
    // MARK: - Vars
    
    var order = NSManagedObject()
    var titulos = ["Valor", "Data e Hora", "Últimos 4 dígitos do cartão", "Nome do portador do cartão"]
    var detalhes = ["", "", "", ""]
    // MARK: - View Methods
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor(patternImage: UIImage(named: "background")!)
        // Do any additional setup after loading the view, typically from a nib.
        
        detalhes[0] = transformnumber(number: (order.value(forKey: "valor") as! Int))
        detalhes[1] = order.value(forKey: "data_hora") as! String
        detalhes[2] = order.value(forKey: "cartao") as! String
        detalhes[3] = order.value(forKey: "portador") as! String

    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    // MARK: - Functions
    
    func transformnumber(number: Int)->String {
        
        let largeNumber = Float(number)
        let numberFormatter = NumberFormatter()
        numberFormatter.locale = Locale(identifier: "pt_BR")
        numberFormatter.numberStyle = .currency
        let formattedNumber = numberFormatter.string(from: (largeNumber/100) as NSNumber)
        return formattedNumber!
        
    }
    
    // MARK: - TableView Methods
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "0", for: indexPath as IndexPath) as! DetailsTableCellViewController
        
        cell.title.text = detalhes[indexPath.section]
        
        return cell
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return titulos.count
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return titulos[section]
    }
}
