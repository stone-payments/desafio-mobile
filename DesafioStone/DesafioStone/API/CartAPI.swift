//
//  CartAPI.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 28/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import Foundation
import Alamofire
import SwiftyJSON

class CartAPI {
  
  static let shared = CartAPI()
  
  static var requestHeaders: HTTPHeaders {
    return [
      "Accept" : "application/json"
    ]
  }
  
  func chekoutPurchase(_ transaction: PurchaseModel, completion: @escaping (Error?) -> Void) {
    
    let checkoutURL: URLConvertible = "http://private-30abf-desafiostoneluan1.apiary-mock.com/transactions"
    let checkoutParameters: Parameters = ["card_number": transaction.card_number,
                                          "value": transaction.value,
                                          "cvv": transaction.cvv,
                                          "card_holder_name": transaction.card_holder_name,
                                          "exp_date": transaction.exp_date]
    
    Alamofire.request(checkoutURL, method: .post, parameters: checkoutParameters, headers: CartAPI.requestHeaders)
      .validate(statusCode: 200..<300)
      .validate(contentType: ["application/json"])
      .responseJSON { response in
        
        switch response.result {
        case .success:
          if let json = response.result.value {
            print("JSON: \(json)")
          }
          
          
          completion(nil)
          
        case .failure(let error):
          print(response.result.debugDescription)
          completion(error)
        }
    }
    
  }

}
