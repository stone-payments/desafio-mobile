
//
//  ServiceManager.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 22/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

import Foundation
import Alamofire

final class ServiceManager: ServiceManagerProtocol {
    
    static let shared = ServiceManager()
    
    func Get(url: String, parameters: [String : Any]?, success: @escaping (Any) -> Void, failure: @escaping (ServiceError) -> Void) {
        
        Alamofire.request(URL(string: url)!,
                          method: .get,
                          parameters: parameters)
            .validate()
            .responseJSON { (response) -> Void in

                guard response.result.isSuccess else {
                    failure(ServiceError(code: (response.response?.statusCode)!))
                    return
                }
        
                success(response.result.value!)
        }
    }

    func Post(url: String, parameters: [String : Any]?, success: @escaping (Any) -> Void, failure: @escaping (ServiceError) -> Void) {
        
        Alamofire.request(URL(string: url)!,
                          method: .post,
                          parameters: parameters)
            .validate()
            .responseJSON { (response) -> Void in
                
                guard response.result.isSuccess else {
                    failure(ServiceError(code: (response.response?.statusCode)!))
                    return
                }
                
                success(response.result.value!)
        }
    }
    
}
