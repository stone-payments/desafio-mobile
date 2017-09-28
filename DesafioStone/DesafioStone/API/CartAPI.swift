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
  
  func chekoutPurchase(_ transaction: PurchaseModel, completion: @escaping (Error?) -> Void) {
    
    let checkoutURL: URLConvertible = ""
    let checkoutParameters: Parameters = [:]
    
    Alamofire.request(checkoutURL, method: .post, parameters: checkoutParameters)
      .validate(statusCode: 200..<300)
      .validate(contentType: ["application/json"])
      .responseJSON { response in
        
        switch response.result {
        case .success:
          if let json = response.result.value {
            print("JSON: \(json)")
          }
          
          if let responseData = response.data {
            let json = JSON(data: responseData)
            
            
          }
          completion(nil)
          
        case .failure(let error):
          print(response.result.debugDescription)
          completion(error)
        }
    }
    
  }

}
