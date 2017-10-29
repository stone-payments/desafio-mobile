//
//  AuxiliaryFunctions.swift
//  Stone_Desafio
//
//  Created by Pedro Velmovitsky on 26/10/17.
//  Copyright © 2017 stone_desafio. All rights reserved.
//

import Foundation
import UIKit

// Auxiliary functions used throughout the code

// MARK: Return image from URL
func setImageFromURl(stringImageUrl url: String) -> UIImage?{
    if let url = NSURL(string: url) {
        if let data = NSData(contentsOf: url as URL) {
            let image = UIImage(data: data as Data)
            let returnImage = resizeImage(image: image!, targetSize: CGSize(width: 150, height: 150))
            return returnImage
        }
    }
    return nil
}

// MARK: Resize image
func resizeImage(image: UIImage, targetSize: CGSize) -> UIImage {
    let size = image.size
    
    let widthRatio  = targetSize.width  / image.size.width
    let heightRatio = targetSize.height / image.size.height
    
    // Figure out what our orientation is, and use that to form the rectangle
    var newSize: CGSize
    if(widthRatio > heightRatio) {
        newSize = CGSize(width: size.width * heightRatio, height: size.height * heightRatio)
    } else {
        newSize = CGSize(width: size.width * widthRatio,  height: size.height * widthRatio)
    }
    
    // This is the rect that we've calculated out and this is what is actually used below
    let rect = CGRect(x: 0, y: 0, width: newSize.width, height: newSize.height)
    
    // Actually do the resizing to the rect using the ImageContext stuff
    UIGraphicsBeginImageContextWithOptions(newSize, false, 1.0)
    image.draw(in: rect)
    let newImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()
    
    return newImage!
}

// MARK: Convert price (Int) to currency format
func priceToCurrency(price: Int)-> String {
    let formatter = NumberFormatter()
    formatter.numberStyle = .currency
    if let formattedAmount = formatter.string(from: price as NSNumber) {
        return formattedAmount
    }
    return "Erro"
}

// MARK: UIViewController extension to dismiss keyboard
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

