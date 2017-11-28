//
//  Seed.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 25/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//
//  First load of data when opening the application for the first time

import Foundation
import RealmSwift
import Alamofire

let group = DispatchGroup()

class Seed {
    //    Check if data is already saved, otherwise saves.
    func run(clearData: Bool = false){
        Realm.Configuration.defaultConfiguration.deleteRealmIfMigrationNeeded = true
        
        let realm = try! Realm()
        if clearData {
            try! realm.write {
                realm.deleteAll()
            }
        }
        let cards = realm.objects(RCard.self)
        if cards.count == 0 {
            self.createLukesCard()
        }

//        COMMENTED FOR TEMPORARY BUG CONTORNATION!
//        explained at ProductsViewController->viewWillAppear()
//
//        let products = realm.objects(RProduct.self)
//        if products.count == 0 {
//            self.loadProducts()
//        }
    }
    
    //    Save products of provided json
    func loadProducts(completionHander: @escaping () -> Swift.Void){
        group.enter()
        Alamofire.request("https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json").validate().responseJSON { response in
            switch response.result {
            case .success:
                print("Validation Successful")
                if let json = response.result.value {
                    let realm = try! Realm()
                    
                    for jProduct in json as! [Any] {
                        try! realm.write({
                            realm.create(RProduct.self, value: jProduct)
                        })
                    }
                    completionHander()
                    group.leave()
                }
            case .failure(let error):
                print(error)
                group.leave()
            }
        }
    }
    
    //    Luke's card
    //    "Do or do not, there is no try." Says Yoda
    func createLukesCard(){
        let realm = try! Realm()
        try! realm.write({
            let card = RCard()
            
            card.number = "1234123412341234"
            card.cvv = 7990
            card.holder_name = "Luke Skywalker"
            
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "dd/mm/yyyy"
            let date = dateFormatter.date(from: "01/12/2024")
            card.exp_date = date!
            
            realm.add(card)
        })
    }
}
