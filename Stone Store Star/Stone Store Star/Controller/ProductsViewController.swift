//
//  ViewController.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 24/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import UIKit
import Alamofire
import AlamofireImage
import RealmSwift

class ProductsViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource{
    @IBOutlet weak var productsCollectionView: UICollectionView!
    let dataHelper = DataHelper()
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        let products = dataHelper.getAllProducts()
        return products.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        // Allow load image even if the content header is missing
        DataRequest.addAcceptableImageContentTypes(["image/jpg"])
        
        let products = dataHelper.getAllProducts()
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "productCell", for: indexPath) as! ProductCollectionViewCell
        cell.product = products[indexPath.item]
        
        // To do: Helper method to format price value.
        let price = products[indexPath.item].price
        var strPrice = String(price)
        strPrice.insert(".", at: strPrice.index(strPrice.endIndex, offsetBy: -2))
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = NSLocale(localeIdentifier: "pt_BR") as Locale!
        let priceText = formatter.string(from: NSNumber.init(floatLiteral: Double(strPrice)!))
        
        cell.priceLabel.text = "\(String(describing: priceText!))"
        cell.nameLabel.text = products[indexPath.item].title
        cell.sellerLabel.text = products[indexPath.item].seller
        
        let url = URL(string: products[indexPath.item].thumbnailHd)!
        cell.image.af_setImage(withURL: url, placeholderImage: UIImage(named: "background")!)
        cell.layer.cornerRadius = 6.0
        cell.clipsToBounds = true

        let openedOrder = dataHelper.getOpenedOrder()
        // Check if the item already been added to opened order before
        for product in openedOrder.products {
            if product.title == products[indexPath.item].title &&
                product.price == products[indexPath.item].price &&
                product.seller == products[indexPath.item].seller
            {
                cell.cartButton.setTitle("Remover", for: .normal)
                cell.cartButton.backgroundColor = cell.cartButton.titleLabel?.textColor
                cell.cartButton.setTitleColor(UIColor.white, for: .normal)
            }
        }
        return cell
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    override func viewWillAppear(_ animated: Bool) {
        // Reload data from collection view, considering
        //the block of alamofire async task
//BUG FOUND! marking as selected aleatory items after segue
//        group.notify(queue: .main) {
//            self.productsCollectionView.reloadData()
//        }
        
//        TEMPORARY BUG CONTORNATION!
        let realm = try! Realm()
        let products = realm.objects(RProduct.self)
        if products.count == 0 {
            Alamofire.request("https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json").validate().responseJSON { response in
                switch response.result {
                case .success:
                    print("Validation Successful")
                    if let json = response.result.value {
                        print("JSON: \(json)") // serialized json response
                        debugPrint(json)
                        let realm = try! Realm()
                        
                        for jProduct in json as! [Any] {
                            debugPrint(jProduct)
                            
                            try! realm.write({
                                realm.create(RProduct.self, value: jProduct)
                            })
                        }
                        self.productsCollectionView.reloadData()
                    }
                case .failure(let error):
                    print(error)
                }
            }
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
