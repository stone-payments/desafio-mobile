//
//  Section.swift
//  desafio-mobile
//
//  Created by Jade Silveira on 29/10/17.
//  Copyright © 2017 Jade Silveira. All rights reserved.
//

import UIKit


protocol SectionOutput: class {
    func reloadSection(_ section: Section)
    func insertItems(atSection section: Section)
}

class Section: NSObject {
    
    var items: [Any] = []
    
    weak var parentOutput: SectionOutput?
    
    init(parentOutput: SectionOutput) {
        self.parentOutput = parentOutput
    }
    
    func didSelectRow(atIndexPath indexPath: IndexPath) {}
    
    func heightForCell(atIndexPath indexPath: IndexPath) -> CGFloat { return 0 }
    
    func heightForFooter(section: Int) -> CGFloat { return 0.0001 }
    
    func tableViewCell() -> UITableViewCell.Type { return UITableViewCell.self }
    
    func willDisplayCell(_ cell: UITableViewCell, atIndexPath indexPath: IndexPath) {}
    
    func willDisplayFooter(_ view: UIView, forSection section: Int) {}
}
