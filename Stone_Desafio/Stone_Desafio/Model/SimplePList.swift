//
//  SimplePList.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 28/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import Foundation

// Class controlling Plist functions
class SimplePList {
    
    var plistName:String
    private var path:String = ""
    
    init (plistName:String) {
        
        self.plistName = plistName
        self.path = getPath(plistName: plistName)
    }
    
    func getPath(plistName:String)->String {
        let path = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)[0] as String + "/" + plistName + ".plist"
        print("Path: " + path)
        return path
    }
    
    func saveData(dataToSave:[String:AnyObject])->Bool {
        let content = dataToSave as NSDictionary
        let result = content.write(toFile: path, atomically: false)
        return result
    }
    
    
    func getData()->[String:AnyObject] {
        let content = NSDictionary(contentsOfFile: path)
        return content as! [String:AnyObject]
    }
    func saveDataToDirectory() {
        
        var pathLocal = Bundle.main.path(forResource: "Transactions", ofType: "plist")
        let contentLocal = NSDictionary(contentsOfFile: pathLocal!)
        
        let content = NSDictionary(contentsOfFile: path)
        
        if (content == nil) {
            saveData(dataToSave: contentLocal as! [String : AnyObject])
        }
    }
}
