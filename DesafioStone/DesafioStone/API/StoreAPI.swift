//
//  StoreAPI.swift
//  DesafioStone
//
//  Created by Luan Barbalho Kalume on 26/09/17.
//  Copyright © 2017 Luan. All rights reserved.
//

import Foundation
import Alamofire
import SwiftyJSON

class StoreAPI {
  
  static let shared = StoreAPI()
  
  func getStoreProducts(completion: @escaping ([ProductModel]?) -> Void) {
    
    var modelList = [ProductModel]()
    
    let requestURL: URLConvertible = "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json"
    Alamofire.request(requestURL, method: .get, parameters: nil)
      .validate(statusCode: 200..<300)
      .responseJSON { response in
        
        switch response.result {
        case .success:
          
          if let json = response.result.value {
            print("JSON: \(json)")
          }
          
          if let responseData = response.data {
            let json = JSON(data: responseData)
            for i in 0..<json.count {
              modelList.append(ProductModel(title: json[i]["title"].stringValue,
                                            price: json[i]["price"].doubleValue,
                                            zipcode: json[i]["zipcode"].stringValue,
                                            seller: json[i]["seller"].stringValue,
                                            thumbnailHd: json[i]["thumbnailHd"].stringValue,
                                            date: json[i]["date"].stringValue))
            }
          }
          completion(modelList)
          
        case .failure:
          print(response.result.debugDescription)
          completion(nil)
        }
    }
  }
}
