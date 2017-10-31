//
//  Extensions.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//


import UIKit


// MARK: - Reusable View
protocol ReusableView: class {
    static var defaultReuseIdentifier: String { get }
}

extension ReusableView where Self: UIView {
    static var defaultReuseIdentifier: String {
        return NSStringFromClass(self).components(separatedBy: ".").last ?? String(describing: Mirror(reflecting: self).subjectType)
    }
}

extension UIView: ReusableView {}


extension UITableView {
    
    func insertItems(atSection section: Int, newCount: Int) {
        
        beginUpdates()
        
        let rowsCount = numberOfRows(inSection: section)
        
        let maxCount = max(rowsCount, newCount)
        let minCount = min(rowsCount, newCount)
        
        var changed = [IndexPath]()
        
        for i in minCount..<maxCount {
            let indexPath = IndexPath(row: i, section: section)
            changed.append(indexPath)
        }
        
        if newCount > rowsCount {
            insertRows(at: changed, with: .automatic)
        } else if rowsCount > newCount {
            deleteRows(at: changed, with: .automatic)
        } else {
            reloadSections(IndexSet(integer: section), with: .automatic)
        }
        
        endUpdates()
    }
}

extension UIColor {
    
    class var greenCart: UIColor {
        return UIColor(red: 39, green: 174, blue: 96, alpha: 1)
    }
}
