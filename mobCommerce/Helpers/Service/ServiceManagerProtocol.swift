//
//  ServiceManagerProtocol.swift
//  mobCommerce
//
//  Created by Rafael de Paula on 26/08/17.
//  Copyright © 2017 Rafael de Paula. All rights reserved.
//

protocol ServiceManagerProtocol {
    
    func Get(url: String, parameters: [String : Any]?, success: @escaping (Any) -> Void, failure: @escaping (ServiceError) -> Void)
    func Post(url: String, parameters: [String : Any]?, success: @escaping (Any) -> Void, failure: @escaping (ServiceError) -> Void)
    
}
