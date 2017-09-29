//
//  ViewController.swift
//  StoneDesafioMobile
//
//  Created by Gabriela Schirmer Mauricio on 24/09/17.
//  Copyright © 2017 Gabriela Schirmer Mauricio. All rights reserved.
//

import UIKit
import Alamofire
import AlamofireImage

class ViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {

    // MARK: - View Methods

    /*
     
     {
         "card_number":"1234123412341234",
         "value":7990,
         "cvv":789,
         "card_holder_name":"Luke Skywalker",
         "exp_date":"12/24"
     }
     
     */
    
    var json = [[String:AnyObject]]()
    var images = [UIImage]()
    var selectedcell = Int()

    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor(patternImage: UIImage(named: "background")!)
        
        let layout: UICollectionViewFlowLayout = UICollectionViewFlowLayout()
        layout.sectionInset = UIEdgeInsets(top: 20, left: 20, bottom: 15, right: 20)
        collectionView!.collectionViewLayout = layout
        
        getdata()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
    // MARK: - Functions
    
    func getdata() {
        Alamofire.request("https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json").responseJSON { response in
//            print("Request: \(String(describing: response.request))")   // original url request
//            print("Response: \(String(describing: response.response))") // http url response
//            print("Result: \(response.result)")                         // response serialization result
            
            
            if let result = response.result.value {
                self.json = result as! [[String : AnyObject]]
                self.collectionView?.reloadData()
            }
        }
    }
    
    func fetchImage(url: URL)->UIImage? {
        let imageData = NSData(contentsOf: url)
        var img = UIImage()
        
        if imageData != nil {
            img = UIImage( data: imageData! as Data )!
        }
        
        return img
    }
    // MARK: - CollectionView Methods

    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return json.count
    }
    
    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "0", for: indexPath as IndexPath) as! CollectionCellViewController
        
        if (json.count > 0) {
            let title = self.json[indexPath.item]["title"]!
            let price = self.json[indexPath.item]["price"]!
            let seller = self.json[indexPath.item]["seller"]!
            let thumb = self.json[indexPath.item]["thumbnailHd"]! as! String
//            let date = self.json[indexPath.item]["date"]!
//            let zip = self.json[indexPath.item]["zipcode"]!

            let largeNumber = price as! Float
            let numberFormatter = NumberFormatter()
            numberFormatter.locale = Locale(identifier: "pt_BR")
            numberFormatter.numberStyle = .currency
            let formattedNumber = numberFormatter.string(from: (largeNumber/100) as NSNumber)

            cell.imagem.af_setImage(withURL: URL(string: thumb)!)
            cell.preco.text = formattedNumber
            cell.produto.text = title as? String
            cell.vendedor.text = seller as? String
            
        }
        
        
        cell.layer.cornerRadius = 5.0
        return cell
    }
    
    
    // MARK: - CollectionViewLayout Methods

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let padding: CGFloat = 55
        let cellsizeW = (collectionView.bounds.width - padding)/2
        
        return CGSize(width: cellsizeW, height: cellsizeW + 90)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 15
    }
    
    
    // MARK: - Segue Methods

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if segue.identifier == "gotoproduct" {
            
            let chosenProduct = segue.destination as! SingleProdViewController
            
            if let indexPath = collectionView?.indexPathsForSelectedItems?.first {
                
                chosenProduct.product = self.json[indexPath.item] //May it found nil please re - check array values
                chosenProduct.itemid = indexPath.item
            }
        }
    }
            
}



