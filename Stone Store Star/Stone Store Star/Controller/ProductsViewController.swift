//
//  ViewController.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 24/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//

import UIKit
import RealmSwift
import Alamofire
import AlamofireImage

class ProductsViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource{
    @IBOutlet weak var productsCollectionView: UICollectionView!
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        let realm = try! Realm()
        return realm.objects(RProduct.self).count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        DataRequest.addAcceptableImageContentTypes(["image/jpg"])
        
        let realm = try! Realm()
        let products = realm.objects(RProduct.self)
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "productCell", for: indexPath) as! ProductCollectionViewCell
        cell.product = products[indexPath.item]

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
        
        DataRequest.addAcceptableImageContentTypes(["image/jpg"])
        let url = URL(string: products[indexPath.item].thumbnailHd)!
        cell.image.af_setImage(withURL: url, placeholderImage: UIImage(named: "background")!)
        cell.layer.cornerRadius = 6.0
        cell.clipsToBounds = true

        let mHelper = MHelper()
        let openedOrder = mHelper.getOpenedOrder()
        for product in openedOrder.products {
            if product.title == cell.product?.title &&
                product.price == cell.product?.price &&
                product.seller == cell.product?.seller
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
        
//        if productsCollectionView.contentSize.height == 0.0 {
//            sleep(4)
//            productsCollectionView.reloadData()
//        }
        self.productsCollectionView.reloadData()
    }
    override func viewWillAppear(_ animated: Bool) {
        // Move to a background thread to do some long running work
//        DispatchQueue.global(qos: .userInitiated).async {
//            let image = self.loadOrGenerateAnImage()
//            // Bounce back to the main thread to update the UI
//            DispatchQueue.main.async {
//                productsCollectionView.reloadData()
//            }
//        }
//        DispatchQueue.async(execure)
//        dispatch_async(dispatch_get_main_queue(), {
//
//        })
//        DispatchQueue.main.async {
//            self.productsCollectionView.reloadData()
//        }
        
        
        let realm = try! Realm()
        let products = realm.objects(RProduct.self)
        if products.count == 0 {
            group.enter()
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
                        group.leave()
                    }
                case .failure(let error):
                    print(error)
                    group.leave()
                }
            }
        }
//        group.notify(queue: .main) {
//            self.productsCollectionView.reloadData()
//        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
