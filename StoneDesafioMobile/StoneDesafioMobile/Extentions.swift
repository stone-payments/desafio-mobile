//
//  Extentions.swift
//  StoneDesafioMobile
//
//  Created by Gabriela Schirmer Mauricio on 27/09/17.
//  Copyright © 2017 Gabriela Schirmer Mauricio. All rights reserved.
//

import Foundation
import UIKit

extension String {
    func stringWithOnlyNumbers() -> String {
        return self.characters.reduce("") { (acc, c) -> String in
            guard c.isDigit() else { return acc }
            return "\(acc)\(c)"
        }
    }
    
    func withMask(mask: String) -> String {
        var resultString = String()
        
        let chars = self.characters
        let maskChars = mask.characters
        
        var stringIndex = chars.startIndex
        var maskIndex = mask.startIndex
                
        while stringIndex < chars.endIndex && maskIndex < maskChars.endIndex {
            if (maskChars[maskIndex] == "#") {
                resultString.append(chars[stringIndex])
                stringIndex = stringIndex.successor(in: mask)
            } else {
                resultString.append(maskChars[maskIndex])
            }
            maskIndex = maskIndex.successor(in: mask)
        }
        
        return resultString
    }
}

extension String.Index{
    func successor(in string:String)->String.Index{
        return string.index(after: self)
    }
    
    func predecessor(in string:String)->String.Index{
        return string.index(before: self)
    }
    
    func advance(_ offset:Int, `for` string:String)->String.Index{
        return string.index(self, offsetBy: offset)
    }
}

extension Character {
    func isDigit() -> Bool {
        let s = String(self).unicodeScalars
        let uni = s[s.startIndex]
        
        let digits = NSCharacterSet.decimalDigits
        let isADigit = digits.contains(uni)
        
        return isADigit
    }
}

extension UIViewController {
    func hideKeyboardWhenTappedAround() {
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(UIViewController.dismissKeyboard))
        tap.cancelsTouchesInView = false
        view.addGestureRecognizer(tap)
    }
    
    @objc func dismissKeyboard() {
        view.endEditing(true)
    }
}
