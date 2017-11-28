//
//  DataHelper.swift
//  Stone Store Star
//
//  Created by Kennedy Noia on 27/11/2017.
//  Copyright © 2017 Kennedy Noia. All rights reserved.
//
// Simple model layer to abstract the manage stored data.

import Foundation
import RealmSwift
import Alamofire

class DataHelper{
    //    Search for an open order to contain the items
    //in the shopping cart, if it does not exist creates.
    func getOpenedOrder() -> ROrder {
        let realm = try! Realm()
        var openedOrder = realm.objects(ROrder.self).filter("status = 0").first
        if openedOrder == nil {
            try! realm.write({
                let newOrder = ROrder()
                newOrder.statusEnum = .Open
                realm.add(newOrder)
                openedOrder = newOrder
            })
        }
        return openedOrder!
    }
    
    func getAllTransactions() -> Results<RTransaction> {
        let realm = try! Realm()
        return realm.objects(RTransaction.self)
    }
    
    func getAllProducts() -> Results<RProduct> {
        let realm = try! Realm()
        return realm.objects(RProduct.self)
    }
    
    //  Add a selected product to opened order
    func addToOpenedOrder(product: RProduct) {
        let realm = try! Realm()
        let openedOrder = self.getOpenedOrder()
        
        try! realm.write({
            openedOrder.products.append(product)
        })
    }
    
    //    Remove a product from the opened order
    func removeFromOpenedOrder(product: RProduct) {
        let realm = try! Realm()
        let openedOrder = self.getOpenedOrder()
        
        try! realm.write({
            for (index,productInsideOrder) in openedOrder.products.enumerated() {
                if (productInsideOrder.title == product.title &&
                    productInsideOrder.price == product.price &&
                    productInsideOrder.seller == product.seller){
                    openedOrder.products.remove(at: index)
                    return
                }
            }
        })
    }
    
    //    Returns the Luke's card
    func getUserCard() -> RCard {
        let realm = try! Realm()
        let userCard = realm.objects(RCard.self).last
        return userCard!
    }
    
    //    Makes a POST request to get aprovement of transaction
    //with card and value data. If the transaction was aproved,
    //changes the order to a closed one and saves the transaction.
    func closeOrder(completionHander: @escaping () -> Swift.Void){
        let order = self.getOpenedOrder()
        let card = self.getUserCard()
        var totalPrice = 0
        
        for product in order.products {
            totalPrice += product.price
        }
        
        let calendar = Calendar.current
        let month = calendar.component(.month, from: card.exp_date)
        let year = calendar.component(.year, from: card.exp_date)
        let parameters: Parameters = [
            "card_number": card.number,
            "value": String(totalPrice),
            "cvv": String(card.cvv),
            "card_holder_name": card.holder_name,
            "exp_date": "\(month)/\(year)"
        ]
        group.enter()
        Alamofire.request("http://private-9c299-stonestorestar.apiary-mock.com/transactions",
                          method: .post,
                          parameters: parameters,
                          encoding: JSONEncoding.default).responseJSON { response in
            switch response.result {
            case .success:
                print("Validation Successful")
                if response.result.value != nil {
                    let realm = try! Realm()

                    try! realm.write({
                        order.statusEnum = .Closed
                        
                        let newOrder = ROrder()
                        newOrder.statusEnum = .Open
                        realm.add(newOrder)
                        
                        let transaction = RTransaction()
                        transaction.card = card
                        transaction.value = totalPrice
                        realm.add(transaction)
                        
                        completionHander()
                    })
                }
                group.leave()
            case .failure(let error):
                print(error)
                print("RESPONSE~>")
                debugPrint(response)
                group.leave()
            }
        }
    }
}
