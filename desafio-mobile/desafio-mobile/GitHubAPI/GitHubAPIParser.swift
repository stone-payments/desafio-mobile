//
//  GitHubAPIParser.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import Foundation


open class GitHubAPIParser {
    
    static let defaultJsonResultsKey: String  = "results"
    
    static open func dictionaryObject(_ object: Any?) -> [String : Any]? {
        return objectOfType(object, type: [String : Any].self)
    }
    
    static open func arrayObject(_ object: Any?) -> [Any]? {
        return objectOfType(object, type: [Any].self)
    }
    
    static open func parseJSON(_ object: Any?) -> Any?  {
        if let arrayObject = object as? NSArray {
            return objectOfType(arrayObject, type: [Any].self)
        }
        return objectOfType(object, type: [String : Any].self)
    }
    
    static func objectOfType<T>(_ object: Any?, type: T.Type) -> T? {
        
        guard let _obj = object as? T else {
            return nil
        }
        
        return _obj
    }
}
