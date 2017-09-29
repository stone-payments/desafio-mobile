//
//  CheckoutViewController.swift
//  StoneDesafioMobile
//
//  Created by Gabriela Schirmer Mauricio on 27/09/17.
//  Copyright © 2017 Gabriela Schirmer Mauricio. All rights reserved.
//

import Foundation
import UIKit
import CoreData
import Alamofire

class CheckoutViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate {
    
    // MARK: - IBOutlets
    
    @IBOutlet weak var finalizarcomprabtn: UIButton!
    @IBOutlet weak var mytableview: UITableView!
    
    // MARK: - Vars
    
    var activeTextField = Int()
    var inputsplaceholders = ["Número do cartão", "Nome do portador do cartão", "Vencimento do cartão", "CVV"]
    var inputvalues = ["", "", "", ""]
    let mask = "##/##"
    var precototal = Int()
    
    // MARK: - View Methods
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor(patternImage: UIImage(named: "background")!)
        finalizarcomprabtn.layer.cornerRadius = 5.0
        mytableview.delegate = self
        let nib = UINib(nibName: "TableSectionHeader", bundle: nil)
        mytableview.register(nib, forHeaderFooterViewReuseIdentifier: "TableSectionHeader")
        self.hideKeyboardWhenTappedAround()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    // MARK: - Functions
    
    func save(order: [String:Any]) {
        
        guard let appDelegate =
            UIApplication.shared.delegate as? AppDelegate else {
                return
        }
        
        let managedContext = appDelegate.persistentContainer.viewContext
        
        let entity = NSEntityDescription.entity(forEntityName: "Transacao", in: managedContext)!
        
        let item = NSManagedObject(entity: entity, insertInto: managedContext)
        
        item.setValue(order["cartao"], forKeyPath: "cartao")
        item.setValue(order["portador"], forKeyPath: "portador")
        item.setValue(order["data_hora"], forKeyPath: "data_hora")
        item.setValue(order["valor"], forKeyPath: "valor")
        
        do {
            try managedContext.save()
            createalert(titulo: "Sucesso", msg: "Transação salva")
        } catch let error as NSError {
            print("Could not save. \(error), \(error.userInfo)")
            createalert(titulo: "Erro", msg: "Erro ao salvar transaçã")
        }
    }
    
    func createalert(titulo: String, msg: String) {
        let alert = UIAlertController(title: titulo, message: msg, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }

    func deletecart() {
        guard let appDelegate =
            UIApplication.shared.delegate as? AppDelegate else {
                return
        }
        
        let managedContext = appDelegate.persistentContainer.viewContext
        
        let fetchRequest = NSFetchRequest<NSManagedObject>(entityName: "Cart_item")
        
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
    
    func transformnumber(number: Int)->String {
        
        let largeNumber = Float(number)
        let numberFormatter = NumberFormatter()
        numberFormatter.locale = Locale(identifier: "pt_BR")
        numberFormatter.numberStyle = .currency
        let formattedNumber = numberFormatter.string(from: (largeNumber/100) as NSNumber)
        return formattedNumber!
        
    }
    
    
    // MARK: - TableView Methods
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return inputsplaceholders.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "0", for: indexPath as IndexPath) as! CheckoutTableCellViewController
        
        cell.input.attributedPlaceholder = NSAttributedString(string: inputsplaceholders[indexPath.item], attributes: [NSAttributedStringKey.foregroundColor: UIColor(red: 1, green: 1, blue: 1, alpha: 0.5)])

        cell.input.delegate = self
        cell.input.tag = indexPath.item
        
        switch indexPath.item {
        case 0:
            cell.input.keyboardType = UIKeyboardType.numberPad
        case 1:
            cell.input.keyboardType = UIKeyboardType.default
        case 2:
            cell.input.keyboardType = UIKeyboardType.numberPad
        case 3:
            cell.input.keyboardType = UIKeyboardType.numberPad
        default:
            break
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let cell = tableView.dequeueReusableHeaderFooterView(withIdentifier: "TableSectionHeader")
        let header = cell as! TableSectionHeader
        header.titleLabel.text = transformnumber(number: (precototal))
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 70
    }
    

    // MARK: - TextField Methods

    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool  {
        
        switch activeTextField {
            case 0:
                inputvalues[0] = textField.text!
                let currentCharacterCount = textField.text?.characters.count ?? 0
                if (range.length + range.location > currentCharacterCount) {
                    return false
                }
                let newLength = currentCharacterCount + string.characters.count - range.length
                return newLength <= 16
            case 1:
                inputvalues[1] = textField.text!
                return true
            case 2:
                inputvalues[2] = textField.text!

                guard let normalText = textField.text else { return false }
                
                let beginning = textField.beginningOfDocument
                
                // save cursor location
                let cursorLocation = textField.position(from: beginning, offset: range.location + string.characters.count)
                
                let newString = (normalText as NSString).replacingCharacters(in: range, with: string)
                let newStringClean = newString.stringWithOnlyNumbers().withMask(mask: mask)
                
                guard newString != newStringClean else { return true }
                
                textField.text = newStringClean
                guard string != "" else { return false }
                
                // fix cursor location after changing textfield.text
                if let cL = cursorLocation {
                    let textRange = textField.textRange(from: cL, to: cL)
                    textField.selectedTextRange = textRange
                }
                
                return false
            case 3:
                inputvalues[3] = textField.text!

                let currentCharacterCount = textField.text?.characters.count ?? 0
                if (range.length + range.location > currentCharacterCount){
                    return false
                }
                let newLength = currentCharacterCount + string.characters.count - range.length
                return newLength <= 3
            default:
                break
        }
        
        return false
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        self.activeTextField = textField.tag
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        inputvalues[textField.tag] = textField.text!
    }
    
    
    // MARK: - IBActions
    
    @IBAction func finalizarcompraaction(_ sender: Any) {
        
        if inputvalues.contains("") {
            createalert(titulo: "Atenção", msg: "Todos os campos devem ser preenchidos")
            return
        } else {
            if inputvalues[0].count < 16 {
                createalert(titulo: "Atenção", msg: "Número do cartão precisa ter 16 digitos")
                return
            }
            
            if inputvalues[2].count < 5 {
                createalert(titulo: "Atenção", msg: "Complete a data de vencimento do cartão")
                return
            }
            
            if inputvalues[3].count < 3 {
                createalert(titulo: "Atenção", msg: "O digito verificador precisa ter 3 números")
                return
            }
        }
        
        let urlString = "https://private-9a64c-stonedesafiomobile.apiary-mock.com/orders"
        
//            + card_number (required, string)
//            + value (required, number)
//            + card_holder_name (required, string)
//            + cvv (required, number)
//            + exp_date (required, string)
        
        let parameters = ["card_number" : self.inputvalues[0], "value" : self.precototal, "card_holder_name" : self.inputvalues[1], "cvv" : Int(inputvalues[3]) as Any, "exp_date" : inputvalues[2]] as [String : Any]
        
        Alamofire.request(urlString, method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: nil).responseJSON {
            response in
            print(response)
            switch response.result {
                case .success:
                    let date = Date()
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
                    let timeStamp = dateFormatter.string(from: date)
                    
                    let last = self.inputvalues[0].suffix(4)
                    
                    let arr = ["cartao" : last, "portador" : self.inputvalues[1], "data_hora" : timeStamp, "valor" : self.precototal] as [String : Any]
                    
                    self.save(order: arr)
                    self.deletecart()

                    break
                case .failure(let error):

                    print(error)
            }
        }
    }
}
